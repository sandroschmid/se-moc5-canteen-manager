package com.example.canteenchecker.canteenmanager.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.app.entity.Canteen;
import com.example.canteenchecker.canteenmanager.ui.activity.CanteenFormActivity;
import com.example.canteenchecker.canteenmanager.ui.adapter.RatingsAdapter;

/**
 * @author sschmid
 */
public final class ReviewsListFragment extends BaseFragment {

  private static final String STATE_CANTEEN = "STATE_CANTEEN";

  private RatingsAdapter ratingsAdapter;
  private RecyclerView rvRatings;
  private Canteen canteen;

  @Override
  public void setArguments(@Nullable final Bundle args) {
    super.setArguments(args);
    canteen = CanteenFormActivity.getCanteen(args);
  }

  @Override
  public void onSaveInstanceState(@NonNull final Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(STATE_CANTEEN, canteen);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    ratingsAdapter.destroy();
  }

  @Override
  protected void initView(final View view) {
    super.initView(view);

    ratingsAdapter = new RatingsAdapter(view.getContext());
    rvRatings = view.findViewById(R.id.rvRatings);
    rvRatings.setLayoutManager(new LinearLayoutManager(getContext()));
    rvRatings.setItemAnimator(new DefaultItemAnimator());
    rvRatings.setAdapter(ratingsAdapter);
  }

  @Override
  protected void setViewData() {
    super.setViewData();
    setCanteen(canteen);
  }

  @Override
  protected int getLayout() {
    return R.layout.fragment_reviews_list;
  }

  @Override
  protected void restoreSavedState(final Bundle savedInstanceState) {
    setCanteen((Canteen) savedInstanceState.getParcelable(STATE_CANTEEN));
  }

  public void setCanteen(final Canteen canteen) {
    this.canteen = canteen;
    if (canteen != null) {
      ratingsAdapter.setRatings(canteen.getRatings());
    } else {
      ratingsAdapter.removeAll();
    }
  }
}
