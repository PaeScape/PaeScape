package com.arlania.model;

import com.arlania.world.content.minigames.impl.kingdom.NobilitySystem;
import com.arlania.world.entity.impl.player.Player;

public enum GameMode {

    NORMAL, ULTIMATE_IRONMAN, IRONMAN, HARDCORE_IRONMAN, SEASONAL_IRONMAN;

    public static void set(Player player, GameMode newMode, boolean death) {
        if (!death && !player.getClickDelay().elapsed(1000))
            return;
        player.getClickDelay().reset();
        player.getPacketSender().sendInterfaceRemoval();

        NobilitySystem.removeContribution(player);

        player.setGameMode(newMode);
        player.getPacketSender().sendIronmanMode(newMode.ordinal());

        player.setPlayerLocked(player.newPlayer());
    }

    public static void setSeasonal(Player player) {
        player.setGameMode(GameMode.SEASONAL_IRONMAN);
        player.boxScape = true;
    }
}
