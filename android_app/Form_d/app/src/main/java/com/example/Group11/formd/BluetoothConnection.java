package com.example.Group11.formd;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;

/**
 * Created by jcvar on 4/1/2017.
 */

public class BluetoothConnection {

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager bluetoothManager;



    private BluetoothConnection conneciton;
    private BluetoothConnection(){

        // Initializes Bluetooth adapter.
//        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

    }

    public BluetoothConnection getBluetoothCOnnection(){
        if(conneciton == null){
            conneciton = new BluetoothConnection();
        }
        return conneciton;
    }

}
