package com.example.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.model.Pago;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

class datosIncompletosException extends Exception {
    public datosIncompletosException(String message) {
        super(message);
    }
}

public class DetallesFunciones extends AppCompatActivity {

    private TextView tvPelicula, tvDuracion, tvFecha, tvSala, tvEntradas, tvTotalPagar;
    private EditText etNombre, etNumeroTarjeta, etCVV;
    private Button btnPagar, btnCancelar;
    private int idFuncion;
    private int idPago;
    private double totalPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_funciones);

        // Referencias a elementos del layout
        tvPelicula = findViewById(R.id.TvPelicula);
        tvDuracion = findViewById(R.id.TvDuracion);
        tvFecha = findViewById(R.id.TvFecha);
        tvSala = findViewById(R.id.TvSala);
        tvEntradas = findViewById(R.id.TvEntradas);
        tvTotalPagar = findViewById(R.id.TvTotalPagar);
        etNombre = findViewById(R.id.idPropietario);
        etNumeroTarjeta = findViewById(R.id.idNumTarjeta);
        etCVV = findViewById(R.id.idCodCV);
        btnPagar = findViewById(R.id.btnPagar);
        btnCancelar = findViewById(R.id.btnCancelartwo);

        // Obtener datos desde la actividad anterior
        Intent intent = getIntent();
        String pelicula = intent.getStringExtra("Titulo");
        String duracion = intent.getStringExtra("Duracion");
        String fecha = intent.getStringExtra("Fecha");
        String sala = intent.getStringExtra("Sala");
        String horario = intent.getStringExtra("Horario");
        int entradas = Integer.parseInt(intent.getStringExtra("Entradas"));
        idFuncion = intent.getIntExtra("FuncionId", 0);
        idPago = generarIdPago();

        // Calcular total
        totalPagar = entradas * 5.00;

        // Mostrar datos en la interfaz
        tvPelicula.setText("Película: " + pelicula);
        tvDuracion.setText("Duración: " + duracion);
        tvFecha.setText("Fecha: " + fecha);
        tvSala.setText("Horario: " + horario);
        tvEntradas.setText("Entradas: " + entradas);
        tvTotalPagar.setText("Total a pagar: $" + totalPagar);

        // Acción del botón "Pagar"
        btnPagar.setOnClickListener(v -> {
            try {
                validarCampos();
                guardarPago(etNombre.getText().toString(), etNumeroTarjeta.getText().toString(), entradas);
                Toast.makeText(this, "Pago realizado con éxito", Toast.LENGTH_LONG).show();
                finish();
            } catch (datosIncompletosException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "Error al guardar el pago", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelar.setOnClickListener(v -> {
            Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private int generarIdPago() {
        return (int) (System.currentTimeMillis() % 100000);
    }

    private void validarCampos() throws datosIncompletosException {
        String propietario = etNombre.getText().toString().trim();
        String numTarjeta = etNumeroTarjeta.getText().toString().trim();
        String codigoCVV = etCVV.getText().toString().trim();

        if (propietario.isEmpty()) {
            throw new datosIncompletosException("No está ingresado el nombre del propietario.");
        }
        if (numTarjeta.isEmpty()) {
            throw new datosIncompletosException("No ingresó el número de la tarjeta.");
        }
        if (codigoCVV.isEmpty()) {
            throw new datosIncompletosException("No ingresó el código CVV.");
        }
    }


    private void guardarPago(String nombre, String numeroTarjeta, int entradas) throws IOException {
        Pago pago = new Pago(idPago, idFuncion, nombre, numeroTarjeta, "Crédito/Débito", entradas, totalPagar);
        try (FileOutputStream fos = openFileOutput("pagos.txt", MODE_APPEND)) {
            fos.write((pago.toString() + "\n").getBytes());
        }
        try (FileOutputStream fosBin = openFileOutput("funcion" + idFuncion + ".bin", MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fosBin)) {
            oos.writeObject(pago);
        }
        Toast.makeText(this, "Pago guardado con éxito", Toast.LENGTH_SHORT).show();
    }
}
