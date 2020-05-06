package com.example.myrecyclerview.presentation.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.myrecyclerview.R;

import java.util.Random;

public class GameActivity extends Activity {
    private EditText number;
    private TextView textView;
    private TextView historicText;
    private Button button;
    private ProgressBar progressBar;
    private int searchedValue;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        number = findViewById(R.id.editText_game);
        textView = findViewById(R.id.game_txt);
        historicText = findViewById(R.id.historic);
        button = findViewById(R.id.game_button);
        progressBar = findViewById(R.id.progressBar);

        button.setOnClickListener(buttonListener);
        init();
    }

    private void init(){
        score = 0;
        Random rand = new Random();
        searchedValue = rand.nextInt(100) + 1;

        progressBar.setProgress(score);
        historicText.setText("");
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Debug:", "Button cliked");

            String txtNumber = number.getText().toString();
            if (txtNumber.equals(""))
                return;

            historicText.append(txtNumber + "\r\n");
            progressBar.incrementProgressBy(1);
            score++;
            int value = Integer.parseInt(txtNumber);
            if (value == searchedValue)
                endGame();
            else if (value < searchedValue)
                Toast.makeText(getApplicationContext(), "Greater", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "Lower", Toast.LENGTH_LONG).show();
        }
    };

    private void endGame() {
        //On affiche une boite de dialogue
        new AlertDialog.Builder(GameActivity.this)

             //On lui met un titre
            .setTitle("Congratulations !")

            //un message
            .setMessage("You finished with "+score+" attempt(s)")

            //Un boutton rejouer
            .setPositiveButton("Retry", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                init();
            }
        })

            //Un bouton quitter
            .setNegativeButton("Exit", new AlertDialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            })

            //Une image
            .setIcon(R.drawable.victory_bob)

            //On l'affiche
           .show();
    }
}