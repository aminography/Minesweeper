package com.amin.minesweeper.logic.command;

import com.amin.minesweeper.view.viewmodels.BaseCellMemento;

/**
 * Created by Amin on 1/22/2016.
 */
public abstract class ClearCellCommand extends BaseCellCommand {

    public ClearCellCommand(BaseCellMemento memento) {
        super(memento);
    }
}
