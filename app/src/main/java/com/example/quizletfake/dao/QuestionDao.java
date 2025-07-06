package com.example.quizletfake.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quizletfake.model.Answer;
import com.example.quizletfake.model.Question;
import com.example.quizletfake.model.Quiz;

import java.util.List;

@Dao
public interface QuestionDao {
    @Query("SELECT question.quizID,question.questionContent, answer.answerContent FROM question,answer WHERE question.questionID=answer.questionID and question.quizID= :quizID")
    public Cursor getAllQuestionAnswerDisplay(int quizID);
    @Insert
    public long InsertQuiz(Quiz quiz);
    @Insert
    public List<Long> InsertQuestions(List<Question> questions);
    @Insert
    public void InsertAnswers(List<Answer> answers);

    @Query("SELECT question.quizID,question.questionContent,question.questionID,answer.answerID, answer.answerContent FROM question,answer WHERE question.questionID=answer.questionID and question.quizID= :quizID")
    public Cursor getAllQuestionAnswer(int quizID);
    @Update
    void updateQuiz(Quiz quiz);
    @Update
    void updateQuestions(List<Question> questions);
    @Update
    void updateAnswers(List<Answer> answers);
    @Delete
    void deleteAnswers(List<Answer> answersDelete);
    @Delete
    void deleteQuestions(List<Question> questionsDelete);
    @Insert
    List<Long> addQuestions(List<Question> questionsAdd);
    @Insert
    void addAnswers(List<Answer> answersAdd);
}