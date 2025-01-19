package com.example.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    public datosIncompletosException() {
        super("Faltan datos en uno o más campos obligatorios.");
    }
    public datosIncompletosException(String message) {
        super(message);
    }
}
public class DetallesFunciones extends AppCompatActivity {
    private EditText idPropietario;
    private EditText idNumTarjeta;
    private EditText idCodCV;
    private Button btnPagar;
    private Button btnCancelartwo;
    private int idFuncion;
    private int idPago;
    private double totalPagar;

    private TextView tvPelicula, tvDuracion, tvFecha, tvSala, tvEntradas, tvTotalPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_funciones);

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
        btnCancelartwo= findViewById(R.id.btnCancelartwo);

        Intent intent = getIntent();
        String pelicula = intent.getStringExtra("Titulo");
        String duracion = intent.getStringExtra("Duracion");
        String fecha = intent.getStringExtra("Fecha");
        String sala = intent.getStringExtra("Sala");
        String horario = intent.getStringExtra("Horario");
        int entradas = Integer.parseInt(intent.getStringExtra("Entradas"));
        idFuncion = intent.getIntExtra("FuncionId", 0);
        idPago = generarIdPago();
        totalPagar = entradas * 5.00;

        tvPelicula.setText("Película: " + pelicula);
        tvDuracion.setText("Duración: " + duracion);
        tvFecha.setText("Fecha: " + fecha);
        tvSala.setText("Horario: " + horario);
        tvEntradas.setText("Entradas: " + entradas);
        tvTotalPagar.setText("Total a pagar: $" + totalPagar);

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    verificarCampos();
                    guardarPago(idPropietario.getText().toString(), idNumTarjeta.getText().toString(), entradas);
                    Toast.makeText(DetallesFunciones.this, "Pago realizado con éxito", Toast.LENGTH_LONG).show();
                    finish();
                } catch (datosIncompletosException e) {
                    Toast.makeText(DetallesFunciones.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }catch (IOException e) {
                    Toast.makeText(DetallesFunciones.this, "Error al guardar el pago", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancelartwo.setOnClickListener(v -> {
            Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show();
            finish();
        });


    }
    private int generarIdPago() {
        return (int) (System.currentTimeMillis() % 100000);
    }
    private void verificarCampos() throws datosIncompletosException {
        String Propietario = idPropietario.getText().toString().trim();
        String NumTarjeta = idNumTarjeta.getText().toString().trim();
        String CodCVV = idCodCV.getText().toString().trim();

        if (Propietario.isEmpty() || NumTarjeta.isEmpty() || CodCVV.isEmpty()) {
            throw new datosIncompletosException("Debes llenar todos los campos antes de continuar.");
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
        Toast.makeText(DetallesFunciones.this, "Pago guardado con éxito", Toast.LENGTH_SHORT).show();
    }

}
