package com.example.canteenchecker.canteenmanager.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.app.entity.Canteen;
import com.example.canteenchecker.canteenmanager.app.event.AdminCanteenReceivedEvent;
import com.example.canteenchecker.canteenmanager.app.event.BaseRequestResultEvent;
import com.example.canteenchecker.canteenmanager.app.event.EventReceiver;
import com.example.canteenchecker.canteenmanager.app.form.SeekBarInput;
import com.example.canteenchecker.canteenmanager.app.form.TextInput;
import com.example.canteenchecker.canteenmanager.app.request.GetAdminCanteenRequest;
import com.example.canteenchecker.canteenmanager.app.request.PutAdminCanteenRequest;
import com.example.canteenchecker.canteenmanager.ui.activity.CanteenFormActivity;
import com.example.canteenchecker.canteenmanager.ui.activity.MapEditorActivity;
import com.example.canteenchecker.canteenmanager.ui.activity.ReviewsListActivity;

import java.text.NumberFormat;

/**
 * @author sschmid
 */
public final class CanteenFormFragment extends BaseFormFragment implements SwipeRefreshLayout.OnRefreshListener {

  private static final NumberFormat priceFormat = NumberFormat.getNumberInstance();

  private final AdminCanteenReceivedEvent adminCanteenReceivedEvent = App.getInstance()
      .getEventManager()
      .getAdminCanteenReceivedEvent();

  private EventReceiver<BaseRequestResultEvent.RequestResult<Canteen>> adminCanteenEventReceiver;
  private Canteen canteen;
  private FloatingActionButton btnShowReviews;

  private boolean hasShowReviewsButton;

  @Override
  public void setArguments(@Nullable final Bundle args) {
    super.setArguments(args);
    canteen = CanteenFormActivity.getCanteen(args);
    hasShowReviewsButton = !CanteenFormActivity.hasReviews(args);
  }

  @Override
  public void submit() {
    startLoading();
    canteen.setName(this.<TextInput>getInput(R.id.etName).getValue());
    canteen.setMeal(this.<TextInput>getInput(R.id.etMeal).getValue());
    canteen.setMealPrice(Float.parseFloat(this.<TextInput>getInput(R.id.etMealPrice).getValue()));
    canteen.setAddress(this.<TextInput>getInput(R.id.etAddress).getValue());
    canteen.setWebsite(this.<TextInput>getInput(R.id.etWebsite).getValue());
    canteen.setPhoneNumber(this.<TextInput>getInput(R.id.etPhone).getValue());
    canteen.setAverageWaitingTime(this.<SeekBarInput>getInput(R.id.sbAverageWaitingTime).getValue());
    new PutAdminCanteenRequest(getContext(), canteen).send();
  }

  @Override
  protected int getLayout() {
    return R.layout.fragment_canteen_form;
  }

  @Override
  public void onRefresh() {
    startLoading();
    new GetAdminCanteenRequest(getContext()).send();
  }

  @Override
  protected void initView(final View view) {
    super.initView(view);

    int order = 0;
    addInput(TextInput.createRequiredTextInput(
        ++order,
        (AppCompatEditText) view.findViewById(R.id.etName)
    ));
    addInput(new TextInput(++order, (AppCompatEditText) view.findViewById(R.id.etMeal)));
    addInput(new TextInput(++order, (AppCompatEditText) view.findViewById(R.id.etMealPrice)));
    addInput(new TextInput(++order, (AppCompatEditText) view.findViewById(R.id.etAddress)));
    addInput(new TextInput(++order, (AppCompatEditText) view.findViewById(R.id.etWebsite)));
    view.findViewById(R.id.btnMap).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View v) {
        showMap();
      }
    });

    addInput(new TextInput(++order, (AppCompatEditText) view.findViewById(R.id.etPhone)));

    final AppCompatTextView tvAvgWaitingTime = view.findViewById(R.id.tvAverageWaitingTime);
    final AppCompatSeekBar sbAvgWaitingTime = view.findViewById(R.id.sbAverageWaitingTime);
    addInput(new SeekBarInput(++order, sbAvgWaitingTime));
    sbAvgWaitingTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

      @Override
      public void onProgressChanged(final SeekBar seekBar,
                                    final int progress,
                                    final boolean fromUser) {
        tvAvgWaitingTime.setText(getResources().getQuantityString(
            R.plurals.app_canteen_label_avg_waiting_time,
            progress,
            progress
        ));
      }

      @Override
      public void onStartTrackingTouch(final SeekBar seekBar) {
        // ignore
      }

      @Override
      public void onStopTrackingTouch(final SeekBar seekBar) {
        // ignore
      }
    });

    btnShowReviews = view.findViewById(R.id.btnShowReviews);
    btnShowReviews.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View view) {
        ReviewsListActivity.show(getContext());
      }
    });
    btnShowReviews.setVisibility(hasShowReviewsButton ? View.VISIBLE : View.GONE);
  }

  @Override
  protected void setViewData() {
    super.setViewData();
    if (canteen == null) {
      onRefresh();
    } else {
      showData(canteen);
    }
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
              getContext(),
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

  private void showData(final Canteen data) {
    canteen = data;
    this.<TextInput>getInput(R.id.etName).setValue(data.getName());
    this.<TextInput>getInput(R.id.etMeal).setValue(data.getMeal());
    this.<TextInput>getInput(R.id.etMealPrice).setValue(priceFormat.format(data.getMealPrice()));
    this.<TextInput>getInput(R.id.etAddress).setValue(data.getAddress());
    this.<TextInput>getInput(R.id.etWebsite).setValue(data.getWebsite());
    this.<TextInput>getInput(R.id.etPhone).setValue(data.getPhoneNumber());
    this.<SeekBarInput>getInput(R.id.sbAverageWaitingTime).setValue(data.getAverageWaitingTime());
  }

  private void showMap() {
    MapEditorActivity.show(getContext(), canteen.getAddress());
  }
}
