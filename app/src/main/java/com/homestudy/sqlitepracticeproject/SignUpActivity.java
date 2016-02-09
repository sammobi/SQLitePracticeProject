package com.homestudy.sqlitepracticeproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends Activity {

    EditText mSignupUsernameEt, mNameEt, mSignupPasswordEt, mConfirmPasswordEt, mEmailEt;

    Button mSignupBtn;
    SQLiteAdapter sqLiteAdapter;

    String username, name, email, password, confirmpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mSignupUsernameEt = (EditText) findViewById(R.id.signupUserNameEt);
        mNameEt = (EditText) findViewById(R.id.nameEt);

        mSignupPasswordEt = (EditText) findViewById(R.id.signupPasswordEt);

        mConfirmPasswordEt = (EditText) findViewById(R.id.confirmPasswordEt);
        mEmailEt = (EditText) findViewById(R.id.emailEt);
        mSignupBtn = (Button) findViewById(R.id.btnsignup);

        sqLiteAdapter = new SQLiteAdapter(this);


        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = mEmailEt.getText().toString();
                username = mSignupUsernameEt.getText().toString();
                name = mNameEt.getText().toString();
                password = mSignupPasswordEt.getText().toString();
                confirmpassword = mConfirmPasswordEt.getText().toString();

                if (!isValidUsername(username)) {
                    mSignupUsernameEt.setError("Please enter a username more than 8 characters. ");

                    return;
                } else if (!isValidName(name)) {
                    mNameEt.setError("Please enter your name");
                    return;
                } else if (!isValidPassword(password)) {
                    mSignupPasswordEt.setError("Please enter a password more than 8 characters. ");

                    return;
                } else if (!isValidConfirmPassword(confirmpassword)) {
                    mConfirmPasswordEt.setError("Please enter a password more than 8 characters.");
                    return;
                } else if (!password.equals(confirmpassword)) {

                    mConfirmPasswordEt.setError("Please check your password combination ");
                    return;

                } else if (!isValidEmail(email)) {
                    mEmailEt.setError("Invalid email address");
                    return;

                } else {
                    long l = sqLiteAdapter.insertOrUpdateUser(email, username, name, password);
                    if (l > 0) {

                        Toast.makeText(getApplicationContext(), "Successfully Signed Up", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Signup Unsuccessful", Toast.LENGTH_LONG).show();

                    }

                }


            }
        });


    }


    private boolean isValidUsername(String username) {
        if (username != null && username.length() >= 8) {
            return true;
        }
        return false;
    }

    private boolean isValidName(String name) {
        if (name != null) {
            return true;
        }
        return false;
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    // validating password with retype password
    private boolean isValidPassword(String password) {
        if (password != null && password.length() > 6) {
            return true;
        } else {
            return false;
        }

    }

    // validating password with retype password
    private boolean isValidConfirmPassword(String confirmpassword) {
        if (confirmpassword != null && confirmpassword.length() > 6) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPassWordAndConfirmPassword(String password, String confirmpassword) {
        boolean pstatus = false;
        if (confirmpassword != null && password != null && confirmpassword.equals(password)) {
            if (confirmpassword.equals(password)) {
                pstatus = true;
            }
        }

        return pstatus;
    }


}
