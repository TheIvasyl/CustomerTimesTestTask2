package com.iyakoviv.customertimestest.presentation.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iyakoviv.customertimestest.R;
import com.iyakoviv.customertimestest.domain.model.AccountModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountRecyclerViewAdapter extends RecyclerView.Adapter<AccountRecyclerViewAdapter.ViewHolder> {

  private List<AccountModel> listElements = new ArrayList<>();
  private boolean allItemsLoaded;

  public AccountRecyclerViewAdapter() {
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.list_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    viewHolder.item = listElements.get(i);

    String idText = viewHolder.item.getValue().get("Id");

    if (viewHolder.accountText == null){
      return;
    }
    viewHolder.accountText.setText(idText);
  }

  @Override
  public int getItemCount() {
    return listElements.size();
  }


  public void addNewItems(List<AccountModel> page) {
    if (page.size() == 0) {
      allItemsLoaded = true;
      return;
    }
    listElements.addAll(page);
    Log.d("ADAPTER", "ADDED ANOTHER PAGE");
  }

  public boolean isAllItemsLoaded() {
    return allItemsLoaded;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.accountText)
    TextView accountText;

    AccountModel item;
    View view;
    Context context;

    public ViewHolder(View itemView) {
      super(itemView);
      context = itemView.getContext();
      view = itemView;
      ButterKnife.bind(this, itemView);
    }
  }
}
