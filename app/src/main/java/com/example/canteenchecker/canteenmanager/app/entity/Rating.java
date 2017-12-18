package com.example.canteenchecker.canteenmanager.app.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * @author sschmid
 */
public final class Rating extends BaseEntity {

  public static final Parcelable.Creator<Rating> CREATOR = new Parcelable.Creator<Rating>() {
    public Rating createFromParcel(Parcel in) {
      return new Rating(in);
    }

    public Rating[] newArray(int size) {
      return new Rating[size];
    }
  };

  private final String description;
  private final String userName;
  private final Date date;
  private final int rating;

  public Rating(final String id,
                   final String description,
                   final String userName,
                   final long timestamp,
                   final int rating) {
    super(id);
    this.description = description;
    this.userName = userName;
    this.date = new Date(timestamp);
    this.rating = rating;
  }

  public Rating(final Parcel parcel) {
    super(parcel);
    description = parcel.readString();
    userName = parcel.readString();
    date = (Date) parcel.readSerializable();
    rating = parcel.readInt();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(description);
    dest.writeString(userName);
    dest.writeSerializable(date);
    dest.writeInt(rating);
  }

  public boolean hasDescription() {
    return description != null && !description.isEmpty();
  }

  public String getDescription() {
    return description;
  }

  public String getUserName() {
    return userName;
  }

  public Date getDate() {
    return date;
  }

  public int getRating() {
    return rating;
  }
}
