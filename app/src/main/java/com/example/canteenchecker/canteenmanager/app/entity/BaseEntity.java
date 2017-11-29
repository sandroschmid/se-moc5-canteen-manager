package com.example.canteenchecker.canteenmanager.app.entity;

import android.os.Parcelable;

/**
 * @author sschmid
 */
public abstract class BaseEntity implements Parcelable {

  @Override
  public int describeContents() {
    return 0;
  }
}
