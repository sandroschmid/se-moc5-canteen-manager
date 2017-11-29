package com.example.canteenchecker.canteenmanager;

import android.app.Application;

/**
 * @author sschmid
 */
public final class App extends Application {

  private static App _instance = null;

  public static App getInstance() {
    return _instance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    _instance = this;
  }
}
