package com.example.canteenchecker.canteenmanager.app.dto;

import android.os.Parcelable;

/**
 * @author sschmid
 */
public abstract class BaseDto implements Parcelable {

  @Override
  public int describeContents() {
    return 0;
  }
}