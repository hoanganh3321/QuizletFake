package com.example.quizletfake.recycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizletfake.R;
import com.example.quizletfake.model.Flashcard;

import java.util.List;

public class FlashcardOfAQuizAdapter extends RecyclerView.Adapter<FlashcardViewHolder>{
    private List<Flashcard> listFlashCard;
    private Context context;


    public FlashcardOfAQuizAdapter(List<Flashcard> listFlashCard, Context context) {
        this.listFlashCard = listFlashCard;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    private LayoutInflater inflater;
    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.layout_flashcard_item,parent,false);
        FlashcardViewHolder fvh = new FlashcardViewHolder(v,context);
        //cvh.setOnBtnShowQuizClickListener(callback);
        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        Flashcard c = listFlashCard.get(position);
        holder.bind(c);
    }

    @Override
    public int getItemCount() {
        return listFlashCard.size();
    }
}
