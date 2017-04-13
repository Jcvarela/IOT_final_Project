package com.example.group11.formdapp.Utilities.BluetoothLE;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;


import com.example.group11.formdapp.ServerAdapter.MyRequest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by jcvar on 4/11/2017.
 */

public class BluetoothLE {

    private static final String TAG = "BluetoothLE";

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private byte[] searchCheck;

    private static BluetoothLE bluetoothLE;

    private HashSet<Beacon> tempBeacons;
    private HashSet<Beacon> beacons;


    private BluetoothLE(){
        Log.i(TAG, "class Created");

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

        beacons = new HashSet<Beacon>();
        tempBeacons = new HashSet<Beacon>();
    }

    public static BluetoothLE getBluetoothLE(){

        if(bluetoothLE == null){
            bluetoothLE = new BluetoothLE();
        }

        return bluetoothLE;
    }


    public Iterator<Beacon> getBeaconIte(){
        return beacons.iterator();
    }

    public void startScan(AppCompatActivity activity){
        this.startScan(activity,null);
    }
    public void startScan(AppCompatActivity activity, byte[] search){
        if(!locationPermissionCheck(activity)){
            Toast.makeText(activity, "The permission to get BLE location data is required", Toast.LENGTH_SHORT).show();
            return;
        }

        searchCheck = search;
        tempBeacons = new HashSet<Beacon>();
        mBluetoothLeScanner.startScan(mScanCallback);
    }


    public void stopScan(){
        mBluetoothLeScanner.stopScan(mScanCallback);
        beacons = tempBeacons;
        searchCheck = null;
    }

    private boolean locationPermissionCheck(AppCompatActivity activity){
        int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        
        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)){
                activity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }


    private void addBeaconToTemp(byte[] info, String id){
        if(info == null){
            return;
        }

        if(info != null && !Arrays.equals(info,searchCheck)) {
            return;
        }

        MyRequest.foundBeacon();
        stopScan();


        byte[] tempInfo = info.clone();

        tempBeacons.add(new Beacon(tempInfo, id));
    }


    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            SparseArray<byte[]> sparseArray = result.getScanRecord().getManufacturerSpecificData();

            byte[] info = sparseArray.get(sparseArray.keyAt(0));

            String id =  result.getDevice().toString();

            addBeaconToTemp(info, id);

            //int mRssi = result.getRssi();

            Log.i(TAG, "toString: " +  result.toString());
//            Log.i(TAG, "Device toString: " + result.getDevice().toString());
//            Log.i(TAG, "Device Uuids: " + Arrays.toString(result.getDevice().getUuids()));
//            Log.i(TAG, "Device Name: " + result.getDevice().getName());
//
//            Log.i(TAG, "Record Name: " + result.getScanRecord().getDeviceName());
//            Log.i(TAG, "Record uuids: " + result.getScanRecord().getServiceUuids());


//            Log.i(TAG,"info size " + sparseArray.size());
//            Log.i(TAG,"info size " + Arrays.toString(sparseArray.get(0)));
//            Log.i(TAG,"key at 0" + sparseArray.keyAt(0));
//            Log.i(TAG,"value of key" + Arrays.toString(sparseArray.get(76)));

//            Log.i(TAG, "Record manufacture specific Data: " + result.getScanRecord().get);
            Log.i(TAG+2, "size: " + tempBeacons.size());
        }
    };
}
