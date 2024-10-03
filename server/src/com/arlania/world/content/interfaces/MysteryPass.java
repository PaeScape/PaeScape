package com.arlania.world.content.interfaces;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.world.World;
import com.arlania.world.content.PlayerLogs;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

import java.text.NumberFormat;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class MysteryPass {

    public static void showMysteryPass(Player player) {


        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Mystery Pass"); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, ""); //2nd column header

        player.getPacketSender().sendString(8846, "Easy Raid"); //1st Button
        player.getPacketSender().sendString(8823, "Medium Raid"); //2nd Button
        player.getPacketSender().sendString(8824, "Hard Raid"); //3rd Button
        player.getPacketSender().sendString(8827, "Expert Raid"); //4th Button
        player.getPacketSender().sendString(8837, "Easy PVM"); //5th Button
        player.getPacketSender().sendString(8840, "Medium PVM"); //6th Button
        player.getPacketSender().sendString(8843, "Hard PVM"); //7th Button
        player.getPacketSender().sendString(8859, "Expert PVM"); //8th Button
        player.getPacketSender().sendString(8862, "Complete"); //9th Button
        player.getPacketSender().sendString(8865, ""); //10th Button
        player.getPacketSender().sendString(15303, ""); //11th Button
        player.getPacketSender().sendString(15306, ""); //12th Button
        player.getPacketSender().sendString(15309, ""); //13th Button

        if (player.mpRaidEasy)
            player.getPacketSender().sendString(8846, "@gre@Easy Raid");
        else if (player.mpRaidsDone >= 10)
            player.getPacketSender().sendString(8846, "@yel@Easy Raid");
        else
            player.getPacketSender().sendString(8846, "@red@Easy Raid");

        if (player.mpRaidMedium)
            player.getPacketSender().sendString(8823, "@gre@Medium Raid");
        else if (player.mpRaidsDone >= 25)
            player.getPacketSender().sendString(8823, "@yel@Medium Raid");
        else
            player.getPacketSender().sendString(8823, "@red@Medium Raid");

        if (player.mpRaidHard)
            player.getPacketSender().sendString(8824, "@gre@Hard Raid");
        else if (player.mpRaidsDone >= 50)
            player.getPacketSender().sendString(8824, "@yel@Hard Raid");
        else
            player.getPacketSender().sendString(8824, "@red@Hard Raid");

        if (player.mpRaidExpert)
            player.getPacketSender().sendString(8827, "@gre@Expert Raid");
        else if (player.mpRaidsDone >= 100)
            player.getPacketSender().sendString(8827, "@yel@Expert Raid");
        else
            player.getPacketSender().sendString(8827, "@red@Expert Raid");

        if (player.mpBossEasy)
            player.getPacketSender().sendString(8837, "@gre@Easy PVM");
        else if (player.mpBossKills >= 10)
            player.getPacketSender().sendString(8837, "@yel@Easy PVM");
        else
            player.getPacketSender().sendString(8837, "@red@Easy PVM");

        if (player.mpBossMedium)
            player.getPacketSender().sendString(8840, "@gre@Medium PVM");
        else if (player.mpBossKills >= 25)
            player.getPacketSender().sendString(8840, "@yel@Medium PVM");
        else
            player.getPacketSender().sendString(8840, "@red@Medium PVM");

        if (player.mpBossHard)
            player.getPacketSender().sendString(8843, "@gre@Hard PVM");
        else if (player.mpBossKills >= 100)
            player.getPacketSender().sendString(8843, "@yel@Hard PVM");
        else
            player.getPacketSender().sendString(8843, "@red@Hard PVM");

        if (player.mpBossExpert)
            player.getPacketSender().sendString(8859, "@gre@Expert PVM");
        else if (player.mpBossKills >= 250)
            player.getPacketSender().sendString(8859, "@yel@Expert PVM");
        else
            player.getPacketSender().sendString(8859, "@red@Expert PVM");

        if (player.mpComplete)
            player.getPacketSender().sendString(8862, "@gre@Complete");
        else if ((player.mpBossKills >= 250) && (player.mpRaidsDone >= 100))
            player.getPacketSender().sendString(8862, "@yel@Complete");
        else
            player.getPacketSender().sendString(8862, "@red@Complete");

        int string = 8760;
        int reward = 8720;


        player.getPacketSender().sendString(string++, "@blu@Raids Completed: @gre@" + myFormat.format(player.mpRaidsDone)); //
        player.getPacketSender().sendString(string++, "@blu@Boss kills: @gre@" + myFormat.format(player.mpBossKills)); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Easy Raids - Complete 10 Raids."); //
        player.getPacketSender().sendString(string++, "Reward - 5 Crystal Keys"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Medium Raids - Complete 25 Raids."); //
        player.getPacketSender().sendString(string++, "Reward - 3 Rare Candies"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Hard Raids - Complete 50 Raids."); //
        player.getPacketSender().sendString(string++, "Reward - 1 Mystery Box"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Expert Raids - Complete 100 Raids."); //
        player.getPacketSender().sendString(string++, "Reward - 1 Elite Mystery Box"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Easy PVM - Kill 50 Bosses"); //
        player.getPacketSender().sendString(string++, "Reward - 5 Crystal Keys"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Medium PVM - Kill 100 Bosses"); //
        player.getPacketSender().sendString(string++, "Reward - 3 Rare Candy"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Hard PVM - Kill 250 Bosses"); //
        player.getPacketSender().sendString(string++, "Reward - 1 Mystery Box"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Expert PVM - Kill 500 Bosses"); //
        player.getPacketSender().sendString(string++, "Reward - 1 Elite Mystery Box"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Complete Mystery Pass");
        player.getPacketSender().sendString(string++, "Complete 100 Raids and Kill 500 Bosses."); //
        player.getPacketSender().sendString(string++, "Reward - 1 Supreme Mystery Box"); //
        player.getPacketSender().sendString(string++, ""); //


        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //


        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //


    }


    public static void handleButton(Player player, int button) {

        switch (button) {

            case 8846:
                if (player.activeInterface == "mysterypass") {
                    if (!player.mpRaidEasy && player.getInventory().getFreeSlots() > 0 && player.mpRaidsDone >= 10) {
                        player.getInventory().add(989, 5);
                        player.mpRaidEasy = true;
                    }
                    MysteryPass.showMysteryPass(player);
                }
                break;

            case 8823:
                if (player.activeInterface == "mysterypass") {
                    if (!player.mpRaidMedium && player.getInventory().getFreeSlots() > 0 && player.mpRaidsDone >= 25) {
                        player.getInventory().add(4562, 3);
                        player.mpRaidMedium = true;
                    }
                    MysteryPass.showMysteryPass(player);
                }
                break;

            case 8824:
                if (player.activeInterface == "mysterypass") {
                    if (!player.mpRaidHard && player.getInventory().getFreeSlots() > 0 && player.mpRaidsDone >= 50) {
                        player.getInventory().add(6199, 1);
                        player.mpRaidHard = true;
                    }
                    MysteryPass.showMysteryPass(player);
                }
                break;

            case 8827:
                if (player.activeInterface == "mysterypass") {
                    if (!player.mpRaidExpert && player.getInventory().getFreeSlots() > 0 && player.mpRaidsDone >= 100) {
                        player.getInventory().add(15501, 1);
                        player.mpRaidExpert = true;
                    }
                    MysteryPass.showMysteryPass(player);
                }
                break;

            case 8837:
                player.getPacketSender().sendMessage("Active Interface: " + player.activeInterface);
                player.getPacketSender().sendMessage("mpBossEasy: " + player.mpBossEasy);
                player.getPacketSender().sendMessage("mpBossKills: " + player.mpBossKills);
                if (player.activeInterface == "mysterypass") {
                    if (!player.mpBossEasy && player.getInventory().getFreeSlots() > 0 && player.mpBossKills >= 50) {
                        player.getInventory().add(989, 5);
                        player.mpBossEasy = true;
                    }
                    MysteryPass.showMysteryPass(player);
                }
                break;

            case 8840:
                if (player.activeInterface == "mysterypass") {
                    if (!player.mpBossMedium && player.getInventory().getFreeSlots() > 0 && player.mpBossKills >= 100) {
                        player.getInventory().add(4562, 3);
                        player.mpBossMedium = true;
                    }
                    MysteryPass.showMysteryPass(player);
                }
                break;

            case 8843:
                if (player.activeInterface == "mysterypass") {
                    if (!player.mpBossHard && player.getInventory().getFreeSlots() > 0 && player.mpBossKills >= 250) {
                        player.getInventory().add(6199, 1);
                        player.mpBossHard = true;
                    }
                    MysteryPass.showMysteryPass(player);
                }
                break;

            case 8859:
                if (player.activeInterface == "mysterypass") {
                    if (!player.mpBossExpert && player.getInventory().getFreeSlots() > 0 && player.mpBossKills >= 500) {
                        player.getInventory().add(15501, 1);
                        player.mpBossExpert = true;
                    }
                    MysteryPass.showMysteryPass(player);
                }
                break;

            case 8862:
                if (player.activeInterface == "mysterypass") {
                    if (!player.mpComplete && player.getInventory().getFreeSlots() > 1 &&
                            player.mpRaidEasy && player.mpRaidMedium && player.mpRaidHard && player.mpRaidExpert &&
                            player.mpBossEasy && player.mpBossMedium && player.mpBossHard && player.mpBossExpert
                            && player.mpBossKills >= 500 && player.mpRaidsDone >= 100) {
                        World.sendMessage("drops", "<img=10> <col=008FB2>" + player.getUsername() + " has just completed a Mystery Pass!");
                        PlayerLogs.log(player.getUsername(), player.getUsername() + " has just completed a Mystery Pass!");

                        player.getInventory().delete(6749, 1);
                        player.getInventory().add(603, 1);

                        player.mpRaidsDone = 0;
                        player.mpBossKills = 0;
                        player.mysteryPass = false;
                        player.mpRaidEasy = false;
                        player.mpRaidMedium = false;
                        player.mpRaidHard = false;
                        player.mpRaidExpert = false;
                        player.mpBossEasy = false;
                        player.mpBossMedium = false;
                        player.mpBossHard = false;
                        player.mpBossExpert = false;

                        String discordMessage = "[Mystery Pass] " + player.getUsername() + " has completed a Mystery Pass!";

                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.PASS_LOGS_CH).get());

                    } else {
                        player.getPacketSender().sendMessage("Please complete all other Mystery Pass challenges and collect their rewards.");
                    }
                    MysteryPass.showMysteryPass(player);
                }

            case 8865:

                break;

            case 15303:

                break;

            case 15306:

                break;

            case 15309:

                break;


        }
    }


}

