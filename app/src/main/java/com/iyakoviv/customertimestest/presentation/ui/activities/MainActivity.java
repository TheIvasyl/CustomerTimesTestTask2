package com.iyakoviv.customertimestest.presentation.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.iyakoviv.customertimestest.R;
import com.iyakoviv.customertimestest.data.RepositoryImpl;
import com.iyakoviv.customertimestest.domain.executor.impl.ThreadExecutor;
import com.iyakoviv.customertimestest.domain.model.AccountModel;
import com.iyakoviv.customertimestest.presentation.presenters.MainPresenter;
import com.iyakoviv.customertimestest.presentation.presenters.impl.MainPresenterImpl;
import com.iyakoviv.customertimestest.presentation.ui.adapter.AccountRecyclerViewAdapter;
import com.iyakoviv.customertimestest.threading.MainThreadImpl;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    RecyclerView recyclerView;

    private AccountRecyclerViewAdapter recyclerViewAdapter;

    private MainPresenter mPresenter;

    private final int VISIBLE_THRESHOLD = 1;

    private int lastVisibleItem;
    private int totalItemCount;
    private int pageNumber = 1;

    private boolean loading = false;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        mPresenter = new MainPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new RepositoryImpl(this));

        Log.d("ACTIVITY", "PRESENTER CREATED");

        mPresenter.setView(this);
        mPresenter.loadPageFromDb(pageNumber);

        setUpRecyclerViewAdapter();
        setUpLoadMoreListener();

    }

    private void setUpRecyclerViewAdapter() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new AccountRecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
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
    }

    @Override
    public void addNewItems(List<AccountModel> page) {
        recyclerViewAdapter.addNewItems(page);
        Log.d("ACTIVITY", "items added");
        recyclerViewAdapter.notifyDataSetChanged();
        loading = false;
    }


    private void setUpLoadMoreListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager
                        .findLastVisibleItemPosition();
                if (!loading
                        && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                    pageNumber++;
                    loading = true;
                    mPresenter.loadPageFromDb(pageNumber);
                }
            }
        });
    }

}
