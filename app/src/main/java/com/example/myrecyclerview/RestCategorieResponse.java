package com.example.myrecyclerview;

import java.util.List;

public class RestCategorieResponse {
    private String title;
    private String description;
    private String lien;
    private List<Categories> categories;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLien() {
        return lien;
    }

    public List<Categories> getCategories() {
        return categories;
    }
}
