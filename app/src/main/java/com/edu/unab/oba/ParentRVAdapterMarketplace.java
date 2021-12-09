package com.edu.unab.oba;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import model.Brand;
import model.Category;
import model.Product;

public class ParentRVAdapterMarketplace extends RecyclerView.Adapter<ParentRVAdapterMarketplace.ParentViewHolder> implements Filterable {

    Context context;
    ArrayList<Category> categories = new ArrayList<>();
    ArrayList<Category> allCategories = new ArrayList<>();

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

    @Override
    public Filter getFilter() {
        return filter;
    }


    /// Enabling search filtering for products

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList <Category> filterCategories = new ArrayList<>();

            if(constraint.toString().isEmpty())
                filterCategories.addAll(allCategories);
            else {
                for(Category category: allCategories) {
                    ArrayList<Brand> filterBrands = new ArrayList<>();
                    Boolean addCategory = false;
                    for(Brand brand: category.getBrands()) {
                        Boolean addBrand = false;
                        ArrayList<Product> filterProducts = new ArrayList<>();
                        for (Product product : brand.getProducts()) {
                            if (product.getNombre().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                filterProducts.add(product);
                                addBrand = true;
                            }
                        }
                        if(addBrand){
                            Brand brandToAdd = new Brand();
                            brandToAdd.setBrand(brand.getBrand());
                            brandToAdd.setProducts(filterProducts);
                            filterBrands.add(brandToAdd);
                            addCategory= true;
                        }
                    }
                    if(addCategory){
                        Category categoryToAdd = new Category();
                        categoryToAdd.setCategory(category.getCategory());
                        categoryToAdd.setImgCategory(category.getImgCategory());
                        categoryToAdd.setBrands(filterBrands);
                        filterCategories.add(categoryToAdd);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterCategories;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            categories.clear();
            categories.addAll((ArrayList<Category>) results.values);
            notifyDataSetChanged();
        }
    };

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
        allCategories = new ArrayList<>(categories);
        notifyDataSetChanged();
    }

}
