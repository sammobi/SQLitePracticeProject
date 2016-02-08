package com.homestudy.sqlitepracticeproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    EditText mUsernameEt, mPasswordEt;
    Button mLoginBtn, mSignupBtn;
    TextView mForgotPasswordTv;
    String username, name, email, password;

    SQLiteAdapter sqLiteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteAdapter = new SQLiteAdapter(this);


        mUsernameEt = (EditText) findViewById(R.id.UsernameEt);
        mPasswordEt = (EditText) findViewById(R.id.PasswordEt);
        mLoginBtn = (Button) findViewById(R.id.LoginBtn);
        mSignupBtn = (Button) findViewById(R.id.SignupBtn);
        mForgotPasswordTv = (TextView) findViewById(R.id.forgotPasswordTv);

        sqLiteAdapter.insertOrUpdateUser(email, username, name, password);
        boolean isUserExist = sqLiteAdapter.isUserExist(email, password);
        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);

                startActivity(intent);


            }
        });


    }

}
