package com.example.canteenchecker.canteenmanager.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.app.entity.Canteen;
import com.example.canteenchecker.canteenmanager.app.form.FormInput;
import com.example.canteenchecker.canteenmanager.app.form.SeekBarInput;
import com.example.canteenchecker.canteenmanager.app.form.TextInput;
import com.example.canteenchecker.canteenmanager.app.request.PutAdminCanteenRequest;
import com.example.canteenchecker.canteenmanager.ui.activity.BaseLoadingActivity;
import com.example.canteenchecker.canteenmanager.ui.activity.CanteenFormActivity;
import com.example.canteenchecker.canteenmanager.ui.activity.MapEditorActivity;
import com.example.canteenchecker.canteenmanager.ui.activity.ReviewsListActivity;

import java.text.NumberFormat;

/**
 * @author sschmid
 */
public final class CanteenFormFragment extends BaseFormFragment implements SeekBar.OnSeekBarChangeListener {

  private static final int REQUEST_CODE_ADDRESS = 1;
  private static final String STATE_CANTEEN = "STATE_CANTEEN";
  private static final NumberFormat priceFormat = NumberFormat.getNumberInstance();

  static {
    priceFormat.setMaximumFractionDigits(2);
  }

  private AppCompatTextView tvAvgWaitingTime;
  private FloatingActionButton btnShowReviews;

  private Canteen canteen;
  private boolean hasShowReviewsButton;

  @Override
  public void setArguments(@Nullable final Bundle args) {
    super.setArguments(args);
    canteen = CanteenFormActivity.getCanteen(args);
    hasShowReviewsButton = !CanteenFormActivity.hasReviews(args);
  }

  // region SeekBar.ChangeListener

  @Override
  public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
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

  // endregion

  @Override
  public void submit() {
    startLoading();
    canteen.setName(this.<TextInput>getInput(R.id.etName).getValue());
    canteen.setMeal(this.<TextInput>getInput(R.id.etMeal).getValue());
    final String mealPriceValue = this.<TextInput>getInput(R.id.etMealPrice).getValue();
    canteen.setMealPrice(mealPriceValue.isEmpty() ? 0 : Float.parseFloat(mealPriceValue));
    canteen.setAddress(this.<TextInput>getInput(R.id.etAddress).getValue());
    canteen.setWebsite(this.<TextInput>getInput(R.id.etWebsite).getValue());
    canteen.setPhoneNumber(this.<TextInput>getInput(R.id.etPhone).getValue());
    canteen.setAverageWaitingTime(this.<SeekBarInput>getInput(R.id.sbAverageWaitingTime).getValue());
    new PutAdminCanteenRequest(getContext(), canteen).send();
  }

  @Override
  public void onSaveInstanceState(@NonNull final Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(STATE_CANTEEN, canteen);
  }

  @Override
  public void restoreSavedState(final Bundle savedInstanceState) {
    setCanteen((Canteen) savedInstanceState.getParcelable(STATE_CANTEEN));
  }

  @Override
  public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_ADDRESS && resultCode == Activity.RESULT_OK) {
      final String address = MapEditorActivity.getResult(data);
      canteen.setAddress(address);
      this.<TextInput>getInput(R.id.etAddress).setValue(address);
    }
  }

  @Override
  protected int getLayout() {
    return R.layout.fragment_canteen_form;
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
    addInput(new TextInput(
        ++order,
        (AppCompatEditText) view.findViewById(R.id.etMealPrice),
        new FormInput.Validator<TextInput>() {
          @Override
          public boolean isValid(final TextInput input) {
            if (input.isEmpty()) {
              return true;
            }

            try {
              Float.parseFloat(input.getValue());
            } catch (NumberFormatException e) {
              return false;
            }

            return true;
          }

          @Override
          public void showError(final TextInput input) {
            final AppCompatEditText view = input.getView();
            view.setError(view.getContext().getString(R.string.app_error_field_invalid_float));
          }
        }
    ));
    addInput(new TextInput(++order, (AppCompatEditText) view.findViewById(R.id.etAddress)));
    addInput(new TextInput(++order, (AppCompatEditText) view.findViewById(R.id.etWebsite)));
    addInput(new TextInput(++order, (AppCompatEditText) view.findViewById(R.id.etPhone)));

    tvAvgWaitingTime = view.findViewById(R.id.tvAverageWaitingTime);
    final AppCompatSeekBar sbAvgWaitingTime = view.findViewById(R.id.sbAverageWaitingTime);
    addInput(new SeekBarInput(++order, sbAvgWaitingTime));
    sbAvgWaitingTime.setOnSeekBarChangeListener(this);

    view.findViewById(R.id.btnMap).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View v) {
        showMap();
      }
    });

    btnShowReviews = view.findViewById(R.id.btnShowReviews);
    btnShowReviews.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View view) {
        ReviewsListActivity.show(getContext(), canteen);
      }
    });
    btnShowReviews.setVisibility(hasShowReviewsButton ? View.VISIBLE : View.GONE);

    view.findViewById(R.id.btnViewWeb).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View view) {
        final Intent websiteIntent = new Intent(
            Intent.ACTION_VIEW,
            Uri.parse(CanteenFormFragment.this.<TextInput>getInput(R.id.etWebsite).getValue())
        );

        if (getContext().getPackageManager().queryIntentActivities(websiteIntent, 0).size() > 0) {
          startActivity(websiteIntent);
        } else {
          Toast.makeText(
              getContext(),
              R.string.app_error_cannot_show_website,
              Toast.LENGTH_SHORT
          ).show();
        }
      }
    });

    view.findViewById(R.id.btnCall).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View view) {
        final Intent websiteIntent = new Intent(
            Intent.ACTION_VIEW,
            Uri.parse(String.format(
                "tel:%s",
                CanteenFormFragment.this.<TextInput>getInput(R.id.etPhone).getValue()
            ))
        );

        if (getContext().getPackageManager().queryIntentActivities(websiteIntent, 0).size() > 0) {
          startActivity(websiteIntent);
        } else {
          Toast.makeText(
              getContext(),
              R.string.app_error_cannot_call,
              Toast.LENGTH_SHORT
          ).show();
        }
      }
    });
  }

  @Override
  protected void setViewData() {
    super.setViewData();
    setCanteen(canteen);
  }

  @Override
  public void startLoading() {
    ((BaseLoadingActivity) getActivity()).startLoading();
  }

  public void setCanteen(final Canteen data) {
    canteen = data;
    if (data != null) {
      this.<TextInput>getInput(R.id.etName).setValue(data.getName());
      this.<TextInput>getInput(R.id.etMeal).setValue(data.getMeal());
      this.<TextInput>getInput(R.id.etMealPrice).setValue(priceFormat.format(data.getMealPrice()));
      this.<TextInput>getInput(R.id.etAddress).setValue(data.getAddress());
      this.<TextInput>getInput(R.id.etWebsite).setValue(data.getWebsite());
      this.<TextInput>getInput(R.id.etPhone).setValue(data.getPhoneNumber());
      this.<SeekBarInput>getInput(R.id.sbAverageWaitingTime).setValue(data.getAverageWaitingTime());
    } else {
      reset();
    }
  }

  private void showMap() {
    final Intent intent = MapEditorActivity.createIntent(
        getContext(),
        this.<TextInput>getInput(R.id.etAddress).getValue()
    );
    startActivityForResult(intent, REQUEST_CODE_ADDRESS);
  }
}
