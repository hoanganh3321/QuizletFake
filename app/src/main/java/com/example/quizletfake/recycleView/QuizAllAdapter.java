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
import com.example.quizletfake.dao.QuizDao;
import com.example.quizletfake.model.QuizDisplay;

import java.util.ArrayList;
import java.util.List;

public class QuizAllAdapter extends RecyclerView.Adapter<QuizViewHolder>{
    private QuizDao quizDao;
    private List<QuizDisplay> listQuiz;
    private List<QuizDisplay> listQuizReal;
    private Context context;
    private LayoutInflater inflater;
    public QuizAllAdapter(QuizDao quizDao, Context context) {
        //this.realQuizList=categoryList;
        this.quizDao = quizDao;
        new LoadListQuiz().execute(quizDao);
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

    public void changeList(String query) {
        List<QuizDisplay> result= new ArrayList<>() ;
        for (QuizDisplay c:listQuiz) {
            if(c.getTitle().toLowerCase().contains(query.toLowerCase())){
                result.add(c);
            }
        }
        //tong sy nhat: dep trai nhat lop SE1623
        listQuiz=result;
        this.notifyDataSetChanged();
    }

    public void resetList() {
        listQuiz=listQuizReal;
        this.notifyDataSetChanged();
    }

    private class LoadListQuiz extends AsyncTask<QuizDao, Integer, List<QuizDisplay>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listQuiz=new ArrayList<>();
        }

        @Override
        protected List<QuizDisplay> doInBackground(QuizDao... quizDaos) {
            QuizDao quizDao1 = quizDaos[0];
            Cursor c = quizDao1.getAllQuizDisplay();
            Cursor n=quizDao1.getNumberOfQuestion();
            List<QuizDisplay> listQuiz2= new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    @SuppressLint("Range") int authorID = c.getInt(c.getColumnIndex("accountID"));
                    @SuppressLint("Range") int quizID = c.getInt(c.getColumnIndex("quizID"));
                    @SuppressLint("Range") String title = c.getString(c.getColumnIndex("quizTitle"));
                    @SuppressLint("Range") String authorName = c.getString(c.getColumnIndex("accountName"));
                    QuizDisplay ca = new QuizDisplay(authorID,quizID,title,authorName);
                    listQuiz2.add(ca);
                } while (c.moveToNext());
            }
            int i=0;
            if (n.moveToFirst()) {
                do {

                    @SuppressLint("Range") int number = n.getInt(0);
                    listQuiz2.get(i).setNumberOfQuestion(number);
                    i++;
                } while (n.moveToNext());
            }
            return listQuiz2;
        }

        @Override
        protected void onPostExecute(List<QuizDisplay> quizzes) {
            super.onPostExecute(quizzes);
            listQuiz=quizzes;
            listQuizReal=quizzes;
            QuizAllAdapter.this.notifyDataSetChanged();
        }
    }
}
