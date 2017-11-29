package com.example.canteenchecker.canteenmanager.ui.activity;

import android.view.View;

import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.ui.utility.LoadingViewHandler;

/**
 * @author sschmid
 */
public abstract class BaseLoadingActivity extends BaseActivity implements LoadingViewHandler.LoadingView {

  private LoadingViewHandler loadingViewHandler;
  private View vProgress;
  private View vContent;

  @Override
  public View getProgressView() {
    return vProgress;
  }

  @Override
  public View getContentView() {
    return vContent;
  }

  @Override
  public boolean isLoading() {
    return loadingViewHandler.isLoading();
  }

  @Override
  public void startLoading() {
    loadingViewHandler.startLoading();
  }

  @Override
  public void stopLoading() {
    loadingViewHandler.stopLoading();
  }

  @Override
  protected void initView() {
    vProgress = findViewById(R.id.vProgress);
    vContent = findViewById(R.id.vContent);

    loadingViewHandler = new LoadingViewHandler(this, this);
    loadingViewHandler.initView();
  }
}
