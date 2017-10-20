package com.amin.minesweeper.view.viewmodels;

import com.amin.minesweeper.logic.CellState;

/**
 * Created by Amin on 1/22/2016.
 */
public class EmptyCellViewModel extends BaseCellViewModel {

    public EmptyCellViewModel(int listPosition, int cellXAxis, int cellYAxis, CellState cellState) {
        super(BaseCellViewModel.TYPE_EMPTY_CELL, listPosition, cellXAxis, cellYAxis, cellState);
    }

    @Override
    public void setMemento(BaseCellMemento memento) {
        mCellState = memento.getMementoCellState();
    }

    @Override
    public BaseCellMemento createMemento(){
        return new EmptyCellMemento();
    }

    private class EmptyCellMemento extends BaseCellMemento {

        private EmptyCellMemento() {
            super(mListPosition, mCellXAxis, mCellYAxis, mCellState);
        }
    }
}
