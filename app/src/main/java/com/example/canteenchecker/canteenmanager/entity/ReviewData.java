package com.example.canteenchecker.canteenmanager.entity;

public class ReviewData {

  private final float averageRating;
  private final int totalRatings;
  private final int ratingsOne;
  private final int ratingsTwo;
  private final int ratingsThree;
  private final int ratingsFour;
  private final int ratingsFive;

  public ReviewData(
      float averageRating,
      int totalRatings,
      int ratingsOne,
      int ratingsTwo,
      int ratingsThree,
      int ratingsFour,
      int ratingsFive
  ) {
    this.averageRating = averageRating;
    this.totalRatings = totalRatings;
    this.ratingsOne = ratingsOne;
    this.ratingsTwo = ratingsTwo;
    this.ratingsThree = ratingsThree;
    this.ratingsFour = ratingsFour;
    this.ratingsFive = ratingsFive;
  }

  public float getAverageRating() {
    return averageRating;
  }

  public int getTotalRatings() {
    return totalRatings;
  }

  public int getRatingsOne() {
    return ratingsOne;
  }

  public int getRatingsTwo() {
    return ratingsTwo;
  }

  public int getRatingsThree() {
    return ratingsThree;
  }

  public int getRatingsFour() {
    return ratingsFour;
  }

  public int getRatingsFive() {
    return ratingsFive;
  }

  public int getTotalRatingsOfMostCommonGrade() {
    return Math.max(
        Math.max(Math.max(Math.max(ratingsOne, ratingsTwo), ratingsThree), ratingsFour),
        ratingsFive
    );
  }

}
