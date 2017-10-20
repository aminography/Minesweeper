package com.amin.minesweeper;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amin.minesweeper.logic.BoardMaker;
import com.amin.minesweeper.logic.CellState;
import com.amin.minesweeper.logic.datastructure.DataGrid;
import com.amin.minesweeper.logic.command.BaseCellCommand;
import com.amin.minesweeper.logic.command.ClearCellCommand;
import com.amin.minesweeper.logic.command.FlagCellCommand;
import com.amin.minesweeper.logic.command.OpenCellCommand;
import com.amin.minesweeper.logic.command.SuspectCellCommand;
import com.amin.minesweeper.tools.PositionTranslator;
import com.amin.minesweeper.tools.UIUtils;
import com.amin.minesweeper.view.recyclerview.BaseRecyclerViewModel;
import com.amin.minesweeper.view.recyclerview.RecyclerItemClickListener;
import com.amin.minesweeper.view.viewfactory.ViewHolderFactory;
import com.amin.minesweeper.view.viewmodels.BaseCellMemento;
import com.amin.minesweeper.view.viewmodels.BaseCellViewModel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Amin on 1/11/2016.
 */
public class GameFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private TextView mMinesCountTextView;
    private TextView mTimeTextView;
    private Button mUndoButton;
    private Button mRedoButton;
    private Button mResetButton;
    private List<BaseCellCommand> mCommandsList;
    private int mCurrentCommandIndex = -1;
    private GameRecyclerViewAdapter mAdapter;
    private GridLayoutManager mRecyclerViewGridLayoutManager;
    private boolean mIsSwappedActions;
    private CountDownTimer mCountDownTimer;
    private int mHeight = 8;
    private int mWidth = 8;
    private int mMinesCount = 10;
    private int mCellCount = mHeight * mWidth;
    private int mOpenedCount = 0;
    private int mRemainMinesCount = mMinesCount;
    private int mCellWidth;

    enum GameState {
        NOT_STARTED,
        STARTED,
        LOOSE,
        WIN,
    }

    private GameState mGameState = GameState.NOT_STARTED;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommandsList = new ArrayList<>();
        final SharedPreferences preferences = getActivity().getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        mIsSwappedActions = preferences.getBoolean(MainActivity.KEY_PREF_IS_SWAPPED_ACTIONS, false);
        int difficulty = preferences.getInt(MainActivity.KEY_PREF_DIFFICULTY, MainActivity.VALUE_DIFFICULTY_NORMAL);
        if (difficulty == MainActivity.VALUE_DIFFICULTY_NORMAL) {
            mHeight = MainActivity.DIFFICULTY_NORMAL_HEIGHT;
            mWidth = MainActivity.DIFFICULTY_NORMAL_WIDTH;
            mMinesCount = MainActivity.DIFFICULTY_NORMAL_MINES;
        } else if (difficulty == MainActivity.VALUE_DIFFICULTY_HARD) {
            mHeight = MainActivity.DIFFICULTY_HARD_HEIGHT;
            mWidth = MainActivity.DIFFICULTY_HARD_WIDTH;
            mMinesCount = MainActivity.DIFFICULTY_HARD_MINES;
        } else if (difficulty == MainActivity.VALUE_DIFFICULTY_EXPERT) {
            mHeight = MainActivity.DIFFICULTY_EXPERT_HEIGHT;
            mWidth = MainActivity.DIFFICULTY_EXPERT_WIDTH;
            mMinesCount = MainActivity.DIFFICULTY_EXPERT_MINES;
        }
        mCellCount = mHeight * mWidth;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mCellWidth = (int) ((size.x - UIUtils.pxFromDp(getActivity(), 16)) / mWidth);

        mRecyclerViewGridLayoutManager = new GridLayoutManager(getActivity(), mWidth);
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        mTimeTextView = (TextView) getView().findViewById(R.id.time_textView);
        mMinesCountTextView = (TextView) getView().findViewById(R.id.mines_count_textView);
        mUndoButton = (Button) getView().findViewById(R.id.undo_button);
        mRedoButton = (Button) getView().findViewById(R.id.redo_button);
        mResetButton = (Button) getView().findViewById(R.id.reset_button);

        mUndoButton.setOnClickListener(mUndoOnClickListener);
        mRedoButton.setOnClickListener(mRedoOnClickListener);
        mResetButton.setOnClickListener(mResetOnClickListener);

        mRecyclerView.addItemDecoration(new ItemDecorationGrid((int) UIUtils.pxFromDp(getActivity(), 2), mWidth));
        mAdapter = new GameRecyclerViewAdapter(getActivity(), mCellWidth);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mRecyclerViewGridLayoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, this));

        mTimeTextView.setTextColor(ViewHolderFactory.getInstance().getPrimaryTextColor(getActivity()));
        mMinesCountTextView.setTextColor(ViewHolderFactory.getInstance().getPrimaryTextColor(getActivity()));

        initGame();
    }

    private void initGame() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final DataGrid<BaseRecyclerViewModel> dataGrid = BoardMaker.createBoard(mWidth, mHeight, mMinesCount);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCommandsList.clear();
                        mCurrentCommandIndex = -1;
                        mAdapter.replaceModelsList(dataGrid);
                        mGameState = GameState.NOT_STARTED;
                        mOpenedCount = 0;
                        mRemainMinesCount = mMinesCount;
                        mTimeTextView.setText(String.valueOf(0));
                        mMinesCountTextView.setText(String.valueOf(mRemainMinesCount));
                        fitUndoRedoButton();
                        initTimer(0);
                    }
                });
            }
        }).start();
    }

    private void initTimer(final int startFromSec) {
        final int max_duration = 1000000;
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = new CountDownTimer(max_duration * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int value = (int) (max_duration - TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - 1);
                if (value < 0) {
                    value = 0;
                }
                if (mTimeTextView != null && getActivity() != null) {
                    final int finalValue = value;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTimeTextView.setText(String.valueOf(finalValue + startFromSec));
                        }
                    });
                } else {
                    cancel();
                }
            }

            @Override
            public void onFinish() {
            }
        };
    }

    private void fitUndoRedoButton() {
        if (mCurrentCommandIndex == -1) {
            mUndoButton.setEnabled(false);
            if (mCommandsList.size() == 0) {
                mRedoButton.setEnabled(false);
            } else {
                mRedoButton.setEnabled(true);
            }
        } else if (mCurrentCommandIndex == mCommandsList.size() - 1) {
            mUndoButton.setEnabled(true);
            mRedoButton.setEnabled(false);
        } else {
            mUndoButton.setEnabled(true);
            mRedoButton.setEnabled(true);
        }
    }

    private View.OnClickListener mResetOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            initGame();
        }
    };

    private View.OnClickListener mUndoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCurrentCommandIndex >= 0) {
                BaseCellCommand baseCellCommand = mCommandsList.get(mCurrentCommandIndex);
                mCurrentCommandIndex--;
                baseCellCommand.onReverseCommand();
                fitUndoRedoButton();
            }
        }
    };

    private View.OnClickListener mRedoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCurrentCommandIndex < mCommandsList.size() - 1) {
                mCurrentCommandIndex++;
                BaseCellCommand baseCellCommand = mCommandsList.get(mCurrentCommandIndex);
                baseCellCommand.onRunCommand();
                fitUndoRedoButton();
            }
        }
    };

    private void doActionOne(final BaseCellViewModel viewModel) {
        if (viewModel.getCellState() == CellState.DEFAULT || viewModel.getCellState() == CellState.SUSPECTED) {
            OpenCellCommand command = new OpenCellCommand(viewModel.createMemento()) {
                @Override
                public void onRunCommand() {
                    mMementoList.clear();
                    for (int i = 0; i < mAdapter.getItemCount(); i++) {
                        BaseCellViewModel m = (BaseCellViewModel) mAdapter.getItem(i);
                        mMementoList.add(m.createMemento());
                    }
                    if (viewModel.getViewType() == BaseCellViewModel.TYPE_MINE_CELL) {
                        viewModel.setCellState(CellState.EXPLODED);
                        for (int i = 0; i < mAdapter.getItemCount(); i++) {
                            if (i != viewModel.getListPosition()) {
                                BaseCellViewModel vm = (BaseCellViewModel) mAdapter.getItem(i);
                                if (vm.getViewType() == BaseCellViewModel.TYPE_MINE_CELL && vm.getCellState() != CellState.FLAGED) {
                                    vm.setCellState(CellState.OPENED);
                                }
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                        mGameState = GameState.LOOSE;
                        mCountDownTimer.cancel();
                        Toast.makeText(getActivity(), "Game Over!", Toast.LENGTH_SHORT).show();
                    } else if (viewModel.getViewType() == BaseCellViewModel.TYPE_EMPTY_CELL) {
                        openEmptyCellBFS(viewModel);
                        mAdapter.notifyDataSetChanged();
                        checkWinGame();
                    } else if (viewModel.getViewType() == BaseCellViewModel.TYPE_HINT_CELL) {
                        viewModel.setCellState(CellState.OPENED);
                        mOpenedCount++;
                        mAdapter.notifyDataSetChanged();
                        checkWinGame();
                    }
                }

                @Override
                public void onReverseCommand() {
                    for (int i = 0; i < mAdapter.getItemCount(); i++) {
                        BaseCellViewModel m = (BaseCellViewModel) mAdapter.getItem(i);
                        m.setMemento(mMementoList.get(i));
                    }
                    mAdapter.notifyDataSetChanged();
                    if (mGameState == GameState.WIN || mGameState == GameState.LOOSE) {
                        mGameState = GameState.STARTED;
                        int timer = Integer.parseInt(mTimeTextView.getText().toString());
                        initTimer(timer);
                        mCountDownTimer.start();
                    }
                }
            };
            int commandsSize = mCommandsList.size();
            if (mCurrentCommandIndex < commandsSize - 1) {
                for (int i = mCurrentCommandIndex + 1; i < commandsSize; i++) {
                    mCommandsList.remove(mCurrentCommandIndex + 1);
                }
            }
            mCommandsList.add(command);
            mCurrentCommandIndex++;
            command.onRunCommand();
            fitUndoRedoButton();
        }
    }

    private void doActionTwo(final BaseCellViewModel viewModel) {
        BaseCellCommand command = null;
        if (viewModel.getCellState() == CellState.DEFAULT) {
            command = new FlagCellCommand(viewModel.createMemento()) {
                @Override
                public void onRunCommand() {
                    mMementoList.clear();
                    for (int i = 0; i < mAdapter.getItemCount(); i++) {
                        BaseCellViewModel m = (BaseCellViewModel) mAdapter.getItem(i);
                        mMementoList.add(m.createMemento());
                    }
                    viewModel.setCellState(CellState.FLAGED);
                    mAdapter.notifyDataSetChanged();
                    mMinesCountTextView.setText(String.valueOf(--mRemainMinesCount));
                }

                @Override
                public void onReverseCommand() {
                    for (int i = 0; i < mAdapter.getItemCount(); i++) {
                        BaseCellViewModel m = (BaseCellViewModel) mAdapter.getItem(i);
                        m.setMemento(mMementoList.get(i));
                    }
                    mAdapter.notifyDataSetChanged();
                    mMinesCountTextView.setText(String.valueOf(++mRemainMinesCount));
                }
            };
        } else if (viewModel.getCellState() == CellState.FLAGED) {
            command = new SuspectCellCommand(viewModel.createMemento()) {
                @Override
                public void onRunCommand() {
                    mMementoList.clear();
                    for (int i = 0; i < mAdapter.getItemCount(); i++) {
                        BaseCellViewModel m = (BaseCellViewModel) mAdapter.getItem(i);
                        mMementoList.add(m.createMemento());
                    }
                    viewModel.setCellState(CellState.SUSPECTED);
                    mAdapter.notifyDataSetChanged();
                    mMinesCountTextView.setText(String.valueOf(++mRemainMinesCount));
                }

                @Override
                public void onReverseCommand() {
                    for (int i = 0; i < mAdapter.getItemCount(); i++) {
                        BaseCellViewModel m = (BaseCellViewModel) mAdapter.getItem(i);
                        m.setMemento(mMementoList.get(i));
                    }
                    mAdapter.notifyDataSetChanged();
                    mMinesCountTextView.setText(String.valueOf(--mRemainMinesCount));
                }
            };
        } else if (viewModel.getCellState() == CellState.SUSPECTED) {
            command = new ClearCellCommand(viewModel.createMemento()) {
                @Override
                public void onRunCommand() {
                    mMementoList.clear();
                    for (int i = 0; i < mAdapter.getItemCount(); i++) {
                        BaseCellViewModel m = (BaseCellViewModel) mAdapter.getItem(i);
                        mMementoList.add(m.createMemento());
                    }
                    viewModel.setCellState(CellState.DEFAULT);
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onReverseCommand() {
                    for (int i = 0; i < mAdapter.getItemCount(); i++) {
                        BaseCellViewModel m = (BaseCellViewModel) mAdapter.getItem(i);
                        m.setMemento(mMementoList.get(i));
                    }
                    mAdapter.notifyDataSetChanged();
                }
            };
        }
        if (command != null) {
            int commandsSize = mCommandsList.size();
            if (mCurrentCommandIndex < commandsSize - 1) {
                for (int i = mCurrentCommandIndex + 1; i < commandsSize; i++) {
                    mCommandsList.remove(mCurrentCommandIndex + 1);
                }
            }
            mCommandsList.add(command);
            mCurrentCommandIndex++;
            command.onRunCommand();
            fitUndoRedoButton();
        }
    }

    private List<BaseCellMemento> openEmptyCellBFS(BaseCellViewModel viewModel) {
        List<BaseCellMemento> changedCellMementos = new ArrayList<>();
        Queue<BaseCellViewModel> queue = new ArrayDeque<>();
        queue.add(viewModel);
        while (!queue.isEmpty()) {
            BaseCellViewModel removed = queue.remove();
            if (removed.getCellState() != CellState.OPENED) {
                changedCellMementos.add(removed.createMemento());
                removed.setCellState(CellState.OPENED);
                mOpenedCount++;
            }
            int position = removed.getListPosition();
            int[] neighbourPositions = getNeighbourPositions(position);
            for (int pos : neighbourPositions) {
                if (pos >= 0) {
                    BaseCellViewModel vm = (BaseCellViewModel) mAdapter.getItem(pos);
                    if (vm.getCellState() != CellState.OPENED && vm.getCellState() != CellState.FLAGED) {
                        if (vm.getViewType() == BaseCellViewModel.TYPE_EMPTY_CELL) {
                            changedCellMementos.add(vm.createMemento());
                            vm.setCellState(CellState.OPENED);
                            mOpenedCount++;
                            queue.add(vm);
                        } else if (vm.getViewType() == BaseCellViewModel.TYPE_HINT_CELL) {
                            changedCellMementos.add(vm.createMemento());
                            vm.setCellState(CellState.OPENED);
                            mOpenedCount++;
                        }
                    }
                }
            }
        }
        return changedCellMementos;
    }

    private int[] getNeighbourPositions(int pos) {
        int[] res = new int[8];
        res[0] = PositionTranslator.up(pos, mWidth);
        res[1] = PositionTranslator.upRight(pos, mWidth, mHeight);
        res[2] = PositionTranslator.right(pos, mWidth, mHeight);
        res[3] = PositionTranslator.downRight(pos, mWidth, mHeight);
        res[4] = PositionTranslator.down(pos, mWidth, mHeight);
        res[5] = PositionTranslator.downLeft(pos, mWidth, mHeight);
        res[6] = PositionTranslator.left(pos, mWidth);
        res[7] = PositionTranslator.upLeft(pos, mWidth);
        return res;
    }

    private void checkWinGame() {
        if ((mCellCount - mOpenedCount) == mMinesCount) {
            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                BaseCellViewModel vm = (BaseCellViewModel) mAdapter.getItem(i);
                if (vm.getViewType() == BaseCellViewModel.TYPE_MINE_CELL) {
                    vm.setCellState(CellState.FLAGED);
                }
            }
            mAdapter.notifyDataSetChanged();
            mGameState = GameState.WIN;
            mCountDownTimer.cancel();
            Toast.makeText(getActivity(), "You Win!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mGameState == GameState.NOT_STARTED) {
            mGameState = GameState.STARTED;
            mCountDownTimer.start();
        }
        BaseCellViewModel viewModel = ((BaseCellViewModel) mAdapter.getItem(position));
        if (mGameState != GameState.WIN && mGameState != GameState.LOOSE) {
            if (mIsSwappedActions) {
                doActionTwo(viewModel);
            } else {
                doActionOne(viewModel);
            }
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        if (mGameState == GameState.NOT_STARTED) {
            mGameState = GameState.STARTED;
            mCountDownTimer.start();
        }
        BaseCellViewModel viewModel = ((BaseCellViewModel) mAdapter.getItem(position));
        if (mGameState != GameState.WIN && mGameState != GameState.LOOSE) {
            if (!mIsSwappedActions) {
                doActionTwo(viewModel);
            } else {
                doActionOne(viewModel);
            }
        }
    }

    public void setIsSwappedActions(boolean isSwappedActions) {
        mIsSwappedActions = isSwappedActions;
    }
}
