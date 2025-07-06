package com.example.quizletfake;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.Calendar;

public class FragmentHoSo extends Fragment {


    private EditText name,email,dob,pass,repass;

    private Button btnSignEdit;
    private AccountDao accountDao;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    private ArrayAdapter<Account> adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BindingView(view);
        BindAction();
        dob.addTextChangedListener(new TextWatcher() {
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
                    dob.setText(current);
                    dob.setSelection(sel < current.length() ? sel : current.length());


                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        BindData();
    }

    private void BindData() {
        Account thisAccountLoggedIn = AccountNow.thisAccount;
        name.setText(thisAccountLoggedIn.getAccountName());
        email.setText(thisAccountLoggedIn.getAccountEmail());
        dob.setText(thisAccountLoggedIn.getAccountDOB());
    }

    void BindingView(View v){
        name = v.findViewById(R.id.edtNameEdit);
        email = v.findViewById(R.id.edtEmailEdit);
        dob = v.findViewById(R.id.edtDobEdit);
        pass = v.findViewById(R.id.edtPassEdit);
        repass = v.findViewById(R.id.edtRepassEdit);
        btnSignEdit = v.findViewById(R.id.btnSignEdit);
    }
    void BindAction(){
        btnSignEdit.setOnClickListener(this::onBtnSignEditClick);
    }

    private void onBtnSignEditClick(View view) {
        if(pass.getText().toString().trim().equals(repass.getText().toString())==false){
            Toast.makeText(this.getContext(), "Password and Re-Password not ", Toast.LENGTH_SHORT).show();
        }else {
        Account a = AccountNow.thisAccount;
        a.setAccountName(name.getText().toString());
        a.setAccountEmail(email.getText().toString());
        a.setAccountDOB(dob.getText().toString());
        a.setAccountPassword(pass.getText().toString());
       new UpdateAccount().execute(a);}

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ho_so, container, false);
    }
    private class UpdateAccount extends AsyncTask<Account, Integer, Integer> {
        @Override
        protected Integer doInBackground(Account... accounts) {
             accountDao.Update(accounts[0]);
             return 1;
        }

        @Override
        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);
            if(i>=0){
                Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
