package com.example.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.model.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IniciarSesionActivity extends AppCompatActivity {

    private List<Usuario> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        // Cargar usuarios desde el archivo
        usuarios = cargarUsuarios();

        EditText usuarioInput = findViewById(R.id.idUserName);
        EditText contrasenaInput = findViewById(R.id.idUserClave);
        Button iniciarSesionButton = findViewById(R.id.idLog);

        iniciarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuarioIngresado = usuarioInput.getText().toString().trim();
                String contrasenaIngresada = contrasenaInput.getText().toString().trim();

                if (usuarioIngresado.isEmpty() || contrasenaIngresada.isEmpty()) {
                    Toast.makeText(IniciarSesionActivity.this, "Usuario o contraseña no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean accesoExitoso = false;
                for (Usuario usuario : usuarios) {
                    if (usuario.getUsuario().equals(usuarioIngresado) && usuario.getContrasena().equals(contrasenaIngresada)) {
                        accesoExitoso = true;
                        break;
                    }
                }

                if (accesoExitoso) {
                    Toast.makeText(IniciarSesionActivity.this, "Ingreso exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(IniciarSesionActivity.this, PeliculaDisponibleActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(IniciarSesionActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.usuarios);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 5) {
                    try {
                        int puesto = Integer.parseInt(partes[0]);
                        String nombre = partes[1];
                        String apellido = partes[2];
                        String usuario = partes[3];
                        String contrasena = partes[4];

                        usuarios.add(new Usuario(puesto, nombre, apellido, usuario, contrasena));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usuarios;
    }
}