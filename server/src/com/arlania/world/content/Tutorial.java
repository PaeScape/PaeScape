package com.arlania.world.content;


import com.arlania.GameSettings;
import com.arlania.model.GameMode;
import com.arlania.model.Position;
import com.arlania.net.PlayerSession;
import com.arlania.net.SessionState;
import com.arlania.net.login.LoginResponses;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.PlayerLoading;
import org.jboss.netty.channel.Channel;

public class Tutorial {


    public static void questProgress(Player player) {

        switch (player.tutorialIsland) {

            case 0:
                //Dialogue giving brief introduction to PaeScape
                DialogueManager.start(player, 1000);
                player.tutorialIsland = 1;
                break;
            case 1:
                //Dialogue explaining PaePoints and Perks
                startTutorial(player);
                break;
            case 2:
            case 3:
            case 4:
                // (2) Telling player to mine and giving a pick axe
                // (3) Telling player about Accelerate perk and teaching player how to unlock it. Accelerate is free.
                // (4) Congratulate player on unlocking first perk and using it. Tell them to mine a few then progress further into the dungeon.

                miningTutorial(player);
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                // (5) Intro to combat then have player select combat style to give player weapon/ammo
                // (6) Player fights rat
                // (7) Player gets new perk
                // (8) Player has to kill the Rat again
                // (9) Tell player to go up the north east ladder

                combatTutorial(player);
                break;

            case 10:
                endTutorial(player);
                break;
            //Discuss Events, Equipment Upgrades, Boss Instances (make NPCs or Dialogue for each)
            //Teleport player to main land with starter stuff
        }

    }


    public static void newPlayer(Player player) {

        player.sendMessage("@red@Talk to Patrick to start your tutorial!");
        Position position = new Position(3138, 3087, 0);
        player.moveTo(position);

    }



    public static void miningTutorial(Player player) {
        switch (player.tutorialIsland) {
            case 2:
                //Intro to mining
                DialogueManager.start(player, 1004);
                //give player pickaxe
                if (player.getInventory().getAmount(1265) == 0)
                    player.getInventory().add(1265, 1);
                //check if player has ores, then move to next step
                if (player.getInventory().getAmount(436) > 0 || player.getInventory().getAmount(438) > 0) {
                    player.tutorialIsland = 3;
                    miningTutorial(player);
                }
                break;
            case 3:
                //Talk about buying accelerate perk
                DialogueManager.start(player, 1005);
                break;
            /*default:
                //Tell player to move on to Vannaka
                DialogueManager.start(player, 1010);
                break;*/
        }
    }

    public static void combatTutorial(Player player) {
        switch (player.tutorialIsland) {
            case 5:
                //Intro to combat then have player select combat style to give player weapon/ammo
                DialogueManager.start(player, 1011);
                player.setDialogueActionId(1011);
                break;
            case 6:
                //Player fights rat
                DialogueManager.start(player, 1013);
                break;
            case 7:
                //Player gets new perk
                DialogueManager.start(player, 1015);
                break;
            case 8:
                //Player has to kill the Rat again
                DialogueManager.start(player, 1016);
                break;
            case 9:
                //Tell player to go up the north east ladder
                DialogueManager.start(player, 1017);
                break;
            default:

                break;
        }
    }


    public static void startTutorial(Player player) {
        //move into dungeon
        Position position = new Position(3088, 9520, 0);
        player.moveTo(position);

        player.getPacketSender().sendMessage("Please walk south to the mining area and talk to the Mining Tutor.");
        player.tutorialIsland = 2;
    }


    public static void endTutorial(Player player) {
        player.setDialogueActionId(190);
        DialogueManager.start(player, 190);
    }


}
