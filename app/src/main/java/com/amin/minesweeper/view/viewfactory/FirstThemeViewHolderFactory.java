package com.amin.minesweeper.view.viewfactory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.amin.minesweeper.R;
import com.amin.minesweeper.view.viewholders.BaseEmptyCellViewHolder;
import com.amin.minesweeper.view.viewholders.BaseHintCellViewHolder;
import com.amin.minesweeper.view.viewholders.BaseMineCellViewHolder;
import com.amin.minesweeper.view.viewholders.FirstThemeEmptyCellViewHolder;
import com.amin.minesweeper.view.viewholders.FirstThemeHintCellViewHolder;
import com.amin.minesweeper.view.viewholders.FirstThemeMineCellViewHolder;

/**
 * Created by Amin on 1/22/2016.
 */
public class FirstThemeViewHolderFactory extends ViewHolderFactory {

    private FirstThemeViewHolderFactory(){
    }

    public static ViewHolderFactory getInstance(){
        if(mInstance == null){
            mInstance = new FirstThemeViewHolderFactory();
        }
        return mInstance;
    }

    @Override
    public BaseEmptyCellViewHolder createEmptyCellViewHolder(LayoutInflater layoutInflater, ViewGroup parent, int cellWidth) {
        return new FirstThemeEmptyCellViewHolder(layoutInflater, parent, cellWidth);
    }

    @Override
    public BaseHintCellViewHolder createHintCellViewHolder(LayoutInflater layoutInflater, ViewGroup parent, int cellWidth) {
        return new FirstThemeHintCellViewHolder(layoutInflater, parent, cellWidth);
    }

    @Override
    public BaseMineCellViewHolder createMineCellViewHolder(LayoutInflater layoutInflater, ViewGroup parent, int cellWidth) {
        return new FirstThemeMineCellViewHolder(layoutInflater, parent, cellWidth);
    }

    @Override
    public int getBoardBackgroundColor(Context context) {
        return context.getResources().getColor(R.color.boardBackgroundColor1);
    }

    @Override
    public int getCellBackgroundColor(Context context) {
        return context.getResources().getColor(R.color.cellBackgroundColor1);
    }

    @Override
    public int getCellCoverColor(Context context) {
        return context.getResources().getColor(R.color.cellCoverColor1);
    }

    @Override
    public int getPrimaryTextColor(Context context) {
        return context.getResources().getColor(R.color.textColorPrimaryLight);
    }

    @Override
    public int getSecondaryTextColor(Context context) {
        return context.getResources().getColor(R.color.textColorSecondaryLight);
    }
}
