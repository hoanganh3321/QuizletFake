package com.example.quizletfake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizletfake.dao.AccountDao;
import com.example.quizletfake.dao.AppDatabase;

public class LandingActivity extends AppCompatActivity {
    private Button btnsignup, btnsignin,btnForgotAccount;
    private FragmentSignIn fragmentSignIn;
    private  FragmentSignUp fragmentSignUp;
    private FragmentForgotNamePassword fragmentForgotNamePassword;
    private AccountDao accountDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        BindingView();
        BindingAction();
        if (fragmentSignIn == null) {
            fragmentSignIn = new FragmentSignIn();
        }
        fragmentSignIn.setAccountDao(accountDao);
        fragmentSignIn.setContext(this);
        fragmentSignIn.setOnLoginSuccessListener(this::OnLoginSuccess);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentForSignInAndSignUp, fragmentSignIn)
                .commit();
    }

    void BindingView(){
        btnsignin = findViewById(R.id.btnLogIn);
        btnsignup =  findViewById(R.id.btnSignUp);
        btnForgotAccount=  findViewById(R.id.btnForgotAccount);
        AppDatabase db =AppDatabase.getInstance(this);
        accountDao=db.accountDao();

    }
    void BindingAction(){
        btnsignin.setOnClickListener(this::ToLogin);
       btnsignup.setOnClickListener(this::ToSignUp);
        btnForgotAccount.setOnClickListener(this::btnForgotClick);
    }

    private void btnForgotClick(View view) {
        if (fragmentForgotNamePassword == null) {
            fragmentForgotNamePassword = new FragmentForgotNamePassword();
        }
        fragmentForgotNamePassword.setAccountDao(accountDao);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentForSignInAndSignUp, fragmentForgotNamePassword)
                .commit();
    }

    private void ToLogin(View view) {
        if (fragmentSignIn == null) {
            fragmentSignIn = new FragmentSignIn();
        }
        fragmentSignIn.setAccountDao(accountDao);
        fragmentSignIn.setContext(this);
        fragmentSignIn.setOnLoginSuccessListener(this::OnLoginSuccess);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentForSignInAndSignUp, fragmentSignIn)
                .commit();
    }

    private void OnLoginSuccess() {
        Intent intentToMainActivity = new Intent(this, MainActivity.class);
        //Toast.makeText(this,"Login thành công",Toast.LENGTH_SHORT).show();

        startActivity(intentToMainActivity);
        finish();
    }

    private void ToSignUp(View view) {
        if (fragmentSignUp == null) {
            fragmentSignUp = new FragmentSignUp();
        }
        fragmentSignUp.setAccountDao(accountDao);
        fragmentSignUp.setContext(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentForSignInAndSignUp, fragmentSignUp)
                .commit();
    }
}