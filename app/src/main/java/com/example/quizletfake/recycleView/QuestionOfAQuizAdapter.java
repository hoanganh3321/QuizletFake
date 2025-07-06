package com.example.quizletfake.recycleView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizletfake.R;
import com.example.quizletfake.dao.QuestionDao;
import com.example.quizletfake.model.QuestionAnswerDisplay;

import java.util.ArrayList;
import java.util.List;

public class QuestionOfAQuizAdapter extends RecyclerView.Adapter<QuestionViewHolder>{
    private QuestionDao questionDao;
    private Context context;

    private int quizID;
    public void setQuizID(int quizID){
        this.quizID=quizID;
    }
    private LayoutInflater inflater;
    private List<QuestionAnswerDisplay> questionAnswerDisplays;
    public QuestionOfAQuizAdapter(QuestionDao questionDao, Context context) {
        //this.realQuizList=categoryList;
        this.questionDao = questionDao;
        new LoadListQuestion().execute(questionDao);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.layout_question_item,parent,false);
        QuestionViewHolder cvh = new QuestionViewHolder(v);
        //cvh.setOnBtnShowQuizClickListener(callback);
        return cvh;
    }
    /*private QuizViewHolder.OnBtnShowQuizClick callback;

    public void setOnBtnShowQuizClickListener(QuizViewHolder.OnBtnShowQuizClick callback) {
        this.callback = callback;
    }*/

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        QuestionAnswerDisplay c = questionAnswerDisplays.get(position);
        holder.bind(c);
    }

    @Override
    public int getItemCount() {
        return questionAnswerDisplays.size();
    }

    public List<QuestionAnswerDisplay>  getListQuestionAnswer() {
        return  questionAnswerDisplays;
    }

    private class LoadListQuestion extends AsyncTask<QuestionDao, Integer, List<QuestionAnswerDisplay>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            questionAnswerDisplays=new ArrayList<>();
        }

        @Override
        protected List<QuestionAnswerDisplay> doInBackground(QuestionDao... questionDaos) {
            Cursor c = questionDaos[0].getAllQuestionAnswerDisplay(quizID);
            List<QuestionAnswerDisplay> listQuestionAnswer = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    @SuppressLint("Range") int quizID = c.getInt(c.getColumnIndex("quizID"));
                    @SuppressLint("Range") String questionContent = c.getString(c.getColumnIndex("questionContent"));
                    @SuppressLint("Range") String answerContent = c.getString(c.getColumnIndex("answerContent"));
                    QuestionAnswerDisplay ca = new QuestionAnswerDisplay(quizID,questionContent,answerContent);
                    listQuestionAnswer.add(ca);
                } while (c.moveToNext());
            }
            return listQuestionAnswer;
        }

        @Override
        protected void onPostExecute(List<QuestionAnswerDisplay> questionAnswerDisplaysResult) {
            super.onPostExecute(questionAnswerDisplaysResult);
            questionAnswerDisplays=questionAnswerDisplaysResult;
            QuestionOfAQuizAdapter.this.notifyDataSetChanged();
        }
    }
}
