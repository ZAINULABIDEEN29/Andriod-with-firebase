package com.example.miniproject;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {

    private EditText emailEditText;
    private Button sendLinkButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        mAuth = FirebaseAuth.getInstance();


        emailEditText = findViewById(R.id.email);
        sendLinkButton = findViewById(R.id.btn_send_reset_link);
        progressBar = findViewById(R.id.progressBar);


        sendLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = emailEditText.getText().toString().trim();


        if (email.isEmpty()) {
            emailEditText.setError("Email is required!");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please provide a valid email!");
            emailEditText.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        sendLinkButton.setEnabled(false);

        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()

                .setUrl("https://miniproject.page.link/resetPassword")

                .setHandleCodeInApp(true)
                .setAndroidPackageName(
                        getPackageName(),
                        true,
                        "1"
                )
                .build();


        mAuth.sendPasswordResetEmail(email, actionCodeSettings)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        sendLinkButton.setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(Forgot_Password.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {

                            Exception e = task.getException();
                            android.util.Log.e("ForgotPassword", "Email send failed", e);
                            Toast.makeText(Forgot_Password.this, "Something went wrong! Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
