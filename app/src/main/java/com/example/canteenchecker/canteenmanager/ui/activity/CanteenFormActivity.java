package com.example.canteenchecker.canteenmanager.ui.activity;

import android.view.Menu;
import android.view.MenuItem;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.R;

/**
 * @author sschmid
 */
public final class CanteenFormActivity extends BaseFormActivity {

  @Override
  protected int getLayout() {
    return R.layout.activity_canteen_form;
  }

  @Override
  protected void initView() {
    super.initView();
  }

  @Override
  public boolean onCreateOptionsMenu(final Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menuSignOut:
        App.getInstance().getSecurityManager().signOut();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  protected void submit() {

  }
}
