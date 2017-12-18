package com.example.canteenchecker.canteenmanager.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.canteenchecker.canteenmanager.App;

/**
 * @author sschmid
 */
public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (!App.getInstance().getAuthenticationGuard().canVisit(getClass().getName())) {
      return;
    }

    setContentView(getLayout());
    initView();
    initEventReceivers();

    if (savedInstanceState != null) {
      restoreSavedState(savedInstanceState);
    }

    setViewData();
  }

  protected abstract int getLayout();

  protected void initView(){
    // dummy
  }

  protected void setViewData() {
    // dummy
  }

  protected void initEventReceivers() {
    // dummy
  }

  protected abstract void restoreSavedState(final Bundle savedInstanceState);
}
