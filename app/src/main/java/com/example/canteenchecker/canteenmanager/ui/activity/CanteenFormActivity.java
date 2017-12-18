package com.example.canteenchecker.canteenmanager.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.app.entity.Canteen;
import com.example.canteenchecker.canteenmanager.ui.fragment.CanteenFormFragment;
import com.example.canteenchecker.canteenmanager.ui.fragment.ReviewsListFragment;

/**
 * @author sschmid
 */
public final class CanteenFormActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

  private static final String EXTRA_CANTEEN = "EXTRA_CANTEEN";
  private static final String EXTRA_REVIEWS_SHOWN = "EXTRA_REVIEWS_SHOWN";

  public static void show(final Context context) {
    show(context, null);
  }

  public static void show(final Context context, Canteen canteen) {
    final Intent intent = new Intent(context, CanteenFormActivity.class);
    if (canteen != null) {
      intent.putExtra(EXTRA_CANTEEN, canteen);
    }
    context.startActivity(intent);
  }

  public static Canteen getCanteen(final Bundle args) {
    return args == null ? null : (Canteen) args.getParcelable(EXTRA_CANTEEN);
  }

  public static boolean hasReviews(final Bundle args) {
    return args != null && args.getBoolean(EXTRA_REVIEWS_SHOWN, false);
  }

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

    final View reviewListContainer = findViewById(R.id.fragReviewList);

    Bundle args = getIntent().getExtras();
    if (args == null) {
      args = new Bundle();
    }

    args.putBoolean(EXTRA_REVIEWS_SHOWN, reviewListContainer != null);

    canteenFormFragment = new CanteenFormFragment();
    canteenFormFragment.setArguments(args);

    final FragmentTransaction fragmentTransaction = getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.fragCanteenForm, canteenFormFragment);

    if (reviewListContainer != null) {
      reviewsListFragment = new ReviewsListFragment();
      reviewsListFragment.setArguments(args);
      fragmentTransaction.replace(R.id.fragReviewList, reviewsListFragment);
    }

    fragmentTransaction.commit();
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
