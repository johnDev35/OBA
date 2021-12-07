package com.edu.unab.oba;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
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
import model.Category;
import model.Product;

public class ParentRVAdapterMarketplace extends RecyclerView.Adapter<ParentRVAdapterMarketplace.ParentViewHolder> {

    Context context;
    ArrayList<Category> categories = new ArrayList<>();

    public ParentRVAdapterMarketplace(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
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
        int curPosition = 0;
        String categoryName = "", brandTitle="";
        Brand currentBrand = new Brand();

        outer: for (Category category : categories) {
            Log.d("Categoría", category.getCategory());
            for (Brand brand : category.getBrands()) {
                Log.d("Marca", brand.getBrand());
                if (curPosition == position) {
                    Log.d("Marca", brand.getBrand()+" posición" + position);
                    currentBrand = brand;
                    categoryName = category.getCategory();
                    break outer;
                }
                curPosition++;
            }
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        ChildRVAdapterMarketplace childRVAdapterMarketplace = new ChildRVAdapterMarketplace(holder.childRVMarketplace.getContext());

        holder.childRVMarketplace.setAdapter(childRVAdapterMarketplace);
        holder.childRVMarketplace.setLayoutManager(layoutManager);
        if(!currentBrand.getProducts().isEmpty())
            holder.txtBrand.setText(categoryName + ": " + currentBrand.getBrand());
        else
            holder.txtBrand.setText("No hay productos para la cetegoría");
        childRVAdapterMarketplace.setProducts(currentBrand.getProducts());
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        for (Category category : categories) {
            for(Brand brand: category.getBrands()){
                if(!brand.getBrand().isEmpty()){
                    itemCount ++;
                }
            }
        }
        return itemCount;
    }

    public class ParentViewHolder extends RecyclerView.ViewHolder {
        TextView txtBrand;
        RecyclerView childRVMarketplace;

        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBrand = itemView.findViewById(R.id.txtBrand);
            childRVMarketplace = itemView.findViewById(R.id.childRVMarketplace);
        }

    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

}
