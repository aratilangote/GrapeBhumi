package com.capstone.grapediseaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
//import android.util.Patterns;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    Button btnSignup, btnSignin;
    TextInputLayout txtinputname, txtinputphone, txtinputmail, txtinputpass, txtinputconfirmpass, txtinputaddress;
    TextInputEditText nameEditText, phoneEditText, mailEditText, passEditText, confirmpassEditText, addressEditText;
    ProgressBar progressBar;

    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressBar = findViewById(R.id.progressbar1);

        txtinputname = findViewById(R.id.nameLayoutId);
        txtinputphone = findViewById(R.id.phoneLayoutId);
        txtinputmail = findViewById(R.id.mailLayoutId);
        txtinputpass = findViewById(R.id.passLayoutId);
        txtinputconfirmpass = findViewById(R.id.conPassLayoutId);
        txtinputaddress = findViewById(R.id.addressLayoutId);

        nameEditText = findViewById(R.id.nameEditextId);
        phoneEditText = findViewById(R.id.phoneEditTextId);
        mailEditText = findViewById(R.id.phoneEditTextId);
        passEditText = findViewById(R.id.passEditTextId);
        confirmpassEditText = findViewById(R.id.phoneEditTextId);
        addressEditText = findViewById(R.id.addressEditTextId);

        btnSignup = findViewById(R.id.btn_signup);
        btnSignin = findViewById(R.id.btn_signin);

        btnSignin.setOnClickListener(view -> startActivity(new Intent(SignupActivity.this, LoginActivity.class)));
        btnSignup.setOnClickListener(view -> {
           String name = nameEditText.getText().toString();
           String phone = phoneEditText.getText().toString();
           String mail = mailEditText.getText().toString();
           String pass = passEditText.getText().toString();
           String conpass = confirmpassEditText.getText().toString();
           String address = addressEditText.getText().toString();
           progressBar.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(name)){
                txtinputname.setError("Please enter your name");
                txtinputname.requestFocus();
            }else if(TextUtils.isEmpty(phone)){
                txtinputname.setError("Please enter your mobile number");
                txtinputphone.requestFocus();
            }else if(phone.length() != 10){
                txtinputphone.setError("Mobile number should be 10 digits.");
                txtinputphone.requestFocus();
            }else if(TextUtils.isEmpty(mail)){
                txtinputmail.setError("Email can not be empty.");
                txtinputmail.requestFocus();
            }
//            else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
//                txtinputmail.setError("Please enter a re-enter your email address");
//                txtinputmail.requestFocus();
//            }
            else if(TextUtils.isEmpty(pass)){
                txtinputpass.setError("Password can not be empty.");
                txtinputpass.requestFocus();
            }else if(TextUtils.isEmpty(conpass)){
                txtinputconfirmpass.setError("Please confirm your password");
                txtinputconfirmpass.requestFocus();
            }else if(TextUtils.isEmpty(address)){
                txtinputaddress.setError("Address can not be empty.");
                txtinputaddress.requestFocus();
            } else{
                userSignup(name, phone, mail, pass, conpass, address);
            }
        });
    }

    private void userSignup(final String name, final String phone, final String mail, final String pass, final String conpass, final String address ){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "Account successfully created!", Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    //send verification mail
                    firebaseUser.sendEmailVerification();

                    //open user profile after successful registration
                    Intent intent = new Intent(SignupActivity.this, HomeActivity.class);

                    //To prevent user from returning back to sign up activity on pressing back button after registration
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}