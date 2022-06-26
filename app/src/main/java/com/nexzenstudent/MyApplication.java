package com.nexzenstudent;

import android.app.Application;
import android.content.res.Configuration;


import androidx.multidex.MultiDex;

import util.LanguagePrefs;

/**
 * Created by shreehari on 26-11-2016.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();

        if (LanguagePrefs.getLang(this) != null) {
            new LanguagePrefs(this);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (LanguagePrefs.getLang(this) != null) {
            new LanguagePrefs(this);
        }
        super.onConfigurationChanged(newConfig);
    }

}
