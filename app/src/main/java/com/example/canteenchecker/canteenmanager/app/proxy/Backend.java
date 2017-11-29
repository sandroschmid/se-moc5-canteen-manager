package com.example.canteenchecker.canteenmanager.app.proxy;

import com.example.canteenchecker.canteenmanager.app.entity.Canteen;
import com.example.canteenchecker.canteenmanager.app.proxy.dto.CanteenDto;
import com.example.canteenchecker.canteenmanager.app.proxy.dto.Credentials;

public final class Backend {

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

  public Canteen getAdminCanteen(final String authToken) throws BackendException {
    final CanteenDto dto = http.get("/Admin/Canteen", authToken, CanteenDto.class);
    return dto == null ? null : dto.toCanteen();
  }
}
