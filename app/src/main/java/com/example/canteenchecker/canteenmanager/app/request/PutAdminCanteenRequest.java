package com.example.canteenchecker.canteenmanager.app.request;

import android.content.Context;

import com.example.canteenchecker.canteenmanager.app.entity.Canteen;
import com.example.canteenchecker.canteenmanager.service.AdminCanteenService;

/**
 * @author sschmid
 */
public final class PutAdminCanteenRequest extends BaseRequest<AdminCanteenService> {

  public PutAdminCanteenRequest(final Context context, final Canteen canteen) {
    super(context, Method.PUT, canteen);
  }

  @Override
  Class<AdminCanteenService> getServiceClass() {
    return AdminCanteenService.class;
  }
}
