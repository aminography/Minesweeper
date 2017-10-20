package com.amin.minesweeper.view.viewmodels;

import com.amin.minesweeper.logic.CellState;

/**
 * Created by Amin on 1/22/2016.
 */
public class MineCellViewModel extends BaseCellViewModel {

    public MineCellViewModel(int listPosition, int cellXAxis, int cellYAxis, CellState cellState) {
        super(BaseCellViewModel.TYPE_MINE_CELL, listPosition, cellXAxis, cellYAxis, cellState);
    }

    @Override
    public void setMemento(BaseCellMemento memento) {
        mCellState = memento.getMementoCellState();
    }

    @Override
    public BaseCellMemento createMemento(){
        return new MineCellMemento();
    }

    private class MineCellMemento extends BaseCellMemento {

        private MineCellMemento() {
            super(mListPosition, mCellXAxis, mCellYAxis, mCellState);
        }
    }
}

