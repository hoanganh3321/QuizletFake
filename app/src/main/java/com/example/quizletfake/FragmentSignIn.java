package com.example.quizletfake;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizletfake.dao.AccountDao;
import com.example.quizletfake.model.Account;

import java.util.ArrayList;
import java.util.List;


public class FragmentSignIn extends Fragment {
    private EditText name, pass;
    private Button btnLog;
    private AccountDao accountDao;
    private ArrayAdapter<Account> adapter;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }
    public void setAccountDao(AccountDao accountDao){
        this.accountDao=accountDao;
    }
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    void BindingView(View view){
        name = view.findViewById(R.id.edtNameLog);
        pass = view.findViewById(R.id.edtPasslLog);

        btnLog = view.findViewById(R.id.btnLog);

    }
    void BindAction(){

        btnLog.setOnClickListener(this::LogIn);
    }


    private void LogIn(View view) {
        List<String> infor= new ArrayList<>();
        infor.add( name.getText().toString().trim());
        infor.add( pass.getText().toString().trim());
        //dung Asyntask
        new GetAccount().execute(infor);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      try {
          pref = context.getSharedPreferences("MyPref", context.MODE_PRIVATE);
          editor = pref.edit();
      }catch (Exception e) {
      }
      if(pref!=null){
    //kiem tra preferent xem co tai khoan hay chua
        if(isAccountNotLogOut()){
            //neu co tk roi thi dat tk vao AccountNow
            AccountNow.thisAccount = getAccountFromPreference();
            //chuyen sang MainActivity  and finish
            if (callback != null) {
                callback.onSuccess();
            }
        }else{
            //neu chua co
            BindingView(view);
            BindAction();
        }}
    }

    private Account getAccountFromPreference() {
        int accountID = pref.getInt("accountID",-1);
        String accountName = pref.getString("accountName", null);
        String accountPassword = pref.getString("accountPassword", null);
        String accountEmail = pref.getString("accountEmail", null);
        String accountDOB = pref.getString("accountDOB", null);
        return new Account(accountID,accountName,accountPassword,accountEmail,accountDOB);
    }

    private boolean isAccountNotLogOut() {
        int accountID = pref.getInt("accountID", -1);
        if (accountID==-1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }
    private class GetAccount extends AsyncTask<List<String>, Integer, Boolean> {


        @Override
        protected Boolean doInBackground(List<String>... lists) {
            Account account =accountDao.GetAccount(lists[0].get(0),lists[0].get(1));
            if(account!=null){
                //set account
                editor.clear();
                editor.putInt("accountID", account.getAccountID());
                editor.putString("accountName", account.getAccountName());
                editor.putString("accountPassword", account.getAccountPassword());
                editor.putString("accountEmail", account.getAccountEmail());
                editor.putString("accountDOB", account.getAccountDOB());
                editor.commit();
                AccountNow.thisAccount=account;
                return true;
            }else{
                return false;
            }


        }

        @Override
        protected void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if(bool){
                if (callback != null) {
                    callback.onSuccess();
                }
            }else{
                Toast.makeText(context,"Login thất bại",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public interface OnLoginSuccess {
        void onSuccess();
    }

    private OnLoginSuccess callback;
    public void setOnLoginSuccessListener(OnLoginSuccess callback) {
        this.callback = callback;
    }
}