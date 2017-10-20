package com.amin.minesweeper.view.viewmodels;

import com.amin.minesweeper.logic.CellState;

/**
 * Created by Amin on 1/22/2016.
 */
public class BaseCellMemento {

    private int mMementoCellListPosition;
    private int mMementoCellXAxis;
    private int mMementoCellYAxis;
    private int mMementoCellState;

    protected BaseCellMemento(int cellListPosition, int cellXAxis, int cellYAxis, CellState cellState){
        mMementoCellListPosition = cellListPosition;
        mMementoCellXAxis = cellXAxis;
        mMementoCellYAxis = cellYAxis;
        mMementoCellState = cellState.ordinal();
    }

    public int getmMementoCellListPosition() {
        return mMementoCellListPosition;
    }

    public int getMementoCellXAxis() {
        return mMementoCellXAxis;
    }

    public int getMementoCellYAxis() {
        return mMementoCellYAxis;
    }

    public CellState getMementoCellState() {
        return CellState.values()[mMementoCellState];
    }
}
