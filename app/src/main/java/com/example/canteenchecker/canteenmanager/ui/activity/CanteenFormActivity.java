package com.example.canteenchecker.canteenmanager.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.ui.fragment.CanteenFormFragment;
import com.example.canteenchecker.canteenmanager.ui.fragment.ReviewsListFragment;

/**
 * @author sschmid
 */
public final class CanteenFormActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

  private SwipeRefreshLayout swipeRefreshLayout;
  private CanteenFormFragment canteenFormFragment;
  private ReviewsListFragment reviewsListFragment;

  @Override
  protected int getLayout() {
    return R.layout.activity_canteen_form;
  }

  @Override
  protected void initView() {
    swipeRefreshLayout = findViewById(R.id.swipeRefresh);
    swipeRefreshLayout.setOnRefreshListener(this);

    final FragmentManager fragmentManager = getSupportFragmentManager();
    canteenFormFragment = (CanteenFormFragment) fragmentManager.findFragmentById(R.id.fragCanteenForm);
    reviewsListFragment = (ReviewsListFragment) fragmentManager.findFragmentById(R.id.fragReviewList);
  }

  @Override
  protected void restoreSavedState(final Bundle savedInstanceState) {
    // nothing to do
  }

  @Override
  public boolean onCreateOptionsMenu(final Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menuSignOut:
        App.getInstance().getSecurityManager().signOut();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onRefresh() {
    swipeRefreshLayout.setRefreshing(false);
    canteenFormFragment.onRefresh();
    if (reviewsListFragment != null) {
      reviewsListFragment.onRefresh();
    }
  }
}
