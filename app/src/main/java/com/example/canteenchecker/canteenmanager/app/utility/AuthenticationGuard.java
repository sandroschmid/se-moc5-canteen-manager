package com.example.canteenchecker.canteenmanager.app.utility;

import android.content.Context;
import android.content.Intent;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.ui.activity.LoginActivity;

/**
 * @author sschmid
 */
public final class AuthenticationGuard {

  private final Context context;
  private final SecurityManager securityManager;

  public AuthenticationGuard(final Context context, final SecurityManager securityManager) {
    this.context = context;
    this.securityManager = securityManager;
  }

  public boolean canVisit(final String activityClassName) {
    final boolean isAuthenticated = securityManager.isAuthenticated();
    if (LoginActivity.class.getName().equals(activityClassName)) {
      if (isAuthenticated) {
        context.startActivity(new Intent(context, App.HOME_ACTIVITY));
        return false;
      }

      return true;
    }

    if (isAuthenticated) {
      return true;
    }

    context.startActivity(new Intent(context, LoginActivity.class));
    return false;
  }
}
