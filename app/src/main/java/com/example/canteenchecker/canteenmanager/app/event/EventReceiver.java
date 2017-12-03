package com.example.canteenchecker.canteenmanager.app.event;

/**
 * @author sschmid
 */
@FunctionalInterface
public interface EventReceiver<TData> {

  void onNewEvent(TData data);
}
