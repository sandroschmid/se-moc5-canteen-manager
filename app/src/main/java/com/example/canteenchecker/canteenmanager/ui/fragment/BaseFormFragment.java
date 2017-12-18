package com.example.canteenchecker.canteenmanager.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.app.form.FormInput;
import com.example.canteenchecker.canteenmanager.ui.utility.FormViewHandler;

/**
 * @author sschmid
 */
public abstract class BaseFormFragment extends BaseLoadingFragment implements FormViewHandler.FormView {

  private final FormViewHandler formViewHandler = new FormViewHandler(this);

  private View vSubmit;

  @Override
  public View getSubmitView() {
    return vSubmit;
  }

  @Override
  public <TInput extends FormInput<TInput, ?, ?>> TInput getInput(final int resId) {
    // noinspection unchecked
    return (TInput) formViewHandler.getInput(resId);
  }

  @Override
  public void addInput(final FormInput<?, ?, ?> input) {
    formViewHandler.addInput(input);
  }

  @Override
  public void trySubmit() {
    formViewHandler.trySubmit();
  }

  @Override
  public final void removeErrors() {
    formViewHandler.removeErrors();
  }

  @Override
  public void reset() {
    formViewHandler.reset();
  }

  @Override
  public void onSaveInstanceState(final Bundle outState) {
    super.onSaveInstanceState(outState);
    formViewHandler.onSaveInstanceState(outState);
  }

  @Override
  public void restoreSavedState(final Bundle savedInstanceState) {
    formViewHandler.restoreSavedState(savedInstanceState);
  }

  @Override
  protected void initView(final View view) {
    super.initView(view);
    vSubmit = view.findViewById(R.id.vSubmit);
    formViewHandler.initView();
  }
}
