package com.example.quizletfake.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName= "answer")
public class Answer {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "answerID")
    private int answerID;
    @NotNull
    @ColumnInfo(name = "answerContent")
    private String answerContent;
    @NotNull
    @ColumnInfo(name = "questionID")
    private int questionID;

    public Answer(int answerID, @NotNull String answerContent, int questionID) {
        this.answerID = answerID;
        this.answerContent = answerContent;
        this.questionID = questionID;
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    @NotNull
    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(@NotNull String answerContent) {
        this.answerContent = answerContent;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerID=" + answerID +
                ", answerContent='" + answerContent + '\'' +
                ", questionID=" + questionID +
                '}';
    }
}
