package com.example.canteenchecker.canteenmanager.app.event;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author sschmid
 */
public final class CoordinatesReceivedEvent extends BaseRequestResultEvent<LatLng> {

  private static final String KEY_COORDINATES = "KEY_COORDINATES";

  public CoordinatesReceivedEvent(final Context context) {
    super(context, CoordinatesReceivedEvent.class.getName());
  }

  @Override
  void setResult(final Intent intent, final RequestResult<LatLng> data) {
    super.setResult(intent, data);
    intent.putExtra(KEY_COORDINATES, data.getData());
  }

  @Override
  LatLng getData(final Intent intent) {
    return intent.getParcelableExtra(KEY_COORDINATES);
  }
}
