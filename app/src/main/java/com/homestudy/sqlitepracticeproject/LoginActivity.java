package com.homestudy.sqlitepracticeproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends Activity {

    EditText mUsernameEt, mPasswordEt;
    Button mLoginBtn, mSignupBtn;
    TextView mForgotPasswordTv;
    String username, name, email, password;

    SQLiteAdapter sqLiteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        File f = new File(Environment.getExternalStorageDirectory() + "/com.homestudy.sqlitepracticeproject/");
        if (!f.exists()) {
            f.mkdir();
        }

        sqLiteAdapter = new SQLiteAdapter(this);


        mUsernameEt = (EditText) findViewById(R.id.UsernameEt);
        mPasswordEt = (EditText) findViewById(R.id.PasswordEt);
        mLoginBtn = (Button) findViewById(R.id.LoginBtn);
        mSignupBtn = (Button) findViewById(R.id.SignupBtn);
        mForgotPasswordTv = (TextView) findViewById(R.id.forgotPasswordTv);


        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });

        mForgotPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(intent);*/

            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = mUsernameEt.getText().toString();
                password = mPasswordEt.getText().toString();


                if (!isValidUsername(username)) {
                    mUsernameEt.setError("Invalid Email");
                }


                if (!isValidPassword(password)) {
                    mPasswordEt.setError("Invalid Password");
                }

                final boolean isUserExist = sqLiteAdapter.isUserExist(username, password);

                if (isUserExist == true) {

                    Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    // validating email id
    private boolean isValidUsername(String username) {
        if (username != null && username.length() > 8) {
            return true;
        }
        return false;
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }
}
