package com.example.cinema;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.model.Funcion;
import com.example.model.Pelicula;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InformacionPeliculaActivity extends AppCompatActivity {
    private TextView titleMovie;
    private TextView durationMovie;
    private ImageView imageMovie;
    private String titulo;
    private String duracion;
    private String nombreArchivo;
    private EditText editTextFecha;
    private List<Funcion> funciones;
    private Spinner spHorarios;
    private int peliculaID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_informacion_pelicula);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        funciones = cargarFuncionesDesdeArchivos();



        titleMovie = findViewById(R.id.titleMovie);
        durationMovie = findViewById(R.id.durationMovie);
        imageMovie = findViewById(R.id.imageMovie);

        titulo = getIntent().getStringExtra("Titulo");
        duracion = getIntent().getStringExtra("Duracion");
        nombreArchivo = getIntent().getStringExtra("Imagen");
        peliculaID = getIntent().getIntExtra("PeliculaId", 0);

        titleMovie.setText(titulo);
        durationMovie.setText(duracion);

        int imageResId = getResources().getIdentifier(nombreArchivo, "drawable", getPackageName());
        imageMovie.setImageResource(imageResId);

        spHorarios = findViewById(R.id.spHorarios);


        editTextFecha = findViewById(R.id.editTextFecha);

        editTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_WEEK);

                // Configurar el DatePicker
                DatePickerDialog dpicker = new DatePickerDialog(InformacionPeliculaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int anio, int mes, int dia) {
                        String fechaSeleccionada = String.format("%02d/%02d/%04d", dia, mes + 1, anio);
                        editTextFecha.setText(fechaSeleccionada);
                        String[] datossp = mostrarFuncionesSpinner(fechaSeleccionada);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(InformacionPeliculaActivity.this, android.R.layout.simple_spinner_item, datossp);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spHorarios.setAdapter(adapter);
                    }
                }, year, month, day);
                dpicker.show();
            }
        });
    }

    public String[] mostrarFuncionesSpinner(String fecha){
        ArrayList<String> elemntos = new ArrayList<>();
        for(Funcion funcion: funciones){
            if(funcion.getFecha().equals(fecha) && funcion.getIdPelicula() == peliculaID){
                elemntos.add(funcion.toString());
            }
        }
        return elemntos.toArray(new String[0]);
    }

    public List<Funcion> cargarFuncionesDesdeArchivos() {
        List<Funcion> funciones = new ArrayList<>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.funciones);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 5) {
                    int funcionID = Integer.parseInt(partes[0].trim());
                    int peliculaID = Integer.parseInt(partes[1].trim());
                    String titulo = partes[2].trim();
                    String duracion = partes[3].trim();
                    String nombreArchivo = partes[4].trim();
                    funciones.add(new Funcion(funcionID ,peliculaID, titulo, duracion, nombreArchivo));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return funciones;
    }

}