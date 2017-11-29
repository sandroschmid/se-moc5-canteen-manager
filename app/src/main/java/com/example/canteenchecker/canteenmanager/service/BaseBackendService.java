package com.example.canteenchecker.canteenmanager.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.canteenchecker.canteenmanager.proxy.BackendException;
import com.example.canteenchecker.canteenmanager.request.BaseRequest;

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

    TResult result = null;
    try {
      result = executeRequest(intent);
    } catch (BackendException e) {
      Log.d(TAG, "Could not execute backend-request", e);
    }

    sendResult(intent.getStringExtra(BaseRequest.KEY_REQUEST_ID), result);
  }

  abstract TResult executeRequest(final Intent intent) throws BackendException;

  abstract String getIntentAction();

  abstract void onSuccess(Intent intent, TResult result);

  void onFailure(Intent intent) {
    // dummy
  }

  private void sendResult(final String requestId, final @Nullable TResult result) {
    Intent intent = new Intent(getIntentAction());
    intent.putExtra(BaseRequest.KEY_REQUEST_ID, requestId);
    if (result == null) {
      onFailure(intent);
      intent.putExtra(BaseRequest.KEY_SUCCESS, false);
    } else {
      onSuccess(intent, result);
      intent.putExtra(BaseRequest.KEY_SUCCESS, true);
    }

    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
  }
}
