package com.example.canteenchecker.canteenmanager.ui.activity;

import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.model.form.TextInput;

/**
 * @author sschmid
 */
public class LoginActivity extends BaseFormActivity {

  private static final String TAG = LoginActivity.class.getName();

  @Override
  protected int getLayout() {
    return R.layout.activity_login;
  }

  @Override
  protected void initView() {
    super.initView();

    addInput(TextInput.createRequiredTextInput((AppCompatEditText) findViewById(R.id.etUsername)));

    final AppCompatEditText etPassword = findViewById(R.id.etPassword);
    etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
          trySubmit();
          return true;
        }
        return false;
      }
    });

    addInput(TextInput.createRequiredTextInput(etPassword));
  }

  @Override
  protected void submit() {
    final String email = this.<TextInput>getInput(R.id.etUsername).getValue();
    final String password = this.<TextInput>getInput(R.id.etPassword).getValue();

    Log.d(TAG, String.format("Authenticate using %s:%s", email, password));
  }
}
