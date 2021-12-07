package com.edu.unab.oba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.Cart;

public class RVAdapterCart extends RecyclerView.Adapter<RVAdapterCart.CartViewHolder> {
    ArrayList <Cart> cartProducts= new ArrayList<>();
    RVChangeListener rvChangeListener;

    Context context;
    int numProducts = 0, totalValue = 0;

    public RVAdapterCart(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.rv_item_cart,
                parent,
                false
        );
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart currentCartProduct = cartProducts.get(holder.getAbsoluteAdapterPosition());

        Picasso.get().load(currentCartProduct.getProducto().getImagen())
                .placeholder(R.drawable.no_products)
                .into(holder.imgViewProductInCart);

        holder.edTxtUnitPriceInCart.setText("" + currentCartProduct.getProducto().getPrecio());
        holder.edTxtQuantityInCart.setText("" + currentCartProduct.getCantidad());
        holder.edTxtTotalPrice.setText("" + currentCartProduct.getValorItem());
        holder.txtProductNameInCart.setText(currentCartProduct.getProducto().getNombre());

        holder.btnAddItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = currentCartProduct.getCantidad();
                int unitPrice = currentCartProduct.getProducto().getPrecio();

                numProducts +=1;
                totalValue += unitPrice;
                quantity ++;
                int price = unitPrice * quantity;

                currentCartProduct.setCantidad(quantity);
                currentCartProduct.setValorItem(price);

                updateMarketplace();
                notifyItemChanged(holder.getAbsoluteAdapterPosition());
            }
        });
        holder.btnRemoveItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = currentCartProduct.getCantidad();
                int unitPrice = currentCartProduct.getProducto().getPrecio();

                if(quantity>0){
                    numProducts --;
                    totalValue -= unitPrice;
                    quantity --;
                    int price = unitPrice * quantity;

                    currentCartProduct.setCantidad(quantity);
                    currentCartProduct.setValorItem(price);
                    updateMarketplace();
                    notifyItemChanged(holder.getAbsoluteAdapterPosition());
                }
            }
        });
        holder.btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = currentCartProduct.getCantidad();
                int price = currentCartProduct.getValorItem();
                numProducts -= quantity;
                totalValue -= price;
                cartProducts.remove(currentCartProduct);
                updateMarketplace();
                notifyItemRemoved(holder.getAbsoluteAdapterPosition());
            }
        });



    }

    private void updateMarketplace(){
        ((MarketplaceActivity) context).updateCart(cartProducts);
        ((MarketplaceActivity) context).updatePrice(totalValue);
        ((MarketplaceActivity) context).updateQuantity(numProducts);
    }


    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    public void setCartProducts(ArrayList<Cart> cartProducts) {
        this.cartProducts = cartProducts;
        numProducts = 0;
        totalValue = 0;
        for(Cart cart: cartProducts){
            numProducts += cart.getCantidad();
            totalValue += cart.getValorItem();
        }

        notifyDataSetChanged();
    }


    public class CartViewHolder extends RecyclerView.ViewHolder{
        TextView txtProductNameInCart;
        EditText edTxtUnitPriceInCart, edTxtQuantityInCart , edTxtTotalPrice;
        ImageView imgViewProductInCart;
        FloatingActionButton btnAddItems, btnRemoveItems, btnDeleteProduct;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductNameInCart = itemView.findViewById(R.id.txtProductNameInCart);
            edTxtUnitPriceInCart =itemView.findViewById(R.id.edTxtUnitPriceinCart);
            edTxtQuantityInCart = itemView.findViewById(R.id.edTxtQuantityInCart);
            edTxtTotalPrice = itemView.findViewById(R.id.edTxtTotalPrice);
            imgViewProductInCart = itemView.findViewById(R.id.imgViewProductInCart);
            btnAddItems = itemView.findViewById(R.id.btnAddItems);
            btnRemoveItems = itemView.findViewById(R.id.btnRemoveItems);
            btnDeleteProduct = itemView.findViewById(R.id.btnDeleteProduct);
        }
    }

    public interface RVChangeListener{
        void applyChanges();
    }
}
