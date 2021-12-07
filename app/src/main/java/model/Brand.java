package model;

import java.util.ArrayList;

public class Brand {
    String brand;
<<<<<<< HEAD
    ArrayList<Product> products = new ArrayList<>();
=======
            ArrayList<String> productos= new ArrayList<>();
>>>>>>> main


    public Brand() {
    }

    public String getBrand() {
        return brand;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public  void addProduct(Product product){
        products.add(product);
    }
}
