package com.delloil.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.delloil.trivia.data.AnswerListAsyncResponse;
import com.delloil.trivia.data.QuestionBank;
import com.delloil.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Question> questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                Log.d(TAG, "processFinished: "+questionArrayList);
            }
        });
        Log.d(TAG, "onCreate: "+questionList);
    }
}
