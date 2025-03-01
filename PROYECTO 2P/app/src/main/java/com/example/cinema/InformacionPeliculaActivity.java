package com.example.cinema;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.exception.DatosIncompletosException;
import com.example.model.Funcion;
import com.example.exception.SinFuncionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Activity que muestra la información de una película, incluyendo su título, duración, imagen,
 * fecha, horario y el número de entradas. Permite al usuario seleccionar la fecha y el horario de la función
 * para luego proceder con la compra de entradas.
 */
public class InformacionPeliculaActivity extends AppCompatActivity {
    private TextView titleMovie;
    private TextView durationMovie;
    private ImageView imageMovie;
    private String titulo;
    private String duracion;
    private String nombreArchivo;
    private EditText editTextFecha;
    private EditText editTextEntradas;
    private List<Funcion> funciones;
    private Spinner spHorarios;
    private int peliculaID;
    private Button btnContinuar;
    private Button btnCancelar;

    /**
     * Método llamado al crear la actividad. Configura la interfaz de usuario, carga las funciones desde un archivo
     * y maneja los eventos de interacción con los elementos.
     *
     * @param savedInstanceState El estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_informacion_pelicula);

        // Configuración de la interfaz de usuario.
        funciones = cargarFuncionesDesdeArchivos();

        // Inicialización de los componentes de la interfaz.
        titleMovie = findViewById(R.id.titleMovie);
        durationMovie = findViewById(R.id.durationMovie);
        imageMovie = findViewById(R.id.imageMovie);
        editTextFecha = findViewById(R.id.editTextFecha);
        editTextEntradas = findViewById(R.id.editTextEntradas);
        spHorarios = findViewById(R.id.spHorarios);
        btnContinuar = findViewById(R.id.btnContinuar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Obtención de los datos pasados a través del Intent.
        titulo = getIntent().getStringExtra("Titulo");
        duracion = getIntent().getStringExtra("Duracion") + " " + "min";
        nombreArchivo = getIntent().getStringExtra("Imagen");
        peliculaID = getIntent().getIntExtra("PeliculaId", 0);

        // Actualización de la interfaz con los datos de la película.
        titleMovie.setText(titulo);
        durationMovie.setText(duracion);
        int imageResId = getResources().getIdentifier(nombreArchivo, "drawable", getPackageName());
        imageMovie.setImageResource(imageResId);

        final int[] idFuncion = {0};

        // Configuración del selector de fecha.
        editTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_WEEK);

                // Configuración del DatePicker
                DatePickerDialog dpicker = new DatePickerDialog(InformacionPeliculaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int anio, int mes, int dia) {
                        String fechaSeleccionada = String.format("%02d/%02d/%04d", dia, mes + 1, anio);
                        editTextFecha.setText(fechaSeleccionada);

                        // Cargar los horarios para la fecha seleccionada.
                        ArrayList<String> elemntos = new ArrayList<>();
                        for(Funcion funcion: funciones) {
                            if(funcion.getFecha().equals(fechaSeleccionada) && funcion.getIdPelicula() == peliculaID) {
                                elemntos.add(funcion.toString());
                                idFuncion[0] = funcion.getIdFuncion();
                            }
                        }

                        String[] datossp = elemntos.toArray(new String[0]);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(InformacionPeliculaActivity.this, android.R.layout.simple_spinner_item, datossp);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spHorarios.setAdapter(adapter);

                        // Si no hay funciones disponibles para la fecha, lanzar una excepción.
                        if (elemntos.isEmpty()) {
                            try {
                                throw new SinFuncionException();
                            } catch (SinFuncionException e) {
                                Toast.makeText(InformacionPeliculaActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    }
                }, year, month, day);

                dpicker.show();
            }
        });

        // Configuración del botón "Continuar".
        btnContinuar.setOnClickListener(v -> {
            try {
                validarCampos();
                Toast.makeText(this, "Datos completos, continuando...", Toast.LENGTH_SHORT).show();

                // Enviar los datos a la siguiente actividad.
                Intent intent = new Intent(this, DetalleFuncion.class);
                intent.putExtra("Titulo", titulo);
                intent.putExtra("Duracion", duracion);
                intent.putExtra("Fecha", editTextFecha.getText().toString());
                intent.putExtra("Horario", spHorarios.getSelectedItem().toString());
                intent.putExtra("Entradas", editTextEntradas.getText().toString());
                intent.putExtra("Funcion", idFuncion[0]);
                startActivity(intent);

            } catch (DatosIncompletosException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Configuración del botón "Cancelar".
        btnCancelar.setOnClickListener(v -> {
            Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    /**
     * Carga las funciones de cine desde un archivo de recursos raw.
     *
     * @return Lista de funciones.
     */
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
                    funciones.add(new Funcion(funcionID, peliculaID, titulo, duracion, nombreArchivo));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return funciones;
    }

    /**
     * Valida los campos del formulario antes de proceder con la compra de entradas.
     *
     * @throws DatosIncompletosException Si algún campo obligatorio está vacío.
     */
    private void validarCampos() throws DatosIncompletosException {
        String fecha = editTextFecha.getText().toString();
        String horario = spHorarios.getSelectedItem() != null ? spHorarios.getSelectedItem().toString() : "";
        String numeroEntradas = editTextEntradas.getText().toString();

        if (fecha.isEmpty()) {
            throw new DatosIncompletosException("La fecha no está seleccionada.");
        }
        if (horario.isEmpty()) {
            throw new DatosIncompletosException("El horario no está seleccionado.");
        }
        if (numeroEntradas.isEmpty()) {
            throw new DatosIncompletosException("El número de entradas no está especificado.");
        }
    }
}
