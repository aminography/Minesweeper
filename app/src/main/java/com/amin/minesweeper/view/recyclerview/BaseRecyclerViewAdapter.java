package com.amin.minesweeper.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.amin.minesweeper.view.viewholders.BaseCellViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amin on 1/11/2016.
 */
public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    private LayoutInflater mLayoutInflater;
    protected List<BaseRecyclerViewModel> mModelsList = new ArrayList<>();

    public BaseRecyclerViewAdapter(Context context) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder viewHolder, int position) {
        viewHolder.setViewModel(mModelsList.get(position));
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createBaseRecyclerViewHolder(parent, viewType);
    }

    public abstract BaseCellViewHolder createBaseRecyclerViewHolder(ViewGroup parent, int viewType);

    @Override
    public int getItemCount() {
        return mModelsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mModelsList.get(position).getViewType();
    }

    public BaseRecyclerViewModel getItem(int position) {
        return mModelsList.get(position);
    }

    public void addItem(BaseRecyclerViewModel viewModel, int position) {
        mModelsList.add(position, viewModel);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mModelsList.remove(position);
        notifyItemRemoved(position);
    }

    public void replaceModelsList(List<BaseRecyclerViewModel> modelList) {
        mModelsList.clear();
        for (int i = 0; i < modelList.size(); i++) {
            mModelsList.add(modelList.get(i));
        }
        notifyDataSetChanged();
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }
}
