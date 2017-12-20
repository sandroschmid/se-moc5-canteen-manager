package com.example.canteenchecker.canteenmanager.service;

import android.content.Intent;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.app.backend.Backend;
import com.example.canteenchecker.canteenmanager.app.backend.BackendException;
import com.example.canteenchecker.canteenmanager.app.dto.CredentialsDto;
import com.example.canteenchecker.canteenmanager.app.event.BaseRequestResultEvent;
import com.example.canteenchecker.canteenmanager.app.request.AuthenticateRequest;

/**
 * @author sschmid
 */
public final class AuthenticateService extends BaseBackendService<String> {

  private static final String TAG = AuthenticateService.class.getName();

  public AuthenticateService() {
    super(AuthenticateService.class.getName());
  }

  @Override
  protected String executeRequest(final Intent intent) throws BackendException {
    final CredentialsDto credentialsDto = intent.getParcelableExtra(AuthenticateRequest.KEY_DATA);
    return Backend.getInstance().authenticate(credentialsDto);
  }

  @Override
  protected BaseRequestResultEvent<String> getEvent() {
    return App.getInstance().getEventManager().getAuthenticatedEvent();
  }
}
