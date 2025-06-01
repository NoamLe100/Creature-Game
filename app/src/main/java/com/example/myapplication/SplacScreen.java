package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Process;
import android.widget.ProgressBar;

public class SplacScreen extends AppCompatActivity {
    private ProgressBar progressBar;
    private int progreesStatus = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splac_screen);
    }
}