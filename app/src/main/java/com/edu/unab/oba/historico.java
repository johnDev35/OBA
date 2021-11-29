package com.edu.unab.oba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class historico extends AppCompatActivity {

    private ImageView btnHistorico,btnTienda,btnCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        btnHistorico = findViewById(R.id.btnHistorico);
        btnTienda = findViewById(R.id.btnTienda);
        btnCarrito = findViewById(R.id.btnCarrito);

        btnTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(historico.this,MarketplaceActivity.class);
                startActivity(intent);
            }
        });
        btnHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(historico.this,historico.class);
                startActivity(intent);
            }
        });
    }
}