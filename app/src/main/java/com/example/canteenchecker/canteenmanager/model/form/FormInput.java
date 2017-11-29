package com.example.canteenchecker.canteenmanager.model.form;

import android.os.Bundle;
import android.view.View;

/**
 * @author sschmid
 */
public abstract class FormInput<TInput extends FormInput<TInput, ?, ?>, TView extends View, TValue> {

  public interface Validator<TFormInput extends FormInput<?, ?, ?>> {
    boolean isValid(TFormInput input);

    void showError(TFormInput input);
  }

  final TView view;
  final Validator<TInput> validator;

  public FormInput(final TView view) {
    this(view, null);
  }

  public FormInput(final TView view, final Validator<TInput> validator) {
    this.view = view;
    this.validator = validator;
  }

  public TView getView() {
    return view;
  }

  public abstract TValue getValue();

  public final boolean isValid() {
    // noinspection unchecked
    return validator == null || validator.isValid((TInput) this);
  }

  public final boolean validate() {
    if (!isValid()) {
      // noinspection unchecked
      validator.showError((TInput) this);
      return false;
    }
    return true;
  }

  public void removeErrors() {
    // dummy
  }

  public abstract void saveState(Bundle outState);

  public abstract void restoreState(Bundle savedInstanceState);

  final String getStateKey() {
    return String.format("form_input_%s", view.getId());
  }
}
