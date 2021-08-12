package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {

    private EditText mName, mEmail, mPassword, mPhone, mAddress;
    private Button mLoginBtn, mValidateBtn;
    boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mName = findViewById(R.id.login_name);
        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);
        mPhone = findViewById(R.id.login_phone);
        mAddress = findViewById(R.id.login_address);


        mLoginBtn = findViewById(R.id.login_btn);
        mValidateBtn = findViewById(R.id.valid_btn);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checked) {
                    Toast.makeText(LogInActivity.this, "SuccessFully Register", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LogInActivity.this, " Validate First", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void checkValidate(View view){
        if (TextUtils.isEmpty(mName.getText())) {
            mName.setError("Name required!");
        } else if (TextUtils.isEmpty(mPassword.getText())) {
            mPassword.setError("Password required!");
        } else if (TextUtils.isEmpty(mEmail.getText())) {
            mEmail.setError("Email required!");
        } else if (TextUtils.isEmpty(mPhone.getText())) {
            mPhone.setError("Phone required!");
        } else if (TextUtils.isEmpty(mAddress.getText())) {
            mAddress.setError("Address required!");
        } else {
            if(nameValidCheck() && emailValidCheck() && mobileValidCheck()){
                Toast.makeText(LogInActivity.this, " All Are Valid Fields", Toast.LENGTH_SHORT).show();
                checked = true;
            }
        }
    }

    public boolean nameValidCheck(){
        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(mName.getText().toString());
        boolean bs = ms.matches();
        if (bs == false) {
            Toast.makeText(LogInActivity.this, " Provide Valid Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public boolean emailValidCheck(){
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(mEmail.getText().toString());
        boolean bs = matcher.matches();
        if (bs == false) {
            Toast.makeText(LogInActivity.this, " Provide Valid Email", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean mobileValidCheck() {
        String phone = mPhone.getText().toString();
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            if(phone.length() > 9 && phone.length() <= 13){
                return true;
            }
        }
        Toast.makeText(LogInActivity.this, " Provide Valid Mobile Number", Toast.LENGTH_SHORT).show();
        return false;
    }


}