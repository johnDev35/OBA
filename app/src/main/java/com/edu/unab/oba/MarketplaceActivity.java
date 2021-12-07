package com.edu.unab.oba;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import model.Cart;
import model.Product;

public class MarketplaceActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton btnShowMore, btnAddToCart, btnSetBudget, btnScanCode;
    FragmentManager fragmentManager;
    Boolean showMoreIsEnabled;
    ArrayList<Cart> cartProducts = new ArrayList<>();
    int totalPrice = 0, numItems = 0;
    TextView txtCartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);

        // Regresar al inicio
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Número de elementos en el carro de compras
        numItems = 0;

        txtCartItems = findViewById(R.id.cart_badge);
        updateBadge(txtCartItems);

        // BOTONES FLOTANTES
        btnShowMore = findViewById(R.id.btnShowMore);
        btnAddToCart = findViewById(R.id.btnCheckout);
        btnSetBudget = findViewById(R.id.btnSetBudget);
        btnScanCode = findViewById(R.id.btnScanCode);
        btnAddToCart.setVisibility(View.GONE);
        btnSetBudget.setVisibility(View.GONE);
        btnScanCode.setVisibility(View.GONE);

        // Variable para mostrar/ocultar botones flotantes
        showMoreIsEnabled = false;
        btnShowMore.setOnClickListener(this);

        btnSetBudget.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);
        btnScanCode.setOnClickListener(this);

        fragmentManager = getSupportFragmentManager();
    }

    // MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_marketplace, menu);
        String tag = "products";

        MenuItem mnuSearch = menu.findItem(R.id.mnuSearch);
        SearchView searchView = (SearchView) mnuSearch.getActionView();
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Fragment fragment = fragmentManager.findFragmentByTag(tag);
                ((FragmentProducts) fragment).setFilterText(newText);
                return false;
            }
        });

/*
        // Adicionar badge de conteo para productos en el menú para el carro de compras
        final MenuItem itmCart;
        itmCart = menu.findItem(R.id.checkout);
        View actionView = itmCart.getActionView();
        txtCartItemsMenu = actionView.findViewById(R.id.cart_badge_menu);
        updateBadge(txtCartItemsMenu);
*/
        return true;
    }


    private void changeButtonVisibility() {
        if (!showMoreIsEnabled) {
            btnAddToCart.setVisibility(View.VISIBLE);
            btnSetBudget.setVisibility(View.VISIBLE);
            btnScanCode.setVisibility(View.VISIBLE);
        } else {
            btnAddToCart.setVisibility(View.GONE);
            btnSetBudget.setVisibility(View.GONE);
            btnScanCode.setVisibility(View.GONE);
        }
        showMoreIsEnabled = !showMoreIsEnabled;
    }

    @Override
    public void onClick(View v) {

        Fragment fragment = null;
        String tag = "";
        switch (v.getId()) {
            case R.id.btnShowMore:
                changeButtonVisibility();
                break;
            case R.id.btnSetBudget:
                tag = "budget";
                fragment = fragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragmentManager.beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .replace(R.id.fragmentContainerViewMarketPlace,
                                    FragmentBudget.class, null,
                                    tag)
                            .setReorderingAllowed(true)
                            .addToBackStack(tag)
                            .commit();
                } else fragmentManager.popBackStack(tag, 0);
                changeButtonVisibility();
                break;
            case R.id.btnCheckout:
                tag = "checkout";
                fragment = fragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    Bundle bundleCheckout = new Bundle();
                    bundleCheckout.putParcelableArrayList(tag, cartProducts);
                    FragmentCart fragmentCart = new FragmentCart();
                    fragmentCart.setArguments(bundleCheckout);

                    FragmentManager fragmentManagerCheckout = getSupportFragmentManager();
                    fragmentManagerCheckout.beginTransaction()
                            .replace(R.id.fragmentContainerViewMarketPlace, fragmentCart, tag)
                            .setReorderingAllowed(true)
                            .addToBackStack(tag)
                            .commit();
                } else
                    fragmentManager.popBackStack(tag, 0);

                changeButtonVisibility();
                break;
            case R.id.btnScanCode:
                tag = "scanner";
                fragment = fragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerViewMarketPlace, FragmentScanBarCode.class, null, tag)
                            .setReorderingAllowed(true)
                            .addToBackStack(tag)
                            .commit();
                } else
                    fragmentManager.popBackStack(tag, 0);

                changeButtonVisibility();

            default:
                break;
        }
    }

    private void updateBadge(TextView txtUpdateBadge) {
        if (txtUpdateBadge != null) {
            if (numItems == 0) {
                if (txtUpdateBadge.getVisibility() != View.GONE) {
                    txtUpdateBadge.setVisibility(View.GONE);
                }
            } else {
                txtUpdateBadge.setText(String.valueOf(Math.min(numItems, 999)));
                if (txtUpdateBadge.getVisibility() != View.VISIBLE) {
                    txtUpdateBadge.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    public void addToCart(Product producto) {

        //Buscar si el producto está en el carro de compras si está suma uno a la cantidad
        // de lo contrario lo añade a la lista
        boolean inCart = false;
        int priceItem = 0;
        for (Cart cartProduct : cartProducts) {
            if (cartProduct.getProducto().getNombre().equals(producto.getNombre())) {
                cartProduct.setCantidad(cartProduct.getCantidad() + 1);
                priceItem = cartProduct.getProducto().getPrecio() * cartProduct.getCantidad();
                cartProduct.setValorItem(priceItem);
                inCart = true;
                break;
            }
        }

        if (!inCart) {
            priceItem = producto.getPrecio();
            Cart newProduct = new Cart(producto, 1, priceItem);
            cartProducts.add(newProduct);
        }
        updatePrice(totalPrice + priceItem);
        updateQuantity(numItems + 1);
    }

    public void updateCart(ArrayList<Cart> updatedCartProducts) {
        this.cartProducts = updatedCartProducts;
    }

    public void updateQuantity(int quantity) {
        numItems = quantity;
        updateBadge(txtCartItems);
        // updateBadge(txtCartItemsMenu);
    }

    public void updatePrice(int price) {
        totalPrice = price;
    }

    public int getQuantity() {
        return numItems;
    }

    public int getTotalPrice() {
        return totalPrice;
    }


}