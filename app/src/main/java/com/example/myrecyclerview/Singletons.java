package com.example.myrecyclerview;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myrecyclerview.data.CategoriesAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson gsonInstance;
    private static SharedPreferences sharedPreferences;
    private static CategoriesAPI categoriesInstance;

    public static Gson getGson(){
        if(gsonInstance == null) {
            gsonInstance = new Gson();
            //gsonInstance = new GsonBuilder()
              //      .setLenient()
                //    .create();
        }

        return gsonInstance;
    }

    public static SharedPreferences getSharedPref(Context context){
        if(sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences("app_esiea",Context.MODE_PRIVATE);

        return sharedPreferences;
    }

    public static CategoriesAPI getCategoriesAPI(){
        if(categoriesInstance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
            categoriesInstance = retrofit.create(CategoriesAPI.class);
        }

        return categoriesInstance;
    }
}
