package com.iyakoviv.customertimestest.presentation.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.iyakoviv.customertimestest.R;
import com.iyakoviv.customertimestest.data.RepositoryImpl;
import com.iyakoviv.customertimestest.domain.executor.impl.ThreadExecutor;
import com.iyakoviv.customertimestest.presentation.presenters.SplashPresenter;
import com.iyakoviv.customertimestest.presentation.presenters.impl.SplashPresenterImpl;
import com.iyakoviv.customertimestest.threading.MainThreadImpl;

public class SplashScreenActivity extends AppCompatActivity implements SplashPresenter.View{

  private SplashPresenter mPresenter;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);

    mPresenter = new SplashPresenterImpl(
            ThreadExecutor.getInstance(),
            MainThreadImpl.getInstance(),
            this,
            new RepositoryImpl(this));

    createDatabaseAndLoadData();
  }

  @Override
  public void goToMainActivity(){
    Log.d("ACTIVITY", "GOING TO MAINT ACTIVITY");
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }


  private void createDatabaseAndLoadData() {
    mPresenter.loadDescribe();
  }

  @Override
  public void showProgress() {

  }

  @Override
  public void hideProgress() {

  }

  @Override
  public void showError(String message) {

  }
}
