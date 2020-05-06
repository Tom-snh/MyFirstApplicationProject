package com.example.myrecyclerview.presentation.controller;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.myrecyclerview.Constant;
import com.example.myrecyclerview.data.CategoriesAPI;
import com.example.myrecyclerview.presentation.model.Categories;
import com.example.myrecyclerview.presentation.model.RestCategorieResponse;
import com.example.myrecyclerview.presentation.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class MainController {
    private SharedPreferences sharedPreferences;
    private String json;
    private Gson gson;
    private MainActivity view;

    public MainController(MainActivity view, SharedPreferences sharedPreferences, Gson gson) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    public void onStart(){
        List<Categories> categoriesList = getDataFromCache();

        if(categoriesList != null)
            view.showList(categoriesList);
        else
            start(); //makingCall()
    }

    private List<Categories> getDataFromCache() {
        json = sharedPreferences.getString(Constant.KEY_CATEGORIES_LIST, null);
        if(json == null)
            return null;
        else{
            //Toast.makeText(getApplicationContext(), "List loaded",Toast.LENGTH_SHORT).show();
            Type listType = new TypeToken<List<Categories>>(){}.getType();
            return gson.fromJson(json,listType);
        }
    }
    //API
    private void start(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CategoriesAPI categoriesApi = retrofit.create(CategoriesAPI.class);

        Call<RestCategorieResponse> call = categoriesApi.getCategoriesResponse();
        call.enqueue(new Callback<RestCategorieResponse>() {
            @Override
            public void onResponse(Call<RestCategorieResponse> call, Response<RestCategorieResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Categories> categoriesList = response.body().getCategories();
                    saveList(categoriesList);
                    view.showList(categoriesList);
                }
                else
                    view.showError();
            }

            @Override
            public void onFailure(Call<RestCategorieResponse> call, Throwable t) {
                view.showError();
            }
        });
    }

    private void saveList(List<Categories> categoriesList) {
        //sharedPreferences = getSharedPreferences("app_esiea",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        gson = new Gson();
        json = gson.toJson(categoriesList);
        editor.putString(Constant.KEY_CATEGORIES_LIST, json);
        editor.apply();
        //Toast.makeText(getApplicationContext(), "List saved",Toast.LENGTH_SHORT).show();
    }

    public void onItemClicked(Categories categories){

    }
}
