package com.example.group11.formdapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.group11.formdapp.Utilities.BluetoothLE.BluetoothLE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyMainActivity";

    private BluetoothLE b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b  = BluetoothLE.getBluetoothLE();

        b.startScan(this,new byte[]{2, 21, 1, 2, 3, 4, 5, 6, 7, 8, 9, 16, 17, 18, 19, 20, 21, 22, 0, 15, -16, 0, -58});
    }

}
