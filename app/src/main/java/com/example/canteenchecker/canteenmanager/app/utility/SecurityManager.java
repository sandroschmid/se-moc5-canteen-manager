package com.example.canteenchecker.canteenmanager.app.utility;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.app.proxy.NotAuthenticatedException;
import com.example.canteenchecker.canteenmanager.event.EventReceiver;

/**
 * @author sschmid
 */
public final class SecurityManager {

  private static final String PREF_AUTH_TOKEN = "PREF_AUTH_TOKEN";

  private final SharedPreferences sharedPreferences;

  public SecurityManager(final App app, final EventManager eventManager) {
    this.sharedPreferences = app.getSharedPreferences();

    eventManager.getSignedOutEvent().register(new EventReceiver<Void>() {
      @Override
      @SuppressLint("ApplySharedPref")
      public void onNewEvent(final Void result) {
        sharedPreferences.edit()
            .remove(PREF_AUTH_TOKEN)
            .commit();
      }
    });
  }

  public boolean isAuthenticated() {
    return sharedPreferences.contains(PREF_AUTH_TOKEN);
  }

  public String getAuthToken() throws NotAuthenticatedException {
    final String authToken = sharedPreferences.getString(PREF_AUTH_TOKEN, null);
    if (authToken == null) {
      throw new NotAuthenticatedException();
    }

    return authToken;
  }

  @SuppressLint("ApplySharedPref")
  public void onAuthenticated(final String authToken) {
    sharedPreferences.edit()
        .putString(PREF_AUTH_TOKEN, authToken)
        .commit();
  }

  @SuppressLint("ApplySharedPref")
  public void signOut() {
    App.getInstance().getEventManager().getSignedOutEvent().send();
  }
}
