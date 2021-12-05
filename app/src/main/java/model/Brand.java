package model;

import java.util.ArrayList;

public class Brand {
    String brand;
    ArrayList<Product> products = new ArrayList<>();


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
