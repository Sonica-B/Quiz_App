package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Splash extends AppCompatActivity {

    //public static List<Questions> quelist;
    private FirebaseFirestore firestore ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        firestore = FirebaseFirestore.getInstance();
        //quelist = new ArrayList<>();

      new Thread(){
            public void run(){
                loadData();
            }
      }.start();
    }
    public void loadData() {
       // quelist.clear();
        Task<QuerySnapshot> doc = firestore.collection("Questions").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                Splash.this.startActivity(intent);
                Splash.this.finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
             /*   .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Failed to fetch data",Toast.LENGTH_SHORT).show();
                    }

                });*/
               /* if(task.isSuccessful()){
                    QuerySnapshot questions = task.getResult();
                    if(questions.exists) {
                        for (QueryDocumentSnapshot doc : questions) {
                            quelist.add(new Questions(doc.getString("Que"),
                                    doc.getString("Option 1"),
                                    doc.getString("Option 2"),
                                    doc.getString("Option 3"),
                                    doc.getString("Option 4"),
                                    Integer.valueOf(doc.getString("Answer"))
                            ));
                        }
                        Intent intent = new Intent(Splash.this,MainActivity.class);
                        startActivity(intent);
                    }
                       // Splash.this.finish();

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
    }*/
}

}