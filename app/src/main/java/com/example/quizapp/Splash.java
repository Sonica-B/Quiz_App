package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;

import java.util.ArrayList;
import java.util.List;

public class Splash extends AppCompatActivity {

    private List<Questions> quelist;
    private FirebaseFirestore firestore ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        firestore = FirebaseFirestore.getInstance();
        quelist = new ArrayList<>();

      new Thread(){
            public void run(){
                loadData();
            }
      }.start();
    }
    private void loadData() {
        quelist.clear();
        firestore.collection("Questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot questions = task.getResult();

                    for (QueryDocumentSnapshot doc : questions){
                        quelist.add(new Questions(doc.getString("Que"),
                                doc.getString("Option 1"),
                                doc.getString("Option 2"),
                                doc.getString("Option 3"),
                                doc.getString("Option 4"),
                                Integer.valueOf(doc.getString("Answer"))
                        ));

                        Intent intent = new Intent(Splash.this,MainActivity.class);
                        startActivity(intent);
                        Splash.this.finish();
                    }
                    else {
                        Toast.makeText(Splash.this,"No Questions Exists",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else {
                    Toast.makeText(Splash.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}