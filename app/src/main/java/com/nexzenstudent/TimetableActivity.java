package com.nexzenstudent;

import android.os.Build;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import Config.ConstValue;
import adapter.TimetableFragmentAdapter;

/**
 * Created by Rajesh Dabhi on 20/6/2017.
 */

public class TimetableActivity extends CommonAppCompatActivity {

    private ListView lv_timetable;

    public static int[] sequencefinal = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_31));
        }

        //lv_timetable = (ListView)findViewById(R.id.lv_timetable);
        //loadTimetableData("1");

        ViewPager vp_timetable = (ViewPager) findViewById(R.id.vp_timetable);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_day);

        /*tabLayout.addTab(tabLayout.newTab().setText("MON"));
        tabLayout.addTab(tabLayout.newTab().setText("TUE"));
        tabLayout.addTab(tabLayout.newTab().setText("WED"));
        tabLayout.addTab(tabLayout.newTab().setText("THU"));
        tabLayout.addTab(tabLayout.newTab().setText("FRI"));
        tabLayout.addTab(tabLayout.newTab().setText("SAT"));
        tabLayout.addTab(tabLayout.newTab().setText("SUN"));*/

        vp_timetable.setAdapter(new TimetableFragmentAdapter(getSupportFragmentManager(), sequencefinal));
        tabLayout.setupWithViewPager(vp_timetable);

        if (ConstValue.HOLIDAY_START_DAY.equalsIgnoreCase("Sun")) {
            tabLayout.getTabAt(0).setText(getResources().getString(R.string.sun));
            tabLayout.getTabAt(1).setText(getResources().getString(R.string.mon));
            tabLayout.getTabAt(2).setText(getResources().getString(R.string.tue));
            tabLayout.getTabAt(3).setText(getResources().getString(R.string.wed));
            tabLayout.getTabAt(4).setText(getResources().getString(R.string.thu));
            tabLayout.getTabAt(5).setText(getResources().getString(R.string.fri));
            tabLayout.getTabAt(6).setText(getResources().getString(R.string.sat));
            int[] sequence = {7, 1, 2, 3, 4, 5, 6};
            sequencefinal = sequence;
        } else if (ConstValue.HOLIDAY_START_DAY.equalsIgnoreCase("Mon")) {
            tabLayout.getTabAt(0).setText(getResources().getString(R.string.mon));
            tabLayout.getTabAt(1).setText(getResources().getString(R.string.tue));
            tabLayout.getTabAt(2).setText(getResources().getString(R.string.wed));
            tabLayout.getTabAt(3).setText(getResources().getString(R.string.thu));
            tabLayout.getTabAt(4).setText(getResources().getString(R.string.fri));
            tabLayout.getTabAt(5).setText(getResources().getString(R.string.sat));
            tabLayout.getTabAt(6).setText(getResources().getString(R.string.sun));
            int[] sequence = {1, 2, 3, 4, 5, 6, 7};
            sequencefinal = sequence;
        } else if (ConstValue.HOLIDAY_START_DAY.equalsIgnoreCase("Tue")) {
            tabLayout.getTabAt(0).setText(getResources().getString(R.string.tue));
            tabLayout.getTabAt(1).setText(getResources().getString(R.string.wed));
            tabLayout.getTabAt(2).setText(getResources().getString(R.string.thu));
            tabLayout.getTabAt(3).setText(getResources().getString(R.string.fri));
            tabLayout.getTabAt(4).setText(getResources().getString(R.string.sat));
            tabLayout.getTabAt(5).setText(getResources().getString(R.string.sun));
            tabLayout.getTabAt(6).setText(getResources().getString(R.string.mon));
            int[] sequence = {2, 3, 4, 5, 6, 7, 1};
            sequencefinal = sequence;
        } else if (ConstValue.HOLIDAY_START_DAY.equalsIgnoreCase("Wed")) {
            tabLayout.getTabAt(0).setText(getResources().getString(R.string.wed));
            tabLayout.getTabAt(1).setText(getResources().getString(R.string.thu));
            tabLayout.getTabAt(2).setText(getResources().getString(R.string.fri));
            tabLayout.getTabAt(3).setText(getResources().getString(R.string.sat));
            tabLayout.getTabAt(4).setText(getResources().getString(R.string.sun));
            tabLayout.getTabAt(5).setText(getResources().getString(R.string.mon));
            tabLayout.getTabAt(6).setText(getResources().getString(R.string.tue));
            int[] sequence = {3, 4, 5, 6, 7, 1, 2};
            sequencefinal = sequence;
        } else if (ConstValue.HOLIDAY_START_DAY.equalsIgnoreCase("Thu")) {
            tabLayout.getTabAt(0).setText(getResources().getString(R.string.thu));
            tabLayout.getTabAt(1).setText(getResources().getString(R.string.fri));
            tabLayout.getTabAt(2).setText(getResources().getString(R.string.sat));
            tabLayout.getTabAt(3).setText(getResources().getString(R.string.sun));
            tabLayout.getTabAt(4).setText(getResources().getString(R.string.mon));
            tabLayout.getTabAt(5).setText(getResources().getString(R.string.tue));
            tabLayout.getTabAt(6).setText(getResources().getString(R.string.wed));
            int[] sequence = {4, 5, 6, 7, 1, 2, 3};
            sequencefinal = sequence;
        } else if (ConstValue.HOLIDAY_START_DAY.equalsIgnoreCase("Fri")) {
            tabLayout.getTabAt(0).setText(getResources().getString(R.string.fri));
            tabLayout.getTabAt(1).setText(getResources().getString(R.string.sat));
            tabLayout.getTabAt(2).setText(getResources().getString(R.string.sun));
            tabLayout.getTabAt(3).setText(getResources().getString(R.string.mon));
            tabLayout.getTabAt(4).setText(getResources().getString(R.string.tue));
            tabLayout.getTabAt(5).setText(getResources().getString(R.string.wed));
            tabLayout.getTabAt(6).setText(getResources().getString(R.string.thu));
            int[] sequence = {5, 6, 7, 1, 2, 3, 4};
            sequencefinal = sequence;
        } else if (ConstValue.HOLIDAY_START_DAY.equalsIgnoreCase("Sat")) {
            tabLayout.getTabAt(0).setText(getResources().getString(R.string.sat));
            tabLayout.getTabAt(1).setText(getResources().getString(R.string.sun));
            tabLayout.getTabAt(2).setText(getResources().getString(R.string.mon));
            tabLayout.getTabAt(3).setText(getResources().getString(R.string.tue));
            tabLayout.getTabAt(4).setText(getResources().getString(R.string.wed));
            tabLayout.getTabAt(5).setText(getResources().getString(R.string.thu));
            tabLayout.getTabAt(6).setText(getResources().getString(R.string.fri));
            int[] sequence = {6, 7, 1, 2, 3, 4, 5};
            sequencefinal = sequence;
        }

        /*tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tabLayout.getSelectedTabPosition();
                position++;
                loadTimetableData(""+position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/

    }

    /*private void loadTimetableData(String day_id){
        TimetableAdapter adapter = new TimetableAdapter(this, new JSONArray(),day_id);
        lv_timetable.setAdapter(adapter);
    }*/

}