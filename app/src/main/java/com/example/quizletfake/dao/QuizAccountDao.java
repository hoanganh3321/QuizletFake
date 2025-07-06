package com.example.quizletfake.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.quizletfake.model.QuizAccount;

@Dao
public interface QuizAccountDao {
    @Insert
    public void insertQuizAccount(QuizAccount q);
}
