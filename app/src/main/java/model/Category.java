package model;

public class Category {
    String category;
    String imgCategory;

    public Category() {
    }

    public Category(String category, String imgCategory) {
        this.category = category;
        this.imgCategory = imgCategory;
    }

    public String getCategory() {
        return category;
    }

    public String getImgCategory() {
        return imgCategory;
    }
}
