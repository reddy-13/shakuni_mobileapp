package com.nexzenstudent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Config.ConstValue;
import adapter.ManuAdapter;
import fcm.MyFirebaseRegister;
import util.AnimateFirstDisplayListener;
import util.CommonClass;
import util.ContextWrapper;
import util.LanguagePrefs;

//import android.support.design.widget.FloatingActionButton;
//import androidx.core.content.LocalBroadcastManager;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements GridView.OnItemClickListener {

    AsyncTask<Void, Void, Void> mRegisterTask;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;


    ImageView logout;
    List<String> menu_name;
    List<Integer> menu_icon;
    CommonClass common;
    ImageView school_logo;
    public TextView school_name;
    DisplayImageOptions options;
    ImageLoaderConfiguration imgconfig;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        school_name = (TextView) findViewById(R.id.school_name);


//        imageloader config code
        File cacheDir = StorageUtils.getCacheDirectory(this);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_home_logo)
                .showImageForEmptyUri(R.drawable.ic_home_logo)
                .showImageOnFail(R.drawable.ic_home_logo)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer())
                .imageScaleType(ImageScaleType.NONE)
                .build();

        imgconfig = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(imgconfig);

//        firebase

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Fetching FCM registration token failed");
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
                        System.out.println("My Token"+token);
//                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

//        firebase
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#CC000000"));
        }

        if (getIntent().getStringExtra("notification") != null) {
            Intent startmainIntent = new Intent(this, NotificationActivity.class);
            startActivity(startmainIntent);
        }

        common = new CommonClass(this);

      //  ImageView iv_language = (ImageView) findViewById(R.id.imglanguage);
        //iv_language.setOnClickListener(new View.OnClickListener() {
         //   @Override
            //public void onClick(View v) {
           //     ChooseLanguageDialog chooseLanguageDialog = new ChooseLanguageDialog(MainActivity.this);
              //  chooseLanguageDialog.show();
        //    }
       // });


        logout = (ImageView) findViewById(R.id.imglogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  SharedPreferences myPrefs = getSharedPreferences("MY_PREF",
                        MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(getApplicationContext(),getString(R.string.main_activity_logout), Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1); */

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                // Setting Dialog Title
                alertDialog.setTitle(R.string.logout);

                // Setting Dialog Message
                alertDialog.setMessage(R.string.are_you_sure_you_want_to_logout_this_app);


                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton(getResources().getText(android.R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User pressed YES button. Write Logic Here
                        SharedPreferences myPrefs = getSharedPreferences(ConstValue.PREF_NAME,
                                MODE_PRIVATE);
                        SharedPreferences.Editor editor = myPrefs.edit();
                        editor.clear();
                        editor.commit();
                        Toast.makeText(getApplicationContext(), getString(R.string.main_activity_logout), Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        finish();
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton(getResources().getText(android.R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User pressed No button. Write Logic Here
                        Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent1);
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        });


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, EnquiryActivity.class);
                startActivity(intent);
            }
        });
        final FloatingActionButton fab_web = (FloatingActionButton) findViewById(R.id.fab_web);
        fab_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, ViewBookActivity.class);
                intent.putExtra("title", getResources().getString(R.string.app_name));
                intent.putExtra("book_pdf", "https://nexzensystem.com/");
                startActivity(intent);
            }
        });
        final FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, TopStudentActivity.class);
                startActivity(intent);
            }
        });

        menu_icon = new ArrayList<Integer>();
        menu_name = new ArrayList<String>();

        menu_icon.add(R.drawable.ic_menu_01);
        menu_icon.add(R.drawable.ic_menu_02);
        menu_icon.add(R.drawable.ic_menu_03);
        menu_icon.add(R.drawable.ic_menu_04);
        menu_icon.add(R.drawable.ic_menu_05);
        menu_icon.add(R.drawable.ic_menu_06);
        menu_icon.add(R.drawable.ic_menu_07);
        menu_icon.add(R.drawable.ic_menu_08);
        menu_icon.add(R.drawable.ic_menu_09);
        menu_icon.add(R.drawable.ic_menu_10);
        menu_icon.add(R.drawable.ic_menu_11);
        menu_icon.add(R.drawable.ic_menu_12);
        menu_icon.add(R.drawable.ic_menu_13);
        menu_icon.add(R.drawable.ic_menu_14);
        menu_icon.add(R.drawable.ic_menu_15);
        menu_icon.add(R.drawable.ic_menu_16);

        menu_name.add(getString(R.string.menu_profile));
        menu_name.add("Attendance");
        menu_name.add(getString(R.string.menu_exam));
        menu_name.add(getString(R.string.menu_result));
        menu_name.add(getString(R.string.menu_teacher));
        menu_name.add(getString(R.string.menu_growth));
        menu_name.add(getString(R.string.menu_holiday));
        menu_name.add(getString(R.string.menu_news));
        menu_name.add(getString(R.string.menu_notice));
        menu_name.add(getString(R.string.menu_school));
        menu_name.add(getString(R.string.menu_time_table));
        menu_name.add(getString(R.string.menu_quiz));
        menu_name.add(getString(R.string.menu_fees));
        menu_name.add(getString(R.string.menu_book));
        menu_name.add(getString(R.string.menu_notification));
        menu_name.add("Contact");

        GridView gridview = (GridView) findViewById(R.id.gridView);
        ManuAdapter adapter = new ManuAdapter(getApplicationContext(), menu_name, menu_icon);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(this);

        gridview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                /*if(scrollState == SCROLL_STATE_TOUCH_SCROLL){
                    fab.setVisibility(View.INVISIBLE);
                    fab2.setVisibility(View.INVISIBLE);
                }else{
                    fab.setVisibility(View.VISIBLE);
                    fab2.setVisibility(View.VISIBLE);
                }*/
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                /*int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    fab.setVisibility(View.INVISIBLE);
                }else {
                    fab.setVisibility(View.VISIBLE);
                }*/
            }
        });

        MyFirebaseRegister myFirebaseRegister = new MyFirebaseRegister(this);
        myFirebaseRegister.RegisterUser();
        school_logo = (ImageView) findViewById(R.id.school_logo);
        school_name.setText(common.getSession(ConstValue.SCHOOL_NAME));

        ImageLoader.getInstance().displayImage(ConstValue.BASE_URL + "/uploads/profile/" + common.getSession("school_logo"), school_logo, options, animateFirstListener);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        common.open_screen(position);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));*/

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(ConstValue.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(ConstValue.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        //NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale newLocale = new Locale(LanguagePrefs.getLang(newBase));
        // .. create or get your new Locale object here.
        Context context = ContextWrapper.wrap(newBase, newLocale);
        super.attachBaseContext(context);
    }

}
