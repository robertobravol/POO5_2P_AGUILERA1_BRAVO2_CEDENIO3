package com.example.cinema;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exception.DatosIncompletosException;
import com.example.model.Pago;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


/**
 * Activity que gestiona los detalles de una función de cine, incluyendo información sobre la película, entradas, pagos y el procesamiento del pago.
 */
public class DetalleFuncion extends AppCompatActivity {
    private EditText idPropietario;
    private EditText idNumTarjeta;
    private EditText idCodCV;
    private Button btnPagar;
    private Button btnCancelartwo;
    private int idFuncion;
    private int idPago;
    private double totalPagar;
    private TextView tvPelicula;
    private TextView tvDuracion;
    private TextView tvFecha;
    private TextView tvSala;
    private TextView tvEntradas;
    private TextView tvTotalPagar;
    private RadioGroup radioGroupTarjeta;
    private RadioButton idVisa;
    private RadioButton idMaster;
    private CheckBox idTerminos;

    /**
     * Método onCreate. Inicializa la actividad y configura los componentes de la interfaz.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_funciones);

        // Inicialización de los componentes de la interfaz
        tvPelicula = findViewById(R.id.TvPelicula);
        tvDuracion = findViewById(R.id.TvDuracion);
        tvFecha = findViewById(R.id.TvFecha);
        tvSala = findViewById(R.id.TvSala);
        tvEntradas = findViewById(R.id.TvEntradas);
        tvTotalPagar = findViewById(R.id.TvTotalPagar);
        idPropietario = findViewById(R.id.idPropietario);
        idNumTarjeta = findViewById(R.id.idNumTarjeta);
        idCodCV = findViewById(R.id.idCodCV);
        btnPagar = findViewById(R.id.btnPagar);
        btnCancelartwo = findViewById(R.id.btnCancelartwo);
        radioGroupTarjeta = findViewById(R.id.RadioGroupTarjeta);
        idVisa = findViewById(R.id.idVisa);
        idMaster = findViewById(R.id.idMaster);
        idTerminos = findViewById(R.id.idTerminos);

        // Obtención de datos del intent
        Intent intent = getIntent();
        String pelicula = intent.getStringExtra("Titulo");
        String duracion = intent.getStringExtra("Duracion");
        String fecha = intent.getStringExtra("Fecha");
        String sala = intent.getStringExtra("Sala");
        String horario = intent.getStringExtra("Horario");
        int entradas = Integer.parseInt(intent.getStringExtra("Entradas"));
        idFuncion = intent.getIntExtra("Funcion", 0);
        idPago = generarIdPago();
        totalPagar = entradas * 5.00;

        // Configuración de TextViews
        tvPelicula.setText("Película: " + pelicula);
        tvDuracion.setText("Duración: " + duracion);
        tvFecha.setText("Fecha: " + fecha);
        tvSala.setText("Horario: " + horario);
        tvEntradas.setText("Entradas: " + entradas);
        tvTotalPagar.setText("Total a pagar: $" + totalPagar);

        // Configuración de los listeners de los botones
        btnPagar.setOnClickListener(v -> {
            int selectedId = radioGroupTarjeta.getCheckedRadioButtonId();
            String seleccion = "";
            if (selectedId == R.id.idMaster) {
                seleccion = "Mastercard";
            } else if (selectedId == R.id.idVisa) {
                seleccion = "Visa";
            }

            try {
                verificarCampos();
                guardarPago(idPropietario.getText().toString(), idNumTarjeta.getText().toString(), seleccion, entradas);
                Toast.makeText(DetalleFuncion.this, "Pago realizado con éxito", Toast.LENGTH_LONG).show();
                finish();
            } catch (DatosIncompletosException e) {
                Toast.makeText(DetalleFuncion.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(DetalleFuncion.this, "Error al guardar el pago", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelartwo.setOnClickListener(v -> {
            Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    /**
     * Genera un identificador único para el pago utilizando preferencias compartidas.
     *
     * @return Nuevo identificador del pago.
     */
    private int generarIdPago() {
        SharedPreferences sharedPreferences = getSharedPreferences("PagoPrefs", MODE_PRIVATE);
        int ultimoIdPago = sharedPreferences.getInt("ultimoIdPago", 0);
        int nuevoIdPago = ultimoIdPago + 1;
        sharedPreferences.edit().putInt("ultimoIdPago", nuevoIdPago).apply();
        return nuevoIdPago;
    }

    /**
     * Verifica que todos los campos requeridos estén completos.
     *
     * @throws DatosIncompletosException Si algún campo está vacío.
     */
    private void verificarCampos() throws DatosIncompletosException {
        String Propietario = idPropietario.getText().toString().trim();
        String NumTarjeta = idNumTarjeta.getText().toString().trim();
        String CodCVV = idCodCV.getText().toString().trim();

        if (Propietario.isEmpty() || NumTarjeta.isEmpty() || CodCVV.isEmpty() || !idTerminos.isChecked()) {
            throw new DatosIncompletosException("Debes llenar todos los campos antes de continuar.");
        }
    }

    /**
     * Guarda los detalles del pago en un archivo de texto y en un archivo binario.
     *
     * @param nombre         Nombre del propietario de la tarjeta.
     * @param numeroTarjeta  Número de la tarjeta.
     * @param tipo           Tipo de tarjeta (Visa o Mastercard).
     * @param entradas       Cantidad de entradas adquiridas.
     * @throws IOException Si ocurre un error al escribir en los archivos.
     */
    private void guardarPago(String nombre, String numeroTarjeta, String tipo, int entradas) throws IOException {
        // Crear el objeto Pago
        Pago pago = new Pago(idPago, idFuncion, nombre, numeroTarjeta, tipo, entradas, totalPagar);

        // Obtener el directorio público en "Documents"
        File directorioDocumentos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

        // Asegurarse de que el directorio exista
        if (!directorioDocumentos.exists()) {
            directorioDocumentos.mkdirs();
        }

        // Crear el archivo pagos.txt en ese directorio
        File archivoPagos = new File(directorioDocumentos, "pagos.txt");

        // Escribir los datos en el archivo pagos.txt
        try (FileOutputStream fos = new FileOutputStream(archivoPagos, true)) { // 'true' para agregar contenido
            fos.write((pago.toString() + "\n").getBytes());
        }

        // Crear el archivo .bin en el directorio Documents
        File archivoBin = new File(directorioDocumentos, "funcion" + idFuncion + ".bin");
        // Guardar el objeto en el archivo .bin
        try (FileOutputStream fosBin = new FileOutputStream(archivoBin);
             ObjectOutputStream oos = new ObjectOutputStream(fosBin)) {
            oos.writeObject(pago);
        }
        Toast.makeText(DetalleFuncion.this, "Pago guardado con éxito", Toast.LENGTH_SHORT).show();
    }
}
