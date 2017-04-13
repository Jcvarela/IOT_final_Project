package com.example.group11.formdapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.group11.formdapp.ServerAdapter.RequestAPI;
import com.example.group11.formdapp.Utilities.BluetoothLE.BluetoothLE;
import com.example.group11.formdapp.Utilities.MemoryManagment.GlobalJSON;
import com.example.group11.formdapp.Utilities.fields.FieldList;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String USER_NAME = "Jorge";
    public static final String ANDROID_ID = "Jorge";
    public static final byte[] BEACON_ID = {2, 21, 1, 2, 3, 4, 5, 6, 7, 8, 9, 16, 17, 18, 19, 20, 21, 22, 0, 15, -16, 0, -58};

    private static final String TAG = "MyMainActivity";
    private BluetoothLE b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setWelcomeScreen();

        callServer();



        b  = BluetoothLE.getBluetoothLE();

        b.startScan(this,new byte[]{2, 21, 1, 2, 3, 4, 5, 6, 7, 8, 9, 16, 17, 18, 19, 20, 21, 22, 0, 15, -16, 0, -58});
    }

    public void setWelcomeScreen(){
        TextView view = (TextView) findViewById(R.id.WelcomeText);
        view.setText("Welcome " + USER_NAME);
    }

    public void callServer(){
        GlobalJSON.setJSON();
    }




}
