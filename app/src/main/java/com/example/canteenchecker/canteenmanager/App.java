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
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * @author sschmid
 */
public final class App extends Application {

  private static final String TAG = App.class.getName();
  public static final Class<? extends Activity> HOME_ACTIVITY = CanteenFormActivity.class;

  private static final String FIREBASE_MESSAGING_TOPIC_CANTEENS = "canteens";

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

    FirebaseApp.initializeApp(this);
    FirebaseMessaging.getInstance().subscribeToTopic(FIREBASE_MESSAGING_TOPIC_CANTEENS);

    final String token = FirebaseInstanceId.getInstance().getToken();
    Log.d(TAG, "Firebase token: " + token);

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
