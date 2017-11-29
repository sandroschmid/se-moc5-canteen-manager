package com.example.canteenchecker.canteenmanager.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * @author sschmid
 */
public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayout());

    initView();
    if (savedInstanceState != null) {
      restoreSavedState(savedInstanceState);
    }
  }

  protected abstract int getLayout();

  protected abstract void initView();

  protected abstract void restoreSavedState(final Bundle savedInstanceState);
}
