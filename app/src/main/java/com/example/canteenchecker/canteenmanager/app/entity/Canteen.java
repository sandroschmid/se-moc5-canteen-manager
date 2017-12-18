package com.example.canteenchecker.canteenmanager.app.entity;

import android.os.Parcel;

import java.util.ArrayList;

public final class Canteen extends BaseEntity {

  public static final Creator<Canteen> CREATOR = new Creator<Canteen>() {
    @Override
    public Canteen createFromParcel(Parcel source) {
      return new Canteen(source);
    }

    @Override
    public Canteen[] newArray(int size) {
      return new Canteen[size];
    }
  };

  private String name;
  private String meal;
  private float mealPrice;
  private String address;
  private String phoneNumber;
  private String website;
  private float averageRating;
  private int averageWaitingTime;
  private ArrayList<Rating> ratings;

  public Canteen(
      final String id,
      final String name,
      final String meal,
      final float mealPrice,
      final String address,
      final String website,
      final String phoneNumber,
      final float averageRating,
      final int averageWaitingTime,
      final ArrayList<Rating> ratings
  ) {
    super(id);
    this.name = name;
    this.meal = meal;
    this.mealPrice = mealPrice;
    this.address = address;
    this.website = website;
    this.phoneNumber = phoneNumber;
    this.averageRating = averageRating;
    this.averageWaitingTime = averageWaitingTime;
    this.ratings = ratings;
  }

  protected Canteen(Parcel in) {
    super(in);
    this.name = in.readString();
    this.meal = in.readString();
    this.mealPrice = in.readFloat();
    this.address = in.readString();
    this.phoneNumber = in.readString();
    this.website = in.readString();
    this.averageRating = in.readFloat();
    this.averageWaitingTime = in.readInt();
    this.ratings = in.createTypedArrayList(Rating.CREATOR);
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(this.name);
    dest.writeString(this.meal);
    dest.writeFloat(this.mealPrice);
    dest.writeString(this.address);
    dest.writeString(this.phoneNumber);
    dest.writeString(this.website);
    dest.writeFloat(this.averageRating);
    dest.writeInt(this.averageWaitingTime);
    dest.writeTypedList(this.ratings);
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

  public ArrayList<Rating> getRatings() {
    return ratings;
  }
}
