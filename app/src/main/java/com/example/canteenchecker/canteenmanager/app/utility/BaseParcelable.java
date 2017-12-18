package com.example.canteenchecker.canteenmanager.app.utility;

import android.os.Parcelable;

/**
 * @author sschmid
 */
public abstract class BaseParcelable implements Parcelable {

  @Override
  public int describeContents() {
    return 0;
  }
}
