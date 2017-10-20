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
import com.amin.minesweeper.view.viewholders.SecondThemeEmptyCellViewHolder;
import com.amin.minesweeper.view.viewholders.SecondThemeHintCellViewHolder;
import com.amin.minesweeper.view.viewholders.SecondThemeMineCellViewHolder;

/**
 * Created by Amin on 1/22/2016.
 */
public class SecondThemeViewHolderFactory extends ViewHolderFactory {

    private SecondThemeViewHolderFactory(){
    }

    public static ViewHolderFactory getInstance(){
        if(mInstance == null){
            mInstance = new SecondThemeViewHolderFactory();
        }
        return mInstance;
    }

    @Override
    public BaseEmptyCellViewHolder createEmptyCellViewHolder(LayoutInflater layoutInflater, ViewGroup parent, int cellWidth) {
        return new SecondThemeEmptyCellViewHolder(layoutInflater, parent, cellWidth);
    }

    @Override
    public BaseHintCellViewHolder createHintCellViewHolder(LayoutInflater layoutInflater, ViewGroup parent, int cellWidth) {
        return new SecondThemeHintCellViewHolder(layoutInflater, parent, cellWidth);
    }

    @Override
    public BaseMineCellViewHolder createMineCellViewHolder(LayoutInflater layoutInflater, ViewGroup parent, int cellWidth) {
        return new SecondThemeMineCellViewHolder(layoutInflater, parent, cellWidth);
    }

    @Override
    public int getBoardBackgroundColor(Context context) {
        return context.getResources().getColor(R.color.boardBackgroundColor2);
    }

    @Override
    public int getCellBackgroundColor(Context context) {
        return context.getResources().getColor(R.color.cellBackgroundColor2);
    }

    @Override
    public int getCellCoverColor(Context context) {
        return context.getResources().getColor(R.color.cellCoverColor2);
    }

    @Override
    public int getPrimaryTextColor(Context context) {
        return context.getResources().getColor(R.color.textColorPrimaryDark);
    }

    @Override
    public int getSecondaryTextColor(Context context) {
        return context.getResources().getColor(R.color.textColorSecondaryDark);
    }
}
