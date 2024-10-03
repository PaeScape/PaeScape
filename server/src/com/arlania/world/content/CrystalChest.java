package com.arlania.world.content;

import com.arlania.GameSettings;
import com.arlania.model.Animation;
import com.arlania.model.GameMode;
import com.arlania.model.GameObject;
import com.arlania.model.Item;
import com.arlania.model.container.impl.Bank;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.entity.impl.player.Player;

public class CrystalChest {

    public static void handleChest(final Player p, final GameObject chest) {


        //if(!p.getClickDelay().elapsed(200))
        //return;
        if (!p.getInventory().contains(989)) {
            p.getPacketSender().sendMessage("This chest can only be opened with a Crystal key.");
            return;
        }

        if (p.getInventory().getFreeSlots() < 5) {
            p.getPacketSender().sendMessage("@red@You need 5 free slots to open the crystal chest.");
            return;
        }

        p.performAnimation(new Animation(827));
        p.getInventory().delete(989, 1);

        if (!p.chatFilter)
            p.getPacketSender().sendMessage("You open the chest...");

        p.CrystalChests++;

        p.getAchievementTracker().progress(AchievementData.OPEN_10_CRYSTAL_CHESTS, 1);
        p.getAchievementTracker().progress(AchievementData.OPEN_100_CRYSTAL_CHESTS, 1);
        p.getAchievementTracker().progress(AchievementData.OPEN_1000_CRYSTAL_CHESTS, 1);


        if (p.CrystalChests >= 10)
            p.getAchievementTracker().progress(AchievementData.OPEN_10_CRYSTAL_CHESTS, 10);
        if (p.CrystalChests >= 100)
            p.getAchievementTracker().progress(AchievementData.OPEN_100_CRYSTAL_CHESTS, 100);
        if (p.CrystalChests >= 1000)
            p.getAchievementTracker().progress(AchievementData.OPEN_1000_CRYSTAL_CHESTS, 1000);


        //roll random kind of supply loot
        //give random quantity based on type of supply loot
        //add to inventory

        int lootOne = 2;
        int lootTwo = 2;
        int lootThree = 2;

        int lootOneQty = 0;
        int lootTwoQty = 0;
        int lootThreeQty = 0;

        int lootTypeOne = RandomUtility.inclusiveRandom(1, 4);
        int lootTypeTwo = RandomUtility.inclusiveRandom(1, 4);
        int lootTypeThree = RandomUtility.inclusiveRandom(1, 4);

        //high supply
        if (lootTypeOne == 1) {
            lootOne = GameSettings.HIGHSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.HIGHSUPPLYDROPS.length - 1)];
            lootOneQty = 10 + RandomUtility.inclusiveRandom(20);
        }

        //low supply
        else {
            lootOne = GameSettings.LOWSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.LOWSUPPLYDROPS.length - 1)];
            lootOneQty = 80 + RandomUtility.inclusiveRandom(200);
        }


        //high supply
        if (lootTypeTwo == 1) {
            lootTwo = GameSettings.HIGHSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.HIGHSUPPLYDROPS.length - 1)];
            lootTwoQty = 10 + RandomUtility.inclusiveRandom(20);
        }

        //low supply
        else {
            lootTwo = GameSettings.LOWSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.LOWSUPPLYDROPS.length - 1)];
            lootTwoQty = 80 + RandomUtility.inclusiveRandom(200);
        }

        //high supply
        if (lootTypeThree == 1) {
            lootThree = GameSettings.HIGHSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.HIGHSUPPLYDROPS.length - 1)];
            lootThreeQty = 10 + RandomUtility.inclusiveRandom(20);
        }

        //low supply
        else {
            lootThree = GameSettings.LOWSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.LOWSUPPLYDROPS.length - 1)];
            lootThreeQty = 80 + RandomUtility.inclusiveRandom(200);
        }


        int extraloot = 0;

        if (p.getStaffRights().getStaffRank() == 1)
            extraloot = 1;
        else if (p.getStaffRights().getStaffRank() == 2)
            extraloot = 2;
        else if (p.getStaffRights().getStaffRank() == 3)
            extraloot = 3;
        else if (p.getStaffRights().getStaffRank() == 4)
            extraloot = 4;
        else if (p.getStaffRights().getStaffRank() == 5)
            extraloot = 5;
        else if (p.getStaffRights().getStaffRank() > 5)
            extraloot = 6;


        p.getInventory().add(lootOne, lootOneQty);
        p.getInventory().add(lootTwo, lootTwoQty);

        if (p.looterBanking && p.getGameMode() != GameMode.ULTIMATE_IRONMAN) {
            Item itemOne = new Item(lootOne, lootOneQty);
            Item itemTwo = new Item(lootTwo, lootTwoQty);
            if (!p.chatFilter && p.personalFilterKeyLoot) {
                p.getPacketSender().sendMessage("Your first supply loot is: " + lootOneQty + " " + itemOne.getName());
                p.getPacketSender().sendMessage("Your second supply loot is: " + lootTwoQty + " " + itemTwo.getName());
            }
            p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, lootOne)), new Item(lootOne, lootOneQty), p.getInventory().getSlot(lootOne), false, true);
            p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, lootTwo)), new Item(lootTwo, lootTwoQty), p.getInventory().getSlot(lootTwo), false, true);
        }

        if (extraloot >= RandomUtility.inclusiveRandom(1, 20)) {
            p.getInventory().add(lootThree, lootThreeQty);

            if (p.looterBanking && p.getGameMode() != GameMode.ULTIMATE_IRONMAN) {
                Item itemThree = new Item(lootThree, lootThreeQty);
                if (!p.chatFilter && p.personalFilterKeyLoot) {
                    p.getPacketSender().sendMessage("Your third supply loot is: " + lootThreeQty + " " + itemThree.getName());
                }
                p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, lootThree)), new Item(lootThree, lootThreeQty), p.getInventory().getSlot(lootThree), false, true);
            }
        }

        int chance = 250;

        int crystalDust = RandomUtility.inclusiveRandom(chance);

        if (crystalDust == 1) {
            p.getInventory().add(223804, 1);
            String message = "@blu@[RARE DROP] " + p.getUsername() + " has just received a @red@Crystal Dust@blu@ from the Crystal Chest!";
            World.sendMessage("drops", message);
        }

        chance = 100;

        int rareCandy = RandomUtility.inclusiveRandom(chance);

        if (rareCandy == 1) {
            p.getInventory().add(GameSettings.rareCandy, 1);
        }

        chance = 100;

        int effigy = RandomUtility.inclusiveRandom(chance);

        if (effigy == 1) {
            p.getInventory().add(GameSettings.effigy, 1);
        }

        chance = 500;

        int mysteryBox = RandomUtility.inclusiveRandom(chance);

        if (mysteryBox == 1) {
            p.getInventory().add(GameSettings.mysteryBox, 1);
            String message = "@blu@[RARE DROP] " + p.getUsername() + " has just received a @red@Mystery Box@blu@ from the Crystal Chest!";
            World.sendMessage("drops", message);
        }

        chance = 2500;

        int eliteMysteryBox = RandomUtility.inclusiveRandom(chance);

        if (eliteMysteryBox == 1) {
            p.getInventory().add(GameSettings.eliteMysteryBox, 1);
            String message = "@blu@[RARE DROP] " + p.getUsername() + " has just received a @red@Elite Mystery Box@blu@ from the Crystal Chest!";
            World.sendMessage("drops", message);
        }


        //CustomObjects.objectRespawnTask(p, new GameObject(173 , chest.getPosition().copy(), 10, 0), chest, 10);

    }

}
