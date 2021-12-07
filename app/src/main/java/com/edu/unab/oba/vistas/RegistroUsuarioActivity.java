package com.edu.unab.oba.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.unab.oba.R;
import com.edu.unab.oba.controladores.RegistroControlador;
import com.edu.unab.oba.utiles.ValidarCorreo;

public class RegistroUsuarioActivity extends AppCompatActivity {

    private EditText et_nombres,et_apellidos,et_celular,et_correo,et_contra,et_contraConfirmar,et_dirrecion;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        et_nombres= findViewById(R.id.et_nombres);
        et_apellidos= findViewById(R.id.et_apellidos);
        et_celular= findViewById(R.id.et_celular);
        et_correo= findViewById(R.id.et_correo);
        et_contra= findViewById(R.id.et_contra);
        et_contraConfirmar= findViewById(R.id.et_contraConfirmar);
        et_dirrecion= findViewById(R.id.et_dirrecion);
        btnRegistrar= findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(habilitar()){
                    RegistroControlador.registrar(RegistroUsuarioActivity.this,
                                        getNombres(),
                                        getApellidos(),
                                        getCelular(),
                                        getCorreo(),
                                        getContra(),
                                        getDirrecion());
                }else {
                    Toast.makeText(RegistroUsuarioActivity.this, "hay campos incorrectos o campos vacios", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean habilitar(){

        String nombres= getNombres().trim();
        String apellidos= getNombres().trim();
        String correo= getCorreo().trim();
        String contraseña= getContra().trim();
        String confirmarContra= getContraConfirmar().trim();
        String celular= getCelular().trim();
        String dirrecion= getDirrecion().trim();

        if(nombres.length()>2 && apellidos.length()>2 && ValidarCorreo.validar(correo) && contraseña.length()>4 && confirmarContra.equals(contraseña) && celular.length()>9 && dirrecion.length()>9){
            return true;
        }else {
            return false;
        }

    }

    public String getNombres() {
        return et_nombres.getText().toString();
    }

    public String getApellidos() {
        return et_apellidos.getText().toString();
    }

    public String getCelular() {
        return et_celular.getText().toString();
    }

    public String getCorreo() {
        return et_correo.getText().toString();
    }

    public String getContra() {
        return et_contra.getText().toString();
    }

    public String getContraConfirmar() {
        return et_contraConfirmar.getText().toString();
    }

    public String getDirrecion() {
        return et_dirrecion.getText().toString();
    }
}