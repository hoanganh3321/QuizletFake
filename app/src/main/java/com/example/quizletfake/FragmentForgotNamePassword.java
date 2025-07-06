package com.example.quizletfake;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizletfake.dao.AccountDao;
import com.example.quizletfake.model.Account;

public class FragmentForgotNamePassword extends Fragment {

    private AccountDao accountDao;
    private TextView textView9;
    private EditText mail;
    private Button getP;

   public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    private void GetPassword(View view) {
        getAccount();
    }

    private void getAccount() {
        new AAccount().execute(mail.getText().toString());
    }

    private class AAccount extends AsyncTask<String, Integer, Account> {
        @Override
        protected Account doInBackground(String... integers) {
            return accountDao.GetAccountByMail(integers[0]);

        }

        @Override
        protected void onPostExecute(Account accounts) {
            super.onPostExecute(accounts);
            if (accounts != null) {
                //           Toast.makeText(this, "Your Password is" + signup.getAccountPassword(), Toast.LENGTH_SHORT).show();
                textView9.setText("Your Username is: "+accounts.getAccountName()+"" +
                        "\n Your Password is:"+accounts.getAccountPassword());
                textView9.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mail = view.findViewById(R.id.editTextText);
        getP = view.findViewById(R.id.button);
        textView9 = view.findViewById(R.id.textView7);
        getP.setOnClickListener(this::GetPassword);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_name_password, container, false);
    }
}