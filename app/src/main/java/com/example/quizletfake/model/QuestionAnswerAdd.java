package com.example.quizletfake.model;

public class QuestionAnswerAdd {
    private int position;
    private String questionContent;
    private String AnswerContent;

    public QuestionAnswerAdd(int position, String questionContent, String answerContent) {
        this.position = position;
        this.questionContent = questionContent;
        AnswerContent = answerContent;
    }

    public QuestionAnswerAdd() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getAnswerContent() {
        return AnswerContent;
    }

    public void setAnswerContent(String answerContent) {
        AnswerContent = answerContent;
    }
}
