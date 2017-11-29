package com.example.canteenchecker.canteenmanager.app.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.canteenchecker.canteenmanager.app.proxy.dto.BaseDto;

public final class Canteen extends BaseDto {

  public static final Parcelable.Creator<Canteen> CREATOR = new Parcelable.Creator<Canteen>() {
    public Canteen createFromParcel(Parcel in) {
      return new Canteen(in);
    }

    public Canteen[] newArray(int size) {
      return new Canteen[size];
    }
  };

  private final String id;
  private final String name;
  private final String setMeal;
  private final float setMealPrice;
  private final String address;
  private final String phoneNumber;
  private final String website;
  private final float averageRating;
  private final int averageWaitingTime;

  public Canteen(
      final String id,
      final String name,
      final String setMeal,
      final float setMealPrice,
      final String address,
      final String website,
      final String phoneNumber,
      final float averageRating,
      final int averageWaitingTime
  ) {
    this.id = id;
    this.name = name;
    this.setMeal = setMeal;
    this.setMealPrice = setMealPrice;
    this.address = address;
    this.website = website;
    this.phoneNumber = phoneNumber;
    this.averageRating = averageRating;
    this.averageWaitingTime = averageWaitingTime;
  }

  public Canteen(final Parcel parcel) {
    this.id = parcel.readString();
    this.name = parcel.readString();
    this.setMeal = parcel.readString();
    this.setMealPrice = parcel.readFloat();
    this.address = parcel.readString();
    this.website = parcel.readString();
    this.phoneNumber = parcel.readString();
    this.averageRating = parcel.readFloat();
    this.averageWaitingTime = parcel.readInt();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    dest.writeString(id);
    dest.writeString(name);
    dest.writeString(setMeal);
    dest.writeFloat(setMealPrice);
    dest.writeString(phoneNumber);
    dest.writeString(website);
    dest.writeFloat(averageRating);
    dest.writeInt(averageWaitingTime);
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getWebsite() {
    return website;
  }

  public String getSetMeal() {
    return setMeal;
  }

  public float getSetMealPrice() {
    return setMealPrice;
  }

  public float getAverageRating() {
    return averageRating;
  }

  public String getAddress() {
    return address;
  }

  public int getAverageWaitingTime() {
    return averageWaitingTime;
  }
}
