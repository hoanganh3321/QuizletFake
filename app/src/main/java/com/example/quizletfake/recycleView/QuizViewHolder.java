package com.example.quizletfake.recycleView;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizletfake.AccountNow;
import com.example.quizletfake.R;

public class QuizViewHolder extends RecyclerView.ViewHolder{
    private Context context;
    private TextView tvQuizTitle;
    private TextView tvNumberOfQuestion;
    private TextView tvAccountName;
    private Button btnEditQuiz;
    private int quizID;
    private int accountID;

    public QuizViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context=context;
        bindingView();
        bindingAction();
    }

    private void bindingAction() {

        itemView.setOnClickListener(this::onItemViewClick);
        btnEditQuiz.setOnClickListener(this::onBtnEditQuizClick);
    }

    private void onBtnEditQuizClick(View view) {
        if (callback2 != null) {
            callback2.onClick(quizID,tvQuizTitle.getText().toString());
        }
    }
    public interface OnBtnEditQuizClick {
        void onClick(int quizID, String quizTitle);
    }

    private OnBtnEditQuizClick callback2;
    public void setOnBtnEditQuizClickListener(OnBtnEditQuizClick callback) {
        this.callback2 = callback;
    }
    private void onItemViewClick(View view) {
        if (callback != null) {
            callback.onClick(quizID);
        }
    }
    public interface OnBtnShowQuizClick {
        void onClick(int id);
    }

    private OnBtnShowQuizClick callback;
    public void setOnBtnShowQuizClickListener(OnBtnShowQuizClick callback) {
        this.callback = callback;
    }

    private void bindingView() {
        tvQuizTitle=itemView.findViewById(R.id.tvQuizTitle);
        tvNumberOfQuestion=itemView.findViewById(R.id.tvNumberOfQuestion);
        tvAccountName=itemView.findViewById(R.id.tvAccountName);
        btnEditQuiz=itemView.findViewById(R.id.btnEditQuiz);
    }
    public void bind(int accountID,int quizID,String quizTitle ,int numberOfQuestion ,String accountName ) {
        this.quizID=quizID;
        this.accountID=accountID;
        tvQuizTitle.setText(quizTitle);
        tvNumberOfQuestion.setText(""+numberOfQuestion+" thuật ngữ");
        tvAccountName.setText(accountName);
        if(accountID== AccountNow.thisAccount.getAccountID()){
            btnEditQuiz.setEnabled(true);
            btnEditQuiz.setVisibility(View.VISIBLE);
        }else{
            btnEditQuiz.setEnabled(false);
            btnEditQuiz.setVisibility(View.GONE);
        }
    }
}
