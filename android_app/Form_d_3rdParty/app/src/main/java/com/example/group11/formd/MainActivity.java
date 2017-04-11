package com.example.group11.formd;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements BeaconConsumer{
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1 ;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;

    private static final String TAG = "MyMainActivity";
    private static final String TAG2 = "FUCK_THIS_SHIT";
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    private Button startButton;
    private Button stopButton;
    private boolean entryMessageRaised = false;
    private boolean rangingMessageRaised = false;

    private BeaconManager beaconManager;
    private Region beaconRegion = null;

    final static String ALTBEACON_LAYOUT =      "m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25";
    final static String EDDYSTONE_TLM_LAYOUT =  "x,s:0-1=feaa,m:2-2=20,d:3-3,d:4-5,d:6-7,d:8-11,d:12-15";
    final static String EDDYSTONE_UID_LAYOUT =  "s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19";
    final static String EDDYSTONE_URL_LAYOUT =  "s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-20v";
    final static String IBEACON_LAYOUT =        "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";

    private void showAlert(final String title, final String message){
        runOnUiThread(
            new Runnable() {
                @Override
                public void run() {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle(title);
                    alertDialog.setMessage(message);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which){
                                    dialog.dismiss();
                                }
                    });
                }
            }
        );
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById( R.id.stopButton);
        startButton.setOnClickListener((v)->{startBeaconMonitoring();});
        stopButton.setOnClickListener((v)->{stopBeaconMonitoring();});

       // requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1234);

        beaconManager = BeaconManager.getInstanceForApplication(this);
        //beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(IBEACON_LAYOUT));
        beaconManager.bind(this);

        //other shit
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        setScanSettings();

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(this, "The permission to get BLE location data is required", Toast.LENGTH_SHORT).show();
            }else{
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }else{
            Toast.makeText(this, "Location permissions already granted", Toast.LENGTH_SHORT).show();
        }

        mBluetoothLeScanner.startScan(mScanCallback);
//        mBluetoothLeScanner.startScan(null, mScanSettings, mScanCallback);
        Log.i(TAG2, "Start: " + isLocationEnabled(this.getApplicationContext()) + " permission " + permissionCheck);
    }

    //More Shit ===================================================================================
    protected ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            ScanRecord mScanRecord = result.getScanRecord();
            byte[] manufacturerData = mScanRecord.getManufacturerSpecificData(224);
            int mRssi = result.getRssi();

            Log.i(TAG2, "toString: " +  result.toString());
            Log.i(TAG2, "Uuids: " + result.getDevice().getUuids());
            Log.i(TAG2, "Name: " + result.getDevice().getName());
            Log.i(TAG2, "Device: " + result.getDevice().toString());
            Log.i(TAG2, "ScanRecord by: " + Arrays.toString(result.getScanRecord().getBytes()));
            Log.i(TAG2, "mRssi: " + mRssi + "\n\n\n");
        }
    };

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }


    private ScanSettings mScanSettings;

    private void setScanSettings() {
        ScanSettings.Builder mBuilder = new ScanSettings.Builder();
        mBuilder.setReportDelay(0);
        mBuilder.setScanMode(ScanSettings.SCAN_MODE_LOW_POWER);
        mScanSettings = mBuilder.build();
    }
    //============================================================================================


    @Override
    public void onBeaconServiceConnect() {
        Log.i(TAG, "onBeaconServicesConnect called");

        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.i(TAG, "I just saw a beacon for the first time!");
               /* if(!entryMessageRaised){
                    showAlert("didExitRegion", "Exiting region " + region.getUniqueId() +
                    " Beacon detected UUID/major/minor: " + region.getId1() +
                    "/" + region.getId2() + "/" + region.getId3());

                    entryMessageRaised = true;
                }*/

            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(TAG, "I no longer see a beacon");
               /* if(!entryMessageRaised){
                    showAlert("didExitRegion", "Exiting region " + region.getUniqueId() +
                            " Beacon detected UUID/major/minor: " + region.getId1() +
                            "/" + region.getId2() + "/" + region.getId3());

                    entryMessageRaised = true;
                }*/
            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {
                Log.i(TAG, "I have just switched from seeing/not seeing beacons: "+ i);
                /*Not implemented*/
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
        } catch (RemoteException e) {    }

        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                Log.i(TAG, "inside range");

                if (beacons.size() > 0) {
                    Log.i(TAG, "The first beacon I see is about "+beacons.iterator().next().getDistance()+" meters away.");
                }

//                if(!rangingMessageRaised && beacons != null && !beacons.isEmpty()){
//                    for(Beacon beacon: beacons){
//                        Log.i(TAG, "The first beacon I see is about "+beacons.iterator().next().getDistance()+" meters away.");
//                        showAlert("didExitRegion", "Exiting region " + region.getUniqueId() +
//                                " Beacon detected UUID/major/minor: " + region.getId1() +
//                                "/" + region.getId2() + "/" + region.getId3());
//                    }
//                    rangingMessageRaised = true;
//                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }


    private void startBeaconMonitoring(){
        Log.i(TAG,"start button press");

        try{
            beaconRegion = new Region("MyBeacons", Identifier.parse("00000000-0000-0000-0000-000000000009"),
                    Identifier.parse("ffff"), Identifier.parse("2445"));

            beaconManager.startMonitoringBeaconsInRegion(beaconRegion);
            beaconManager.startRangingBeaconsInRegion(beaconRegion);
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }

    private void stopBeaconMonitoring(){
        Log.i(TAG,"stop button press");
        try{
            beaconManager.stopMonitoringBeaconsInRegion(beaconRegion);
            beaconManager.stopRangingBeaconsInRegion(beaconRegion);
        } catch(RemoteException e){
            e.printStackTrace();
        }
    }
}
