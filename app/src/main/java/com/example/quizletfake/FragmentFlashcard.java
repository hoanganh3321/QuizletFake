package com.example.quizletfake;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizletfake.model.Flashcard;
import com.example.quizletfake.model.QuestionAnswerDisplay;
import com.example.quizletfake.recycleView.FlashcardOfAQuizAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentFlashcard extends Fragment {
    private List<Flashcard> flashcards;
    private List<QuestionAnswerDisplay> questionAnswerDisplays;
    private RecyclerView recyclerView;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setQuestionAnswerDisplays(List<QuestionAnswerDisplay> questionAnswerDisplays) {
        this.questionAnswerDisplays = questionAnswerDisplays;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flashcards=new ArrayList<>();
        recyclerView = view.findViewById(R.id.rcvFlashcard);
        for(QuestionAnswerDisplay qad: questionAnswerDisplays){
            flashcards.add(new Flashcard(qad.getQuestionID(),qad.getQuestionContent(),qad.getAnswerContent()));
        }
        FlashcardOfAQuizAdapter foaqa = new FlashcardOfAQuizAdapter(flashcards,context);
        recyclerView.setAdapter(foaqa);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flashcard, container, false);
    }
}