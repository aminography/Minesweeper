package com.amin.minesweeper.view.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amin.minesweeper.R;
import com.amin.minesweeper.logic.CellState;
import com.amin.minesweeper.tools.UIUtils;
import com.amin.minesweeper.view.recyclerview.BaseRecyclerViewModel;
import com.amin.minesweeper.view.viewfactory.ViewHolderFactory;
import com.amin.minesweeper.view.viewmodels.EmptyCellViewModel;
import com.amin.minesweeper.view.viewmodels.HintCellViewModel;

/**
 * Created by Amin on 1/22/2016.
 */
public class SecondThemeHintCellViewHolder extends BaseHintCellViewHolder {

    private static final int LAYOUT_RESOURCE_ID = R.layout.test_cell_item;

    public SecondThemeHintCellViewHolder(LayoutInflater layoutInflater, ViewGroup parent, int cellWidth) {
        super(layoutInflater, parent, LAYOUT_RESOURCE_ID, cellWidth);
        mCoverView.setBackgroundColor(ViewHolderFactory.getInstance().getCellCoverColor(mCoverView.getContext()));
        mRootFrameLayout.setBackgroundColor(ViewHolderFactory.getInstance().getCellBackgroundColor(mRootFrameLayout.getContext()));
    }

    @Override
    protected void onBindViewModel(BaseRecyclerViewModel baseViewModel) {
        HintCellViewModel viewModel = (HintCellViewModel) baseViewModel;

        switch (viewModel.getCellState()) {
            case DEFAULT:
                mCoverView.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.GONE);
                mImageView.setImageBitmap(null);
                mTextView.setText("");
                break;
            case FLAGED:
                mCoverView.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.VISIBLE);
                mImageView.setImageResource(R.drawable.ic_flag);
                mTextView.setText("");
                break;
            case SUSPECTED:
                mCoverView.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.VISIBLE);
                mImageView.setImageResource(R.drawable.ic_suspected);
                mTextView.setText("");
                break;
            case OPENED:
                mCoverView.setVisibility(View.GONE);
                mImageView.setVisibility(View.GONE);
                mTextView.setText(String.valueOf(viewModel.getHintNumber()));
                mTextView.setTextColor(UIUtils.getColorForNamber(mTextView.getContext(), viewModel.getHintNumber()));
                break;
        }
    }
}
