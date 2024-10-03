package com.arlania.world.content.interfaces;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.util.RandomUtility;
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

public class EventPass {

    public static void showEventPass(Player player) {


        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Event Pass"); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, ""); //2nd column header

        player.getPacketSender().sendString(8846, "Easy XP"); //1st Button
        player.getPacketSender().sendString(8823, "Medium XP"); //2nd Button
        player.getPacketSender().sendString(8824, "Hard XP"); //3rd Button
        player.getPacketSender().sendString(8827, "Expert XP"); //4th Button
        player.getPacketSender().sendString(8837, "Easy PVM"); //5th Button
        player.getPacketSender().sendString(8840, "Medium PVM"); //6th Button
        player.getPacketSender().sendString(8843, "Hard PVM"); //7th Button
        player.getPacketSender().sendString(8859, "Expert PVM"); //8th Button
        player.getPacketSender().sendString(8862, "Complete"); //9th Button
        player.getPacketSender().sendString(8865, ""); //10th Button
        player.getPacketSender().sendString(15303, ""); //11th Button
        player.getPacketSender().sendString(15306, ""); //12th Button
        player.getPacketSender().sendString(15309, ""); //13th Button

        if (player.epSkillEasy)
            player.getPacketSender().sendString(8846, "@gre@Easy XP");
        else if (player.epExperience >= 10000000)
            player.getPacketSender().sendString(8846, "@yel@Easy XP");
        else
            player.getPacketSender().sendString(8846, "@red@Easy XP");

        if (player.epSkillMedium)
            player.getPacketSender().sendString(8823, "@gre@Medium XP");
        else if (player.epExperience >= 25000000)
            player.getPacketSender().sendString(8823, "@yel@Medium XP");
        else
            player.getPacketSender().sendString(8823, "@red@Medium XP");

        if (player.epSkillHard)
            player.getPacketSender().sendString(8824, "@gre@Hard XP");
        else if (player.epExperience >= 100000000)
            player.getPacketSender().sendString(8824, "@yel@Hard XP");
        else
            player.getPacketSender().sendString(8824, "@red@Hard XP");

        if (player.epSkillExpert)
            player.getPacketSender().sendString(8827, "@gre@Expert XP");
        else if (player.epExperience >= 250000000)
            player.getPacketSender().sendString(8827, "@yel@Expert XP");
        else
            player.getPacketSender().sendString(8827, "@red@Expert XP");

        if (player.epBossEasy)
            player.getPacketSender().sendString(8837, "@gre@Easy PVM");
        else if (player.epBossKills >= 10)
            player.getPacketSender().sendString(8837, "@yel@Easy PVM");
        else
            player.getPacketSender().sendString(8837, "@red@Easy PVM");

        if (player.epBossMedium)
            player.getPacketSender().sendString(8840, "@gre@Medium PVM");
        else if (player.epBossKills >= 25)
            player.getPacketSender().sendString(8840, "@yel@Medium PVM");
        else
            player.getPacketSender().sendString(8840, "@red@Medium PVM");

        if (player.epBossHard)
            player.getPacketSender().sendString(8843, "@gre@Hard PVM");
        else if (player.epBossKills >= 100)
            player.getPacketSender().sendString(8843, "@yel@Hard PVM");
        else
            player.getPacketSender().sendString(8843, "@red@Hard PVM");

        if (player.epBossExpert)
            player.getPacketSender().sendString(8859, "@gre@Expert PVM");
        else if (player.epBossKills >= 250)
            player.getPacketSender().sendString(8859, "@yel@Expert PVM");
        else
            player.getPacketSender().sendString(8859, "@red@Expert PVM");

        if (player.epComplete)
            player.getPacketSender().sendString(8862, "@gre@Complete");
        else if ((player.epBossKills >= 250) && (player.epExperience >= 250000000))
            player.getPacketSender().sendString(8862, "@yel@Complete");
        else
            player.getPacketSender().sendString(8862, "@red@Complete");

        int string = 8760;
        int reward = 8720;


        player.getPacketSender().sendString(string++, "@blu@Experience gained: @gre@" + myFormat.format(player.epExperience)); //
        player.getPacketSender().sendString(string++, "@blu@Boss kills: @gre@" + myFormat.format(player.epBossKills)); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Easy XP - Gain 10m total experience."); //
        player.getPacketSender().sendString(string++, "Reward - 5 Crystal Keys"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Medium XP - Gain 25m total experience."); //
        player.getPacketSender().sendString(string++, "Reward - 10 Crystal Keys"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Hard XP - Gain 100m total experience."); //
        player.getPacketSender().sendString(string++, "Reward - 15 Crystal Keys"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Expert XP - Gain 250m total experience."); //
        player.getPacketSender().sendString(string++, "Reward - Personal Event token"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Easy PVM - Kill 10 Bosses"); //
        player.getPacketSender().sendString(string++, "Reward - 1 Rare Candy"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Medium PVM - Kill 25 Bosses"); //
        player.getPacketSender().sendString(string++, "Reward - 3 Rare Candy"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Hard PVM - Kill 100 Bosses"); //
        player.getPacketSender().sendString(string++, "Reward - 5 Rare Candy"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Expert PVM - Kill 250 Bosses"); //
        player.getPacketSender().sendString(string++, "Reward - Personal Event token"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Complete Event Pass");
        player.getPacketSender().sendString(string++, "Gain 250m Experience and Kill 250 Bosses."); //
        player.getPacketSender().sendString(string++, "Reward - Global Event token"); //
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
                if (player.activeInterface == "eventpass") {
                    if (!player.epSkillEasy && player.getInventory().getFreeSlots() > 0 && player.epExperience >= 10000000) {
                        player.getInventory().add(989, 5);
                        player.epSkillEasy = true;
                    }
                    EventPass.showEventPass(player);
                }
                break;

            case 8823:
                if (player.activeInterface == "eventpass") {
                    if (!player.epSkillMedium && player.getInventory().getFreeSlots() > 0 && player.epExperience >= 25000000) {
                        player.getInventory().add(989, 10);
                        player.epSkillMedium = true;
                    }
                    EventPass.showEventPass(player);
                }
                break;

            case 8824:
                if (player.activeInterface == "eventpass") {
                    if (!player.epSkillHard && player.getInventory().getFreeSlots() > 0 && player.epExperience >= 100000000) {
                        player.getInventory().add(989, 15);
                        player.epSkillHard = true;
                    }
                    EventPass.showEventPass(player);
                }
                break;

            case 8827:
                if (player.activeInterface == "eventpass") {
                    if (!player.epSkillExpert && player.getInventory().getFreeSlots() > 0 && player.epExperience >= 250000000) {
                        int[] events = {4020, 4021, 4022, 4023, 4024, 4025, 4027, 4028, 4029, 4030, 4031, 4032};
                        player.getInventory().add(events[RandomUtility.inclusiveRandom(events.length - 1)], 1);
                        player.epSkillExpert = true;
                    }
                    EventPass.showEventPass(player);
                }
                break;

            case 8837:
                if (player.activeInterface == "eventpass") {
                    if (!player.epBossEasy && player.getInventory().getFreeSlots() > 0 && player.epBossKills >= 10) {
                        player.getInventory().add(4562, 1);
                        player.epBossEasy = true;
                    }
                    EventPass.showEventPass(player);
                }
                break;

            case 8840:
                if (player.activeInterface == "eventpass") {
                    if (!player.epBossMedium && player.getInventory().getFreeSlots() > 0 && player.epBossKills >= 25) {
                        player.getInventory().add(4562, 3);
                        player.epBossMedium = true;
                    }
                    EventPass.showEventPass(player);
                }
                break;

            case 8843:
                if (player.activeInterface == "eventpass") {
                    if (!player.epBossHard && player.getInventory().getFreeSlots() > 0 && player.epBossKills >= 100) {
                        player.getInventory().add(4562, 5);
                        player.epBossHard = true;
                    }
                    EventPass.showEventPass(player);
                }
                break;

            case 8859:
                if (player.activeInterface == "eventpass") {
                    if (!player.epBossExpert && player.getInventory().getFreeSlots() > 0 && player.epBossKills >= 250) {
                        int[] events = {4020, 4021, 4022, 4023, 4024, 4025, 4027, 4028, 4029, 4030, 4031, 4032};
                        player.getInventory().add(events[RandomUtility.inclusiveRandom(events.length - 1)], 1);
                        player.epBossExpert = true;
                    }
                    EventPass.showEventPass(player);
                }
                break;

            case 8862:
                if (player.activeInterface == "eventpass") {
                    if (!player.epComplete && player.getInventory().getFreeSlots() > 1 &&
                            player.epSkillEasy && player.epSkillMedium && player.epSkillHard && player.epSkillExpert &&
                            player.epBossEasy && player.epBossMedium && player.epBossHard && player.epBossExpert
                            && player.epBossKills >= 250 && player.epExperience >= 250000000) {
                        World.sendMessage("drops", "<img=10> <col=008FB2>" + player.getUsername() + " has just completed a Event Pass!");
                        PlayerLogs.log(player.getUsername(), player.getUsername() + " has just completed a Event Pass!");

                        int[] events = {7020, 7021, 7022, 7023, 7024, 7025, 7027, 7028, 7029, 7032, 7040, 7041, 7042, 7043, 7044, 7045, 7046, 7047, 7048, 7049};

                        player.getInventory().delete(6769, 1);
                        player.getInventory().add(events[RandomUtility.inclusiveRandom(events.length - 1)], 1);

                        player.epExperience = 0;
                        player.epBossKills = 0;
                        player.eventPass = false;
                        player.epSkillEasy = false;
                        player.epSkillMedium = false;
                        player.epSkillHard = false;
                        player.epSkillExpert = false;
                        player.epBossEasy = false;
                        player.epBossMedium = false;
                        player.epBossHard = false;
                        player.epBossExpert = false;

                        String discordMessage = "[Event Pass] " + player.getUsername() + " has completed an Event Pass!";

                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.PASS_LOGS_CH).get());
                    } else {
                        player.getPacketSender().sendMessage("Please complete all other Event Pass challenges and collect their rewards.");
                    }
                    EventPass.showEventPass(player);
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

