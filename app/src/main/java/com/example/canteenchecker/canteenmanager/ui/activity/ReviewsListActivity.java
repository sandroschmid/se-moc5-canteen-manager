package com.example.canteenchecker.canteenmanager.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.ui.fragment.ReviewsListFragment;

/**
 * @author  sschmid
 */
public final class ReviewsListActivity extends BaseLoadingActivity {

  public static void show(final Context context) {
    context.startActivity(new Intent(context, ReviewsListActivity.class));
  }

  private SwipeRefreshLayout swipeRefreshLayout;
  private ReviewsListFragment reviewsListFragment;

  @Override
  protected int getLayout() {
    return R.layout.activity_reviews_list;
  }

  @Override
  protected void initView() {
    super.initView();

    swipeRefreshLayout = findViewById(R.id.swipeRefresh);
    swipeRefreshLayout.setOnRefreshListener(this);

    reviewsListFragment = new ReviewsListFragment();
    reviewsListFragment.setArguments(getIntent().getExtras());

    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.fragReviewList, reviewsListFragment)
        .commit();
  }

  @Override
  protected void restoreSavedState(final Bundle savedInstanceState) {
    // nothing to do
  }

  @Override
  public void onRefresh() {
    swipeRefreshLayout.setRefreshing(false);
    reviewsListFragment.onRefresh();
  }
}
