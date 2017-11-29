package com.example.canteenchecker.canteenmanager.app.proxy.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sschmid
 */
public final class Credentials extends BaseDto {

  public static final Parcelable.Creator<Credentials> CREATOR = new Parcelable.Creator<Credentials>() {
    public Credentials createFromParcel(Parcel in) {
      return new Credentials(in);
    }

    public Credentials[] newArray(int size) {
      return new Credentials[size];
    }
  };

  @JsonProperty("username")
  private final String userName;

  @JsonProperty("password")
  private final String password;

  public Credentials(final String userName, final String password) {
    this.userName = userName;
    this.password = password;
  }

  public Credentials(final Parcel parcel) {
    this.userName = parcel.readString();
    this.password = parcel.readString();
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    dest.writeString(userName);
    dest.writeString(password);
  }

  @Override
  public String toString() {
    return String.format("%s:%s", userName, password);
  }
}
