package com.example.canteenchecker.canteenmanager.service.firebase;

import android.util.Log;

import com.example.canteenchecker.canteenmanager.app.request.GetAdminCanteenRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public final class PushService extends FirebaseMessagingService {

  private static final String TAG = PushService.class.getName();

  private static final String TYPE_KEY = "type";
  private static final String TYPE_VALUE = "canteenDataChanged";
  private static final String CANTEEN_ID_KEY = "canteenId";

  @Override
  public void onMessageReceived(final RemoteMessage remoteMessage) {
    Log.d(
        TAG,
        String.format("Received firebase message from %s at %s",
            remoteMessage.getFrom(),
            SimpleDateFormat.getDateTimeInstance().format(new Date(remoteMessage.getSentTime()))
        )
    );

    final Map<String, String> data = remoteMessage.getData();
    if (TYPE_VALUE.equals(data.get(TYPE_KEY))) {
      new GetAdminCanteenRequest(this).send();
    }
  }

  @Override
  public void onDeletedMessages() {
    super.onDeletedMessages();
    Log.w(TAG, "Firebase messages deleted");
    new GetAdminCanteenRequest(this).send();
  }
}
