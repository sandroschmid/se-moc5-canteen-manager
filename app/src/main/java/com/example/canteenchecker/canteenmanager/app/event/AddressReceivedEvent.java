package com.example.canteenchecker.canteenmanager.app.event;

import android.content.Context;
import android.content.Intent;
import android.location.Address;

/**
 * @author sschmid
 */
public final class AddressReceivedEvent extends BaseRequestResultEvent<Address> {

  private static final String KEY_ADDRESS = "KEY_ADDRESS";

  public AddressReceivedEvent(final Context context) {
    super(context, AddressReceivedEvent.class.getName());
  }

  @Override
  void setResult(final Intent intent, final RequestResult<Address> data) {
    super.setResult(intent, data);
    intent.putExtra(KEY_ADDRESS, data.getData());
  }

  @Override
  Address getData(final Intent intent) {
    return intent.getParcelableExtra(KEY_ADDRESS);
  }
}
