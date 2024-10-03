package com.arlania.world.content.minigames.impl.chaosraids;


import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.GroundItem;
import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.model.container.impl.MailBox;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.PlayerLogs;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.clog.CollectionLog;
import com.arlania.world.content.clog.CollectionLog.CustomCollection;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.content.minigames.impl.kingdom.NobilitySystem;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

import static com.arlania.world.content.minigames.impl.strongholdraids.Equipment.*;

public class ChaosRewards {

    public static int[] uniques = {21031, 21032, 21033, 20012, 20010, 20011, 20013, 20014, 14062, 20016, 20017, 20018, 222951, 207582};

    public static int keyFive = 201543;
    public static int keySix = 201544;
    public static int keySeven = 201545;
    public static int keyEight = 201546;
    public static int keyNine = 201547;
    public static int keyTen = 206754;

    public static void grantLoot(Player member) {

        int bossOfTheDay = 7286;
        int raidBonus = member.chaosRaidBonus;
        boolean raidBoost = member.getchaosKC() >= 250;

        member.getCollectionLog().addKill(CollectionLog.CustomCollection.CHAOS.getId());

        int raidKey = 0;

        if (member.chaosLootWave == 5)
            raidKey = keyFive;
        else if (member.chaosLootWave == 6)
            raidKey = keySix;
        else if (member.chaosLootWave == 7)
            raidKey = keySeven;
        else if (member.chaosLootWave == 8)
            raidKey = keyEight;
        else if (member.chaosLootWave == 9)
            raidKey = keyNine;
        else if (member.chaosLootWave == 10)
            raidKey = keyTen;

        int points = 2 * member.chaosLootWave;

        if (member.mysteryPass)
            member.mpRaidsDone += 1;

        member.setPaePoints(member.getPaePoints() + points);
        member.getPacketSender().sendMessage("You've gained " + points + " Paepoints for completing the raid!");
        member.getPacketSender().sendMessage("You now have " + member.getPaePoints() + " PaePoints!");
        member.setchaosKC(member.getchaosKC() + 1);
        member.getPacketSender().sendMessage("You now have " + member.getchaosKC() + " Chaos completions!");
        member.getSkillManager().addExperience(Skill.DUNGEONEERING, 30000 * member.chaosLootWave);

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

        member.getAchievementTracker().progress(AchievementData.COMPLETE_25_CHAOS_RAID, 1);
        member.getAchievementTracker().progress(AchievementData.COMPLETE_100_CHAOS_RAID, 1);
        member.getAchievementTracker().progress(AchievementData.COMPLETE_250_CHAOS_RAID, 1);

        if (member.getchaosKC() >= 250)
            member.getAchievementTracker().progress(AchievementData.COMPLETE_250_CHAOS_RAID, 250);
        if (member.getchaosKC() >= 100)
            member.getAchievementTracker().progress(AchievementData.COMPLETE_100_CHAOS_RAID, 100);
        if (member.getchaosKC() >= 25)
            member.getAchievementTracker().progress(AchievementData.COMPLETE_25_CHAOS_RAID, 25);

        member.getInventory().add(raidKey, 1);

        //2nd Key (Difficulty)
        if(member.tempRaidsDifficulty >= RandomUtility.inclusiveRandom(1, 10)) {
            member.getInventory().add(raidKey, 1);
            member.getPacketSender().sendMessage("You receive an additional Raid Key from your Difficulty Setting!");

            bonusLoot(member, member.difficulty);
        }

        //3rd Key (Wealth Bonus)
        if (member.getWealthBonus(member, bossOfTheDay, raidBoost, raidBonus) * 100 > RandomUtility.inclusiveRandom(100) + 1) {
            member.getInventory().add(raidKey, 1);
            member.getPacketSender().sendMessage("You receive an additional Raid Key from your Wealth Bonus!");
        }

        //4th Key (Raider Event)
        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.RAIDER)) {
            if (RandomUtility.inclusiveRandom(100) > 50) {
                member.getInventory().add(raidKey, 1);
                member.getPacketSender().sendMessage("@red@You receive an additional Raid Key from the Raider Event!");
            }
        }

        //5th Key (Nobility System)
        if(NobilitySystem.getNobilityBoost(member) > RandomUtility.RANDOM.nextDouble()) {
            member.getInventory().add(raidKey, 1);
            member.getPacketSender().sendMessage("@red@You receive an additional Raid Key from the Nobility System!");
        }

        if(member.difficulty == 10) {
            member.getAchievementTracker().progress(AchievementData.COMPLETE_M7_CHAOS_RAID, 1);
        }  else if(member.difficulty == 8) {
            member.getAchievementTracker().progress(AchievementData.COMPLETE_M5_CHAOS_RAID, 1);
        }  else if(member.difficulty == 6) {
            member.getAchievementTracker().progress(AchievementData.COMPLETE_M3_CHAOS_RAID, 1);
        }  else if(member.difficulty == 4) {
            member.getAchievementTracker().progress(AchievementData.COMPLETE_M1_CHAOS_RAID, 1);
        }  else if(member.difficulty == 2) {
            member.getAchievementTracker().progress(AchievementData.COMPLETE_HARD_CHAOS_RAID, 1);
        }



        if (member.getSkiller().getSkillerTask().getType() == "chaos") {
            member.getSkiller().handleSkillerTaskGather(true);
            member.getSkillManager().addExperience(Skill.SKILLER, member.getSkiller().getSkillerTask().getXP());
        }

        if (member.getSubscription().getSubscriptionLevel() > 0) {
            if (RandomUtility.inclusiveRandom(50) == 1) {

                if (member.getInventory().getFreeSlots() > 0)
                    member.getInventory().add(member.getSubscription().getBoxType(), 1);
                else
                    GroundItemManager.spawnGroundItem(member, new GroundItem(new Item(member.getSubscription().getBoxType(), 1), member.getPosition(), member.getUsername(), false, 150, true, 200));

                String message = "@blu@[SUBSCRIPTION BOX]";
                member.sendMessage(message);

                String message2 = "@blu@[SUB BOX] " + member.getUsername() + "@bla@ has just received a @blu@Subscription Box@bla@ from Chaos Raids!";
                World.sendMessage("drops", message2);


                String discordMessage = "[" + member.getSubscription() + "]" + member.getUsername()
                        + " just received a Subscription Box from Chaos Raids at " + member.getchaosKC() + " KC!";

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.SUBSCRIPTION_BOX_DROPS_CH).get());

            }

        }

        member.resetRaids(member);
    }

    public static void bonusLoot(Player member, int difficulty) {

        if(RandomUtility.inclusiveRandom(20) != 1 || difficulty < 0 || difficulty > 10) {
            return;
        }

        int tier = difficulty - 1;

        int[] dungEquipment = {meleeHelm[tier][0], meleeBody[tier][0], meleeLegs[tier][0], meleeRapier[tier][0],
                meleeLong[tier][0], meleeMaul[tier][0], meleeShield[tier][0], meleeGloves[tier][0], meleeBoots[tier][0],
                rangeHelm[tier][0], rangeBody[tier][0], rangeLegs[tier][0], rangeBow[tier][0], rangedGloves[tier][0], rangedBoots[tier][0],
                magicHelm[tier][0], magicBody[tier][0], magicLegs[tier][0], magicStaff[tier][0], magicGloves[tier][0], magicBoots[tier][0]};

        int randomLoot = dungEquipment[RandomUtility.inclusiveRandom(dungEquipment.length - 1)];

        member.getPacketSender().sendMessage("You received a " + ItemDefinition.forId(randomLoot) + " and it was sent to your Mailbox!");
        MailBox.addMail(member.getUsername().toString(), new Item(randomLoot, 1));
    }

    public static void announce(Player player, Item item) {
        String itemName = item.getDefinition().getName();
        String itemMessage = Misc.anOrA(itemName) + " " + itemName;
        String message = player.getUsername() + " has just received <col=ad66ff>" + itemMessage
                + "</col> from Chaos Raids at " + player.getchaosKC() + " KC!";
        World.sendMessage("drops", message);

        player.getCollectionLog().handleDrop(CustomCollection.CHAOS.getId(), item.getId(), 1);

        String discordMessage = player.getUsername() + " has just received " + itemMessage + " from Chaos Raids at " + player.getchaosKC() + " KC!";
        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
        PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemMessage + " from Chaos Raids.");
    }
}
