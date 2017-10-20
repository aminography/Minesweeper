package com.amin.minesweeper.logic;

import com.amin.minesweeper.logic.datastructure.DataGrid;
import com.amin.minesweeper.logic.datastructure.GridIterator;
import com.amin.minesweeper.view.recyclerview.BaseRecyclerViewModel;
import com.amin.minesweeper.view.viewmodels.BaseCellViewModel;
import com.amin.minesweeper.view.viewmodels.EmptyCellViewModel;
import com.amin.minesweeper.view.viewmodels.HintCellViewModel;
import com.amin.minesweeper.view.viewmodels.MineCellViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Amin on 1/24/2016.
 */
public class BoardMaker {

    public static DataGrid<BaseRecyclerViewModel> createBoard(int width, int height, int minesCount) {
        DataGrid<BaseRecyclerViewModel> tempDataGrid = new DataGrid<>(width, height);
        DataGrid<BaseRecyclerViewModel> dataGrid = new DataGrid<>(width, height);
        int cellsCount = width * height;
        ArrayList<Integer> randList = new ArrayList<>();
        for (int i = 0; i < cellsCount; i++) {
            randList.add(i);
        }
        Collections.shuffle(randList);
        HashMap<Integer, Integer> minePositionsMap = new HashMap<>();
        for (int i = 0; i < minesCount; i++) {
            minePositionsMap.put(randList.get(i), randList.get(i));
        }

        int index = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                BaseCellViewModel viewModel;
                if (minePositionsMap.containsKey(index)) {
                    viewModel = new MineCellViewModel(index, i, j, CellState.DEFAULT);
                } else {
                    viewModel = new EmptyCellViewModel(index, i, j, CellState.DEFAULT);
                }
                tempDataGrid.add(viewModel);
                index++;
            }
        }

        index = 0;
        GridIterator mItemsGridIterator = tempDataGrid.iterator();
        for (int i = 0; i < width * height; i++) {
            BaseCellViewModel viewModel = (BaseCellViewModel) mItemsGridIterator.getCurrent();
            if (viewModel.getViewType() == BaseCellViewModel.TYPE_MINE_CELL) {
                dataGrid.add(viewModel);
            } else {
                int neighbourMines = 0;
                if (mItemsGridIterator.hasUp()) {
                    BaseCellViewModel vm = (BaseCellViewModel) mItemsGridIterator.up();
                    if (vm.getViewType() == BaseCellViewModel.TYPE_MINE_CELL) {
                        neighbourMines++;
                    }
                    mItemsGridIterator.down();
                }
                if (mItemsGridIterator.hasUpRight()) {
                    BaseCellViewModel vm = (BaseCellViewModel) mItemsGridIterator.upRight();
                    if (vm.getViewType() == BaseCellViewModel.TYPE_MINE_CELL) {
                        neighbourMines++;
                    }
                    mItemsGridIterator.downLeft();
                }
                if (mItemsGridIterator.hasRight()) {
                    BaseCellViewModel vm = (BaseCellViewModel) mItemsGridIterator.right();
                    if (vm.getViewType() == BaseCellViewModel.TYPE_MINE_CELL) {
                        neighbourMines++;
                    }
                    mItemsGridIterator.left();
                }
                if (mItemsGridIterator.hasDownRight()) {
                    BaseCellViewModel vm = (BaseCellViewModel) mItemsGridIterator.downRight();
                    if (vm.getViewType() == BaseCellViewModel.TYPE_MINE_CELL) {
                        neighbourMines++;
                    }
                    mItemsGridIterator.upLeft();
                }
                if (mItemsGridIterator.hasDown()) {
                    BaseCellViewModel vm = (BaseCellViewModel) mItemsGridIterator.down();
                    if (vm.getViewType() == BaseCellViewModel.TYPE_MINE_CELL) {
                        neighbourMines++;
                    }
                    mItemsGridIterator.up();
                }
                if (mItemsGridIterator.hasDownLeft()) {
                    BaseCellViewModel vm = (BaseCellViewModel) mItemsGridIterator.downLeft();
                    if (vm.getViewType() == BaseCellViewModel.TYPE_MINE_CELL) {
                        neighbourMines++;
                    }
                    mItemsGridIterator.upRight();
                }
                if (mItemsGridIterator.hasLeft()) {
                    BaseCellViewModel vm = (BaseCellViewModel) mItemsGridIterator.left();
                    if (vm.getViewType() == BaseCellViewModel.TYPE_MINE_CELL) {
                        neighbourMines++;
                    }
                    mItemsGridIterator.right();
                }
                if (mItemsGridIterator.hasUpLeft()) {
                    BaseCellViewModel vm = (BaseCellViewModel) mItemsGridIterator.upLeft();
                    if (vm.getViewType() == BaseCellViewModel.TYPE_MINE_CELL) {
                        neighbourMines++;
                    }
                    mItemsGridIterator.downRight();
                }

                if (neighbourMines == 0) {
                    dataGrid.add(new EmptyCellViewModel(index, viewModel.getCellXAxis(), viewModel.getCellYAxis(), CellState.DEFAULT));
                } else {
                    dataGrid.add(new HintCellViewModel(index, viewModel.getCellXAxis(), viewModel.getCellYAxis(), CellState.DEFAULT, neighbourMines));
                }
            }
            mItemsGridIterator.next();
            index++;
        }
        return dataGrid;
    }
}
