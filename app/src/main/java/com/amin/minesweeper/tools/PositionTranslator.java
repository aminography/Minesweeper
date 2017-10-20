package com.amin.minesweeper.tools;

/**
 * Created by Amin on 1/22/2016.
 */
public class PositionTranslator {

    public static int up(int position, int gridWidth) {
        if (position < gridWidth) {
            return -1;
        } else {
            return position - gridWidth;
        }
    }

    public static int down(int position, int gridWidth, int gridHeight) {
        if ((gridWidth * gridHeight) < (position + gridWidth + 1) || position >= gridWidth * (gridHeight - 1)) {
            return -1;
        } else {
            return position + gridWidth;
        }
    }

    public static int left(int position, int gridWidth) {
        if (position % gridWidth == 0) {
            return -1;
        } else {
            return position - 1;
        }
    }

    public static int right(int position, int gridWidth, int gridHeight) {
        if ((gridWidth * gridHeight) < (position + 2) || position % gridWidth == (gridWidth - 1)) {
            return -1;
        } else {
            return position + 1;
        }
    }

    public static int upLeft(int position, int gridWidth) {
        if (up(position, gridWidth) < 0 || left(position, gridWidth) < 0) {
            return -1;
        } else {
            return position - (gridWidth + 1);
        }
    }

    public static int upRight(int position, int gridWidth, int gridHeight) {
        if (up(position, gridWidth) < 0 || right(position, gridWidth, gridHeight) < 0) {
            return -1;
        } else {
            return position - (gridWidth - 1);
        }
    }

    public static int downLeft(int position, int gridWidth, int gridHeight) {
        if ((gridWidth * gridHeight) < (position + gridWidth) || position >= gridWidth * (gridHeight - 1) || position % gridWidth == 0) {
            return -1;
        } else {
            return position + (gridWidth - 1);
        }
    }

    public static int downRight(int position, int gridWidth, int gridHeight) {
        if ((gridWidth * gridHeight) < (position + gridWidth + 2) || position >= gridWidth * (gridHeight - 1) || position % gridWidth == (gridWidth - 1)) {
            return -1;
        } else {
            return position + (gridWidth + 1);
        }
    }
}
