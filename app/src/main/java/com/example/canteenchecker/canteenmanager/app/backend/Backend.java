package com.example.canteenchecker.canteenmanager.app.backend;

import com.example.canteenchecker.canteenmanager.app.dto.CanteenDto;
import com.example.canteenchecker.canteenmanager.app.dto.CredentialsDto;
import com.example.canteenchecker.canteenmanager.app.entity.Canteen;

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

  public String authenticate(final CredentialsDto credentialsDto) throws BackendException {
    return http.post("/Admin/Login", null, credentialsDto, String.class);
  }

  public Canteen getAdminCanteen(final String authToken) throws BackendException {
    final CanteenDto dto = http.get("/Admin/Canteen", authToken, CanteenDto.class);
    return dto == null ? null : dto.toCanteen();
  }

  public Canteen putAdminCanteen(final String authToken, final Canteen canteen) throws BackendException {
    http.put("/Admin/Canteen", authToken, new CanteenDto(canteen));
    return canteen;
  }
}
