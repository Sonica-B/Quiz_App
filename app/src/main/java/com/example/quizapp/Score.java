package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Score extends AppCompatActivity {

    private TextView Score;
    private Button PlayAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Score = findViewById(R.id.FinalScore);
        PlayAgain = findViewById(R.id.PlayAgain);

        String score_str = getIntent().getStringExtra("Score");
        Score.setText(score_str);

        PlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Score.this,MainActivity.class);
                Score.this.startActivity(intent);
               // Score.this.finish();
            }
        });
    }
}