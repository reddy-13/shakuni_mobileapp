package com.nexzenstudent;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import Config.ConstValue;
import util.CommonClass;
import util.JSONParser;
import util.JSONReader;

public class HolidaysActivity extends CommonAppCompatActivity {
    ArrayList<String> month_array;
    ArrayList<String> year_array;
    ArrayList<String> note_array;
    ArrayList<String> day_array;
    int current_year;
    int current_month;
    int max_days;
    int current_date;

    CommonClass common;
    JSONReader j_reader;
    JSONArray objAttendenceData;
    HolidayAdapter adapter;

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_71));
        }

        j_reader = new JSONReader(this);
        common = new CommonClass(this);
        note_array = new ArrayList<String>();

        month_array = new ArrayList<>();

        int[] months = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        for (int month : months) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM", Locale.getDefault());
            cal.set(Calendar.MONTH, month);
            String month_name = month_date.format(cal.getTime());

            month_array.add(month_name);
        }

        year_array = new ArrayList<String>();
        day_array = new ArrayList<String>();
        day_array.add("Sun");
        day_array.add("Mon");
        day_array.add("Tue");
        day_array.add("Wed");
        day_array.add("Thu");
        day_array.add("Fri");
        day_array.add("Sat");

        //day_array.addAll(Arrays.asList(DateFormatSymbols.getInstance().getShortWeekdays()));

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        current_year = c.get(Calendar.YEAR);
        SimpleDateFormat sformator = new SimpleDateFormat("MM", Locale.US);

        current_month = Integer.parseInt(sformator.format(c.getTime())); // c.get(Calendar.MONTH);

        for (int i = 2016; i <= year; i++) {
            year_array.add(String.valueOf(i));
        }

        max_days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        current_date = c.get(Calendar.DATE);

        Spinner spinnermonth = (Spinner) findViewById(R.id.spinnermonth);
        Spinner spinneryear = (Spinner) findViewById(R.id.spinneryear);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, month_array); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnermonth.setAdapter(spinnerArrayAdapter);
        spinnermonth.setSelection(current_month - 1);
        spinnermonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                current_month = position + 1;
                // new getAttendenceTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, year_array); //selected item will look like a spinner set from XML
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneryear.setAdapter(spinnerArrayAdapter2);
        spinneryear.setSelection(year_array.indexOf(String.valueOf(current_year)));
        spinneryear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                current_year = Integer.parseInt(year_array.get(position));
                //   new getAttendenceTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HolidaysActivity.this, EnquiryActivity.class);
                startActivity(intent);
            }
        });

        Button btnShow = (Button) findViewById(R.id.btnshow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a calendar object and set year and month
                Calendar mycal = new GregorianCalendar(current_year, current_month - 1, 1);

                // Get the number of days in that month
                int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
                max_days = daysInMonth;

                bindDates(max_days);

            }
        });

        gridView = (GridView) findViewById(R.id.gridView2);
        adapter = new HolidayAdapter();

        //gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        bindDates(max_days);
        //new getAttendenceTask().execute();
    }

    ArrayList<Integer> days = new ArrayList<>();
    ArrayList<Integer> months = new ArrayList<>();

    private void bindDates(int selectedMaxDays) {

        days.clear();
        months.clear();
        for (int i = 1; i <= selectedMaxDays; i++) {
            days.add(i);
            months.add(current_month);
        }

        Log.e("Holiday", days.toString());

        try {

            Calendar pastMonth = Calendar.getInstance();
            pastMonth.set(Calendar.MONTH, -1);
            int totalDay = pastMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
            Log.e("Holiday", "past total days:" + totalDay);

            int equldate = 0;
            if (ConstValue.HOLIDAY_START_DAY.equalsIgnoreCase("Sun")) {
                equldate = 0;
            } else if (ConstValue.HOLIDAY_START_DAY.equalsIgnoreCase("Mon")) {
                equldate = 1;
            } else if (ConstValue.HOLIDAY_START_DAY.equalsIgnoreCase("Tue")) {
                equldate = 2;
            } else if (ConstValue.HOLIDAY_START_DAY.equalsIgnoreCase("Wed")) {
                equldate = 3;
            } else if (ConstValue.HOLIDAY_START_DAY.equalsIgnoreCase("Thu")) {
                equldate = 4;
            } else if (ConstValue.HOLIDAY_START_DAY.equalsIgnoreCase("Fri")) {
                equldate = 5;
            } else if (ConstValue.HOLIDAY_START_DAY.equalsIgnoreCase("Sat")) {
                equldate = 6;
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date a_date = format.parse(String.valueOf(current_year) + "-" + String.valueOf(current_month) + "-" + 1);
            String dayname = day_array.get(a_date.getDay());
            Log.e("Holiday", dayname);
            Log.e("Holiday", "" + a_date.getDay());

            //6
            int finalTotal = (a_date.getDay() == equldate) ? totalDay : (totalDay - (a_date.getDay() + (7 - equldate)));

            for (int i = totalDay - 1; i >= finalTotal; i--) {
                days.add(0, i);
                months.add(0, current_month - 1);
            }

            Log.e("Holiday", "finals" + days.toString());
            Log.e("Holiday", "finals Month" + months.toString());

            max_days = days.size();

            new getAttendenceTask().execute();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public class HolidayAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return max_days;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            convertView = inflater.inflate(R.layout.row_of_holiday, null);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            //TextView month = (TextView)convertView.findViewById(R.id.month);
            TextView day = (TextView) convertView.findViewById(R.id.day);
            TextView note = (TextView) convertView.findViewById(R.id.note);

            int dates = days.get(position);
            int month = months.get(position);

            date.setText(String.valueOf(dates + 1));
            //month.setText(month_array.get(current_month - 1));

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            try {
                Date a_date = format.parse(String.valueOf(current_year) + "-" + String.valueOf(month) + "-" + String.valueOf(dates));
                day.setText(day_array.get(a_date.getDay()));

                /*if (LanguagePrefs.getLang(HolidaysActivity.this).equalsIgnoreCase("en")) {
                    if (day_array.get(a_date.getDay()).equalsIgnoreCase("Sun")) {
                        convertView.setBackgroundColor(getResources().getColor(R.color.color_3));

                    }
                } else {*/
                if (month == current_month) {
                    if (day_array.get(a_date.getDay()).equalsIgnoreCase(ConstValue.HOLIDAY_END_DAY)) {
                        convertView.setBackgroundColor(getResources().getColor(R.color.color_3));
                    }
                } else {
                    day.setTextColor(Color.parseColor("#e5e5e5"));
                }
                /*}*/
                date.setText(String.valueOf(dates));

                boolean is_attend = false;
                for (int i = 0; i < objAttendenceData.length(); i++) {
                    JSONObject obj = objAttendenceData.getJSONObject(i);
                    Date b_date = format.parse(obj.getString("holiday_date"));
                    if (a_date.equals(b_date)) {
                        is_attend = true;
                        convertView.setBackgroundColor(getResources().getColor(R.color.color_3));
                        note.setText(obj.getString("holiday_title"));

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return convertView;
        }
    }

    public class getAttendenceTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            String responseString = null;

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("school_id", j_reader.getJSONkeyString("student_data", "school_id")));
            nameValuePairs.add(new BasicNameValuePair("year", String.valueOf(current_year)));
            nameValuePairs.add(new BasicNameValuePair("month", String.valueOf(current_month)));

            JSONParser jsonParser = new JSONParser();

            try {
                String json_responce = jsonParser.makeHttpRequest(ConstValue.HOLIDAY_URL, "POST", nameValuePairs);
                Log.e("Holiday", "" + json_responce);
                JSONObject jObj = new JSONObject(json_responce);
                if (jObj.has("responce") && !jObj.getBoolean("responce")) {
                    responseString = jObj.getString("error");
                } else {
                    if (jObj.has("data")) {
                        objAttendenceData = jObj.getJSONArray("data");

                    } else {
                        responseString = "User not found";
                    }
                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                responseString = e.getMessage();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                responseString = e.getMessage();
                e.printStackTrace();
            }

            // TODO: register the new account here.
            return responseString;
        }

        @Override
        protected void onPostExecute(final String success) {

            if (success == null) {
                if (objAttendenceData != null) {
                    note_array.clear();
                    gridView.setAdapter(adapter);
                }
            } else {
                Toast.makeText(getApplicationContext(), success, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
        }
    }

}
