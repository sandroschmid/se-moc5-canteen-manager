package com.example.canteenchecker.canteenmanager.app.event;

import android.content.Context;
import android.content.Intent;

/**
 * @author sschmid
 */
public final class RatingDeletedEvent extends BaseRequestResultEvent<String> {

  private static final String KEY_RATING_ID = "KEY_RATING_ID";

  public RatingDeletedEvent(final Context context) {
    super(context, RatingDeletedEvent.class.getName());
  }

  @Override
  void setResult(final Intent intent, final RequestResult<String> data) {
    super.setResult(intent, data);
    intent.putExtra(KEY_RATING_ID, data.getData());
  }

  @Override
  String getData(final Intent intent) {
    return intent.getStringExtra(KEY_RATING_ID);
  }
}
