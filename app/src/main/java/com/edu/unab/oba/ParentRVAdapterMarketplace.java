package com.edu.unab.oba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import model.Brand;
import model.Product;

public class ParentRVAdapterMarketplace extends RecyclerView.Adapter<ParentRVAdapterMarketplace.ParentViewHolder> {

    Context context;
    ArrayList <Brand> brands = new ArrayList<>();

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

        holder.txtBrand.setText(currentBrand.getBrand());
        ArrayList<Product> products = new ArrayList<>();


        // Llenar listas de Categorías
        //TODO  definir categorías
        // Por ahora se asume solo una categoría se debe separar por cada categoría de productos durante la conexión a la base de datos

        switch (brands.get(position).getBrand()) {
            case "Colombina":
                products.add(new Product("Colombina", "Bonbonbum Mistery", 2500, "bombón", "Unidad", "A52", "A256", "Caja x 24 Unidades", R.drawable.bonbonbun_bombones_mistery_x24));
                products.add(new Product("Colombina", "Bonbonbum surtido", 2500, "bombón", "Unidad", "A52", "A257", "Caja x 24 Unidades", R.drawable.bonbonbum_bombones));
                products.add(new Product("Colombina", "Bonbonbum surtido", 1800, "bombón", "Unidad", "A52", "A258", "Caja x 12 Unidades", R.drawable.bonbonbun_bombones_surtido_x12));
                products.add(new Product("Colombina", "Bonbonbum rojo fresa", 2500, "bombón", "Unidad", "A52", "A259", "Caja x 24 Unidades", R.drawable.bonbonbun_bombones_rojo_fresa_x12));
                break;
            case "Bianchi":
                products.add(new Product("Bianchi", "Chocolate blanco", 4500, "caramelo", "Unidad", "A50", "A260", "Bolsa x 100 Unidades", R.drawable.bianchi_caramelos_chocolate_blanco_x100));
                products.add(new Product("Bianchi", "Chocolate oscuro", 4500, "caramelo", "Unidad", "A50", "A261", "Bolsa x 100 Unidades", R.drawable.bianchi_caramelos_chocolate_x100));
                products.add(new Product("Bianchi", "Chocolate surtido", 4500, "caramelo", "Unidad", "A50", "A262", "Bolsa x 100 Unidades", R.drawable.bianchi_caramelos_chocolate_surtidos_x50));
                break;
            case "Barrilete":
                products.add(new Product("Barrilete", "Barrilete", 3500, "caramelo", "Unidad", "A38", "A263", "Bolsa x 50 Unidades", R.drawable.barrilete_caramelos_x50));
                break;
            case "Fruticas":
                products.add(new Product("Fruticas", "Fruticas crujimasticables", 3000, "gomas", "Unidad", "A16", "A264", "Bolsa x 100 Unidades", R.drawable.fruticas_dulce_duro_crujimasticables_x100));
                products.add(new Product("Fruticas", "Fruticas lovecandy", 3000, "gomas", "Unidad", "A16", "A265", "Bolsa x 100 Unidades", R.drawable.fruticas_dulce_duro_lovecandy_x100));
                products.add(new Product("Fruticas", "Fruticas maracuyá", 3000, "gomas", "Unidad", "A16", "A266", "Bolsa x 100 Unidades", R.drawable.fruticas_dulce_duro_maracuya_x100));
                products.add(new Product("Fruticas", "Fruticas surtido", 3000, "gomas", "Unidad", "A16", "A267", "Bolsa x 100 Unidades", R.drawable.fruticas_dulce_duro_surtido_x50));
                break;
        }
        ChildRVAdapterMarketplace childRVAdapterMarketplace =  new ChildRVAdapterMarketplace(holder.childRVMarketplace.getContext());
        childRVAdapterMarketplace.setProducts(products);
        holder.childRVMarketplace.setAdapter(childRVAdapterMarketplace);
        holder.childRVMarketplace.setLayoutManager(layoutManager);
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    public void setBrands(ArrayList<Brand> brands) {
        this.brands = brands;
        notifyDataSetChanged();
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
}
