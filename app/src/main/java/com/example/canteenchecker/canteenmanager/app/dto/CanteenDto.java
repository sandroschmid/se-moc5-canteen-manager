package com.example.canteenchecker.canteenmanager.app.dto;

import com.example.canteenchecker.canteenmanager.app.entity.Canteen;
import com.example.canteenchecker.canteenmanager.app.entity.Rating;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
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
  private List<RatingDto> ratings;

  public CanteenDto() {
    // empty
  }

  public CanteenDto(Canteen canteen) {
    canteenId = Integer.parseInt(canteen.getId());
    name = canteen.getName();
    meal = canteen.getMeal();
    mealPrice = canteen.getMealPrice();
    address = canteen.getAddress();
    website = canteen.getWebsite();
    phone = canteen.getPhoneNumber();
    averageRating = canteen.getAverageRating();
    averageWaitingTime = canteen.getAverageWaitingTime();
  }

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
        averageWaitingTime,
        getRatings()
    );
  }

  public ArrayList<Rating> getRatings() {
    final ArrayList<Rating> result = new ArrayList<>();
    if (ratings != null) {
      for (final RatingDto rating : ratings) {
        result.add(rating.toRating());
      }
    }
    return result;

//    final Rating[] result = new Rating[ratings.size()];
//    if (ratings != null) {
//      for (int i = 0; i < ratings.size(); i++) {
//        result[i] = ratings.get(i).toRating();
//      }
//    }
//    return result;
  }
}