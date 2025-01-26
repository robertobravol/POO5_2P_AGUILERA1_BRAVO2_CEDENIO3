package com.example.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exception.CredencialesInvalidasException;
import com.example.exception.DatosIncompletosException;
import com.example.model.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity para iniciar sesión en la aplicación.
 * Esta actividad permite a los usuarios ingresar su nombre de usuario y contraseña
 * para verificar si coinciden con las credenciales almacenadas.
 * Si las credenciales son válidas, se accede a la siguiente actividad.
 */
public class IniciarSesionActivity extends AppCompatActivity {
    private List<Usuario> usuarios;

    /**
     * Método llamado cuando se crea la actividad.
     * Configura la vista de la actividad y maneja la lógica para el inicio de sesión.
     *
     * @param savedInstanceState El estado guardado de la actividad, si existe.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        // Cargar los usuarios desde el archivo de recursos
        usuarios = cargarUsuarios();

        // Obtener las vistas necesarias
        EditText usuarioInput = findViewById(R.id.idUserName);
        EditText contrasenaInput = findViewById(R.id.idUserClave);
        Button iniciarSesionButton = findViewById(R.id.idLog);

        // Configurar el botón para iniciar sesión
        iniciarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos ingresados por el usuario
                String usuarioIngresado = usuarioInput.getText().toString().trim();
                String contrasenaIngresada = contrasenaInput.getText().toString().trim();

                // Verificar si los campos están vacíos
                if (usuarioIngresado.isEmpty() || contrasenaIngresada.isEmpty()) {
                    Toast.makeText(IniciarSesionActivity.this, "Usuario o contraseña no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar si las credenciales ingresadas son correctas
                boolean accesoExitoso = false;
                for (Usuario usuario : usuarios) {
                    if (usuario.getUsuario().equals(usuarioIngresado) && usuario.getContrasena().equals(contrasenaIngresada)) {
                        accesoExitoso = true;
                        break;
                    }
                }

                // Si las credenciales no son válidas, lanzar una excepción personalizada
                if (!accesoExitoso) {
                    try {
                        throw new CredencialesInvalidasException();
                    } catch (CredencialesInvalidasException e) {
                        // Mostrar un mensaje de error si las credenciales son incorrectas
                        Toast.makeText(IniciarSesionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Si el inicio de sesión es exitoso, abrir la siguiente actividad
                    Toast.makeText(IniciarSesionActivity.this, "Ingreso exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(IniciarSesionActivity.this, PeliculaDisponibleActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Carga los usuarios desde un archivo de recursos en formato CSV.
     *
     * @return Una lista de usuarios cargados desde el archivo.
     */
    private List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            // Abrir el archivo de recursos y leerlo línea por línea
            InputStream inputStream = getResources().openRawResource(R.raw.usuarios);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String linea;
            while ((linea = reader.readLine()) != null) {
                // Separar la línea en partes usando coma como delimitador
                String[] partes = linea.split(",");
                if (partes.length == 5) {
                    try {
                        // Parsear los datos del usuario y agregarlo a la lista
                        int puesto = Integer.parseInt(partes[0]);
                        String nombre = partes[1];
                        String apellido = partes[2];
                        String usuario = partes[3];
                        String contrasena = partes[4];

                        usuarios.add(new Usuario(puesto, nombre, apellido, usuario, contrasena));
                    } catch (NumberFormatException e) {
                        // Manejar el error en caso de que el formato de los datos sea incorrecto
                        e.printStackTrace();
                    }
                }
            }

            // Cerrar el lector después de procesar el archivo
            reader.close();
        } catch (IOException e) {
            // Manejar posibles errores al leer el archivo
            e.printStackTrace();
        }

        // Retornar la lista de usuarios cargados
        return usuarios;
    }
}