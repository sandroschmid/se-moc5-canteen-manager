package com.example.canteenchecker.canteenmanager.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.app.form.FormInput;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sschmid
 */
public abstract class BaseFormActivity extends BaseLoadingActivity {

  private final Map<Integer, FormInput<?, ?, ?>> inputs = new HashMap<>();

  private View vSubmit;

  @Override
  protected void initView() {
    super.initView();

    vSubmit = findViewById(getSubmitViewId());
    vSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        trySubmit();
      }
    });
  }

  @Override
  protected void restoreSavedState(final Bundle savedInstanceState) {
    for (final FormInput<?, ?, ?> input : inputs.values()) {
      input.restoreState(savedInstanceState);
    }
  }

  @Override
  protected void onSaveInstanceState(final Bundle outState) {
    super.onSaveInstanceState(outState);

    for (final FormInput<?, ?, ?> input : inputs.values()) {
      input.saveState(outState);
    }
  }

  protected int getSubmitViewId() {
    return R.id.vSubmit;
  }

  protected <TInput extends FormInput<TInput, ?, ?>> TInput getInput(final int resId) {
    // noinspection unchecked
    return (TInput) inputs.get(resId);
  }

  protected void addInput(final FormInput<?, ?, ?> input) {
    final Integer resId = input.getView().getId();
    if (inputs.containsKey(resId)) {
      throw new RuntimeException(String.format("Input view with ID %s is already set", resId));
    }

    inputs.put(resId, input);
  }

  protected final void removeErrors() {
    for (final FormInput<?, ?, ?> input : inputs.values()) {
      input.removeErrors();
    }
  }

  protected final void trySubmit() {
    removeErrors();

    final View firstInvalidView = validateForm();
    if (firstInvalidView == null) {
      startLoading();
      submit();
    } else {
      firstInvalidView.requestFocus();
    }
  }

  protected abstract void submit();

  private View validateForm() {
    View firstInvalidView = null;
    for (final FormInput<?, ?, ?> input : FormInput.sort(inputs.values())) {
      if (!input.validate() && firstInvalidView == null) {
        firstInvalidView = input.getView();
      }
    }
    return firstInvalidView;
  }
}
