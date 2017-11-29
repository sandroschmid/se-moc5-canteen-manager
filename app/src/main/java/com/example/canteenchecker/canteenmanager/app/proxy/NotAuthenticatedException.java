package com.example.canteenchecker.canteenmanager.app.proxy;

public final class NotAuthenticatedException extends Exception {

  public NotAuthenticatedException() {
    super("No authentication token available");
  }
}
