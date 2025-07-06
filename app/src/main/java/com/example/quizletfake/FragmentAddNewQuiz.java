package com.example.quizletfake;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizletfake.recycleView.AAddQuestionAdapter;
import com.example.quizletfake.dao.QuestionDao;
import com.example.quizletfake.model.QuestionAnswerAdd;

import java.util.List;

public class FragmentAddNewQuiz extends Fragment {

    private Button btnSaveQuiz;
    private Button btnAddQuestionAnswer;
    private EditText edtQuizTitle;
    private RecyclerView recyclerView;
    private LinearLayout llAddQuestionAnswer;
    private Context context;
    private QuestionDao questionDao;
    public void setQuestionDao(QuestionDao questionDao){
        this.questionDao=questionDao;
    }
    //AddQuestionAdapter addQuestionAdapter;
    AAddQuestionAdapter aaddQuestionAdapter;
    List<QuestionAnswerAdd> questionAnswerAddList;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView(view);
        bindingAction();
        //initRecycleView();
        initLinearLayout();
    }

    private void initLinearLayout() {
        aaddQuestionAdapter=new AAddQuestionAdapter(context,questionDao);
        aaddQuestionAdapter.setLlAddQuestionAnswer(llAddQuestionAnswer);
    }

    /*private void initRecycleView() {
        addQuestionAdapter=new AddQuestionAdapter(context,questionDao);
        addQuestionAdapter.setQuestionAnswerAddList(new ArrayList<>());
        recyclerView.setAdapter(addQuestionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }*/

    private void bindingAction() {
        btnAddQuestionAnswer.setOnClickListener(this::btnAddNewItem);
        btnSaveQuiz.setOnClickListener(this::btnSaveQuizClick);
    }

    private void btnSaveQuizClick(View view) {
        String title = edtQuizTitle.getText().toString().trim();
        if(title.length()==0){
            Toast.makeText(context, "Quiz title không thể để trống", Toast.LENGTH_SHORT).show();
        }else{
            aaddQuestionAdapter.saveThisQuiz(title);
            //addQuestionAdapter.saveThisQuiz(title);
        }

    }

    private void btnAddNewItem(View view) {

        //addQuestionAdapter.addNewItem();
        aaddQuestionAdapter.addNewItem(R.layout.layout_add_question_item);

    }

    private void bindingView(View view) {
        llAddQuestionAnswer=view.findViewById(R.id.llAddQuestionAnswer);
        //recyclerView=view.findViewById(R.id.rcvAddQuestionAnswer);
        btnSaveQuiz=view.findViewById(R.id.btnSaveQuizAdd);
        btnAddQuestionAnswer=view.findViewById(R.id.btnAddNewItem);
        edtQuizTitle=view.findViewById(R.id.edtQuizTitle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_quiz_hai, container, false);
    }

    public void setContext(Context mainActivity) {
        this.context=mainActivity;
    }
}
