package com.amin.minesweeper.logic.command;

/**
 * Created by Amin on 1/22/2016.
 */
public abstract class BaseCommand {

    protected BaseCommand(){
    }

    public abstract void onRunCommand();
}
