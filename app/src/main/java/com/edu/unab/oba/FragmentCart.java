package com.edu.unab.oba;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import model.Cart;

public class FragmentCart extends Fragment implements View.OnClickListener, RVAdapterCart.RVChangeListener {

    RecyclerView rVCartList;
    RecyclerView.LayoutManager cartListLayoutManager;
    EditText edTxtNumProducts, edTxtPrice;
    FloatingActionButton btnBack, btnSaveCart;

    ArrayList <Cart> cartItems = new ArrayList<>();

    FirebaseAuth mAuth;

    int numItems, totalPrice;

    public FragmentCart() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        bundle = getArguments();
        cartItems = bundle.getParcelableArrayList("checkout");
        mAuth = FirebaseAuth.getInstance();
        numItems = ((MarketplaceActivity) getContext()).getQuantity();
        totalPrice = ((MarketplaceActivity) getContext()).getTotalPrice();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Asignar los elementos de la vista a clases
        edTxtNumProducts = view.findViewById(R.id.edTxtNumProducts);
        edTxtPrice = view.findViewById(R.id.edTxtPrice);
        btnBack = view.findViewById(R.id.btnBack);
        btnSaveCart = view.findViewById(R.id.btnSaveCart);

        edTxtPrice.setText("" + totalPrice);
        edTxtNumProducts.setText("" +  numItems);

        btnSaveCart.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        // Recycler View
        rVCartList = view.findViewById(R.id.RVCartList);
        // Instanciar el adaptador del recycler view
        RVAdapterCart rVAdapterCart = new RVAdapterCart(getContext(), this);
        rVAdapterCart.setCartProducts(cartItems);

        rVCartList.setAdapter(rVAdapterCart);
        cartListLayoutManager = new LinearLayoutManager(getContext());
        rVCartList.setLayoutManager(cartListLayoutManager);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSaveCart:
                saveCartToDatabase();
                break;
            case R.id.btnBack:
                getParentFragmentManager().popBackStack();
                break;
            default:
                break;
        }

    }

    private void saveCartToDatabase(){
        FirebaseFirestore dbClient = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String currentDateTime;
        final boolean[] areThereProducts = {false};

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        }else{
            currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        }

        if(currentUser != null){
            CollectionReference collectionReference = dbClient.collection("Persona/" + currentUser.getUid() + "/Compras/");
            List<Task> saveTasks = new ArrayList<>();
            Map<String, Integer> resumenCompra = new HashMap<String, Integer>();

            resumenCompra.put("Total Compra", totalPrice);
            resumenCompra.put("Total productos", numItems);
            for (Cart cart: cartItems) {
                saveTasks.add(collectionReference.document(currentDateTime + "/Productos/" + cart.getProducto().getCodigo())
                        .set(cart));
                areThereProducts[0] = true;
            }

            Tasks.whenAllSuccess(saveTasks).addOnCompleteListener(new OnCompleteListener<List<Object>>() {
                @Override
                public void onComplete(@NonNull Task<List<Object>> task) {
                    if(task.isSuccessful()){
                        if(areThereProducts[0]) {
                            collectionReference.document(currentDateTime).set(resumenCompra);
                            Toast.makeText(getContext(), "Los productos del carrito se guardaron exitosamente", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getContext(), "No hay productos en el carrito", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getContext(), "Error: " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(getContext(), "Se requiere que esté activado para guardar la lista", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void applyChanges(int numProducts, int priceProducts) {
        numItems = numProducts;
        totalPrice = priceProducts;
        edTxtNumProducts.setText("" + numItems);
        edTxtPrice.setText(""+totalPrice);

    }
}