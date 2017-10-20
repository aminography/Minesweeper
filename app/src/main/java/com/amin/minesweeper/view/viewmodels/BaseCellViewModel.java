package com.amin.minesweeper.view.viewmodels;

import com.amin.minesweeper.logic.CellState;
import com.amin.minesweeper.view.recyclerview.BaseRecyclerViewModel;

/**
 * Created by Amin on 1/22/2016.
 */
public abstract class BaseCellViewModel extends BaseRecyclerViewModel {

    public static final int TYPE_EMPTY_CELL = 1;
    public static final int TYPE_MINE_CELL = 2;
    public static final int TYPE_HINT_CELL = 3;

    protected int mCellXAxis;
    protected int mCellYAxis;

    protected CellState mCellState;

    public BaseCellViewModel(int viewType, int listPosition, int cellXAxis, int cellYAxis, CellState cellState) {
        super(viewType, listPosition);
        mCellXAxis = cellXAxis;
        mCellYAxis = cellYAxis;
        mCellState = cellState;
    }

    public abstract BaseCellMemento createMemento();

    public abstract void setMemento(BaseCellMemento memento);

    public int getCellXAxis() {
        return mCellXAxis;
    }

    public int getCellYAxis() {
        return mCellYAxis;
    }

    public CellState getCellState() {
        return mCellState;
    }

    public void setCellState(CellState cellState) {
        mCellState = cellState;
    }
}
