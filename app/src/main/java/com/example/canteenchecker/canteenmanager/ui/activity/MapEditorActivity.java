package com.example.canteenchecker.canteenmanager.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.app.event.AddressReceivedEvent;
import com.example.canteenchecker.canteenmanager.app.event.BaseRequestResultEvent;
import com.example.canteenchecker.canteenmanager.app.event.CoordinatesReceivedEvent;
import com.example.canteenchecker.canteenmanager.app.event.EventReceiver;
import com.example.canteenchecker.canteenmanager.app.request.AddressRequest;
import com.example.canteenchecker.canteenmanager.app.request.CoordinatesRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author sschmid
 */
public final class MapEditorActivity
    extends BaseLoadingActivity
    implements GoogleMap.OnMapClickListener {

  private static final String EXTRA_LOCATION = "EXTRA_LOCATION";
  private static final float DEFAULT_MAP_ZOOM_FACTOR = 15f;

  public static Intent createIntent(final Context context, final String address) {
    final Intent intent = new Intent(context, MapEditorActivity.class);
    intent.putExtra(EXTRA_LOCATION, address);
    return intent;
  }

  public static String getResult(final Intent intent) {
    return intent.getStringExtra(EXTRA_LOCATION);
  }

  private SupportMapFragment mapFragment;
  private AppCompatEditText etAddress;

  private Marker marker;

  private final CoordinatesReceivedEvent coordinatesReceivedEvent = App.getInstance()
      .getEventManager()
      .getCoordinatesReceivedEvent();

  private final AddressReceivedEvent addressReceivedEvent = App.getInstance()
      .getEventManager()
      .getAddressReceivedEvent();

  private EventReceiver<BaseRequestResultEvent.RequestResult<LatLng>> latLngEventReceiver;
  private EventReceiver<BaseRequestResultEvent.RequestResult<Address>> addressEventReceiver;

  @Override
  public boolean onSupportNavigateUp() {
    setResult(Activity.RESULT_OK, getResultIntent());
    finish();
    return true;
  }

  @Override
  public void onBackPressed() {
    setResult(Activity.RESULT_OK, getResultIntent());
    super.onBackPressed();
  }

  @Override
  protected int getLayout() {
    return R.layout.activity_map_editor;
  }

  @Override
  protected void initView() {
    super.initView();

    mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragMap);
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(final GoogleMap map) {
        map.setOnMapClickListener(MapEditorActivity.this);
        final UiSettings settings = map.getUiSettings();
        settings.setAllGesturesEnabled(true);
        settings.setZoomControlsEnabled(true);
        stopLoading();
      }
    });

    etAddress = findViewById(R.id.etAddress);
    etAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(final TextView textView, final int id, final KeyEvent keyEvent) {
        if (id == EditorInfo.IME_ACTION_SEARCH || id == EditorInfo.IME_NULL) {
          findLocation(etAddress.getText().toString());
          return true;
        }
        return false;
      }
    });

    final View btnSearch = findViewById(R.id.btnSearch);
    btnSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View view) {
        findLocation(etAddress.getText().toString());
      }
    });
  }

  @Override
  protected void setViewData() {
    super.setViewData();
    onRefresh();
  }

  @Override
  protected void restoreSavedState(final Bundle savedInstanceState) {
    // nothing to do
  }

  @Override
  protected void initEventReceivers() {
    super.initEventReceivers();
    latLngEventReceiver = new EventReceiver<BaseRequestResultEvent.RequestResult<LatLng>>() {
      @Override
      public void onNewEvent(final BaseRequestResultEvent.RequestResult<LatLng> result) {
        if (result.isSuccessful()) {
          stopLoading();
          showData(result.getData());
        } else {
          Toast.makeText(
              MapEditorActivity.this,
              R.string.app_error_load_failure_adress,
              Toast.LENGTH_SHORT
          ).show();
        }
      }
    };

    addressEventReceiver = new EventReceiver<BaseRequestResultEvent.RequestResult<Address>>() {
      @Override
      public void onNewEvent(final BaseRequestResultEvent.RequestResult<Address> result) {
        if (result.isSuccessful()) {
          showData(result.getData());
        } else {
          stopLoading();
          Toast.makeText(
              MapEditorActivity.this,
              R.string.app_error_load_failure_adress,
              Toast.LENGTH_SHORT
          ).show();
        }
      }
    };

    coordinatesReceivedEvent.register(latLngEventReceiver);
    addressReceivedEvent.register(addressEventReceiver);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    coordinatesReceivedEvent.unregister(latLngEventReceiver);
    addressReceivedEvent.unregister(addressEventReceiver);
  }

  @Override
  public void onMapClick(final LatLng latLng) {
    startLoading();
    new AddressRequest(this, latLng).send();
  }

  @Override
  public void onRefresh() {
    final String location = getLocationFromIntent();
    findLocation(location);
    setEditAddressValue(location);
  }

  private void findLocation(final String location) {
    startLoading();
    new CoordinatesRequest(this, location).send();
  }

  private String getLocationFromIntent() {
    return getIntent().getStringExtra(EXTRA_LOCATION);
  }

  private void showData(final LatLng latLng) {
    if (marker != null) {
      marker.remove();
    }

    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(final GoogleMap map) {
        if (latLng == null) {
          map.animateCamera(CameraUpdateFactory.newLatLngZoom(
              new LatLng(0, 0),
              DEFAULT_MAP_ZOOM_FACTOR
          ));
        } else {
          marker = map.addMarker(new MarkerOptions().position(latLng));
          map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_MAP_ZOOM_FACTOR));
        }
        stopLoading();
      }
    });
  }

  private void showData(final Address address) {
    if (address == null) {
      return;
    }

    if (marker != null) {
      marker.remove();
    }

    setEditAddressValue(addressToString(address));
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(final GoogleMap map) {
        final LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        marker = map.addMarker(new MarkerOptions().position(latLng));
        stopLoading();
      }
    });
  }

  private void setEditAddressValue(final String address) {
    etAddress.setText(address);
    etAddress.setSelection(address.length());
  }

  private String addressToString(final Address address) {
    final StringBuilder addressText = new StringBuilder();
    for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
      addressText.append(address.getAddressLine(i)).append(", ");
    }

    final int addressTextLength = addressText.length();
    addressText.delete(addressTextLength - 2, addressTextLength);
    return addressText.toString();
  }

  private Intent getResultIntent() {
    final Intent intent = new Intent();
    intent.putExtra(EXTRA_LOCATION, etAddress.getText().toString());
    return intent;
  }
}
