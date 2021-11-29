package com.edu.unab.oba;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import model.Brand;
import model.Product;

public class ParentRVAdapterMarketplace extends RecyclerView.Adapter<ParentRVAdapterMarketplace.ParentViewHolder> {

    Context context;
    String category;
    ArrayList <Brand> brands = new ArrayList<>();

    FirebaseFirestore dbProducts;


    public ParentRVAdapterMarketplace(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.parent_rv_item_marketplace,
                parent,
                false
        );
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder holder, int position) {
        Brand currentBrand = brands.get(position);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        ChildRVAdapterMarketplace childRVAdapterMarketplace =  new ChildRVAdapterMarketplace(holder.childRVMarketplace.getContext());

        holder.childRVMarketplace.setAdapter(childRVAdapterMarketplace);
        holder.childRVMarketplace.setLayoutManager(layoutManager);

        loadProductsByBrand(holder, childRVAdapterMarketplace, currentBrand.getBrand());

    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    public void setBrands(ArrayList<Brand> brands) {
        this.brands = brands;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public class ParentViewHolder extends RecyclerView.ViewHolder{
        TextView txtBrand;
        RecyclerView childRVMarketplace;

        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBrand= itemView.findViewById(R.id.txtBrand);
            childRVMarketplace = itemView.findViewById(R.id.childRVMarketplace);
        }

    }

    private void loadProductsByBrand(ParentViewHolder holder, ChildRVAdapterMarketplace childRVAdapterMarketplace,String brand){
        dbProducts = FirebaseFirestore.getInstance();
        ArrayList<Product> products = new ArrayList<>();
        FirebaseStorage dbImages = FirebaseStorage.getInstance();

        childRVAdapterMarketplace.setProducts(products);

        dbProducts.collection("/marketplace_products")
                .whereEqualTo("categoria", category)
                .whereEqualTo("marca", brand)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            holder.txtBrand.setText(brand);
                            for(QueryDocumentSnapshot document: task.getResult()){
                                Product newProduct = new Product();
                                newProduct.setMarca(document.getData().get("marca").toString());
                                newProduct.setNombre(document.getData().get("nombre").toString());
                                newProduct.setPrecio(Integer.parseInt(document.getData().get("precio").toString()));
                                newProduct.setCategoria(document.getData().get("categoria").toString());
                                newProduct.setUnd_medida(document.getData().get("und_medida").toString());
                                newProduct.setUbicacion(document.getData().get("ubicacion").toString());
                                newProduct.setCodigo(document.getId());
                                newProduct.setFormato(document.getData().get("formato").toString());

                                String storageUrl = document.getData().get("url_imagen").toString();
                                StorageReference storageRef = dbImages.getReferenceFromUrl(storageUrl);
                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        newProduct.setUrl_imagen(uri.toString());
                                        products.add(newProduct);
                                        childRVAdapterMarketplace.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        else{
                            Toast.makeText(context.getApplicationContext(), "No documents found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
