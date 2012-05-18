package com.sender.sender.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
	    private static String MY_EMAIL_PREF = "email";
	    private static String MY_AUTH_PREF = "auth";

	    private static SharedPreferences getPrefs(Context context) {
	        return context.getSharedPreferences("myprefs", 0);
	    }

	    public static String getEmailPref(Context context) {
	        return getPrefs(context).getString(MY_EMAIL_PREF, "default");
	    }

	    public static String getAuthPref(Context context) {
	        return getPrefs(context).getString(MY_AUTH_PREF, "noauth");
	    }

	    public static void setEmailPref(Context context, String value) {
	        // perform validation etc..
	        getPrefs(context).edit().putString(MY_EMAIL_PREF, value).commit();
	    }
	    public static void setAuthPref(Context context, String value) {
	        // perform validation etc..
	        getPrefs(context).edit().putString(MY_AUTH_PREF, value).commit();
	    }

	    
}
