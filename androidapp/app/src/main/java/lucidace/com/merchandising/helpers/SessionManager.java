package lucidace.com.merchandising.helpers;

/**
 * Created by kraiba on 8/12/15.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.HashMap;

import lucidace.com.merchandising.LoginActivity;
import lucidace.com.merchandising.models.common.User;


public class SessionManager {
    private String TAG = SessionManager.class.getSimpleName();
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "MerchandisingPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    public static final String KEY_UID = "uid";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ROLE = "role";
    public static final String KEY_PASSWORD = "password";
    private static final String KEY_NOTIFICATIONS = "notifications";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_FULL_NAME = "full_name";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void storeUser(User user) {
        editor.putString(KEY_UID, user.getUser_id());
        editor.putString(KEY_NAME, user.getUsername());
        editor.putString(KEY_FULL_NAME, user.getFull_name());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_TOKEN, user.getAccess_token());
        editor.commit();

        Log.e(TAG, "User is stored in shared preferences. " + user.getUsername() + ", " + user.getEmail());
    }





    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect userHASH to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }
    public User getUser() {
        if (pref.getString(KEY_UID, null) != null) {
            String user_id, user_name,full_name ,email,access_token;
            user_id = pref.getString(KEY_UID, null);
            user_name = pref.getString(KEY_NAME, null);
            full_name= pref.getString(KEY_NAME, null);
            email = pref.getString(KEY_EMAIL, null);
            access_token = pref.getString(KEY_TOKEN, null);

            User user = new User(user_id, full_name,user_name, email,access_token);
            return user;
        }
        return null;
    }
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}