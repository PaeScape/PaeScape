package com.arlania.world.content.minigames.impl.gwdraids;


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
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

import static com.arlania.world.content.minigames.impl.strongholdraids.Equipment.*;

public class GwdrRewards {

    public static int[] uniques = {14008, 14009, 14010, 14011, 14012, 14013, 14014, 14015, 14016, 224003, 11987};

    public static void grantLoot(RaidsParty party, Player member) {

        int bossOfTheDay = 13447;
        int raidKey = 211942;
        int raidBonus = member.gwdRaidBonus;
        boolean raidBoost = member.getGwdRaidKC() >= 250;

        member.getCollectionLog().addKill(CollectionLog.CustomCollection.GWD.getId());


        member.getInventory().add(raidKey, 1);

        //2nd Key (Difficulty)
        if(party.getOwner().difficulty >= RandomUtility.inclusiveRandom(1, 10)) {
            member.getInventory().add(raidKey, 1);
            member.getPacketSender().sendMessage("You receive an additional Raid Key from your Difficulty Setting!");

            bonusLoot(member, party.getOwner().difficulty);
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

        if(member.checkAchievementAbilities(member, "cryptic") && RandomUtility.inclusiveRandom(2500) == 1) {
            member.getInventory().add(6749, 1);
        }
        if(member.checkAchievementAbilities(member, "gambler") && RandomUtility.inclusiveRandom(1000) == 1) {
            member.getInventory().add(2795, 1);
        }

        if(party.getOwner().difficulty == 10) {
            member.getAchievementTracker().progress(AchievementData.COMPLETE_M7_GWD_RAID, 1);
        }  else if(party.getOwner().difficulty == 8) {
            member.getAchievementTracker().progress(AchievementData.COMPLETE_M5_GWD_RAID, 1);
        }  else if(party.getOwner().difficulty == 6) {
            member.getAchievementTracker().progress(AchievementData.COMPLETE_M3_GWD_RAID, 1);
        }  else if(party.getOwner().difficulty == 4) {
            member.getAchievementTracker().progress(AchievementData.COMPLETE_M1_GWD_RAID, 1);
        }  else if(party.getOwner().difficulty == 2) {
            member.getAchievementTracker().progress(AchievementData.COMPLETE_HARD_GWD_RAID, 1);
        }

        member.setPaePoints(member.getPaePoints() + 15);
        member.getPacketSender().sendMessage("You've gained " + 15 + " Paepoints for completing the raid!");
        member.getPacketSender().sendMessage("You now have " + member.getPaePoints() + " PaePoints!");
        member.setGwdRaidKC(member.getGwdRaidKC() + 1);
        member.getPacketSender().sendMessage("You now have " + member.getGwdRaidKC() + " GWD Raid completions!");
        member.getSkillManager().addExperience(Skill.DUNGEONEERING, 100000);

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

        member.getAchievementTracker().progress(AchievementData.COMPLETE_25_GWD_RAID, 1);
        member.getAchievementTracker().progress(AchievementData.COMPLETE_100_GWD_RAID, 1);
        member.getAchievementTracker().progress(AchievementData.COMPLETE_250_GWD_RAID, 1);

        if (member.getGwdRaidKC() >= 250)
            member.getAchievementTracker().progress(AchievementData.COMPLETE_250_GWD_RAID, 250);
        if (member.getGwdRaidKC() >= 100)
            member.getAchievementTracker().progress(AchievementData.COMPLETE_100_GWD_RAID, 100);
        if (member.getGwdRaidKC() >= 25)
            member.getAchievementTracker().progress(AchievementData.COMPLETE_25_GWD_RAID, 25);

        if (member.mysteryPass)
            member.mpRaidsDone += 1;


        if (member.getSkiller().getSkillerTask().getType() == "gwd") {
            member.getSkiller().handleSkillerTaskGather(true);
            member.getSkillManager().addExperience(Skill.SKILLER, member.getSkiller().getSkillerTask().getXP());
        }

        member.gwdRaidLoot = false;

        if (member.getSubscription().getSubscriptionLevel() > 0) {
            if (RandomUtility.inclusiveRandom(50) == 1) {

                if (member.getInventory().getFreeSlots() > 0)
                    member.getInventory().add(member.getSubscription().getBoxType(), 1);
                else
                    GroundItemManager.spawnGroundItem(member, new GroundItem(new Item(member.getSubscription().getBoxType(), 1), member.getPosition(), member.getUsername(), false, 150, true, 200));

                String message = "@blu@[SUBSCRIPTION BOX]";
                member.sendMessage(message);

                String message2 = "@blu@[SUB BOX] " + member.getUsername() + "@bla@ has just received a @blu@Subscription Box@bla@ from GWD Raids!";
                World.sendMessage("drops", message2);


                String discordMessage = "[" + member.getSubscription() + "]" + member.getUsername()
                        + " just received a Subscription Box from GWD Raids at " + member.getGwdRaidKC() + " KC!";

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
                + "</col> from GWD Raids at " + player.getGwdRaidKC() + " KC!";
        World.sendMessage("drops", message);

        player.getCollectionLog().handleDrop(CustomCollection.GWD.getId(), item.getId(), 1);

        String discordMessage = player.getUsername() + " has just received " + itemMessage + " from GWD Raids at " + player.getGwdRaidKC() + " KC!";
        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
        PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemMessage + " from GWD Raids.");
    }
}
