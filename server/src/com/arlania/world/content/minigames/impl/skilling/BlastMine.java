package com.arlania.world.content.minigames.impl.skilling;

import com.arlania.model.*;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.clog.CollectionLog.CustomCollection;
import com.arlania.world.content.skill.impl.mining.MiningData;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;

import java.util.ArrayList;

public class BlastMine {

    public static int[] uniques = {221345, 219988, 212013, 212014, 212015, 212016};

    public static void makeCavity(Player player, final GameObject hardRock) {

        int Cavity = 328581;
        int cycles = 10 + RandomUtility.inclusiveRandom(20);

        int pickaxe = MiningData.getPickaxe(player);

        if (pickaxe < 0) {
            player.getPacketSender().sendMessage("You need a pickaxe to dig a cavity in the wall.");
            return;
        }

        if (player.getSkillManager().getCurrentLevel(Skill.MINING) < 30) {
            player.getPacketSender().sendMessage("You need to have Level 30 mining to do this.");
            return;
        }

        final MiningData.Pickaxe p = MiningData.forPick(pickaxe);


        player.performAnimation(new Animation(p.getAnim()));
        player.getPacketSender().sendMessage("You dig a cavity into the wall!");
        player.getSkillManager().addExperience(Skill.MINING, player.getSkillManager().getMaxLevel(Skill.MINING));

        CustomObjects.deleteGlobalObject(player.getInteractingObject());
        CustomObjects.spawnGlobalObject(new GameObject(Cavity, hardRock.getPosition().copy(), 10, hardRock.getFace()));
    }

    public static void fillCavity(Player player, final GameObject Cavity) {

        int hardRock = 328580;
        int cycles = 5;


        if (player.checkAchievementAbilities(player, "gatherer") && player.getInventory().contains(213574) && player.getInventory().contains(590)) {

        } else if (!player.getInventory().contains(213573) || !player.getInventory().contains(590)) {
            player.getPacketSender().sendMessage("You need a pot of dynamite and a tinderbox for the cavity!");
            player.getPacketSender().sendMessage("Players with the 'Gatherer' Achievement Ability can use noted Dynamite!");
            return;
        }

        player.performAnimation(new Animation(733));
        player.performGraphic(new Graphic(1028, GraphicHeight.HIGH));

        if (player.checkAchievementAbilities(player, "gatherer") && player.getInventory().contains(213574)) {
            player.getInventory().delete(213574, 1);
        } else {
            player.getInventory().delete(213573, 1);
        }

        player.getPacketSender().sendMessage("You fill the cavity with a pot of dynamite!");

        if (!player.getEquipment().contains(219988) && (RandomUtility.inclusiveRandom(100) > player.getSkillManager().getCurrentLevel(Skill.MINING))) {
            player.getPacketSender().sendMessage("The dynamite damages you and the ore!");
            player.dealDamage(new Hit(50, Hitmask.RED, CombatIcon.NONE));
        } else {
            player.getPacketSender().sendMessage("You successfully get ore!");
            player.getSkillManager().addExperience(Skill.FIREMAKING, player.getSkillManager().getMaxLevel(Skill.MINING) * 5);
            loot(player);
        }

        CustomObjects.deleteGlobalObject(player.getInteractingObject());
        CustomObjects.spawnGlobalObject(new GameObject(hardRock, Cavity.getPosition().copy(), 10, Cavity.getFace()));
    }

    public static void loot(Player player) {

        //int lootCrate = 0;
        //int crateQty = 0;
        //player.getPacketSender().sendMessage(randomLocation(player).getX() + ", " + randomLocation(player).getY());

        //coal

        GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(454, RandomUtility.inclusiveRandom(2) + 1), randomLocation(player), player.getUsername(), false, 150, true, 200));

        //gold ore
        if ((player.getSkillManager().getCurrentLevel(Skill.MINING) >= 40) && (RandomUtility.inclusiveRandom(100) > 50)) {
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(445, RandomUtility.inclusiveRandom(2) + 1), randomLocation(player), player.getUsername(), false, 150, true, 200));
            player.getSkillManager().addExperience(Skill.MINING, 20);
        }

        //mithril ore
        if ((player.getSkillManager().getCurrentLevel(Skill.MINING) >= 55) && (RandomUtility.inclusiveRandom(100) > 60)) {
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(448, RandomUtility.inclusiveRandom(2) + 1), randomLocation(player), player.getUsername(), false, 150, true, 200));
            player.getSkillManager().addExperience(Skill.MINING, 70);
        }

        //adamantite ore
        if ((player.getSkillManager().getCurrentLevel(Skill.MINING) >= 70) && (RandomUtility.inclusiveRandom(100) > 70)) {
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(450, RandomUtility.inclusiveRandom(2) + 1), randomLocation(player), player.getUsername(), false, 150, true, 200));
            player.getSkillManager().addExperience(Skill.MINING, 120);
        }

        //runite ore
        if ((player.getSkillManager().getCurrentLevel(Skill.MINING) >= 85) && (RandomUtility.inclusiveRandom(100) > 80)) {
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(452, RandomUtility.inclusiveRandom(2) + 1), randomLocation(player), player.getUsername(), false, 150, true, 200));
            player.getSkillManager().addExperience(Skill.MINING, 170);
        }


        if (RandomUtility.inclusiveRandom(5000) < player.getSkillManager().getCurrentLevel(Skill.MINING)) {
            if ((player.getInventory().contains(221140) || player.getEquipment().contains(221140)) && (player.getInventory().getFreeSlots() > 0))
                player.getInventory().add(220703, 1);
            else
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(220703), randomLocation(player), player.getUsername(), false, 150, true, 200));
        }

        if (RandomUtility.inclusiveRandom(25000) < player.getSkillManager().getCurrentLevel(Skill.MINING)) {
            int uniqueLoot = uniques[RandomUtility.inclusiveRandom(uniques.length - 1)];
            player.getCollectionLog().handleDrop(CustomCollection.BlastMine.getId(), uniqueLoot, 1);
            player.getInventory().add(uniqueLoot, 1);
        }

    }

    public static Position randomLocation(Player player) {
        ArrayList<Position> positions = new ArrayList<>();

        int x = player.getPosition().getX();
        int y = player.getPosition().getY();

        int minX = x - 2;

        int minY = y - 2;
        int maxY = y + 2;

        for (int xCoords = minX; xCoords <= x; xCoords++) {
            for (int yCoords = minY; yCoords <= maxY; yCoords++) {
                positions.add(new Position(xCoords, yCoords, 0));
            }
        }

        return positions.get(RandomUtility.inclusiveRandom(positions.size() - 1));
    }


}
