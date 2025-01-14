package com.example.cinema;

import android.os.Bundle;

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

    }

    public List<Pelicula> cargarPeliculasDesdeArchivo() {
        List<Pelicula> peliculasList = new ArrayList<>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.peliculas);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    // Convertir el primer campo a int (idPelicula)
                    int peliculaID = Integer.parseInt(partes[0].trim());
                    String titulo = partes[1].trim();
                    int duracion = Integer.parseInt(partes[2].trim());
                    peliculasList.add(new Pelicula(peliculaID, titulo, duracion));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return peliculasList;

    }
}

