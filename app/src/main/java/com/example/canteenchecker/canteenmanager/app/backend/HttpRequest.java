package com.example.canteenchecker.canteenmanager.app.backend;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author sschmid
 */
public final class HttpRequest {

  // ADMIN --> https://canteencheckeradmin.azurewebsites.net/
  // Test users: P20798/P20798#! S23423432/S23423432
  // SWAGGER --> https://canteenchecker.azurewebsites.net/swagger/ui/index
  private static final String SERVICE_BASE_URL = "https://canteenchecker.azurewebsites.net/";
  private static final long ARTIFICIAL_DELAY = 100;

  public <T> T get(
      final String relativeURL,
      final String authToken,
      final Class<T> cls
  ) throws BackendException {
    try {
      final URLConnection connection = createConnection("GET", relativeURL, authToken);
      final InputStream inputStream = connection.getInputStream();
      try {
        return new ObjectMapper().readValue(inputStream, cls);
      } finally {
        try {
          inputStream.close();
        } catch (IOException ignored) {
        }
      }
    } catch (IOException e) {
      throw new BackendException(e);
    }
  }

  public <REQUEST, RESPONSE> RESPONSE post(
      final String relativeURL,
      final String authToken,
      final REQUEST data,
      final Class<RESPONSE> cls
  ) throws BackendException {
    final ObjectMapper objectMapper = new ObjectMapper();
    try {
      final HttpURLConnection connection = createConnection("POST", relativeURL, authToken);
      connection.setDoOutput(true);
      final OutputStream outputStream = connection.getOutputStream();
      try {
        objectMapper.writeValue(outputStream, data);
      } finally {
        try {
          outputStream.close();
        } catch (IOException ignored) {
          throw new BackendException(ignored);
        }
      }

      final InputStream inputStream = connection.getInputStream();
      try {
        return objectMapper.readValue(inputStream, cls);
      } finally {
        try {
          inputStream.close();
        } catch (IOException ignored) {
          throw new BackendException(ignored);
        }
      }
    } catch (IOException e) {
      throw new BackendException(e);
    }
  }

  public <REQUEST> void put(
      final String relativeURL,
      final String authToken,
      final REQUEST data
  ) throws BackendException {
    final ObjectMapper objectMapper = new ObjectMapper();
    try {
      final HttpURLConnection connection = createConnection("PUT", relativeURL, authToken);
      connection.setDoOutput(true);
      final OutputStream outputStream = connection.getOutputStream();
      try {
        objectMapper.writeValue(outputStream, data);
      } finally {
        try {
          outputStream.close();
        } catch (IOException ignored) {
          throw new BackendException(ignored);
        }
      }
    } catch (IOException e) {
      throw new BackendException(e);
    }
  }

  public void delete(final String relativeURL, final String authToken) throws BackendException {
    createConnection("DELETE", relativeURL, authToken);
  }

  private HttpURLConnection createConnection(
      final String method,
      final String relativeURL,
      final String authToken
  ) throws BackendException {
    // add artificial delay for testing
    try {
      Thread.sleep(ARTIFICIAL_DELAY);
    } catch (InterruptedException e) {
      throw new BackendException(e);
    }

    try {
      final HttpURLConnection connection = (HttpURLConnection) new URL(
          new URL(SERVICE_BASE_URL),
          relativeURL
      ).openConnection();
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestMethod(method);
      if (authToken != null) {
        connection.setRequestProperty("Authorization", String.format("Bearer %s", authToken));
      }
      return connection;
    } catch (IOException e) {
      throw new BackendException(e);
    }
  }
}
