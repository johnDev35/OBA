package com.edu.unab.oba;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import model.Brand;
import model.Category;
import model.Product;

public class FragmentProducts extends Fragment {

    FirebaseFirestore dbCategories, dbProducts;
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
                ArrayList<Category> currentCategory = new ArrayList<>();
                if(selectedCategory.getCategory().equals("Todos los productos")){
                    currentCategory = new ArrayList<>(categories);
                }else{
                    currentCategory = new ArrayList<>();
                    currentCategory.add(selectedCategory);
                }
                parentRVAdapterMarketplace.setCategories(currentCategory);

                Toast.makeText(getContext(), selectedCategory.getCategory() + " selected ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // RECYCLER VIEW
        parentRVMarketplace = view.findViewById(R.id.parentRVProduct);
        // Adapter RV
        parentRVAdapterMarketplace = new ParentRVAdapterMarketplace(getContext());

        // Layout RV
        parentLayoutManager = new LinearLayoutManager(getContext());
        parentRVMarketplace.setAdapter(parentRVAdapterMarketplace);
        parentRVMarketplace.setLayoutManager(parentLayoutManager);

        if(categories.isEmpty()) {
            loadAll();
        }
        return view;
    }

    private void loadAll() {
        dbCategories = FirebaseFirestore.getInstance();
        int numCores = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(numCores * 2, numCores *2,
                60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        Task<List<Task<QuerySnapshot>>> task1 = dbCategories.collection("product_categories")
                .get()
                .continueWithTask(new Continuation<QuerySnapshot, Task<List<QuerySnapshot>>>() {
                    @Override
                    public Task<List<QuerySnapshot>> then(@NonNull Task<QuerySnapshot> task) {
                        List<Task<QuerySnapshot>> tasks = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Category newCategory= new Category();
                                newCategory.setCategory(documentSnapshot.getId());
                                newCategory.setImgCategory(documentSnapshot.getString("image_url"));
                                categories.add(newCategory);
                                tasks.add(documentSnapshot.getReference().collection("brands").get());
                            }
                        }

                        return Tasks.whenAllSuccess(tasks);
                    }
                })
                .continueWithTask(new Continuation<List<QuerySnapshot>, Task<List<Task<QuerySnapshot>>>>() {
                    @NonNull
                    @Override
                    public Task<List<Task<QuerySnapshot>>> then(@NonNull Task<List<QuerySnapshot>> task) throws Exception {
                        List<Task<QuerySnapshot>> tasks = new ArrayList<>();
                        if (task.isSuccessful()) {
                            List<QuerySnapshot> taskResult = task.getResult();
                            for (QuerySnapshot qs : taskResult) {
                                for (DocumentSnapshot ds : qs.getDocuments()) {
                                    tasks.add(ds.getReference().collection("id_products").get());
                                }
                            }
                        }
                        return Tasks.whenAllSuccess(tasks);
                    }
                })
                .continueWithTask(new Continuation<List<Task<QuerySnapshot>>, Task<List<Task<QuerySnapshot>>>>() {
                    @NonNull
                    @Override
                    public Task<List<Task<QuerySnapshot>>> then(@NonNull Task<List<Task<QuerySnapshot>>> task) throws Exception {
                        List<Task<DocumentSnapshot>> taskProducts = new ArrayList<>();
                        if (task.isSuccessful()) {
                            List<Task<QuerySnapshot>> tasks =  task.getResult();
                            for (Object taskResult1 : tasks) {
                                QuerySnapshot qs = (QuerySnapshot) taskResult1;
                                String categoryName, brandName;
                                Brand newBrand = new Brand();

                                if (!qs.getDocuments().isEmpty()) {
                                    List<DocumentSnapshot> qsDocuments = qs.getDocuments();
                                    List<String> infoCategory = Arrays.asList(qsDocuments.get(0).getReference().getPath().split("/"));
                                    // Category name
                                    categoryName = infoCategory.get(1);
                                    // Brand name
                                    brandName = infoCategory.get(3);

                                    dbProducts = FirebaseFirestore.getInstance();
                                    // Product list
                                    for (DocumentSnapshot ds : qs.getDocuments()) {
                                        taskProducts.add(dbProducts.collection("/marketplace_products")
                                                .document(ds.getId()).get());
                                    }
                                    newBrand.setBrand(brandName);
                                    loadBrandForCategory(categoryName, newBrand);
                                }
                            }
                        }
                        return Tasks.whenAllSuccess(taskProducts);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<List<Task<QuerySnapshot>>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Task<QuerySnapshot>>> task) {
                        if(task.isSuccessful()){
                            List<Task<QuerySnapshot>> taskList = task.getResult();
                            for(Object taskResult2: taskList){
                                DocumentSnapshot document = (DocumentSnapshot) taskResult2;
                                if(document.getData() != null) { //El producto no está en la base de datos
                                    if (!document.getData().isEmpty()) { //El producto está en la base de datos pero no tiene información
                                        Product newProduct = new Product();
                                        newProduct.setMarca(document.getData().get("marca").toString());
                                        newProduct.setNombre(document.getData().get("nombre").toString());
                                        newProduct.setPrecio(Integer.parseInt(document.getData().get("precio").toString()));
                                        newProduct.setCategoria(document.getData().get("categoria").toString());
                                        newProduct.setUnd_medida(document.getData().get("und_medida").toString());
                                        newProduct.setUbicacion(document.getData().get("ubicacion").toString());
                                        newProduct.setCodigo(document.getId());
                                        newProduct.setFormato(document.getData().get("formato").toString());
                                        newProduct.setUrl_imagen(document.getData().get("url_imagen").toString());
                                        loadProductForBrand(newProduct);
                                    }
                                    else{
                                        Log.d("Error Producto", "Falta información de producto: " + document.getId());
                                    }
                                }else{
                                    Log.d("Error producto", "Falta producto en la base de datos: " + document.getId());
                                }
                            }
                            spinnerAdapterMarketplace.notifyDataSetChanged();
                            spnCategory.setSelection(findCategoryByName("Todos los productos"));
                            Toast.makeText(getContext(), "Charge Successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private int findCategoryByName(String categoryName){
        for(int position=0; position < spinnerAdapterMarketplace.getCount(); position++){
            Category cat = (Category) spnCategory.getItemAtPosition(position);
            if(cat.getCategory().equals(categoryName))
                return position;
        }

        return 0;
    }

    private void loadProductForBrand(Product newProduct){
        String categoryName = newProduct.getCategoria();
        String brandName = newProduct.getMarca();

        for(Category category: categories){
            if(category.getCategory().equals(categoryName)){
                for(Brand brand: category.getBrands()){
                    if(brand.getBrand().equals(brandName)){
                        brand.addProduct(newProduct);
                    }
                }
            }
        }
    }

    private void loadBrandForCategory(String categoryName,  Brand newBrand){
        boolean brandExists= false;

        for(Category category: categories){
            if(category.getCategory().equals(categoryName)){
                for(Brand brand: category.getBrands()){
                    if(brand.getBrand().equals(newBrand.getBrand())){
                        brandExists = true;
                    }
                }
                // if does not exist brand in category
                if(!brandExists) {
                    category.addBrand(newBrand);
                }
            }
        }
    }

    public void setFilterText(String text){
        parentRVAdapterMarketplace.getFilter().filter(text);
    }
}




