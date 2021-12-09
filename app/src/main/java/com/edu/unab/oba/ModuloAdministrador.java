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
        btnProductos = findViewById(R.id.btnProductos);
        //btn1= findViewById(R.id.btnRegistrarPrueba);

        btnPersonas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPersonas = new Intent(ModuloAdministrador.this, ListarPersonasActivity.class);
                startActivity(intentPersonas);
            }
        });

        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProductos = new Intent(ModuloAdministrador.this, ListarProductosActivity.class);
                startActivity(intentProductos);
            }
        });

//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentPersonas = new Intent(ModuloAdministrador.this, RegistrarProductoActivity.class);
//                startActivity(intentPersonas);
//            }
//        });
    }
}