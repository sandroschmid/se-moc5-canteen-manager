package com.example.canteenchecker.canteenmanager.service.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * @author sschmid
 */
public final class InstanceIdService extends FirebaseInstanceIdService {

  private static final String TAG = InstanceIdService.class.getName();

  @Override
  public void onTokenRefresh() {
    final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    Log.e(TAG, "Refreshed token: " + refreshedToken);
  }
}
