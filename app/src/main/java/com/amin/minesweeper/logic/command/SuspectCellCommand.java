package com.amin.minesweeper.logic.command;

import com.amin.minesweeper.view.viewmodels.BaseCellMemento;

/**
 * Created by Amin on 1/22/2016.
 */
public abstract class SuspectCellCommand extends BaseCellCommand {

    public SuspectCellCommand(BaseCellMemento memento) {
        super(memento);
    }
}
