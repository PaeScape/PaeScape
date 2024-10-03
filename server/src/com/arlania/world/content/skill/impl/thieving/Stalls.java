package com.arlania.world.content.skill.impl.thieving;

import com.arlania.model.Animation;
import com.arlania.model.GameMode;
import com.arlania.model.Skill;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;

public class Stalls {

    public static void stealFromStall(Player player, int objId) {

        String message = "";
        int reward = 995;
        int lvlreq = 1;
        int xp = 0;

        //Silk Stall (11729)
        //Baker's Stall (11730)
        //Gem Stall (11731)
        //Fur Stall (11732)
        //Spice Stall (11733)


        if (objId == 11732)//Fur Stall
        {
            lvlreq = 1;
            xp = 10;
            int[] rewards = {948};

            if(player.checkAchievementAbilities(player, "gatherer") || (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.Harvester)) {
                rewards[0] = 949;
            }

            int random = RandomUtility.inclusiveRandom(rewards.length - 1);
            reward = rewards[random];
            message = "You've succesfully stolen from the Fur Stall.";
        } else if (objId == 11729)//Silk Stall
        {
            lvlreq = 20;
            xp = 25;
            int[] rewards = {950};

            if(player.checkAchievementAbilities(player, "gatherer") || (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.Harvester)) {
                rewards[0] = 951;
            }

            int random = RandomUtility.inclusiveRandom(rewards.length - 1);
            reward = rewards[random];
            message = "You've succesfully stolen from the Silk Stall.";

        }

        if (objId == 11733)//Spice Stall
        {
            lvlreq = 65;
            xp = 81;
            int[] rewards = {2003};

            if(player.checkAchievementAbilities(player, "gatherer") || (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.Harvester)) {
                rewards[0] = 2004;
            }

            int random = RandomUtility.inclusiveRandom(rewards.length - 1);
            reward = rewards[random];
            message = "You've succesfully stolen from the Spice Stall.";

        } else if (objId == 11730)//Baker's Stall
        {
            lvlreq = 5;
            xp = 16;
            int[] rewards = {1891, 1897, 7178, 7198, 7208, 7218};
            if(player.checkAchievementAbilities(player, "gatherer") || (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.Harvester)) {
                rewards[0] = 1892;
                rewards[1] = 1898;
                rewards[2] = 7179;
                rewards[3] = 7199;
                rewards[4] = 7209;
                rewards[5] = 7219;
            }

            int random = RandomUtility.inclusiveRandom(rewards.length - 1);
            reward = rewards[random];
            message = "You've succesfully stolen from the Baker Stall.";

        } else if (objId == 11731)//Gem Stall
        {
            lvlreq = 75;
            xp = 160;
            int[] rewards = {1617, 1619, 1621, 1623, 1631};

            if(player.checkAchievementAbilities(player, "gatherer") || (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.Harvester)) {
                rewards[0] = 1618;
                rewards[1] = 1620;
                rewards[2] = 1622;
                rewards[3] = 1624;
                rewards[4] = 1632;
            }

            int random = RandomUtility.inclusiveRandom(rewards.length - 1);
            reward = rewards[random];
            message = "You've succesfully stolen from the Gem Stall.";

        } else if (objId == 2565)//Silver Stall
        {
            lvlreq = 50;
            xp = 54;
            int[] rewards = {442, 2355, 1724, 1718};

            if(player.checkAchievementAbilities(player, "gatherer") || (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.Harvester)) {
                rewards[0] = 443;
                rewards[1] = 2356;
                rewards[2] = 1724;
                rewards[3] = 1719;
            }

            int random = RandomUtility.inclusiveRandom(rewards.length - 1);
            reward = rewards[random];
            message = "You've succesfully stolen from the Silver Stall.";

        }

        if (player.getInventory().getFreeSlots() < 1) {
            player.getPacketSender().sendMessage("You need some more inventory space to do this.");
            return;
        }
        if (player.getCombatBuilder().isBeingAttacked()) {
            player.getPacketSender().sendMessage("You must wait a few seconds after being out of combat before doing this.");
            return;
        }
        if (!player.getClickDelay().elapsed(1000))
            return;
        if (player.getSkillManager().getCurrentLevel(Skill.THIEVING) < lvlreq) {
            player.getPacketSender().sendMessage("You need a Thieving level of at least " + lvlreq + " to steal from this stall.");
            return;
        }

        player.performAnimation(new Animation(881));
        player.getPacketSender().sendMessage(message);
        player.getPacketSender().sendInterfaceRemoval();
        player.getSkillManager().addExperience(Skill.THIEVING, xp * player.acceleratedResources());
        player.getClickDelay().reset();
        player.getInventory().add(reward, player.acceleratedResources());
        player.getSkillManager().stopSkilling();


    }


}
