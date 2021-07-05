package com.example.madcamp1_2_2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SplashActivity  extends AppCompatActivity {

    // Request code for READ_CONTACTS. It can be any number > 0.
    public static final int REQUEST_READ_CONTACTS = 79;
    public static final int REQUEST_WRITE_CONTACTS = 132;

    Activity mActivity;
    Context mContext;
    Handler handler;
    int permissionCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceStare) {
        super.onCreate(savedInstanceStare);
        setContentView(R.layout.activity_splash);

        mActivity = this;
        mContext = getApplicationContext();

        checkPermission(Manifest.permission.READ_CONTACTS, REQUEST_READ_CONTACTS);
        checkPermission(Manifest.permission.WRITE_CONTACTS, REQUEST_WRITE_CONTACTS);

    /*
        String[] s = new String[]{"android.Manifest.permission.READ_CONTACTS", "android.Manifest.permission.WRITE_CONTACTS"};
        if (!hasPermissions(this, s)) {
            requestLocationPermission();
        }
    */
        handler = new Handler();
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(mActivity, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(mActivity, new String[] { permission }, requestCode);
        }
        else {
            permissionCount += 1;
            Toast.makeText(mActivity, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }
/*
    public static boolean hasPermissions(Context context, String[] permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
*/
/*
    protected void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                android.Manifest.permission.READ_CONTACTS)) {
            Log.d("yjyj", "in requestLocationPermission if read");

            // show UI part if you want here to show some rationale !!!
        } else if (!ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                android.Manifest.permission.READ_CONTACTS)) {
            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
            Log.d("yjyj", "in requestLocationPermission else read");
        }
        else if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                android.Manifest.permission.WRITE_CONTACTS)) {
            Log.d("yjyj", "in requestLocationPermission if write");
            // show UI part if you want here to show some rationale !!!
        } else {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_CONTACTS},
                    REQUEST_WRITE_CONTACTS);
            Log.d("yjyj", "in requestLocationPermission else write");
        }
    }
*/

    public void onRequestPermissionsResult (int requestCode,
                                            String permissions[], int[] grantResults) {
        Log.d("yjyj", "In Splash Activity, onRequestPermissionsResult");

        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionCount += 1;
                    Log.d("yjyj", "In Splash Activity, case Read");
                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                break;
            }
            case REQUEST_WRITE_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionCount += 1;
                    Log.d("yjyj", "In Splash Activity, case Write");
                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                break;
            }
        }
        if(permissionCount >= 1) {
            Log.d("yjyj", "in Splash Activity, permissionCount == 2");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
            return;
        }
        else {
            Log.d("yjyj", "in SplashActivity, permissionCount != 2");
            return;
        }
    }

    @Override
    protected void onPause() {
        Log.d("yjyj", "In Splash, onPuase");
        super.onPause();
        //finish();
    }

    @Override
    protected void onStop() {
        Log.d("yjyj", "In Splash, onStop");
        super.onStop();
    }
}