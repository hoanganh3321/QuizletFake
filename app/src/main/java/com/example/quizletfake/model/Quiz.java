package com.example.quizletfake.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName= "quiz")
public class Quiz {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "quizID")
    private int quizID;
    @NotNull
    @ColumnInfo(name = "quizTitle")
    private String quizTitle;
    @NotNull
    @ColumnInfo(name = "accountID")
    private int accountID;

    public Quiz(int quizID, @NotNull String quizTitle, int accountID) {
        this.quizID = quizID;
        this.quizTitle = quizTitle;
        this.accountID = accountID;
    }
    public Quiz() {
    }
    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    @NotNull
    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(@NotNull String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizID=" + quizID +
                ", quizTitle='" + quizTitle + '\'' +
                ", accountID=" + accountID +
                '}';
    }
}
