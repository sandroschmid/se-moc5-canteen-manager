package com.example.canteenchecker.canteenmanager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.canteenchecker.canteenmanager.app.utility.AuthenticationGuard;
import com.example.canteenchecker.canteenmanager.app.utility.EventManager;
import com.example.canteenchecker.canteenmanager.app.utility.SecurityManager;
import com.example.canteenchecker.canteenmanager.ui.activity.CanteenFormActivity;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * @author sschmid
 */
public final class App extends Application {

  private static final String TAG = App.class.getName();
  public static final Class<? extends Activity> HOME_ACTIVITY = CanteenFormActivity.class;

  private static App instance = null;

  private SharedPreferences sharedPreferences;
  private EventManager eventManager;
  private SecurityManager securityManager;
  private AuthenticationGuard authenticationGuard;

  public static App getInstance() {
    return instance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;

    final String token = FirebaseInstanceId.getInstance().getToken();
    Log.e(TAG, "Firebase token: " + token);

    sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
    eventManager = new EventManager(this);
    securityManager = new SecurityManager(this, eventManager);
    authenticationGuard = new AuthenticationGuard(this, securityManager);
  }

  public SharedPreferences getSharedPreferences() {
    return sharedPreferences;
  }

  public EventManager getEventManager() {
    return eventManager;
  }

  public SecurityManager getSecurityManager() {
    return securityManager;
  }

  public AuthenticationGuard getAuthenticationGuard() {
    return authenticationGuard;
  }
}
