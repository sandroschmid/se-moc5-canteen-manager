package com.example.canteenchecker.canteenmanager.app.proxy;

import com.example.canteenchecker.canteenmanager.app.proxy.dto.Credentials;

public class Backend {

  private static Backend instance;

  public static Backend getInstance() {
    if (instance == null) {
      instance = new Backend();
    }

    return instance;
  }

  private final HttpRequest http = new HttpRequest();

  private Backend() {
  }

  public String authenticate(final Credentials credentials) throws BackendException {
    return http.post("/Admin/Login", null, credentials, String.class);
  }
}
