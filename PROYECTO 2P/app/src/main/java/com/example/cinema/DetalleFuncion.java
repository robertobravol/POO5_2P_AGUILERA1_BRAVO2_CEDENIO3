package com.example.cinema;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exception.DatosIncompletosException;
import com.example.model.Pago;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


/**
 * Clase DetalleFuncion
 *
 * Representa la actividad que gestiona los detalles de una función de cine, incluyendo
 * información sobre la película, entradas, pagos y el procesamiento del pago.
 * Extiende {@link AppCompatActivity}.
 */
public class DetalleFuncion extends AppCompatActivity {

    /** Campo para el nombre del propietario de la tarjeta */
    private EditText idPropietario;

    /** Campo para el número de la tarjeta */
    private EditText idNumTarjeta;

    /** Campo para el código CVV de la tarjeta */
    private EditText idCodCV;

    /** Botón para confirmar el pago */
    private Button btnPagar;

    /** Botón para cancelar la operación */
    private Button btnCancelartwo;

    /** Identificador único de la función */
    private int idFuncion;

    /** Identificador único del pago */
    private int idPago;

    /** Total a pagar por las entradas */
    private double totalPagar;

    /** TextView para mostrar el título de la película */
    private TextView tvPelicula;

    /** TextView para mostrar la duración de la película */
    private TextView tvDuracion;

    /** TextView para mostrar la fecha de la función */
    private TextView tvFecha;

    /** TextView para mostrar la sala de la función */
    private TextView tvSala;

    /** TextView para mostrar la cantidad de entradas compradas */
    private TextView tvEntradas;

    /** TextView para mostrar el total a pagar */
    private TextView tvTotalPagar;

    /** Grupo de radio buttons para seleccionar el tipo de tarjeta */
    private RadioGroup radioGroupTarjeta;

    /** RadioButton para seleccionar Visa */
    private RadioButton idVisa;

    /** RadioButton para seleccionar Mastercard */
    private RadioButton idMaster;

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

        if (Propietario.isEmpty() || NumTarjeta.isEmpty() || CodCVV.isEmpty()) {
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
        Pago pago = new Pago(idPago, idFuncion, nombre, numeroTarjeta, tipo, entradas, totalPagar);
        try (FileOutputStream fos = openFileOutput("pagos.txt", MODE_APPEND)) {
            fos.write((pago.toString() + "\n").getBytes());
        }
        try (FileOutputStream fosBin = openFileOutput("funcion" + idFuncion + ".bin", MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fosBin)) {
            oos.writeObject(pago);
        }
        Toast.makeText(DetalleFuncion.this, "Pago guardado con éxito", Toast.LENGTH_SHORT).show();
    }
}
