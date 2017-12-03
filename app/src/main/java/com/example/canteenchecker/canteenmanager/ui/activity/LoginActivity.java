package com.example.canteenchecker.canteenmanager.ui.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.app.event.BaseRequestResultEvent;
import com.example.canteenchecker.canteenmanager.app.event.EventReceiver;
import com.example.canteenchecker.canteenmanager.app.form.TextInput;
import com.example.canteenchecker.canteenmanager.app.request.AuthenticateRequest;

/**
 * @author sschmid
 */
public final class LoginActivity extends BaseFormActivity {

  private EventReceiver<BaseRequestResultEvent.RequestResult<String>> authEventReceiver;

  private static final String TAG = LoginActivity.class.getName();

  @Override
  protected int getLayout() {
    return R.layout.activity_login;
  }

  @Override
  protected void initView() {
    super.initView();

    int order = 1;
    addInput(TextInput.createRequiredTextInput(order++, (AppCompatEditText) findViewById(R.id.etUsername)));

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

    addInput(TextInput.createRequiredTextInput(order, etPassword));
  }

  @Override
  protected void initEventReceivers() {
    super.initEventReceivers();
    authEventReceiver = new EventReceiver<BaseRequestResultEvent.RequestResult<String>>() {
      @Override
      public void onNewEvent(final BaseRequestResultEvent.RequestResult<String> authResult) {
        if (authResult.isSuccessful()) {
          App.getInstance().getSecurityManager().onAuthenticated(authResult.getData());
          startActivity(new Intent(LoginActivity.this, App.HOME_ACTIVITY));
        } else {
          stopLoading();
        }
      }
    };

    App.getInstance().getEventManager().getAuthenticatedEvent().register(authEventReceiver);
  }

  @Override
  public void onRefresh() {
    // nothing to do
  }

  @Override
  public void submit() {
    final String userName = this.<TextInput>getInput(R.id.etUsername).getValue();
    final String password = this.<TextInput>getInput(R.id.etPassword).getValue();

    new AuthenticateRequest(this, userName, password).send();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    App.getInstance().getEventManager().getAuthenticatedEvent().unregister(authEventReceiver);
  }
}
