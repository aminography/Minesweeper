package com.amin.minesweeper;

import android.app.Activity;
import android.view.ViewGroup;

import com.amin.minesweeper.view.recyclerview.BaseRecyclerViewAdapter;
import com.amin.minesweeper.view.viewfactory.ViewHolderFactory;
import com.amin.minesweeper.view.viewholders.BaseCellViewHolder;
import com.amin.minesweeper.view.viewmodels.BaseCellViewModel;

/**
 * Created by Amin on 1/11/2016.
 */
public class GameRecyclerViewAdapter extends BaseRecyclerViewAdapter {

    private int mCellWidth;

    public GameRecyclerViewAdapter(Activity activity, int cellWidth) {
        super(activity);
        mCellWidth = cellWidth;
    }

    @Override
    public BaseCellViewHolder createBaseRecyclerViewHolder(ViewGroup parent, int viewType) {
        BaseCellViewHolder viewHolder = null;
        switch (viewType){
            case BaseCellViewModel.TYPE_EMPTY_CELL:
                viewHolder = ViewHolderFactory.getInstance().createEmptyCellViewHolder(getLayoutInflater(), parent, mCellWidth);
                break;
            case BaseCellViewModel.TYPE_MINE_CELL:
                viewHolder = ViewHolderFactory.getInstance().createMineCellViewHolder(getLayoutInflater(), parent, mCellWidth);
                break;
            case BaseCellViewModel.TYPE_HINT_CELL:
                viewHolder = ViewHolderFactory.getInstance().createHintCellViewHolder(getLayoutInflater(), parent, mCellWidth);
                break;
        }
        return viewHolder;
    }
}
