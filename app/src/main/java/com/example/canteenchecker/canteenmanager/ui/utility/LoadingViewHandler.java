package com.example.canteenchecker.canteenmanager.ui.utility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;

/**
 * @author sschmid
 */
public final class LoadingViewHandler {

  public interface LoadingView {
    View getProgressView();

    View getContentView();

    boolean isLoading();

    void startLoading();

    void stopLoading();
  }

  private final Context context;
  private final LoadingView loadingView;

  private boolean isLoading;
  private View vProgress;
  private View vContent;

  public LoadingViewHandler(final Context context, final LoadingView loadingView) {
    this.context = context;
    this.loadingView = loadingView;
  }

  public void initView() {
    vProgress = loadingView.getProgressView();
    vContent = loadingView.getContentView();
  }

  public boolean isLoading() {
    return isLoading;
  }

  public void startLoading() {
    showProgress(true);
  }

  public void stopLoading() {
    showProgress(false);
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
  private void showProgress(final boolean isShowing) {
    if (isLoading == isShowing) {
      return;
    }

    isLoading = isShowing;
    // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
    // for very easy animations. If available, use these APIs to fade-in
    // the progress spinner.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
      int shortAnimTime = context.getResources().getInteger(android.R.integer.config_shortAnimTime);

      vContent.setVisibility(isShowing ? View.GONE : View.VISIBLE);
      vContent.animate().setDuration(shortAnimTime).alpha(
          isShowing ? 0 : 1).setListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          vContent.setVisibility(isShowing ? View.GONE : View.VISIBLE);
        }
      });

      vProgress.setVisibility(isShowing ? View.VISIBLE : View.GONE);
      vProgress.animate().setDuration(shortAnimTime).alpha(
          isShowing ? 1 : 0).setListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          vProgress.setVisibility(isShowing ? View.VISIBLE : View.GONE);
        }
      });
    } else {
      // The ViewPropertyAnimator APIs are not available, so simply show
      // and hide the relevant UI components.
      vProgress.setVisibility(isShowing ? View.VISIBLE : View.GONE);
      vContent.setVisibility(isShowing ? View.GONE : View.VISIBLE);
    }
  }
}
