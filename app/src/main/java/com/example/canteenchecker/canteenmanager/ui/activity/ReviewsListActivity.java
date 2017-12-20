package com.example.canteenchecker.canteenmanager.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.app.entity.Canteen;
import com.example.canteenchecker.canteenmanager.app.event.AdminCanteenReceivedEvent;
import com.example.canteenchecker.canteenmanager.app.event.BaseRequestResultEvent;
import com.example.canteenchecker.canteenmanager.app.event.EventReceiver;
import com.example.canteenchecker.canteenmanager.app.request.GetAdminCanteenRequest;
import com.example.canteenchecker.canteenmanager.ui.fragment.RatingsListFragment;

/**
 * @author sschmid
 */
public final class ReviewsListActivity extends BaseLoadingActivity {

  private static final String EXTRA_CANTEEN = "EXTRA_CANTEEN";

  public static void show(final Context context) {
    show(context, null);
  }

  public static void show(final Context context, final Canteen canteen) {
    final Intent intent = new Intent(context, ReviewsListActivity.class);
    if (canteen != null) {
      intent.putExtra(EXTRA_CANTEEN, canteen);
    }
    context.startActivity(intent);
  }

  private final AdminCanteenReceivedEvent adminCanteenReceivedEvent = App.getInstance()
      .getEventManager()
      .getAdminCanteenReceivedEvent();

  private EventReceiver<BaseRequestResultEvent.RequestResult<Canteen>> adminCanteenEventReceiver;

  private SwipeRefreshLayout swipeRefreshLayout;
  private RatingsListFragment ratingsListFragment;

  @Override
  protected int getLayout() {
    return R.layout.activity_reviews_list;
  }

  @Override
  protected void initView() {
    super.initView();

    swipeRefreshLayout = findViewById(R.id.swipeRefresh);
    swipeRefreshLayout.setOnRefreshListener(this);

    ratingsListFragment = new RatingsListFragment();
    ratingsListFragment.setArguments(getIntent().getExtras());

    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.fragReviewList, ratingsListFragment)
        .commit();
  }

  @Override
  protected void initEventReceivers() {
    super.initEventReceivers();
    adminCanteenEventReceiver = new EventReceiver<BaseRequestResultEvent.RequestResult<Canteen>>() {
      @Override
      public void onNewEvent(final BaseRequestResultEvent.RequestResult<Canteen> result) {
        stopLoading();
        if (result.isSuccessful()) {
          showData(result.getData());
        } else {
          Toast.makeText(
              ReviewsListActivity.this,
              R.string.app_error_load_failure_admin_canteen,
              Toast.LENGTH_SHORT
          ).show();
        }
      }
    };

    adminCanteenReceivedEvent.register(adminCanteenEventReceiver);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    adminCanteenReceivedEvent.unregister(adminCanteenEventReceiver);
  }

  @Override
  protected void restoreSavedState(final Bundle savedInstanceState) {
    // nothing to do
  }

  @Override
  public void onRefresh() {
    startLoading();
    swipeRefreshLayout.setRefreshing(false);
    new GetAdminCanteenRequest(this).send();
  }

  private void showData(final Canteen canteen) {
    ratingsListFragment.setCanteen(canteen);
  }
}
