package com.example.canteenchecker.canteenmanager.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

import com.example.canteenchecker.canteenmanager.R;

/**
 * @author sschmid
 */
public abstract class BaseLoadingActivity extends BaseActivity {

  private View vProgress;
  private View vContent;

  private boolean isLoading;

  @Override
  protected void initView() {
    vProgress = findViewById(getProgressViewId());
    vContent = findViewById(getContentViewId());
  }

  protected int getProgressViewId() {
    return R.id.vProgress;
  }

  protected int getContentViewId() {
    return R.id.vContent;
  }

  protected boolean isLoading() {
    return isLoading;
  }

  protected void startLoading() {
    showProgress(true);
  }

  protected void stopLoading() {
    showProgress(false);
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
  private void showProgress(final boolean isShowing) {
    isLoading = isShowing;
    // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
    // for very easy animations. If available, use these APIs to fade-in
    // the progress spinner.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
      int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

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
