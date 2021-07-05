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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment {

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

    // 내가 만든 변수들.
    public static final int REQUEST_CODE_PLUS = 101;
    Context mContext;
    Activity mActivity;
    static Contact contact = new Contact();
    ViewGroup v;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("yjyj", "In Fragment1, onCreateView");

        mContext = getActivity().getApplicationContext();

        v = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);
        listView = v.findViewById(R.id.list);
        contact.getContacts(mContext);

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
            Log.d("yjyj", "result_ok");
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
            Log.d("yjyj", "In Fragment1, called getView");
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
}