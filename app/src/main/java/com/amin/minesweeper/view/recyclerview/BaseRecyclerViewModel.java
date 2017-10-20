package com.amin.minesweeper.view.recyclerview;

/**
 * Created by Amin on 1/11/2016.
 */
public abstract class BaseRecyclerViewModel {

    private int mViewType;
    protected int mListPosition;

    public BaseRecyclerViewModel(int viewType, int listPosition) {
        mViewType = viewType;
        mListPosition = listPosition;
    }

    public int getViewType() {
        return mViewType;
    }

    public int getListPosition() {
        return mListPosition;
    }
}
