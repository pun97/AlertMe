package com.example.pun.alertme;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import static android.bluetooth.BluetoothDevice.ACTION_ACL_CONNECTED;
import static android.content.Intent.ACTION_POWER_CONNECTED;
import static android.content.Intent.ACTION_POWER_DISCONNECTED;
import static android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int myBuildVersion = Build.VERSION.SDK_INT;
        if (myBuildVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 1);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(), "NEED PERMISSIONS FOR WORKING", Toast.LENGTH_LONG).show();
                } else {
                    MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
                    this.registerReceiver(myBroadCastReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
                    this.registerReceiver(myBroadCastReceiver, new IntentFilter("android.provider.Telephony.SMS_READ"));
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
