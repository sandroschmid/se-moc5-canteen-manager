package com.example.canteenchecker.canteenmanager.app.request;

import android.content.Context;

/**
 * @author sschmid
 */
public abstract class BaseRequestSender<TResult> {

  private final Context context;

  protected BaseRequestSender(final Context context) {
    this.context = context;
  }

  public Context getContext() {
    return context;
  }

  public abstract void onResult(TResult result);

  public abstract void onFailure();
}
