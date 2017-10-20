package com.amin.minesweeper.tools;

import android.content.Context;
import android.os.Vibrator;

import com.amin.minesweeper.R;

/**
 * Created by Amin on 1/11/2016.
 */
public class UIUtils {

    public static void vibrate(final Context context, final int time) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(time);
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static int getColorForNamber(Context context, int number){
        int color;
        switch (number) {
            case 1:
                color = context.getResources().getColor(R.color.numberColor1);
                break;
            case 2:
                color = context.getResources().getColor(R.color.numberColor2);
                break;
            case 3:
                color = context.getResources().getColor(R.color.numberColor3);
                break;
            case 4:
                color = context.getResources().getColor(R.color.numberColor4);
                break;
            case 5:
                color = context.getResources().getColor(R.color.numberColor5);
                break;
            case 6:
                color = context.getResources().getColor(R.color.numberColor6);
                break;
            case 7:
                color = context.getResources().getColor(R.color.numberColor7);
                break;
            case 8:
                color = context.getResources().getColor(R.color.numberColor8);
                break;
            default:
                color = context.getResources().getColor(R.color.numberColor1);
                break;
        }
        return color;
    }
}
