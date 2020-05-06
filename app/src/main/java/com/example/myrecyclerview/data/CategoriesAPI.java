package com.example.myrecyclerview.data;

import com.example.myrecyclerview.presentation.model.RestCategorieResponse;

import retrofit2.Call;
import retrofit2.http.GET;

//Cette classe récupère les données de l'API

public interface CategoriesAPI {
    @GET("https://eonet.sci.gsfc.nasa.gov/api/v2.1/categories")
    Call<RestCategorieResponse> getCategoriesResponse();
}
