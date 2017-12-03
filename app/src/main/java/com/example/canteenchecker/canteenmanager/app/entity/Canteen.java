package com.example.canteenchecker.canteenmanager.app.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.canteenchecker.canteenmanager.app.dto.BaseDto;

public final class Canteen extends BaseDto {

  public static final Parcelable.Creator<Canteen> CREATOR = new Parcelable.Creator<Canteen>() {
    public Canteen createFromParcel(Parcel in) {
      return new Canteen(in);
    }

    public Canteen[] newArray(int size) {
      return new Canteen[size];
    }
  };

  private String id;
  private String name;
  private String meal;
  private float mealPrice;
  private String address;
  private String phoneNumber;
  private String website;
  private float averageRating;
  private int averageWaitingTime;

  public Canteen(
      final String id,
      final String name,
      final String meal,
      final float mealPrice,
      final String address,
      final String website,
      final String phoneNumber,
      final float averageRating,
      final int averageWaitingTime
  ) {
    this.id = id;
    this.name = name;
    this.meal = meal;
    this.mealPrice = mealPrice;
    this.address = address;
    this.website = website;
    this.phoneNumber = phoneNumber;
    this.averageRating = averageRating;
    this.averageWaitingTime = averageWaitingTime;
  }

  public Canteen(final Parcel parcel) {
    this.id = parcel.readString();
    this.name = parcel.readString();
    this.meal = parcel.readString();
    this.mealPrice = parcel.readFloat();
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
    dest.writeString(meal);
    dest.writeFloat(mealPrice);
    dest.writeString(address);
    dest.writeString(website);
    dest.writeString(phoneNumber);
    dest.writeFloat(averageRating);
    dest.writeInt(averageWaitingTime);
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getMeal() {
    return meal;
  }

  public void setMeal(final String meal) {
    this.meal = meal;
  }

  public float getMealPrice() {
    return mealPrice;
  }

  public void setMealPrice(final float mealPrice) {
    this.mealPrice = mealPrice;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(final String address) {
    this.address = address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(final String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(final String website) {
    this.website = website;
  }

  public float getAverageRating() {
    return averageRating;
  }

  public int getAverageWaitingTime() {
    return averageWaitingTime;
  }

  public void setAverageWaitingTime(final int averageWaitingTime) {
    this.averageWaitingTime = averageWaitingTime;
  }
}
