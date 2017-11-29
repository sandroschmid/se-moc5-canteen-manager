package com.example.canteenchecker.canteenmanager.event;

import android.content.Context;
import android.content.Intent;

/**
 * @author sschmid
 */
public abstract class BaseRequestResultEvent<TData> extends BaseEvent<BaseRequestResultEvent.RequestResult<TData>> {

  public static class RequestResult<TData> {
    private final boolean isSuccessful;
    private final TData data;

    public RequestResult() {
      this(false);
    }

    public RequestResult(final boolean isSuccessful) {
      this(isSuccessful, null);
    }

    public RequestResult(final boolean isSuccessful, final TData data) {
      this.isSuccessful = isSuccessful;
      this.data = data;
    }

    public boolean isSuccessful() {
      return isSuccessful;
    }

    public TData getData() {
      return data;
    }
  }

  private static final String KEY_SUCCESS = "KEY_SUCCESS";

  public BaseRequestResultEvent(final Context context, final String name) {
    super(context, name);
  }

  @Override
  void setResult(final Intent intent, final RequestResult<TData> data) {
    intent.putExtra(KEY_SUCCESS, data != null && data.isSuccessful());
  }

  @Override
  RequestResult<TData> getResult(final Intent intent) {
    return new RequestResult<>(intent.getBooleanExtra(KEY_SUCCESS, false), getData(intent));
  }

  TData getData(final Intent intent) {
    return null;
  }
}
