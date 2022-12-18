package com.capstone.grapediseaseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    Button SignIn, SignUp;
    TextInputLayout txtinputmail, txtinputpass;
    TextInputEditText editTextmail, editTextpass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SignIn = findViewById(R.id.signinBtn);
        SignUp = findViewById(R.id.signupBtn);
        txtinputmail = findViewById(R.id.mailLayout);
        txtinputpass = findViewById(R.id.passLayout);
        editTextmail = findViewById(R.id.mailEditText);
        editTextpass = findViewById(R.id.passEditText);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextmail.getText().toString();
                String password = editTextpass.getText().toString();
                //sending data to validateEmailMethod().
                validateEmail(email,password);
            }
        });
    }

    private boolean validateEmail(String email, String password) {

        if (email.isEmpty()) {
            txtinputmail.setError("Email Address is mandatory");
            txtinputmail.requestFocus();
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtinputmail.setError("Please enter a valid email address");
            txtinputmail.requestFocus();
            return false;
        }else{
            validateUserLogin(email,password);
            return true;
        }
    }

    private boolean validateUserLogin(String email,String password){
        if (password.isEmpty()){
            txtinputpass.setError("Password can not be empty");
            txtinputpass.requestFocus();
            return false;
        }else {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            return true;
        }
    }

}