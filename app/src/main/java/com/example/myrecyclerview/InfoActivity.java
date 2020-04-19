package com.example.myrecyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class InfoActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Button button = (Button)findViewById(R.id.previous_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(context,MainActivity.class);
                //startActivity(intent);
                InfoActivity.this.finish();
            }
        });
        getIncommingIntent();
    }

    private void getIncommingIntent(){
        if(getIntent().hasExtra("desc_1") && getIntent().hasExtra("image_1")){
            String description = getIntent().getStringExtra("desc_1");
            int image = getIntent().getIntExtra("image_1",0);
            setImage(image,description);
        }
    }

    private void setImage(int image_name,String desc){
        TextView description = findViewById(R.id.textInfo);
        description.setText(desc);

        ImageView imageView = findViewById(R.id.imageInfo);
        imageView.setImageResource(image_name);
    }
}
