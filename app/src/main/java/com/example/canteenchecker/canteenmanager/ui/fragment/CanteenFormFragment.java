package com.example.canteenchecker.canteenmanager.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.app.entity.Canteen;
import com.example.canteenchecker.canteenmanager.app.form.TextInput;
import com.example.canteenchecker.canteenmanager.app.request.GetAdminCanteenRequest;
import com.example.canteenchecker.canteenmanager.event.BaseRequestResultEvent;
import com.example.canteenchecker.canteenmanager.event.EventReceiver;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author sschmid
 */
public final class CanteenFormFragment extends BaseFormFragment implements SwipeRefreshLayout.OnRefreshListener {

  private static final String TAG = CanteenFormFragment.class.getName();

  private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);

  private EventReceiver<BaseRequestResultEvent.RequestResult<Canteen>> adminCanteenEventReceiver;

  @Override
  public void submit() {
    // TODO
  }

  @Override
  protected int getLayout() {
    return R.layout.fragment_canteen_form;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    App.getInstance().getEventManager().getAdminCanteenReceivedEvent().unregister(adminCanteenEventReceiver);
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
    addInput(TextInput.createRequiredTextInput(++order, (AppCompatEditText) view.findViewById(R.id.etName)));
    addInput(new TextInput(++order, (AppCompatEditText) view.findViewById(R.id.etMeal)));
    addInput(new TextInput(++order, (AppCompatEditText) view.findViewById(R.id.etMealPrice)));
    addInput(new TextInput(++order, (AppCompatEditText) view.findViewById(R.id.etAddress)));
    addInput(new TextInput(++order, (AppCompatEditText) view.findViewById(R.id.etWebsite)));
    addInput(new TextInput(++order, (AppCompatEditText) view.findViewById(R.id.etPhone)));

//    onRefresh();
    new GetAdminCanteenRequest(getContext()).send();
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
          Toast.makeText(getContext(), R.string.app_error_load_failure_admin_canteen, Toast.LENGTH_SHORT).show();
        }
      }
    };

    App.getInstance().getEventManager().getAdminCanteenReceivedEvent().register(adminCanteenEventReceiver);
  }

  private void showData(final Canteen data) {
    this.<TextInput>getInput(R.id.etName).getView().setText(data.getName());
    this.<TextInput>getInput(R.id.etMeal).getView().setText(data.getSetMeal());
    this.<TextInput>getInput(R.id.etMealPrice).getView().setText(currencyFormat.format(data.getSetMealPrice()));
    this.<TextInput>getInput(R.id.etAddress).getView().setText(data.getAddress());
    this.<TextInput>getInput(R.id.etWebsite).getView().setText(data.getWebsite());
    this.<TextInput>getInput(R.id.etPhone).getView().setText(data.getName());
  }
}
