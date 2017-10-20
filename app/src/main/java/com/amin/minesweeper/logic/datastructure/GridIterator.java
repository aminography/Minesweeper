package com.amin.minesweeper.logic.datastructure;

/**
 * Created by Amin on 1/11/2016.
 */
public class GridIterator<E> implements BaseGridIterator<E> {

    private DataGrid<E> mDataGrid;
    private int mCurrentLinearPosition;
    private int mWidth;
    private int mHeight;

    public GridIterator(DataGrid<E> dataGrid) {
        mDataGrid = dataGrid;
        mCurrentLinearPosition = 0;
        mWidth = dataGrid.getWidth();
        mHeight = dataGrid.getHeight();
    }

    public E get(int xAxis, int yAxis) {
        mCurrentLinearPosition = yAxis * mWidth + xAxis;
        return mDataGrid.get(mCurrentLinearPosition);
    }

    public int getCurrentXAxis() {
        return mCurrentLinearPosition % mWidth;
    }

    public int getCurrentYAxis() {
        return mCurrentLinearPosition / mWidth;
    }

    @Override
    public E up() {
        if (hasUp()) {
            mCurrentLinearPosition -= mWidth;
            return mDataGrid.get(mCurrentLinearPosition);
        } else {
            return null;
        }
    }

    @Override
    public E down() {
        if (hasDown()) {
            mCurrentLinearPosition += mWidth;
            return mDataGrid.get(mCurrentLinearPosition);
        } else {
            return null;
        }
    }

    @Override
    public E left() {
        if (hasLeft()) {
            mCurrentLinearPosition -= 1;
            return mDataGrid.get(mCurrentLinearPosition);
        } else {
            return null;
        }
    }

    @Override
    public E right() {
        if (hasRight()) {
            mCurrentLinearPosition += 1;
            return mDataGrid.get(mCurrentLinearPosition);
        } else {
            return null;
        }
    }

    @Override
    public E upRight() {
        if (hasUpRight()) {
            mCurrentLinearPosition -= (mWidth - 1);
            return mDataGrid.get(mCurrentLinearPosition);
        } else {
            return null;
        }
    }

    @Override
    public E upLeft() {
        if (hasUpLeft()) {
            mCurrentLinearPosition -= (mWidth + 1);
            return mDataGrid.get(mCurrentLinearPosition);
        } else {
            return null;
        }
    }

    @Override
    public E downRight() {
        if (hasDownRight()) {
            mCurrentLinearPosition += (mWidth + 1);
            return mDataGrid.get(mCurrentLinearPosition);
        } else {
            return null;
        }
    }

    @Override
    public E downLeft() {
        if (hasDownLeft()) {
            mCurrentLinearPosition += (mWidth - 1);
            return mDataGrid.get(mCurrentLinearPosition);
        } else {
            return null;
        }
    }

    @Override
    public boolean hasUp() {
        if (mCurrentLinearPosition < mWidth) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean hasDown() {
        if (mDataGrid.size() < (mCurrentLinearPosition + mWidth + 1) || mCurrentLinearPosition >= mWidth * (mHeight - 1)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean hasLeft() {
        if (mCurrentLinearPosition % mWidth == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean hasRight() {
        if (mDataGrid.size() < (mCurrentLinearPosition + 2) || mCurrentLinearPosition % mWidth == (mWidth - 1)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean hasUpLeft() {
        return hasUp() && hasLeft();
    }

    @Override
    public boolean hasUpRight() {
        return hasUp() && hasRight();
    }

    @Override
    public boolean hasDownLeft() {
        if (mDataGrid.size() < (mCurrentLinearPosition + mWidth) || mCurrentLinearPosition >= mWidth * (mHeight - 1) || mCurrentLinearPosition % mWidth == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean hasDownRight() {
        if (mDataGrid.size() < (mCurrentLinearPosition + mWidth + 2) || mCurrentLinearPosition >= mWidth * (mHeight - 1) || mCurrentLinearPosition % mWidth == (mWidth - 1)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean hasNext() {
        if (mDataGrid.size() > mCurrentLinearPosition + 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public E next() {
        if (hasNext()) {
            mCurrentLinearPosition += 1;
            return mDataGrid.get(mCurrentLinearPosition);
        } else {
            return null;
        }
    }

    public E getCurrent() {
        return mDataGrid.get(mCurrentLinearPosition);
    }

    @Override
    public void remove() {
        int sizeBeforeRemove = mDataGrid.size();
        mDataGrid.remove(mCurrentLinearPosition);
        if (mCurrentLinearPosition == (sizeBeforeRemove - 1)) {
            mCurrentLinearPosition--;
        }
    }
}
