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

import model.Persona;

public class ListarPersonasActivity extends AppCompatActivity {

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    PersonaAdapter perAdapter;

    private Button btnCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_personas);
        recyclerView=findViewById(R.id.recyclerActivityPersona);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query qry = firestore.collection("Persona");
        FirestoreRecyclerOptions<Persona> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Persona>().setQuery(qry,Persona.class).build();
        perAdapter=new PersonaAdapter(firestoreRecyclerOptions);
        perAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(perAdapter);

        btnCrear = findViewById(R.id.btnregistrar1);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPersonas = new Intent(ListarPersonasActivity.this, RegistrarPersonaActivity.class);
                startActivity(intentPersonas);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        perAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        perAdapter.stopListening();
    }
}