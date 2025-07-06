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

import com.example.quizletfake.AccountNow;
import com.example.quizletfake.R;
import com.example.quizletfake.dao.QuizDao;
import com.example.quizletfake.model.QuizDisplay;

import java.util.ArrayList;
import java.util.List;

public class QuizRecentAdapter extends RecyclerView.Adapter<QuizViewHolder>{
    private QuizDao quizDao;
    private List<QuizDisplay> listQuiz;
    private Context context;
    private LayoutInflater inflater;
    public QuizRecentAdapter(QuizDao quizDao, Context context) {
        //this.realQuizList=categoryList;
        this.quizDao = quizDao;
        new LoadListQuizRecently().execute(quizDao);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.layout_quiz_item,parent,false);
        QuizViewHolder cvh = new QuizViewHolder(v,context);
        cvh.setOnBtnShowQuizClickListener(callback);
        cvh.setOnBtnEditQuizClickListener(callback2);
        return cvh;
    }
    private QuizViewHolder.OnBtnEditQuizClick callback2;
    public void setOnBtnEditQuizClickListener(QuizViewHolder.OnBtnEditQuizClick callback) {
        this.callback2 = callback;
    }
    private QuizViewHolder.OnBtnShowQuizClick callback;

    public void setOnBtnShowQuizClickListener(QuizViewHolder.OnBtnShowQuizClick callback) {
        this.callback = callback;
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {

        QuizDisplay c = listQuiz.get(position);
        holder.bind(c.getAuthorID(),c.getQuizID(),c.getTitle(),c.getNumberOfQuestion(),c.getAuthorName());
    }

    @Override
    public int getItemCount() {
        return listQuiz.size();
    }
    private class LoadListQuizRecently extends AsyncTask<QuizDao, Integer, List<QuizDisplay>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listQuiz=new ArrayList<>();
        }

        @Override
        protected List<QuizDisplay> doInBackground(QuizDao... quizDaos) {
            QuizDao quizDao1 = quizDaos[0];
            Cursor c = quizDao1.getTenQuizRecently(AccountNow.thisAccount.getAccountID());
            List<QuizDisplay> listQuiz2= new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    @SuppressLint("Range") int authorID = c.getInt(c.getColumnIndex("accountID"));
                    @SuppressLint("Range") int quizID = c.getInt(c.getColumnIndex("quizID"));
                    @SuppressLint("Range") String title = c.getString(c.getColumnIndex("quizTitle"));
                    QuizDisplay ca = new QuizDisplay();
                    ca.setAuthorID(authorID);
                    ca.setQuizID(quizID);
                    ca.setTitle(title);
                    listQuiz2.add(ca);
                } while (c.moveToNext());
            }
            List<Integer> listQuizID=new ArrayList<>();
            for(QuizDisplay qd: listQuiz2){
                listQuizID.add(qd.getQuizID());
            }
            Cursor n=quizDao1.getNumberOfQuestion(listQuizID);

            if (n.moveToFirst()) {
                do {

                    @SuppressLint("Range") int number = n.getInt(0);
                    @SuppressLint("Range") int quizID = n.getInt(1);
                    for(QuizDisplay qd: listQuiz2){
                        if(qd.getQuizID()==quizID){
                            qd.setNumberOfQuestion(number);
                        }
                    }
                } while (n.moveToNext());
            }
            Cursor m =quizDao1.getAuthorOfQuiz(listQuizID);
            if (m.moveToFirst()) {
                do {

                    @SuppressLint("Range") String authorName = m.getString(0);
                    @SuppressLint("Range") int quizID = m.getInt(1);
                    for(QuizDisplay qd: listQuiz2){
                        if(qd.getQuizID()==quizID){
                            qd.setAuthorName(authorName);
                        }
                    }
                } while (m.moveToNext());
            }
            return listQuiz2;
        }

        @Override
        protected void onPostExecute(List<QuizDisplay> quizzes) {
            super.onPostExecute(quizzes);
            listQuiz=quizzes;
            QuizRecentAdapter.this.notifyDataSetChanged();
        }
    }
}
