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

import com.example.quizletfake.dao.QuizDao;
import com.example.quizletfake.recycleView.QuizAllAdapter;
import com.example.quizletfake.recycleView.QuizViewHolder;

public class FragmentTrangChu extends Fragment {
    private QuizDao quizDao;
    private QuizAllAdapter adapter;
    private FragmentTrangChu fragmentTrangChu;
    private RecyclerView recyclerView;
    private Context context;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView(view);
        bindingAction();
        realCategoryList();
        initRecyclerView();
    }
    public void setQuizDao(QuizDao quizDao){
        this.quizDao=quizDao;
    }
    public void setContext(Context context){
        this.context=context;
    }
    private void initRecyclerView() {
        adapter=new QuizAllAdapter(quizDao, context);
        adapter.setOnBtnShowQuizClickListener(callback);
        adapter.setOnBtnEditQuizClickListener(callback2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    private void realCategoryList() {
    }

    private void bindingAction() {
    }

    private void bindingView(View view) {
        recyclerView=view.findViewById(R.id.rcvTrangChuQuiz);

    }
    private QuizViewHolder.OnBtnEditQuizClick callback2;
    public void setOnBtnEditQuizClickListener(QuizViewHolder.OnBtnEditQuizClick callback) {
        this.callback2 = callback;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trang_chu, container, false);
    }

    public void changeAdapter(String query) {
        adapter.changeList(query);
    }

    public void resetAdapter() {
        adapter.resetList();
    }
    private QuizViewHolder.OnBtnShowQuizClick callback;
    public void setOnBtnShowQuizClickListener(QuizViewHolder.OnBtnShowQuizClick callback) {
        this.callback = callback;
    }
}
