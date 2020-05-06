package com.example.myrecyclerview.presentation.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.myrecyclerview.data.CategoriesAPI;
import com.example.myrecyclerview.Constant;
import com.example.myrecyclerview.R;
import com.example.myrecyclerview.presentation.model.Categories;
import com.example.myrecyclerview.presentation.model.RestCategorieResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private String json;
    private Gson gson;
    private List<Categories> categoriesList;
    private int[] imageCategories = {R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,
            R.drawable.pic5,R.drawable.pic6,R.drawable.pic7,R.drawable.pic8,
            R.drawable.pic9,R.drawable.pic10,R.drawable.pic11,R.drawable.pic12,R.drawable.pic13};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Categories> categoriesList = getDataFromCache();
        if(categoriesList != null)
            showList(categoriesList);
        else
            start(); //makingCall()
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    private List<Categories> getDataFromCache() {
        sharedPreferences = getSharedPreferences("app_esiea",MODE_PRIVATE);
        gson = new Gson();
        json = sharedPreferences.getString(Constant.KEY_CATEGORIES_LIST, null);
        if(json == null)
            return null;
        else{
            //Toast.makeText(getApplicationContext(), "List loaded",Toast.LENGTH_SHORT).show();
            Type listType = new TypeToken<List<Categories>>(){}.getType();
            categoriesList = gson.fromJson(json,listType);
            return categoriesList;
        }
    }

    private void showList(List<Categories> categoriesList) {
        Log.d(TAG,"RecyclerView : init");
        recyclerView = findViewById(R.id.recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        /* use a linear layout manager */
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setHasFixedSize(true);

        //Fake list
        /*List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }*/

        // defini un adapteur
        mAdapter = new ListAdapter(categoriesList,imageCategories,this);
        recyclerView.setAdapter(mAdapter);
    }

    //API
    private static final String BASE_URL = "https://eonet.sci.gsfc.nasa.gov/api/v2.1/categories/";
    private void start(){
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
                    saveList(categoriesList);
                    showList(categoriesList);
                }
                else
                    showError();
            }

            @Override
            public void onFailure(Call<RestCategorieResponse> call, Throwable t) {
                showError();
            }
        });
    }

    private void saveList(List<Categories> categoriesList) {
        sharedPreferences = getSharedPreferences("app_esiea",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        gson = new Gson();
        json = gson.toJson(categoriesList);
        editor.putString(Constant.KEY_CATEGORIES_LIST, json);
        editor.apply();
        //Toast.makeText(getApplicationContext(), "List saved",Toast.LENGTH_SHORT).show();
    }

    private void showError(){
        Toast.makeText(getApplicationContext(), "API Error",Toast.LENGTH_SHORT).show();
    }
}