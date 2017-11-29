package com.example.canteenchecker.canteenmanager.service;

import android.content.Intent;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.app.entity.Canteen;
import com.example.canteenchecker.canteenmanager.app.proxy.Backend;
import com.example.canteenchecker.canteenmanager.app.proxy.BackendException;
import com.example.canteenchecker.canteenmanager.app.proxy.NotAuthenticatedException;
import com.example.canteenchecker.canteenmanager.app.request.BaseRequest;
import com.example.canteenchecker.canteenmanager.app.request.PutAdminCanteenRequest;
import com.example.canteenchecker.canteenmanager.event.BaseRequestResultEvent;

/**
 * @author sschmid
 */
public final class AdminCanteenService extends BaseBackendService<Canteen> {

  public AdminCanteenService() {
    super(AdminCanteenService.class.getName());
  }

  @Override
  Canteen executeRequest(final Intent intent) throws NotAuthenticatedException, BackendException {
    final Backend backend = Backend.getInstance();
    final BaseRequest.Method method = (BaseRequest.Method) intent.getSerializableExtra(BaseRequest.KEY_METHOD);
    if (BaseRequest.Method.GET.equals(method)) {
      return backend.getAdminCanteen(getAuthToken());
    } else {
      final Canteen canteen = intent.getParcelableExtra(PutAdminCanteenRequest.KEY_DTO);
      return backend.putAdminCanteen(getAuthToken(), canteen);
    }
  }

  @Override
  BaseRequestResultEvent<Canteen> getEvent() {
    return App.getInstance().getEventManager().getAdminCanteenReceivedEvent();
  }
}
