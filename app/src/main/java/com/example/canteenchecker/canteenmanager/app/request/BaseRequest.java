package com.example.canteenchecker.canteenmanager.app.request;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.canteenchecker.canteenmanager.app.utility.BaseParcelable;

/**
 * @author sschmid
 */
public abstract class BaseRequest<TService extends Service> {

  public static final String KEY_METHOD = "KEY_METHOD";
  public static final String KEY_DTO = "KEY_DTO";

  public enum Method {
    GET, POST, PUT
  }

  final BaseParcelable dto;

  private final Context context;
  private final Method method;

  protected BaseRequest(final Context context) {
    this(context, Method.GET, null);
  }

  protected BaseRequest(final Context context, final Method method) {
    this(context, method, null);
  }

  protected BaseRequest(final Context context, final Method method, @Nullable final BaseParcelable dto) {
    this.context = context;
    this.method = method;
    this.dto = dto;
  }

  public final void send() {
    final Intent intent = new Intent(context, getServiceClass());
    setData(intent);
    context.startService(intent);
  }

  abstract Class<TService> getServiceClass();

  void setData(final Intent intent) {
    intent.putExtra(KEY_METHOD, method);
    if (!method.equals(Method.GET) && dto != null) {
      intent.putExtra(KEY_DTO, dto);
    }
  }
}
