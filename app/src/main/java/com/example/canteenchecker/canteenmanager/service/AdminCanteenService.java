package com.example.canteenchecker.canteenmanager.service;

import android.content.Intent;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.app.backend.Backend;
import com.example.canteenchecker.canteenmanager.app.backend.BackendException;
import com.example.canteenchecker.canteenmanager.app.backend.NotAuthenticatedException;
import com.example.canteenchecker.canteenmanager.app.entity.Canteen;
import com.example.canteenchecker.canteenmanager.app.event.BaseRequestResultEvent;
import com.example.canteenchecker.canteenmanager.app.request.BaseRequest;
import com.example.canteenchecker.canteenmanager.app.request.PutAdminCanteenRequest;

/**
 * @author sschmid
 */
public final class AdminCanteenService extends BaseBackendService<Canteen> {

  public AdminCanteenService() {
    super(AdminCanteenService.class.getName());
  }

  @Override
  protected Canteen executeRequest(final Intent intent) throws NotAuthenticatedException, BackendException {
    final Backend backend = Backend.getInstance();
    final BaseRequest.Method method = (BaseRequest.Method) intent.getSerializableExtra(BaseRequest.KEY_METHOD);
    if (BaseRequest.Method.GET.equals(method)) {
      return backend.getAdminCanteen(getAuthToken());
    } else {
      final Canteen canteen = intent.getParcelableExtra(PutAdminCanteenRequest.KEY_DATA);
      return backend.putAdminCanteen(getAuthToken(), canteen);
    }
  }

  @Override
  protected BaseRequestResultEvent<Canteen> getEvent() {
    return App.getInstance().getEventManager().getAdminCanteenReceivedEvent();
  }
}
