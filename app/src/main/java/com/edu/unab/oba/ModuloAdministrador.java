package com.edu.unab.oba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModuloAdministrador extends AppCompatActivity {

    private Button btnPersonas, btnProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulo_administrador);

        btnPersonas = findViewById(R.id.btnPersonas);

        btnPersonas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdmin = new Intent(ModuloAdministrador.this, ListarPersonasActivity.class);
                startActivity(intentAdmin);
            }
        });
    }
}