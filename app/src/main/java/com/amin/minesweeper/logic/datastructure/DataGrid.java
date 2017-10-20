package com.amin.minesweeper.logic.datastructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amin on 1/11/2016.
 */
public class DataGrid<E> extends ArrayList<E> implements List<E> {

    private ArrayList<E> mList;
    private int mWidth;
    private int mHeight;

    public DataGrid(int width, int height) {
        mList = new ArrayList<>();
        mWidth = width;
        mHeight = height;
    }

    @Override
    public int size() {
        return mList.size();
    }

    @Override
    public boolean add(E object) {
        return mList.add(object);
    }

    @Override
    public E get(int index) {
        return mList.get(index);
    }

    @Override
    public GridIterator<E> iterator() {
        return new GridIterator<>(this);
    }

    public int getHeight() {
        return mHeight;
    }

    public int getWidth() {
        return mWidth;
    }

    public E get(int xAxis, int yAxis) {
        return mList.get(yAxis * mWidth + xAxis);
    }
}
