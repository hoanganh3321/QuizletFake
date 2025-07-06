package com.example.quizletfake;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizletfake.dao.AccountDao;
import com.example.quizletfake.model.Account;

import java.util.Calendar;
import java.util.regex.Pattern;

public class FragmentSignUp extends Fragment {
    private EditText name,emaila,doba,pass,repass;

    private ListView listView;
    private Button btnSignUp;
    private AccountDao accountDao;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }
    public void setAccountDao(AccountDao accountDao){
        this.accountDao=accountDao;
    }
    @Nullable
    @Override
    public Context getContext() {
        return context;
    }
    private String email="";
    void BindingView(View view){

        name = view.findViewById(R.id.edtName);
        emaila = view.findViewById(R.id.edtEmail);
        doba = view.findViewById(R.id.edtDob);
        pass = view.findViewById(R.id.edtPass);
        repass = view.findViewById(R.id.edtRepass);
        btnSignUp = view.findViewById(R.id.btnSign);
        listView =  view.findViewById(R.id.listAcount);

    }
    void BindAction(){
        btnSignUp.setOnClickListener(this::SignUp);
    }

    private void SignUp(View view) {

                String username = name.getText().toString().trim();
                email = emaila.getText().toString().trim();
                String dob = doba.getText().toString().trim();
                String password = pass.getText().toString().trim();
        Account account = new Account( username, password, email, dob);

                Pattern patternAccAndPass = Pattern.compile("^\\w{8,32}$");
                Pattern patternEmail = Pattern.compile("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");


                if (dob.equals("") || email.equals("") || username.equals("") || password.equals("")) {
                    Toast.makeText(this.getContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                    if (!patternAccAndPass.matcher(username).find()) {
                        Toast.makeText(this.getContext(), "Username not valid character length from 8-32", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(pass.getText().toString().trim().equals(repass.getText().toString())==false){
                        Toast.makeText(this.getContext(), "Password and Re-Password are not the same", Toast.LENGTH_SHORT).show();
                    }

                    else if (!patternAccAndPass.matcher(password).find()) {
                        Toast.makeText(this.getContext(), "Password not valid", Toast.LENGTH_SHORT).show();
                        return;
                    }
                   else if (!patternEmail.matcher(email).find()) {
                        Toast.makeText(this.getContext(), "Email not valid", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        new InsertAccount().execute(account);
        };

    }
    private class InsertAccount extends AsyncTask<Account, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Account... accounts) {
            Cursor c = accountDao.GetEmail(email);
            if(c.getCount()>0){
                return false;
            }else{
                accountDao.Insert(accounts[0]);
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if(success){
                Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"Email duplicate",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BindingView(view);
        BindAction();

        doba.addTextChangedListener(new TextWatcher() {
            private final String ddmmyyyy = "DDMMYYYY";
            private final Calendar cal = Calendar.getInstance();
            private String current = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);

                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    doba.setText(current);
                    doba.setSelection(sel < current.length() ? sel : current.length());

//clone from https://stackoverflow.com/questions/16889502/how-to-mask-an-edittext-to-show-the-dd-mm-yyyy-date-format
                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View result =inflater.inflate(R.layout.fragment_sign_up, container, false);

        return result;
    }
}