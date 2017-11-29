package com.example.canteenchecker.canteenmanager.app.form;

import android.os.Bundle;
import android.view.View;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author sschmid
 */
public abstract class FormInput<TInput extends FormInput<TInput, ?, ?>, TView extends View, TValue> {

  public static SortedSet<FormInput<?, ?, ?>> sort(final Collection<FormInput<?,?,?>> inputs) {
    final SortedSet<FormInput<?, ?, ?>> set = new TreeSet(new Comparator<FormInput<?, ?, ?>>() {
      @Override
      public int compare(final FormInput<?, ?, ?> i1, final FormInput<?, ?, ?> i2) {
        return i1.getOrder() < i2.getOrder() ? -1 : 1;
      }
    });

    set.addAll(inputs);
    return set;
  }

  public interface Validator<TFormInput extends FormInput<?, ?, ?>> {
    boolean isValid(TFormInput input);

    void showError(TFormInput input);
  }

  final int order;
  final TView view;
  final Validator<TInput> validator;

  public FormInput(final int order, final TView view) {
    this(order, view, null);
  }

  public FormInput(final int order, final TView view, final Validator<TInput> validator) {
    this.order = order;
    this.view = view;
    this.validator = validator;
  }

  public int getOrder() {
    return order;
  }

  public TView getView() {
    return view;
  }

  public abstract void setValue(TValue value);

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
