package com.example.myrecyclerview.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrecyclerview.R;
import com.example.myrecyclerview.presentation.view.MainActivity;

public class FirstActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button button = findViewById(R.id.start_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Changement d'activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                //Fin de l'activity courante
                finish();
            }
        });
    }
}
