package com.amin.minesweeper.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by Amin on 1/11/2016.
 */
public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    private BaseRecyclerViewModel mViewModel;

    public BaseRecyclerViewHolder(LayoutInflater layoutInflater, ViewGroup parent, int layoutResourceID) {
        super(layoutInflater.inflate(layoutResourceID, parent, false));
    }

    public BaseRecyclerViewModel getViewModel() {
        return mViewModel;
    }

    public void setViewModel(BaseRecyclerViewModel viewModel){
        mViewModel = viewModel;
        onBindViewModel(viewModel);
    }

    protected abstract void onBindViewModel(BaseRecyclerViewModel baseViewModel);
}
