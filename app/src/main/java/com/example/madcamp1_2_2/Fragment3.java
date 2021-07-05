package com.example.madcamp1_2_2;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.telephony.CarrierConfigManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3<isLocationAvailable> extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int GPS_ENABLE_REQUEST_CODE = 100;
    private static final int REQUEST_F_LOCATION = 101;
    private static final int REQUEST_C_LOCATION = 102;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDos.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment3 newInstance(String param1, String param2) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    static Context mContext;
    Switch sw;
    boolean isSwitchOn = false;
    String address;
    boolean isLocationAvailable = false;
    GpsTracker gpsTracker;
    ViewGroup viewGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragments
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_3, container, false);
        TimePicker mTimePicker = (TimePicker) viewGroup.findViewById(R.id.timePicker);

        mContext = getContext();

        //앞서 설정한 값으로 보여주기
        //없으면 현재시간
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("daily alarm", Context.MODE_PRIVATE);
        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());
        Calendar nextNotifyTime = new GregorianCalendar();
        //nextNotifyTime.setTimeInMillis(millis);

        Date nextDate = nextNotifyTime.getTime();
        /**String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분", Locale.getDefault()).format(nextDate);
        Toast.makeText(getContext().getApplicationContext(), "[처음 실행시] 다음 알람은 " + date_text + "으로 알람이 설정되었습니다",
                Toast.LENGTH_SHORT).show();
         */

        //이전 설정값으로 TimePicker 초기화
        Date currentTime = nextNotifyTime.getTime();
        SimpleDateFormat HourFormat = new SimpleDateFormat("kk", Locale.getDefault());
        SimpleDateFormat MinuteFormat = new SimpleDateFormat("mm", Locale.getDefault());

        int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
        int pre_minute = Integer.parseInt((MinuteFormat.format(currentTime)));

        mTimePicker.setHour(pre_hour);
        mTimePicker.setMinute(pre_minute);

        /**Button button = (Button) viewGroup.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour, minute;
                hour = mTimePicker.getHour();
                minute = mTimePicker.getMinute();

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 1);
                }
                Date currentDateTime = calendar.getTime();
                String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분", Locale.getDefault()).format(currentDateTime);
                Toast.makeText(viewGroup.getContext().getApplicationContext(), date_text + "으로 알람이 설정되었습니다", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = getContext().getSharedPreferences("daily alarm", Context.MODE_PRIVATE).edit();
                editor.putLong("nextNotifyTime", (long) calendar.getTimeInMillis());
                editor.apply();

                diaryNotification(calendar);
            }
        });*/

        sw = viewGroup.findViewById(R.id.switch1);
        sw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                CheckState();
                if (isSwitchOn){
                    int hour, minute;
                    hour = mTimePicker.getHour();
                    minute = mTimePicker.getMinute();

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    if (calendar.before(Calendar.getInstance())) {
                        calendar.add(Calendar.DATE, 1);
                    }
                    Date currentDateTime = calendar.getTime();
                    String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분", Locale.getDefault()).format(currentDateTime);
                    Toast.makeText(viewGroup.getContext().getApplicationContext(), date_text + "으로 알람이 설정되었습니다", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = getContext().getSharedPreferences("daily alarm", Context.MODE_PRIVATE).edit();
                    editor.putLong("nextNotifyTime", (long) calendar.getTimeInMillis());
                    editor.apply();

                    diaryNotification(calendar);
                }
                else{
                    Toast.makeText(viewGroup.getContext().getApplicationContext(), "알람이 해제되었습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String[] s = new String[]{"android.permission.ACCESS_FINE_LOCATION"};
        if (!hasPermissions(mContext, s)) {
            requestLocationPermission();
        }

        return viewGroup;
    }


    public String getCurrentAddress(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses;
        Address address;
        try{
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    100);
            if (addresses.size() > 0) {
                address = addresses.get(0);
                return address.getAddressLine(0).toString() + "\n";
            }
            else return null;


        } catch (IOException ioException) {
            Toast.makeText(mContext, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            requestLocationPermission();
            return "지오코더 서비스 사용불가";
        }catch (IllegalArgumentException illegalArgumentException){
            Toast.makeText(mContext, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            if (!checkLocationServicesStatus(mContext)) requestLocationPermission();
            return "주소 미발견";
        }
    }

    /**private void showDialogForLocationServiceSetting(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        if (! ((Activity) mContext).isFinishing()){
            builder.create().show();

        }
    }*/

    protected void requestLocationPermission() {
        /**if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            // show UI part if you want here to show some rationale !!!
        } else {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_F_LOCATION);
        }*/
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            // show UI part if you want here to show some rationale !!!
        } else {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_F_LOCATION);
        }
    }

    public void onRequestPermissionsResult (int requestCode,
                                            String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_F_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isLocationAvailable = true;
                    gpsTracker = new GpsTracker(mContext);
                    Log.d("testtest", "after permission");

                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();

                    address = getCurrentAddress(latitude, longitude);
                    Log.d("testtest", "after getAddress");

                    TextView textview = (TextView) viewGroup.findViewById(R.id.textView);
                    textview.setText(Double.toString(latitude) + ' ' + Double.toString(longitude));
                    TextView textview2 = (TextView) viewGroup.findViewById(R.id.textView2);
                    textview2.setText(address);

                    Log.d("testtest", Double.toString(latitude) + ' ' + Double.toString(longitude));
                }
                else isLocationAvailable = false;
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**switch (requestCode){
            case GPS_ENABLE_REQUEST_CODE:
                if (checkLocationServicesStatus(mContext)) {
                    if (checkLocationServicesStatus(mContext)){
                        checkRunTimePermission();
                        return;
                    }
                }
                break;
        }*/
    }

    private boolean checkLocationServicesStatus(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isLocationEnabled();
    }

    private void CheckState(){
        if (sw.isChecked()) isSwitchOn = true;
        else isSwitchOn = false;
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

    void diaryNotification(Calendar calendar){
        Boolean dailyNotify = false;
        if (isSwitchOn) dailyNotify = true;
        else dailyNotify = false;

        PackageManager pm = mContext.getPackageManager();
        ComponentName receiver = new ComponentName(mContext, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        if (dailyNotify){
            if (alarmManager != null){
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }
            pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        }
    }
}