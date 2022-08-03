package net.foucry.pilldroid;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lincoln on 05/05/16.
 */
public class PrefManager {
    // Shared preferences file name
    private static final String PREF_NAME = "Pildroid-Prefs";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String DATABASE_VERSION = "DatabaseVersion";
    private static final String IS_UNDERSTOOD = "IsUnderStood";
    final SharedPreferences pref;
    // shared pref mode
    final int PRIVATE_MODE = 0;
    SharedPreferences.Editor editor;

    public PrefManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor = pref.edit();
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.apply();
    }

    public int getDatabaseVersion() {
        return pref.getInt(DATABASE_VERSION, 0);
    }

    public void setDatabaseVersion(int version) {
        editor = pref.edit();
        editor.putInt(DATABASE_VERSION, version);
        editor.apply();
    }

    public boolean isUnderstood() {
        return pref.getBoolean(IS_UNDERSTOOD, false);
    }

    public void setUnderstood(boolean isUnderstood) {
        editor = pref.edit();
        editor.putBoolean(IS_UNDERSTOOD, isUnderstood);
        editor.apply();
    }
}
