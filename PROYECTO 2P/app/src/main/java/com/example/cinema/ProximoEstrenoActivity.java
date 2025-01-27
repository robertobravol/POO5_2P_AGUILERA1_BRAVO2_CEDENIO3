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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * Activity que muestra los próximos estrenos de películas.
 * Esta actividad carga los datos de los estrenos desde un archivo de texto y los muestra en una tabla.
 * También permite navegar de vuelta a la actividad principal.
 */
public class ProximoEstrenoActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private List<Estreno> estrenosList;
    private Button btnGoToSecond;
    private Button btnOrdenar;

    /**
     * Método llamado cuando se crea la actividad.
     * Configura la vista de la actividad, carga los estrenos y llena dinámicamente la tabla con los datos.
     *
     * @param savedInstanceState El estado guardado de la actividad, si existe.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_proximos_estrenos);
        btnGoToSecond = findViewById(R.id.idBtSalir);
        btnOrdenar = findViewById(R.id.idBtOrdenar);
        btnOrdenar.setOnClickListener(v -> {
            Collections.sort(estrenosList, Comparator.comparing(Estreno::getTitulo));

            // Reconstruir la tabla con los datos ordenados
            mostrarDatosEnTabla(estrenosList);
        });
        btnGoToSecond.setOnClickListener(v -> {
            Intent intent = new Intent(ProximoEstrenoActivity.this, MainActivity.class);
            startActivity(intent);
        });

        tableLayout = findViewById(R.id.idTableEstreno);
        estrenosList = cargarEstrenosDesdeArchivo();
        mostrarDatosEnTabla(estrenosList);
    }

    /**
     * Método para mostrar los datos en la tabla.
     * Este método construye dinámicamente las filas de la tabla.
     *
     * @param estrenos La lista de estrenos que se deben mostrar.
     */
    private void mostrarDatosEnTabla(List<Estreno> estrenos) {

        TableLayout table = findViewById(R.id.idTableEstreno);
        int childCount = table.getChildCount();
        for (int i = 1; i < childCount; i++) {
            table.removeViewAt(1);
        }

        for (Estreno estreno : estrenos) {
            TableRow fila = new TableRow(this);
            TextView titulo = new TextView(this);
            titulo.setText(estreno.getTitulo());
            titulo.setPadding(8, 8, 8, 8);
            TextView fecha = new TextView(this);
            fecha.setText(estreno.getFecha());
            fecha.setPadding(8, 8, 8, 8);
            fila.addView(titulo);
            fila.addView(fecha);
            table.addView(fila);
        }
    }

    /**
     * Carga los estrenos desde un archivo de recursos en formato CSV.
     *
     * @return Una lista de estrenos cargados desde el archivo.
     */
    private List<Estreno> cargarEstrenosDesdeArchivo() {
        List<Estreno> estrenosList = new ArrayList<>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.estrenos);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
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



