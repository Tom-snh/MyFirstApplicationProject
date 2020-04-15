package com.example.myrecyclerview;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriesAPI {
    @GET("https://eonet.sci.gsfc.nasa.gov/api/v2.1/categories")
    Call<RestCategorieResponse> getCategoriesResponse();
}
