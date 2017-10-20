package com.amin.minesweeper.logic.command;

import com.amin.minesweeper.view.viewmodels.BaseCellMemento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amin on 1/22/2016.
 */
public abstract class BaseCellCommand extends BaseCommand {

    protected List<BaseCellMemento> mMementoList;
    private BaseCellMemento mCellMemento;

    protected BaseCellCommand(BaseCellMemento memento) {
        super();
        mMementoList = new ArrayList<>();
        mCellMemento = memento;
    }

    public abstract void onReverseCommand();
}
