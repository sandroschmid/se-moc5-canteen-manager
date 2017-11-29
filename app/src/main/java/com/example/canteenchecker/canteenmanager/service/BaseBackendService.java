package com.example.canteenchecker.canteenmanager.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.canteenchecker.canteenmanager.app.proxy.BackendException;
import com.example.canteenchecker.canteenmanager.event.BaseRequestResultEvent;

/**
 * @author sschmid
 */
public abstract class BaseBackendService<TResult> extends IntentService {

  private static final String TAG = BaseBackendService.class.getName();

  public BaseBackendService(final String name) {
    super(name);
  }

  @Override
  protected final void onHandleIntent(@Nullable final Intent intent) {
    if (intent == null) {
      return;
    }

    TResult responseData = null;
    try {
      responseData = executeRequest(intent);
    } catch (BackendException e) {
      Log.d(TAG, "Could not execute backend-request", e);
    }

    final BaseRequestResultEvent.RequestResult<TResult> result = new BaseRequestResultEvent.RequestResult<>(
        responseData != null,
        responseData
    );

    getEvent().send(result);
  }

  abstract TResult executeRequest(final Intent intent) throws BackendException;

  abstract BaseRequestResultEvent<TResult> getEvent();
}
