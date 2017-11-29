package com.example.canteenchecker.canteenmanager.model.form;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;

import com.example.canteenchecker.canteenmanager.R;

/**
 * @author sschmid
 */
public final class TextInput extends FormInput<TextInput, AppCompatEditText, String> {

  private static final class RequiredValidator implements Validator<TextInput> {

    @Override
    public boolean isValid(final TextInput input) {
      return !TextUtils.isEmpty(input.getValue());
    }

    @Override
    public void showError(final TextInput input) {
      final AppCompatEditText view = input.getView();
      view.setError(view.getContext().getString(R.string.app_error_field_required));
    }
  }

  public static TextInput createRequiredTextInput(final AppCompatEditText view) {
    return new TextInput(view, new RequiredValidator());
  }

  public static TextInput createRequiredTextInput(final AppCompatEditText view, final Validator<TextInput> validator) {
    return new TextInput(view, new Validator<TextInput>() {
      private final RequiredValidator requiredValidator = new RequiredValidator();

      @Override
      public boolean isValid(final TextInput input) {
        return requiredValidator.isValid(input) && validator.isValid(input);
      }

      @Override
      public void showError(final TextInput input) {
        if (!requiredValidator.isValid(input)) {
          requiredValidator.showError(input);
          return;
        }

        validator.showError(input);
      }
    });
  }

  public TextInput(final AppCompatEditText view) {
    super(view);
  }

  public TextInput(final AppCompatEditText view, final Validator<TextInput> validator) {
    super(view, validator);
  }

  @Override
  public String getValue() {
    return view.getText().toString().trim();
  }

  @Override
  public void removeErrors() {
    view.setError(null);
  }

  @Override
  public void saveState(final Bundle outState) {
    outState.putString(getStateKey(), getValue());
  }

  @Override
  public void restoreState(final Bundle savedInstanceState) {
    view.setText(savedInstanceState.getString(getStateKey()));
  }
}
