package com.example.quizletfake.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName= "question")
public class Question {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "questionID")
    private int questionID;
    @NotNull
    @ColumnInfo(name = "questionContent")
    private String questionContent;
    @NotNull
    @ColumnInfo(name = "quizID")
    private int quizID;

    public Question(int questionID, @NotNull String questionContent, int quizID) {
        this.questionID = questionID;
        this.questionContent = questionContent;
        this.quizID = quizID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    @NotNull
    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(@NotNull String questionContent) {
        this.questionContent = questionContent;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionID=" + questionID +
                ", questionContent='" + questionContent + '\'' +
                ", quizID=" + quizID +
                '}';
    }
}
