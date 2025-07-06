package com.example.quizletfake.recycleView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
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
import com.example.quizletfake.model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class AEditQuestionAdapter {
        private QuestionDao questionDao;
        private Context context;
        private LayoutInflater inflater;
        private int i=0;
        private LinearLayout llAddQuestionAnswer;
        private List<AEditQuestionViewGroup> viewGroupList;
        private  int layout_add_question_item;
        private int originalSize;

    public void setLayout_add_question_item(int layout_add_question_item) {
        this.layout_add_question_item = layout_add_question_item;
    }

    public AEditQuestionAdapter(Context context, QuestionDao questionDao) {
            this.context = context;
            this.inflater=LayoutInflater.from(context);
            this.questionDao=questionDao;
            viewGroupList=new ArrayList<>();
        }

        public void setLlAddQuestionAnswer(LinearLayout llAddQuestionAnswer) {
            this.llAddQuestionAnswer = llAddQuestionAnswer;
        }

        public void addNewItem() {
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
            viewGroupList.add(new AEditQuestionViewGroup(edtQuestion,edtAnswer,btnRemove,tvPosition, 0,quizID,0));
            i++;
        }
    List<Question> questions = new ArrayList<>();
    List<Answer>answers =new ArrayList<>();
    List<Question> questionsDelete = new ArrayList<>();
    List<Answer>answersDelete =new ArrayList<>();
    List<Question> questionsAdd = new ArrayList<>();
    List<Answer>answersAdd =new ArrayList<>();
    Quiz quiz;
    int quizID;
        public void saveThisQuiz(int quizID, String title) {
            int i=0;
            for (AEditQuestionViewGroup a: viewGroupList) {
                //delete or update question
                if(i<originalSize){
                    if(a.isDeleted()==false&&a.isAccepted()){
                        questions.add(a.getQuestion());
                        answers.add(a.getAnswer());
                    }else if(a.isDeleted()==true){
                        questionsDelete.add(a.getQuestion());
                        answersDelete.add(a.getAnswer());
                    }
                }
                //add question answer
                else{
                    if(a.isDeleted()==false&&a.isAccepted()){
                        questionsAdd.add(a.getQuestion());
                        answersAdd.add(a.getAnswer());
                    }

                }
                i++;
            }
            if(questions.size()==0&&questionsAdd.size()==0){
                Toast.makeText(context,"Không được để trống Question hoặc Answer.", Toast.LENGTH_SHORT).show();
                return;
            }
            quiz=new Quiz(quizID,title, AccountNow.thisAccount.getAccountID());
            //Update
new Update().execute(1);
        }

    public void setQuizID(int quizID) {
        this.quizID=quizID;
            //nhan cac questionAnswer tu DataBase
        new DisplayQuestionsAnswers().execute(quizID);

    }
    private class DisplayQuestionsAnswers extends AsyncTask<Integer,Integer, Cursor> {

        @Override
        protected Cursor doInBackground(Integer... integers) {
            //nhan cac questionAnswer tu DataBase
            return questionDao.getAllQuestionAnswer(integers[0]);
        }

        @Override
        protected void onPostExecute(Cursor c) {
            super.onPostExecute(c);
            //add vao list cac AAddQuestionViewGroup trong
            for(int i=0;i<c.getCount();i++){
                addNewItem();
            }
            int j=0;
            //cho data vao cac AAddQuestionViewGroup do
            if (c.moveToFirst()) {
                do {
                    @SuppressLint("Range") int questionID = c.getInt(c.getColumnIndex("questionID"));
                    @SuppressLint("Range") int answerID = c.getInt(c.getColumnIndex("answerID"));
                    @SuppressLint("Range") int quizID = c.getInt(c.getColumnIndex("quizID"));
                    @SuppressLint("Range") String answerContent = c.getString(c.getColumnIndex("answerContent"));
                    @SuppressLint("Range") String questionContent = c.getString(c.getColumnIndex("questionContent"));
                    AEditQuestionViewGroup viewGroup=viewGroupList.get(j);
                    viewGroup.getTvPosition().setText(""+j);
                    viewGroup.getEdtQuestion().setText(questionContent);
                    viewGroup.setQuestionID(questionID);
                    viewGroup.getEdtAnswer().setText(answerContent);
                    viewGroup.setAnswerID(answerID);
                    viewGroup.setQuizID(quizID);
                    viewGroupList.set(j,viewGroup);
                    j++;
                } while (c.moveToNext());
            }
            originalSize=j;
        }

    /*private class EditQuizQuestionsAnswers extends AsyncTask<List<QuestionAnswerAdd>, Integer, Boolean> {

    @Override
    protected Boolean doInBackground(List<QuestionAnswerAdd>... lists) {
        String i = lists[0].get(questionAnswerAddListAfterDeletedAndSave.size()-1).getQuestionContent();
        lists[0].remove(questionAnswerAddListAfterDeletedAndSave.size()-1);
        Quiz a = new Quiz();
        a.setQuizTitle(i);
        a.setAccountID(1);
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
    }*/
}
    private class Update extends AsyncTask<Integer,Integer,Integer>{

    @Override
    protected Integer doInBackground(Integer... integers) {
        //update quiz title
questionDao.updateQuiz(quiz);
        //update questions
questionDao.updateQuestions(questions);
        //update answer
        questionDao.updateAnswers(answers);
        //delete answer
        questionDao.deleteAnswers(answersDelete);
        //delete question
        questionDao.deleteQuestions(questionsDelete);
        //add question
        List<Long> listIDOfQuesttion= questionDao.addQuestions(questionsAdd);

        //add answer
        int i=0;
        for(Answer a: answersAdd){
            a.setQuestionID(listIDOfQuesttion.get(i).intValue());
            i++;
        }
        questionDao.addAnswers(answersAdd);
        return 1;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        Toast.makeText(context,"Saved", Toast.LENGTH_SHORT).show();
    }
}
}
