package com.example.canteenchecker.canteenmanager.app.request;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;

/**
 * @author sschmid
 */
public abstract class BaseRequest<TService extends Service> {

  public static final String KEY_METHOD = "KEY_METHOD";
  public static final String KEY_DATA = "KEY_DATA";

  public enum Method {
    GET, POST, PUT, DELETE
  }

  final Parcelable data;

  private final Context context;
  private final Method method;

  protected BaseRequest(final Context context) {
    this(context, Method.GET, null);
  }

  protected BaseRequest(final Context context, final Method method) {
    this(context, method, null);
  }

  protected BaseRequest(
      final Context context,
      final Method method,
      @Nullable final Parcelable data
  ) {
    this.context = context;
    this.method = method;
    this.data = data;
  }

  public final void send() {
    final Intent intent = new Intent(context, getServiceClass());
    setData(intent);
    context.startService(intent);
  }

  abstract Class<TService> getServiceClass();

  void setData(final Intent intent) {
    intent.putExtra(KEY_METHOD, method);
    if (data != null) {
      intent.putExtra(KEY_DATA, data);
    }
  }
}
