package com.edu.unab.oba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnMarketplace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show();

        // Botón para ir al marketplace
        btnMarketplace = findViewById(R.id.btnMarketplace);
        btnMarketplace.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Clicked button", Toast.LENGTH_SHORT).show();
        switch (v.getId()){
            case R.id.btnMarketplace:
                Intent intMarketplace = new Intent(this, MarketplaceActivity.class);
                startActivity(intMarketplace);
                break;
            default:
                break;
        }
    }
}