package com.delloil.trivia.data;

import com.delloil.trivia.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {

    void processFinished(ArrayList<Question> questionArrayList);
}
