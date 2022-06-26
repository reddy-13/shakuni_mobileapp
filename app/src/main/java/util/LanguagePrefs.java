package util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Created by Rajesh on 2018-03-29.
 */

public class LanguagePrefs {

    private static SharedPreferences sharedPreferences;

    private static String PREFS_LANGUAGE = "languages";

    private Context context;

    public LanguagePrefs(Context context) {
        this.context = context;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String language = sharedPreferences.getString(PREFS_LANGUAGE, null);

        if (language != null) {
            initRTL(language);
        } else {
            saveLanguage("en");
            initRTL("en");
        }

    }

    public void saveLanguage(String language) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(PREFS_LANGUAGE, language);

            editor.apply();
            language = language.equalsIgnoreCase("ar") ? "Arabic" : "English";
            //Toast.makeText(context, language + " is your preferred language.", Toast.LENGTH_SHORT).show();
        } catch (Exception exc) {

        }

    }

    public void initRTL(String lang) {

        if (lang.equalsIgnoreCase("ar")) {
            Resources res = context.getResources();
            Configuration newConfig = new Configuration(res.getConfiguration());
            Locale locale = new Locale("ar");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                newConfig.setLocale(locale);
                newConfig.setLayoutDirection(locale);
            }
            res.updateConfiguration(newConfig, null);
        } else {
            Resources res = context.getResources();
            Configuration newConfig = new Configuration(res.getConfiguration());
            Locale locale = new Locale("en");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                newConfig.setLocale(locale);
                newConfig.setLayoutDirection(locale);
            }
            res.updateConfiguration(newConfig, null);
        }
    }

    public static String getLang(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PREFS_LANGUAGE, "en");
    }

    public static void clearLanguage(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();
    }

}
