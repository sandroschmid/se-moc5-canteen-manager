package com.example.canteenchecker.canteenmanager.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author sschmid
 */
public abstract class BaseFragment extends Fragment {

  @Nullable
  @Override
  public View onCreateView(
      final LayoutInflater inflater,
      @Nullable final ViewGroup container,
      @Nullable final Bundle savedInstanceState
  ) {
    final View view = inflater.inflate(getLayout(), container, false);
    initView(view);

    if (savedInstanceState != null) {
      restoreSavedState(savedInstanceState);
    }

    initEventReceivers();
    return view;
  }

  protected abstract int getLayout();

  protected abstract void initView(View view);

  protected void initEventReceivers() {
    // dummy
  }

  protected abstract void restoreSavedState(final Bundle savedInstanceState);
}
