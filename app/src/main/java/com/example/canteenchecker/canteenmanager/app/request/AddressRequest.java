package com.example.canteenchecker.canteenmanager.app.request;

import android.content.Context;

import com.example.canteenchecker.canteenmanager.service.gms.ReverseGeoCodingService;
import com.google.android.gms.maps.model.LatLng;

/**
 * @author sschmid
 */
public final class AddressRequest extends BaseRequest<ReverseGeoCodingService> {

  public AddressRequest(final Context context, final LatLng latLng) {
    super(context, Method.GET, latLng);
  }

  @Override
  Class<ReverseGeoCodingService> getServiceClass() {
    return ReverseGeoCodingService.class;
  }
}
