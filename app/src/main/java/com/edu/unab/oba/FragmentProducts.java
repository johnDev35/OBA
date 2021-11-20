package com.edu.unab.oba;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import model.Brand;
import model.Cart;
import model.Category;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProducts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProducts extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentProducts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProducts.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProducts newInstance(String param1, String param2) {
        FragmentProducts fragment = new FragmentProducts();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Spinner spnCategory;
    ArrayList<Brand> brands = new ArrayList<>();
    ArrayList<Category> categories = new ArrayList<>();
    ArrayList <Cart> cartProducts = new ArrayList<>();

    RecyclerView parentRVMarketplace;
    RecyclerView.LayoutManager parentLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products, container, false);
        // Inflate the layout for this fragment

        //TODO Crear marcas de productos

        brands.add(new Brand("Colombina"));
        brands.add(new Brand("Bianchi"));
        brands.add(new Brand("Fruticas"));
        brands.add(new Brand("Barrilete"));

        // RECYCLER VIEW
        parentRVMarketplace = view.findViewById(R.id.parentRVProduct);
        // Adaptador RV
        ParentRVAdapterMarketplace parentRVAdapterMarketplace = new ParentRVAdapterMarketplace(getContext());
        parentRVAdapterMarketplace.setBrands(brands);

        // Layout RV
        parentLayoutManager = new LinearLayoutManager(getContext());
        parentRVMarketplace.setAdapter(parentRVAdapterMarketplace);
        parentRVMarketplace.setLayoutManager(parentLayoutManager);


        // SPINNER
        spnCategory = view.findViewById(R.id.spnCategory);

        //TODO lista de categorías de productos

        categories.add(new Category("Caramelos",R.drawable.category_candy));
        categories.add(new Category("Barras de cereal", R.drawable.category_candy));
        categories.add(new Category("Chocolates", R.drawable.category_candy));
        categories.add(new Category("Dulces duros", R.drawable.category_candy));
        categories.add(new Category("Bombones", R.drawable.category_lollipop));

        SpinnerAdapterMarketplace spinnerAdapterMarketplace;
        spinnerAdapterMarketplace = new SpinnerAdapterMarketplace(getContext(), categories);
        spnCategory.setAdapter(spinnerAdapterMarketplace);

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), selectedCategory.getCategory() + " selected" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}