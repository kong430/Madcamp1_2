package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /// ... 코드 계속

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs) ;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition() ;
                changeView(pos) ;
            }

            /// ... 코드 생략
        }) ;
    }

    private void changeView(int index) {
        TextView textView1 = (TextView) findViewById(R.id.text1) ;
        TextView textView2 = (TextView) findViewById(R.id.text2) ;
        TextView textView3 = (TextView) findViewById(R.id.text3) ;

        switch (index) {
            case 0 :
                textView1.setVisibility(View.VISIBLE) ;
                textView2.setVisibility(View.INVISIBLE) ;
                textView3.setVisibility(View.INVISIBLE) ;
                break ;
            case 1 :
                textView1.setVisibility(View.INVISIBLE) ;
                textView2.setVisibility(View.VISIBLE) ;
                textView3.setVisibility(View.INVISIBLE) ;
                break ;
            case 2 :
                textView1.setVisibility(View.INVISIBLE) ;
                textView2.setVisibility(View.INVISIBLE) ;
                textView3.setVisibility(View.VISIBLE) ;
                break ;

        }
    }
}