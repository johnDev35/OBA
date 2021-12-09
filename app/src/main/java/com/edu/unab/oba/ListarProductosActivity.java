package com.edu.unab.oba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import model.Product;

public class ListarProductosActivity extends AppCompatActivity {

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    ProductoAdapter proAdapter;

    private Button btnCrear, btnCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_productos);
        recyclerView=findViewById(R.id.recyclerActivityProductos);
        //recyclerView.setItemAnimator(null); //Quitar error: "Invalid view holder adapter"
        recyclerView.getRecycledViewPool().clear();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query qry = firestore.collection("marketplace_products");
        FirestoreRecyclerOptions<Product> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Product>().setQuery(qry,Product.class).build();
        proAdapter=new ProductoAdapter(firestoreRecyclerOptions);
        proAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(proAdapter);

        btnCrear = findViewById(R.id.btnRegistrarProducto);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPersonas = new Intent(ListarProductosActivity.this, RegistrarProductoActivity.class);
                startActivity(intentPersonas);

            }
        });
        btnCategoria = findViewById(R.id.btnRegistrarCategoria);
        btnCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListarProductosActivity.this, RegistrarCategoriaActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        proAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        proAdapter.stopListening();
    }
}