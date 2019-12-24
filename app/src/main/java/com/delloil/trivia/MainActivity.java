package com.delloil.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.delloil.trivia.data.AnswerListAsyncResponse;
import com.delloil.trivia.data.QuestionBank;
import com.delloil.trivia.model.Question;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView questionText;
    private TextView counterText;
    private Button trueButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private Button falseButton;

    private int currentQuestionIndex =0;
    private List <Question>questionList;

    private static final String TAG = "trackmain" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = findViewById(R.id.next_Button);
        prevButton = findViewById(R.id.prev_Button);
        trueButton = findViewById(R.id.true_Button);
        falseButton = findViewById(R.id.false_Button);
        counterText = findViewById(R.id.counter_textView);
        questionText = findViewById(R.id.question_TextView);

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);

        questionList = new QuestionBank().getQuestions(

                new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                Log.d(TAG, "processFinished: "+questionArrayList);
                questionText.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
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
                break;
            case R.id.true_Button:
                break;
            case R.id.false_Button:
                break;
        }
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        questionText.setText(question);
    }


}
