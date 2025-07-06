package com.example.quizletfake.recycleView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizletfake.R;
import com.example.quizletfake.model.QuestionAnswerDisplay;

public class QuestionViewHolder extends RecyclerView.ViewHolder{
    private TextView tvQuestionContent;
    private TextView tvAnswerContent;
    private int questionID;
    public QuestionViewHolder(@NonNull View itemView) {
        super(itemView);
        bindingView();
        bindingAction();
    }
    private void bindingAction() {
        itemView.setOnClickListener(this::onItemViewClick);
    }
    private void onItemViewClick(View view) {
        if (callback != null) {
            callback.onClick(questionID);
        }
    }
    public interface OnBtnShowQuizClick {
        void onClick(int id);
    }

    private QuizViewHolder.OnBtnShowQuizClick callback;
    public void setOnBtnShowQuizClickListener(QuizViewHolder.OnBtnShowQuizClick callback) {
        this.callback = callback;
    }

    private void bindingView() {
        tvQuestionContent=itemView.findViewById(R.id.tvQuestionContent);
        tvAnswerContent=itemView.findViewById(R.id.tvAnswerContent);
    }
    public void bind(QuestionAnswerDisplay questionAnswerDisplay) {
        this.questionID=questionAnswerDisplay.getQuestionID();
        tvQuestionContent.setText(questionAnswerDisplay.getQuestionContent());
        tvAnswerContent.setText(questionAnswerDisplay.getAnswerContent());
    }
}
