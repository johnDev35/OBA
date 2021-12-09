package com.edu.unab.oba;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrarProductoActivity extends AppCompatActivity {


    Spinner spinMarca, spinCategoria;
    EditText codigo, nombre, precio, medida, formato, ubicacion;
    Button btnRegistrar, btnAbrirPicture, btnGaleria;
    ImageView imgPro;


    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    StorageReference mStorage = FirebaseStorage.getInstance().getReference();

    //private static final int GALLERY_INTENT=1;
    private final int PICK_PHOTO  = 1;
    private Uri archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_producto);

        codigo= findViewById(R.id.codigo);
        nombre = findViewById(R.id.nombre);
        precio =findViewById(R.id.precio);
        medida = findViewById(R.id.unimed);
        formato = findViewById(R.id.formato);
        ubicacion = findViewById(R.id.ubicacion);
        imgPro = findViewById(R.id.imageViewProducto);

        spinCategoria = (Spinner) findViewById(R.id.spcategoria);
        //String categoriaSeleccionada = spinCategoria.getSelectedItem().toString();
        spinCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                Log.d("ITEM SELECCIONADO - ","es.... "+item);
                //lista de Marcas
                //spinCategoria.setSelection(2);
                CollectionReference refMarca = firestore.collection("product_categories").document(item.toString()).collection("brands");
                List<String> listMarca = new ArrayList<>();
                ArrayAdapter<String> adapterMarca = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listMarca);
                adapterMarca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //spinMarca.setPrompt("Seleccionar una Marca x Categoría");
                spinMarca.setAdapter(adapterMarca);
                refMarca.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot id : task.getResult()) {
                                String marcaId = id.getId();
                                listMarca.add(marcaId);
                            }
                            adapterMarca.notifyDataSetChanged();
                        }
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinMarca = (Spinner) findViewById(R.id.spmarca);

        btnRegistrar = findViewById(R.id.btnregistrarPro);
        btnGaleria=findViewById(R.id.btngaleria);
//        btnAbrirPicture=findViewById(R.id.btnfoto);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarProducto();
            }
        });
        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFotoGaleria();
            }
        });
//        btnAbrirPicture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              Intent imagen= new Intent(Intent.ACTION_PICK);
//                imagen.setType("image/*");
//                startActivityForResult(imagen,GALLERY_INTENT);
//            }
//        });

        //lista de Categorias
        CollectionReference refCate = firestore.collection("product_categories");
        List<String> listCate = new ArrayList<>();
        ArrayAdapter<String> adapterCate = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listCate);
        adapterCate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategoria.setAdapter(adapterCate);
        refCate.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot id : task.getResult()) {
                        //String cateId = id.getString("image_url"); //Para un campo campo del doc de la collec
                        String cateId = id.getId();
                        listCate.add(cateId);
                    }
                    adapterCate.notifyDataSetChanged();
                }
            }
        });


    }


    public void registrarProducto(){
        if (archivo!=null){
            StorageReference fotoRef =  mStorage.child("products_images").child(archivo.getLastPathSegment());
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
                        Map<String, Object> producto = new HashMap<>();
                        Map<String, Object> catego = new HashMap<>();
                        Map<String, Object> marca = new HashMap<>();
                        Map<String, Object> idPro = new HashMap<>();
                        producto.put("formato", formato.getText().toString());
                        producto.put("nombre", nombre.getText().toString());
                        producto.put("precio", Integer.parseInt(precio.getText().toString()));
                        producto.put("ubicacion", ubicacion.getText().toString());
                        producto.put("und_medida", medida.getText().toString());
                        producto.put("categoria", spinCategoria.getSelectedItem().toString());
                        producto.put("marca", spinMarca.getSelectedItem().toString());
                        producto.put("url_imagen", descargaUrl.toString());
                        firestore.collection("marketplace_products").document(codigo.getText().toString()).set(producto);
                        firestore.collection("product_categories").document(spinCategoria.getSelectedItem().toString()).set(catego);
                        firestore.collection("product_categories").document(spinCategoria.getSelectedItem().toString()).collection("brands").document(spinMarca.getSelectedItem().toString()).set(marca);
                        firestore.collection("product_categories").document(spinCategoria.getSelectedItem().toString()).collection("brands").document(spinMarca.getSelectedItem().toString()).collection("id_products").document(codigo.getText().toString()).set(idPro);
                        Toast.makeText(RegistrarProductoActivity.this, "Registro de Producto Exitoso!", Toast.LENGTH_LONG).show();
                        Intent intentPersonas = new Intent(RegistrarProductoActivity.this, ListarProductosActivity.class);
                        startActivity(intentPersonas);
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
                imgPro.setImageBitmap(bitmapImg);
                Toast.makeText(RegistrarProductoActivity.this, "Imagen Seleccionada!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
//    @Override //PUSH imagen
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
//            Uri uri = data.getData();
//            StorageReference carpeta = mStorage.child("products_images").child(uri.getLastPathSegment()); //Reeferencia Carpeta
//            carpeta.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(RegistrarProductoActivity.this, "La Imagen ha sido Cargada!", Toast.LENGTH_LONG).show();
//                    Uri descargaURL = taskSnapshot.getUploadSessionUri();
//                    Toast.makeText(RegistrarProductoActivity.this, "Se cargo -> "+descargaURL, Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    }
}