package com.example.zbn.lightcontroller;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

public class MainActivity extends AppCompatActivity implements BeaconConsumer {
    public TextView tvName;
    public Button btnLamp1, btnLamp2, btnLamp3, btnAll;

    //public Controller controller;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private BeaconManager beaconManager;
    protected static final String TAG = "MonitoringActivity";
    private boolean beacon1,beacon2,beacon3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }

        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);


        tvName = findViewById(R.id.tvName);
        btnLamp1 = findViewById(R.id.btnLamp1);
        btnLamp2 = findViewById(R.id.btnLamp2);
        btnLamp3 = findViewById(R.id.btnLamp3);
        btnAll = findViewById(R.id.btnAll);

        btnLamp1.setOnClickListener(new Lamp1Listener());
        btnLamp2.setOnClickListener(new Lamp2Listener());
        btnLamp3.setOnClickListener(new Lamp3Listener());
        btnAll.setOnClickListener(new LampAllListener());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("info beacon", "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });
                    builder.show();
                }
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {

                // Log.i(TAG, "I just saw an beacon for the first time!" + region.getId1() + region.getUniqueId() );

                if(region.getUniqueId() == "B1"){
                    setBeacon1(true);
                } else if( region.getUniqueId() == "B2"){
                    setBeacon2(true);
                } else if(region.getUniqueId() == "B3") {
                    setBeacon3(true);
                }
                Log.i(TAG, "ON - Beacon1 = " + getBeacon1() + ", Beacon2 = " + getBeacon2() + ", Beacon3 = " +getBeacon3());
            }

            @Override
            public void didExitRegion(Region region) {
                //Log.i(TAG, "I no longer see "+  region.getId1());

                if(region.getUniqueId() == "B1"){
                    setBeacon1(false);
                } else if( region.getUniqueId() == "B2"){
                    setBeacon2(false);
                } else if(region.getUniqueId() == "B3") {
                    setBeacon3(false);
                }

                Log.i(TAG, "OFF - Beacon1 = " + getBeacon1() + ", Beacon2 = " + getBeacon2() + ", Beacon3 = " +getBeacon3());
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                //  Log.i(TAG, "I have just switched from seeing/not seeing beacons: "+state);
            }
        });

        Identifier id1 = Identifier.parse("c59581c6-8b5c-4066-ad35-3f6d363de6dd");
        Identifier id2 = Identifier.parse("c3d4f8aa-c8ae-4a2b-8f38-856f90b3a856");
        Identifier id3 = Identifier.parse("b120d266-4b49-4d4f-b948-37da6b0a63a2");
        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("B1", id1, null, null));
            beaconManager.startMonitoringBeaconsInRegion(new Region("B2", id2, null, null));
            beaconManager.startMonitoringBeaconsInRegion(new Region("B3", id3, null, null));
            // beaconManager.startMonitoringBeaconsInRegion(new Region("NULL", null, null, null));
        } catch (RemoteException e) {
            Log.i(TAG, e.toString());
        }
    }

    private void setBeacon1(boolean b) {
        this.beacon1 = b;
    }

    public boolean getBeacon1() {
        return beacon1;
    }

    private void setBeacon2(boolean b) {
        this.beacon2 = b;
    }

    public boolean getBeacon2() {
        return beacon2;
    }

    private void setBeacon3(boolean b) {
        this.beacon3 = b;
    }

    public boolean getBeacon3() {
        return beacon3;
    }

    public void lampAllClick(View view) {
        Intent intent = new Intent(this, LampAll.class);
        startActivity(intent);
    }

    public void lamp1Click(View view) {
        Intent intent = new Intent(this, Lamp1Activity.class);
        startActivity(intent);
    }

    public void lamp2Click(View view) {
        Intent intent = new Intent(this, Lamp2Activity.class);
        startActivity(intent);
    }

    public void lamp3Click(View view) {
        Intent intent = new Intent(this, Lamp3Activity.class);
        startActivity(intent);
    }

    private class LampAllListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            lampAllClick(view);
        }
    }

    private class Lamp1Listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            lamp1Click(view);
        }
    }

    private class Lamp2Listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            lamp2Click(view);
        }
    }

    private class Lamp3Listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            lamp3Click(view);
        }
    }
}
