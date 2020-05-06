package com.example.myrecyclerview.presentation.model;

import com.example.myrecyclerview.presentation.model.Categories;

import java.util.List;

//Cette classe indique la réponse de l'API (ce qui sera affiché dans l'application)

public class RestCategorieResponse {
    private List<Categories> categories;

    public RestCategorieResponse(List<Categories> categories) {
        this.categories = categories;
    }

    public List<Categories> getCategories() {
        return categories;
    }
}
