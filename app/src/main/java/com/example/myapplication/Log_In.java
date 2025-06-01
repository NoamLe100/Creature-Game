package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Log_In extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mAuth = FirebaseAuth.getInstance();


        register("noam@gmail.com", "123456");
    }
    public  void register(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login successful, update UI with the logged-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToStartScreen(); // פונקציה שתעביר לעמוד הבית
                        } else {
                            // If login fails, display a message to the user
                            Toast.makeText(Log_In.this, "Login failed. Please check your credentials.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void goToStartScreen()
    {
        Intent intent = new Intent(Log_In.this,StartScreen.class);
        startActivity(intent);
    }
}