package com.edu.unab.oba;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import model.Product;

public class ProductoAdapter extends FirestoreRecyclerAdapter<Product, ProductoAdapter.ProductoVH>{

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public ProductoAdapter(@NonNull FirestoreRecyclerOptions<Product> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull ProductoVH holder, int position, @NonNull Product producto) {
        DocumentSnapshot productoId = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String codigo = productoId.getId();
        holder.lcodigo.setText(codigo);
        holder.lnompro.setText(producto.getNombre());
        holder.lprecio.setText(String.valueOf(producto.getPrecio()));
        holder.lmedid.setText(producto.getUndMedida());
        holder.lmarca.setText(producto.getMarca());
        holder.lcategoria.setText(producto.getCategoria());
        holder.lubicacion.setText(producto.getUbicacion());
        holder.lformato.setText(producto.getFormato());
        holder.progressBar.setVisibility(View.VISIBLE);
        Picasso.get()
                .setIndicatorsEnabled(true);
        Picasso.get()
                .load(producto.getImagen())
                .fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        Picasso.get()
                                .load(producto.getImagen())
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

        holder.btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //editar();

            }
        });

        holder.btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("marketplace_products").document(codigo.toString()).delete();
                firestore.collection("product_categories").document(producto.getCategoria().toString()).collection("brands").document(producto.getMarca().toString()).collection("id_products").document(codigo.toString()).delete();
                Log.d("MENSAJE: ","Producto Eliminado Exitosamente!");
            }
        });
    }

    @NonNull
    @Override
    public ProductoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_productos, parent, false);
        return new ProductoVH(vista);
    }

    public class ProductoVH extends RecyclerView.ViewHolder {
        private TextView lnompro, lmarca, lprecio, lcategoria, lmedid, lubicacion, lformato, lcodigo;
        private ImageButton btnBorrar, btnModificar;
        private ImageView imgViewProduct;
        ProgressBar progressBar;
        public ProductoVH(@NonNull View itemView) {
            super(itemView);
            lcodigo=itemView.findViewById(R.id.lblcodigo);
            lnompro=itemView.findViewById(R.id.lblNombreProducto);
            lprecio=itemView.findViewById(R.id.lblprecio);
            lformato=itemView.findViewById(R.id.lblformato);
            lubicacion=itemView.findViewById(R.id.lblubicacion);
            lcategoria=itemView.findViewById(R.id.lblcategoria);
            lmarca=itemView.findViewById(R.id.lblmarca);
            lmedid=itemView.findViewById(R.id.lblmedida);
            imgViewProduct=itemView.findViewById(R.id.imageViewProducto);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progressBar1);

            btnModificar = itemView.findViewById(R.id.btnModificarProducto);
            btnBorrar = itemView.findViewById(R.id.btnBorrarProducto);



        }
    }

    public void editar(){

    }

}
