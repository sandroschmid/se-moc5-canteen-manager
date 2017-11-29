package com.example.canteenchecker.canteenmanager.app.request;

import android.content.Context;

import com.example.canteenchecker.canteenmanager.service.AdminCanteenService;

/**
 * @author sschmid
 */
public final class GetAdminCanteenRequest extends BaseRequest<AdminCanteenService> {

  public GetAdminCanteenRequest(final Context context) {
    super(context);
  }

  @Override
  Class<AdminCanteenService> getServiceClass() {
    return AdminCanteenService.class;
  }
}
