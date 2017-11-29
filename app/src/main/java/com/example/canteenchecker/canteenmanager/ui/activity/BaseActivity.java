package com.example.canteenchecker.canteenmanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.event.EventReceiver;

/**
 * @author sschmid
 */
public abstract class BaseActivity extends AppCompatActivity {

  private EventReceiver<Void> signedOutEventReceiver;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayout());

    initView();
    if (savedInstanceState != null) {
      restoreSavedState(savedInstanceState);
    }

    signedOutEventReceiver = new EventReceiver<Void>() {
      @Override
      public void onNewEvent(final Void result) {
        startActivity(new Intent(BaseActivity.this, LoginActivity.class));
      }
    };

    App.getInstance().getEventManager().getSignedOutEvent().register(signedOutEventReceiver);
  }

  @Override
  protected void onResume() {
    super.onResume();
    App.getInstance().getAuthenticationGuard().canVisit(getClass().getName());
  }

  protected abstract int getLayout();

  protected abstract void initView();

  protected abstract void restoreSavedState(final Bundle savedInstanceState);

  @Override
  protected void onDestroy() {
    super.onDestroy();
    App.getInstance().getEventManager().getSignedOutEvent().unregister(signedOutEventReceiver);
  }
}
