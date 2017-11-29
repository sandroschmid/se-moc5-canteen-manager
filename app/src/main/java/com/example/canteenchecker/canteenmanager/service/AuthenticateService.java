package com.example.canteenchecker.canteenmanager.service;

import android.content.Intent;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.app.proxy.Backend;
import com.example.canteenchecker.canteenmanager.app.proxy.BackendException;
import com.example.canteenchecker.canteenmanager.app.proxy.dto.Credentials;
import com.example.canteenchecker.canteenmanager.app.request.AuthenticateRequest;
import com.example.canteenchecker.canteenmanager.event.BaseRequestResultEvent;

/**
 * @author sschmid
 */
public final class AuthenticateService extends BaseBackendService<String> {

  private static final String TAG = AuthenticateService.class.getName();

  public AuthenticateService() {
    super(AuthenticateService.class.getName());
  }

  @Override
  String executeRequest(final Intent intent) throws BackendException {
    final Credentials credentials = intent.getParcelableExtra(AuthenticateRequest.KEY_DTO);
    return Backend.getInstance().authenticate(credentials);
  }

  @Override
  BaseRequestResultEvent<String> getEvent() {
    return App.getInstance().getEventManager().getAuthenticatedEvent();
  }
}
