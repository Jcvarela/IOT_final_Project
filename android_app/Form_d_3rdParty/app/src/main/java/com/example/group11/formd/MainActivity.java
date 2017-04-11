package com.example.group11.formd;

import android.Manifest;
import android.content.DialogInterface;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class MainActivity extends AppCompatActivity implements BeaconConsumer{

    private static final String TAG = "MainActivity";

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


        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1234);

        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(IBEACON_LAYOUT));
        beaconManager.bind(this);
    }



    @Override
    public void onBeaconServiceConnect() {
        Log.i(TAG, "onBeaconServicesConnect called");

        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                if(!entryMessageRaised){
                    showAlert("didExitRegion", "Exiting region " + region.getUniqueId() +
                    " Beacon detected UUID/major/minor: " + region.getId1() +
                    "/" + region.getId2() + "/" + region.getId3());

                    entryMessageRaised = true;
                }

            }

            @Override
            public void didExitRegion(Region region) {
                if(!entryMessageRaised){
                    showAlert("didExitRegion", "Exiting region " + region.getUniqueId() +
                            " Beacon detected UUID/major/minor: " + region.getId1() +
                            "/" + region.getId2() + "/" + region.getId3());

                    entryMessageRaised = true;
                }
            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {
                /*Not implemented*/
            }
        });

        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if(!rangingMessageRaised && beacons != null && !beacons.isEmpty()){
                    for(Beacon beacon: beacons){
                        showAlert("didExitRegion", "Exiting region " + region.getUniqueId() +
                                " Beacon detected UUID/major/minor: " + region.getId1() +
                                "/" + region.getId2() + "/" + region.getId3());
                    }
                    rangingMessageRaised = true;
                }
            }
        });
    }


    private void startBeaconMonitoring(){
        Log.i(TAG,"start button press");
        try{
            beaconRegion = new Region("MyBeacons", Identifier.parse("00000000000000000000000000000000"),
                    Identifier.parse("4"), Identifier.parse("200"));

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
