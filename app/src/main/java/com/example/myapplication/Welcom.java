package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Welcom  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
    public  void  goToSingUp(View view)
    {
        Intent intent = new Intent(Welcom.this,SignInActivity.class);
        startActivity(intent);
    }
    public void goToLogin(View view)
    {
        Intent intent = new Intent(Welcom.this, Log_In.class);
        startActivity(intent);
    }

}
