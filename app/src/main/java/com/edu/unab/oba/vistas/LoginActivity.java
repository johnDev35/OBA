package com.edu.unab.oba.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.unab.oba.MainActivity;
import com.edu.unab.oba.R;
import com.edu.unab.oba.controladores.LoginControlador;
import com.edu.unab.oba.utiles.ValidarCorreo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ImageView iv_logo;
    private EditText et_correo,et_contraseña;
    private TextView tv_recuperarContra,tv_registrarse;
    private String correo,contraseña;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iv_logo= findViewById(R.id.iv_logo);
        et_correo = findViewById(R.id.et_correo);
        et_contraseña = findViewById(R.id.et_contraseña);
        tv_recuperarContra = findViewById(R.id.tv_recuperarContra);
        tv_registrarse = findViewById(R.id.tv_registrarse);
        btnLogin= findViewById(R.id.btnLogin);

        correo =et_correo.getText().toString();
        contraseña =et_contraseña.getText().toString();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(habilitar()){
                    LoginControlador.login(LoginActivity.this, getCorreo(), getContraseña());
                }else {
                    Toast.makeText(LoginActivity.this, "Correo y Contraseña no registrados", Toast.LENGTH_SHORT).show();
                }

            }
        });
        tv_recuperarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RecuperarContraActivity.class);
                startActivity(intent);
            }
        });
        tv_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistroUsuarioActivity.class));
            }
        });



    }

    private boolean habilitar(){

        String correo= getCorreo().trim();
        String contraseña= getContraseña().trim();

        if(ValidarCorreo.validar(correo) && contraseña.length()>5){
            return true;
        }else {
            return false;
        }
    }

    public String getCorreo() {
        return et_correo.getText().toString();
    }

    public String getContraseña() {
        return et_contraseña.getText().toString();
    }

    private void conectado() {

        FirebaseUser usuario= FirebaseAuth.getInstance().getCurrentUser();

        if (usuario!=null){
            startActivity(new Intent(this, MainActivity.class));
        }

    }

}