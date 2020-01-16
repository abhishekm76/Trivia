package com.delloil.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.delloil.trivia.data.AnswerListAsyncResponse;
import com.delloil.trivia.data.QuestionBank;
import com.delloil.trivia.model.Question;
import com.delloil.trivia.model.Score;
import com.delloil.trivia.util.Prefs;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView questionText;
    private TextView counterText;
    private Button trueButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private Button falseButton;
    private String toastMessage ="";
    private  int backColor = 0;
    private int currentQuestionIndex =0;
    private List <Question>questionList;
    private int scoreCounter =0;
    private Score score;
    private TextView scoreTextView;
    private Prefs prefs;

    private static final String TAG = "trackmain" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        backColor=getResources().getColor(R.color.colorPrimary);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = new Score();//score object created
        prefs = new Prefs(MainActivity.this);

        nextButton = findViewById(R.id.next_Button);
        prevButton = findViewById(R.id.prev_Button);
        trueButton = findViewById(R.id.true_Button);
        falseButton = findViewById(R.id.false_Button);
        counterText = findViewById(R.id.counter_textView);
        questionText = findViewById(R.id.question_TextView);
        scoreTextView = findViewById(R.id.score_text);

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);

        scoreTextView.setText(MessageFormat.format("Current Score: {0}", String.valueOf(score.getScore()))); // Initial score
        questionList = new QuestionBank().getQuestions(

                new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                Log.d(TAG, "processFinished: "+questionArrayList);
                questionText.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                String qMessage=getResources().getString(R.string.counterMessage);
                counterText.setText(currentQuestionIndex +qMessage +questionArrayList.size());
            }
        }
        );
        Log.d(TAG, "onCreate: "+questionList);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.prev_Button:
                if(currentQuestionIndex>0){
                currentQuestionIndex=(currentQuestionIndex-1)%questionList.size();}
                updateQuestion();
                break;
            case R.id.next_Button:
                currentQuestionIndex=(currentQuestionIndex+1)%questionList.size();
                updateQuestion();
                prefs.saveHighest(scoreCounter);
                Log.d(TAG,"high"+prefs.getHigh());
                break;
            case R.id.true_Button:
                checkAnswer(true);
                updateQuestion();
                break;
            case R.id.false_Button:
                checkAnswer(false);
                updateQuestion();
                break;
        }
    }

    private void fadeAnimation(){
        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(350);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(backColor);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private void checkAnswer(boolean userAnswer) {
        boolean questionAnswer =questionList.get(currentQuestionIndex).getAnswerTrue();
        if(userAnswer== questionAnswer){
            toastMessage = "Correct";
            addPoints();
            fadeAnimation();
        }else{
            toastMessage ="Wrong";
            shakeanimation();
            deductPoints();
        }
        Toast.makeText(MainActivity.this,toastMessage,Toast.LENGTH_SHORT).show();
    }


    private void addPoints(){
        scoreCounter+= 100;
        score.setScore(scoreCounter );
        scoreTextView.setText(MessageFormat.format("Current Score: {0}", String.valueOf(score.getScore())));
         Log.d(TAG, "score"+ score.getScore());
    }

    private void deductPoints(){
        scoreCounter-= 100;
        if (scoreCounter <0) { scoreCounter=0;}
        score.setScore(scoreCounter );
        scoreTextView.setText(MessageFormat.format("Current Score: {0}", String.valueOf(score.getScore())));
        Log.d(TAG, "scoreminus"+ score.getScore());
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        questionText.setText(question);
        String qMessage=getResources().getString(R.string.counterMessage);
        counterText.setText(currentQuestionIndex +qMessage+questionList.size());
    }

    private void shakeanimation(){
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                cardView.setCardBackgroundColor(backColor);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
