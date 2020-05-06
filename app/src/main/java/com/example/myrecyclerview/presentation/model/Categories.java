package com.example.myrecyclerview.presentation.model;

//Cette classe donne le format d'une Catégorie

public class Categories {
    private int id;
    private String title;
    private String description;

    public Categories(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
