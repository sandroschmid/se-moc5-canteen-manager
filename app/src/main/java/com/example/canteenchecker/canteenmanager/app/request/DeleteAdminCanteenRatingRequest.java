package com.example.canteenchecker.canteenmanager.app.request;

import android.content.Context;
import android.content.Intent;

import com.example.canteenchecker.canteenmanager.service.AdminCanteenRatingService;

/**
 * @author sschmid
 */
public final class DeleteAdminCanteenRatingRequest extends BaseRequest<AdminCanteenRatingService> {

  public static final String KEY_RATING_ID = "KEY_RATING_ID";

  private final String ratingId;

  public DeleteAdminCanteenRatingRequest(final Context context, final String ratingId) {
    super(context, Method.DELETE);
    this.ratingId = ratingId;
  }

  @Override
  Class<AdminCanteenRatingService> getServiceClass() {
    return AdminCanteenRatingService.class;
  }

  @Override
  void setData(final Intent intent) {
    super.setData(intent);
    intent.putExtra(KEY_RATING_ID, ratingId);
  }
}
