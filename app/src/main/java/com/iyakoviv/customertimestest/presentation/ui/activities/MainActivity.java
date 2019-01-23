package com.iyakoviv.customertimestest.presentation.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.iyakoviv.customertimestest.R;
import com.iyakoviv.customertimestest.data.RepositoryImpl;
import com.iyakoviv.customertimestest.data.datasource.RestClient;
import com.iyakoviv.customertimestest.data.datasource.services.DescribeService;
import com.iyakoviv.customertimestest.domain.executor.MainThread;
import com.iyakoviv.customertimestest.domain.executor.impl.ThreadExecutor;
import com.iyakoviv.customertimestest.presentation.presenters.MainPresenter;
import com.iyakoviv.customertimestest.presentation.presenters.MainPresenter.View;
import com.iyakoviv.customertimestest.presentation.presenters.impl.MainPresenterImpl;
import com.iyakoviv.customertimestest.threading.MainThreadImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new RepositoryImpl(this));


        Log.d("ACTIVITY", "presenterCreated");
        mPresenter.loadDescribe();
        mPresenter.loadQuery();
        mPresenter.loadPageFromDb(0);

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

    @Override
    public void setText(String text) {
        TextView view = findViewById(R.id.text_view);
        view.setText(text);
    }
}
