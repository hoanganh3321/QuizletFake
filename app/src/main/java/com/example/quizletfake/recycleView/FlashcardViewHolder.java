package com.example.quizletfake.recycleView;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizletfake.R;
import com.example.quizletfake.model.Flashcard;

public class FlashcardViewHolder extends RecyclerView.ViewHolder{
    private AnimatorSet frontAnim;
    private AnimatorSet backAnim;
    private TextView tvFront;
    private TextView tvBack;
    private boolean isFront=true;
    private float scale;
    private Context context;
    private int questionID;
    public FlashcardViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        tvFront=itemView.findViewById(R.id.card_front);
        tvBack=itemView.findViewById(R.id.card_back);
        tvFront.setOnClickListener(this::cardClick);
        tvBack.setOnClickListener(this::cardClick);
        scale=context.getResources().getDisplayMetrics().density;
        tvFront.setCameraDistance(8000*scale);
        tvBack.setCameraDistance(8000*scale);
        frontAnim= (AnimatorSet) AnimatorInflater.loadAnimator(context,R.animator.front_animator);
        backAnim= (AnimatorSet) AnimatorInflater.loadAnimator(context,R.animator.back_animator);
    }
    private void cardClick(View view) {
        if(isFront){
            frontAnim.setTarget(tvFront);
            backAnim.setTarget(tvBack);
            frontAnim.start();
            backAnim.start();
            isFront=false;
        }else{
            frontAnim.setTarget(tvBack);
            backAnim.setTarget(tvFront);
            backAnim.start();
            frontAnim.start();

            isFront=true;
        }
    }
    public void bind(Flashcard flashcard) {
        this.questionID=flashcard.getQuestionID();
        tvFront.setText(flashcard.getFrontText());
        tvBack.setText(flashcard.getBackText());
    }
}
