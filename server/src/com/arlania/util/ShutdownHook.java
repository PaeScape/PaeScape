package com.arlania.util;

import com.arlania.DiscordBot;
import com.arlania.GameServer;
import com.arlania.HighScores;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.PlayerHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ShutdownHook extends Thread {

    /**
     * The ShutdownHook logger to print out information.
     */
    private static final Logger logger = Logger.getLogger(ShutdownHook.class.getName());

    @Override
    public void run() {
        logger.info("The shutdown hook is processing all required actions...");
        //World.savePlayers();
        GameServer.setUpdating(true);
        for (Player player : World.getPlayers()) {
            if (player != null) {
                //	World.deregister(player);
                PlayerHandler.handleLogout(player);
            }
        }
        World.saveWorldState();
        try {
            HighScores.finish();
        } catch (InterruptedException e) {
            GameServer.getLogger().log(Level.SEVERE, "Error finalizing HS object", e);
        }

        DiscordBot.getInstance().shutdown();

        logger.info("The shudown hook actions have been completed, shutting the server down...");
    }
}
