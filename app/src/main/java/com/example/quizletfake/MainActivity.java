package com.example.quizletfake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentContainerView;

import com.example.quizletfake.dao.AccountDao;
import com.example.quizletfake.dao.AppDatabase;
import com.example.quizletfake.dao.QuestionDao;
import com.example.quizletfake.dao.QuizAccountDao;
import com.example.quizletfake.dao.QuizDao;
import com.example.quizletfake.model.Account;
import com.example.quizletfake.model.QuestionAnswerDisplay;
import com.example.quizletfake.model.QuizAccount;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private QuizDao quizDao;
    private QuestionDao questionDao;
    private AccountDao accountDao;
    private Button btnTrangChu;
    private Button btnThuVien;
    private Button btnHoSo;
    private Button btnThongKe;
    private FragmentContainerView fragmentContainerView;
    private FragmentTrangChu fragmentTrangChu;
    private FragmentThuVien fragmentThuVien;
    private FragmentHoSo fragmentHoSo;
    private FragmentDetailQuiz fragmentDetailQuiz;
    private FragmentFlashcard fragmentFlashcard;
    private FragmentLearn fragmentLearn;
    private FragmentAddNewQuiz fragmentAddNewQuiz;
    private FragmentEditQuiz fragmentEditQuiz;
    private FragmentThongKe fragmentThongKe;
    private ConstraintLayout cl;
    private QuizAccount quizAccount;
    private QuizAccountDao quizAccountDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Kiểm tra xem đã đăng nhập chưa
        if (AccountNow.thisAccount == null) {
            restoreLoginFromSharedPreferences();
        }

        if (AccountNow.thisAccount == null) {
            // Nếu vẫn null thì chuyển về màn hình đăng nhập
            Intent intent = new Intent(this, LandingActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bindingView();
        bindingAction();
        realQuizList();
    }

    private void restoreLoginFromSharedPreferences() {
        SharedPreferences pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        int accountId = pref.getInt("account_id", -1);
        if (accountId != -1) {
            // Đọc lại tài khoản từ CSDL
            AppDatabase db = AppDatabase.getInstance(this);
            AccountDao accountDao = db.accountDao();
            Account account = accountDao.getAccountById(accountId); // ⚠️ nếu dùng Room với LiveData, cần dùng AsyncTask
            AccountNow.thisAccount = account;
        }
    }
    private void realQuizList() {

    }

    private void bindingAction() {
        btnTrangChu.setOnClickListener(this::onBtnTrangChuClick);
        btnThuVien.setOnClickListener(this::onBtnThuVienClick);
        btnHoSo.setOnClickListener(this::onBtnHoSoClick);
        btnThongKe.setOnClickListener(this::onBtnThongKeClick);
        cl.setOnClickListener(this::layoutClick);
    }
    private void layoutClick(View view) {
        //hide Android soft keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void onBtnHoSoClick(View view) {
        if (fragmentHoSo == null) {
            fragmentHoSo = new FragmentHoSo();
        }
        fragmentHoSo.setContext(this);
        fragmentHoSo.setDao(accountDao);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragmentHoSo)
                .addToBackStack(null)
                .commit();
    }

    private void onBtnThuVienClick(View view) {
        if (fragmentThuVien == null) {
            fragmentThuVien = new FragmentThuVien();
        }
        fragmentThuVien.setQuizDao(quizDao);
        fragmentThuVien.setContext(this);
        fragmentThuVien.setOnBtnEditQuizClickListener(this::onBtnEditQuizClick);
        fragmentThuVien.setOnBtnShowQuizClickListener(this::onBtnShowQuizClick);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragmentThuVien)
                .addToBackStack(null)
                .commit();
    }

    private void onBtnShowQuizClick(int quizId) {
        if (AccountNow.thisAccount == null) {
            Toast.makeText(this, "Bạn chưa đăng nhập. Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LandingActivity.class));
            finish();
            return;
        }
        // Ghi lịch sử truy cập quiz
        quizAccount.setQuizID(quizId);
        quizAccount.setAccountID(AccountNow.thisAccount.getAccountID());
        quizAccount.setLastTimeJoin(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        new AddQuizToHistory().execute(quizAccountDao);

        // Hiển thị quiz
        if (fragmentDetailQuiz == null) {
            fragmentDetailQuiz = new FragmentDetailQuiz();
        }
        fragmentDetailQuiz.setQuestionDao(questionDao);
        fragmentDetailQuiz.setContext(this);
        fragmentDetailQuiz.setQuizID(quizId);
        fragmentDetailQuiz.setOnBtnLearnFlashCardClickListener(this::onBtnLearnFlashCardClick);
        fragmentDetailQuiz.setOnBtnLearnClickListener(this::onBtnLearnClick);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragmentDetailQuiz)
                .addToBackStack(null)
                .commit();

    }

    private void onBtnLearnClick(List<QuestionAnswerDisplay> questionAnswerDisplays) {
        //replace fragment
        if (fragmentLearn == null) {
            fragmentLearn = new FragmentLearn();
        }
        fragmentLearn.setQuestionAnswerDisplayList(questionAnswerDisplays);
        // Lấy quizId từ fragmentDetailQuiz nếu có
        int quizId = 0;
        if (fragmentDetailQuiz != null) {
            try {
                java.lang.reflect.Field f = fragmentDetailQuiz.getClass().getDeclaredField("quizID");
                f.setAccessible(true);
                quizId = f.getInt(fragmentDetailQuiz);
            } catch (Exception e) { quizId = 0; }
        }
        fragmentLearn.setQuizId(quizId);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragmentLearn)
                .addToBackStack(null)
                .commit();
    }

    private void onBtnLearnFlashCardClick(List<QuestionAnswerDisplay> questionAnswerDisplays) {
        //replace fragment
        if (fragmentFlashcard == null) {
            fragmentFlashcard = new FragmentFlashcard();
        }
        fragmentFlashcard.setContext(this);
        fragmentFlashcard.setQuestionAnswerDisplays(questionAnswerDisplays);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragmentFlashcard)
                .addToBackStack(null)
                .commit();
    }


    private void onBtnTrangChuClick(View view) {
        if (fragmentTrangChu == null) {
            fragmentTrangChu = new FragmentTrangChu();
        }
        fragmentTrangChu.setQuizDao(quizDao);
        fragmentTrangChu.setContext(this);
        fragmentTrangChu.setOnBtnEditQuizClickListener(this::onBtnEditQuizClick);
        fragmentTrangChu.setOnBtnShowQuizClickListener(this::onBtnShowQuizClick);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragmentTrangChu)
                .addToBackStack(null)
                .commit();
    }

    private void bindingView() {
        btnTrangChu=findViewById(R.id.btnTrangChu);
        btnThuVien=findViewById(R.id.btnThuVien);
        btnHoSo=findViewById(R.id.btnHoSo);
        fragmentContainerView=findViewById(R.id.fragmentContainerView);
        cl=findViewById(R.id.constraintLayout);
        quizAccount=new QuizAccount();
        btnThongKe=findViewById(R.id.btnThongKe);

        //DAO
        AppDatabase db = AppDatabase.getInstance(this);
        quizDao=db.quizDao();
        quizAccountDao=db.quizAccountDao();
        questionDao=db.questionDao();
        accountDao=db.accountDao();
        //them fragment trang chu luc mo activity
        if (fragmentTrangChu == null) {
            fragmentTrangChu = new FragmentTrangChu();
        }
        fragmentTrangChu.setQuizDao(quizDao);
        fragmentTrangChu.setContext(this);
        fragmentTrangChu.setOnBtnEditQuizClickListener(this::onBtnEditQuizClick);
        fragmentTrangChu.setOnBtnShowQuizClickListener(this::onBtnShowQuizClick);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragmentTrangChu)
                .addToBackStack(null)
                .commit();
    }

    private void onBtnThongKeClick(View view) {
        if (fragmentThongKe == null) {
            fragmentThongKe = new FragmentThongKe();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragmentThongKe)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuuu, menu);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.optSearch).getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //adapter.changeList(query);
                fragmentTrangChu.changeAdapter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.resetList();
                fragmentTrangChu.resetAdapter();
                return false;
            }
        });
        searchView.setOnSearchClickListener(this::onSearchClick);
        return super.onCreateOptionsMenu(menu);
    }

    private void onSearchClick(View view) {
        if (fragmentTrangChu == null) {
            fragmentTrangChu = new FragmentTrangChu();
        }
        fragmentTrangChu.setQuizDao(quizDao);
        fragmentTrangChu.setContext(this);
        fragmentTrangChu.setOnBtnEditQuizClickListener(this::onBtnEditQuizClick);
        fragmentTrangChu.setOnBtnShowQuizClickListener(this::onBtnShowQuizClick);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragmentTrangChu)
                .addToBackStack(null)
                .commit();
    }

    private void onBtnEditQuizClick(int quizID, String quizTitle) {
        if (fragmentEditQuiz == null) {
            fragmentEditQuiz = new FragmentEditQuiz();
        }
        fragmentEditQuiz.setContext(this);
        fragmentEditQuiz.setQuestionDao(questionDao);
        fragmentEditQuiz.setQuiz(quizID,quizTitle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragmentEditQuiz)
                .addToBackStack(null)
                .commit();

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.optClose){
            finish();
        }
        if(itemId==R.id.optAddNewQuiz){
            if (fragmentAddNewQuiz == null) {
                fragmentAddNewQuiz = new FragmentAddNewQuiz();
            }
            fragmentAddNewQuiz.setContext(this);
            fragmentAddNewQuiz.setQuestionDao(questionDao);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, fragmentAddNewQuiz)
                    .addToBackStack(null)
                    .commit();

        }
        if(itemId==R.id.optLogOut){
            AccountNow.thisAccount=null;
            getSharedPreferences("MyPref", MODE_PRIVATE).edit().clear().commit();
            //open login activity
            Intent intentToLandingActivity = new Intent(this,LandingActivity.class);
            this.startActivity(intentToLandingActivity);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private class AddQuizToHistory extends AsyncTask<QuizAccountDao, Integer, Boolean> {


        @Override
        protected Boolean doInBackground(QuizAccountDao... quizAccountDaos) {
            quizAccountDaos[0].insertQuizAccount(quizAccount);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){

            }else{

            }

        }
    }
}