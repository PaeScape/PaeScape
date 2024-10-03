package com.arlania.world.content;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.*;
import com.arlania.model.container.impl.Bank;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

public class LarranChest {

    public static void openChest(final Player p, final GameObject chest) {

        int larranKey = 223490;

        if (!p.getClickDelay().elapsed(10)) {
            p.getPacketSender().sendMessage("@red@A magical force stops you from attemping to pick the lock.");
            return;
        }

        if(p.getInventory().contains(1523)) {

            if(p.getInventory().getFreeSlots() > 0 || p.getInventory().contains(larranKey)) {
                p.getInventory().add(larranKey, p.getInventory().getAmount(1523));
                p.getInventory().delete(1523, p.getInventory().getAmount(1523));
            }
        }


        if (!p.getInventory().contains(larranKey)) {
            p.getPacketSender().sendMessage("@red@This chest can only be opened with a Larran's Key.");
            return;
        }

        if (p.getInventory().getFreeSlots() < 5) {
            p.getPacketSender().sendMessage("@red@You need 5 free slots to open Larran's Chest.");
            return;
        }

        if (p.getCombatBuilder().isBeingAttacked()) {
            p.getPacketSender().sendMessage("You must wait a few seconds after being out of combat before doing this.");
            return;
        }

        p.performAnimation(new Animation(827));

        if (p.getPosition().getY() > 3955) {
            if (RandomUtility.inclusiveRandom(1, 10) > 1) {
                p.getInventory().delete(larranKey, 1);
            } else {
                p.getPacketSender().sendMessage("Larran's Magic saves your key from disappearing...");
            }
        } else {
            p.getInventory().delete(larranKey, 1);
        }
        handleChest(p, chest);

    }


    public static void handleChest(final Player p, final GameObject chest) {


        if (!p.chatFilter)
            p.getPacketSender().sendMessage("You open the chest...");

        p.TaintedChests++;

        p.getAchievementTracker().progress(AchievementData.OPEN_10_LARRAN_CHESTS, 1);
        p.getAchievementTracker().progress(AchievementData.OPEN_100_LARRAN_CHESTS, 1);
        p.getAchievementTracker().progress(AchievementData.OPEN_1000_LARRAN_CHESTS, 1);


        if (p.TaintedChests >= 10)
            p.getAchievementTracker().progress(AchievementData.OPEN_10_LARRAN_CHESTS, 10);
        if (p.TaintedChests >= 100)
            p.getAchievementTracker().progress(AchievementData.OPEN_100_LARRAN_CHESTS, 100);
        if (p.TaintedChests >= 1000)
            p.getAchievementTracker().progress(AchievementData.OPEN_1000_LARRAN_CHESTS, 1000);


        //roll random kind of supply loot
        //give random quantity based on type of supply loot
        //add to inventory

        int lootOne = 2;

        int lootOneQty = 0;

        //high supply
        lootOne = GameSettings.WILDYSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.WILDYSUPPLYDROPS.length - 1)];
        lootOneQty = 25 + RandomUtility.inclusiveRandom(50);


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

        if (p.looterBanking && p.getGameMode() != GameMode.ULTIMATE_IRONMAN) {
            Item itemOne = new Item(lootOne, lootOneQty);
            if (!p.chatFilter && p.personalFilterKeyLoot) {
                p.getPacketSender().sendMessage("Your first supply loot is: " + lootOneQty + " " + itemOne.getName());
            }
            p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, lootOne)), new Item(lootOne, lootOneQty), p.getInventory().getSlot(lootOne), false, true);
        }

        int chance = 50;

        int rareCandy = RandomUtility.inclusiveRandom(chance);

        if (rareCandy == 1) {
            p.getInventory().add(GameSettings.rareCandy, 1);
        }

        chance = 100;

        int statuette = RandomUtility.inclusiveRandom(chance);

        if (statuette == 1) {
            int[] artifacts = {14876, 14877, 14878, 14879, 14880, 14881, 14882, 14883, 14884, 14885, 14886, 14887, 14888, 14889, 14890, 14891, 14892};
            int rand = RandomUtility.inclusiveRandom(artifacts.length - 1);

            p.getInventory().add(artifacts[rand], 1);
        }

        chance = 500;

        int zenyte = RandomUtility.inclusiveRandom(chance);

        if (zenyte == 1) {
            p.getInventory().add(20566, 1);
        }

        chance = 1000;

        if (p.wildTainted > 0) {
            chance = 500;
        }

        int tainted = RandomUtility.inclusiveRandom(chance);

        if (tainted == 1) {
            p.getInventory().add(2050, 1);
            World.sendMessage("drops", "<img=10><col=009966> " + p.getUsername() + " has just received a Tainted Crystal from Larran's Chest!");

            String discordMessage = p.getUsername() + " received a Tainted Crystal from Larran's Chest!";

            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
        }


    }

}
