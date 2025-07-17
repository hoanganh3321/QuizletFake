package com.example.quizletfake;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizletfake.model.QuestionAnswerDisplay;
import com.example.quizletfake.model.StudySession;
import com.example.quizletfake.model.StudySessionPrefs;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FragmentLearn extends Fragment {
    private int rightAnswer;
    private String realRightAnswer;
    private int soCauDung=0;
    private int numberOfQuestion;
    private int i=0;
    private Button btnAnswer0;
    private Button btnAnswer1;
    private Button btnAnswer2;
    private Button btnAnswer3;
    private Button btnContinue;
    private TextView tvLearnQuestion;
    private TextView tvCompliment;
    private TextView tvResult;
    private List<QuestionAnswerDisplay> questionAnswerDisplayList;
    Random r ;
    private int quizId; // Thêm biến này

    public void setQuestionAnswerDisplayList(List<QuestionAnswerDisplay> questionAnswerDisplayList) {
        this.questionAnswerDisplayList = questionAnswerDisplayList;
    }

    public void setQuizId(int quizId) { this.quizId = quizId; }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        r = new Random();
        bindingView(view);
        bindingAction();

        initFirst();

    }

    private void initFirst() {
        i=0;
        tvResult.setEnabled(false);
        tvResult.setVisibility(View.GONE);
        soCauDung=0;
        addData();
    }

    private void addData() {

        btnContinue.setEnabled(false);
        btnContinue.setVisibility(View.GONE);
        tvCompliment.setEnabled(false);
        tvCompliment.setVisibility(View.GONE);
        rightAnswer=(int)Math.floor(Math.random()*3.5);
        realRightAnswer=questionAnswerDisplayList.get(i).getAnswerContent();
        if(rightAnswer==0){
            btnAnswer0.setText(questionAnswerDisplayList.get(i).getAnswerContent());
            btnAnswer1.setText(questionAnswerDisplayList.get(r.nextInt((numberOfQuestion-1-0)+1)+0).getAnswerContent());
            btnAnswer2.setText(questionAnswerDisplayList.get(r.nextInt((numberOfQuestion-1-0)+1)+0).getAnswerContent());
            btnAnswer3.setText(questionAnswerDisplayList.get(r.nextInt((numberOfQuestion-1-0)+1)+0).getAnswerContent());
        }else if(rightAnswer==1){
            btnAnswer1.setText(questionAnswerDisplayList.get(i).getAnswerContent());
            btnAnswer0.setText(questionAnswerDisplayList.get(r.nextInt((numberOfQuestion-1-0)+1)+0).getAnswerContent());
            btnAnswer2.setText(questionAnswerDisplayList.get(r.nextInt((numberOfQuestion-1-0)+1)+0).getAnswerContent());
            btnAnswer3.setText(questionAnswerDisplayList.get(r.nextInt((numberOfQuestion-1-0)+1)+0).getAnswerContent());
        }
        else if(rightAnswer==2){
            btnAnswer2.setText(questionAnswerDisplayList.get(i).getAnswerContent());
            btnAnswer1.setText(questionAnswerDisplayList.get(r.nextInt((numberOfQuestion-1-0)+1)+0).getAnswerContent());
            btnAnswer0.setText(questionAnswerDisplayList.get(r.nextInt((numberOfQuestion-1-0)+1)+0).getAnswerContent());
            btnAnswer3.setText(questionAnswerDisplayList.get(r.nextInt((numberOfQuestion-1-0)+1)+0).getAnswerContent());
        }
        else if(rightAnswer==3){
            btnAnswer3.setText(questionAnswerDisplayList.get(i).getAnswerContent());
            btnAnswer1.setText(questionAnswerDisplayList.get(r.nextInt((numberOfQuestion-1-0)+1)+0).getAnswerContent());
            btnAnswer2.setText(questionAnswerDisplayList.get(r.nextInt((numberOfQuestion-1-0)+1)+0).getAnswerContent());
            btnAnswer0.setText(questionAnswerDisplayList.get(r.nextInt((numberOfQuestion-1-0)+1)+0).getAnswerContent());
        }
        tvLearnQuestion.setText(questionAnswerDisplayList.get(i).getQuestionContent());
    }

    private void bindingAction() {
        btnAnswer0.setOnClickListener(this::onBtnAnswer0Click);
        btnAnswer1.setOnClickListener(this::onBtnAnswer1Click);
        btnAnswer2.setOnClickListener(this::onBtnAnswer2Click);
        btnAnswer3.setOnClickListener(this::onBtnAnswer3Click);
        btnContinue.setOnClickListener(this::onBtnContinueClick);
    }

    private void onBtnContinueClick(View view) {
        i++;
        if(i==questionAnswerDisplayList.size()){
            btnAnswer0.setVisibility(View.GONE);
            btnAnswer1.setVisibility(View.GONE);
            btnAnswer2.setVisibility(View.GONE);
            btnAnswer3.setVisibility(View.GONE);
            tvLearnQuestion.setVisibility(View.GONE);
            btnAnswer0.setEnabled(false);
            btnAnswer1.setEnabled(false);
            btnAnswer2.setEnabled(false);
            btnAnswer3.setEnabled(false);
            tvLearnQuestion.setVisibility(View.GONE);
            btnContinue.setEnabled(false);
            btnContinue.setVisibility(View.GONE);
            tvCompliment.setEnabled(false);
            tvCompliment.setVisibility(View.GONE);
            result();
        }else{
            enableAnswerBtn();
            addData();
        }

    }

    private void result() {
        float percent1 = (soCauDung*100f)/numberOfQuestion;
        int percent=Math.round(percent1);
        if(percent<70){
            tvResult.setText("Cố gắng lên nhé, bạn làm đúng "+percent+" %");
        }
        if(percent>=70 &&percent<=85){
            tvResult.setText("Giỏi quá đi, bạn làm đúng "+percent+" %");
        }
        if(percent>85&&percent<=95){
            tvResult.setText("Bạn học siêu ghê, bạn làm đúng "+percent+" %");
        }
        if(percent>95){
            tvResult.setText("Hoàn hảo, bạn làm đúng "+percent+" %");
        }
        tvResult.setEnabled(true);
        tvResult.setVisibility(View.VISIBLE);

        // Lưu kết quả học vào StudySessionPrefs
        int accountId = AccountNow.thisAccount != null ? AccountNow.thisAccount.getAccountID() : 0;
        int quizId = this.quizId;
        int correctCount = soCauDung;
        int totalCount = numberOfQuestion;
        long durationMillis = 0; // Nếu có thể tính thời gian làm bài thì truyền vào, tạm để 0
        StudySession session = new StudySession(0, accountId, quizId, new Date(), correctCount, totalCount, durationMillis);
        StudySessionPrefs.saveSession(requireContext(), session);
    }

    private void onBtnAnswer3Click(View view) {
        if(btnAnswer3.getText().toString().equals(realRightAnswer)){
            dungRoi();
        }else{
            saiRoi();
        }
        disableAnswerBtn();
    }

    private void onBtnAnswer2Click(View view) {
        if(btnAnswer2.getText().toString().equals(realRightAnswer)){
            dungRoi();
        }else{
            saiRoi();
        }
        disableAnswerBtn();
    }

    private void onBtnAnswer1Click(View view) {
        if(btnAnswer1.getText().toString().equals(realRightAnswer)){
            dungRoi();
        }else{
            saiRoi();
        }
        disableAnswerBtn();
    }

    private void onBtnAnswer0Click(View view) {
        if(btnAnswer0.getText().toString().equals(realRightAnswer)){
            dungRoi();
        }else{
            saiRoi();
        }
        disableAnswerBtn();
    }

    private void disableAnswerBtn() {
        btnAnswer0.setEnabled(false);
        btnAnswer1.setEnabled(false);
        btnAnswer2.setEnabled(false);
        btnAnswer3.setEnabled(false);
    }
    private void enableAnswerBtn() {
        btnAnswer0.setEnabled(true);
        btnAnswer1.setEnabled(true);
        btnAnswer2.setEnabled(true);
        btnAnswer3.setEnabled(true);
    }

    private void saiRoi() {
        btnContinue.setBackgroundColor(Color.parseColor("#C90537"));
        tvCompliment.setTextColor(Color.parseColor("#C90537"));
        tvCompliment.setBackgroundColor(Color.parseColor("#EEB0B3"));
        tvCompliment.setTextSize(14);

        tvCompliment.setText("Không chính xác\nTrả lời đúng: \n"+realRightAnswer);
        btnContinue.setText("Đã hiểu");
        btnContinue.setVisibility(View.VISIBLE);
        tvCompliment.setVisibility(View.VISIBLE);
        btnContinue.setEnabled(true);
        tvCompliment.setEnabled(true);
    }

    private void dungRoi() {
        soCauDung++;
        btnContinue.setBackgroundColor(Color.parseColor("#0B972A"));
        tvCompliment.setTextColor(Color.parseColor("#0B972A"));
        tvCompliment.setBackgroundColor(Color.parseColor("#93DF8A"));
        tvCompliment.setTextSize(20);

        int y = r.nextInt((6-1)+1)+1;
        if(y == 1){
            tvCompliment.setText("Bạn giỏi quá!");
        }
        if(y == 2){
            tvCompliment.setText("Hoàn hảo!");
        }
        if(y == 3){
            tvCompliment.setText("Hoan hô!");
        }
        if(y == 4){
            tvCompliment.setText("Chính xác!");
        }
        if(y == 5){
            tvCompliment.setText("Làm tốt lắm!");
        }
        if(y == 6){
            tvCompliment.setText("Tuyệt vời!");
        }
        btnContinue.setText("Tiếp tục");
        btnContinue.setVisibility(View.VISIBLE);
        tvCompliment.setVisibility(View.VISIBLE);
        btnContinue.setEnabled(true);
        tvCompliment.setEnabled(true);
    }

    private void bindingView(View view) {
        btnAnswer0=view.findViewById(R.id.btnAnswer0);
        btnAnswer1=view.findViewById(R.id.btnAnswer1);
        btnAnswer2=view.findViewById(R.id.btnAnswer2);
        btnAnswer3=view.findViewById(R.id.btnAnswer3);
        btnContinue=view.findViewById(R.id.btnContinue);
        tvLearnQuestion=view.findViewById(R.id.tvLearnQuestion);
        tvCompliment=view.findViewById(R.id.tvCompliment);
        numberOfQuestion=questionAnswerDisplayList.size();
        tvResult=view.findViewById(R.id.tvResult);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn, container, false);
    }
}