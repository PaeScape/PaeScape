package com.arlania.world.content;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.GameMode;
import com.arlania.model.Skill;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.seasonal.SeasonalHandler;
import com.arlania.world.content.skill.SkillManager;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

public class Prestige {

    public static void processPrestige(Player player) {

        for (int i = 0; i <= 24; i++) {
            if (player.getSkillManager().getCurrentLevel(Skill.forId(i)) < player.getSkillManager().getMaxLevel(i)) {
                player.getSkillManager().setCurrentLevel(Skill.forId((i)), player.getSkillManager().getMaxLevel(Skill.forId(i)));
            }
            if (SkillManager.getMaxAchievingLevel(Skill.forId(i), player) > player.getSkillManager().getMaxLevel(Skill.forId(i))) {

                player.getPacketSender().sendMessage("You must have the maximum level in each skill, no equipment on, and 5 empty inventory spaces.");
                player.getPacketSender().sendInterfaceRemoval();

                if (GameSettings.DISCORD)
                    new MessageBuilder().append(player.getUsername() + " was not able to prestige because all stats were not maxed.").send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_PRESTIGE_CH).get());
                return;
            }
        }
        player.getSkillManager().resetSkill(Skill.AGILITY);
        player.getSkillManager().resetSkill(Skill.ATTACK);
        player.getSkillManager().resetSkill(Skill.CONSTITUTION);
        player.getSkillManager().resetSkill(Skill.COOKING);
        player.getSkillManager().resetSkill(Skill.CRAFTING);
        player.getSkillManager().resetSkill(Skill.DEFENCE);
        player.getSkillManager().resetSkill(Skill.DUNGEONEERING);
        player.getSkillManager().resetSkill(Skill.FARMING);
        player.getSkillManager().resetSkill(Skill.FIREMAKING);
        player.getSkillManager().resetSkill(Skill.FISHING);
        player.getSkillManager().resetSkill(Skill.FLETCHING);
        player.getSkillManager().resetSkill(Skill.HERBLORE);
        player.getSkillManager().resetSkill(Skill.HUNTER);
        player.getSkillManager().resetSkill(Skill.MAGIC);
        player.getSkillManager().resetSkill(Skill.MINING);
        player.getSkillManager().resetSkill(Skill.PRAYER);
        player.getSkillManager().resetSkill(Skill.RANGED);
        player.getSkillManager().resetSkill(Skill.RUNECRAFTING);
        player.getSkillManager().resetSkill(Skill.SKILLER);
        player.getSkillManager().resetSkill(Skill.SLAYER);
        player.getSkillManager().resetSkill(Skill.SMITHING);
        player.getSkillManager().resetSkill(Skill.STRENGTH);
        player.getSkillManager().resetSkill(Skill.SUMMONING);
        player.getSkillManager().resetSkill(Skill.THIEVING);
        player.getSkillManager().resetSkill(Skill.WOODCUTTING);

        if (player.getSkillManager().getMaxLevel(0) != 1 || player.getSkillManager().getMaxLevel(1) != 1 ||
                player.getSkillManager().getMaxLevel(2) != 1 || player.getSkillManager().getMaxLevel(3) != 100 ||
                player.getSkillManager().getMaxLevel(4) != 1 || player.getSkillManager().getMaxLevel(5) != 10 ||
                player.getSkillManager().getMaxLevel(6) != 1 || player.getSkillManager().getMaxLevel(7) != 1 ||
                player.getSkillManager().getMaxLevel(8) != 1 || player.getSkillManager().getMaxLevel(9) != 1 ||
                player.getSkillManager().getMaxLevel(10) != 1 || player.getSkillManager().getMaxLevel(11) != 1 ||
                player.getSkillManager().getMaxLevel(12) != 1 || player.getSkillManager().getMaxLevel(13) != 1 ||
                player.getSkillManager().getMaxLevel(14) != 1 || player.getSkillManager().getMaxLevel(15) != 1 ||
                player.getSkillManager().getMaxLevel(16) != 1 || player.getSkillManager().getMaxLevel(17) != 1 ||
                player.getSkillManager().getMaxLevel(18) != 1 || player.getSkillManager().getMaxLevel(19) != 1 ||
                player.getSkillManager().getMaxLevel(20) != 1 || player.getSkillManager().getMaxLevel(21) != 1 ||
                player.getSkillManager().getMaxLevel(22) != 1 || player.getSkillManager().getMaxLevel(23) != 1 ||
                player.getSkillManager().getMaxLevel(24) != 1) {
            //player.getPacketSender().sendMessage("@red@There was an error with your Prestige. The staff team has been notified!");

            if (GameSettings.DISCORD)
                new MessageBuilder().append(player.getUsername() + " had an issue prestiging. Error 4").send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_PRESTIGE_CH).get());
            return;
        }

        if (GameSettings.DISCORD)
            new MessageBuilder().append(player.getUsername() + " has Prestiged Successfully!").send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_PRESTIGE_CH).get());


        if (player.prestige < 10) {
            if(player.getGameMode() != GameMode.SEASONAL_IRONMAN) {
                player.perkUpgrades += 3;
            } else {
                player.perkUpgrades++;
            }
            player.sendMessage("You can now upgrade three Prestige Perks!");
            player.prestige += 1;
            player.setexprate(player.getexprate() + 1);
            World.sendMessage("status", "<col=996633>" + player.getUsername() + " has just prestiged!");

            if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                int points = 5 * player.prestige;

                SeasonalHandler.addPoints(player, points);

                String time = player.ticksToMinutes(player.getTotalPlayTime() + player.getRecordedLogin().elapsed());
                String message = player.getUsername() + " completed a Prestige and has " + player.seasonPoints + " Points! (" + time + ")";
                String discordMessage = GameSettings.SUCCESSKID + " " + message;

                String filename = "Seasonal_Log_" + player.seasonMonth + "_" + player.seasonYear;
                PlayerLogs.log(filename, message);

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_SEASONAL_POINTS_CH).get());
            }

            if(player.prestige > 9)
                player.getAchievementTracker().progress(AchievementData.PRESTIGE_10_TIMES, 1);
            if(player.prestige > 8)
                player.getAchievementTracker().progress(AchievementData.PRESTIGE_9_TIMES, 1);
            if(player.prestige > 7)
                player.getAchievementTracker().progress(AchievementData.PRESTIGE_8_TIMES, 1);
            if(player.prestige > 6)
                player.getAchievementTracker().progress(AchievementData.PRESTIGE_7_TIMES, 1);
            if(player.prestige > 5)
                player.getAchievementTracker().progress(AchievementData.PRESTIGE_6_TIMES, 1);
            if(player.prestige > 4)
                player.getAchievementTracker().progress(AchievementData.PRESTIGE_5_TIMES, 1);
            if(player.prestige > 3)
                player.getAchievementTracker().progress(AchievementData.PRESTIGE_4_TIMES, 1);
            if(player.prestige > 2)
                player.getAchievementTracker().progress(AchievementData.PRESTIGE_3_TIMES, 1);
            if(player.prestige > 1)
                player.getAchievementTracker().progress(AchievementData.PRESTIGE_2_TIMES, 1);
            if(player.prestige > 0)
                player.getAchievementTracker().progress(AchievementData.PRESTIGE_1_TIME, 1);


        } else {
            player.extraPrestiges++;
            World.sendMessage("status", "<col=996633>" + player.getUsername() + " has now completed " + player.extraPrestiges + " Bonus Prestiges!");
        }

        int randomUpgrade = RandomUtility.inclusiveRandom(4033, 4035);

        player.getInventory().add(randomUpgrade, 1);
        player.getInventory().add(6199, 5); //5 Mystery boxes
        player.getInventory().add(4562, 10); //10 rare candy
        player.getInventory().add(989, 25); //25 crystal keys


        player.getPacketSender().sendInterfaceRemoval();

                    /*} else {
                        player.getPacketSender().sendMessage("You must have the maximum level in each skill, no equipment on, and 5 empty inventory spaces.");
                        player.getPacketSender().sendInterfaceRemoval();
                    }*/


    }


}