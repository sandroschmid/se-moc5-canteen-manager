package com.example.canteenchecker.canteenmanager.app.form;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;

/**
 * @author sschmid
 */
public final class SeekBarInput extends FormInput<SeekBarInput, AppCompatSeekBar, Integer> {

  public SeekBarInput(final int order, final AppCompatSeekBar view) {
    super(order, view);
  }

  @Override
  public void setValue(final Integer progress) {
    view.setProgress(progress);
  }

  @Override
  public Integer getValue() {
    return view.getProgress();
  }

  @Override
  public void removeErrors() {
    // has no errors
  }

  @Override
  public void saveState(final Bundle outState) {
    outState.putInt(getStateKey(), getValue());
  }

  @Override
  public void restoreState(final Bundle savedInstanceState) {
    view.setProgress(savedInstanceState.getInt(getStateKey()));
  }
}
