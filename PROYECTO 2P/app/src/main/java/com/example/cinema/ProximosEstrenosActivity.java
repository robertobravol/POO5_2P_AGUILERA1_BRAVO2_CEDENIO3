package com.example.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.model.Estreno;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.widget.TableLayout;
import android.widget.TableRow;

public class ProximosEstrenosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);  // Esto es para habilitar la funcionalidad de pantalla completa, si lo necesitas.
        setContentView(R.layout.activity_proximos_estrenos);

        // Configurar el botón para iniciar la segunda actividad
        Button btnGoToSecond = findViewById(R.id.idBtSalir);
        btnGoToSecond.setOnClickListener(v -> {
            // Crear un Intent para iniciar la segunda actividad
            Intent intent = new Intent(ProximosEstrenosActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Configurar el TableLayout para mostrar los estrenos
        TableLayout tableLayout = findViewById(R.id.idTableEstreno);

        // Cargar datos del archivo estrenos.txt
        List<Estreno> estrenosList = cargarEstrenosDesdeArchivo();

        // Llenar la tabla dinámicamente con los datos de los estrenos
        for (Estreno estreno : estrenosList) {
            TableRow fila = new TableRow(this);

            TextView titulo = new TextView(this);
            titulo.setText(estreno.getTitulo());
            titulo.setPadding(8, 8, 8, 8);

            TextView fecha = new TextView(this);
            fecha.setText(estreno.getFecha());
            fecha.setPadding(8, 8, 8, 8);

            fila.addView(titulo);
            fila.addView(fecha);

            tableLayout.addView(fila);
        }
    }

    private List<Estreno> cargarEstrenosDesdeArchivo() {
        List<Estreno> estrenosList = new ArrayList<>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.estrenos);  // Asegúrate de que el archivo 'estrenos.txt' esté en la carpeta 'res/raw'
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    // Convertir el primer campo a int (idPelicula)
                    int idPelicula = Integer.parseInt(partes[0].trim());
                    String titulo = partes[1].trim();
                    String fecha = partes[2].trim();
                    estrenosList.add(new Estreno(idPelicula, titulo, fecha));
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return estrenosList;
    }
}

