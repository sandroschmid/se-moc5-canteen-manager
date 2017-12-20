package com.example.canteenchecker.canteenmanager.app.request;

import android.content.Context;

import com.example.canteenchecker.canteenmanager.app.dto.LocationDto;
import com.example.canteenchecker.canteenmanager.service.gms.GeoCodingService;

/**
 * @author sschmid
 */
public final class CoordinatesRequest extends BaseRequest<GeoCodingService> {

  public CoordinatesRequest(final Context context, final String location) {
    super(context, Method.GET, new LocationDto(location));
  }

  @Override
  Class<GeoCodingService> getServiceClass() {
    return GeoCodingService.class;
  }
}
