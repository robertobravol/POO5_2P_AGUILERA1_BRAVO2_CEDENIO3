package com.example.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.model.Estreno;
import com.example.model.Pelicula;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class PeliculasDisponiblesActivity extends AppCompatActivity {

    private List<Pelicula> peliculas;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_peliculas_disponibles);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        peliculas = cargarPeliculasDesdeArchivo();

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


    private void openDetails(int idPelicula, String titulo, String duracion, String nombreImagen) {
        Intent intent = new Intent(PeliculasDisponiblesActivity.this, InformacionPeliculaActivity.class);
        intent.putExtra("PeliculaId", idPelicula);
        intent.putExtra("Titulo", titulo);
        intent.putExtra("Duracion", duracion);
        intent.putExtra("Imagen", nombreImagen);
        startActivity(intent);
    }


    public List<Pelicula> cargarPeliculasDesdeArchivo() {
        List<Pelicula> peliculasList = new ArrayList<>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.peliculas);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    int peliculaID = Integer.parseInt(partes[0].trim());
                    String titulo = partes[1].trim();
                    String duracion = partes[2].trim();
                    String nombreArchivo = partes[3].trim();
                    peliculasList.add(new Pelicula(peliculaID, titulo, duracion, nombreArchivo));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return peliculasList;

    }
}