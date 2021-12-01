package com.edu.unab.oba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private EditText et_nombre_usuario,et_nombres,et_apellidos,ed_celular,et_contra,et_email,et_dirrecion;
    private Button btnregistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
    }
}