package com.edu.unab.oba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MarketplaceActivity extends AppCompatActivity {
    FloatingActionButton btnShowMore, btnAddToCart, btnSetBudget, btnScanCode;
    Boolean showMoreIsEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);

        // Botones flotantes
        btnShowMore = findViewById(R.id.btnShowMore);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnSetBudget = findViewById(R.id.btnSetBudget);
        btnScanCode = findViewById(R.id.btnScanCode);
        btnAddToCart.setVisibility(View.GONE);
        btnSetBudget.setVisibility(View.GONE);
        btnScanCode.setVisibility(View.GONE);

        showMoreIsEnabled = false;

        btnShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!showMoreIsEnabled){
                    btnAddToCart.setVisibility(View.VISIBLE);
                    btnSetBudget.setVisibility(View.VISIBLE);
                    btnScanCode.setVisibility(View.VISIBLE);
                } else{
                    btnAddToCart.setVisibility(View.GONE);
                    btnSetBudget.setVisibility(View.GONE);
                    btnScanCode.setVisibility(View.GONE);
                }
                showMoreIsEnabled = !showMoreIsEnabled;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_marketplace, menu);
        return true;
    }
}