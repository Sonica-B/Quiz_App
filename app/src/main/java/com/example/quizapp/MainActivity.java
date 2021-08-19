package com.example.quizapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable AnimDraw;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView AnimGrad = (ImageView) findViewById(R.id.root_layout);
        AnimGrad.setBackgroundResource(R.drawable.gradient_animation);
        AnimDraw = (AnimationDrawable) AnimGrad.getBackground();
        AnimDraw.setEnterFadeDuration(50);
        AnimDraw.setExitFadeDuration(5000);
        AnimDraw.start();



        Button Start_btn = (Button) findViewById(R.id.Startbtn);
        Start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Question1.class);
                startActivity(intent);
            }
        });


    }
}