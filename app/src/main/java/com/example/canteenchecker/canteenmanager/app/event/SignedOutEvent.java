package com.example.canteenchecker.canteenmanager.app.event;

import android.content.Context;

/**
 * @author sschmid
 */
public final class SignedOutEvent extends BaseEvent<Void> {

  public SignedOutEvent(final Context context) {
    super(context, SignedOutEvent.class.getName());
  }
}
