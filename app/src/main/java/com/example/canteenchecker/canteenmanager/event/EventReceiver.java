package com.example.canteenchecker.canteenmanager.event;

/**
 * @author sschmid
 */
@FunctionalInterface
public interface EventReceiver<TData> {

  void onNewEvent(TData data);
}
