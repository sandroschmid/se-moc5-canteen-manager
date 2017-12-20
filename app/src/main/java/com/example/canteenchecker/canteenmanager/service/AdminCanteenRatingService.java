package com.example.canteenchecker.canteenmanager.service;

import android.content.Intent;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.app.backend.Backend;
import com.example.canteenchecker.canteenmanager.app.backend.BackendException;
import com.example.canteenchecker.canteenmanager.app.backend.NotAuthenticatedException;
import com.example.canteenchecker.canteenmanager.app.event.BaseRequestResultEvent;
import com.example.canteenchecker.canteenmanager.app.request.DeleteAdminCanteenRatingRequest;

/**
 * @author sschmid
 */
public final class AdminCanteenRatingService extends BaseBackendService<String> {

  public AdminCanteenRatingService() {
    super(AdminCanteenRatingService.class.getName());
  }

  @Override
  protected String executeRequest(final Intent intent) throws NotAuthenticatedException, BackendException {
    final String ratingId = intent.getStringExtra(DeleteAdminCanteenRatingRequest.KEY_RATING_ID);
    Backend.getInstance().deleteRatingFromAdminCanteen(getAuthToken(), ratingId);
    return ratingId;
  }

  @Override
  protected BaseRequestResultEvent<String> getEvent() {
    return App.getInstance().getEventManager().getRatingDeletedEvent();
  }
}
