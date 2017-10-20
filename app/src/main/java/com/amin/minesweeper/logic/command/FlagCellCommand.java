package com.amin.minesweeper.logic.command;

import com.amin.minesweeper.view.viewmodels.BaseCellMemento;

/**
 * Created by Amin on 1/22/2016.
 */
public abstract class FlagCellCommand extends BaseCellCommand {

    public FlagCellCommand(BaseCellMemento memento) {
        super(memento);
    }
}
