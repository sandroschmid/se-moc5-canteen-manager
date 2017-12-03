package com.example.canteenchecker.canteenmanager.service;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.app.backend.NotAuthenticatedException;

/**
 * @author sschmid
 */
public abstract class BaseBackendService<TResult> extends BaseRequestService<TResult> {

  public BaseBackendService(final String name) {
    super(name);
  }

  String getAuthToken() throws NotAuthenticatedException {
    return App.getInstance().getSecurityManager().getAuthToken();
  }
}
