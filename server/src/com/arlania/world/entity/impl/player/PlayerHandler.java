package com.arlania.world.entity.impl.player;

import com.arlania.*;
import com.arlania.engine.task.TaskManager;
import com.arlania.engine.task.impl.*;
import com.arlania.model.*;
import com.arlania.model.container.impl.Bank;
import com.arlania.model.container.impl.Equipment;
import com.arlania.model.definitions.WeaponAnimations;
import com.arlania.model.definitions.WeaponInterfaces;
import com.arlania.net.PlayerSession;
import com.arlania.net.SessionState;
import com.arlania.net.packet.impl.EquipPacketListener;
import com.arlania.net.security.ConnectionHandler;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.content.PlayerLogs;
import com.arlania.world.content.*;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.clan.ClanChatManager;
import com.arlania.world.content.combat.effect.CombatPoisonEffect;
import com.arlania.world.content.combat.effect.CombatTeleblockEffect;
import com.arlania.world.content.combat.magic.Autocasting;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.content.combat.pvp.BountyHunter;
import com.arlania.world.content.combat.range.DwarfMultiCannon;
import com.arlania.world.content.combat.weapon.CombatSpecial;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.grandexchange.GrandExchange;
import com.arlania.world.content.minigames.bots.FarmingBot;
import com.arlania.world.content.minigames.bots.FishingBot;
import com.arlania.world.content.minigames.bots.MiningBot;
import com.arlania.world.content.minigames.bots.WCingBot;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.content.skill.impl.hunter.Hunter;
import com.arlania.world.content.skill.impl.slayer.Slayer;
import com.arlania.world.entity.impl.player.antibotting.actions.Action;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.logging.Level;


public class PlayerHandler {


    /**
     * Gets the player according to said name.
     *
     * @param name The name of the player to search for.
     * @return The player who has the same name as said param.
     */


    public static Player getPlayerForName(String name) {
        for (Player player : World.getPlayers()) {
            if (player == null)
                continue;
            if (player.getUsername().equalsIgnoreCase(name))
                return player;
        }
        return null;
    }

    public static void handleLogin(Player player) {
        System.out.println("[World] Registering player - [username, host] : [" + player.getUsername() + ", " + player.getHostAddress() + "]");

        if (player.getStaffRights().toString().equals("SUPPORT")) {
            player.setStaffRights(StaffRights.DONATOR);
        }

        player.getMailBox().checkRemoteItemSystems();
        player.getCollectionLog().loadUpgrades();
        player.getTradingPostManager().hookShop();
        player.getActionTracker().offer(new Action(Action.ActionType.LOGIN));
        player.setActive(true);
        ConnectionHandler.add(player.getHostAddress());
        World.getPlayers().add(player);
        World.updatePlayersOnline();
        PlayersOnlineInterface.add(player);
        player.getSession().setState(SessionState.LOGGED_IN);

        player.getPacketSender().sendMapRegion().sendDetails();

        player.getRecordedLogin().reset();

        player.getPacketSender().sendTabs();

        for (int i = 0; i < player.getBanks().length; i++) {
            if (player.getBank(i) == null) {
                player.setBank(i, new Bank(player));
            }
        }
        player.getInventory().refreshItems();
        player.getEquipment().refreshItems();

        WeaponAnimations.assign(player, player.getEquipment().get(Equipment.WEAPON_SLOT));
        WeaponInterfaces.assign(player, player.getEquipment().get(Equipment.WEAPON_SLOT));
        CombatSpecial.updateBar(player);
        BonusManager.update(player, 0, 0);

        player.getSummoning().login();
        //player.getFarming().load();
        Slayer.checkDuoSlayer(player, true);


        player.getRelations().setPrivateMessageId(1).onLogin(player).updateLists(true);

        player.getPacketSender().sendConfig(172, player.isAutoRetaliate() ? 1 : 0)
                .sendTotalXp(player.getSkillManager().getTotalGainedExp())
                .sendConfig(player.getFightType().getParentId(), player.getFightType().getChildId())
                .sendRunStatus()
                .sendRunEnergy(player.getRunEnergy())
                .sendString(8135, "" + player.getMoneyInPouch())
                .sendInteractionOption("Follow", 3, false)
                .sendInteractionOption("Trade With", 4, false);

        Autocasting.onLogin(player);
        EquipPacketListener.equipStaff(player);
        PrayerHandler.deactivateAll(player);
        CurseHandler.deactivateAll(player);
        BonusManager.sendCurseBonuses(player);
        InformationPanel.refreshPanel(player);
        //Minigame.updateInterface(player);

        TaskManager.submit(new PlayerSkillsTask(player));
        if (player.isPoisoned()) {
            TaskManager.submit(new CombatPoisonEffect(player));
        }
        if (player.getPrayerRenewalPotionTimer() > 0) {
            TaskManager.submit(new PrayerRenewalPotionTask(player));
        }
        if (player.getTeleblockTimer() > 0) {
            TaskManager.submit(new CombatTeleblockEffect(player));
        }
        if (player.getSkullTimer() > 0) {
            player.setSkullIcon(1);
            TaskManager.submit(new CombatSkullEffect(player));
        }

        if (player.getSpecialPercentage() < 100) {
            TaskManager.submit(new PlayerSpecialAmountTask(player));
        }
        if (player.hasStaffOfLightEffect()) {
            TaskManager.submit(new StaffOfLightSpecialAttackTask(player));
        }
        if (player.getMinutesBonusExp() >= 0) {
            TaskManager.submit(new BonusExperienceTask(player));
        }


        player.getUpdateFlag().flag(Flag.APPEARANCE);

        Lottery.onLogin(player);
        Locations.login(player);


        if (player.playerEventTimer > 0) {
            if (player.accelerateEvent) {
                player.accelerateEventTimer = player.playerEventTimer;
                player.playerEventTimer = 0;
                player.accelerateEvent = false;
            }
            if (player.accuracyEvent) {
                player.accuracyEventTimer = player.playerEventTimer;
                player.playerEventTimer = 0;
                player.accuracyEvent = false;
            }
            if (player.droprateEvent) {
                player.droprateEventTimer = player.playerEventTimer;
                player.playerEventTimer = 0;
                player.droprateEvent = false;
            }
            if (player.doubleBossPointEvent) {
                player.doubleBossPointEventTimer = player.playerEventTimer;
                player.playerEventTimer = 0;
                player.doubleBossPointEvent = false;
            }
            if (player.doubleSkillerPointsEvent) {
                player.doubleSkillerPointsEventTimer = player.playerEventTimer;
                player.playerEventTimer = 0;
                player.doubleSkillerPointsEvent = false;
            }
            if (player.doubleSlayerPointsEvent) {
                player.doubleSlayerPointsEventTimer = player.playerEventTimer;
                player.playerEventTimer = 0;
                player.doubleSlayerPointsEvent = false;
            }
            if (player.loadedEvent) {
                player.loadedEventTimer = player.playerEventTimer;
                player.playerEventTimer = 0;
                player.loadedEvent = false;
            }
            if (player.doubleLoot) {
                player.doubleLootTimer = player.playerEventTimer;
                player.playerEventTimer = 0;
                player.doubleLoot = false;
            }
            if (player.doubleExpEvent) {
                player.doubleExpEventTimer = player.playerEventTimer;
                player.playerEventTimer = 0;
                player.doubleExpEvent = false;
            }
            if (player.eventBoxEvent) {
                player.universalDropEventTimer = player.playerEventTimer;
                player.playerEventTimer = 0;
                player.eventBoxEvent = false;
            }
            if (player.bossKillsEvent) {
                player.bossKillsEventTimer = player.playerEventTimer;
                player.playerEventTimer = 0;
                player.bossKillsEvent = false;
            }
            if (player.maxHitEvent) {
                player.maxHitEventTimer = player.playerEventTimer;
                player.playerEventTimer = 0;
                player.maxHitEvent = false;
            }

        }

        if(!player.passFix)
            player.passFix(player);

        if(!player.perkReset)
            player.perkFix(player);

        if(player.wildHoarder > 0)
            player.removeWildHoarder(player);


        if(player.getStaffRights().getStaffRank() > 6) {

            player.subscriptionStartDate = GameLoader.getEpoch();
            player.subscriptionEndDate = GameLoader.getEpoch() + 5;
            player.setSubscription(Subscriptions.DRAGONSTONE);

        } else if (GameLoader.getEpoch() > player.subscriptionEndDate && player.getSubscription().isSubscriber()) {

            player.getPacketSender().sendMessage("@red@Your subscription has ended!");

            player.subscriptionStartDate = 0;
            player.subscriptionEndDate = 0;
            player.setSubscription(Subscriptions.PLAYER);

        }


        if (GameSettings.holidayEvent) {
            player.getPacketSender().sendMessage("<img=10> @blu@Our Holiday Event is now active. Check Discord for more information!");
        }

        if (player.getexprate() > player.prestige + 5)
            player.setexprate(player.prestige + 5);


        if (player.experienceLocked()) {
            player.getPacketSender().sendMessage("@red@Warning: your experience is currently locked.");
        }
        ClanChatManager.handleLogin(player);
        ClanChatManager.join(player, "help");


        if (WellOfWealth.isActive()) {
            player.getPacketSender().sendMessage("<img=10> @blu@The Well of Wealth is granting x2 Easier Droprates for another " + WellOfWealth.getMinutesRemaining() + " minutes.");
        }
        if (WellOfGoodwill.isActive()) {
            player.getPacketSender().sendMessage("<img=10> @blu@The Well of Goodwill is granting 30% Bonus xp for another " + WellOfGoodwill.getMinutesRemaining() + " minutes.");
        }
        PlayerPanel.refreshPanel(player);
        InformationPanel.refreshPanel(player);

        /*if (player.slayerQty != "max" && player.slayerQty != "min" && player.slayerQty != "random")
            player.slayerQty = "random";

        if (player.artisanQty != "max" && player.artisanQty != "min" && player.artisanQty != "random")
            player.artisanQty = "random";*/


        player.getPacketSender().sendMessage("@blu@Today's daily boss is: " + GameLoader.getSpecialBossDay());
        player.getPacketSender().sendMessage("@blu@Today's bonus XP skill is: " + GameLoader.getSkillDay());

        GlobalEventHandler.listAllActiveEffects(player);

        if (GameLoader.getWeekOfYear() != player.WeekOfYear) {
            int xpTimer = 60000 + (3000 * player.getStaffRights().getStaffRank()) + (1500 * player.prestige);
            player.WeekOfYear = GameLoader.getWeekOfYear();
            player.doubleExpTimer = xpTimer;
            player.getPacketSender().sendMessage("@blu@" + xpTimer / 100 + " minutes of Double XP have been added to your account!");
        }


        //New player
        if (player.newPlayer()) {
            Tutorial.newPlayer(player);
        }

        player.getPacketSender().updateSpecialAttackOrb().sendIronmanMode(player.getGameMode().ordinal());

        if (player.getStaffRights() == StaffRights.OWNER || player.getStaffRights() == StaffRights.MODERATOR || player.getStaffRights() == StaffRights.ADMINISTRATOR || player.getStaffRights() == StaffRights.DEVELOPER) {
            World.sendMessage("", "<img=" + player.getStaffRights().ordinal() + "><col=6600CC> " + Misc.formatText(player.getStaffRights().toString().toLowerCase()) + " " + player.getUsername() + " has just logged in, feel free to message them for support.");
        }
        if (player.getStaffRights() == StaffRights.MODERATOR || player.getStaffRights() == StaffRights.ADMINISTRATOR || player.getStaffRights() == StaffRights.DEVELOPER || player.getStaffRights() == StaffRights.OWNER) {
            if (!StaffList.staff.contains(player.getUsername())) {
                StaffList.login(player);
            }
        }

        /*if (player.getUsername().contentEquals("Spiderman")) {
            World.sendMessage("@red@Welcome your Community Manager. The friendly neighborhood Spiderman!");
            //World.sendMessage("<img="+player.getStaffRights().ordinal()+"><col=6600CC> Community Manager " + player.getUsername() + " has just logged in, feel free to contact them.");
        }*/

        GrandExchange.onLogin(player);

        if (player.getMinigameAttributes().getMiningBotAttributes().getResourcesLeftToGather() > 0) {
            MiningBot.start(player);
        }

        if (player.getMinigameAttributes().getWCingBotAttributes().getResourcesLeftToGather() > 0) {
            WCingBot.start(player);
        }

        if (player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToGather() > 0) {
            FarmingBot.start(player);
        }

        if (player.getMinigameAttributes().getFishingBotAttributes().getResourcesLeftToGather() > 0) {
            FishingBot.start(player);
        }


        //raid loot fix

        if (!player.coxCheck) {
            player.coxCount = player.getCoxKC();
            player.coxRemaining = player.getCoxKC();
            player.coxCheck = true;

        }
        if (!player.tobCheck) {
            player.tobCount = player.getTobKC();
            player.tobRemaining = player.getTobKC();
            player.tobCheck = true;
        }
        if (!player.chaosCheck) {
            player.chaosCount = player.getchaosKC();
            player.chaosRemaining = player.getchaosKC();
            player.chaosCheck = true;
        }

        if (player.coxRemaining > 0)
            player.sendMessage("<col=FF0000>You have unclaimed CoX loots. Use command ::raidloots to claim!");
        if (player.tobRemaining > 0)
            player.sendMessage("<col=FF0000>You have unclaimed ToB loots. Use command ::raidloots to claim!");
        if (player.chaosRemaining > 0)
            player.sendMessage("<col=FF0000>You have unclaimed Chaos Raids loots. Use command ::raidloots to claim!");

        player.getCofferData();

        if (!player.petStorageUpdate2) {
            player.petFix(player);
            player.petStorageUpdate2 = true;
        }

        //check Slayer and Skiller Achievements

        if (player.totalSkillerTasks >= 1000)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_1000_SKILLER_TASKS, 1000);
        if (player.totalSkillerTasks >= 100)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_100_SKILLER_TASKS, 100);
        if (player.totalSkillerTasks >= 10)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_10_SKILLER_TASKS, 10);

        if (player.totalSlayerTasks >= 1000)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_1000_SLAYER_TASKS, 1000);
        if (player.totalSlayerTasks >= 100)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_100_SLAYER_TASKS, 100);
        if (player.totalSlayerTasks >= 10)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_10_SLAYER_TASKS, 10);

        //check Prestige achievements
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

        //check C-log achievements
        if(player.completedLogs >= 50)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_50_CLOGS, 50);
        if(player.completedLogs >= 45)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_45_CLOGS, 45);
        if(player.completedLogs >= 40)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_40_CLOGS, 40);
        if(player.completedLogs >= 35)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_35_CLOGS, 35);
        if(player.completedLogs >= 30)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_30_CLOGS, 30);
        if(player.completedLogs >= 25)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_25_CLOGS, 25);
        if(player.completedLogs >= 20)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_20_CLOGS, 20);
        if(player.completedLogs >= 15)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_15_CLOGS, 15);
        if(player.completedLogs >= 10)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_10_CLOGS, 10);
        if(player.completedLogs >= 5)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_5_CLOGS, 5);

        //check Raid KC achievements

        int totalRaids = player.getCoxKC() + player.getTobKC() + player.getshrKC() + player.getchaosKC() + player.getCompletedBarrows() + player.getGwdRaidKC();

        if(totalRaids >= 5000)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_5000_RAIDS, 5000);
        if(totalRaids >= 2500)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_2500_RAIDS, 2500);
        if(totalRaids >= 1000)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_1000_RAIDS, 1000);
        if(totalRaids >= 500)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_500_RAIDS, 500);
        if(totalRaids >= 250)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_250_RAIDS, 250);
        if(totalRaids >= 100)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_100_RAIDS, 100);
        if(totalRaids >= 50)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_50_RAIDS, 50);
        if(totalRaids >= 25)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_25_RAIDS, 25);
        if(totalRaids >= 10)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_10_RAIDS, 10);
        if(totalRaids >= 5)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_5_RAIDS, 5);

        //Community Event Check
        CommunityEvents.checkEvent(player);

        if (player.getMailBox().getValidItemsSize() > 0) {
            player.sendMessage("[MailBox] You've got mail!");
        }

        player.logins++;

        PlayerLogs.log(player.getUsername(), "Login from host " + player.getHostAddress() + ", serial number: " + player.getHardwareID());

        String discordMessage = "Login: " + player.getUsername() + " " + Misc.getCurrentServerTime() + ", IP: " + player.getHostAddress() + ", HWID: " + player.getHardwareID();

        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_LOGIN_CH).get());

        String commandLog = "[" + Misc.getCurrentServerTime() + "] Login player - [" + player.getUsername() + ", " + player.getHostAddress() + ", " + player.getHardwareID() + "]";
        //PlayerLogs.log("Login_History", commandLog);
    }

    public static boolean handleLogout(Player player) {
        try {

            PlayerSession session = player.getSession();

            if (session.getChannel().isOpen()) {
                session.getChannel().close();
            }

            if (!player.isRegistered()) {
                return true;
            }

            boolean exception = GameServer.isUpdating() || World.getLogoutQueue().contains(player) && player.getLogoutTimer().elapsed(600000);
            if (player.logout() || exception) {
                //new Thread(new HighscoresHandler(player)).start();
                System.out.println("[World] Deregistering player - [username, host] : [" + player.getUsername() + ", " + player.getHostAddress() + "]");

                String commandLog = "[" + Misc.getCurrentServerTime() + "] Logout player - [" + player.getUsername() + ", " + player.getHostAddress() + ", " + player.getHardwareID() + "]";
                //PlayerLogs.log("Login_History", commandLog);

                String discordMessage = "Logout: " + player.getUsername() + " " + Misc.getCurrentServerTime();

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_LOGIN_CH).get());

                player.getSession().setState(SessionState.LOGGING_OUT);
                ConnectionHandler.remove(player.getHostAddress());
                player.setTotalPlayTime(player.getTotalPlayTime() + player.getRecordedLogin().elapsed());
                player.getPacketSender().sendInterfaceRemoval();
                if (player.getCannon() != null) {
                    DwarfMultiCannon.pickupCannon(player, player.getCannon(), true);
                }
                if (exception && player.getResetPosition() != null) {
                    player.moveTo(player.getResetPosition());
                    player.setResetPosition(null);
                }
                if (player.getRegionInstance() != null) {
                    player.getRegionInstance().destruct();
                }


                if (player.getStaffRights() == StaffRights.MODERATOR || player.getStaffRights() == StaffRights.ADMINISTRATOR || player.getStaffRights() == StaffRights.DEVELOPER || player.getStaffRights() == StaffRights.OWNER) {
                    StaffList.logout(player);
                }

                if (player.getMinigameAttributes().getMiningBotAttributes().getBot() != null) {
                    MiningBot.endBot(player.getMinigameAttributes().getMiningBotAttributes().getBot(), player);
                }
                if (player.getMinigameAttributes().getWCingBotAttributes().getBot() != null)
                    WCingBot.endBot(player.getMinigameAttributes().getWCingBotAttributes().getBot(), player);
                if (player.getMinigameAttributes().getFarmingBotAttributes().getBot() != null)
                    FarmingBot.endBot(player.getMinigameAttributes().getFarmingBotAttributes().getBot(), player);
                if (player.getMinigameAttributes().getFishingBotAttributes().getBot() != null)
                    FishingBot.endBot(player.getMinigameAttributes().getFishingBotAttributes().getBot(), player);
                Hunter.handleLogout(player);
                Locations.logout(player);
                player.getSummoning().unsummon(false, false);
                //player.getFarming().save();
                player.getTradingPostManager().unhookShop();
                BountyHunter.handleLogout(player);
                ClanChatManager.leave(player, false);
                player.getRelations().updateLists(false);
                PlayersOnlineInterface.remove(player);
                TaskManager.cancelTasks(player.getCombatBuilder());
                TaskManager.cancelTasks(player);
                HighScores.sendToHighScores(player);
                player.save();
                World.getPlayers().remove(player);
                session.setState(SessionState.LOGGED_OUT);
                World.updatePlayersOnline();

                if(player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                    player.getPacketSender().sendMessage("@red@Seasonal accounts will be removed after the season is over!");
                    player.getPacketSender().sendMessage("@red@Seasonal accounts are not able to transfer wealth!");
                    player.getPacketSender().sendMessage("@red@Seasonal accounts can't transfer off Donation rank!");
                    player.getPacketSender().sendMessage("@red@Seasonal rewards will be automatically sent to your linked account!");
                }

                if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null) {
                    RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
                    party.remove(player, false, true);
                }

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
        return true;
    }
}
