package com.example.canteenchecker.canteenmanager.app.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.canteenchecker.canteenmanager.app.utility.BaseParcelable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sschmid
 */
public final class CredentialsDto extends BaseParcelable {

  public static final Parcelable.Creator<CredentialsDto> CREATOR = new Parcelable.Creator<CredentialsDto>() {
    public CredentialsDto createFromParcel(Parcel in) {
      return new CredentialsDto(in);
    }

    public CredentialsDto[] newArray(int size) {
      return new CredentialsDto[size];
    }
  };

  @JsonProperty("username")
  private final String userName;

  @JsonProperty("password")
  private final String password;

  public CredentialsDto(final String userName, final String password) {
    this.userName = userName;
    this.password = password;
  }

  public CredentialsDto(final Parcel parcel) {
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
