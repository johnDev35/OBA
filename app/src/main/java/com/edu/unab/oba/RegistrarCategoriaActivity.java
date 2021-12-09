package com.edu.unab.oba;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistrarCategoriaActivity extends AppCompatActivity {

    EditText categoriaNva, marcaNva;
    Button btnRegistrar, btnImg;
    ImageView imgCate;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    private final int PICK_PHOTO  = 1;
    private Uri archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_categoria);

        categoriaNva = findViewById(R.id.cate1);
        marcaNva = findViewById(R.id.marc1);
        imgCate = findViewById(R.id.imgVCate);
        btnImg=findViewById(R.id.btngaleria1);
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFotoGaleria();
            }
        });
        btnRegistrar = findViewById(R.id.btnregistrarCate);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarCategoria();
            }
        });

    }

    public void registrarCategoria(){
        if (archivo!=null){
            StorageReference fotoRef =  mStorage.child("categories_icons").child(archivo.getLastPathSegment());
            fotoRef.putFile(archivo).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @NonNull
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw new Exception();
                    }
                    return fotoRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri descargaUrl = task.getResult();
                        Map<String, Object> catego = new HashMap<>();
                        Map<String, Object> marca = new HashMap<>();
                        catego.put("image_url", descargaUrl.toString());
                        firestore.collection("product_categories").document(categoriaNva.getText().toString()).set(catego);
                        firestore.collection("product_categories").document(categoriaNva.getText().toString()).collection("brands").document(marcaNva.getText().toString()).set(marca);
                        Toast.makeText(RegistrarCategoriaActivity.this, "Registro de Categoria Exitoso!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegistrarCategoriaActivity.this, ListarProductosActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }


    public void abrirFotoGaleria(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Seleccione una Imagen"), PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_PHOTO && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            archivo = data.getData();
            try {
                Bitmap bitmapImg = MediaStore.Images.Media.getBitmap(getContentResolver(), archivo);
                imgCate.setImageBitmap(bitmapImg);
                Toast.makeText(RegistrarCategoriaActivity.this, "Imagen Seleccionada!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}