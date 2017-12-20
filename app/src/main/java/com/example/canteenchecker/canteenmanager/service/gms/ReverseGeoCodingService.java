package com.example.canteenchecker.canteenmanager.service.gms;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.app.event.BaseRequestResultEvent;
import com.example.canteenchecker.canteenmanager.app.request.AddressRequest;
import com.example.canteenchecker.canteenmanager.service.BaseRequestService;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * @author sschmid
 */
public final class ReverseGeoCodingService extends BaseRequestService<Address> {

  public ReverseGeoCodingService() {
    super(ReverseGeoCodingService.class.getName());
  }

  @Override
  protected Address executeRequest(final Intent intent) throws IOException {
    final LatLng latLng = intent.getParcelableExtra(AddressRequest.KEY_DATA);
    final Geocoder geocoder = new Geocoder(this);
    final List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
    return addresses != null && !addresses.isEmpty() ? addresses.get(0) : null;
  }

  @Override
  protected BaseRequestResultEvent<Address> getEvent() {
    return App.getInstance().getEventManager().getAddressReceivedEvent();
  }
}
