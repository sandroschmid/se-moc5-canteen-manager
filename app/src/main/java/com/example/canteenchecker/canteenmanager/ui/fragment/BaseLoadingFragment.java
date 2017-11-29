package com.example.canteenchecker.canteenmanager.ui.fragment;

import android.view.View;

import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.ui.utility.LoadingViewHandler;

/**
 * @author sschmid
 */
public abstract class BaseLoadingFragment extends BaseFragment implements LoadingViewHandler.LoadingView {

  private final LoadingViewHandler loadingViewHandler = new LoadingViewHandler(getContext(), this);

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
  protected void initView(final View view) {
    vProgress = view.findViewById(R.id.vProgress);
    vContent = view.findViewById(R.id.vContent);
    loadingViewHandler.initView();
  }
}
