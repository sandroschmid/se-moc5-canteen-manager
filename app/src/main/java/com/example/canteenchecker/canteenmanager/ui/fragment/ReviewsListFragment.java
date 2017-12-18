package com.example.canteenchecker.canteenmanager.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.app.entity.Canteen;
import com.example.canteenchecker.canteenmanager.app.entity.Rating;
import com.example.canteenchecker.canteenmanager.app.event.AdminCanteenReceivedEvent;
import com.example.canteenchecker.canteenmanager.app.event.BaseRequestResultEvent;
import com.example.canteenchecker.canteenmanager.app.event.EventReceiver;
import com.example.canteenchecker.canteenmanager.app.request.GetAdminCanteenRequest;
import com.example.canteenchecker.canteenmanager.ui.adapter.RatingsAdapter;

import java.util.ArrayList;

/**
 * @author sschmid
 */
public final class ReviewsListFragment extends BaseLoadingFragment implements SwipeRefreshLayout.OnRefreshListener {

  private static final String STATE_RATINGS = "STATE_RATINGS";

  private final AdminCanteenReceivedEvent adminCanteenReceivedEvent = App.getInstance()
      .getEventManager()
      .getAdminCanteenReceivedEvent();

  private final RatingsAdapter ratingsAdapter = new RatingsAdapter();

  private EventReceiver<BaseRequestResultEvent.RequestResult<Canteen>> adminCanteenEventReceiver;
  private RecyclerView rvRatings;

  @Override
  public void onRefresh() {
    startLoading();
    new GetAdminCanteenRequest(getContext()).send();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    adminCanteenReceivedEvent.unregister(adminCanteenEventReceiver);
  }

  @Override
  protected void initView(final View view) {
    super.initView(view);

    rvRatings = view.findViewById(R.id.vContent);
    rvRatings.setLayoutManager(new LinearLayoutManager(getContext()));
    rvRatings.setItemAnimator(new DefaultItemAnimator());
    rvRatings.setAdapter(ratingsAdapter);
  }

  @Override
  protected void setViewData() {
    super.setViewData();
    // onRefresh();
  }

  @Override
  protected void initEventReceivers() {
    super.initEventReceivers();

    adminCanteenEventReceiver = new EventReceiver<BaseRequestResultEvent.RequestResult<Canteen>>() {
      @Override
      public void onNewEvent(final BaseRequestResultEvent.RequestResult<Canteen> result) {
        stopLoading();
        if (result.isSuccessful()) {
          setData(result.getData().getRatings());
        } else {
          Toast.makeText(
              getContext(),
              R.string.app_error_load_failure_admin_canteen_ratings,
              Toast.LENGTH_SHORT
          ).show();
        }
      }
    };

    adminCanteenReceivedEvent.register(adminCanteenEventReceiver);
  }

  @Override
  protected int getLayout() {
    return R.layout.fragment_reviews_list;
  }

  @Override
  protected void restoreSavedState(final Bundle savedInstanceState) {
    ratingsAdapter.setRatings(savedInstanceState.<Rating>getParcelableArrayList(STATE_RATINGS));
  }

  @Override
  public void onSaveInstanceState(@NonNull final Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelableArrayList(STATE_RATINGS, ratingsAdapter.getRatings());
  }

  private void setData(final ArrayList<Rating> ratings) {
    ratingsAdapter.setRatings(ratings);
    stopLoading();
  }
}
