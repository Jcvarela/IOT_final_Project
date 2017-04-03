package com.example.Group11.formd;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private final static int REQUEST_ENABLE_BT = 1;

    private ArrayList<BluetoothDevice> mLeDevices;
    private boolean mScanning;
    private Handler mHandler;
    private BluetoothLeScanner mLEScanner;
    private ScanSettings settings;
    private List<ScanFilter> filters;
    private BluetoothGatt mGatt;
    private int CompanyIdentifier = 0x0059;

    private TextView IdTextview;
    private TextView DistTextview;
    private TextView RSSITextview;
    private TextView RSSIonemeterTextview;
    private int ratiodb;
    private float ratiolinear;
    private float Distance;

    private static final long SCAN_PERIOD = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){

        mHandler = new Handler();
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

    }


    @Override
    protected void onResume(){
        super.onResume();

        if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        else {
            if(Build.VERSION.SDK_INT >= 21){
                mLEScanner = mBluetoothAdapter.getBluetoothLeScanner();
                settings = new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                        .build();
                filters = new ArrayList<ScanFilter>();
            }
            scanLeDevices(true);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()){
            scanLeDevices(false);
        }
    }

    @Override
    protected void onDestroy(){
        if(mGatt == null){
            return;
        }

        mGatt.close();
        mGatt= null;
        super.onDestroy();
    }

    private void scanLeDevices(final boolean enable){
        if(enable){
            mHandler.postDelayed(() -> {
                    if(Build.VERSION.SDK_INT < 21){
                        mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    }
                else {
                        mLEScanner.startScan(mScanCallback);
                    }
            },SCAN_PERIOD);

            if(Build.VERSION.SDK_INT < 21){
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
            else {
                mLEScanner.startScan(filters,settings,mScanCallback);
            }

        } else {
            if(Build.VERSION.SDK_INT < 21){
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
            else {
                mLEScanner.startScan(mScanCallback);
            }
        }
    }


    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            Log.i("Callback: ",String.valueOf(callbackType));
            Log.i("result: ",result.toString());

            BluetoothDevice btDevice = result.getDevice();
            Log.i("RSSI value is: ",String.valueOf(result.getRssi()));

            android.bluetooth.le.ScanRecord btScanRecord = result.getScanRecord();

            if(btScanRecord != null){
                byte[] data = btScanRecord.getManufacturerSpecificData(CompanyIdentifier);

                if(data != null){
                    Log.i("RSSI for 1 merer is: ", String.valueOf(data[data.length -1]));

                    //lets Calculate Distance
                    ratiodb = data[data.length -1] - result.getRssi();
                    ratiolinear = (float)Math.pow(10d ,ratiodb/10d);
                    Distance = (float) Math.sqrt(ratiolinear);


                    Log.i("Distance: ", Distance + "");

                }

            }
            Connect();
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results){
            for(ScanResult sr : results){
                Log.i("ScanResult Result: ",sr.toString());
            }
        }

        @Override
        public void onScanFailed(int errorCode){
            Log.e("Scan Failed", "Error Code: " + errorCode);
        }

    };

    private BluetoothAdapter.LeScanCallback mLeScanCallback; //= (device, rssi, scanRecord) -> {
//        runOnUiThread( ()-> {
//            Log.i("onLeScan", device.toString());
//        });
//    };


    private void Connect(){
        scanLeDevices(false);
    }
}
