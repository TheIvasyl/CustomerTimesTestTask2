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

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    RecyclerView recyclerView;

    private AccountRecyclerViewAdapter recyclerViewAdapter;

    private MainPresenter mPresenter;

    private PublishProcessor<Integer> paginator = PublishProcessor.create();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    private final int VISIBLE_THRESHOLD = 1;

    private int lastVisibleItem;
    private int totalItemCount;
    private int pageNumber = 1;

    private List<AccountModel> lastLoadedPage;
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


        setUpRecyclerViewAdapter();

        createDatabaseAndLoadData();

        mPresenter.setView(this);

        setUpLoadMoreListener();
        subscribeForData();

    }

    private void setUpRecyclerViewAdapter() {
        recyclerViewAdapter = new AccountRecyclerViewAdapter();
        if (recyclerView != null){
            recyclerView.setAdapter(recyclerViewAdapter);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
        }
        else Log.d("ACTIVITY", "RECYCLER VIEW IS NULL");
    }

    private void createDatabaseAndLoadData() {
        mPresenter.loadDescribe();
        mPresenter.loadQuery();
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
    public void setLastLoadedPage(List<AccountModel> page) {
        lastLoadedPage = page;
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
                    paginator.onNext(pageNumber);
                    loading = true;
                }
            }
        });
    }

    /**
     * subscribing for data
     */
    private void subscribeForData() {

        Disposable disposable = paginator
                .onBackpressureDrop()
                .concatMap(new Function<Integer, Publisher<List<AccountModel>>>() {
                    @Override
                    public Publisher<List<AccountModel>> apply(@NonNull Integer page) {
                        loading = true;
                        return dataFromNetwork(page);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AccountModel>>() {
                    @Override
                    public void accept(@NonNull List<AccountModel> items) {
                        recyclerViewAdapter.addNewItems(items);
                        Log.d("TAG", "items added");
                        recyclerViewAdapter.notifyDataSetChanged();
                        loading = false;
                    }
                });

        compositeDisposable.add(disposable);

        paginator.onNext(pageNumber);

    }

    private Flowable<List<AccountModel>> dataFromNetwork(final int page) {
        return Flowable.just(true)
                .delay(2, TimeUnit.SECONDS)
                .map(new Function<Boolean, List<AccountModel>>() {
                    @Override
                    public List<AccountModel> apply(@NonNull Boolean value) {
                        final List<AccountModel> items = loadAccountsPage(page);
                        return items;
                    }
                });
    }

    private List<AccountModel> loadAccountsPage(int page){
        mPresenter.loadPageFromDb(page);
        //List<AccountModel> list = new ArrayList<>();
        //Map<String, String> map = new HashMap<>();
        //map.put("Id", "ASDASDASD");
        //list.add(new AccountModel(map));
        return lastLoadedPage;
    }
}
