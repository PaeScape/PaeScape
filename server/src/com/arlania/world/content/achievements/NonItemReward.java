package com.arlania.world.content.achievements;

import com.arlania.world.entity.impl.player.Player;

public interface NonItemReward {
    void giveReward(Player player);

    String rewardDescription();
}
