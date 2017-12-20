package com.example.canteenchecker.canteenmanager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.canteenchecker.canteenmanager.app.entity.Canteen;
import com.example.canteenchecker.canteenmanager.app.event.BaseRequestResultEvent;
import com.example.canteenchecker.canteenmanager.app.event.EventReceiver;
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

  public static final Class<? extends Activity> HOME_ACTIVITY = CanteenFormActivity.class;

  private static final String TAG = App.class.getName();
  private static final String FIREBASE_MESSAGING_TOPIC_CANTEENS = "canteens";
  private static final String KEY_CURRENT_CANTEEN = "KEY_CURRENT_CANTEEN";

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

    eventManager.getAdminCanteenReceivedEvent()
        .register(new EventReceiver<BaseRequestResultEvent.RequestResult<Canteen>>() {
          @Override
          public void onNewEvent(final BaseRequestResultEvent.RequestResult<Canteen> result) {
            sharedPreferences.edit()
                .putString(
                    KEY_CURRENT_CANTEEN,
                    result.isSuccessful() ? result.getData().getId() : null
                )
                .apply();
          }
        });
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

  public String getCurrentCanteen() {
    return sharedPreferences.getString(KEY_CURRENT_CANTEEN, null);
  }
}
