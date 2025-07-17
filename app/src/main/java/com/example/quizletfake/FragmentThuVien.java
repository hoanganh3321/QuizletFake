package com.example.quizletfake;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizletfake.dao.QuizDao;
import com.example.quizletfake.model.Quiz;
import com.example.quizletfake.recycleView.QuizAllAdapter;
import com.example.quizletfake.recycleView.QuizCreatedAdapter;
import com.example.quizletfake.recycleView.QuizRecentAdapter;
import com.example.quizletfake.recycleView.QuizViewHolder;

import java.util.List;

public class FragmentThuVien extends Fragment {
    private QuizDao quizDao;
    private Spinner dropdown;
    private List<Quiz> quizList;
    public QuizAllAdapter adapter;
    private RecyclerView recyclerView;
    private Context context;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Xử lý nút back trên Toolbar nếu có
        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
        }
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
        //adapter=new QuizAllAdapter(quizDao, context);
        //recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    private void realCategoryList() {
    }

    private void bindingAction() {
    }

    private void bindingView(View view) {
        dropdown=view.findViewById(R.id.spinner);
        recyclerView=view.findViewById(R.id.rcvThuVienQuiz);
        //dropdown
        String[] items = new String[]{"Đã học gần đây", "Đã tạo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(((String) parent.getItemAtPosition(position)).equals("Đã tạo")){
                    QuizCreatedAdapter quizCreatedAdapter = new QuizCreatedAdapter(quizDao,context);
                    recyclerView.setAdapter(quizCreatedAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    quizCreatedAdapter.setOnBtnShowQuizClickListener(callback);
                    quizCreatedAdapter.setOnBtnEditQuizClickListener(callback2);
                }
                if(((String) parent.getItemAtPosition(position)).equals("Đã học gần đây")){
                    QuizRecentAdapter quizRecentAdapter = new QuizRecentAdapter(quizDao,context);
                    recyclerView.setAdapter(quizRecentAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    quizRecentAdapter.setOnBtnShowQuizClickListener(callback);
                    quizRecentAdapter.setOnBtnEditQuizClickListener(callback2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thu_vien, container, false);

    }

}
