package com.edu.unab.oba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import model.Brand;
import model.Category;

public class MarketplaceActivity extends AppCompatActivity {
    FloatingActionButton btnShowMore, btnAddToCart, btnSetBudget, btnScanCode;
    Boolean showMoreIsEnabled;
    Spinner spnCategory;
    ArrayList <Brand> brands = new ArrayList<>();
    ArrayList<Category> categories = new ArrayList<>();

    RecyclerView parentRVMarketplace;
    RecyclerView.LayoutManager parentLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);

        // Regresar al inicio

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TODO Crear marcas de productos

        brands.add(new Brand("Colombina"));
        brands.add(new Brand("Bianchi"));
        brands.add(new Brand("Fruticas"));
        brands.add(new Brand("Barrilete"));

        // Recycler View
        parentRVMarketplace = findViewById(R.id.parentRVProduct);
        // Adaptador
        ParentRVAdapterMarketplace parentRVAdapterMarketplace = new ParentRVAdapterMarketplace(this);
        parentRVAdapterMarketplace.setBrands(brands);

        // Layout
        parentLayoutManager = new LinearLayoutManager(this);
        parentRVMarketplace.setAdapter(parentRVAdapterMarketplace);
        parentRVMarketplace.setLayoutManager(parentLayoutManager);

        // Spinner

        spnCategory = findViewById(R.id.spnCategory);


        //TODO lista de categorías de productos

        categories.add(new Category("Caramelos",R.drawable.category_candy));
        categories.add(new Category("Barras de cereal", R.drawable.category_candy));
        categories.add(new Category("Chocolates", R.drawable.category_candy));
        categories.add(new Category("Dulces duros", R.drawable.category_candy));
        categories.add(new Category("Bombones", R.drawable.category_lollipop));

        SpinnerAdapterMarketplace spinnerAdapterMarketplace;
        spinnerAdapterMarketplace = new SpinnerAdapterMarketplace(this, categories);
        spnCategory.setAdapter(spinnerAdapterMarketplace);

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                Toast.makeText(MarketplaceActivity.this, selectedCategory.getCategory() + " selected" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // Botones flotantes
        btnShowMore = findViewById(R.id.btnShowMore);
        btnAddToCart = findViewById(R.id.btnCheckout);
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