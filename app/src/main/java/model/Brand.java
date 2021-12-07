package model;

import java.util.ArrayList;

public class Brand {
    String brand;
            ArrayList<String> productos= new ArrayList<>();

    public Brand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public ArrayList<String> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<String> productos) {
        this.productos = productos;
    }
}
