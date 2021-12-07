package com.edu.unab.oba.vistas;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.unab.oba.R;
import com.edu.unab.oba.controladores.RecuperarContraControlador;
import com.edu.unab.oba.utiles.ValidarCorreo;

public class RecuperarContraActivity extends AppCompatActivity {

    private EditText et_correo;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contra);

        et_correo= findViewById(R.id.et_correo);
        btnEnviar= findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(habilitar()){
                    RecuperarContraControlador.recuperarContra(RecuperarContraActivity.this, getCorreo());
                }else {
                    Toast.makeText(RecuperarContraActivity.this, "Los campos no son correctos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean habilitar(){

        String correo= getCorreo().trim();

        if(ValidarCorreo.validar(correo)){
            return true;
        }else {
            return false;
        }

    }

    private String getCorreo() {
        return et_correo.getText().toString();
    }
}