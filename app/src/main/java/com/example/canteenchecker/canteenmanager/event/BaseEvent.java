package com.example.canteenchecker.canteenmanager.event;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashSet;
import java.util.Set;

/**
 * @author sschmid
 */
public abstract class BaseEvent<TData> extends BroadcastReceiver {

  private final LocalBroadcastManager localBroadcastManager;
  private final String intentAction;
  private final IntentFilter intentFilter;

  private Set<EventReceiver<TData>> receivers = new HashSet<>();

  public BaseEvent(final Context context, final String intentAction) {
    this.localBroadcastManager = LocalBroadcastManager.getInstance(context);
    this.intentAction = intentAction;
    this.intentFilter = new IntentFilter(intentAction);
  }

  public void send() {
    send(null);
  }

  public void send(@Nullable final TData data) {
    final Intent intent = new Intent(intentAction);
    setResult(intent, data);
    localBroadcastManager.sendBroadcast(intent);
  }

  @Override
  public void onReceive(final Context context, final Intent intent) {
    if (receivers.isEmpty()) {
      return;
    }

    final TData result = getResult(intent);
    for (final EventReceiver<TData> receiver : receivers) {
      receiver.onNewEvent(result);
    }
  }

  public void register(final EventReceiver<TData> receiver) {
    if (receivers.isEmpty()) {
      register();
    }

    receivers.add(receiver);
  }

  public void unregister(final EventReceiver<TData> receiver) {
    receivers.remove(receiver);

    if (receivers.isEmpty()) {
      unregister();
    }
  }

  void setResult(final Intent intent, final TData data) {
    // dummy
  }

  TData getResult(final Intent intent) {
    return null;
  }

  private void register() {
    localBroadcastManager.registerReceiver(this, intentFilter);
  }

  private void unregister() {
    localBroadcastManager.unregisterReceiver(this);
  }
}
