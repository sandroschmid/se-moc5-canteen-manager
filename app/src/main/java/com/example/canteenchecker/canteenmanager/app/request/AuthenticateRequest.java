package com.example.canteenchecker.canteenmanager.app.request;

import android.content.Context;

import com.example.canteenchecker.canteenmanager.app.proxy.dto.Credentials;
import com.example.canteenchecker.canteenmanager.service.AuthenticateService;

/**
 * @author sschmid
 */
public final class AuthenticateRequest extends BaseRequest<AuthenticateService> {

  public AuthenticateRequest(final Context context, final String userName, final String password) {
    super(context, new Credentials(userName, password));
  }

  @Override
  Class<AuthenticateService> getServiceClass() {
    return AuthenticateService.class;
  }
}
