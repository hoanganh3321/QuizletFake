package com.example.quizletfake.recycleView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quizletfake.model.QuestionAnswerAdd;

public class AAddQuestionViewGroup {
    private EditText edtQuestion;
    private EditText edtAnswer;
    private Button btnRemove;
    private TextView tvPosition;

    public AAddQuestionViewGroup(EditText edtQuestion, EditText edtAnswer, Button btnRemove, TextView tvPosition) {
        this.edtQuestion = edtQuestion;
        this.edtAnswer = edtAnswer;
        this.btnRemove = btnRemove;
        this.tvPosition = tvPosition;
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
}
