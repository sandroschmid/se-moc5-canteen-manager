package com.example.canteenchecker.canteenmanager.service;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.app.dto.LocationDto;
import com.example.canteenchecker.canteenmanager.app.event.BaseRequestResultEvent;
import com.example.canteenchecker.canteenmanager.app.request.CoordinatesRequest;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * @author sschmid
 */
public final class GeoCodingService extends BaseRequestService<LatLng> {

  public GeoCodingService() {
    super(GeoCodingService.class.getName());
  }

  @Override
  LatLng executeRequest(final Intent intent) throws IOException {
    final LocationDto locationDto = intent.getParcelableExtra(CoordinatesRequest.KEY_DATA);
    final Geocoder geocoder = new Geocoder(this);
    final List<Address> addresses = geocoder.getFromLocationName(locationDto.getLocation(), 10);
    if (addresses != null && !addresses.isEmpty()) {
      int i = 0;
      Address address;
      do {
        address = addresses.get(i++);
      } while (i < addresses.size() && !address.hasLatitude() && !address.hasLongitude());

      if (address.hasLatitude() && address.hasLongitude()) {
        return new LatLng(address.getLatitude(), address.getLongitude());
      }
    }

    return null;
  }

  @Override
  BaseRequestResultEvent<LatLng> getEvent() {
    return App.getInstance().getEventManager().getCoordinatesReceivedEvent();
  }
}
