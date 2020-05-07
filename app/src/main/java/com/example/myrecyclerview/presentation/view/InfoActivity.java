package com.example.myrecyclerview.presentation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myrecyclerview.R;


public class InfoActivity extends AppCompatActivity {

    @Override
    //Méthode lancée au début de l'activité
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Se réfère au layout activity_info
        setContentView(R.layout.activity_info);

        //On récupère l'id du bouton puis on éxécute une action s'il est cliqué
        Button button = findViewById(R.id.previous_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoActivity.this.finish();
            }
        });
        getIncommingIntent();
    }


    private void getIncommingIntent(){
        //On vérifie que l'intent a bien récupéré les données
        if(getIntent().hasExtra("description") && getIntent().hasExtra("image")){
            //Si oui, on les stock dans une variable..
            String description = getIntent().getStringExtra("description");
            int image = getIntent().getIntExtra("image",0);

            //...puis on l'affiche dans l'activité courante
            setImage(image,description);
        }
    }

    private void setImage(int image_name,String desc){
        //On récupère l'id du textView...
        TextView description = findViewById(R.id.textInfo);
        //...et prend la valeur de la donnée desc
        description.setText(desc);

        //de meme pour l'image (int)
        ImageView imageView = findViewById(R.id.imageInfo);
        imageView.setImageResource(image_name);
    }
}
