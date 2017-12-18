package com.example.canteenchecker.canteenmanager.app.dto;

import com.example.canteenchecker.canteenmanager.app.entity.Rating;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sschmid
 */
public final class RatingDto {

  @JsonProperty("ratingId")
  private int ratingId;

  @JsonProperty("username")
  private String username;

  @JsonProperty("remark")
  private String remark;

  @JsonProperty("ratingPoints")
  private int ratingPoints;

  @JsonProperty("timestamp")
  private long timestamp;

  public Rating toRating() {
    return new Rating(String.valueOf(ratingId), remark, username, timestamp, ratingPoints);
  }
}
