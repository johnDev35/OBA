package com.edu.unab.oba;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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
    Boolean showMoreIsEnabled;
    ArrayList<Cart> cartProducts = new ArrayList<>();

    int numItems;
    TextView txtCartItems, txtCartItemsMenu;

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
    }

    // MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_marketplace, menu);

        // Adicionar badge de conteo para productos en el menú para el carro de compras
        final MenuItem itmCart;
        itmCart = menu.findItem(R.id.checkout);
        View actionView = itmCart.getActionView();
        txtCartItemsMenu = (TextView) actionView.findViewById(R.id.cart_badge_menu);
        updateBadge(txtCartItemsMenu);

        return true;
    }

    private void changeButtonVisibility(){
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
        switch (v.getId()) {
            case R.id.btnShowMore:
                changeButtonVisibility();
                break;
            case R.id.btnSetBudget:
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerViewMarketPlace, FragmentBudget.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("products")
                        .commit();
                changeButtonVisibility();
                break;
            case R.id.btnCheckout:
                Bundle bundleCheckout = new Bundle();
                bundleCheckout.putParcelableArrayList("checkout", cartProducts);
                FragmentCart fragmentCart = new FragmentCart();
                fragmentCart.setArguments(bundleCheckout);

                FragmentManager fragmentManagerCheckout = getSupportFragmentManager();
                fragmentManagerCheckout.beginTransaction()
                        .replace(R.id.fragmentContainerViewMarketPlace, fragmentCart)
                        .setReorderingAllowed(true)
                        .addToBackStack("cart")
                        .commit();
                changeButtonVisibility();
                break;
            case R.id.btnScanCode:
                FragmentManager fragmentManagerScanCode = getSupportFragmentManager();
                fragmentManagerScanCode.beginTransaction()
                        .replace(R.id.fragmentContainerViewMarketPlace, FragmentScanBarCode.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("scanner")
                        .commit();
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
                txtUpdateBadge.setText(String.valueOf(Math.min(numItems, 99)));
                if (txtUpdateBadge.getVisibility() != View.VISIBLE) {
                    txtUpdateBadge.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    public void addToCart(Product producto) {
        numItems += 1;
        updateBadge(txtCartItems);
        updateBadge(txtCartItemsMenu);


        //Buscar si el producto está en el carro de compras si está suma uno a la cantidad
        // de lo contrario lo añade a la lista
        boolean inCart = false;
        for (Cart cartProduct : cartProducts) {
            if (cartProduct.getProducto().getNombre().equals(producto.getNombre())) {
                cartProduct.setCantidad(cartProduct.getCantidad() + 1);
                cartProduct.setValorItem(cartProduct.getProducto().getPrecio() * cartProduct.getCantidad());
                inCart = true;
                break;
            }
        }

        if (!inCart) {
            Cart newProduct = new Cart(producto, 1, producto.getPrecio());
            cartProducts.add(newProduct);
        }
    }

}