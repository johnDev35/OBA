package com.edu.unab.oba;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import model.Cart;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCart extends Fragment implements View.OnClickListener {

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
        cartItems =bundle.getParcelableArrayList("checkout");
        int i =0;

    }

    RecyclerView rVCartList;
    RecyclerView.LayoutManager cartListLayoutManager;
    EditText edTxtNumProducts, edTxtPrice;
    Button btnOkEditCart, btnCancelEditCart;

    ArrayList <Cart> cartItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Asignar los elementos de la vista a clases
        edTxtNumProducts = view.findViewById(R.id.edTxtNumProducts);
        edTxtPrice = view.findViewById(R.id.edTxtPrice);
        btnOkEditCart = view.findViewById(R.id.btnOkEditCart);
        btnCancelEditCart = view.findViewById(R.id.btnCancelEditCart);

        btnOkEditCart.setOnClickListener(this);
        btnCancelEditCart.setOnClickListener(this);

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
            case R.id.btnOkEditCart:
                break;
            case R.id.btnCancelEditCart:
                break;
            default:
                break;
        }
        getParentFragmentManager().popBackStack();
    }
}