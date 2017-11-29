package com.example.canteenchecker.canteenmanager.app.utility;

import android.content.Context;

import com.example.canteenchecker.canteenmanager.event.AdminCanteenReceivedEvent;
import com.example.canteenchecker.canteenmanager.event.AuthenticatedEvent;
import com.example.canteenchecker.canteenmanager.event.SignedOutEvent;

/**
 * @author sschmid
 */
public final class EventManager {

  private final AuthenticatedEvent authenticatedEvent;
  private final SignedOutEvent signedOutEvent;
  private final AdminCanteenReceivedEvent adminCanteenReceivedEvent;

  public EventManager(final Context context) {
    authenticatedEvent = new AuthenticatedEvent(context);
    signedOutEvent = new SignedOutEvent(context);
    adminCanteenReceivedEvent = new AdminCanteenReceivedEvent(context);
  }

  public AuthenticatedEvent getAuthenticatedEvent() {
    return authenticatedEvent;
  }

  public SignedOutEvent getSignedOutEvent() {
    return signedOutEvent;
  }

  public AdminCanteenReceivedEvent getAdminCanteenReceivedEvent() {
    return adminCanteenReceivedEvent;
  }
}
