package com.example.canteenchecker.canteenmanager.app.request;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.example.canteenchecker.canteenmanager.app.proxy.dto.BaseDto;

/**
 * @author sschmid
 */
public abstract class BaseRequest<TService extends Service> {

  public static final String KEY_DTO = "KEY_DTO";

  final BaseDto dto;

  private final Context context;

  protected BaseRequest(final Context context, final BaseDto dto) {
    this.context = context;
    this.dto = dto;
  }

  public final void send() {
    final Intent intent = new Intent(context, getServiceClass());
    setData(intent);
    context.startService(intent);
  }

  abstract Class<TService> getServiceClass();

  void setData(final Intent intent) {
    intent.putExtra(KEY_DTO, dto);
  }
}
