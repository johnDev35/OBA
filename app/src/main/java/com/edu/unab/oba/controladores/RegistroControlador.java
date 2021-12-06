package com.edu.unab.oba.controladores;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.edu.unab.oba.MainActivity;
import com.edu.unab.oba.controladores.ConstantesFirebase;
import com.edu.unab.oba.modelos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;


public class RegistroControlador {

    public static void registrar(Context contexto, String nombres, String apellidos, String celular, String correo, String contra, String dirrecion) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo ,contra)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            guardarUsuario(contexto, nombres, apellidos, celular, correo, dirrecion);
                        }else {
                            Toast.makeText(contexto, "Error al intentar registrar usuario", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private static void guardarUsuario(Context contexto, String nombres, String apellidos, String celular, String correo, String dirrecion) {

        try {

            FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

            String id= firebaseUser.getUid();

            long tiempoRegistro= firebaseUser.getMetadata().getCreationTimestamp();

            Usuario usuario= new Usuario(id, nombres, apellidos, celular, correo, dirrecion, "0", tiempoRegistro);

            FirebaseFirestore.getInstance()
                    .collection(ConstantesFirebase.USUARIOS)
                    .document(id)
                    .set(usuario, SetOptions.merge())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Intent intent= new Intent(contexto, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                contexto.startActivity(intent);
                                Toast.makeText(contexto, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(contexto, "Error al intentar guardar datos del usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }catch (NullPointerException e){
            e.getCause();
        }
    }


}
