package com.example.myapplication;

import static android.widget.Toast.makeText;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AirPlaneReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() == Intent.ACTION_AIRPLANE_MODE_CHANGED) {

            boolean isOnAIRPlane = intent.getBooleanExtra("state", false);
            if (isOnAIRPlane)
                Toast.makeText(context, "on Airrrrrr", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "NOT on Airrrrrr", Toast.LENGTH_SHORT).show();

        }
    }
}