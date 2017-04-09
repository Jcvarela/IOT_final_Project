package com.example.group11.formd;

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
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter; //require for any bluetooth activity (represent the device own bluetooth, there is only one, so play nice)
    private final static int REQUEST_ENABLE_BT = 1;

    private ArrayList<BluetoothDevice> mLeDevices;
    private boolean mScanning;
    private Handler mHandler; //send and process messengers
    private BluetoothLeScanner mLEScanner; //to scan for bluetooth at low energy
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
    private TextView view;

    private static final long SCAN_PERIOD = 10000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (TextView) findViewById(R.id.info);
        init();
    }

    public void init(){

        //turn off application if bluetooth_le is not supported by the device
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this,"not supported", Toast.LENGTH_SHORT).show();
            finish();
        }

        mHandler = new Handler();

        //Initialized bluetooth adapter
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    @Override
    protected void onResume(){
        super.onResume();

        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()){
            //ask user to enable bluetooth
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        else {
//            if(Build.VERSION.SDK_INT >= 21){
//                mLEScanner = mBluetoothAdapter.getBluetoothLeScanner();
//                settings = new ScanSettings.Builder()
//                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
//                        .build();
//                filters = new ArrayList<ScanFilter>();
//            }
           scanLeDevices(true);
        }
    }

    /**
     * Activity for scanning and displaying available BLE devices.
     */
    private void scanLeDevices(final boolean enable){

        if (enable) {
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);

            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }


    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    view.setText("found something");


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                }
            };

}
