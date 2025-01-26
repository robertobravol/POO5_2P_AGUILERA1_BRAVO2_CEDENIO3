package com.example.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Activity principal de la aplicación.
 * Esta actividad sirve como pantalla inicial, mostrando opciones para navegar a otras actividades.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Método llamado cuando se crea la actividad.
     * Configura la vista de la actividad y establece los listeners para los botones.
     *
     * @param savedInstanceState El estado guardado de la actividad, si existe.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Habilitar Edge to Edge para que la interfaz ocupe toda la pantalla
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Configurar el botón para iniciar la segunda actividad (IniciarSesionActivity)
        Button btnGoToSecond = findViewById(R.id.idLogin);
        btnGoToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para iniciar la actividad de inicio de sesión
                Intent intent = new Intent(MainActivity.this, IniciarSesionActivity.class);
                startActivity(intent);
            }
        });

        // Configurar el botón para iniciar la tercera actividad (ProximoEstrenoActivity)
        Button btGoTo = findViewById(R.id.idEstreno);
        btGoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para iniciar la actividad de próximos estrenos
                Intent intent = new Intent(MainActivity.this, ProximoEstrenoActivity.class);
                startActivity(intent);
            }
        });

        // Ajustar el padding de la vista TvFuncion según los márgenes de las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.TvFuncion), (v, insets) -> {
            // Obtener los márgenes de las barras del sistema (barras de estado y navegación)
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Establecer los márgenes como padding de la vista
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}