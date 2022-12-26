package com.capstone.grapediseaseapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button SignIn, SignUp;
    TextInputLayout txtinputmail, txtinputpass;
    TextInputEditText editTextmail, editTextpass;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressbar2);

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
                String email = editTextmail.getText().toString().trim();
                String password = editTextpass.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);
                //sending data to validateEmailMethod().
                //validateEmail(email, password);

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                AlertDialog alertDialog = builder.create();

                db.collection("user")
                        .document(email.split("@")[0])
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            String pass = "";
                            try {
//                                pass = task.getResult().get("password").toString();
                                pass = Objects.requireNonNull(documentSnapshot.get("Password")).toString();
                            } catch (Exception ex) {
                                System.out.println("Exception occurred!");
                            }
                            if (pass.equals(password)) {
                                CurrentProfile.email = documentSnapshot.get("Email").toString();
                                progressBar.setVisibility(View.VISIBLE);
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            } else {
                                SignIn.setEnabled(true);
                                //progress.setVisibility(View.INVISIBLE);
                                // loginEmail.setText(null);
                                // loginPassword.setText(null);

                                builder.setMessage("Your account password is wrong. Please try again.");
                                builder.setTitle("Wrong Password");
                                builder.setCancelable(true);

                                alertDialog.show();
                            }
                        }).addOnFailureListener(e -> {
                            Toast.makeText(LoginActivity.this, "Account not found.", Toast.LENGTH_SHORT).show();
                            if (alertDialog.isShowing())
                                alertDialog.dismiss();
                        });
                ;
            }

        });
    }

    private boolean validateEmail(String email, String password) {

        if (email.isEmpty()) {
            txtinputmail.setError("Email Address is mandatory");
            txtinputmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtinputmail.setError("Please enter a valid email address");
            txtinputmail.requestFocus();
            return false;
        } else {
            validateUserLogin(email, password);
            return true;
        }
    }

    private boolean validateUserLogin(String email, String password) {
        if (password.isEmpty()) {
            txtinputpass.setError("Password can not be empty");
            txtinputpass.requestFocus();
            return false;
        } else {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            return true;
        }
    }

}