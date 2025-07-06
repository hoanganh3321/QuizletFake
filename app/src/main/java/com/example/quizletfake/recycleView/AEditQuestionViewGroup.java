package com.example.quizletfake.recycleView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quizletfake.model.Answer;
import com.example.quizletfake.model.Question;
import com.example.quizletfake.model.QuestionAnswerAdd;

public class AEditQuestionViewGroup {
    private EditText edtQuestion;
    private EditText edtAnswer;
    private Button btnRemove;
    private TextView tvPosition;
    private int questionID;
    private int quizID;
    private int answerID;

    public AEditQuestionViewGroup(EditText edtQuestion, EditText edtAnswer, Button btnRemove, TextView tvPosition, int questionID, int quizID, int answerID) {
        this.edtQuestion = edtQuestion;
        this.edtAnswer = edtAnswer;
        this.btnRemove = btnRemove;
        this.tvPosition = tvPosition;
        this.questionID = questionID;
        this.quizID = quizID;
        this.answerID = answerID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public boolean isDeleted(){
        return tvPosition.getText().toString().equals("-1");
    }
    public boolean isAccepted(){
        return edtQuestion.getText().toString().trim().length()>0 && edtAnswer.getText().toString().trim().length()>0;
    }
    public QuestionAnswerAdd getQuestionAnswerAdd(){
        return new QuestionAnswerAdd(
                Integer.parseInt(tvPosition.getText().toString()),
                edtQuestion.getText().toString().trim(),
                edtAnswer.getText().toString().trim());
    }

    public EditText getEdtQuestion() {
        return edtQuestion;
    }

    public void setEdtQuestion(EditText edtQuestion) {
        this.edtQuestion = edtQuestion;
    }

    public EditText getEdtAnswer() {
        return edtAnswer;
    }

    public void setEdtAnswer(EditText edtAnswer) {
        this.edtAnswer = edtAnswer;
    }

    public Button getBtnRemove() {
        return btnRemove;
    }

    public void setBtnRemove(Button btnRemove) {
        this.btnRemove = btnRemove;
    }

    public TextView getTvPosition() {
        return tvPosition;
    }

    public void setTvPosition(TextView tvPosition) {
        this.tvPosition = tvPosition;
    }

    public Question getQuestion() {
        return new Question(questionID,edtQuestion.getText().toString().trim(),quizID);
    }

    public Answer getAnswer() {
        return new Answer(answerID,edtAnswer.getText().toString().trim(),questionID);
    }
}
