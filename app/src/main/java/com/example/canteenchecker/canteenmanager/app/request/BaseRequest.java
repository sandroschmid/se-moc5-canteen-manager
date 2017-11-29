package com.example.canteenchecker.canteenmanager.app.request;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.example.canteenchecker.canteenmanager.app.proxy.dto.BaseDto;

import java.util.UUID;

/**
 * @author sschmid
 */
public abstract class BaseRequest<TService extends Service, TResult> extends BroadcastReceiver {

  public static final String KEY_REQUEST_ID = "KEY_REQUEST_ID";
  public static final String KEY_DTO = "KEY_DTO";
  public static final String KEY_SUCCESS = "KEY_SUCCESS";

  protected final BaseDto dto;

  private final String id = UUID.randomUUID().toString();
  private final String intentAction;
  private final BaseRequestSender<TResult> sender;

  protected BaseRequest(final String intentAction, final BaseRequestSender<TResult> sender, final BaseDto dto) {
    this.intentAction = intentAction;
    this.sender = sender;
    this.dto = dto;
  }

  public final void send() {
    final Context context = sender.getContext();
    LocalBroadcastManager.getInstance(context)
        .registerReceiver(this, new IntentFilter(intentAction));

    final Intent intent = new Intent(context, getServiceClass());
    setData(intent);
    context.startService(intent);
  }

  @Override
  public void onReceive(final Context context, final Intent intent) {
    final String resultId = intent.getStringExtra(KEY_REQUEST_ID);
    if (id.equals(resultId)) {
      LocalBroadcastManager.getInstance(context).unregisterReceiver(this);

      if (intent.getBooleanExtra(KEY_SUCCESS, false)) {
        sender.onResult(getResult(intent));
      } else {
        sender.onFailure();
      }
    }
  }

  abstract Class<TService> getServiceClass();

  void setData(final Intent intent) {
    intent.putExtra(KEY_REQUEST_ID, id);
    intent.putExtra(KEY_DTO, dto);
  }

  abstract TResult getResult(Intent intent);
}
