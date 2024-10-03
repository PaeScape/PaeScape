package com.arlania.world.entity.impl.player;

import com.arlania.model.container.impl.Bank;

import java.io.File;

/*
 * @Author Bas - Restoring Arlania w/ this file
 */

public class EcoReset {

    public static void main(String[] args) {

        /*
         * Loading character files
         */
        for (File file : new File("data/saves/characters/").listFiles()) {
            Player player = new Player(null);
            player.setUsername(file.getName().substring(0, file.getName().length() - 5));

            PlayerLoading.getResultWithoutLogin(player); //comment out line 78-81 in playerloading.java for this

            /*
             * Money pouch, inventory, equipment, and dung bound items
             */
            player.setMoneyInPouch(0);
            player.getInventory().resetItems();
            player.getEquipment().resetItems();
            player.getMinigameAttributes().getDungeoneeringAttributes().setBoundItems(new int[5]);

            /*
             * Reset Bank
             */
            for (Bank bank : player.getBanks()) {
                if (bank == null) {
                    return;
                }
                bank.resetItems();
            }

            /*
             * Clear pack yack / beast of burden
             */
            if (player.getSummoning().getBeastOfBurden() != null) {
                player.getSummoning().getBeastOfBurden().resetItems();
            }

            /*
             * Reset the points
             */
            //leave loyalty points
            player.getPointsHandler().setPkPoints(0, true);
            player.setBossPoints(0);
            player.getPointsHandler().setdonatorpoints(0, 0);
            player.getPointsHandler().setDungeoneeringTokens(0, true);
            //player.getPointsHandler().setCommendations(0, true);
            player.getPointsHandler().setPrestigePoints(0, true);
            player.getPointsHandler().setTriviaPoints(0, true);
            player.getPointsHandler().setSlayerPoints(0, true);
            //player.getPointsHandler().setVotingPoints(0, true);

            /*
             * Save File
             */
            PlayerSaving.save(player);
            System.out.println("Account Reset Successfully");
        }
    }
}
