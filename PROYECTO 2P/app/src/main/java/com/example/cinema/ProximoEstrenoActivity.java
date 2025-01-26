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

/**
 * Activity que muestra los próximos estrenos de películas.
 * Esta actividad carga los datos de los estrenos desde un archivo de texto y los muestra en una tabla.
 * También permite navegar de vuelta a la actividad principal.
 */
public class ProximoEstrenoActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private List<Estreno> estrenosList;
    private Button btnGoToSecond;

    /**
     * Método llamado cuando se crea la actividad.
     * Configura la vista de la actividad, carga los estrenos y llena dinámicamente la tabla con los datos.
     *
     * @param savedInstanceState El estado guardado de la actividad, si existe.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Habilitar Edge to Edge para que la interfaz ocupe toda la pantalla
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_proximos_estrenos);

        // Configurar el botón para iniciar la actividad principal
        btnGoToSecond = findViewById(R.id.idBtSalir);
        btnGoToSecond.setOnClickListener(v -> {
            // Crear un Intent para iniciar la actividad principal
            Intent intent = new Intent(ProximoEstrenoActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Configurar el TableLayout para mostrar los estrenos
        tableLayout = findViewById(R.id.idTableEstreno);

        // Cargar los datos del archivo estrenos.txt
        estrenosList = cargarEstrenosDesdeArchivo();

        // Llenar la tabla dinámicamente con los datos de los estrenos
        for (Estreno estreno : estrenosList) {
            TableRow fila = new TableRow(this);

            // Crear y configurar la vista para el título del estreno
            TextView titulo = new TextView(this);
            titulo.setText(estreno.getTitulo());
            titulo.setPadding(8, 8, 8, 8);

            // Crear y configurar la vista para la fecha del estreno
            TextView fecha = new TextView(this);
            fecha.setText(estreno.getFecha());
            fecha.setPadding(8, 8, 8, 8);

            // Agregar las vistas de título y fecha a la fila
            fila.addView(titulo);
            fila.addView(fecha);

            // Agregar la fila a la tabla
            tableLayout.addView(fila);
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
            // Abrir el archivo de recursos y leerlo línea por línea
            InputStream inputStream = getResources().openRawResource(R.raw.estrenos);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String linea;
            while ((linea = reader.readLine()) != null) {
                // Separar la línea en partes usando coma como delimitador
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    // Parsear los datos del estreno y agregarlo a la lista
                    int idPelicula = Integer.parseInt(partes[0].trim());
                    String titulo = partes[1].trim();
                    String fecha = partes[2].trim();
                    estrenosList.add(new Estreno(idPelicula, titulo, fecha));
                }
            }
            // Cerrar el lector después de procesar el archivo
            reader.close();
        } catch (IOException e) {
            // Manejar posibles errores al leer el archivo
            e.printStackTrace();
        }

        // Retornar la lista de estrenos cargados
        return estrenosList;
    }
}

