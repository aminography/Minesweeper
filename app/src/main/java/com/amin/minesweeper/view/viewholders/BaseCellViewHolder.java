package com.amin.minesweeper.view.viewholders;

import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amin.minesweeper.R;
import com.amin.minesweeper.view.recyclerview.BaseRecyclerViewHolder;

/**
 * Created by Amin on 1/22/2016.
 */
public abstract class BaseCellViewHolder extends BaseRecyclerViewHolder {

    protected TextView mTextView;
    protected ImageView mImageView;
    protected View mCoverView;
    protected FrameLayout mRootFrameLayout;

    public BaseCellViewHolder(LayoutInflater layoutInflater, ViewGroup parent, int layoutResourceID, int cellWidth) {
        super(layoutInflater, parent, layoutResourceID);
        mTextView = (TextView) itemView.findViewById(R.id.textView);
        mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        mRootFrameLayout = (FrameLayout) itemView.findViewById(R.id.root_layout);
        mCoverView = itemView.findViewById(R.id.cover_view);

        GridLayoutManager.LayoutParams params2 = (GridLayoutManager.LayoutParams) mRootFrameLayout.getLayoutParams();
        params2.height = cellWidth;
        params2.width = cellWidth;
    }
}
