package com.arlania.world.content.minigames.impl.barrows;


import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.*;
import com.arlania.model.container.impl.Bank;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.DropLog;
import com.arlania.world.content.DropLog.DropLogEntry;
import com.arlania.world.content.PlayerLogs;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.clog.CollectionLog.CustomCollection;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

public class Rewards {

    public static int[] lowSupply = GameSettings.LOWSUPPLYDROPS;

    public static int[] highSupply = GameSettings.HIGHSUPPLYDROPS;

    public static int[] uniques = {4708, 4710, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738,
            4745, 4747, 4749, 4751, 4753, 4755, 4757, 4759, 18845, 20104};

    public static void grantLoot(Player member) {

        if (!member.barrowsRaidLoot) {
            member.getPacketSender().sendMessage("@red@You must complete the Raid to get loot!");
            return;
        }

        int teamSize = 1;

        if (member.teamSize > 1)
            teamSize = member.teamSize;

        int kills = member.totalBarrowsRaidKills / teamSize;
        int points = kills;

        boolean raidBoost = member.getCompletedBarrows() >= 250;

        double killsPerDrop = 30 * (1 - member.getWealthBonus(member, 0, raidBoost, 0));

        if (killsPerDrop < 15)
            killsPerDrop = 15;

        member.getCollectionLog().addKill(CustomCollection.BARROWS.getId());

        int barrowsPieces = (int) (kills / killsPerDrop);

        if (RandomUtility.inclusiveRandom(500) <= barrowsPieces) {
            member.performAnimation(new Animation(827));
            member.getInventory().add(20104, 1);
            String itemName = new Item(20104).getDefinition().getName();
            String itemMessage = Misc.anOrA(itemName) + " " + itemName;
            String message = "@blu@[RARE DROP] " + member.getUsername()
                    + " has just received @red@" + itemMessage + "@blu@ at " + member.getCompletedBarrows() + " KC at Barrows Raids!";
            World.sendMessage("drops", message);
            DropLog.submit(member, new DropLogEntry(20104, 1));
            PlayerLogs.log(member.getUsername(), member.getUsername() + " received " + itemMessage + " from Barrows Raids!");
            member.getCollectionLog().handleDrop(CustomCollection.BARROWS.getId(), 20104, 1);
        }


        int mindRuneQty = 300 + RandomUtility.inclusiveRandom(10 * member.barrowsRaidKills / teamSize);
        int chaosRuneQty = 200 + RandomUtility.inclusiveRandom(10 * member.barrowsRaidKills / teamSize);
        int deathRuneQty = 100 + RandomUtility.inclusiveRandom(10 * member.barrowsRaidKills / teamSize);
        int bloodRuneQty = 40 + RandomUtility.inclusiveRandom(10 * member.barrowsRaidKills / teamSize);
        int boltRackQty = 50 + RandomUtility.inclusiveRandom(15 * member.barrowsRaidKills / teamSize);


        if (member.getInventory().getFreeSlots() >= 5) {
            member.getInventory().add(558, mindRuneQty);
            member.getInventory().add(562, chaosRuneQty);
            member.getInventory().add(560, deathRuneQty);
            member.getInventory().add(565, bloodRuneQty);
            member.getInventory().add(4740, boltRackQty);
        } else {
            Position pos = GameSettings.RAIDS_LOBBY_POSITION;
            Item mindRunes = new Item(558, mindRuneQty);
            Item chaosRunes = new Item(558, chaosRuneQty);
            Item deathRunes = new Item(558, deathRuneQty);
            Item bloodRunes = new Item(558, bloodRuneQty);
            Item boltRacks = new Item(558, boltRackQty);


            GroundItemManager.spawnGroundItem(member, new GroundItem(mindRunes, pos, member.getUsername(), false, 150, true, 200));
            GroundItemManager.spawnGroundItem(member, new GroundItem(chaosRunes, pos, member.getUsername(), false, 150, true, 200));
            GroundItemManager.spawnGroundItem(member, new GroundItem(deathRunes, pos, member.getUsername(), false, 150, true, 200));
            GroundItemManager.spawnGroundItem(member, new GroundItem(bloodRunes, pos, member.getUsername(), false, 150, true, 200));
            GroundItemManager.spawnGroundItem(member, new GroundItem(boltRacks, pos, member.getUsername(), false, 150, true, 200));

        }

        if (barrowsPieces == 0) {
            if (RandomUtility.inclusiveRandom(30) < member.totalBarrowsRaidKills) {
                Position pos = GameSettings.RAIDS_LOBBY_POSITION;
                Item barrowsLoot = new Item(barrows[RandomUtility.inclusiveRandom(barrows.length - 1)], 1);
                GroundItemManager.spawnGroundItem(member, new GroundItem(barrowsLoot, pos, member.getUsername(), false, 150, true, 200));
            }
        }


        for (int i = 0; i < barrowsPieces; i++) {
            Position pos = GameSettings.RAIDS_LOBBY_POSITION;
            Item barrowsLoot = new Item(barrows[RandomUtility.inclusiveRandom(barrows.length - 1)], 1);

            if (member.looterBanking) {
                member.getInventory().add(barrowsLoot.getId(), 1);

                if (member.looterBanking && member.getGameMode() != GameMode.ULTIMATE_IRONMAN) {
                    member.getInventory().switchItem(member.getBank(Bank.getTabForItem(member, barrowsLoot.getId())), new Item(barrowsLoot.getId(), 1), member.getInventory().getSlot(barrowsLoot.getId()), false, true);
                    if (member.personalFilterKeyLoot) {
                        member.getPacketSender().sendMessage("Your " + barrowsLoot.getName() + " was sent to the bank.");
                    }
                }

            } else {
                GroundItemManager.spawnGroundItem(member, new GroundItem(barrowsLoot, pos, member.getUsername(), false, 150, true, 200));
                member.getPacketSender().sendMessage("Your " + barrowsLoot.getName() + " was dropped under you.");
            }

            member.getCollectionLog().handleDrop(CustomCollection.BARROWS.getId(), barrowsLoot.getId(), 1);
        }

        member.setPaePoints(member.getPaePoints() + points);
        member.getPacketSender().sendMessage("You've gained " + points + " Paepoints for completing the Barrows Raid!");
        member.getPacketSender().sendMessage("You now have " + member.getPaePoints() + " PaePoints!");
        member.setCompletedBarrows(member.getCompletedBarrows() + 1);
        member.getPacketSender().sendMessage("You now have " + member.getCompletedBarrows() + " Barrows Raid completions!");
        member.getSkillManager().addExperience(Skill.DUNGEONEERING, 1000 * member.totalBarrowsRaidKills / teamSize);

        member.getAchievementTracker().progress(AchievementData.COMPLETE_5_RAIDS, 1);
        member.getAchievementTracker().progress(AchievementData.COMPLETE_10_RAIDS, 1);
        member.getAchievementTracker().progress(AchievementData.COMPLETE_25_RAIDS, 1);
        member.getAchievementTracker().progress(AchievementData.COMPLETE_50_RAIDS, 1);
        member.getAchievementTracker().progress(AchievementData.COMPLETE_100_RAIDS, 1);
        member.getAchievementTracker().progress(AchievementData.COMPLETE_250_RAIDS, 1);
        member.getAchievementTracker().progress(AchievementData.COMPLETE_500_RAIDS, 1);
        member.getAchievementTracker().progress(AchievementData.COMPLETE_1000_RAIDS, 1);
        member.getAchievementTracker().progress(AchievementData.COMPLETE_2500_RAIDS, 1);
        member.getAchievementTracker().progress(AchievementData.COMPLETE_5000_RAIDS, 1);

        member.getAchievementTracker().progress(AchievementData.COMPLETE_50_BARROWS_RAID, 1);

        if (member.getCompletedBarrows() >= 50)
            member.getAchievementTracker().progress(AchievementData.COMPLETE_50_BARROWS_RAID, 50);


        if (member.getSkiller().getSkillerTask().getType() == "barrowsraid") {
            member.getSkiller().handleSkillerTaskGather(true);
            member.getSkillManager().addExperience(Skill.SKILLER, member.getSkiller().getSkillerTask().getXP());
        }

        member.barrowsRaidLoot = false;
        member.barrowsLootWave = 0;
        member.barrowsRaidKills = 0;
        member.totalBarrowsRaidKills = 0;
    }

    public static void announce(Player player, Item item) {
        String itemName = item.getDefinition().getName();
        String itemMessage = Misc.anOrA(itemName) + " " + itemName;
        String message = player.getUsername() + " has just received <col=ad66ff>" + itemMessage
                + "</col> from Barrows Raids at " + player.getCompletedBarrows() + " KC!";
        //World.sendMessage(message);
        PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemMessage + " from Barrows Raids.");

        String discordMessage = player.getUsername() + " has just received " + itemMessage + " from Barrows Raids at " + player.getCompletedBarrows() + " KC!";
        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
    }

    public static int[] barrows = {4708, 4710, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738,
            4745, 4747, 4749, 4751, 4753, 4755, 4757, 4759, 18845};
}
