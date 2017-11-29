package com.example.canteenchecker.canteenmanager.app.proxy.dto;

import com.example.canteenchecker.canteenmanager.app.entity.Canteen;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author sschmid
 */
public final class CanteenDto {

  @JsonProperty("canteenId")
  private int canteenId;

  @JsonProperty("name")
  private String name;

  @JsonProperty("meal")
  private String meal;

  @JsonProperty("mealPrice")
  private float mealPrice;

  @JsonProperty("address")
  private String address;

  @JsonProperty("website")
  private String website;

  @JsonProperty("phone")
  private String phone;

  @JsonProperty("averageRating")
  private float averageRating;

  @JsonProperty("averageWaitingTime")
  private int averageWaitingTime;

  @JsonProperty("ratings")
  private List<Object> ratings;

  public Canteen toCanteen() {
    return new Canteen(
        String.valueOf(canteenId),
        name,
        meal,
        mealPrice,
        address,
        website,
        phone,
        averageRating,
        averageWaitingTime
    );
  }
}