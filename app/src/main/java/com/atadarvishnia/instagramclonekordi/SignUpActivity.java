package com.atadarvishnia.instagramclonekordi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtEmail, edtUsername, edtPassword;
    private Button btnSignUp;
    private TextView tvGoToLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupView();

        tvGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginActivity();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                signUp();
            }
        });
    }

    private void setupView() {
        edtEmail = findViewById(R.id.edtSignUpEmail);
        edtUsername = findViewById(R.id.edtSignUpUsername);
        edtPassword = findViewById(R.id.edtSignUpPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvGoToLogin = findViewById(R.id.tvSignUpGoToLogin);
        progressBar = findViewById(R.id.pbSignUpActivity);
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void signUp() {

        if (TextUtils.isEmpty(edtEmail.getText().toString().trim()) || edtUsername.getText().toString().trim().length() == 0
                || edtPassword.getText().toString().trim().matches("")) {

            progressBar.setVisibility(View.GONE);
            alertDialogDisplay("Sign Up status...", "Please fill all fields!", true);

        } else {

            ParseUser parseUser = new ParseUser();
            parseUser.setEmail(edtEmail.getText().toString().trim());
            parseUser.setUsername(edtUsername.getText().toString().trim());
            parseUser.setPassword(edtPassword.getText().toString().trim());
            parseUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {

                    if (e != null) {
                        progressBar.setVisibility(View.GONE);
                        ParseUser.logOut();
                        alertDialogDisplay("Sign Up status...", e.getMessage(), true);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        ParseUser.logOut();
                        alertDialogDisplay("Sign Up status...", "Sign Up successful!" + "\n"
                                 + "please verify your email before login.", false);
                    }

                }
            });

        }
    }

    private void alertDialogDisplay(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (error) {
                    dialog.cancel();
                } else {
                    dialog.cancel();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}