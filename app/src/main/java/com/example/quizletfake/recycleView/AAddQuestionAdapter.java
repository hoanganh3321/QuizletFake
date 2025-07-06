package com.example.quizletfake.recycleView;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizletfake.AccountNow;
import com.example.quizletfake.R;
import com.example.quizletfake.dao.QuestionDao;
import com.example.quizletfake.model.Answer;
import com.example.quizletfake.model.Question;
import com.example.quizletfake.model.QuestionAnswerAdd;
import com.example.quizletfake.model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class AAddQuestionAdapter {
    private QuestionDao questionDao;
    private Context context;
    private LayoutInflater inflater;
    private int i=0;
    private LinearLayout llAddQuestionAnswer;
    private List<com.example.quizletfake.recycleView.AAddQuestionViewGroup> viewGroupList;
    public AAddQuestionAdapter(Context context, QuestionDao questionDao) {
        this.context = context;
        this.inflater=LayoutInflater.from(context);
        this.questionDao=questionDao;
        viewGroupList=new ArrayList<>();
    }

    public void setLlAddQuestionAnswer(LinearLayout llAddQuestionAnswer) {
        this.llAddQuestionAnswer = llAddQuestionAnswer;
    }

    public void addNewItem(int layout_add_question_item) {
        View.inflate(context,layout_add_question_item,llAddQuestionAnswer);
        View v1 =(ViewGroup)llAddQuestionAnswer.getChildAt(i);
        EditText edtQuestion= v1.findViewById(R.id.edtQuestion1);
        EditText edtAnswer=v1.findViewById(R.id.edtAnswer1);
        Button btnRemove=v1.findViewById(R.id.btnRemove1);
        TextView tvPosition=v1.findViewById(R.id.tvPosition);
        tvPosition.setText(""+i);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPosition.setText("-1");
                v1.setVisibility(View.GONE);
            }
        });
        viewGroupList.add(new AAddQuestionViewGroup(edtQuestion,edtAnswer,btnRemove,tvPosition));
        i++;
    }
    List<QuestionAnswerAdd>questionAnswerAddListAfterDeletedAndSave;

    public void saveThisQuiz(String title) {
        questionAnswerAddListAfterDeletedAndSave=new ArrayList<>();
        for (AAddQuestionViewGroup a: viewGroupList) {
            if(a.isDeleted()==false && a.isAccepted()){
                questionAnswerAddListAfterDeletedAndSave.add(a.getQuestionAnswerAdd());
            }
        }
        if(questionAnswerAddListAfterDeletedAndSave.size()==0){
            Toast.makeText(context,"Không được để trống Question hoặc Answer.", Toast.LENGTH_SHORT).show();
            return;
        }
        questionAnswerAddListAfterDeletedAndSave.add(new QuestionAnswerAdd(1,title,"aaaaahihi"));
        new AddQuizQuestionsAnswers().execute(questionAnswerAddListAfterDeletedAndSave);
    }



    private class AddQuizQuestionsAnswers extends AsyncTask<List<QuestionAnswerAdd>, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(List<QuestionAnswerAdd>... lists) {
            String i = lists[0].get(questionAnswerAddListAfterDeletedAndSave.size()-1).getQuestionContent();
            lists[0].remove(questionAnswerAddListAfterDeletedAndSave.size()-1);
            Quiz a = new Quiz();
            a.setQuizTitle(i);
            a.setAccountID(AccountNow.thisAccount.getAccountID());
            Long id = questionDao.InsertQuiz(a);
            int id1 = id.intValue();
            List<Question>questions = new ArrayList<>();
            List<Answer>answers =new ArrayList<>();
            for(QuestionAnswerAdd a1: lists[0]){
                Question a2 = new Question(0,a1.getQuestionContent(),id1);
                questions.add(a2);
                Answer a3 = new Answer(0,a1.getAnswerContent(),-1);
                answers.add(a3);
            }
            int z=0;
            List<Long> longs = questionDao.InsertQuestions(questions);
            for(Answer a4: answers){
                a4.setQuestionID(longs.get(z).intValue());
                z++;
            }
            questionDao.InsertAnswers(answers);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
