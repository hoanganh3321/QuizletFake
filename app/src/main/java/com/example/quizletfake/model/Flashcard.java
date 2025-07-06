package com.example.quizletfake.model;

public class Flashcard {
    private int questionID;
    private String frontText;
    private String backText;

    public Flashcard(int questionID, String frontText, String backText) {
        this.questionID = questionID;
        this.frontText = frontText;
        this.backText = backText;
    }

    public Flashcard() {
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getFrontText() {
        return frontText;
    }

    public void setFrontText(String frontText) {
        this.frontText = frontText;
    }

    public String getBackText() {
        return backText;
    }

    public void setBackText(String backText) {
        this.backText = backText;
    }
}
