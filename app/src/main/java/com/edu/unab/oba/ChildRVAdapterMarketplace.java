package com.edu.unab.oba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.Product;

public class ChildRVAdapterMarketplace extends RecyclerView.Adapter<ChildRVAdapterMarketplace.ChildViewHolder> {


    ArrayList <Product> products = new ArrayList<>();
    Context context;

    public ChildRVAdapterMarketplace( Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
          R.layout.child_rv_item_marketplace,
          parent,
          false
        );
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        Product currentItem = products.get(position);
        holder.txtProductName.setText(currentItem.getNombre());
        holder.txtPrice.setText("$"+currentItem.getPrecio());
        holder.txtFormat.setText(currentItem.getFormato());
        holder.txtLocation.setText(currentItem.getUbicacion());
        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof MarketplaceActivity) {
                    ((MarketplaceActivity) context).addToCart(currentItem);
                }
                Toast.makeText(context,  currentItem.getMarca() + " " + currentItem.getNombre() + " seleccionado", Toast.LENGTH_SHORT).show();
            }
        });


        holder.progressBar.setVisibility(View.VISIBLE);
        Picasso.get()
                .setIndicatorsEnabled(true);

        Picasso.get()
                .load(currentItem.getImagen())
                .fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        Picasso.get()
                                .load(currentItem.getImagen())
                                .placeholder(R.drawable.no_products)
                                .into(holder.imgViewProduct, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                        if (holder.progressBar != null) {
                                            holder.progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                    @Override
                                    public void onError(Exception e) {

                                    }
                                });
                    }
                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(R.drawable.no_products)
                                .into(holder.imgViewProduct);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder{
        TextView txtProductName, txtPrice, txtFormat, txtLocation;
        ImageView imgViewProduct;
        FloatingActionButton btnAddToCart;
        ProgressBar progressBar;
        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtFormat = itemView.findViewById(R.id.txtFormat);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            imgViewProduct = itemView.findViewById(R.id.imgViewProduct);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
