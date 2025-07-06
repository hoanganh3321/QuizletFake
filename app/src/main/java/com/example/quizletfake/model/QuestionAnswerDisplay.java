package com.example.quizletfake.model;

public class QuestionAnswerDisplay {
    private int QuestionID;
    private String questionContent;
    private String AnswerContent;

    public QuestionAnswerDisplay(int questionID, String questionContent, String answerContent) {
        QuestionID = questionID;
        this.questionContent = questionContent;
        AnswerContent = answerContent;
    }
    public QuestionAnswerDisplay() {
    }
    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
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
