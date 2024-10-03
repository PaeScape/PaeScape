package com.arlania.world.content.achievements;

import com.arlania.world.entity.impl.player.Player;

public class PaePointReward implements NonItemReward {

    private final int amount;

    public PaePointReward(int amount) {
        this.amount = amount;
    }

    @Override
    public void giveReward(Player player) {
        player.setPaePoints(player.getPaePoints() + amount);
    }

    @Override
    public String rewardDescription() {
        return "-" + amount + " PaePoints.";
    }
}
