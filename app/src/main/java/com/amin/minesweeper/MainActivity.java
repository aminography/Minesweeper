package com.amin.minesweeper;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.amin.minesweeper.view.viewfactory.FirstThemeViewHolderFactory;
import com.amin.minesweeper.view.viewfactory.SecondThemeViewHolderFactory;
import com.amin.minesweeper.view.viewfactory.ViewHolderFactory;

/**
 * Created by Amin on 1/11/2016.
 */
public class MainActivity extends AppCompatActivity {

    public static final String PREF_NAME = "com.amin.minesweeper.pref";
    public static final String KEY_PREF_THEME = "key_pref_theme";
    public static final String KEY_PREF_DIFFICULTY = "key_pref_difficulty";
    public static final String KEY_PREF_IS_SWAPPED_ACTIONS = "key_pref_is_swap_actions";
    public static final int VALUE_LIGHT_THEME = 1;
    public static final int VALUE_DARK_THEME = 2;
    public static final int VALUE_DIFFICULTY_NORMAL = 1;
    public static final int VALUE_DIFFICULTY_HARD = 2;
    public static final int VALUE_DIFFICULTY_EXPERT = 3;

    public static final int DIFFICULTY_NORMAL_WIDTH = 8;
    public static final int DIFFICULTY_NORMAL_HEIGHT = 8;
    public static final int DIFFICULTY_NORMAL_MINES = 10;

    public static final int DIFFICULTY_HARD_WIDTH = 10;
    public static final int DIFFICULTY_HARD_HEIGHT = 26;
    public static final int DIFFICULTY_HARD_MINES = 40;

    public static final int DIFFICULTY_EXPERT_WIDTH = 12;
    public static final int DIFFICULTY_EXPERT_HEIGHT = 48;
    public static final int DIFFICULTY_EXPERT_MINES = 99;

    private GameFragment mGameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        int theme = preferences.getInt(KEY_PREF_THEME, VALUE_LIGHT_THEME);
        if (theme == VALUE_LIGHT_THEME) {
            ViewHolderFactory.clearInstance();
            FirstThemeViewHolderFactory.getInstance();
        } else {
            ViewHolderFactory.clearInstance();
            SecondThemeViewHolderFactory.getInstance();
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.root_layout).setBackgroundColor(ViewHolderFactory.getInstance().getBoardBackgroundColor(getApplicationContext()));

        mGameFragment = (GameFragment) getFragmentManager().findFragmentById(R.id.game_fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            super.onBackPressed();
            return true;
        } else if (id == R.id.action_new_game) {
            onDifficultyPressed(true);
        } else if (id == R.id.action_theme) {
            onThemePressed();
        } else if (id == R.id.action_swap_actions) {
            onSwapActionsPressed();
        } else if (id == R.id.action_game_difficulty) {
            onDifficultyPressed(false);
        }

        return super.onOptionsItemSelected(item);
    }

    private void onThemePressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyleLight);
        builder.setTitle("Select game theme:");
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_settings_theme, null);
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        RadioButton radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1);
        RadioButton radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);

        radioButton1.setText("Light Theme");
        radioButton2.setText("Dark Theme");

        final SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        int theme = preferences.getInt(KEY_PREF_THEME, VALUE_LIGHT_THEME);
        if (theme == VALUE_LIGHT_THEME) {
            radioButton1.setChecked(true);
        } else {
            radioButton2.setChecked(true);
        }

        builder.setView(view);
        builder.setNegativeButton("CANCEL", null);
        builder.setPositiveButton("SELECT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int checkedID = radioGroup.getCheckedRadioButtonId();

                switch (checkedID) {
                    case R.id.radioButton1:
                        preferences.edit().putInt(KEY_PREF_THEME, VALUE_LIGHT_THEME).apply();
                        break;
                    case R.id.radioButton2:
                        preferences.edit().putInt(KEY_PREF_THEME, VALUE_DARK_THEME).apply();
                        break;
                }
                onResetPrompt();
            }
        });
        builder.show();
    }

    private void onDifficultyPressed(final boolean isNewGame) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyleLight);
        builder.setTitle("Select game difficulty:");
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_settings_difficulty, null);
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        RadioButton radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1);
        RadioButton radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);
        RadioButton radioButton3 = (RadioButton) view.findViewById(R.id.radioButton3);

        radioButton1.setText("Normal\n" + DIFFICULTY_NORMAL_WIDTH + "x" + DIFFICULTY_NORMAL_HEIGHT + " cells\n" + DIFFICULTY_NORMAL_MINES + " mines");
        radioButton2.setText("Hard\n" + DIFFICULTY_HARD_WIDTH + "x" + DIFFICULTY_HARD_HEIGHT + " cells\n" + DIFFICULTY_HARD_MINES + " mines");
        radioButton3.setText("Expert\n" + DIFFICULTY_EXPERT_WIDTH + "x" + DIFFICULTY_EXPERT_HEIGHT + " cells\n" + DIFFICULTY_EXPERT_MINES + " mines");

        final SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        int difficulty = preferences.getInt(KEY_PREF_DIFFICULTY, VALUE_DIFFICULTY_NORMAL);
        if (difficulty == VALUE_DIFFICULTY_NORMAL) {
            radioButton1.setChecked(true);
        } else if (difficulty == VALUE_DIFFICULTY_HARD) {
            radioButton2.setChecked(true);
        } else {
            radioButton3.setChecked(true);
        }

        builder.setView(view);
        builder.setNegativeButton("CANCEL", null);
        builder.setPositiveButton("SELECT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int checkedID = radioGroup.getCheckedRadioButtonId();

                switch (checkedID) {
                    case R.id.radioButton1:
                        preferences.edit().putInt(KEY_PREF_DIFFICULTY, VALUE_DIFFICULTY_NORMAL).apply();
                        break;
                    case R.id.radioButton2:
                        preferences.edit().putInt(KEY_PREF_DIFFICULTY, VALUE_DIFFICULTY_HARD).apply();
                        break;
                    case R.id.radioButton3:
                        preferences.edit().putInt(KEY_PREF_DIFFICULTY, VALUE_DIFFICULTY_EXPERT).apply();
                        break;
                }
                if (isNewGame) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }, 50);
                } else {
                    onResetPrompt();
                }
            }
        });
        builder.show();
    }

    private void onSwapActionsPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyleLight);
        builder.setTitle("Select game actions:");
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_settings_theme, null);
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        RadioButton radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1);
        RadioButton radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);

        radioButton1.setText("Click - Long Click");
        radioButton2.setText("Long Click - Click");

        final SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean isSwapped = preferences.getBoolean(KEY_PREF_IS_SWAPPED_ACTIONS, false);
        if (isSwapped) {
            radioButton2.setChecked(true);
        } else {
            radioButton1.setChecked(true);
        }

        builder.setView(view);
        builder.setNegativeButton("CANCEL", null);
        builder.setPositiveButton("SELECT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int checkedID = radioGroup.getCheckedRadioButtonId();

                switch (checkedID) {
                    case R.id.radioButton1:
                        preferences.edit().putBoolean(KEY_PREF_IS_SWAPPED_ACTIONS, false).apply();
                        mGameFragment.setIsSwappedActions(false);
                        break;
                    case R.id.radioButton2:
                        preferences.edit().putBoolean(KEY_PREF_IS_SWAPPED_ACTIONS, true).apply();
                        mGameFragment.setIsSwappedActions(true);
                        break;
                }
            }
        });
        builder.show();
    }

    private void onResetPrompt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyleLight);
        builder.setMessage("Do you wanna restart the game to affect changes?");
        builder.setNegativeButton("CANCEL", null);
        builder.setPositiveButton("RESTART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }, 100);
            }
        });
        builder.show();
    }
}
