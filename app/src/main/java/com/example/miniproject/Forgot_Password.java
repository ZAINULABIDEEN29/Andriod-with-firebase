package com.example.miniproject;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {

    private EditText emailEditText;
    private Button sendLinkButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // The EdgeToEdge call is good for modern UI, so we'll keep it.
        // EdgeToEdge.enable(this); // This line requires androidx.activity:activity:1.8.0 or higher.
        setContentView(R.layout.activity_forgot_password);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Views
        emailEditText = findViewById(R.id.email);
        sendLinkButton = findViewById(R.id.btn_send_reset_link);
        progressBar = findViewById(R.id.progressBar);

        // Set the click listener for the button
        sendLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = emailEditText.getText().toString().trim();

        // --- Input Validation ---
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

        // --- Show Progress and Send Reset Link ---
        progressBar.setVisibility(View.VISIBLE);
        sendLinkButton.setEnabled(false); // Disable button to prevent multiple clicks

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                sendLinkButton.setEnabled(true);

                if (task.isSuccessful()) {
                    Toast.makeText(Forgot_Password.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                    // Optionally, close this activity and go back to the login screen
                    finish();
                } else {
                    // Provide a user-friendly error message
                    Toast.makeText(Forgot_Password.this, "Something went wrong! Please try again.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
