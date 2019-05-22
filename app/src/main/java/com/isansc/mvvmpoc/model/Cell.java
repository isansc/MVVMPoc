package com.isansc.mvvmpoc.model;

import android.text.TextUtils;

public class Cell {

    public Player player;

    public Cell(Player player) {
        this.player = player;
    }

    public boolean isEmpty() {
        return player == null || TextUtils.isEmpty(player.value);
    }
}
