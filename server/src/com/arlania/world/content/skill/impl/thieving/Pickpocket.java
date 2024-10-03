package com.arlania.world.content.skill.impl.thieving;

import com.arlania.model.*;
import com.arlania.model.container.impl.Equipment;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class Pickpocket {

    public static void pickpocketNPC(Player player, int npcId, NPC npc) {

        int reward = 995;
        int rewardQty = 0;
        int lvlreq = 1;
        int nofail = 99;
        int xp = 0;
        Position pos = player.getPosition();

        if (!player.getClickDelay().elapsed(300))
            return;

        if (npcId == 2)//Man
        {
            lvlreq = 1;
            nofail = 8;
            xp = 10;
            reward = 995;
            rewardQty = 100;
        } else if (npcId == 2234)//Master Farmer
        {
            lvlreq = 38;
            nofail = 60;
            xp = 50;
            reward = RandomUtility.inclusiveRandom(5291, 5304);
            rewardQty = RandomUtility.inclusiveRandom(1) + 1;
        } else if (npcId == 23)//Ardougne Knight
        {
            lvlreq = 50;
            nofail = 70;
            xp = 100;
            reward = 995;
            rewardQty = 250;
        } else if (npcId == 21)//Hero
        {
            lvlreq = 65;
            nofail = 90;
            xp = 150;
            reward = 995;
            rewardQty = 400;
        } else if (npcId == 2595)//TzHaar-Pik
        {
            lvlreq = 70;
            nofail = 99;
            xp = 200;
            reward = 6529;
            rewardQty = RandomUtility.inclusiveRandom(100, 250);
        } else if (npcId == 5604)//Elf
        {
            lvlreq = 80;
            nofail = 110;
            xp = 300;
            reward = 995;
            rewardQty = RandomUtility.inclusiveRandom(400, 750);
        } else if (npcId == 23698)//Vyrewatch
        {
            lvlreq = 90;
            nofail = 105;
            xp = 400;
            reward = 995;
            rewardQty = RandomUtility.inclusiveRandom(1000, 1500);
        }


        int pickpocket = RandomUtility.inclusiveRandom(1, nofail - 6);

        if (player.getCombatBuilder().isBeingAttacked()) {
            player.getPacketSender().sendMessage("You must wait a few seconds after being out of combat before doing this.");
            return;
        }
        if (player.getSkillManager().getCurrentLevel(Skill.THIEVING) < lvlreq) {
            player.getPacketSender().sendMessage("You need a Thieving level of at least " + lvlreq + " to pickpocket this npc.");
            return;
        }

        player.performAnimation(new Animation(881));

        if (npcId == 2595) {
            if (RandomUtility.inclusiveRandom(1, 1000) == 1) {
                int[] loot = {6523, 6524, 6525, 6526, 6527, 6528, 6568};
                int randloot = RandomUtility.inclusiveRandom(loot.length - 1);
                player.getInventory().add(loot[randloot], 1);
            }
        } else if (npcId == 5604) {
            if (RandomUtility.inclusiveRandom(1, 250) == 1) {
                int[] loot = {985, 987};
                int randloot = RandomUtility.inclusiveRandom(loot.length - 1);
                player.getInventory().add(loot[randloot], 1);
            }
        } else if (npcId == 23698) {
            if (RandomUtility.inclusiveRandom(1, 500) == 1) {
                int loot = 224777;
                player.getInventory().add(loot, 1);
            }
        }

        if (pickpocket > (player.getSkillManager().getCurrentLevel(Skill.THIEVING)) && lvlreq <= 50) {
            npc.forceChat("Argh, what are you doing?!");
            player.dealDamage(new Hit(20, Hitmask.RED, CombatIcon.NONE));
        } else if (pickpocket > (player.getSkillManager().getCurrentLevel(Skill.THIEVING)) && lvlreq > 50) {
            npc.forceChat("Argh, what are you doing?!");
            player.dealDamage(new Hit(50, Hitmask.RED, CombatIcon.NONE));
        } else {
            if (player.getEquipment().getItems()[Equipment.HANDS_SLOT].getId() == 10075)
                rewardQty *= 2;

            double bonusxp = 1;

            if (player.getEquipment().contains(5553))
                bonusxp += .05;
            if (player.getEquipment().contains(5554))
                bonusxp += .05;
            if (player.getEquipment().contains(5555))
                bonusxp += .05;
            if (player.getEquipment().contains(5556))
                bonusxp += .05;
            if (player.getEquipment().contains(5557))
                bonusxp += .05;

            xp *= bonusxp;


            player.getInventory().add(reward, rewardQty * player.acceleratedResources());
            player.getSkillManager().addExperience(Skill.THIEVING, xp * player.acceleratedResources());
            if (npcId == 2)
                player.getAchievementTracker().progress(AchievementData.PICKPOCKET_10_MEN, player.acceleratedResources());
            else if (npcId == 2234)
                player.getAchievementTracker().progress(AchievementData.PICKPOCKET_25_MASTER_FARMER, player.acceleratedResources());
            else if (npcId == 23)
                player.getAchievementTracker().progress(AchievementData.PICKPOCKET_50_KNIGHTS, player.acceleratedResources());
            else if (npcId == 21)
                player.getAchievementTracker().progress(AchievementData.PICKPOCKET_100_HEROES, player.acceleratedResources());
            else if (npcId == 2595)
                player.getAchievementTracker().progress(AchievementData.PICKPOCKET_250_TZHAAR, player.acceleratedResources());
            else if (npcId == 23698)
                player.getAchievementTracker().progress(AchievementData.PICKPOCKET_1000_VYREWATCH, player.acceleratedResources());


            for (int i = 0; i < player.getSkiller().getSkillerTask().getObjId().length; i++) {
                if (npcId == player.getSkiller().getSkillerTask().getObjId()[i]) {
                    for (int k = 0; k < player.acceleratedResources(); k++) {
                        player.getSkiller().handleSkillerTaskGather(true);
                        player.getSkillManager().addExperience(Skill.SKILLER, player.getSkiller().getSkillerTask().getXP());
                    }
                }
            }


        }
    }
}
	
