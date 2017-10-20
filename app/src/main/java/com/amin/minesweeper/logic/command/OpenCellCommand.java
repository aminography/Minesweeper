package com.amin.minesweeper.logic.command;

import com.amin.minesweeper.view.viewmodels.BaseCellMemento;

/**
 * Created by Amin on 1/22/2016.
 */
public abstract class OpenCellCommand extends BaseCellCommand {

    public OpenCellCommand(BaseCellMemento memento) {
        super(memento);
    }
}
