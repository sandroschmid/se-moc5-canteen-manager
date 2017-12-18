package com.example.canteenchecker.canteenmanager.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.app.entity.Canteen;
import com.example.canteenchecker.canteenmanager.app.event.AdminCanteenReceivedEvent;
import com.example.canteenchecker.canteenmanager.app.event.BaseRequestResultEvent;
import com.example.canteenchecker.canteenmanager.app.event.EventReceiver;
import com.example.canteenchecker.canteenmanager.app.request.GetAdminCanteenRequest;

/**
 * @author sschmid
 */
public final class SplashActivity extends BaseActivity {

  private final AdminCanteenReceivedEvent adminCanteenReceivedEvent = App.getInstance()
      .getEventManager()
      .getAdminCanteenReceivedEvent();

  private EventReceiver<BaseRequestResultEvent.RequestResult<Canteen>> adminCanteenEventReceiver;

  @Override
  public void onDestroy() {
    super.onDestroy();
    adminCanteenReceivedEvent.unregister(adminCanteenEventReceiver);
  }

  @Override
  protected int getLayout() {
    return R.layout.activity_splash;
  }

  @Override
  protected void setViewData() {
    final App app = App.getInstance();
    if (app.getSecurityManager().isAuthenticated()) {
      new GetAdminCanteenRequest(this).send();
    } else {
      waitAndLogout();
    }
  }

  @Override
  protected void initEventReceivers() {
    super.initEventReceivers();
    adminCanteenEventReceiver = new EventReceiver<BaseRequestResultEvent.RequestResult<Canteen>>() {
      @Override
      public void onNewEvent(final BaseRequestResultEvent.RequestResult<Canteen> result) {
        if (result.isSuccessful()) {
          CanteenFormActivity.show(SplashActivity.this, result.getData());
        } else {
          Toast.makeText(SplashActivity.this, R.string.app_error_load_failure_admin_canteen, Toast.LENGTH_SHORT).show();
        }
      }
    };

    adminCanteenReceivedEvent.register(adminCanteenEventReceiver);
  }

  @Override
  protected void restoreSavedState(final Bundle savedInstanceState) {
    // nothing to do
  }

  private void waitAndLogout() {
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(final Void... voids) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          // ignore
        }
        return null;
      }

      @Override
      protected void onPostExecute(final Void result) {
        logout();
      }
    }.execute();
  }

  private void logout() {
    App.getInstance().getEventManager().getSignedOutEvent().send();
  }
}
