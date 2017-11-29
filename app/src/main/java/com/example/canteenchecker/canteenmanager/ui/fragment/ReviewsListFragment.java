package com.example.canteenchecker.canteenmanager.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.canteenchecker.canteenmanager.R;

/**
 * @author sschmid
 */
public final class ReviewsListFragment extends BaseLoadingFragment implements SwipeRefreshLayout.OnRefreshListener {

  @Override
  public void onRefresh() {
    startLoading();
    // TODO
  }

  @Override
  protected int getLayout() {
    return R.layout.fragment_reviews_list;
  }

  @Override
  protected void restoreSavedState(final Bundle savedInstanceState) {
    // nothing to do
  }
}
