package com.example.myrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //showList();
        start(); //makingCall()
    }

    private void showList(List<Categories> categoriesList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Fake list
        /*List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }*/

        // define an adapter
        mAdapter = new ListAdapter(categoriesList);
        recyclerView.setAdapter(mAdapter);
    }

    private static final String BASE_URL = "https://eonet.sci.gsfc.nasa.gov/api/v2.1/categories/";
    private void start(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CategoriesAPI categoriesApi = retrofit.create(CategoriesAPI.class);

        Call<RestCategorieResponse> call = categoriesApi.getCategoriesResponse();
        call.enqueue(new Callback<RestCategorieResponse>() {
            @Override
            public void onResponse(Call<RestCategorieResponse> call, Response<RestCategorieResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Categories> categoriesList = response.body().getCategories();
                    showList(categoriesList);
                }
                else{
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestCategorieResponse> call, Throwable t) {
                showError();
            }
        });
    }

    private void showError(){
        Toast.makeText(getApplicationContext(), "API Error",Toast.LENGTH_SHORT).show();
    }
}
