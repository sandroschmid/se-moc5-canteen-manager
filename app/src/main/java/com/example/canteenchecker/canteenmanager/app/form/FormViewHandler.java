package com.example.canteenchecker.canteenmanager.app.form;

import android.os.Bundle;
import android.view.View;

import com.example.canteenchecker.canteenmanager.app.utility.LoadingViewHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sschmid
 */
public final class FormViewHandler {

  public interface FormView extends LoadingViewHandler.LoadingView {
    View getSubmitView();

    void addInput(final FormInput<?, ?, ?> input);

    <TInput extends FormInput<TInput, ?, ?>> TInput getInput(final int resId);

    void removeErrors();

    void trySubmit();

    void submit();

    void reset();
  }

  private final Map<Integer, FormInput<?, ?, ?>> inputs = new HashMap<>();

  private final FormView formView;

  public FormViewHandler(final FormView formView) {
    this.formView = formView;
  }

  public void initView() {
    final View vSubmit = formView.getSubmitView();
    vSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        trySubmit();
      }
    });
  }

  public void restoreSavedState(final Bundle savedInstanceState) {
    for (final FormInput<?, ?, ?> input : inputs.values()) {
      input.restoreState(savedInstanceState);
    }
  }

  public void onSaveInstanceState(final Bundle outState) {
    for (final FormInput<?, ?, ?> input : inputs.values()) {
      input.saveState(outState);
    }
  }

  public <TInput extends FormInput<TInput, ?, ?>> TInput getInput(final int resId) {
    // noinspection unchecked
    return (TInput) inputs.get(resId);
  }

  public void addInput(final FormInput<?, ?, ?> input) {
    final Integer resId = input.getView().getId();
    if (inputs.containsKey(resId)) {
      throw new RuntimeException(String.format("Input view with ID %s is already set", resId));
    }

    inputs.put(resId, input);
  }

  public void reset() {
    for (final FormInput<?, ?, ?> input : inputs.values()) {
      input.reset();
    }
  }

  public final void removeErrors() {
    for (final FormInput<?, ?, ?> input : inputs.values()) {
      input.removeErrors();
    }
  }

  public void trySubmit() {
    removeErrors();

    final View firstInvalidView = validateForm();
    if (firstInvalidView == null) {
      formView.startLoading();
      formView.submit();
    } else {
      firstInvalidView.requestFocus();
    }
  }

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
