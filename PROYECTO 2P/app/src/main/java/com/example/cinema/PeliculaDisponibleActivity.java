package com.example.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.model.Pelicula;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Activity que muestra las películas disponibles en la aplicación.
 * Esta actividad carga las películas desde un archivo y las muestra como imágenes.
 * Al hacer clic en una imagen de película, se abre una nueva actividad con los detalles de la película.
 */
public class PeliculaDisponibleActivity extends AppCompatActivity {

    // Lista que contiene las películas disponibles.
    private List<Pelicula> peliculas;

    // Imágenes de las películas
    ImageView deadpoolMovie;
    ImageView capitanMovie;
    ImageView avatarMovie;
    ImageView duneMovie;
    ImageView godzillaMovie;
    ImageView insideMovie;
    ImageView juegosMovie;
    ImageView wickedMovie;
    ImageView reinoMovie;
    ImageView misionMovie;

    /**
     * Método llamado cuando se crea la actividad.
     * Configura la vista de la actividad, carga las películas y asigna los listeners a las imágenes.
     *
     * @param savedInstanceState El estado guardado de la actividad, si existe.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Habilitar Edge to Edge para que la interfaz ocupe toda la pantalla
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_peliculas_disponibles);

        // Ajustar el padding de la vista TvFuncion según los márgenes de las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.TvFuncion), (v, insets) -> {
            // Obtener los márgenes de las barras del sistema (barras de estado y navegación)
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Establecer los márgenes como padding de la vista
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Cargar las películas desde el archivo
        peliculas = cargarPeliculasDesdeArchivo();

        // Inicializar las vistas de las películas
        deadpoolMovie = findViewById(R.id.idDeadpool);
        capitanMovie = findViewById(R.id.idCapitan);
        avatarMovie = findViewById(R.id.idAvatar);
        duneMovie = findViewById(R.id.idDune);
        godzillaMovie = findViewById(R.id.idGodzilla);
        insideMovie = findViewById(R.id.idInsideOut);
        juegosMovie = findViewById(R.id.idJuegos);
        wickedMovie = findViewById(R.id.idWicked);
        reinoMovie = findViewById(R.id.idReino);
        misionMovie = findViewById(R.id.idMision);

        // Asignar los listeners a las imágenes de las películas
        deadpoolMovie.setOnClickListener(v -> openDetails(peliculas.get(2).getPeliculaID(), peliculas.get(2).getTitulo(), peliculas.get(2).getDuracion(), peliculas.get(2).getNombreArchivo()));
        capitanMovie.setOnClickListener(v -> openDetails(peliculas.get(8).getPeliculaID(), peliculas.get(8).getTitulo(), peliculas.get(8).getDuracion(), peliculas.get(8).getNombreArchivo()));
        avatarMovie.setOnClickListener(v -> openDetails(peliculas.get(6).getPeliculaID(), peliculas.get(6).getTitulo(), peliculas.get(6).getDuracion(), peliculas.get(6).getNombreArchivo()));
        duneMovie.setOnClickListener(v -> openDetails(peliculas.get(0).getPeliculaID(), peliculas.get(0).getTitulo(), peliculas.get(0).getDuracion(), peliculas.get(0).getNombreArchivo()));
        godzillaMovie.setOnClickListener(v -> openDetails(peliculas.get(3).getPeliculaID(), peliculas.get(3).getTitulo(), peliculas.get(3).getDuracion(), peliculas.get(3).getNombreArchivo()));
        insideMovie.setOnClickListener(v -> openDetails(peliculas.get(1).getPeliculaID(), peliculas.get(1).getTitulo(), peliculas.get(1).getDuracion(), peliculas.get(1).getNombreArchivo()));
        juegosMovie.setOnClickListener(v -> openDetails(peliculas.get(9).getPeliculaID(), peliculas.get(9).getTitulo(), peliculas.get(9).getDuracion(), peliculas.get(9).getNombreArchivo()));
        wickedMovie.setOnClickListener(v -> openDetails(peliculas.get(4).getPeliculaID(), peliculas.get(4).getTitulo(), peliculas.get(4).getDuracion(), peliculas.get(4).getNombreArchivo()));
        reinoMovie.setOnClickListener(v -> openDetails(peliculas.get(7).getPeliculaID(), peliculas.get(7).getTitulo(), peliculas.get(7).getDuracion(), peliculas.get(7).getNombreArchivo()));
        misionMovie.setOnClickListener(v -> openDetails(peliculas.get(5).getPeliculaID(), peliculas.get(5).getTitulo(), peliculas.get(5).getDuracion(), peliculas.get(5).getNombreArchivo()));
    }

    /**
     * Método que abre una nueva actividad con los detalles de la película seleccionada.
     *
     * @param idPelicula El ID de la película.
     * @param titulo El título de la película.
     * @param duracion La duración de la película.
     * @param nombreImagen El nombre del archivo de imagen de la película.
     */
    private void openDetails(int idPelicula, String titulo, String duracion, String nombreImagen) {
        Intent intent = new Intent(PeliculaDisponibleActivity.this, InformacionPeliculaActivity.class);
        intent.putExtra("PeliculaId", idPelicula);
        intent.putExtra("Titulo", titulo);
        intent.putExtra("Duracion", duracion);
        intent.putExtra("Imagen", nombreImagen);
        startActivity(intent);
    }

    /**
     * Carga las películas desde un archivo de recursos en formato CSV.
     *
     * @return Una lista de películas cargadas desde el archivo.
     */
    public List<Pelicula> cargarPeliculasDesdeArchivo() {
        List<Pelicula> peliculasList = new ArrayList<>();
        try {
            // Abrir el archivo de recursos y leerlo línea por línea
            InputStream inputStream = getResources().openRawResource(R.raw.peliculas);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String linea;
            while ((linea = reader.readLine()) != null) {
                // Separar la línea en partes usando coma como delimitador
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    // Parsear los datos de la película y agregarla a la lista
                    int peliculaID = Integer.parseInt(partes[0].trim());
                    String titulo = partes[1].trim();
                    String duracion = partes[2].trim();
                    String nombreArchivo = partes[3].trim();
                    peliculasList.add(new Pelicula(peliculaID, titulo, duracion, nombreArchivo));
                }
            }
            // Cerrar el lector después de procesar el archivo
            reader.close();
        } catch (IOException e) {
            // Manejar posibles errores al leer el archivo
            e.printStackTrace();
        }

        // Retornar la lista de películas cargadas
        return peliculasList;
    }
}