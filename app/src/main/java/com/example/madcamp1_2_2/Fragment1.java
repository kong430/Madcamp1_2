package com.example.madcamp1_2_2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.example.madcamp1_2_2.MainActivity.REQUEST_READ_CONTACTS;
import static com.example.madcamp1_2_2.MainActivity.REQUEST_WRITE_CONTACTS;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment {
    public static final int REQUEST_CODE_PLUS = 101;

    Context mContext;
    Activity mActivity;
    static Contact contact = new Contact();
    ViewGroup v;
    ListView listView;
    Fragment mFragment = this;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment1() {
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
    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("DEBUGmkmk", "in onCreate");
        Log.d("DEBUGmk", "onCreate of LoginFragment");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity().getApplicationContext();
        Log.d("DEBUGmkmk", "in onCreateView");

        String[] s = new String[]{"android.Manifest.permission.READ_CONTACTS", "android.Manifest.permission.WRITE_CONTACTS"};
        if (hasPermissions(mContext, s))  {
            contact.getContacts(mContext);
        } else {
            requestLocationPermission();
        }

        Log.d("DEBUGmk", "onCreateView of LoginFragment");
        v = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);

        listView = v.findViewById(R.id.list);

        ContactAdapter adapter = new ContactAdapter(mContext, R.layout.row, contact.datas);
        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlusActivity.class);
                startActivityForResult(intent, REQUEST_CODE_PLUS);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.detach(mFragment);
            fragmentTransaction.attach(mFragment);
            fragmentTransaction.commit();

            Log.d("DEBUGmkmk", "in onactivityresult requestcode, ");
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

    protected void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                android.Manifest.permission.READ_CONTACTS)) {
            // show UI part if you want here to show some rationale !!!
        } else {
            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                android.Manifest.permission.WRITE_CONTACTS)) {
            // show UI part if you want here to show some rationale !!!
        } else {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_CONTACTS},
                    REQUEST_WRITE_CONTACTS);
        }
    }

    public void onRequestPermissionsResult (int requestCode,
    String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    contact.getContacts(mContext);
                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }


    private class ContactAdapter extends ArrayAdapter<PhoneBook> {
        private ArrayList<PhoneBook> items;
        public ContactAdapter(Context context, int textViewResourceId, ArrayList<PhoneBook> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        // 여기서 리스트 보여주는 거임
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row, parent, false);
            }

            PhoneBook p = items.get(position);
            if (p != null) {
                TextView tt = (TextView) convertView.findViewById(R.id.toptext);
                TextView bt = (TextView) convertView.findViewById(R.id.bottomtext);
                if (tt != null){
                    tt.setText(p.getName());
                }
                if(bt != null){
                    bt.setText(p.getTel());
                }
            }
            return convertView;
        }
    }


    /*
    private class PersonAdapter extends ArrayAdapter<Person> {
        private ArrayList<Person> items;
        public PersonAdapter(Context context, int textViewResourceId, ArrayList<Person> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        // 여기서 리스트 보여주는 거임
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row, parent, false);
            }

            Person p = items.get(position);
            if (p != null) {
                TextView tt = (TextView) convertView.findViewById(R.id.toptext);
                TextView bt = (TextView) convertView.findViewById(R.id.bottomtext);
                if (tt != null){
                    tt.setText(p.getName());
                }
                if(bt != null){
                    bt.setText(p.getNumber());
                }
            }
            return convertView;
        }
    }
    */
}