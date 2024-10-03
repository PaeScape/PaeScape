package com.arlania.world.content.interfaces;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.world.World;
import com.arlania.world.content.PlayerLogs;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

import java.text.NumberFormat;
import java.util.Objects;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class BattlePass {

    public static void showBattlePass(Player player) {


        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Battle Pass"); //title header
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

        if (player.bpSkillEasy)
            player.getPacketSender().sendString(8846, "@gre@Easy XP");
        else if (player.bpExperience >= 10000000)
            player.getPacketSender().sendString(8846, "@yel@Easy XP");
        else
            player.getPacketSender().sendString(8846, "@red@Easy XP");

        if (player.bpSkillMedium)
            player.getPacketSender().sendString(8823, "@gre@Medium XP");
        else if (player.bpExperience >= 25000000)
            player.getPacketSender().sendString(8823, "@yel@Medium XP");
        else
            player.getPacketSender().sendString(8823, "@red@Medium XP");

        if (player.bpSkillHard)
            player.getPacketSender().sendString(8824, "@gre@Hard XP");
        else if (player.bpExperience >= 100000000)
            player.getPacketSender().sendString(8824, "@yel@Hard XP");
        else
            player.getPacketSender().sendString(8824, "@red@Hard XP");

        if (player.bpSkillExpert)
            player.getPacketSender().sendString(8827, "@gre@Expert XP");
        else if (player.bpExperience >= 250000000)
            player.getPacketSender().sendString(8827, "@yel@Expert XP");
        else
            player.getPacketSender().sendString(8827, "@red@Expert XP");

        if (player.bpBossEasy)
            player.getPacketSender().sendString(8837, "@gre@Easy PVM");
        else if (player.bpBossKills >= 50)
            player.getPacketSender().sendString(8837, "@yel@Easy PVM");
        else
            player.getPacketSender().sendString(8837, "@red@Easy PVM");

        if (player.bpBossMedium)
            player.getPacketSender().sendString(8840, "@gre@Medium PVM");
        else if (player.bpBossKills >= 100)
            player.getPacketSender().sendString(8840, "@yel@Medium PVM");
        else
            player.getPacketSender().sendString(8840, "@red@Medium PVM");

        if (player.bpBossHard)
            player.getPacketSender().sendString(8843, "@gre@Hard PVM");
        else if (player.bpBossKills >= 250)
            player.getPacketSender().sendString(8843, "@yel@Hard PVM");
        else
            player.getPacketSender().sendString(8843, "@red@Hard PVM");

        if (player.bpBossExpert)
            player.getPacketSender().sendString(8859, "@gre@Expert PVM");
        else if (player.bpBossKills >= 500)
            player.getPacketSender().sendString(8859, "@yel@Expert PVM");
        else
            player.getPacketSender().sendString(8859, "@red@Expert PVM");

        if (player.bpComplete)
            player.getPacketSender().sendString(8862, "@gre@Complete");
        else if ((player.bpBossKills >= 500) && (player.bpExperience >= 250000000))
            player.getPacketSender().sendString(8862, "@yel@Complete");
        else
            player.getPacketSender().sendString(8862, "@red@Complete");

        int string = 8760;
        int reward = 8720;


        player.getPacketSender().sendString(string++, "@blu@Experience gained: @gre@" + myFormat.format(player.bpExperience)); //
        player.getPacketSender().sendString(string++, "@blu@Boss kills: @gre@" + myFormat.format(player.bpBossKills)); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Easy XP - Gain 10m total experience."); //
        player.getPacketSender().sendString(string++, "Reward - 1 Low Equipment Upgrade"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Medium XP - Gain 25m total experience."); //
        player.getPacketSender().sendString(string++, "Reward - 1 Medium Equipment Upgrade"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Hard XP - Gain 100m total experience."); //
        player.getPacketSender().sendString(string++, "Reward - 1 High Equipment Upgrade"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Expert XP - Gain 250m total experience."); //
        player.getPacketSender().sendString(string++, "Reward - 1 Legendary Equipment Upgrade"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Easy PVM - Kill 50 Bosses"); //
        player.getPacketSender().sendString(string++, "Reward - 1 Low Equipment Upgrade"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Medium PVM - Kill 100 Bosses"); //
        player.getPacketSender().sendString(string++, "Reward - 1 Medium Equipment Upgrade"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Hard PVM - Kill 250 Bosses"); //
        player.getPacketSender().sendString(string++, "Reward - 1 High Equipment Upgrade"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Expert PVM - Kill 500 Bosses"); //
        player.getPacketSender().sendString(string++, "Reward - 1 Legendary Equipment Upgrade"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(string++, "Complete Battle Pass");
        player.getPacketSender().sendString(string++, "Gain 250m Experience and Kill 500 Bosses."); //
        player.getPacketSender().sendString(string++, "Reward - 1 Master Equipment Upgrade"); //
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
                if (player.activeInterface == "battlepass") {
                    if (!player.bpSkillEasy && player.getInventory().getFreeSlots() > 0 && player.bpExperience >= 10000000) {
                        player.getInventory().add(4037, 1);
                        player.bpSkillEasy = true;
                    }
                    BattlePass.showBattlePass(player);
                }
                break;

            case 8823:
                if (player.activeInterface == "battlepass") {
                    if (!player.bpSkillMedium && player.getInventory().getFreeSlots() > 0 && player.bpExperience >= 25000000) {
                        player.getInventory().add(4036, 1);
                        player.bpSkillMedium = true;
                    }
                    BattlePass.showBattlePass(player);
                }
                break;

            case 8824:
                if (player.activeInterface == "battlepass") {
                    if (!player.bpSkillHard && player.getInventory().getFreeSlots() > 0 && player.bpExperience >= 100000000) {
                        player.getInventory().add(4033, 1);
                        player.bpSkillHard = true;
                    }
                    BattlePass.showBattlePass(player);
                }
                break;

            case 8827:
                if (Objects.equals(player.activeInterface, "battlepass")) {
                    if (!player.bpSkillExpert && player.getInventory().getFreeSlots() > 0 && player.bpExperience >= 250000000) {
                        player.getInventory().add(4034, 1);
                        player.bpSkillExpert = true;
                    }
                    BattlePass.showBattlePass(player);
                }
                break;

            case 8837:
                if (Objects.equals(player.activeInterface, "battlepass")) {
                    if (!player.bpBossEasy && player.getInventory().getFreeSlots() > 0 && player.bpBossKills >= 50) {
                        player.getInventory().add(4037, 1);
                        player.bpBossEasy = true;
                    }
                    BattlePass.showBattlePass(player);
                }
                break;

            case 8840:
                if (Objects.equals(player.activeInterface, "battlepass")) {
                    if (!player.bpBossMedium && player.getInventory().getFreeSlots() > 0 && player.bpBossKills >= 100) {
                        player.getInventory().add(4036, 1);
                        player.bpBossMedium = true;
                    }
                    BattlePass.showBattlePass(player);
                }
                break;

            case 8843:
                if (Objects.equals(player.activeInterface, "battlepass")) {
                    if (!player.bpBossHard && player.getInventory().getFreeSlots() > 0 && player.bpBossKills >= 250) {
                        player.getInventory().add(4033, 1);
                        player.bpBossHard = true;
                    }
                    BattlePass.showBattlePass(player);
                }
                break;

            case 8859:
                if (Objects.equals(player.activeInterface, "battlepass")) {
                    if (!player.bpBossExpert && player.getInventory().getFreeSlots() > 0 && player.bpBossKills >= 500) {
                        player.getInventory().add(4034, 1);
                        player.bpBossExpert = true;
                    }
                    BattlePass.showBattlePass(player);
                }
                break;

            case 8862:
                if (Objects.equals(player.activeInterface, "battlepass")) {
                    if (!player.bpComplete && player.getInventory().getFreeSlots() > 1 &&
                            player.bpSkillEasy && player.bpSkillMedium && player.bpSkillHard && player.bpSkillExpert &&
                            player.bpBossEasy && player.bpBossMedium && player.bpBossHard && player.bpBossExpert
                            && player.bpBossKills >= 500 && player.bpExperience >= 250000000) {
                        World.sendMessage("drops", "<img=10> <col=008FB2>" + player.getUsername() + " has just completed a Battle Pass!");
                        PlayerLogs.log(player.getUsername(), player.getUsername() + " has just completed a Battle Pass!");


                        player.getInventory().delete(6758, 1);
                        player.getInventory().add(4035, 1);

                        player.bpExperience = 0;
                        player.bpBossKills = 0;
                        player.battlePass = false;
                        player.bpSkillEasy = false;
                        player.bpSkillMedium = false;
                        player.bpSkillHard = false;
                        player.bpSkillExpert = false;
                        player.bpBossEasy = false;
                        player.bpBossMedium = false;
                        player.bpBossHard = false;
                        player.bpBossExpert = false;

                        String discordMessage = "[Battle Pass] " + player.getUsername() + " has completed a Battle Pass!";

                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.PASS_LOGS_CH).get());
                    } else {
                        player.getPacketSender().sendMessage("Please complete all other Battle Pass challenges and collect their rewards.");
                    }
                    BattlePass.showBattlePass(player);
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

