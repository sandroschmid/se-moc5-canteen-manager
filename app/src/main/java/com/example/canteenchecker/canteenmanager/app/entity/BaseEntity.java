package com.example.canteenchecker.canteenmanager.app.entity;

import android.os.Parcel;

import com.example.canteenchecker.canteenmanager.app.utility.BaseParcelable;

/**
 * @author sschmid
 */
public abstract class BaseEntity extends BaseParcelable {

  private final String id;

  protected BaseEntity(final String id) {
    this.id = id;
  }

  protected BaseEntity(final Parcel parcel) {
    this.id = parcel.readString();
  }

  public String getId() {
    return id;
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    dest.writeString(id);
  }
}
