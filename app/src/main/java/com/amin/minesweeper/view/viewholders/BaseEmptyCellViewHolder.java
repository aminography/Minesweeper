package com.amin.minesweeper.view.viewholders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by Amin on 1/22/2016.
 */
public abstract class BaseEmptyCellViewHolder extends BaseCellViewHolder {

    public BaseEmptyCellViewHolder(LayoutInflater layoutInflater, ViewGroup parent, int layoutResourceID, int cellWidth) {
        super(layoutInflater, parent, layoutResourceID, cellWidth);
    }
}
