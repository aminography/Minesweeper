package com.amin.minesweeper.view.viewfactory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.amin.minesweeper.view.viewholders.BaseEmptyCellViewHolder;
import com.amin.minesweeper.view.viewholders.BaseHintCellViewHolder;
import com.amin.minesweeper.view.viewholders.BaseMineCellViewHolder;

/**
 * Created by Amin on 1/22/2016.
 */
public abstract class ViewHolderFactory {

    protected static ViewHolderFactory mInstance;

    protected ViewHolderFactory(){

    }

    public static ViewHolderFactory getInstance(){
        if(mInstance == null){
            mInstance = FirstThemeViewHolderFactory.getInstance();
        }
        return mInstance;
    }

    public static final void clearInstance(){
        mInstance = null;
    }

    public abstract BaseEmptyCellViewHolder createEmptyCellViewHolder(LayoutInflater layoutInflater, ViewGroup parent, int cellWidth);

    public abstract BaseHintCellViewHolder createHintCellViewHolder(LayoutInflater layoutInflater, ViewGroup parent, int cellWidth);

    public abstract BaseMineCellViewHolder createMineCellViewHolder(LayoutInflater layoutInflater, ViewGroup parent, int cellWidth);

    public abstract int getBoardBackgroundColor(Context context);

    public abstract int getCellBackgroundColor(Context context);

    public abstract int getCellCoverColor(Context context);

    public abstract int getPrimaryTextColor(Context context);

    public abstract int getSecondaryTextColor(Context context);


}
