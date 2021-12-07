package com.edu.unab.oba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModuloAdministrador extends AppCompatActivity {


    private Button btnPersonas, btnProductos, btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulo_administrador);

        btnPersonas = findViewById(R.id.btnPersonas);
        btn1= findViewById(R.id.btnPersonasRegistrar);
        btnProductos = findViewById(R.id.btnProductos);

        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intentProductos = new Intent(MainActivity.this, RegistrarProductoActivity.class);
                //startActivity(intentProductos);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intentPersonas = new Intent(MainActivity.this, RegistrarPersonaActivity.class);
                //startActivity(intentPersonas);
            }
        });

        /*btnPersonas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPersonas = new Intent(ModuloAdministrador.this, ModuloPersonas.class);
                startActivity(intentPersonas);
            }
        });*/
        btnPersonas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPersonas = new Intent(ModuloAdministrador.this, ListarPersonasActivity.class);
                startActivity(intentPersonas);
            }
        });

    }
}