package com.edu.unab.oba;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import model.Cart;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCart extends Fragment implements View.OnClickListener {

    RecyclerView rVCartList;
    RecyclerView.LayoutManager cartListLayoutManager;
    EditText edTxtNumProducts, edTxtPrice;
    FloatingActionButton btnBack, btnSaveCart;

    ArrayList <Cart> cartItems = new ArrayList<>();

    FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentCart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCart.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCart newInstance(String param1, String param2) {
        FragmentCart fragment = new FragmentCart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        bundle = getArguments();
        cartItems = bundle.getParcelableArrayList("checkout");
        mAuth = FirebaseAuth.getInstance();

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

        btnSaveCart.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        // Recycler View
        rVCartList = view.findViewById(R.id.RVCartList);
        // Instanciar el adaptador del recycler view
        RVAdapterCart rVAdapterCart = new RVAdapterCart(getContext());
        rVAdapterCart.setCartProducts(cartItems);

        rVCartList.setAdapter(rVAdapterCart);
        cartListLayoutManager = new LinearLayoutManager(getContext());
        rVCartList.setLayoutManager(cartListLayoutManager);

        //cartListLayoutManager
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
        boolean areThereProducts = false;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        }else{
            currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        }

        if(currentUser != null){
            CollectionReference collectionReference = dbClient.collection("Persona/" + currentUser.getUid() + "/Compras/");

            Map<String, String> resumenCompra = new HashMap<String, String>();

            resumenCompra.put("Total Compra", "150000");
            resumenCompra.put("Total productos", "20");
            for (Cart cart: cartItems){
                collectionReference.document(currentDateTime + "/Productos/" + cart.getProducto().getCodigo())
                        .set(cart.getProducto());
                areThereProducts = true;
            }

            if(areThereProducts)
                collectionReference.document(currentDateTime).set(resumenCompra);
                Toast.makeText(getContext(), "Los productos del carrito se guardaron exitosamente", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "No hay productos en el carrito", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getContext(), "Se requiere que esté activado para guardar la lista", Toast.LENGTH_SHORT).show();
        }


    }
}