package com.edu.unab.oba;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import model.Brand;
import model.Category;

public class FragmentProducts extends Fragment {

    FirebaseFirestore dbCategories, dbBrands, dbProducts;

    ArrayList<Brand> brands = new ArrayList<>();
    ArrayList<Category> categories = new ArrayList<>();

    Spinner spnCategory;
    RecyclerView parentRVMarketplace;
    RecyclerView.LayoutManager parentLayoutManager;
    // Adapters
    SpinnerAdapterMarketplace spinnerAdapterMarketplace;
    ParentRVAdapterMarketplace parentRVAdapterMarketplace;

    public FragmentProducts() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        // Spinner
        spnCategory = view.findViewById(R.id.spnCategory);
        spinnerAdapterMarketplace = new SpinnerAdapterMarketplace(getContext(), categories);
        spnCategory.setAdapter(spinnerAdapterMarketplace);

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                parentRVAdapterMarketplace.setCategory(selectedCategory.getCategory());
                brands.clear();
                loadBrandsForCategory(selectedCategory.getCategory());
                Toast.makeText(getContext(), selectedCategory.getCategory() + " selected " , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // RECYCLER VIEW
        parentRVMarketplace = view.findViewById(R.id.parentRVProduct);
        // Adapter RV
        parentRVAdapterMarketplace = new ParentRVAdapterMarketplace(getContext());
        parentRVAdapterMarketplace.setBrands(brands);

        // Layout RV
        parentLayoutManager = new LinearLayoutManager(getContext());
        parentRVMarketplace.setAdapter(parentRVAdapterMarketplace);
        parentRVMarketplace.setLayoutManager(parentLayoutManager);

        loadCategories();

        return view;
    }

    private void loadCategories(){

        dbCategories = FirebaseFirestore.getInstance();
        FirebaseStorage dbImages = FirebaseStorage.getInstance();

        dbCategories.collection("/product_categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Reference images in Storage
                                StorageReference storageRef = dbImages.getReferenceFromUrl(document.getData().get("image_url").toString());
                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        //add element to list
                                        categories.add(new Category(document.getId(), uri.toString()));
                                        //loadBrandsForCategory(document.getId());
                                        spinnerAdapterMarketplace.notifyDataSetChanged();
                                    }
                                });
                            }
                        } else {
                            Category emptyCategory = new Category("No hay categorías para cargar", "None");
                            categories.add(emptyCategory);
                        }
                    }
                });
    }


    private void loadBrandsForCategory(String category){
        dbBrands = FirebaseFirestore.getInstance();
        dbBrands.collection("/product_categories/" + category +"/brands")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Brand newBrand = new Brand(document.getId());
                        //newBrand.setProductos(addBrandProducts(category, document.getId()));
                        brands.add(newBrand);
                        parentRVAdapterMarketplace.notifyDataSetChanged();
                    }
                } else{
                    Toast.makeText(getContext(), category + " not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /// Review if need it -- commented on line 143
    private ArrayList<String> addBrandProducts(String category, String brand){
        ArrayList<String> products = new ArrayList<>();
        dbProducts = FirebaseFirestore.getInstance();
        dbProducts.collection("/product_categories/" + category + "/brands/" + brand + "/id_products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document: task.getResult()) {
                                products.add(document.getId());
                            }
                        }
                    }
                });
        return products;
    }

}




