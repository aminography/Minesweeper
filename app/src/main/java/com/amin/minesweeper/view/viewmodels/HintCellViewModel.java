package com.amin.minesweeper.view.viewmodels;

import com.amin.minesweeper.logic.CellState;

/**
 * Created by Amin on 1/22/2016.
 */
public class HintCellViewModel extends BaseCellViewModel {

    private int mHintNumber;

    public HintCellViewModel(int listPosition, int cellXAxis, int cellYAxis, CellState cellState, int hintNumber) {
        super(BaseCellViewModel.TYPE_HINT_CELL, listPosition, cellXAxis, cellYAxis, cellState);
        mHintNumber = hintNumber;
    }

    public int getHintNumber() {
        return mHintNumber;
    }

    @Override
    public void setMemento(BaseCellMemento memento) {
        mCellState = memento.getMementoCellState();
    }

    @Override
    public BaseCellMemento createMemento(){
        return new HintCellMemento();
    }

    private class HintCellMemento extends BaseCellMemento {

        private HintCellMemento() {
            super(mListPosition, mCellXAxis, mCellYAxis, mCellState);
        }
    }
}
