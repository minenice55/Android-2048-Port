package com.example.a2048game;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameGrid {

    static final int GRID_SIZE = 4;

    int[][] gridValues;
    GameScene mContext;
    Random prng;

    public GameGrid(GameScene context) {
        this.gridValues = new int[GRID_SIZE][GRID_SIZE];
        this.mContext = context;
        this.prng = new Random();
    }

    public void updateGrid() {
        int idx = 0;
        TextView lbl;
        for (int id : mContext.getGridCells()) {
            int x = idx % GRID_SIZE;
            int y = idx / GRID_SIZE;
            lbl = mContext.findViewById(id);
            if (isCellFree(x, y)) {
                lbl.setText("");
            }
            else {
//                Log.println(Log.DEBUG, "Populating Disp", "Value " + gridValues[x][y] + " at idx " + idx);
                lbl.setText("" + gridValues[x][y]);
            }
            idx++;
        }
    }

    public boolean isCellFree(int x, int y)
    {
        if (x >= GRID_SIZE || x < 0 || y >= GRID_SIZE || y < 0)
            return false;
        return gridValues[x][y] == 0;
    }

    public int getRandomAvailableCell()
    {
        ArrayList<Integer> pos = new ArrayList<>();
        int i = 0;
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                if (isCellFree(x, y)) {
                    Log.println(Log.DEBUG, "Avaialble Cell ", "val " + gridValues[x][y] + " at x " + x + ", y " + y);
                    pos.add(i);
                }
                i++;
            }
        }
        if (pos.size() == 0)
            return -1;
        return pos.get(prng.nextInt(pos.size()));
    }

    public boolean attemptRandPopulate(int amount) {
        boolean success = false;
        for (int i = 0; i < amount; i++) {
            int idx = getRandomAvailableCell();
            if (idx == -1) {
                success = false;
                break;
            }

            int x = idx % GRID_SIZE;
            int y = idx / GRID_SIZE;
            Log.println(Log.DEBUG, "Adding Cell ", "old val " + gridValues[x][y] + " at x " + x + ", y " + y);
            gridValues[x][y] = prng.nextInt(100) < 90 ? 2 : 4;
            success = true;
        }
        updateGrid();
        return success;
    }

    public boolean swipeRows(boolean dir)
    {
        boolean hasMove = false;
        for (int y = 0; y < GRID_SIZE; y++) {
            if (swipeRow(y, dir))
                hasMove = true;
        }
        updateGrid();
        return hasMove;
    }

    public boolean swipeColumns(boolean dir)
    {
        boolean hasMove = false;
        for (int x = 0; x < GRID_SIZE; x++) {
            if(swipeColumn(x, dir))
                hasMove = true;
        }
        updateGrid();
        return hasMove;
    }

    public boolean swipeRow(int y, boolean dir) {
        boolean gotMove = false;
        int current, want;
        //L-to-R (incrementing positions)
        if (dir)
        {
            // 2 -> 3, 1 -> 2 -> 3, 0 -> 1 -> 2 -> 3
            for (int x = GRID_SIZE-2; x >= 0; x--) {
                for (int x2 = x; x2 < GRID_SIZE - 1; x2++) {
                    current = gridValues[x2][y];
                    want = gridValues[x2+1][y];

                    if (current != 0 && (want == 0 || current == want))
                    {
                        gotMove = true;
                        //if cell empty keep moving, if cell match merge and stop
                        gridValues[x2][y] = 0;
                        gridValues[x2+1][y] += current;
                        if (current == want) {
                            mContext.addScore(gridValues[x2+1][y]);
                            continue;
                        }
                    }
                }
            }
        }
        //R-to-L (decrementing positions)
        else
        {
            // 1 -> 0, 2 -> 1 -> 0, 3 -> 2 -> 1 -> 0
            for (int x = 1; x < GRID_SIZE; x++) {
                for (int x2 = x; x2 > 0; x2--) {
                    current = gridValues[x2][y];
                    want = gridValues[x2-1][y];

                    if (current != 0 && (want == 0 || current == want))
                    {
                        gotMove = true;
                        //if cell empty keep moving, if cell match merge and stop
                        gridValues[x2][y] = 0;
                        gridValues[x2-1][y] += current;
                        if (current == want) {
                            mContext.addScore(gridValues[x2-1][y]);
                            continue;
                        }
                    }
                }
            }
        }
        return gotMove;
    }

    public boolean swipeColumn(int x, boolean dir) {
        boolean gotMove = false;
        int current, want;
        //U-to-D (incrementing positions)
        if (dir)
        {
            // 2 -> 3, 1 -> 2 -> 3, 0 -> 1 -> 2 -> 3
            for (int y = GRID_SIZE-2; y >= 0; y--) {
                for (int y2 = y; y2 < GRID_SIZE - 1; y2++) {
                    current = gridValues[x][y2];
                    want = gridValues[x][y2+1];

                    if (current != 0 && (want == 0 || current == want))
                    {
                        gotMove = true;
                        //if cell empty keep moving, if cell match merge and stop
                        gridValues[x][y2] = 0;
                        gridValues[x][y2+1] += current;
                        if (current == want) {
                            mContext.addScore(gridValues[x][y2+1]);
                            continue;
                        }
                    }
                }
            }
        }
        //D-to-U (decrementing positions)
        else
        {
            // 1 -> 0, 2 -> 1 -> 0, 3 -> 2 -> 1 -> 0
            for (int y = 1; y < GRID_SIZE; y++) {
                for (int y2 = y; y2 > 0; y2--) {
                    current = gridValues[x][y2];
                    want = gridValues[x][y2-1];

                    if (current != 0 && (want == 0 || current == want))
                    {
                        gotMove = true;
                        //if cell empty keep moving, if cell match merge and stop
                        gridValues[x][y2] = 0;
                        gridValues[x][y2-1] += current;
                        if (current == want) {
                            mContext.addScore(gridValues[x][y2-1]);
                            continue;
                        }
                    }
                }
            }
        }
        return gotMove;
    }
}
