package com.example.canteenchecker.canteenmanager.app.event;

import android.content.Context;
import android.content.Intent;

import com.example.canteenchecker.canteenmanager.app.entity.Canteen;

/**
 * @author sschmid
 */
public final class AdminCanteenReceivedEvent extends BaseRequestResultEvent<Canteen> {

  private static final String KEY_ADMIN_CANTEEN = "KEY_ADMIN_CANTEEN";

  public AdminCanteenReceivedEvent(final Context context) {
    super(context, AdminCanteenReceivedEvent.class.getName());
  }

  @Override
  void setResult(final Intent intent, final RequestResult<Canteen> data) {
    super.setResult(intent, data);
    intent.putExtra(KEY_ADMIN_CANTEEN, data.getData());
  }

  @Override
  Canteen getData(final Intent intent) {
    return intent.getParcelableExtra(KEY_ADMIN_CANTEEN);
  }
}
