package model;

public class Category {
    String category;
    int imgCategory;

    public Category(String category, int imgCategory) {
        this.category = category;
        this.imgCategory = imgCategory;
    }

    public String getCategory() {
        return category;
    }

    public int getImgCategory() {
        return imgCategory;
    }
}
