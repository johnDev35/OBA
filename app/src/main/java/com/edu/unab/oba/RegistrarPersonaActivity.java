package com.edu.unab.oba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrarPersonaActivity extends AppCompatActivity {

    private EditText nomUsu, nombre, apellido, celular, clave, email, direccion;
    private Button btnRegistrar;
    private RadioButton rb1, rb2, rb3;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_persona);
        nomUsu = findViewById(R.id.usuario);
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        celular = findViewById(R.id.celular);
        email = findViewById(R.id.email);
        direccion = findViewById(R.id.direccion);
        clave = findViewById(R.id.clave);

        btnRegistrar =findViewById(R.id.btnregistrarP);

        rb1 = findViewById(R.id.rbAdmin);
        rb2 = findViewById(R.id.rbEmpleado);
        rb3 = findViewById(R.id.rbCliente);



        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });
    }
    private void registrar() {
        Map<String, Object> persona = new HashMap<>();
        /*persona.put("usuario", nomUsu.getText().toString());*/
        persona.put("nombre", nombre.getText().toString());
        persona.put("apellido", apellido.getText().toString());
        persona.put("celular", celular.getText().toString());
        persona.put("email", email.getText().toString());
        persona.put("direccion", direccion.getText().toString());
        persona.put("clave", clave.getText().toString());
        if (rb1.isChecked()){
            persona.put("perfil",rb1.getText());
        } else if(rb2.isChecked()){
            persona.put("perfil",rb2.getText());
        } else{
            persona.put("perfil",rb3.getText());
        }
        firestore.collection("Persona").document(nomUsu.getText().toString()).set(persona); //Llave tabla
        Toast.makeText(this, "Registro Exitoso!", Toast.LENGTH_LONG).show();
        Intent intentPersonas = new Intent(RegistrarPersonaActivity.this, ListarPersonasActivity.class);
        startActivity(intentPersonas);
    }
}