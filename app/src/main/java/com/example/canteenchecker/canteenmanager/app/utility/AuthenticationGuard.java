package com.example.canteenchecker.canteenmanager.app.utility;

import android.content.Context;
import android.content.Intent;

import com.example.canteenchecker.canteenmanager.ui.activity.LoginActivity;
import com.example.canteenchecker.canteenmanager.ui.activity.SplashActivity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author sschmid
 */
public final class AuthenticationGuard {

  private static final Set<String> anonymousActivities = new HashSet<>();

  static {
    anonymousActivities.add(SplashActivity.class.getName());
    anonymousActivities.add(LoginActivity.class.getName());
  }

  private final Context context;
  private final SecurityManager securityManager;

  public AuthenticationGuard(final Context context, final SecurityManager securityManager) {
    this.context = context;
    this.securityManager = securityManager;
  }

  public boolean canVisit(final String activityClassName) {
    final boolean isAuthenticated = securityManager.isAuthenticated();
    if (anonymousActivities.contains(activityClassName) || isAuthenticated) {
      return true;
    }

    context.startActivity(new Intent(context, LoginActivity.class));
    return false;
  }
}
