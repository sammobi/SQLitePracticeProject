package com.homestudy.sqlitepracticeproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordActivity extends Activity {

    EditText mForgotPasswordEmailEt;
    Button mSubmitbtn;

    SQLiteAdapter sqLiteAdapter;

    String email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mForgotPasswordEmailEt = (EditText) findViewById(R.id.ForgotPasswordEmailEt);
        mSubmitbtn = (Button) findViewById(R.id.btnForgotPassword);


        sqLiteAdapter = new SQLiteAdapter(this);

        mSubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = mForgotPasswordEmailEt.getText().toString().trim();

                if (!isValidEmail(email)) {
                    mForgotPasswordEmailEt.setError("Please enter valid email. ");
                }
                String password = sqLiteAdapter.getPasswordForEmail(email);

                if ( !password.equalsIgnoreCase("")) {

                    Toast.makeText(getApplicationContext(), "Your password is:" + password, Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_LONG).show();
                }


            }


        });


    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
