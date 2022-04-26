package com.example.a2048game;

import java.util.Date;

public class FesData {
    Date startPeriod;
    Date endPeriod;

    Player winningPlayer;

    public FesData() {
    }

    public FesData(Date startPeriod, Date endPeriod) {
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
    }

    public Date getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(Date startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public Player getWinningPlayer() {
        return winningPlayer;
    }

    public void setWinningPlayer(Player winningPlayer) {
        this.winningPlayer = winningPlayer;
    }
}
