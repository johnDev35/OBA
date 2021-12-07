package model;

import java.util.ArrayList;

public class Category {
    String category;
    String imgCategory;
    ArrayList <Brand> brands= new ArrayList<>();

    public Category() {
    }

    public Category(String category, String imgCategory) {
        this.category = category;
        this.imgCategory = imgCategory;
    }

    public ArrayList<Brand> getBrands() {
        return brands;
    }
    public String getImgCategory() {
        return imgCategory;
    }
    public String getCategory() {
        return category;
    }

    public void setBrands(ArrayList<Brand> brands) {
        this.brands = brands;
    }
    public void setImgCategory(String imgCategory) {
        this.imgCategory = imgCategory;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public void addBrand(Brand brand){
        brands.add(brand);
    }


}
