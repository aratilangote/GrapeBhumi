package com.capstone.grapediseaseapp;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class SignupActivity extends AppCompatActivity {

    Button btnSignup, btnSignin;
    TextInputLayout txtinputname, txtinputphone, txtinputmail, txtinputpass, txtinputconfirmpass, txtinputaddress;
    TextInputEditText nameEditText, phoneEditText, mailEditText, passEditText, confirmpassEditText, addressEditText;
    ProgressBar progressBar;

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    //  String userId;

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
        mailEditText = findViewById(R.id.mailEditTextId);
        passEditText = findViewById(R.id.passEditTextId);
        confirmpassEditText = findViewById(R.id.confirmPassEditTextId);
        addressEditText = findViewById(R.id.addressEditTextId);

        btnSignup = findViewById(R.id.btn_signup);
        btnSignin = findViewById(R.id.btn_signin);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        btnSignin.setOnClickListener(view -> startActivity(new Intent(SignupActivity.this, LoginActivity.class)));
        btnSignup.setOnClickListener(view -> {
            String name = nameEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();
            String email = mailEditText.getText().toString().trim();
            String pass = passEditText.getText().toString().trim();
            String conpass = confirmpassEditText.getText().toString().trim();
            String address = addressEditText.getText().toString().trim();
            progressBar.setVisibility(View.VISIBLE);

            Map<String, Object> user = new HashMap<>();
            user.put("Name", name);
            user.put("Phone No.", phone);
            user.put("Email", email);
            user.put("Password", pass);
            user.put("Confirm password", conpass);
            user.put("Address", address);

            // Add a new document with a generated ID
            fstore.collection("user")
                    .document(user.get("Email").toString().split("@")[0])
                    .set(user);
            fstore.collection("user")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                    //open user profile after successful registration
                                    Intent intent = new Intent(SignupActivity.this, HomeActivity.class);

                                    //To prevent user from returning back to sign up activity on pressing back button after registration
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Toast.makeText(SignupActivity.this, "Account successfully created!", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        });
    }

    private boolean validation(String name, String phone, String email, String pass, String conpass, String address) {
        if (TextUtils.isEmpty(name)) {
            txtinputname.setError("Please enter your name");
            txtinputname.requestFocus();
        } else if (TextUtils.isEmpty(phone)) {
            txtinputname.setError("Please enter your mobile number");
            txtinputphone.requestFocus();
        } else if (phone.length() != 10) {
            txtinputphone.setError("Mobile number should be 10 digits.");
            txtinputphone.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            txtinputmail.setError("Email can not be empty.");
            txtinputmail.requestFocus();
        } else if (!email.matches("[a-z0-9._-]+@[a-z]+\\\\.+[a-z]+\"]")) {
            txtinputmail.setError("Please enter a re-enter your email address");
            txtinputmail.requestFocus();
        } else if (TextUtils.isEmpty(pass)) {
            txtinputpass.setError("Password can not be empty.");
            txtinputpass.requestFocus();
        } else if (pass.length() < 6) {
            txtinputpass.setError("Password must be greater than 6 characters");
            txtinputpass.requestFocus();
        } else if (TextUtils.isEmpty(conpass)) {
            txtinputconfirmpass.setError("Please confirm your password");
            txtinputconfirmpass.requestFocus();
        } else if (TextUtils.isEmpty(address)) {
            txtinputaddress.setError("Address can not be empty.");
            txtinputaddress.requestFocus();
        } else {
            return true;
        }
        return false;
    }

}