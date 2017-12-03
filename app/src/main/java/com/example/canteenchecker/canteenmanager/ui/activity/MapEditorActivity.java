package com.example.canteenchecker.canteenmanager.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.widget.Toast;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.app.event.BaseRequestResultEvent;
import com.example.canteenchecker.canteenmanager.app.event.CoordinatesReceivedEvent;
import com.example.canteenchecker.canteenmanager.app.event.EventReceiver;
import com.example.canteenchecker.canteenmanager.app.request.CoordinatesRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author sschmid
 */
public final class MapEditorActivity extends BaseLoadingActivity {

  private static final String EXTRA_LOCATION = "EXTRA_LOCATION";
  private static final float DEFAULT_MAP_ZOOM_FACTOR = 15f;

  public static void show(final Context context, final String address) {
    final Intent intent = new Intent(context, MapEditorActivity.class);
    intent.putExtra(EXTRA_LOCATION, address);
    context.startActivity(intent);
  }

  private SupportMapFragment mapFragment;
  private AppCompatTextView txtLocation;

  private final CoordinatesReceivedEvent coordinatesReceivedEvent = App.getInstance()
      .getEventManager()
      .getCoordinatesReceivedEvent();
  private EventReceiver<BaseRequestResultEvent.RequestResult<LatLng>> latLngEventReceiver;

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
        final UiSettings settings = map.getUiSettings();
        settings.setAllGesturesEnabled(false);
        settings.setZoomControlsEnabled(true);
        stopLoading();
      }
    });

    txtLocation = findViewById(R.id.txtLocation);

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
        stopLoading();
        if (result.isSuccessful()) {
          showData(result.getData());
        } else {
          Toast.makeText(
              MapEditorActivity.this,
              R.string.app_error_load_failure_admin_canteen,
              Toast.LENGTH_SHORT
          ).show();
        }
      }
    };

    coordinatesReceivedEvent.register(latLngEventReceiver);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    coordinatesReceivedEvent.unregister(latLngEventReceiver);
  }

  @Override
  public void onRefresh() {
    startLoading();
    final String location = getLocationFromIntent();
    txtLocation.setText(location);
    new CoordinatesRequest(this, location).send();
  }

  private String getLocationFromIntent() {
    return getIntent().getStringExtra(EXTRA_LOCATION);
  }

  private void showData(final LatLng latLng) {
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(final GoogleMap map) {
        if (latLng == null) {
          map.animateCamera(CameraUpdateFactory.newLatLngZoom(
              new LatLng(0, 0),
              DEFAULT_MAP_ZOOM_FACTOR
          ));
        } else {
          map.addMarker(new MarkerOptions().position(latLng));
          map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_MAP_ZOOM_FACTOR));
        }
      }
    });
  }
}
