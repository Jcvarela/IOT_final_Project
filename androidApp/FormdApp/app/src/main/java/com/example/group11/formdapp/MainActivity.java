package com.example.group11.formdapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.group11.formdapp.ServerAdapter.RequestAPI;
import com.example.group11.formdapp.Utilities.BluetoothLE.BluetoothLE;
import com.example.group11.formdapp.Utilities.MemoryManagment.GlobalJSON;
import com.example.group11.formdapp.Utilities.fields.FieldList;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static final String USER_NAME = "Jorge";
    public static final String ANDROID_ID = "Jorge";
    public static final byte[] BEACON_ID = {2, 21, 1, 2, 3, 4, 5, 6, 7, 8, 9, 16, 17, 18, 19, 20, 21, 22, 0, 15, -16, 0, -58};

    private static final String TAG = "MyMainActivity";
    private BluetoothLE b;

    private boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setWelcomeScreen();

        callServer();

        addBluetoothLE();

        playWithTime();
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(check){
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    public void setWelcomeScreen(){
        TextView view = (TextView) findViewById(R.id.WelcomeText);
        view.setText("Welcome " + USER_NAME);
    }

    public void callServer(){
        GlobalJSON.setJSON();
    }

    public void addBluetoothLE(){
        b  = BluetoothLE.getBluetoothLE();

        b.startScan(this,BEACON_ID);
    }

    int delay;
    public void playWithTime(){

        delay = 500 +(int)(Math.random()*1200);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        ProgressBar view;
                        view = (ProgressBar) findViewById(R.id.progressBar);
                        view.setVisibility(View.VISIBLE);
                    }
                }, delay);
            }
        });

        delay += 1000 + (int)(Math.random()*3000 - delay*0.5);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        switchActivity();
                        check = true;
                    }
                }, delay);
            }
        });
    }

    public void switchActivity(){
        Intent intent = new Intent(getApplicationContext(), IntroPage.class);
        startActivity(intent);
    }
}
