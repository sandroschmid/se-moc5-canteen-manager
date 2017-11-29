package com.example.canteenchecker.canteenmanager.service;

import android.content.Intent;
import android.util.Log;

import com.example.canteenchecker.canteenmanager.proxy.Backend;
import com.example.canteenchecker.canteenmanager.proxy.BackendException;
import com.example.canteenchecker.canteenmanager.proxy.dto.Credentials;
import com.example.canteenchecker.canteenmanager.request.AuthenticateRequest;

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

    Log.d(TAG, String.format("Authenticate using %s", credentials));

    return Backend.getInstance().authenticate(credentials);
  }

  @Override
  String getIntentAction() {
    return AuthenticateRequest.INTENT_ACTION;
  }

  @Override
  void onSuccess(final Intent intent, final String authToken) {
    intent.putExtra(AuthenticateRequest.KEY_DTO, authToken);
  }
}
