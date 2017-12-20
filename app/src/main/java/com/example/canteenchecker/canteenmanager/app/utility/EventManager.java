package com.example.canteenchecker.canteenmanager.app.utility;

import android.content.Context;

import com.example.canteenchecker.canteenmanager.app.event.AddressReceivedEvent;
import com.example.canteenchecker.canteenmanager.app.event.AdminCanteenReceivedEvent;
import com.example.canteenchecker.canteenmanager.app.event.AuthenticatedEvent;
import com.example.canteenchecker.canteenmanager.app.event.CoordinatesReceivedEvent;
import com.example.canteenchecker.canteenmanager.app.event.RatingDeletedEvent;
import com.example.canteenchecker.canteenmanager.app.event.SignedOutEvent;

/**
 * @author sschmid
 */
public final class EventManager {

  private final AuthenticatedEvent authenticatedEvent;
  private final SignedOutEvent signedOutEvent;
  private final AdminCanteenReceivedEvent adminCanteenReceivedEvent;
  private final RatingDeletedEvent ratingDeletedEvent;
  private final CoordinatesReceivedEvent coordinatesReceivedEvent;
  private final AddressReceivedEvent addressReceivedEvent;

  public EventManager(final Context context) {
    authenticatedEvent = new AuthenticatedEvent(context);
    signedOutEvent = new SignedOutEvent(context);
    adminCanteenReceivedEvent = new AdminCanteenReceivedEvent(context);
    ratingDeletedEvent = new RatingDeletedEvent(context);
    coordinatesReceivedEvent = new CoordinatesReceivedEvent(context);
    addressReceivedEvent = new AddressReceivedEvent(context);
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

  public RatingDeletedEvent getRatingDeletedEvent() {
    return ratingDeletedEvent;
  }

  public CoordinatesReceivedEvent getCoordinatesReceivedEvent() {
    return coordinatesReceivedEvent;
  }

  public AddressReceivedEvent getAddressReceivedEvent() {
    return addressReceivedEvent;
  }
}
