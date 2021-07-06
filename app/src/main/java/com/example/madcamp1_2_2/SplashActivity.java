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
    private static final int REQUEST_F_LOCATION = 101;

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
        handler = new Handler();

        String[] s = new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS"};
        if(hasPermissions(mContext, s)) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("yjyj", "in postdelay");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        }


        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_F_LOCATION);
        Log.d("yjyj", "count is" + permissionCount);
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(mActivity, new String[] { permission }, requestCode);
        }
        else {
            permissionCount++;
        }
    }

    public void onRequestPermissionsResult (int requestCode,
                                            String permissions[], int[] grantResults) {
        Log.d("yjyj", "In Splash Activity, onRequestPermissionsResult");
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("yjyj", "In Splash Activity, case Read");
                    permissionCount++;
                    checkPermission(Manifest.permission.WRITE_CONTACTS, REQUEST_WRITE_CONTACTS);
                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                break;
            }
            case REQUEST_WRITE_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionCount++;
                    if(permissionCount >= 3) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("yjyj", "in postdelay");
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 1000);
                    }
                    Log.d("yjyj", "In Splash Activity, case Write");
                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                break;
             }
            case REQUEST_F_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionCount++;
                    checkPermission(Manifest.permission.READ_CONTACTS, REQUEST_READ_CONTACTS);
                    Log.d("yjyj", "In Splash Activity, case Location");
                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                break;
            }
        }
    }

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


    @Override
    public void onPause() {
        Log.d("yjyj", "In Splash, onPuase");
        super.onPause();
        //finish();
    }

    @Override
    public void onStop() {
        Log.d("yjyj", "In Splash, onStop");
        super.onStop();
    }
}