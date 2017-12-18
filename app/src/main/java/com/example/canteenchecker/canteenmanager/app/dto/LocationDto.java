package com.example.canteenchecker.canteenmanager.app.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.canteenchecker.canteenmanager.app.utility.BaseParcelable;

/**
 * @author sschmid
 */
public final class LocationDto extends BaseParcelable {

  public static final Parcelable.Creator<LocationDto> CREATOR = new Parcelable.Creator<LocationDto>() {
    public LocationDto createFromParcel(Parcel in) {
      return new LocationDto(in);
    }

    public LocationDto[] newArray(int size) {
      return new LocationDto[size];
    }
  };

  private final String location;

  public LocationDto(final String location) {
    this.location = location;
  }

  public LocationDto(final Parcel parcel) {
    location = parcel.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    dest.writeString(location);
  }

  public String getLocation() {
    return location;
  }
}
