package com.example.canteenchecker.canteenmanager.app.request;

import android.content.Intent;

import com.example.canteenchecker.canteenmanager.app.proxy.dto.Credentials;
import com.example.canteenchecker.canteenmanager.service.AuthenticateService;

/**
 * @author sschmid
 */
public final class AuthenticateRequest extends BaseRequest<AuthenticateService, String> {

  public static final String INTENT_ACTION = String.format("%sAction", AuthenticateRequest.class.getName());

  public AuthenticateRequest(final BaseRequestSender<String> sender, final String userName, final String password) {
    super(INTENT_ACTION, sender, new Credentials(userName, password));
  }

  @Override
  Class<AuthenticateService> getServiceClass() {
    return AuthenticateService.class;
  }

  @Override
  String getResult(final Intent intent) {
    return intent.getStringExtra(KEY_DTO);
  }
}
