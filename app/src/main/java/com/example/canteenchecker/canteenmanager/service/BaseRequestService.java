package com.example.canteenchecker.canteenmanager.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.canteenchecker.canteenmanager.app.event.BaseRequestResultEvent;

/**
 * @author sschmid
 */
public abstract class BaseRequestService<TResult> extends IntentService {

  private static final String TAG = BaseRequestService.class.getName();

  public BaseRequestService(final String name) {
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
    } catch (Exception e) {
      Log.e(TAG, "Could not execute request", e);
    }

    final BaseRequestResultEvent.RequestResult<TResult> result = new BaseRequestResultEvent.RequestResult<>(
        responseData != null,
        responseData
    );

    getEvent().send(result);
  }

  protected abstract TResult executeRequest(final Intent intent) throws Exception;

  protected abstract BaseRequestResultEvent<TResult> getEvent();
}
