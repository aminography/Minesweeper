package com.amin.minesweeper.logic.datastructure;

import java.util.Iterator;

/**
 * Created by Amin on 1/11/2016.
 */
public interface BaseGridIterator<E> extends Iterator<E> {

    E up();
    E down();
    E left();
    E right();
    E upLeft();
    E upRight();
    E downLeft();
    E downRight();

    boolean hasUp();
    boolean hasDown();
    boolean hasLeft();
    boolean hasRight();
    boolean hasUpLeft();
    boolean hasUpRight();
    boolean hasDownLeft();
    boolean hasDownRight();
}
