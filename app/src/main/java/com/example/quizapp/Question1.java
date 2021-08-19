package com.example.quizapp;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Question1 extends AppCompatActivity implements View.OnClickListener{

    //AnimationDrawable AnimDraw;
    private TextView question,que_count;
    private Button option1, option2, option3, option4;
  //  private List<Questions> questionList;
    private LottieAnimationView confetti;
    private Dialog dialog,loadingDialog;
    private int score;
    private FirebaseFirestore firestore;

    int queNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques1);

        /*ImageView AnimGrad = (ImageView) findViewById(R.id.root_layout);
        AnimGrad.setBackgroundResource(R.drawable.gradient_animation);
        AnimDraw = (AnimationDrawable) AnimGrad.getBackground();
        AnimDraw.setEnterFadeDuration(50);
        AnimDraw.setExitFadeDuration(5000);
        AnimDraw.start();*/


        question = findViewById(R.id.question);
        que_count = findViewById(R.id.quenum);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        dialog = new Dialog(this);
        score = 0;
        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        firestore = FirebaseFirestore.getInstance();

       /* loadingDialog = new Dialog(Question1.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_bg);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();*/

        getQuestionsList();
    }

    private void getQuestionsList()
    {
       // questionList = new ArrayList<>();

       /* questionList.add(new Questions("Question 1","Option 1","Option 2","Option 3","Option 4",2));
        questionList.add(new Questions("Question 2","Option 1","Option 2","Option 3","Option 4",1));
        questionList.add(new Questions("Question 3","Option 1","Option 2","Option 3","Option 4",3));*/

        firestore.collection("Questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                /*if (task.isSuccessful()){
                    QuerySnapshot questions = task.getResult();

                    for (QueryDocumentSnapshot doc : questions){
                        questionList.add(new Questions(doc.getString("Que"),
                                doc.getString("Option 1"),
                                doc.getString("Option 2"),
                                doc.getString("Option 3"),
                                doc.getString("Option 4"),
                                Integer.valueOf(doc.getString("Answer"))
                        ));
                    }*/

                    setQuestion();
                }
               /* else {
                    Toast.makeText(Question1.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }*/
               // loadingDialog.cancel();
            });


    }

    private void setQuestion()
    {
        question.setText(questionList.get(0).getQuestion());
        option1.setText(questionList.get(0).getOption1());
        option2.setText(questionList.get(0).getOption2());
        option3.setText(questionList.get(0).getOption3());
        option4.setText(questionList.get(0).getOption4());

        que_count.setText(String.valueOf(1)+"/"+String.valueOf(questionList.size()));

        queNum = 0;
    }

    @Override
    public void onClick(View v)
    {
        int choice = 0;

        switch (v.getId())
        {
            case R.id.option1:
                choice = 1;
                break;

            case R.id.option2:
                choice = 2;
                break;

            case R.id.option3:
                choice = 3;
                break;

            case R.id.option4:
                choice = 4;
                break;

            default:
        }

        checkAnswer(choice,v);
    }

    private void checkAnswer(int choice,View v)
    {
        if(choice == questionList.get(queNum).getAnswer())
        {
            //Right Choice

            dialog.setContentView(R.layout.correct_ans);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Button btnok = dialog.findViewById(R.id.popbtn);

            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
            score++;

        }
        else
        {
            //Wrong Choice

            dialog.setContentView(R.layout.wrong_ans);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Button btnok = dialog.findViewById(R.id.popbtn);

            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }

        changeQuestion();
    }

    private void changeQuestion()
    {
        if (queNum < (questionList.size()-1))
        {
            //Not Last Question
            // Change Question

            queNum++;

            playAnim(question,0,0);
            playAnim(option1,0,1);
            playAnim(option2,0,2);
            playAnim(option3,0,3);
            playAnim(option4,0,4);

            que_count.setText(String.valueOf(queNum+1) + "/" + String.valueOf(questionList.size()));
        }
        else
        {
            //Last Question
            //Display Score

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Question1.this,Score.class);
                    intent.putExtra("Score", String.valueOf(score));
                    startActivity(intent);
                }
            },2000);

            //Question1.this.finish();
        }
    }

    private void playAnim(View view, final int value, int viewNum)
    {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (value == 0)
                {
                    switch (viewNum)
                    {
                        case 0:
                            ((TextView)view).setText(questionList.get(queNum).getQuestion());
                            break;

                        case 1:
                            ((Button)view).setText(questionList.get(queNum).getOption1());
                            break;

                        case 2:
                            ((Button)view).setText(questionList.get(queNum).getOption2());
                            break;

                        case 3:
                            ((Button)view).setText(questionList.get(queNum).getOption3());
                            break;

                        case 4:
                            ((Button)view).setText(questionList.get(queNum).getOption4());
                            break;

                        default:
                    }

                    playAnim(view,1,viewNum);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }
        );
    }
}