package com.example.canteenchecker.canteenmanager.app.event;

import android.content.Context;
import android.content.Intent;

/**
 * @author sschmid
 */
public final class AuthenticatedEvent extends BaseRequestResultEvent<String> {

  private static final String KEY_AUTH_TOKEN = "KEY_AUTH_TOKEN";

  public AuthenticatedEvent(final Context context) {
    super(context, AuthenticatedEvent.class.getName());
  }

  @Override
  void setResult(final Intent intent, final RequestResult<String> data) {
    super.setResult(intent, data);
    intent.putExtra(KEY_AUTH_TOKEN, data.getData());
  }

  @Override
  String getData(final Intent intent) {
    return intent.getStringExtra(KEY_AUTH_TOKEN);
  }
}
