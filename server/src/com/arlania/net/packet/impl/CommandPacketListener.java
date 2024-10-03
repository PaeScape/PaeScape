package com.arlania.net.packet.impl;

import com.arlania.*;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.engine.task.impl.PlayerDeathTask;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.model.container.impl.Bank;
import com.arlania.model.container.impl.Equipment;
import com.arlania.model.container.impl.MailBox;
import com.arlania.model.container.impl.Shop.ShopManager;
import com.arlania.model.definitions.*;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.net.security.ConnectionHandler;
import com.arlania.util.*;
import com.arlania.world.World;
import com.arlania.world.content.*;
import com.arlania.world.content.LoyaltyProgramme.LoyaltyTitles;
import com.arlania.world.content.PlayerPunishment.Jail;
import com.arlania.world.content.achievements.AchievementInterface;
import com.arlania.world.content.clan.ClanChatManager;
import com.arlania.world.content.clog.CollectionLog.CustomCollection;
import com.arlania.world.content.combat.CombatFactory;
import com.arlania.world.content.combat.strategy.CombatStrategies;
import com.arlania.world.content.combat.weapon.CombatSpecial;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.droppreview.SLASHBASH;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.interfaces.HUDinterface;
import com.arlania.world.content.marketplace.Marketplace;
import com.arlania.world.content.minigames.MinigameAttributes;
import com.arlania.world.content.minigames.impl.barrows.Rewards;
import com.arlania.world.content.minigames.impl.chambersofxeric.CoxRaid;
import com.arlania.world.content.minigames.impl.chaosraids.ChaosRewards;
import com.arlania.world.content.minigames.impl.gauntlet.GauntletRaid;
import com.arlania.world.content.minigames.impl.gwdraids.GwdrRewards;
import com.arlania.world.content.minigames.impl.kingdom.NobilitySystem;
import com.arlania.world.content.minigames.impl.raidsparty.RaidRegroup;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.content.minigames.impl.skilling.BlastMine;
import com.arlania.world.content.minigames.impl.skilling.Wintertodt;
import com.arlania.world.content.pos.TradingPostManager;
import com.arlania.world.content.seasonal.SeasonalHandler;
import com.arlania.world.content.skill.SkillManager;
import com.arlania.world.content.skill.impl.herblore.Decanting;
import com.arlania.world.content.skill.impl.herblore.NotedDecanting;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.content.transportation.TeleportType;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.*;
import com.arlania.world.entity.impl.player.antibotting.ActionTracker;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionCommand;
import org.javacord.api.entity.message.MessageBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;


/**
 * This packet listener manages commands a player uses by using the command
 * console prompted by using the "`" char.
 *
 * @author Gabriel Hannason
 */

public class CommandPacketListener implements PacketListener {
    public static int config;

    private static final String[] automatedClientMessages = {"cps"};

    private static void handleAutomatedClientMessages(final Player player, String[] command, String wholeCommand) {
        if (command[0].equals("cps")) {
            if (command.length == 2) {
                try {
                    int cps = Integer.parseInt(command[1]);
//                    System.out.println(player.getUsername() + " cps " + cps);
                } catch (NumberFormatException e) {
                    // also really bad. scream and shout
                    GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
                }
                // log player cps over time.
                // if cps > x for y minutes, consider a random event
            } else {
                // this is really bad. scream and shout
                // player might be packet manipulating
            }
        }
    }

    private static void playerCommands(final Player player, String[] command, String wholeCommand) {

        if (command[0].equals("cc")) {
            String emoji = command[1];

            if (EmojiHandler.checkEmoji(emoji)) {
                ClanChatManager.sendMessage(player, "<img=" + emoji + ">");
            }
        }

        if (command[0].equals("seasonal")) {

            StringBuilder name = new StringBuilder();
            for (int i = 1; i < command.length; i++) {
                name.append(command[i]).append((i == command.length - 1) ? "" : " ");
            }

            if (!player.getSubscription().isSubscriber()) {
                player.getPacketSender().sendMessage("You must have an active subscription to make a seasonal.");
                return;
            } else {
                SeasonalHandler.newSeasonal(player, name.toString());
            }


        }

        if (command[0].equals("easter")) {

            if (GameLoader.getYear() != 2024)
                return;

            /*if (GlobalEventHandler.globalEventTime > 0) {
                DialogueManager.sendStatement(player, "You can't activate this when there are events running.");
                return;
            }*/

            GlobalEventHandler.addRandomSingleEvent(player);
        }

        if (command[0].equals("xmas")) {

            if (GameLoader.getMonth() != 12)
                return;

            if (GameLoader.getYear() != 2024)
                return;

            if (GlobalEventHandler.globalEventTimeRemaining > 0) {
                DialogueManager.sendStatement(player, "You can't activate this when there are events running.");
                return;
            }

            //GlobalEventHandler.useTokenOnWell(player, 3);
        }

        if (command[0].equals("date")) {

            LocalDate date = LocalDate.of(GameLoader.getYear(), GameLoader.getMonth(), GameLoader.getDayOfTheMonth()); // The date you want to convert

            // Calculate the number of days since the epoch (January 1, 1970)
            long epochDays = date.toEpochDay();

            // Convert to int
            int epochInteger = (int) epochDays;

            //player.subscriptionStartDate = epochInteger;
            player.getPacketSender().sendMessage("Epoch Date: " + epochInteger);
        }

        if (command[0].equals("resethud")) {
            player.displayHUD = false;
            player.hudChoices = 0;
            player.hudOvl = false;
            player.hudFire = false;
            player.hudPoison = false;
            player.hudBonus = false;
            player.hudSlayer = false;
            player.hudSkiller = false;
            player.hudCballs = false;
            player.hudBPexp = false;
            player.hudBPkills = false;
            player.hudEPexp = false;
            player.hudEPkills = false;
            player.hudPETimer = false;
            player.hudColor = "cya";

            player.activeInterface = "hud";
            HUDinterface.showHUDInterface(player);
            player.getPacketSender().sendMessage("@red@Your HUD settings have been reset.");

        }

        /*if (command[0].equals("resetreward")) {

            if (player.getInventory().getFreeSlots() < 28) {
                player.getPacketSender().sendMessage("@red@Please empty your inventory before using this command!");
                return;
            }
            if (!player.achievementCheck) {
                int freeTokens = player.Prestige;

                for (int i = 0; i < freeTokens; i++) {
                    int randomUpgrade = Misc.inclusiveRandom(4033, 4035);

                    player.getInventory().add(randomUpgrade, 1);
                }

                player.getAchievementTracker().resetReward();
            } else
                player.getPacketSender().sendMessage("You have no more Achievements to reset!");
        }*/

        if (command[0].equalsIgnoreCase("linkdisc")) {
            if (command.length < 2) {
                player.sm("Please use ::linkdisc discordName ex: example#1000");
                return;
            }
            String discName = command[1];
            player.setDiscordName(discName);
            player.sm("You are trying to link your discord: " + discName + " please go to discord and activate using -link ingameName.");
            return;
        }

        if (command[0].equalsIgnoreCase("unlink")) {
            player.setDiscordName(null);
            player.setDiscordLinked(false);
            player.sm("You have unlinked your account from discord.");
            return;
        }
        if (command[0].equalsIgnoreCase("clog")) {
            player.getCollectionLog().openInterface();
            return;
        }

        if (command[0].equalsIgnoreCase("teleport")) {
            player.getTeleportInterface().open();
            return;
        }

        if (command[0].equalsIgnoreCase("pz")) {
            if (player.prestige > 0)
                TeleportHandler.teleportPlayer(player, new Position(3032, 4505, 0), TeleportType.NORMAL);
        }

        if (command[0].equalsIgnoreCase("deiron")) {

            if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                player.getPacketSender().sendMessage("This game mode can't de-iron.");
                return;
            }

            if (player.getGameMode() != GameMode.NORMAL) {
                HighScores.toggleDeath(player);
                GameMode.set(player, GameMode.NORMAL, false);
                World.sendMessage("status", "@red@" + player.getUsername() + " has converted to a normal account.");
                return;
            }
        }

        /*if (command[0].equalsIgnoreCase("asdf")) {
            for (Skill skill : Skill.values()) {
                    player.getSkillManager().addExperience(skill, 500000);
            }

            return;
        }*/

        if (command[0].equalsIgnoreCase("paescape23")) {
            if (player.rsog21 || player.walkchaos21 || player.noobsown21 || player.wr3ckedyou || player.ipkmaxjr) {
                player.getPacketSender().sendMessage("@red@You've already claimed a referral!");
                return;
            } else if (!player.rsog21) {

                if (player.getGameMode() != GameMode.SEASONAL_IRONMAN) {
                    if (!PlayerPunishment.hasRecieved1stReferral(player.getHostAddress())) {
                        PlayerPunishment.addIpToReferralList1(player.getHostAddress());
                        World.sendMessage("status", "<col=6600CC>[Referral]: " + player.getUsername() + " has claimed a referral!");
                    } else if (PlayerPunishment.hasRecieved1stReferral(player.getHostAddress()) && !PlayerPunishment.hasRecieved2ndReferral(player.getHostAddress())) {
                        PlayerPunishment.addIpToReferralList2(player.getHostAddress());
                        World.sendMessage("status", "<col=6600CC>[Referral]: " + player.getUsername() + " has claimed a referral!");
                    } else if (PlayerPunishment.hasRecieved1stReferral(player.getHostAddress()) && PlayerPunishment.hasRecieved2ndReferral(player.getHostAddress())) {
                        player.getPacketSender().sendMessage("@red@You've already claimed 2 referrals!");
                        return;
                    } else {
                        player.getPacketSender().sendMessage("@red@You've already claimed 2 referrals!");
                        return;
                    }
                }


                player.rsog21 = true;
                player.setPaePoints(player.getPaePoints() + 500);
                player.getInventory().add(989, 10);
                player.getPacketSender().sendMessage("@blu@You've been given 10 free Crystal Keys and 500 PaePoints!");
                player.getPacketSender().sendMessage("@blu@You now have @red@" + player.getPaePoints() + " @blu@ PaePoints!");
                return;
            }

            return;
        }

        if (command[0].equalsIgnoreCase("noobsown21")) {

            if (player.rsog21 || player.walkchaos21 || player.noobsown21 || player.wr3ckedyou || player.ipkmaxjr) {
                player.getPacketSender().sendMessage("@red@You've already claimed a referral!");
                return;
            } else if (!player.noobsown21) {
                if (player.getGameMode() != GameMode.SEASONAL_IRONMAN) {
                    if (!PlayerPunishment.hasRecieved1stReferral(player.getHostAddress())) {
                        PlayerPunishment.addIpToReferralList1(player.getHostAddress());
                        World.sendMessage("status", "<col=6600CC>[Referral]: " + player.getUsername() + " has claimed a referral!");
                    } else if (PlayerPunishment.hasRecieved1stReferral(player.getHostAddress()) && !PlayerPunishment.hasRecieved2ndReferral(player.getHostAddress())) {
                        PlayerPunishment.addIpToReferralList2(player.getHostAddress());
                        World.sendMessage("status", "<col=6600CC>[Referral]: " + player.getUsername() + " has claimed a referral!");
                    } else if (PlayerPunishment.hasRecieved1stReferral(player.getHostAddress()) && PlayerPunishment.hasRecieved2ndReferral(player.getHostAddress())) {
                        player.getPacketSender().sendMessage("@red@You've already claimed 2 referrals!");
                        return;
                    } else {
                        player.getPacketSender().sendMessage("@red@You've already claimed 2 referrals!");
                        return;
                    }
                }

                player.noobsown21 = true;
                player.setPaePoints(player.getPaePoints() + 500);
                player.getInventory().add(989, 10);
                player.getPacketSender().sendMessage("@blu@You've been given 10 free Crystal Keys and 500 PaePoints!");
                player.getPacketSender().sendMessage("@blu@You now have @red@" + player.getPaePoints() + " @blu@ PaePoints!");
                return;
            }

            return;
        }

        if (command[0].equalsIgnoreCase("walkchaos21")) {

            if (player.rsog21 || player.walkchaos21 || player.noobsown21 || player.wr3ckedyou || player.ipkmaxjr) {
                player.getPacketSender().sendMessage("@red@You've already claimed a referral!");
                return;
            } else if (!player.walkchaos21) {
                if (player.getGameMode() != GameMode.SEASONAL_IRONMAN) {
                    if (!PlayerPunishment.hasRecieved1stReferral(player.getHostAddress())) {
                        PlayerPunishment.addIpToReferralList1(player.getHostAddress());
                        World.sendMessage("status", "<col=6600CC>[Referral]: " + player.getUsername() + " has claimed a referral!");
                    } else if (PlayerPunishment.hasRecieved1stReferral(player.getHostAddress()) && !PlayerPunishment.hasRecieved2ndReferral(player.getHostAddress())) {
                        PlayerPunishment.addIpToReferralList2(player.getHostAddress());
                        World.sendMessage("status", "<col=6600CC>[Referral]: " + player.getUsername() + " has claimed a referral!");
                    } else if (PlayerPunishment.hasRecieved1stReferral(player.getHostAddress()) && PlayerPunishment.hasRecieved2ndReferral(player.getHostAddress())) {
                        player.getPacketSender().sendMessage("@red@You've already claimed 2 referrals!");
                        return;
                    } else {
                        player.getPacketSender().sendMessage("@red@You've already claimed 2 referrals!");
                        return;
                    }
                }


                player.walkchaos21 = true;
                player.setPaePoints(player.getPaePoints() + 500);
                player.getInventory().add(989, 10);
                player.getPacketSender().sendMessage("@blu@You've been given 10 free Crystal Keys and 500 PaePoints!");
                player.getPacketSender().sendMessage("@blu@You now have @red@" + player.getPaePoints() + " @blu@ PaePoints!");
                return;
            }

            return;
        }

        if (command[0].equalsIgnoreCase("ipkmaxjr")) {

            if (player.rsog21 || player.walkchaos21 || player.noobsown21 || player.wr3ckedyou || player.ipkmaxjr) {
                player.getPacketSender().sendMessage("@red@You've already claimed a referral!");
                return;
            } else if (!player.ipkmaxjr) {
                if (!PlayerPunishment.hasRecieved1stReferral(player.getHostAddress())) {
                    PlayerPunishment.addIpToReferralList1(player.getHostAddress());
                    World.sendMessage("status", "<col=6600CC>[Referral]: " + player.getUsername() + " has claimed a referral!");
                } else if (PlayerPunishment.hasRecieved1stReferral(player.getHostAddress()) && !PlayerPunishment.hasRecieved2ndReferral(player.getHostAddress())) {
                    PlayerPunishment.addIpToReferralList2(player.getHostAddress());
                    World.sendMessage("status", "<col=6600CC>[Referral]: " + player.getUsername() + " has claimed a referral!");
                } else if (PlayerPunishment.hasRecieved1stReferral(player.getHostAddress()) && PlayerPunishment.hasRecieved2ndReferral(player.getHostAddress())) {
                    player.getPacketSender().sendMessage("@red@You've already claimed 2 referrals!");
                    return;
                } else {
                    player.getPacketSender().sendMessage("@red@You've already claimed 2 referrals!");
                    return;
                }


                player.ipkmaxjr = true;
                player.setPaePoints(player.getPaePoints() + 500);
                player.getInventory().add(989, 10);
                player.getPacketSender().sendMessage("@blu@You've been given 10 free Crystal Keys and 500 PaePoints!");
                player.getPacketSender().sendMessage("@blu@You now have @red@" + player.getPaePoints() + " @blu@ PaePoints!");
                return;
            }

            return;
        }

        if (command[0].equalsIgnoreCase("wr3ckedyou")) {

            if (player.rsog21 || player.walkchaos21 || player.noobsown21 || player.wr3ckedyou || player.ipkmaxjr) {
                player.getPacketSender().sendMessage("@red@You've already claimed a referral!");
                return;
            } else if (!player.wr3ckedyou) {
                if (!PlayerPunishment.hasRecieved1stReferral(player.getHostAddress())) {
                    PlayerPunishment.addIpToReferralList1(player.getHostAddress());
                    World.sendMessage("status", "<col=6600CC>[Referral]: " + player.getUsername() + " has claimed a referral!");
                } else if (PlayerPunishment.hasRecieved1stReferral(player.getHostAddress()) && !PlayerPunishment.hasRecieved2ndReferral(player.getHostAddress())) {
                    PlayerPunishment.addIpToReferralList2(player.getHostAddress());
                    World.sendMessage("status", "<col=6600CC>[Referral]: " + player.getUsername() + " has claimed a referral!");
                } else if (PlayerPunishment.hasRecieved1stReferral(player.getHostAddress()) && PlayerPunishment.hasRecieved2ndReferral(player.getHostAddress())) {
                    player.getPacketSender().sendMessage("@red@You've already claimed 2 referrals!");
                    return;
                } else {
                    player.getPacketSender().sendMessage("@red@You've already claimed 2 referrals!");
                    return;
                }


                player.wr3ckedyou = true;
                player.setPaePoints(player.getPaePoints() + 500);
                player.getInventory().add(989, 10);
                player.getPacketSender().sendMessage("@blu@You've been given 10 free Crystal Keys and 500 PaePoints!");
                player.getPacketSender().sendMessage("@blu@You now have @red@" + player.getPaePoints() + " @blu@ PaePoints!");
                return;
            }

            return;
        }

        if (command[0].equalsIgnoreCase("hydra")) {
            if (player.getLocation() == Location.WILDERNESS) {
                return;
            }

            return;
        }

        if (command[0].equalsIgnoreCase("raids")) {
            TeleportHandler.teleportPlayer(player, new Position(1234, 3558, 0), TeleportType.NORMAL);
        }
        if (command[0].equalsIgnoreCase("home")) {
            TeleportHandler.teleportPlayer(player, GameSettings.DEFAULT_POSITION, TeleportType.NORMAL);
        }
        if (command[0].equalsIgnoreCase("decant")) {
            Decanting.checkRequirements(player);
            NotedDecanting.checkRequirements(player);
            return;
        }
        if (command[0].equalsIgnoreCase("achievements")) {
            AchievementInterface.open(player);
            return;
        }
        if (command[0].equalsIgnoreCase("drop") || command[0].equalsIgnoreCase("drops")) {
            player.getDropTableInterface().open(true, true);
            return;
        }
        if (command[0].equalsIgnoreCase("checkdrops")) {
            String npcName = wholeCommand.substring(11).toLowerCase();
            player.getDropTableInterface().open(true);
            player.getDropTableInterface().searchNpc(npcName);
            return;
        }
        if (command[0].equalsIgnoreCase("whatdrops")) {
            String itemName = wholeCommand.substring(10).toLowerCase();
            player.getDropTableInterface().open(true);
            player.getDropTableInterface().searchItem(itemName);
            return;
        }
		/*if (command[0].equalsIgnoreCase("pos")) {
			if (player.getLocation() == Location.DUNGEONEERING || player.getLocation() == Location.DUEL_ARENA) {
			player.getPacketSender().sendMessage("You can't open the player shops right now!");
		} else {
				player.getPlayerOwnedShopManager().options();
				return;
			}
		}*/

        if (command[0].equalsIgnoreCase("reward")) {
            player.sendMessage("Check your mailbox for vote rewards now!");
        }

        if (wholeCommand.equalsIgnoreCase("resettitle")) {
            player.setLoyaltyTitle(LoyaltyTitles.NONE);
            player.getUpdateFlag().flag(Flag.APPEARANCE);
            player.setTitle("");
        }

        if (command[0].equalsIgnoreCase("reinv")) {
            RaidRegroup.Regroup(player);

        }

        if (wholeCommand.equalsIgnoreCase("itemdb")) {

            ItemDBmaker.init();
        }

        if (wholeCommand.equalsIgnoreCase("daily")) {

            player.getPacketSender().sendMessage("@blu@::Daily has been replaced with a revamped Loyalty Program!");

			/*
			if(player.getRegionInstance() != null)
			{
				player.getPacketSender().sendMessage("@red@You can't activate ::daily from inside an instance!");
				return;
			}

			if(player.getLocation() == Location.WILDERNESS)
			{
				player.getPacketSender().sendMessage("@red@You can't activate ::daily from the Wilderness!");
				return;
			}

			int day = GameLoader.getDayOfTheMonth();

			if (day == player.getDaily())
			{ player.getPacketSender().sendMessage("You have already received your Daily kills for the day!");}
			else if (day != player.getDaily())
			{
				if (player.getStaffRights().getStaffRank() > 5)
				{ player.DailyKills =  250; }
				else if (player.getStaffRights().getStaffRank() > 4)
				{ player.DailyKills =  150; }
				else if (player.getStaffRights().getStaffRank() > 3)
				{ player.DailyKills =  125; }
				else if (player.getStaffRights().getStaffRank() > 2)
				{ player.DailyKills =  100; }
				else if (player.getStaffRights().getStaffRank() > 1)
				{ player.DailyKills =  75; }
				else if (player.getStaffRights().getStaffRank() > 0)
				{ player.DailyKills =  50; }
				else
				{ player.DailyKills =  25; }

				PlayerPanel.refreshPanel(player);
				player.setDaily(day);
				player.getPacketSender().sendMessage("Your Daily Kills have been reset!");
			}

			player.inDaily = true;
			RecipeForDisaster.enter(player);
			*/
        }

        if (wholeCommand.equalsIgnoreCase("donate") || wholeCommand.equalsIgnoreCase("store")) {
            player.getPacketSender().sendMessage("Please visit our discord for donations!");
        }

        if (command[0].equalsIgnoreCase("claim")) {
            player.sendMessage("Shop items can now be found in your mailbox!");
        }

        if (command[0].equalsIgnoreCase("yell")) {
            if (PlayerPunishment.muted(player.getUsername()) || PlayerPunishment.IPMuted(player.getHostAddress())) {
                player.getPacketSender().sendMessage("You are muted and cannot yell.");
                return;
            }

            int delay = 30;

			/*if(player.getStaffRights() == StaffRights.PLAYER)
				delay = player.getStaffRights().getYellDelay();
			else
				delay = 0;*/

            if (!player.getLastYell().elapsed((delay * 1000))) {
                player.getPacketSender().sendMessage(
                        "You must wait at least " + delay + " seconds between every yell-message you send.");
                return;
            }
            String yellMessage = wholeCommand.substring(4);

            player.getLastYell().reset();
            // if (player.getUsername().equalsIgnoreCase("levi")) {
            // World.sendMessage("" + player.getStaffRights().getYellPrefix() +
            // "<img=" + player.getStaffRights().ordinal()
            // + ">@red@ [DEVELOPER] @bla@" + player.getUsername() + ":" +
            // yellMessage);
            // return;
            // }
            if (player.getStaffRights() == StaffRights.OWNER) {
                World.sendMessage("yell", player.getStaffRights().getYellPrefix() + "<img=" + player.getStaffRights().ordinal()
                        + ">@red@ [Owner] @bla@" + player.getUsername() + ":" + yellMessage);
                return;
            } else if (player.getStaffRights() == StaffRights.DEVELOPER) {
                World.sendMessage("yell", player.getStaffRights().getYellPrefix() + "<img=" + player.getStaffRights().ordinal()
                        + ">@red@ [Developer] @bla@" + player.getUsername() + ":" + yellMessage);
                return;
            } else if (player.getStaffRights() == StaffRights.ADMINISTRATOR) {
                World.sendMessage("yell", player.getStaffRights().getYellPrefix() + "<img=" + player.getStaffRights().ordinal()
                        + ">@or2@ [Administrator] @bla@" + player.getUsername() + ":" + yellMessage);
                return;
            } else if (player.getStaffRights() == StaffRights.MODERATOR) {
                World.sendMessage("yell", player.getStaffRights().getYellPrefix() + "<img=" + player.getStaffRights().ordinal()
                        + "><col=6600CC> [Moderator]</col> @bla@" + player.getUsername() + ":" + yellMessage);
                return;
            } else if (player.getStaffRights() == StaffRights.MASTER_DONATOR) {
                World.sendMessage("yell", player.getStaffRights().getYellPrefix() + "<img=" + player.getStaffRights().ordinal()
                        + ">@or2@ [MASTER] @bla@" + player.getUsername() + ":" + yellMessage);
                return;
            } else if (player.getStaffRights() == StaffRights.UBER_DONATOR) {
                World.sendMessage("yell", player.getStaffRights().getYellPrefix() + "<img=" + player.getStaffRights().ordinal()
                        + "><col=0EBFE9><shad=1> [Uber]</shad></col> @bla@" + player.getUsername() + ":" + yellMessage);
                return;
            } else if (player.getStaffRights() == StaffRights.LEGENDARY_DONATOR) {
                World.sendMessage("yell", player.getStaffRights().getYellPrefix() + "<img=" + player.getStaffRights().ordinal()
                        + "><col=697998><shad=1> [Legendary]</shad></col> @bla@" + player.getUsername() + ":"
                        + yellMessage);
                return;
            } else if (player.getStaffRights() == StaffRights.EXTREME_DONATOR) {
                World.sendMessage("yell", player.getStaffRights().getYellPrefix() + "<img=" + player.getStaffRights().ordinal()
                        + "><col=D9D919><shad=1> [Extreme]</shad></col> @bla@" + player.getUsername() + ":"
                        + yellMessage);
                return;
            } else if (player.getStaffRights() == StaffRights.SUPER_DONATOR) {
                World.sendMessage("yell", player.getStaffRights().getYellPrefix() + "<img=" + player.getStaffRights().ordinal()
                        + "><col=787878><shad=1> [Super]</shad></col> @bla@" + player.getUsername() + ":"
                        + yellMessage);
                return;
            } else if (player.getStaffRights() == StaffRights.DONATOR) {
                World.sendMessage("yell", player.getStaffRights().getYellPrefix() + "<img=" + player.getStaffRights().ordinal()
                        + "><col=FF7F00><shad=1> [Donator]</shad></col> @bla@" + player.getUsername() + ":"
                        + yellMessage);
                return;
            }
            // TO-DO

            else {
                World.sendMessage("yell", "[Player] " + player.getUsername() + ":" + yellMessage);
                return;
            }

        }

        if (command[0].equals("players")) {
            player.getPacketSender().sendMessage("There are currently " + World.getPlayers().size() + " players online!");
        }

        if (wholeCommand.startsWith("profile")) {
            final String[] s = wholeCommand.split(" ");
            if (s.length < 2) {
                ProfileViewing.view(player, player);
                return;
            }
            final String name = wholeCommand.substring(8);
            final Player other = World.getPlayerByName(name);
            if (other == null) {
                player.sendMessage("Player not online: " + name);
                return;
            }
            ProfileViewing.view(player, other);
        }

        if (command[0].equals("skull")) {
            if (player.getSkullTimer() > 0) {
                player.setSkullTimer(0);
                player.setSkullIcon(0);
                player.getUpdateFlag().flag(Flag.APPEARANCE);
            } else {
                CombatFactory.skullPlayer(player);
            }
        }

		/*if (wholeCommand.startsWith("droplog")) {
			final String[] s = wholeCommand.split(" ");
			if (s.length < 2) {
				PlayerDropLog.sendDropLog(player, player);
				return;
			}
			final String name = wholeCommand.substring(8);
			final Player other = World.getPlayerByName(name);
			if (other == null) {
				player.sendMessage("Player not found: " + name);
				return;
			}
			PlayerDropLog.sendDropLog(player, other);
		} else if (wholeCommand.startsWith("drop") && !wholeCommand.startsWith("droplog")) {
			final String[] s = wholeCommand.split(" ");
			if (s.length < 2) {
				player.sendMessage("Enter npc name!");
			}
			final String name = wholeCommand.substring(5).toLowerCase();

			final int id = NpcDefinition.forName(name).getId();
			if (id == -1) {
				player.sendMessage("Npc not found: " + name);
				return;
			}
			MonsterDrops.sendNpcDrop(player, id, name);
		}*/

        if (command[0].equalsIgnoreCase("answer")) {
            String triviaAnswer = wholeCommand.substring(7);
            if (TriviaBot.acceptingQuestion()) {
                TriviaBot.attemptAnswer(player, triviaAnswer);
            } else {

            }
        }

        if (command[0].equalsIgnoreCase("setemail")) {
            String email = wholeCommand.substring(9);
            player.setEmailAddress(email);
            player.getPacketSender().sendMessage("You set your account's email adress to: [" + email + "] ");
            PlayerPanel.refreshPanel(player);
        }

        if (command[0].equalsIgnoreCase("changepassword")) {
            String newPassword = wholeCommand.substring(15);
            if (newPassword.length() <= 2 || newPassword.length() > 15 || !NameUtils.isValidName(newPassword)) {
                player.getPacketSender().sendMessage("That password is invalid. Please try another password.");
                return;
            }
            if (newPassword.contains("_")) {
                player.getPacketSender().sendMessage("Your password can not contain underscores.");
                return;
            }
            player.setPasswordHash(PasswordUtils.hash(newPassword));
            player.getPacketSender().sendMessage("Your new password is: [" + newPassword + "] Write it down!");

        }


		/*if (command[0].equalsIgnoreCase("maxhit")) {
			int attack = DesolaceFormulas.getMeleeAttack(player, victim) / 10;
			int range = DesolaceFormulas.getRangedAttack(player) / 10;
			int magic = DesolaceFormulas.getMagicAttack(player) / 10;
			player.getPacketSender().sendMessage("@bla@Melee attack: @or2@" + attack + "@bla@, ranged attack: @or2@"
					+ range + "@bla@, magic attack: @or2@" + magic);
		}*/
        if (command[0].equals("save")) {
            player.save();
            player.getPacketSender().sendMessage("Your progress has been saved.");
        }
		/*if (command[0].equals("help")) {
			if (player.getLastYell().elapsed(30000)) {
				World.sendStaffMessage("<col=FF0066><img=10> [TICKET SYSTEM]<col=6600FF> " + player.getUsername()
						+ " has requested help. Please help them!");
				player.getLastYell().reset();
				player.getPacketSender()
						.sendMessage("<col=663300>Your help request has been received. Please be patient.");
			} else {
				player.getPacketSender().sendMessage("")
						.sendMessage("<col=663300>You need to wait 30 seconds before using this again.").sendMessage(
								"<col=663300>If it's an emergency, please private message a staff member directly instead.");
			}
		}*/
        if (command[0].equals("empty")) {
            player.getPacketSender().sendInterfaceRemoval().sendMessage("You clear your inventory.");
            player.getSkillManager().stopSkilling();
            player.getInventory().resetItems().refreshItems();
        }

    }

    private static void donatorCommands(final Player player, String[] command, String wholeCommand) {
        if (command[0].equals("dzone") || command[0].equals("dz")) {
            TeleportHandler.teleportPlayer(player, GameSettings.DZ_POSITION, player.getSpellbook().getTeleportType());
        }
    }

    private static void uberDonatorCommands(final Player player, String[] command, String wholeCommand) {

        if (command[0].equals("bank")) {
            player.getSkillManager().stopSkilling();
            if (player.canBank())
                if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN)
                    player.getUimBank().open();
                else
                    player.getBank(player.getCurrentBankTab()).open();
        }
    }

    private static void supportCommands(final Player player, String[] command, String wholeCommand) {

    }

    private static void moderatorCommands(final Player player, String[] command, String wholeCommand) {

        if (command[0].equals("actionlog")) {
            if (command.length < 2) {
                player.sendMessage("Command usage: '::actionlog (target)'");
                return;
            }
            StringBuilder username = new StringBuilder(command[1].toLowerCase());
            if (command.length > 2) {
                for (int i = 2; i < command.length; i++) {
                    username.append(' ').append(command[i].toLowerCase());
                }
            }
            Player target = World.getPlayerByName(username.toString());
            if (target == null) {
                player.sendMessage(username + " is offline.");
                return;
            }
            ActionTracker.openActionLog(player, target);
        }

        if (command[0].equalsIgnoreCase("kick")) {
            StringBuilder name = new StringBuilder();
            for (int i = 1; i < command.length; i++)
                name.append(command[i]).append((i == command.length - 1) ? "" : " ");
            Player playerToKick = World.getPlayerByName(name.toString());
            if (playerToKick == null || playerToKick.getStaffRights().getStaffRank() > 7) {
                player.getPacketSender().sendConsoleMessage("Player " + name + " couldn't be found on Paescape.");
                return;
            } else {
                World.deregister(playerToKick);
                PlayerHandler.handleLogout(playerToKick);
                player.getPacketSender().sendConsoleMessage("Kicked " + playerToKick.getUsername() + ".");
                PlayerLogs.log(player.getUsername(),
                        player.getUsername() + " just kicked " + playerToKick.getUsername() + "!");
            }
        }

        if (command[0].equalsIgnoreCase("fixhilt")) {

            String player2 = command[1];
            Player playerName = World.getPlayerByName(player2);
            playerName.getCollectionLog().handleDrop(6260, 11704, 1);
            playerName.getCollectionLog().addKill(6260);

        }

        if (command[0].equalsIgnoreCase("fixpet")) {
            String player2 = command[1];

            if (command.length > 3) {
                player2 += " " + command[3];
            }
            if (command.length > 4) {
                player2 += " " + command[4];
            }

            Player playerName = World.getPlayerByName(player2);

            playerName.getCollectionLog().handleDrop(CustomCollection.BARROWS.getId(), 20104, 1);
        }

        if (command[0].equalsIgnoreCase("jail")) {
            Player player2 = World.getPlayerByName(command[1]);
            if (player2 != null && player.getStaffRights().getStaffRank() > player2.getStaffRights().getStaffRank()) {
                if (Jail.isJailed(player2)) {
                    player.getPacketSender().sendConsoleMessage("That player is already jailed!");
                    return;
                }
                if (Jail.jailPlayer(player2)) {
                    player2.getSkillManager().stopSkilling();
                    player2.moveTo(GameSettings.JAIL);
                    PlayerLogs.log(player.getUsername(),
                            player.getUsername() + " just jailed " + player2.getUsername() + "!");
                    player.getPacketSender().sendMessage("Jailed player: " + player2.getUsername());
                    player2.getPacketSender().sendMessage("You have been jailed by " + player.getUsername() + ".");
                } else {
                    player.getPacketSender().sendConsoleMessage("Jail is currently full.");
                }
            } else {
                player.getPacketSender().sendConsoleMessage("Could not find that player online.");
            }
        }

        if (command[0].equalsIgnoreCase("mma")) {
            TeleportHandler.teleportPlayer(player, new Position(2038, 4497), TeleportType.NORMAL);

        }

        if (command[0].equals("remindvote")) {
            World.sendMessage("", "<img=10> <col=008FB2>Remember to vote every 12 hours!");
        }

        if (command[0].equalsIgnoreCase("unjail")) {
            Player player2 = World.getPlayerByName(command[1]);
            if (player2 != null) {
                Jail.unjail(player2);
                player.moveTo(GameSettings.DEFAULT_POSITION);
                PlayerLogs.log(player.getUsername(),
                        player.getUsername() + " just unjailed " + player2.getUsername() + "!");
                player.getPacketSender().sendMessage("Unjailed player: " + player2.getUsername());
                player2.getPacketSender().sendMessage("You have been unjailed by " + player.getUsername() + ".");
            } else {
                player.getPacketSender().sendConsoleMessage("Could not find that player online.");
            }
        }
        if (command[0].equals("staffzone")) {
            if (command.length > 1 && command[1].equals("all")) {
                for (Player players : World.getPlayers()) {
                    if (players != null) {
                        if (players.getStaffRights().isStaff()) {
                            TeleportHandler.teleportPlayer(players, new Position(2846, 5147), TeleportType.NORMAL);
                        }
                    }
                }
            } else {
                TeleportHandler.teleportPlayer(player, new Position(2038, 4497), TeleportType.NORMAL);
            }
        }
        if (command[0].equalsIgnoreCase("saveall")) {
            World.savePlayers();
            player.getPacketSender().sendMessage("Saved players!");
        }

        if (command[0].equalsIgnoreCase("movehome")) {
            String player2 = command[1];
            player2 = Misc.formatText(player2.replaceAll("_", " "));
            if (command.length >= 3 && command[2] != null) {
                player2 += " " + Misc.formatText(command[2].replaceAll("_", " "));
            }
            Player playerToMove = World.getPlayerByName(player2);
            if (playerToMove != null) {
                playerToMove.moveTo(GameSettings.DEFAULT_POSITION.copy());
                playerToMove.getPacketSender().sendMessage("You've been teleported home by " + player.getUsername() + ".");
                player.getPacketSender().sendConsoleMessage("Sucessfully moved " + playerToMove.getUsername() + " to home.");
            }
        }
        if (command[0].equalsIgnoreCase("mute")) {
            String rss = command[1];
            if (command.length > 2) {
                rss += " " + command[2];
            }
            if (command.length > 3) {
                rss += " " + command[4];
            }
            Player plr = World.getPlayerByName(rss);

            String player2 = plr.getUsername();

            if (!PlayerSaving.playerExists(player2)) {
                player.getPacketSender().sendConsoleMessage("Player " + player2 + " does not exist.");
                return;
            } else {
                if (PlayerPunishment.muted(player2)) {
                    player.getPacketSender().sendConsoleMessage("Player " + player2 + " already has an active mute.");
                    return;
                }
                PlayerLogs.log(player.getUsername(), player.getUsername() + " just muted " + player2 + "!");
                PlayerPunishment.mute(player2);
                player.getPacketSender()
                        .sendConsoleMessage("Player " + player2 + " was successfully muted. Command logs written.");
                if (plr != null) {
                    plr.getPacketSender().sendMessage("You have been muted by " + player.getUsername() + ".");
                }
            }
        }




		/*if (command[0].equalsIgnoreCase("demote")) {


			String name = wholeCommand.substring(7);

			Player target = World.getPlayerByName(name);

			if (target == null) {
				player.getPacketSender().sendMessage("Player is not online");
			}

			if(target.getStaffRights().getStaffRank() < player.getStaffRights().getStaffRank())
			{
				target.setStaffRights(StaffRights.PLAYER);
				target.getPacketSender().sendRights();
				target.getPacketSender().sendMessage("Your player rights have been changed.");
				player.getPacketSender().sendMessage("Gave " + target + "player.");
			}
			else {
				player.getPacketSender().sendMessage("@red@You can't demote someone with a higher rank than you.");
			}

		}*/

        if (command[0].equals("help")) {
            String player2 = command[1];
            Player playerToHelp = World.getPlayerByName(player2);
            if (playerToHelp == null || playerToHelp.getStaffRights().getStaffRank() > 7) {
                player.getPacketSender().sendConsoleMessage("Player " + player2 + " couldn't be found on Paescape.");
                return;
            }
            for (Skill skill : Skill.values()) {
                playerToHelp.getSkillManager().setCurrentLevel(skill, playerToHelp.getSkillManager().getCurrentLevel(skill));
            }
            playerToHelp.moveTo(GameSettings.DEFAULT_POSITION.copy());
            playerToHelp.getPacketSender().sendMessage("You've been teleported home by " + player.getUsername() + ".");
            player.getPacketSender().sendConsoleMessage("Sucessfully moved " + playerToHelp.getUsername() + " to home.");

            playerToHelp.getPacketSender().sendConsoleMessage("Your skills are now reset.");
            playerToHelp.getUpdateFlag().flag(Flag.APPEARANCE);
        }


        if (command[0].equalsIgnoreCase("unmute")) {
            String rss = command[1];
            if (command.length > 2) {
                rss += " " + command[2];
            }
            if (command.length > 3) {
                rss += " " + command[4];
            }
            Player plr = World.getPlayerByName(rss);

            String player2 = plr.getUsername();

            if (!PlayerSaving.playerExists(player2)) {
                player.getPacketSender().sendConsoleMessage("Player " + player2 + " does not exist.");
                return;
            } else {
                if (!PlayerPunishment.muted(player2)) {
                    player.getPacketSender().sendConsoleMessage("Player " + player2 + " is not muted!");
                    return;
                }
                PlayerLogs.log(player.getUsername(), player.getUsername() + " just unmuted " + player2 + "!");
                PlayerPunishment.unmute(player2);
                player.getPacketSender()
                        .sendConsoleMessage("Player " + player2 + " was successfully unmuted. Command logs written.");
                if (plr != null) {
                    plr.getPacketSender().sendMessage("You have been unmuted by " + player.getUsername() + ".");
                }
            }
        }

		/*if (command[0].equals("sql")) {
			MySQLController.toggle();
			if (player.getStaffRights() == PlayerRights.DEVELOPER) {
				player.getPacketSender().sendMessage("Sql toggled to status: " + GameSettings.MYSQL_ENABLED);
			} else {
				player.getPacketSender().sendMessage("Sql toggled to status: " + GameSettings.MYSQL_ENABLED + ".");
			}
		}*/
        if (command[0].equalsIgnoreCase("toggleinvis")) {
            player.setNpcTransformationId(player.getNpcTransformationId() > 0 ? -1 : 8254);
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }

        if (command[0].equalsIgnoreCase("ban")) {

            String rss = command[1];
            if (command.length > 2) {
                rss += " " + command[2];
            }
            if (command.length > 3) {
                rss += " " + command[4];
            }
            Player player2 = World.getPlayerByName(rss);

            String playerToBan = player2.getUsername();

            if (!PlayerSaving.playerExists(playerToBan) || player2.getStaffRights().getStaffRank() > 7) {
                player.getPacketSender().sendConsoleMessage("Player " + playerToBan + " does not exist.");
                return;
            } else {
                if (PlayerPunishment.banned(playerToBan)) {
                    player.getPacketSender()
                            .sendConsoleMessage("Player " + playerToBan + " already has an active ban.");
                    return;
                }
                PlayerLogs.log(player.getUsername(), player.getUsername() + " just banned " + playerToBan + "!");
                PlayerPunishment.ban(playerToBan);
                player.getPacketSender().sendConsoleMessage(
                        "Player " + playerToBan + " was successfully banned. Command logs written.");
                Player toBan = World.getPlayerByName(playerToBan);
                if (toBan != null) {
                    World.deregister(toBan);
                }
            }
        }

        if (command[0].equalsIgnoreCase("teletome") && player.getLocation() != Locations.Location.WILDERNESS) {
            String playerToTele = wholeCommand.substring(9);
            Player player2 = World.getPlayerByName(playerToTele);
            if (player2 == null || player2.getStaffRights().getStaffRank() > 7) {
                player.getPacketSender().sendConsoleMessage("Cannot find that player online..");
            } else {
                boolean canTele = TeleportHandler.checkReqs(player, player2.getPosition().copy())
                        && player.getRegionInstance() == null && player2.getRegionInstance() == null;
                if (canTele || player.getStaffRights().getStaffRank() > 8) {
                    TeleportHandler.teleportPlayer(player2, player.getPosition().copy(), TeleportType.NORMAL);
                    player.getPacketSender()
                            .sendConsoleMessage("Teleporting player to you: " + player2.getUsername());
                    player2.getPacketSender().sendMessage("You're being teleported to " + player.getUsername() + "...");
                } else {
                    player.getPacketSender().sendConsoleMessage(
                            "You can not teleport that player at the moment. Maybe you or they are in a minigame?");
                }
            }
        }


    }

    private static void administratorCommands(final Player player, String[] command, String wholeCommand) {

        //end event

        if (command[0].equalsIgnoreCase("endevent")) {
            GlobalEventHandler.endGlobalEvent();
        }

        if (command[0].equalsIgnoreCase("endcd")) {
            GameSettings.wellOfEventsCooldown = 30000;
        }

		/*if (command[0].equalsIgnoreCase("givess")) {
			String name = wholeCommand.substring(7);

			Player target = World.getPlayerByName(name);
			if (target == null) {
				player.getPacketSender().sendMessage("Player is not online");
			} else {
				target.setStaffRights(StaffRights.SUPPORT);
				target.getPacketSender().sendRights();
				target.getPacketSender().sendMessage("Your player rights have been changed.");
				player.getPacketSender().sendMessage("Gave " + target + "support.");
			}
		}*/
        if (command[0].equalsIgnoreCase("givemod")) {
            String name = wholeCommand.substring(8);

            Player target = World.getPlayerByName(name);
            if (target == null) {
                player.getPacketSender().sendMessage("Player is not online");
            } else {
                target.setStaffRights(StaffRights.MODERATOR);
                target.getPacketSender().sendRights();
                target.getPacketSender().sendMessage("Your player rights have been changed.");
                player.getPacketSender().sendMessage("Gave " + target + "mod.");
            }
        }

        if (command[0].equalsIgnoreCase("checkloyalty")) {

            String rss = command[1];
            if (command.length > 2) {
                rss += " " + command[2];
            }
            if (command.length > 3) {
                rss += " " + command[3];
            }
            Player player2 = World.getPlayerByName(rss);

            if (player2 != null && player2.getHardwareID() != null) {

                String discordLog = "[" + Misc.getCurrentServerTime() + "] Username: " + player2.getUsername() + " has " + player2.getPointsHandler().getLoyaltyPoints() + " Loyalty Points.";

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_LOYALTYLOGS_CH).get());

            } else
                player.getPacketSender().sendConsoleMessage("Player does not exist.");
        }


        if (command[0].equalsIgnoreCase("cpuban")) {

            String rss = command[1];
            if (command.length > 2) {
                rss += " " + command[2];
            }
            if (command.length > 3) {
                rss += " " + command[3];
            }
            Player player2 = World.getPlayerByName(rss);

            if (player2 != null && player2.getHardwareID() != null) {
                //player2.getAttributes().setForceLogout(true);
                World.deregister(player2);
                ConnectionHandler.banComputer(player2.getUsername(), player2.getHardwareID());
                player.getPacketSender().sendConsoleMessage(player2.getUsername() + " has been CPU-banned.");
            } else
                player.getPacketSender().sendConsoleMessage("Could not CPU-ban that player.");
        }
        if (command[0].equals("tele")) {
            int x = Integer.valueOf(command[1]), y = Integer.valueOf(command[2]);
            int z = player.getPosition().getZ();
            if (command.length > 3) {
                z = Integer.valueOf(command[3]);
            }
            Position position = new Position(x, y, z);
            player.moveTo(position);
            player.getPacketSender().sendConsoleMessage("Teleporting to " + position);
        }
        if (command[0].equalsIgnoreCase("ipmute")) {

            String rss = command[1];
            if (command.length > 2) {
                rss += " " + command[2];
            }
            if (command.length > 3) {
                rss += " " + command[3];
            }
            Player player2 = World.getPlayerByName(rss);
            if (player2 == null) {
                player.getPacketSender().sendConsoleMessage("Could not find that player online.");
                return;
            } else {
                if (PlayerPunishment.IPMuted(player2.getHostAddress())) {
                    player.getPacketSender().sendConsoleMessage(
                            "Player " + player2.getUsername() + "'s IP is already IPMuted. Command logs written.");
                    return;
                }
                final String mutedIP = player2.getHostAddress();
                PlayerPunishment.addMutedIP(mutedIP);
                player.getPacketSender().sendConsoleMessage(
                        "Player " + player2.getUsername() + " was successfully IPMuted. Command logs written.");
                player2.getPacketSender().sendMessage("You have been IPMuted by " + player.getUsername() + ".");
                PlayerLogs.log(player.getUsername(),
                        player.getUsername() + " just IPMuted " + player2.getUsername() + "!");
            }
        }

        if (command[0].equalsIgnoreCase("unban")) {
            String rss = command[1];
            if (command.length > 2) {
                rss += " " + command[2];
            }
            if (command.length > 3) {
                rss += " " + command[3];
            }
            Player player2 = World.getPlayerByName(rss);

            String playerToBan = player2.getUsername();

            String playerToUnban = rss;

            if (!PlayerSaving.playerExists(rss)) {
                player.getPacketSender().sendConsoleMessage("Player " + playerToUnban + " does not exist.");
                return;
            } else {
                if (!PlayerPunishment.banned(playerToUnban)) {
                    player.getPacketSender().sendConsoleMessage("Player " + playerToUnban + " is not banned!");
                    return;
                }
                PlayerLogs.log(player.getUsername(), player.getUsername() + " just unbanned " + playerToUnban + "!");
                PlayerPunishment.unban(playerToUnban);
                player.getPacketSender().sendConsoleMessage(
                        "Player " + playerToUnban + " was successfully unbanned. Command logs written.");
            }
        }
        if (command[0].equalsIgnoreCase("teleto")) {
            String playerToTele = wholeCommand.substring(7);
            Player player2 = World.getPlayerByName(playerToTele);

            if (player2 == null) {
                player.getPacketSender().sendConsoleMessage("Cannot find that player online..");
                return;
            } else {
				/*boolean canTele = TeleportHandler.checkReqs(player, player2.getPosition().copy())
						&& player.getRegionInstance() == null && player2.getRegionInstance() == null;*/

                TeleportHandler.teleportPlayer(player, player2.getPosition().copy(), TeleportType.NORMAL);
                player.getPacketSender().sendConsoleMessage("Teleporting to player: " + player2.getUsername());

            }
        }
        if (command[0].equalsIgnoreCase("moveto")) {
            String playerToTele = wholeCommand.substring(7);
            Player player2 = World.getPlayerByName(playerToTele);

            if (player2 == null) {
                player.getPacketSender().sendConsoleMessage("Cannot find that player online..");
                return;
            } else {
                player.moveTo(player2.getPosition());
                player.getPacketSender().sendConsoleMessage("Moving to player: " + player2.getUsername());
            }
        }

        if (command[0].equalsIgnoreCase("maze")) {
            String rss = command[1];
            if (command.length > 2) {
                rss += " " + command[2];
            }
            if (command.length > 3) {
                rss += " " + command[3];
            }
            Player player2 = World.getPlayerByName(rss);

            if (player2 == null) {
                player.getPacketSender().sendConsoleMessage("Cannot find that player online..");
                return;
            } else {

                String discordLog = "[" + Misc.getCurrentServerTime() + "] Username: " + player.getUsername() + " sent " + player2.getUsername() + " to the Maze Random.";

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_RANDOM_EVENT_LOG_CH).get());


                RandomEvent.randomStart(player2);
            }
        }


        if (command[0].equalsIgnoreCase("cpuban")) {
            String rss = command[1];
            if (command.length > 2) {
                rss += " " + command[2];
            }
            if (command.length > 3) {
                rss += " " + command[3];
            }
            Player player2 = World.getPlayerByName(rss);

            if (player2 != null && player2.getHardwareID() != null) {
                //player2.getAttributes().setForceLogout(true);
                World.deregister(player2);
                ConnectionHandler.banComputer(player2.getUsername(), player2.getHardwareID());
                player.getPacketSender().sendConsoleMessage(player2.getUsername() + " has been CPU-banned.");
            } else
                player.getPacketSender().sendConsoleMessage("Could not CPU-ban that player.");
        }


        if (command[0].equalsIgnoreCase("ipban")) {
            String rss = command[1];
            if (command.length > 2) {
                rss += " " + command[2];
            }
            if (command.length > 3) {
                rss += " " + command[3];
            }
            Player player2 = World.getPlayerByName(rss);

            if (player2 == null) {
                player.getPacketSender().sendConsoleMessage("Could not find that player online.");
                return;
            } else {
                if (PlayerPunishment.IPBanned(player2.getHostAddress())) {
                    player.getPacketSender().sendConsoleMessage(
                            "Player " + player2.getUsername() + "'s IP is already banned. Command logs written.");
                    return;
                }
                final String bannedIP = player2.getHostAddress();
                PlayerPunishment.addBannedIP(bannedIP);
                player.getPacketSender().sendConsoleMessage(
                        "Player " + player2.getUsername() + "'s IP was successfully banned. Command logs written.");
                for (Player playersToBan : World.getPlayers()) {
                    if (playersToBan == null) {
                        continue;
                    }
                    if (playersToBan.getHostAddress() == bannedIP) {
                        PlayerLogs.log(player.getUsername(),
                                player.getUsername() + " just IPBanned " + playersToBan.getUsername() + "!");
                        World.deregister(playersToBan);
                        if (player2.getUsername() != playersToBan.getUsername()) {
                            player.getPacketSender().sendConsoleMessage("Player " + playersToBan.getUsername()
                                    + " was successfully IPBanned. Command logs written.");
                        }
                    }
                }
            }
        }
        if (command[0].equalsIgnoreCase("unipmute")) {
            player.getPacketSender().sendConsoleMessage("Unipmutes can only be handled manually.");
        }


		/*if(command[0].equalsIgnoreCase("ffa-pure")) {
			FreeForAll.startEvent("pure");
		}
		if(command[0].equalsIgnoreCase("ffa-brid")) {
			FreeForAll.startEvent("brid");
		}
		if(command[0].equalsIgnoreCase("ffa-dharok")) {
			FreeForAll.startEvent("dharok");
		}*/


        if (command[0].equals("reset")) {
            for (Skill skill : Skill.values()) {
                int level = skill.equals(Skill.CONSTITUTION) ? 100 : skill.equals(Skill.PRAYER) ? 10 : 1;
                player.getSkillManager().setCurrentLevel(skill, level).setMaxLevel(skill, level).setExperience(skill,
                        SkillManager.getExperienceForLevel(skill == Skill.CONSTITUTION ? 10 : 1));
            }
            player.getPacketSender().sendConsoleMessage("Your skill levels have now been reset.");
            player.getUpdateFlag().flag(Flag.APPEARANCE);
            player.prestige = 0;
            player.perkUpgrades = 0;
        }

		/*if (command[0].equals("emptyitem")) {
			if (player.getInterfaceId() > 0
					|| player.getLocation() != null && player.getLocation() == Location.WILDERNESS) {
				player.getPacketSender().sendMessage("You cannot do this at the moment.");
				return;
			}
			int item = Integer.parseInt(command[1]);
			int itemAmount = player.getInventory().getAmount(item);
			Item itemToDelete = new Item(item, itemAmount);
			player.getInventory().delete(itemToDelete).refreshItems();
		}*/
		/*if (command[0].equals("gold")) {
			Player p = World.getPlayerByName(wholeCommand.substring(5));
			if (p != null) {
				long gold = 0;
				for (Item item : p.getInventory().getItems()) {
					if (item != null && item.getId() > 0 && item.tradeable()) {
						gold += item.getDefinition().getValue();
					}
				}
				for (Item item : p.getEquipment().getItems()) {
					if (item != null && item.getId() > 0 && item.tradeable()) {
						gold += item.getDefinition().getValue();
					}
				}
				for (int i = 0; i < 9; i++) {
					for (Item item : p.getBank(i).getItems()) {
						if (item != null && item.getId() > 0 && item.tradeable()) {
							gold += item.getDefinition().getValue();
						}
					}
				}
				gold += p.getMoneyInPouch();
				player.getPacketSender().sendMessage(
						p.getUsername() + " has " + Misc.insertCommasToNumber(String.valueOf(gold)) + " coins.");
			} else {
				player.getPacketSender().sendMessage("Can not find player online.");
			}
		}*/

        if (command[0].equals("cashineco")) {
            int gold = 0, plrLoops = 0;
            for (Player p : World.getPlayers()) {
                if (p != null) {
                    for (Item item : p.getInventory().getItems()) {
                        if (item != null && item.getId() > 0 && item.tradeable()) {
                            gold += item.getDefinition().getValue();
                        }
                    }
                    for (Item item : p.getEquipment().getItems()) {
                        if (item != null && item.getId() > 0 && item.tradeable()) {
                            gold += item.getDefinition().getValue();
                        }
                    }
                    for (int i = 0; i < 9; i++) {
                        for (Item item : player.getBank(i).getItems()) {
                            if (item != null && item.getId() > 0 && item.tradeable()) {
                                gold += item.getDefinition().getValue();
                            }
                        }
                    }
                    gold += p.getMoneyInPouch();
                    plrLoops++;
                }
            }
            player.getPacketSender().sendMessage(
                    "Total gold in economy right now: " + gold + ", went through " + plrLoops + " players items.");
        }

		/*if (command[0].equals("find")) {
			String name = wholeCommand.substring(5).toLowerCase().replaceAll("_", " ");
			player.getPacketSender().sendConsoleMessage("Finding item id for item - " + name);
			boolean found = false;
			for (int i = 0; i < ItemDefinition.getMaxAmountOfItems(); i++) {
				if (ItemDefinition.forId(i).getName().toLowerCase().contains(name)) {
					player.getPacketSender().sendConsoleMessage("Found item with name ["
							+ ItemDefinition.forId(i).getName().toLowerCase() + "] - id: " + i);
					found = true;
				}
			}
			if (!found) {
				player.getPacketSender().sendConsoleMessage("No item with name [" + name + "] has been found!");
			}
		} else if (command[0].equals("id")) {
			String name = wholeCommand.substring(3).toLowerCase().replaceAll("_", " ");
			player.getPacketSender().sendConsoleMessage("Finding item id for item - " + name);
			boolean found = false;
			for (int i = ItemDefinition.getMaxAmountOfItems() - 1; i > 0; i--) {
				if (ItemDefinition.forId(i).getName().toLowerCase().contains(name)) {
					player.getPacketSender().sendConsoleMessage("Found item with name ["
							+ ItemDefinition.forId(i).getName().toLowerCase() + "] - id: " + i);
					found = true;
				}
			}
			if (!found) {
				player.getPacketSender().sendConsoleMessage("No item with name [" + name + "] has been found!");
			}}*/
    }

    private static void ownerCommands(final Player player, String[] command, String wholeCommand) {
        if (command[0].equalsIgnoreCase("testdisc")) {
            new MessageBuilder().append("[Player News] " + player.getUsername() + " has just achieved level 99 in discordBot!").send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
            return;
        }
        if (command[0].equalsIgnoreCase("movetome")) {
            String playerToTele = wholeCommand.substring(9);
            Player player2 = World.getPlayerByName(playerToTele);
            if (player2 == null) {
                player.getPacketSender().sendConsoleMessage("Cannot find that player..");
                return;
            } else {
                boolean canTele = TeleportHandler.checkReqs(player, player2.getPosition().copy())
                        && player.getRegionInstance() == null && player2.getRegionInstance() == null;
                if (canTele) {
                    player.getPacketSender().sendConsoleMessage("Moving player: " + player2.getUsername());
                    player2.getPacketSender().sendMessage("You've been moved to " + player.getUsername());
                    player2.moveTo(player.getPosition().copy());
                } else {
                    player.getPacketSender()
                            .sendConsoleMessage("Failed to move player to your coords. Are you or them in a minigame?");
                }
            }
        }
        if (command[0].equalsIgnoreCase("forcemovetome")) {
            String playerToTele = wholeCommand.substring(9);
            Player player2 = World.getPlayerByName(playerToTele);
            if (player2 == null) {
                player.getPacketSender().sendConsoleMessage("Cannot find that player..");
                return;
            } else {
                player.getPacketSender().sendConsoleMessage("Moving player: " + player2.getUsername());
                player2.getPacketSender().sendMessage("You've been moved to " + player.getUsername());
                player2.moveTo(player.getPosition().copy());
            }
        }
        if (command[0].equalsIgnoreCase("kill")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(5));
            TaskManager.submit(new PlayerDeathTask(player2));
            PlayerLogs.log(player.getUsername(),
                    player.getUsername() + " just ::killed " + player2.getUsername() + "!");
            player.getPacketSender().sendMessage("Killed player: " + player2.getUsername());
            player2.getPacketSender().sendMessage("You have been Killed by " + player.getUsername() + ".");
        }
        if (command[0].equals("master")) {
            for (Skill skill : Skill.values()) {
                int level = SkillManager.getMaxAchievingLevel(skill, player);
                player.getSkillManager().setCurrentLevel(skill, level).setMaxLevel(skill, level).setExperience(skill, SkillManager.getExperienceForLevel(level));
            }
            player.getPacketSender().sendConsoleMessage("You are now a master of all skills.");
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }
        if (command[0].equals("givemaster")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(5));

            for (Skill skill : Skill.values()) {
                int level = SkillManager.getMaxAchievingLevel(skill, player2);
                player2.getSkillManager().setCurrentLevel(skill, level).setMaxLevel(skill, level).setExperience(skill, SkillManager.getExperienceForLevel(level));
            }
            player2.getPacketSender().sendConsoleMessage("You are now a master of all skills.");
            player2.getUpdateFlag().flag(Flag.APPEARANCE);
        }
		/*if (command[0].equalsIgnoreCase("givedon")) {

			String name = wholeCommand.substring(8);

			Player target = World.getPlayerByName(name);
			if (target == null) {
				player.getPacketSender().sendMessage("Player is not online");
			} else {
				target.setStaffRights(PlayerRights.NOVICE);
				target.getPacketSender().sendRights();
				//target.getPointsHandler().setamountdonated(target.getPointsHandler().getamountdonated(), 10);
				target.getPacketSender().sendMessage("Your player rights have been changed.");
				player.getPacketSender().sendMessage("Gave " + target + "NOVICE Rank.");
			}

		}*/

        if (command[0].equalsIgnoreCase("emptypouch")) {
            String name = wholeCommand.substring(11);
            Player target = World.getPlayerByName(name);
            if (target == null) {
                player.getPacketSender().sendMessage("Player is offline");
            } else {
                target.setMoneyInPouch(0);
            }

        }
        if (command[0].equals("setlev")) {
            int skillId = Integer.parseInt(command[1]);
            int level = Integer.parseInt(command[2]);
            String name = command[3];
            Player target = World.getPlayerByName(name);
            if (level > 15000) {
                player.getPacketSender().sendConsoleMessage("You can only have a maxmium level of 15000.");
                return;
            }
            Skill skill = Skill.forId(skillId);
            target.getSkillManager().setCurrentLevel(skill, level).setMaxLevel(skill, level).setExperience(skill, SkillManager.getExperienceForLevel(level));
            player.getPacketSender().sendConsoleMessage("You have set his " + skill.getName() + " level to " + level);
        }
		/*if (command[0].equalsIgnoreCase("givedon1")) {
			String name = wholeCommand.substring(9);

			Player target = World.getPlayerByName(name);
			if (target == null) {
				player.getPacketSender().sendMessage("Player is not online");
			} else {
				target.setStaffRights(PlayerRights.PROFICIENT);
				target.getPacketSender().sendRights();
				target.incrementAmountDonated(50);
				target.getPacketSender().sendMessage("Your player rights have been changed.");
				player.getPacketSender().sendMessage("Gave " + target + "NOVICE Rank.");
			}
		}*/
		/*if (command[0].equalsIgnoreCase("givedon2")) {
			String name = wholeCommand.substring(9);

			Player target = World.getPlayerByName(name);
			if (target == null) {
				player.getPacketSender().sendMessage("Player is not online");
			} else {
				target.setStaffRights(PlayerRights.ADVANCED);
				target.getPacketSender().sendRights();
				target.incrementAmountDonated(100);
				target.getPacketSender().sendMessage("Your player rights have been changed.");
				player.getPacketSender().sendMessage("Gave " + target + "NOVICE Rank.");
			}
		}*/

        if (command[0].contains("pure")) {
            int[][] data = new int[][]{{Equipment.HEAD_SLOT, 1153}, {Equipment.CAPE_SLOT, 10499},
                    {Equipment.AMULET_SLOT, 1725}, {Equipment.WEAPON_SLOT, 4587}, {Equipment.BODY_SLOT, 1129},
                    {Equipment.SHIELD_SLOT, 1540}, {Equipment.LEG_SLOT, 2497}, {Equipment.HANDS_SLOT, 7459},
                    {Equipment.FEET_SLOT, 3105}, {Equipment.RING_SLOT, 2550}, {Equipment.AMMUNITION_SLOT, 9244}};
            for (int i = 0; i < data.length; i++) {
                int slot = data[i][0], id = data[i][1];
                player.getEquipment().setItem(slot, new Item(id, id == 9244 ? 500 : 1));
            }
            BonusManager.update(player, 0, 0);
            WeaponInterfaces.assign(player, player.getEquipment().get(Equipment.WEAPON_SLOT));
            WeaponAnimations.assign(player, player.getEquipment().get(Equipment.WEAPON_SLOT));
            player.getEquipment().refreshItems();
            player.getUpdateFlag().flag(Flag.APPEARANCE);
            player.getInventory().resetItems();
            player.getInventory().add(1216, 1000).add(9186, 1000).add(862, 1000).add(892, 10000).add(4154, 5000)
                    .add(2437, 1000).add(2441, 1000).add(2445, 1000).add(386, 1000).add(2435, 1000);
            player.getSkillManager().newSkillManager();
            player.getSkillManager().setMaxLevel(Skill.ATTACK, 60).setMaxLevel(Skill.STRENGTH, 85)
                    .setMaxLevel(Skill.RANGED, 85).setMaxLevel(Skill.PRAYER, 520).setMaxLevel(Skill.MAGIC, 70)
                    .setMaxLevel(Skill.CONSTITUTION, 850);
            for (Skill skill : Skill.values()) {
                player.getSkillManager().setCurrentLevel(skill, player.getSkillManager().getMaxLevel(skill))
                        .setExperience(skill,
                                SkillManager.getExperienceForLevel(player.getSkillManager().getMaxLevel(skill)));
            }
        }
		/*if (command[0].equalsIgnoreCase("givedon3")) {
			String name = wholeCommand.substring(9);

			Player target = World.getPlayerByName(name);
			if (target == null) {
				player.getPacketSender().sendMessage("Player is not online");
			} else {
				target.setStaffRights(PlayerRights.EXPERT);
				target.getPacketSender().sendRights();
				target.incrementAmountDonated(250);
				target.getPacketSender().sendMessage("Your player rights have been changed.");
				player.getPacketSender().sendMessage("Gave " + target + "NOVICE Rank.");
			}
		}*/
		/*if (command[0].equalsIgnoreCase("givedon4")) {
			String name = wholeCommand.substring(9);

			Player target = World.getPlayerByName(name);
			if (target == null) {
				player.getPacketSender().sendMessage("Player is not online");
			} else {
				target.setStaffRights(PlayerRights.MASTER);
				target.getPacketSender().sendRights();
				target.incrementAmountDonated(500);
				target.getPacketSender().sendMessage("Your player rights have been changed.");
				player.getPacketSender().sendMessage("Gave " + target + "NOVICE Rank.");
			}
		}*/

//        if (command[0].equalsIgnoreCase("tsql")) {
//            MySQLController.toggle();
//            player.getPacketSender().sendMessage("Sql toggled to status: " + GameSettings.MYSQL_ENABLED);
//
//        }
        if (command[0].equalsIgnoreCase("giveadmin")) {
            String name = wholeCommand.substring(10);

            Player target = World.getPlayerByName(name);
            if (target == null) {
                player.getPacketSender().sendMessage("Player is not online");
            } else {
                target.setStaffRights(StaffRights.ADMINISTRATOR);
                target.getPacketSender().sendRights();
                target.getPacketSender().sendMessage("Your player rights have been changed.");
                player.getPacketSender().sendMessage("Gave " + target + "admin.");
            }
        }
        if (command[0].equals("doublexp")) {
            GameSettings.BONUS_EXP = !GameSettings.BONUS_EXP;
            player.getPacketSender()
                    .sendMessage("@blu@Double XP is now " + (GameSettings.BONUS_EXP ? "@gre@enabled" : "@red@disabled") + ".");
        }
        if (command[0].equals("position")) {
            player.getPacketSender().sendConsoleMessage(player.getPosition().toString());
        }
        if (wholeCommand.equals("sfs")) {
            Lottery.restartLottery();
        }
        if (command[0].equals("giveitem")) {
            boolean err = command.length < 3;

            int item = 0;
            int amount = 0;
            try {
                item = Integer.parseInt(command[1]);
                amount = Integer.parseInt(command[2]);
            } catch (Exception e) {
                GameServer.getLogger().log(Level.SEVERE, "error handling ::giveitem command", e);
                err = true;
            }
            if (err || item <= 0 || amount <= 0) {
                player.sendMessage("Incorrect syntax for ::giveitem");
                player.sendMessage("::giveitem <id> <amount> <playerName>");
                return;
            }
            StringBuilder name = new StringBuilder();
            for (int i = 3; i < command.length; i++)
                name.append(command[i]).append((i == command.length - 1) ? "" : " ");
            name = new StringBuilder(Misc.formatText(name.toString()));

            if (MailBox.addMail(name.toString(), new Item(item, amount))) {
                player.sendMessage("You have sent " + ItemDefinition.forId(item).getName() + " to " + name + ".");
            } else {
                player.sendMessage("Unable to send " + ItemDefinition.forId(item).getName() + " to " + name + ".");
            }
        }
        if (command[0].equals("update")) {
            int time = Integer.parseInt(command[1]);
            if (time > 0) {
                GameServer.setUpdating(true);
                for (Player players : World.getPlayers()) {
                    if (players == null) {
                        continue;
                    }
                    players.getPacketSender().sendSystemUpdate(time);
                }
                TaskManager.submit(new Task(time) {
                    @Override
                    protected void execute() {
                        for (Player player : World.getPlayers()) {
                            if (player != null) {
                                PlayerHandler.handleLogout(player);
                            }
                        }
                        World.saveWorldState();
                        GameServer.getLogger().info("Update task finished!");
                        System.exit(0);
                        stop();
                    }
                });
            }
        }

        if (command[0].equals("normalperks")) {
            player.Berserker = 1;
            player.Bullseye = 1;
            player.Prophetic = 1;
            player.Unnatural = 1;
            player.Lucky = 1;
            player.Accelerate = 1;
            player.Survivalist = 1;
            player.Vampirism = 1;
            player.Prodigy = 1;
            player.Artisan = 1;

            InformationPanel.refreshPanel(player);
        }

        if (command[0].equals("allperks")) {
            player.Berserker = 4;
            player.Bullseye = 4;
            player.Prophetic = 4;
            player.Unnatural = 4;
            player.Lucky = 4;
            player.Accelerate = 4;
            player.Survivalist = 4;
            player.Vampirism = 4;
            player.Prodigy = 4;
            player.Artisan = 4;

            InformationPanel.refreshPanel(player);
        }
        if (command[0].equals("noperks")) {
            player.Berserker = 0;
            player.Bullseye = 0;
            player.Prophetic = 0;
            player.Unnatural = 0;
            player.Lucky = 0;
            player.Accelerate = 0;
            player.Survivalist = 0;
            player.Vampirism = 0;
            player.Prodigy = 0;
            player.Artisan = 0;


            InformationPanel.refreshPanel(player);
        }


        if (command[0].contains("host")) {
            String plr = wholeCommand.substring(command[0].length() + 1);
            Player playr2 = World.getPlayerByName(plr);
            if (playr2 != null) {
                player.getPacketSender().sendConsoleMessage(playr2.getUsername() + " host IP: "
                        + playr2.getHostAddress() + ", hardware id: " + playr2.getHardwareID());
            } else {
                player.getPacketSender().sendConsoleMessage("Could not find player: " + plr);
            }
        }
    }

    private static void developerCommands(Player player, String[] command, String wholeCommand) {

        if (command[0].equals("shr4")) {
            player.strongholdLootFloor = 4;
        }

        if (command[0].equals("gauntlet")) {
            GauntletRaid.start(player);
        }

        if (command[0].equals("customs")) {
            for (int i = 0; i < GameSettings.CUSTOMUNIQUES.length; i++) {
                player.getInventory().add(GameSettings.CUSTOMUNIQUES[i], 1);
            }

        }

        if (command[0].equals("abilityreset")) {
            for (int i = 0; i < 10; i++) {
                player.achievementAbilities[i] = 0;
            }
            player.setAchievementPoints(500);
        }

        if (command[0].equals("tax")) {
            player.getPacketSender().sendMessage("Current Taxes: " + World.taxes);
        }

        if (command[0].equals("soul")) {
            for (int i = 0; i < GameSettings.SOUL_EQUIP.length; i++) {
                player.getInventory().add(GameSettings.SOUL_EQUIP[i], 1);
            }
        }
        if (command[0].equals("tainted")) {
            for (int i = 0; i < GameSettings.TAINTED_EQUIP.length; i++) {
                player.getInventory().add(GameSettings.TAINTED_EQUIP[i], 1);
            }
        }
        if (command[0].equals("blood")) {
            for (int i = 0; i < GameSettings.BLOOD_EQUIP.length; i++) {
                player.getInventory().add(GameSettings.BLOOD_EQUIP[i], 1);
            }
        }
        if (command[0].equals("enriched")) {
            for (int i = 0; i < GameSettings.PINK_EQUIP.length; i++) {
                player.getInventory().add(GameSettings.PINK_EQUIP[i], 1);
            }
        }
        if (command[0].equals("gilded")) {
            for (int i = 0; i < GameSettings.GILDED_EQUIP.length; i++) {
                player.getInventory().add(GameSettings.GILDED_EQUIP[i], 1);
            }
        }


        if (command[0].equals("bingoreset")) {
            player.getBingo().reset();
        }

        if (command[0].equals("raidloot")) {

            player.getInventory().add(202399, 100);
            player.getInventory().add(220526, 100);
            player.getInventory().add(211942, 100);
            player.getInventory().add(206754, 100);
            player.getInventory().add(223502, 100);

            for (int i = 0; i < 100; i++) {

                player.barrowsRaidLoot = true;
                player.totalBarrowsRaidKills = 100;

                Rewards.grantLoot(player);
                RaidChest.coxChest(player, null);
                RaidChest.tobChest(player, null);
                RaidChest.gwdRaidChest(player, null);
                RaidChest.chaosRaidChest(player, null);
                RaidChest.strongholdRaidChest(player, null);
            }

        }

        if (command[0].equals("miniloots")) {

            for (int i = 0; i < 100; i++) {
                player.WintertodtPoints = 2500;

                BlastMine.loot(player);
                Wintertodt.loot(player);
            }

        }

        if (command[0].equals("loots")) {
            int quantity = Integer.parseInt(command[1]);

            int npcId = Integer.parseInt(command[2]);


            /*player.getBingo().getBoard().stream()
                    .filter(square -> !square.isCompleted())
                    .findFirst().ifPresent(square -> {
                        int npc = -1;
                        if (square.getText() == null) {
                            npc = player.getCollectionLog().getCollections().entrySet().stream()
                                    .filter(entry -> entry.getValue().stream().filter(item -> item.getId() == square.getRequirementId()).count() > 0)
                                    .map(entry -> Integer.parseInt(entry.getKey()))
                                    .findFirst().get();
                        } else {
                            npc = square.getRequirementId();
                        }

                        // do drop on npc
                    });*/


            if (npcId > 0) {
                NPC npc = new NPC(npcId, GameSettings.DEFAULT_POSITION);

                for (int j = 0; j < quantity; j++) {
                    NPCDrops.dropItems(player, npc, player.getPosition(), false);
                }
            } else {
                int[] ids = {2882, 2881, 2883, 50, 1999, 6203, 6222, 6247, 6260, 8133, 13447, 5886, 8349, 7286, 2042, 2043, 2044, 1158,
                        1159, 4540, 1580, 3334, 3340, 499, 7287, 2745, 21554, 22359, 22360, 22388, 22340, 22374, 23425,
                        2000, 2001, 2006, 2009, 3200, 3334, 8349, 22061, 2000, 2001, 2006, 2009, 3200, 3334, 8349, 22061, 6715, 6716, 6701, 6725, 6691, 22};


                for (int i = 0; i < ids.length - 1; i++) {
                    NPC npc = new NPC(ids[i], GameSettings.DEFAULT_POSITION);

                    for (int j = 0; j < quantity; j++) {
                        NPCDrops.dropItems(player, npc, player.getPosition(), false);
                    }
                }
            }

        }

        if (command[0].equals("bingotest")) {
            int quantity = Integer.parseInt(command[1]);

            player.getBingo().getBoard().stream()
                    .filter(square -> !square.isCompleted())
                    .map(square -> {
                        int npcId = -1;
                        if (square.getText() == null) {
                            npcId = player.getCollectionLog().getCollections().entrySet().stream()
                                    .filter(entry -> entry.getValue().stream().anyMatch(item -> item.getId() == square.getRequirementId()))
                                    .map(entry -> Integer.parseInt(entry.getKey()))
                                    .findFirst().get();
                        } else {
                            npcId = square.getRequirementId();
                        }
                        return npcId;
                    })
                    .filter(npcId -> npcId > 0)
                    .findFirst()
                    .ifPresent(npcId -> {
                        NPC npc = new NPC(npcId, GameSettings.DEFAULT_POSITION);

                        for (int i = 0; i < quantity; i++) {
                            NPCDrops.dropItems(player, npc, player.getPosition(), false);
                        }
                    });
        }

        if (command[0].equals("shr")) {

            GameSettings.shrOpen = !GameSettings.shrOpen;

            player.getPacketSender().sendMessage("Stronghold Raids are now " + (GameSettings.shrOpen ? "open." : "closed."));

        }

        if (command[0].equals("yeet")) {
            Marketplace.removeItemsWithCollectedTaxes(Long.parseLong(command[1]), World.taxes);
        }

        if (command[0].equals("removenoble")) {
            String targetPlayer = wholeCommand.substring(12);
            Player player2 = World.getPlayerByName(targetPlayer);

            if (player2 != null)
                NobilitySystem.removeContribution(player2);
        }

        if (command[0].equals("playershop")) {
            TradingPostManager.options(player);
            return;
        }

        if (command[0].equals("fillmailbox")) {
            for (int i = 0; i < player.getMailBox().capacity(); i++) {
                int it = RandomUtility.inclusiveRandom(10000);
                player.getMailBox().addMail(it, 1);
            }
            return;
        }

        if (command[0].equals("emptymailbox")) {
            player.getMailBox().deleteAll();
        }

        if (command[0].equals("shrloot")) {
            int[] rewards = {21061, 21063, 21065, 21068, 21069, 21070, 21071, 21072, 21076, 21077, 21078, 21079, 21081, 21083, 763, 764, 765};

            for (int i = 0; i < rewards.length - 1; i++) {
                player.getInventory().add(rewards[i], 1);
            }
        }

        if (command[0].equals("info")) {
            String discordLog = "frogger";
            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            {
                //new MessageBuilder().append(discordLog).send(DiscordBot.getInstance().getAPI().getCachedMessageById(1015401883789185105));
            }
        }

        if (command[0].equals("activeint")) {
            player.getPacketSender().sendMessage("Active Interface: " + player.getInterfaceId());
        }

        if (command[0].equals("fluff")) {

            player.getPacketSender().sendInterfaceRemoval().sendMessage("You clear your inventory and add junk to it.");
            player.getSkillManager().stopSkilling();
            player.getInventory().resetItems().refreshItems();

            player.getInventory().add(14743, 1);
            player.getInventory().add(14745, 1);
            player.getInventory().add(10316, 1);
            player.getInventory().add(10318, 1);
            player.getInventory().add(10507, 1);
            player.getInventory().add(10511, 1);

        }

        if (command[0].equals("song")) {
            player.getPacketSender().sendSong(Integer.parseInt(command[1]));
        }

        if (command[0].equals("sound")) {
            player.getPacketSender().sendSound(Integer.parseInt(command[1]), 10, 0);
        }

        if (command[0].equals("prestige")) {
            player.prestige = Integer.parseInt(command[1]);
        }

        if (command[0].equals("skin")) {
            int type = Integer.parseInt(command[1]);

            //player.getAppearance().set(Appearance.HAIR_COLOUR, type);
            //player.getAppearance().set(Appearance.TORSO_COLOUR, type);
            //player.getAppearance().set(Appearance.LEG_COLOUR, type);
            //player.getAppearance().set(Appearance.FEET_COLOUR, type);
            player.getAppearance().set(Appearance.SKIN_COLOUR, type);

            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }

        if (command[0].equals("dmz")) {

            //DMZ.spawn(player);
            return;
            //String coords = player.getPosition().toString();
            //PlayerLogs.npcSpawnLog("coords", coords);
        }

        if (command[0].equals("perkfix")) {

            player.perkReset = false;

            player.perkFix(player);
            return;

        }

        if (command[0].equals("holiday")) {

            GameSettings.holidayEvent = !GameSettings.holidayEvent;

            player.getPacketSender().sendMessage("Holiday Event is now: " + (GameSettings.holidayEvent ? "@gre@On" : "@red@Off"));

        }

        if (command[0].equals("location")) {

            player.getPacketSender().sendMessage("Current Location: " + player.getLocation());

        }

        if (command[0].equals("actions")) {
            if (command.length < 3) {
                player.sendMessage("Command usage: '::actions (number) (target)'");
                return;
            }

            int numberOfActions;
            try {
                numberOfActions = Integer.parseInt(command[1]);
            } catch (NumberFormatException nfe) {
                player.sendMessage("Command usage: '::actions (number) (target)'");
                return;
            }

            StringBuilder username = new StringBuilder(command[2].toLowerCase());
            if (command.length > 3) {
                for (int i = 3; i < command.length; i++) {
                    username.append(' ').append(command[i].toLowerCase());
                }
            }

            Player target = World.getPlayerByName(username.toString());

            if (target == null) {
                player.sendMessage(username + " is offline.");
                return;
            }

            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            {
                String msg = "Unable to gather " + numberOfActions + " actions for this player.";
                String actionMsg = target.getActionTracker().lastNActions(numberOfActions);
                if (actionMsg != null)
                    msg = actionMsg;
                new MessageBuilder().append(msg).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_ACTIONS_CH).get());
            }

        }

        if (command[0].equals("delhs")) {
            String username = command[1].toLowerCase();
            Player target = World.getPlayerByName(username);
            if (target == null) {
                target = new Player(null).setUsername(username);
                PlayerLoading.getResultWithoutLogin(target);
            }
            HighScores.delete(target);
        }

        if (command[0].equals("wildypoints")) {
            player.WildyPoints += 100000;
        }

        if (command[0].equals("revs")) {
            player.moveTo(new Position(3104, 3934, 0));
        }

        if (command[0].equals("olm")) {
            CoxRaid.spawnOlm(player, 0);
        }


        if (command[0].equals("hidehs")) {
            String username = command[1].toLowerCase();
            Player target = World.getPlayerByName(username);
            if (target == null) {
                target = new Player(null).setUsername(username);
                PlayerLoading.getResultWithoutLogin(target);
            }
            HighScores.toggleHidden(target);
        }


        if (command[0].equals("pp")) {
            String type = command[1].toLowerCase();
            String direction = command[2].toLowerCase();

            int object = -1;


            int sarc = 321255;
            int chest = 321299;
            int urn = 321261;

            int x = player.getPosition().getX();
            int y = player.getPosition().getY();
            int z = player.getPosition().getZ();

            if (type == "s")
                object = sarc;
            else if (type == "c")
                object = chest;
            else if (type == "u")
                object = urn;

            if (direction == "n")
                y++;
            else if (direction == "s")
                y--;
            else if (direction == "w")
                x--;
            else if (direction == "e")
                x++;


            //{321261, 3590, 3833, 0, 0},

            String commandLog = "{" + object + ", " + x + ", " + y + ", " + z + ", 0},";

            PlayerLogs.DevLog("Developer", commandLog);
        }

        if (command[0].equals("discord")) {
            GameSettings.DISCORD = !GameSettings.DISCORD;
            player.getPacketSender().sendMessage("Discord messages are now set to: " + GameSettings.DISCORD);

        }

        if (command[0].equals("heal")) {
            if (player.getStaffRights() == StaffRights.DEVELOPER) {
                player.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, 10000);
                player.getSkillManager().setCurrentLevel(Skill.PRAYER, 10000);
            } else {
                player.getPacketSender().sendMessage("This is command is only for developers.");
            }
        }

        if (command[0].equals("nice")) {

            World.getNpcs().forEach(n -> n.forceChat("Nice!"));
        }

        if (command[0].equals("npcsay") && command.length > 1 && command[1] != null) {
            String message = Character.toUpperCase(wholeCommand.charAt(6)) + wholeCommand.substring(7);
            World.getNpcs().forEach(n -> n.forceChat(message));
        }

        if (command[0].equalsIgnoreCase("getnpcid")) {
            List<NPC> npcs = player.getLocalNpcs();
            for (NPC npc : npcs) {
                player.sendMessage(npc.getDefinition().getName() + " - " + npc.getDefinition().getId());
            }
        }

        if (command[0].equalsIgnoreCase("getloot")) {
            if (command.length < 2) {
                player.sendMessage("Usage: ::getloot (barrows|cox|tob|gwdr|chaos|shr|<npcid>) [waves_completed|amount_killed]");
                return;
            }
            int npcId;
            try {
                npcId = Integer.parseInt(command[1]);
            } catch (NumberFormatException e) {
                npcId = -1;
            }

            if (npcId > 0) {
                player.sendMessage("::getloot <npcid> is not implemented yet, sorry!");
            } else if (command[1].equalsIgnoreCase("barrows")) {
                if (command.length < 3) {
                    player.sendMessage("Usage: ::getloot barrows waves_completed");
                    return;
                }
                player.moveTo(GameSettings.RAIDS_LOBBY_POSITION.copy()); //put player in the item spawn location
                RaidsParty party = putPlayerInParty(player);
                int wavesCompleted = Integer.parseInt(command[2]);
                int barrowsKilled = 0;
                for (int i = 1; i <= wavesCompleted; i++) {
                    barrowsKilled += i;
                }
                player.barrowsLootWave = wavesCompleted;
                player.barrowsRaidKills = barrowsKilled;
                player.totalBarrowsRaidKills = barrowsKilled;
                player.sendMessage("That's " + barrowsKilled + " brothers");
                Rewards.grantLoot(player);
            } else if (command[1].equalsIgnoreCase("chaos")) {
                if (command.length < 3) {
                    player.sendMessage("Usage: ::getloot chaos waves_completed");
                    return;
                }
                RaidsParty party = putPlayerInParty(player);
                player.chaosLootWave = Integer.parseInt(command[2]);
                ChaosRewards.grantLoot(player);
            }
            // Other raids
            else {
                int kc = 1;
                if (command.length > 2) {
                    kc = Integer.parseInt(command[2]);
                }
                RaidsParty party = putPlayerInParty(player);
                if (command[1].equalsIgnoreCase("cox"))
                    for (int i = 0; i < kc; i++)
                        com.arlania.world.content.minigames.impl.chambersofxeric.CoxRewards.grantLoot(party, player);
                else if (command[1].equalsIgnoreCase("tob"))
                    for (int i = 0; i < kc; i++)
                        com.arlania.world.content.minigames.impl.theatreofblood.TobRewards.grantLoot(party, player);
                else if (command[1].equalsIgnoreCase("gwdr"))
                    for (int i = 0; i < kc; i++)
                        GwdrRewards.grantLoot(party, player);
                else if (command[1].equalsIgnoreCase("shr"))
                    for (int i = 0; i < kc; i++)
                        com.arlania.world.content.minigames.impl.strongholdraids.Rewards.grantLoot(party, player);
            }
        }

        if (command[0].equals("maxxp")) {
            player.getSkillManager().setExperience(Skill.AGILITY, 2000000000);
            player.getSkillManager().setExperience(Skill.ATTACK, 2000000000);
            player.getSkillManager().setExperience(Skill.CONSTITUTION, 2000000000);
            player.getSkillManager().setExperience(Skill.COOKING, 2000000000);
            player.getSkillManager().setExperience(Skill.CRAFTING, 2000000000);
            player.getSkillManager().setExperience(Skill.DEFENCE, 2000000000);
            player.getSkillManager().setExperience(Skill.DUNGEONEERING, 2000000000);
            player.getSkillManager().setExperience(Skill.FARMING, 2000000000);
            player.getSkillManager().setExperience(Skill.FIREMAKING, 2000000000);
            player.getSkillManager().setExperience(Skill.FISHING, 2000000000);
            player.getSkillManager().setExperience(Skill.FLETCHING, 2000000000);
            player.getSkillManager().setExperience(Skill.HERBLORE, 2000000000);
            player.getSkillManager().setExperience(Skill.HUNTER, 2000000000);
            player.getSkillManager().setExperience(Skill.MAGIC, 2000000000);
            player.getSkillManager().setExperience(Skill.MINING, 2000000000);
            player.getSkillManager().setExperience(Skill.PRAYER, 2000000000);
            player.getSkillManager().setExperience(Skill.RANGED, 2000000000);
            player.getSkillManager().setExperience(Skill.RUNECRAFTING, 2000000000);
            player.getSkillManager().setExperience(Skill.SKILLER, 2000000000);
            player.getSkillManager().setExperience(Skill.SLAYER, 2000000000);
            player.getSkillManager().setExperience(Skill.SMITHING, 2000000000);
            player.getSkillManager().setExperience(Skill.STRENGTH, 2000000000);
            player.getSkillManager().setExperience(Skill.SUMMONING, 2000000000);
            player.getSkillManager().setExperience(Skill.THIEVING, 2000000000);
            player.getSkillManager().setExperience(Skill.WOODCUTTING, 2000000000);
            player.prestige = 10;

        }

        if (command[0].equals("coxkc")) {
            int amount = Integer.parseInt(command[1]);
            player.setCoxKC(player.getCoxKC() + amount);
        }

        if (command[0].equals("tobkc")) {
            int amount = Integer.parseInt(command[1]);
            player.setTobKC(player.getTobKC() + amount);
        }

        if (command[0].equalsIgnoreCase("giveadmin")) {
            String name = wholeCommand.substring(10);

            Player target = World.getPlayerByName(name);
            if (target == null) {
                player.getPacketSender().sendMessage("Player is not online");
            } else {
                target.setStaffRights(StaffRights.ADMINISTRATOR);
                target.getPacketSender().sendRights();
                target.getPacketSender().sendMessage("Your player rights have been changed.");
                player.getPacketSender().sendMessage("Gave " + target + "admin.");
            }
        }
        if (command[0].equals("givedonor")) {
            int amount = Integer.parseInt(command[1]);
            String rss = command[2];
            if (command.length > 3) {
                rss += " " + command[3];
            }
            if (command.length > 4) {
                rss += " " + command[4];
            }
            Player target = World.getPlayerByName(rss);
            if (target == null) {
                player.getPacketSender().sendConsoleMessage("Player must be online to give them stuff!");
            } else {
                target.getPointsHandler().setdonatorpoints(target.getPointsHandler().getdonatorpoints(), amount);
                target.getPointsHandler().setAmountDonated(player.getPointsHandler().getAmountDonated(), amount);

                StaffRights rights = null;

                if (player.getStaffRights().getStaffRank() < 5) {
                    if (target.getPointsHandler().getAmountDonated() >= 5)
                        rights = StaffRights.DONATOR;
                    if (target.getPointsHandler().getAmountDonated() >= 25)
                        rights = StaffRights.SUPER_DONATOR;
                    if (target.getPointsHandler().getAmountDonated() >= 100)
                        rights = StaffRights.EXTREME_DONATOR;
                    if (target.getPointsHandler().getAmountDonated() >= 250)
                        rights = StaffRights.LEGENDARY_DONATOR;
                    if (target.getPointsHandler().getAmountDonated() >= 500)
                        rights = StaffRights.UBER_DONATOR;
                    if (rights != null && rights != player.getStaffRights()) {
                        target.getPacketSender().sendMessage("You've become a " + Misc.formatText(rights.toString().toLowerCase()) + "! Congratulations!");
                        target.setStaffRights(rights);
                        target.getPacketSender().sendRights();
                    }
                }
                PlayerPanel.refreshPanel(target);
                target.getPacketSender().sendMessage("You've just received " + amount + " donator points from " + player.getUsername());
                player.getPacketSender().sendMessage("You've just sent " + amount + " donator points to " + target.getUsername());
                PlayerLogs.log(player.getUsername(), "sent " + amount + " donator points to " + target.getUsername() + ".");
            }
        }

        if (command[0].equals("globalobject")) {
            int objId = Integer.parseInt(command[1]);

            Position spawnPos = player.getPosition().copy();
            CustomObjects.spawnGlobalObject(new GameObject(objId, spawnPos));

        }

        if (command[0].equals("setprestige")) {
            int amount = Integer.parseInt(command[1]);

            player.prestige = amount;
            player.perkUpgrades = 10000;
        }

        if (command[0].equals("setpaepoints")) {
            int amount = Integer.parseInt(command[1]);

            player.setPaePoints(amount);
        }


        if (command[0].equals("god")) {
            player.setConstitution(30000);
            int level = 1000;

            for (int i = 0; i < 5; i += 1) {
                Skill skill = Skill.forId(i);
                player.getSkillManager().setCurrentLevel(skill, level).setMaxLevel(skill, level).setExperience(skill,
                        SkillManager.getExperienceForLevel(level));
            }
        }
        if (command[0].equals("ungod")) {
            player.setConstitution(990);
            int level = 99;

            for (int i = 0; i < 5; i += 1) {
                Skill skill = Skill.forId(i);
                player.getSkillManager().setCurrentLevel(skill, level).setMaxLevel(skill, level).setExperience(skill,
                        SkillManager.getExperienceForLevel(level));

            }

        }

        if (command[0].equals("maxbank")) {
            for (int i = 0; i < ClueScrolls.EASY_UNIQUES.length; i++) {
                player.setCurrentBankTab(0);
                player.getBank(player.getCurrentBankTab()).add(ClueScrolls.EASY_UNIQUES[i], 100);
            }
            for (int i = 0; i < ClueScrolls.MEDIUM_UNIQUES.length; i++) {
                player.setCurrentBankTab(0);
                player.getBank(player.getCurrentBankTab()).add(ClueScrolls.MEDIUM_UNIQUES[i], 100);
            }
            for (int i = 0; i < ClueScrolls.HARD_UNIQUES.length; i++) {
                player.setCurrentBankTab(0);
                player.getBank(player.getCurrentBankTab()).add(ClueScrolls.HARD_UNIQUES[i], 100);
            }
            for (int i = 0; i < ClueScrolls.ELITE_UNIQUES.length; i++) {
                player.setCurrentBankTab(0);
                player.getBank(player.getCurrentBankTab()).add(ClueScrolls.ELITE_UNIQUES[i], 100);
            }
            for (int i = 0; i < ClueScrolls.MASTER_UNIQUES.length; i++) {
                player.setCurrentBankTab(0);
                player.getBank(player.getCurrentBankTab()).add(ClueScrolls.MASTER_UNIQUES[i], 100);
            }

            for (int i = 0; i < GameSettings.LOWUNIQUES.length; i++) {
                player.setCurrentBankTab(1);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.LOWUNIQUES[i], 100);
            }
            for (int i = 0; i < GameSettings.MEDIUMUNIQUES.length; i++) {
                player.setCurrentBankTab(1);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.MEDIUMUNIQUES[i], 100);
            }
            for (int i = 0; i < GameSettings.HIGHUNIQUES.length; i++) {
                player.setCurrentBankTab(1);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.HIGHUNIQUES[i], 100);
            }
            for (int i = 0; i < GameSettings.LEGENDARYUNIQUES.length; i++) {
                player.setCurrentBankTab(1);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.LEGENDARYUNIQUES[i], 100);
            }
            for (int i = 0; i < GameSettings.MASTERUNIQUES.length; i++) {
                player.setCurrentBankTab(1);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.MASTERUNIQUES[i], 100);
            }
            for (int i = 0; i < GameSettings.LOWSUPPLYDROPS.length; i++) {
                player.setCurrentBankTab(2);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.LOWSUPPLYDROPS[i], 10000);
            }
            for (int i = 0; i < GameSettings.HIGHSUPPLYDROPS.length; i++) {
                player.setCurrentBankTab(2);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.HIGHSUPPLYDROPS[i], 10000);
            }
            for (int i = 0; i < GameSettings.ALCHABLES.length; i++) {
                player.setCurrentBankTab(2);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.ALCHABLES[i], 10000);
            }
            for (int i = 0; i < GameSettings.UNTRADEABLE_ITEMS.length; i++) {
                player.setCurrentBankTab(3);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.UNTRADEABLE_ITEMS[i], 10000);
            }


        }

        if (command[0].equals("ytbank")) {
            for (int i = 0; i < ClueScrolls.EASY_UNIQUES.length; i++) {
                player.setCurrentBankTab(0);
                player.getBank(player.getCurrentBankTab()).add(ClueScrolls.EASY_UNIQUES[i], 1);
            }
            for (int i = 0; i < ClueScrolls.MEDIUM_UNIQUES.length; i++) {
                player.setCurrentBankTab(0);
                player.getBank(player.getCurrentBankTab()).add(ClueScrolls.MEDIUM_UNIQUES[i], 1);
            }
            for (int i = 0; i < ClueScrolls.HARD_UNIQUES.length; i++) {
                player.setCurrentBankTab(0);
                player.getBank(player.getCurrentBankTab()).add(ClueScrolls.HARD_UNIQUES[i], 1);
            }
            for (int i = 0; i < ClueScrolls.ELITE_UNIQUES.length; i++) {
                player.setCurrentBankTab(0);
                player.getBank(player.getCurrentBankTab()).add(ClueScrolls.ELITE_UNIQUES[i], 1);
            }
            for (int i = 0; i < ClueScrolls.MASTER_UNIQUES.length; i++) {
                player.setCurrentBankTab(0);
                player.getBank(player.getCurrentBankTab()).add(ClueScrolls.MASTER_UNIQUES[i], 1);
            }

            for (int i = 0; i < GameSettings.LOWUNIQUES.length; i++) {
                player.setCurrentBankTab(1);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.LOWUNIQUES[i], 1);
            }
            for (int i = 0; i < GameSettings.MEDIUMUNIQUES.length; i++) {
                player.setCurrentBankTab(1);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.MEDIUMUNIQUES[i], 1);
            }
            for (int i = 0; i < GameSettings.HIGHUNIQUES.length; i++) {
                player.setCurrentBankTab(1);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.HIGHUNIQUES[i], 1);
            }
            for (int i = 0; i < GameSettings.LEGENDARYUNIQUES.length; i++) {
                player.setCurrentBankTab(1);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.LEGENDARYUNIQUES[i], 1);
            }
            for (int i = 0; i < GameSettings.MASTERUNIQUES.length; i++) {
                player.setCurrentBankTab(1);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.MASTERUNIQUES[i], 1);
            }
            for (int i = 0; i < GameSettings.LOWSUPPLYDROPS.length; i++) {
                player.setCurrentBankTab(2);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.LOWSUPPLYDROPS[i], 10000);
            }
            for (int i = 0; i < GameSettings.HIGHSUPPLYDROPS.length; i++) {
                player.setCurrentBankTab(2);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.HIGHSUPPLYDROPS[i], 10000);
            }
            for (int i = 0; i < GameSettings.ALCHABLES.length; i++) {
                player.setCurrentBankTab(2);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.ALCHABLES[i], 100);
            }
            for (int i = 0; i < GameSettings.UNTRADEABLE_ITEMS.length; i++) {
                player.setCurrentBankTab(3);
                player.getBank(player.getCurrentBankTab()).add(GameSettings.UNTRADEABLE_ITEMS[i], 1);
            }


        }


        if (command[0].equals("lowuniques")) {
            for (int i = 0; i < GameSettings.LOWUNIQUES.length; i++) {
                player.getInventory().add(GameSettings.LOWUNIQUES[i], 1);
            }
        }

        if (command[0].equals("mediumuniques")) {
            for (int i = 0; i < GameSettings.MEDIUMUNIQUES.length; i++) {
                player.getInventory().add(GameSettings.MEDIUMUNIQUES[i], 1);
            }
        }

        if (command[0].equals("highuniques")) {
            for (int i = 0; i < GameSettings.HIGHUNIQUES.length; i++) {
                player.getInventory().add(GameSettings.HIGHUNIQUES[i], 1);
            }
        }

        if (command[0].equals("legendaryuniques")) {
            for (int i = 0; i < GameSettings.LEGENDARYUNIQUES.length; i++) {
                player.getInventory().add(GameSettings.LEGENDARYUNIQUES[i], 1);
            }
        }

        if (command[0].equals("masteruniques")) {
            for (int i = 0; i < GameSettings.MASTERUNIQUES.length; i++) {
                player.getInventory().add(GameSettings.MASTERUNIQUES[i], 1);
            }
        }

        if (command[0].equals("highsupply")) {
            for (int i = 0; i < GameSettings.HIGHSUPPLYDROPS.length; i++) {
                player.getInventory().add(GameSettings.HIGHSUPPLYDROPS[i], 1);
            }
        }

        if (command[0].equals("lowsupply")) {
            for (int i = 0; i < GameSettings.LOWSUPPLYDROPS.length; i++) {
                player.getInventory().add(GameSettings.LOWSUPPLYDROPS[i], 1);
            }
        }

        if (command[0].equals("maxrange")) {
            player.getInventory().add(20998, 1);
            player.getInventory().add(221326, 2000000);
            player.getInventory().add(14011, 1);
            player.getInventory().add(14012, 1);
            player.getInventory().add(14013, 1);
            player.getInventory().add(20001, 1);
            player.getInventory().add(18895, 1);
            player.getInventory().add(15019, 1);
            player.getInventory().add(18347, 1);
            player.getInventory().add(222109, 1);
        }

        if (command[0].equals("maxmagic")) {
            player.getInventory().add(14014, 1);
            player.getInventory().add(14015, 1);
            player.getInventory().add(14016, 1);
            player.getInventory().add(18847, 1);
            player.getInventory().add(20002, 1);
            player.getInventory().add(18897, 1);
            player.getInventory().add(20560, 1);
            player.getInventory().add(13738, 1);
            player.getInventory().add(221795, 1);
            player.getInventory().add(4454, 1);
            player.getInventory().add(21061, 1);
            player.getInventory().add(560, 500000);
            player.getInventory().add(565, 500000);
            player.getInventory().add(566, 500000);
            player.setSpellbook(MagicSpellbook.ANCIENT);
        }

        if (command[0].equals("maxmelee")) {
            player.getInventory().add(21006, 1);
            player.getInventory().add(14008, 1);
            player.getInventory().add(14009, 1);
            player.getInventory().add(14010, 1);
            player.getInventory().add(20000, 1);
            player.getInventory().add(224780, 1);
            player.getInventory().add(222981, 1);
            player.getInventory().add(15220, 1);
            player.getInventory().add(221295, 1);
        }


        if (command[0].equals("update")) {
            int time = Integer.parseInt(command[1]);
            if (time > 0) {
                GameServer.setUpdating(true);
                for (Player players : World.getPlayers()) {
                    if (players == null) {
                        continue;
                    }
                    players.getPacketSender().sendSystemUpdate(time);
                }
                TaskManager.submit(new Task(time) {
                    @Override
                    protected void execute() {
                        for (Player player : World.getPlayers()) {
                            if (player != null) {
                                World.deregister(player);
                            }
                        }
                        World.saveWorldState();
                        GameServer.getLogger().info("Update task finished!");
                        stop();
                    }
                });
            }
        }

        if (command[0].equals("dev")) {

            int xcoord = 2719;
            int ycoord = 9622;
            int zcoord = 0;

            Position position = new Position(xcoord, ycoord, zcoord);
            player.moveTo(position);
            player.getPacketSender().sendConsoleMessage("Teleporting to " + position);
        }

        if (command[0].equals("north")) {

            int xcoord = player.getPosition().getX();
            int ycoord = player.getPosition().getY();
            int zcoord = player.getPosition().getZ();

            Position position = new Position(xcoord, ycoord + 50, zcoord);
            player.moveTo(position);
            player.getPacketSender().sendConsoleMessage("Teleporting to " + position);
        }
        if (command[0].equals("south")) {

            int xcoord = player.getPosition().getX();
            int ycoord = player.getPosition().getY();
            int zcoord = player.getPosition().getZ();

            Position position = new Position(xcoord, ycoord - 50, zcoord);
            player.moveTo(position);
            player.getPacketSender().sendConsoleMessage("Teleporting to " + position);
        }
        if (command[0].equals("west")) {

            int xcoord = player.getPosition().getX();
            int ycoord = player.getPosition().getY();
            int zcoord = player.getPosition().getZ();

            Position position = new Position(xcoord - 50, ycoord, zcoord);
            player.moveTo(position);
            player.getPacketSender().sendConsoleMessage("Teleporting to " + position);
        }
        if (command[0].equals("east")) {

            int xcoord = player.getPosition().getX();
            int ycoord = player.getPosition().getY();
            int zcoord = player.getPosition().getZ();

            Position position = new Position(xcoord + 50, ycoord, zcoord);
            player.moveTo(position);
            player.getPacketSender().sendConsoleMessage("Teleporting to " + position);
        }

        if (command[0].equalsIgnoreCase("untradeables")) {


            Path path = Paths.get("./data/saves/characters/untradeables.json");

            if (Files.notExists(path)) {
                try {
                    Files.createDirectories(path.getParent());
                } catch (IOException e) {
                    GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
                }
            }

            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

                int[] items = {
                        11995, 11996, 11997, 11978, 12001, 12002, 12003, 12004, 12005, 12006, 11990, 11991, 11992, 11993, 11994, 11989, 11988, 11987, 11986, 11985, 11984, 11983, 11982, 11981, 11979, 20079, 20103, 20080, 20081, 20082, 20083, 14914, 20086, 14916, 20087, 20088, 20089, 20090, 20085,
                        18349, 18351, 18353, 13262, 19634, 19635, 19642, 19643, 19644, 19711, 19712, 19785, 19786, 19787, 19788, 19789, 19790, 19803, 19804,
                        14022, 14020, 14021, 6570, 14019, 20747, 19785, 19786, 19787, 19788, 19789, 19790, 19803, 19804, 8839, 8840, 8841, 8842, 19711, 19712, 607,
                        608, 611, 786, 2686, 2687, 2688, 4428, 6500, 6570, 6758, 7409, 8839, 8840, 8842, 10506, 10551, 11663, 11664, 11665, 13263, 18337,
                        18338, 18339, 19780, 19998, 20003, 20007, 20015, 20023, 20027
                };


                //for(int x=0; x<items.length; x++)
                //{
                //String banka = "\"bank-";
                //String bankb = "\": [";

                //writer.write(banka + x + bankb);
                //writer.newLine();


                for (int y = 0; y < items.length; y++) {

                    writer.write("{");
                    writer.newLine();
                    writer.write("\"id\": " + items[y] + ",");
                    writer.newLine();
                    writer.write("\"amount\": 10,");
                    writer.newLine();
                    writer.write("\"slot\": 0");
                    writer.newLine();
                    writer.write("},");
                    writer.newLine();
                    //}
                    //writer.write("],");
                    //writer.newLine();
                    //player.getPacketSender().sendMessage("You have created 2,500 new items.");
                }
            } catch (IOException e) {
                GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
            }

            player.getPacketSender().sendMessage("You have created all of the untradeable items.");

        }


        if (command[0].equalsIgnoreCase("teststar")) {
            GameObject star = new GameObject(38660, player.getPosition());
            CustomObjects.spawnGlobalObject(star);
        }
        if (command[0].equalsIgnoreCase("title")) {
            String title = wholeCommand.substring(6);
            if (title == null || title.length() <= 2 || title.length() > 9 || !NameUtils.isValidName(title)) {
                player.getPacketSender().sendMessage("You can not set your title to that!");
                return;
            }
            player.setTitle("@or2@" + title);
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }
        if (command[0].equalsIgnoreCase("sstar")) {
            CustomObjects.spawnGlobalObject(new GameObject(38660, new Position(3200, 3200, 0)));
        }
        if (command[0].equals("checkbank")) {
            Player plr = World.getPlayerByName(wholeCommand.substring(10));
            if (plr != null) {
                player.getPacketSender().sendConsoleMessage("Loading bank..");
                for (Bank b : player.getBanks()) {
                    if (b != null) {
                        b.resetItems();
                    }
                }
                for (int i = 0; i < plr.getBanks().length; i++) {
                    for (Item it : plr.getBank(i).getItems()) {
                        if (it != null) {
                            player.getBank(i).add(it, false);
                        }
                    }
                }
                player.getBank(0).open();
            } else {
                player.getPacketSender().sendConsoleMessage("Player is offline!");
            }
        }

        if (command[0].equals("antibot")) {
            AntiBotting.sendPrompt(player);
        }

        if (command[0].equals("checkinv")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(9));
            if (player2 == null) {
                player.getPacketSender().sendConsoleMessage("Cannot find that player online..");
                return;
            }
            player.getInventory().setItems(player2.getInventory().getCopiedItems()).refreshItems();
        }
		/*if (command[0].equalsIgnoreCase("givess")) {
			String name = wholeCommand.substring(7);

			Player target = World.getPlayerByName(name);
			if (target == null) {
				player.getPacketSender().sendMessage("Player is not online");
			} else {
				target.setStaffRights(StaffRights.SUPPORT);
				target.getPacketSender().sendRights();
				target.getPacketSender().sendMessage("Your player rights have been changed.");
				player.getPacketSender().sendMessage("Gave " + target + "support.");
			}
		}*/
        if (command[0].equalsIgnoreCase("givemod")) {
            String name = wholeCommand.substring(8);

            Player target = World.getPlayerByName(name);
            if (target == null) {
                player.getPacketSender().sendMessage("Player is not online");
            } else {
                target.setStaffRights(StaffRights.MODERATOR);
                target.getPacketSender().sendRights();
                target.getPacketSender().sendMessage("Your player rights have been changed.");
                player.getPacketSender().sendMessage("Gave " + target + "mod.");
            }
        }

        if (command[0].equalsIgnoreCase("dumpitems")) {

            String[] args = null;
            DumpBonuses.main(args);
        }

        if (command[0].equalsIgnoreCase("demote")) {
            String name = wholeCommand.substring(7);

            Player target = World.getPlayerByName(name);
            if (target == null) {
                player.getPacketSender().sendMessage("Player is not online");
            } else {
                target.setStaffRights(StaffRights.PLAYER);
                target.getPacketSender().sendRights();
                target.getPacketSender().sendMessage("Your player rights have been changed.");
                player.getPacketSender().sendMessage("Gave " + target + "player.");
            }
        }
        if (command[0].equals("sendstring")) {
            int child = Integer.parseInt(command[1]);
            String string = command[2];
            player.getPacketSender().sendString(child, string);
        }
        if (command[0].equalsIgnoreCase("kbd")) {
            SLASHBASH.startPreview(player);

        }

        if (command[0].equalsIgnoreCase("spec")) {
            player.setSpecialPercentage(1000000);
            CombatSpecial.updateBar(player);
        }


        if (command[0].equals("item")) {
            int id = Integer.parseInt(command[1]);
            int amount = (command.length == 2 ? 1
                    : Integer.parseInt(command[2].trim().toLowerCase().replaceAll("k", "000").replaceAll("m", "000000")
                    .replaceAll("b", "000000000")));
            if (amount > Integer.MAX_VALUE) {
                amount = Integer.MAX_VALUE;
            }
            Item item = new Item(id, amount);
            player.getInventory().add(item, true);

            player.getPacketSender().sendItemOnInterface(47052, 11694, 1);
        }
        if (command[0].equals("itemn")) {
            int amount = 1;
            StringBuilder nameBuilder = new StringBuilder();
            for (int i = 1; i < command.length; i++) {
                try {
                    amount = Integer.parseInt(command[i]);
                } catch (NumberFormatException e) {
                    nameBuilder.append(command[i]).append(" ");
                }
            }
            String name = nameBuilder.substring(0, nameBuilder.length() - 1);
            int id = -1;
            for (ItemDefinition d : ItemDefinition.getDefinitions()) {
                if (d != null && d.getName() != null && d.getName().equalsIgnoreCase(name)) {
                    id = d.getId();
                    break;
                }
            }
            if (id == -1) {
                player.sendMessage("Item not found in RS2 Data. Try ::itemno");
            } else {
                player.sendMessage(String.format("Gave you %d of %d", amount, id));
            }
            if (amount > Integer.MAX_VALUE) {
                amount = Integer.MAX_VALUE;
            }
            Item item = new Item(id, amount);
            player.getInventory().add(item, true);

            player.getPacketSender().sendItemOnInterface(47052, 11694, 1);
        }
        if (command[0].equals("itemno")) {
            int amount = 1;
            StringBuilder nameBuilder = new StringBuilder();
            for (int i = 1; i < command.length; i++) {
                try {
                    amount = Integer.parseInt(command[i]);
                } catch (NumberFormatException e) {
                    nameBuilder.append(command[i]).append(" ");
                }
            }
            String name = nameBuilder.substring(0, nameBuilder.length() - 1);
            int id = -1;
            for (Map.Entry<Integer, ItemDefinition> dos : ItemDefinitionOSRS.getDefinitions().entrySet()) {
                if (dos.getValue().getName() != null && dos.getValue().getName().equalsIgnoreCase(name)) {
                    id = dos.getKey();
                    break;
                }
            }
            if (id == -1) {
                player.sendMessage("Item not found in OSRS Data. Try ::itemn");
            } else {
                player.sendMessage(String.format("Gave you %d of %d", amount, id));
            }
            if (amount > Integer.MAX_VALUE) {
                amount = Integer.MAX_VALUE;
            }
            Item item = new Item(id, amount);
            player.getInventory().add(item, true);

            player.getPacketSender().sendItemOnInterface(47052, 11694, 1);
        }
        if (command[0].equals("bank")) {
            if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN)
                player.getUimBank().open();
            else
                player.getBank(player.getCurrentBankTab()).open();
        }
        if (command[0].equals("setlevel") && !player.getUsername().equalsIgnoreCase("mark")) {
            int skillId = Integer.parseInt(command[1]);
            int level = Integer.parseInt(command[2]);
            if (level > 15000) {
                player.getPacketSender().sendConsoleMessage("You can only have a maxmium level of 15000.");
                return;
            }
            Skill skill = Skill.forId(skillId);
            player.getSkillManager().setCurrentLevel(skill, level).setMaxLevel(skill, level).setExperience(skill, SkillManager.getExperienceForLevel(level));
            player.getPacketSender().sendConsoleMessage("You have set your " + skill.getName() + " level to " + level);
        }

        if (command[0].equals("tasks")) {
            player.getPacketSender().sendConsoleMessage("Found " + TaskManager.getTaskAmount() + " tasks.");
        }
        if (command[0].equals("cpubans")) {
            ConnectionHandler.reloadUUIDBans();
            player.getPacketSender().sendConsoleMessage("UUID bans reloaded!");
        }
        if (command[0].equals("reloadnpcs")) {
            NpcDefinition.parseNpcs().load();
            World.sendMessage("", "@red@NPC Definitions Reloaded.");
        }
        if (command[0].equals("reloadcombat")) {
            CombatStrategies.init();
            World.sendMessage("", "@red@Combat Strategies have been reloaded");
        }
        if (command[0].equals("reloadshops")) {
            ShopManager.parseShops().load();
            NPCDrops.parseDrops().load();
            ItemDefinition.init();
            World.sendMessage("", "@red@Shops, drops, trading post, item defs have been reloaded");
        }
        if (command[0].equals("reloadipbans")) {
            PlayerPunishment.reloadIPBans();
            player.getPacketSender().sendConsoleMessage("IP bans reloaded!");
        }
        if (command[0].equals("id")) {
            String name = wholeCommand.substring(3).toLowerCase().replaceAll("_", " ");
            player.getPacketSender().sendMessage("Finding item id for item - id: <col=ff0000>" + name);
            boolean found = false;
            for (int i = 0; i < ItemDefinition.getMaxAmountOfItems(); i++) {
                if (ItemDefinition.forId(i).getName().toLowerCase().contains(name)) {
                    player.getPacketSender().sendMessage("Found item with name [<col=ff0000>"
                            + ItemDefinition.forId(i).getName().toLowerCase() + "</col>] - id: <col=ff0000> " + i);
                    found = true;
                }
            }
            if (!found) {
                player.getPacketSender().sendConsoleMessage("No item with name [" + name + "] has been found!");
            }
        }
        if (command[0].equals("osrsitem")) {
            int id = Integer.parseInt(command[1]);
            int amount = (command.length == 2 ? 1
                    : Integer.parseInt(command[2].trim().toLowerCase().replaceAll("k", "000").replaceAll("m", "000000")
                    .replaceAll("b", "000000000")));
            if (amount > Integer.MAX_VALUE) {
                amount = Integer.MAX_VALUE;
            }
            Item item = new Item(id + 200_000, amount);
            player.getInventory().add(item, true);
            player.getPacketSender().sendMessage(
                    "you gave yourself osrs item: " + ItemDefinition.forId(item.getId() + GameSettings.OSRS_OBJECTS_OFFSET) + " amount: " + amount + ".");

            player.getPacketSender().sendItemOnInterface(47052, 11694, 1);
        }
        if (command[0].equals("reloadipmutes")) {
            PlayerPunishment.reloadIPMutes();
            player.getPacketSender().sendConsoleMessage("IP mutes reloaded!");
        }
        if (command[0].equals("reloadbans")) {
            PlayerPunishment.reloadBans();
            player.getPacketSender().sendConsoleMessage("Banned accounts reloaded!");
        }
        // if (command[0].equalsIgnoreCase("cpuban2")) {
        // String serial = wholeCommand.substring(8);
        // ConnectionHandler.banComputer("cpuban2", serial);
        // player.getPacketSender()
        // .sendConsoleMessage("" + serial + " cpu was successfully banned.
        // Command logs written.");
        // }
        if (command[0].equalsIgnoreCase("ipban2")) {
            String ip = wholeCommand.substring(7);
            PlayerPunishment.addBannedIP(ip);
            player.getPacketSender().sendConsoleMessage(ip + " IP was successfully banned. Command logs written.");
        }
        if (command[0].equals("scc")) {
            /*
             * PlayerPunishment.addBannedIP("46.16.33.9");
             * ConnectionHandler.banComputer("Kustoms", -527305299);
             * player.getPacketSender().sendMessage("Banned Kustoms.");
             */
            /*
             * for(GrandExchangeOffer of : GrandExchangeOffers.getOffers()) {
             * if(of != null) { if(of.getId() == 34) { //
             * if(of.getOwner().toLowerCase().contains("eliyahu") ||
             * of.getOwner().toLowerCase().contains("matt")) {
             *
             * player.getPacketSender().sendConsoleMessage("FOUND IT! Owner: "
             * +of.getOwner()+", amount: "+of.getAmount()+", finished: "
             * +of.getAmountFinished()); //
             * GrandExchangeOffers.getOffers().remove(of); //} } } }
             */
            /*
             * Player cc = World.getPlayerByName("Thresh"); if(cc != null) {
             * //cc.getPointsHandler().setPrestigePoints(50, true);
             * //cc.getPointsHandler().refreshPanel();
             * //player.getPacketSender().sendConsoleMessage("Did");
             * cc.getSkillManager().setCurrentLevel(Skill.CONSTITUTION,
             * 15000).updateSkill(Skill.CONSTITUTION);
             * cc.getSkillManager().setCurrentLevel(Skill.PRAYER,
             * 15000).updateSkill(Skill.PRAYER); }
             */
            // player.getSkillManager().addExperience(Skill.CONSTITUTION,
            // 200000000);
            // player.getSkillManager().setExperience(Skill.ATTACK, 1000000000);
            System.out.println("Seri: " + player.getHardwareID());
        }
        if (command[0].equals("memory")) {
            // ManagementFactory.getMemoryMXBean().gc();
            /*
             * MemoryUsage heapMemoryUsage =
             * ManagementFactory.getMemoryMXBean().getHeapMemoryUsage(); long mb
             * = (heapMemoryUsage.getUsed() / 1000);
             */
            long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            player.getPacketSender()
                    .sendConsoleMessage("Heap usage: " + Misc.insertCommasToNumber("" + used) + " bytes!");
        }
        if (command[0].equals("star")) {
            ShootingStar.despawn(true);
            player.getPacketSender().sendConsoleMessage("star method called.");
        }
        if (command[0].equals("stree")) {
            EvilTrees.despawn(true);
            player.getPacketSender().sendConsoleMessage("tree method called.");
        }
		/*if(command[0].equals("worm")) {
			WildyWyrmEvent.spawn();
			player.getPacketSender().sendConsoleMessage("Wildywyrm method called.");
		}*/
        if (command[0].equals("save")) {
            player.save();
        }
        if (command[0].equals("saveall")) {
            World.saveWorldState();
            World.savePlayers();
        }
        if (command[0].equals("test")) {
            player.getSkillManager().addExperience(Skill.FARMING, 500);
        }
        if (command[0].equalsIgnoreCase("frame")) {
            int frame = Integer.parseInt(command[1]);
            String text = command[2];
            player.getPacketSender().sendString(frame, text);
        }
        if (command[0].equals("position")) {
            player.getPacketSender().sendConsoleMessage(player.getPosition().toString());
        }
        if (command[0].equals("npc")) {
            int id = Integer.parseInt(command[1]);
            NPC npc = new NPC(id, new Position(player.getPosition().getX(), player.getPosition().getY(),
                    player.getPosition().getZ()));
            World.register(npc);
            // npc.setConstitution(20000);
            player.getPacketSender().sendEntityHint(npc);
            /*
             * TaskManager.submit(new Task(5) {
             *
             * @Override protected void execute() { npc.moveTo(new
             * Position(npc.getPosition().getX() + 2, npc.getPosition().getY() +
             * 2)); player.getPacketSender().sendEntityHintRemoval(false);
             * stop(); }
             *
             * });
             */
            // npc.getMovementCoordinator().setCoordinator(new
            // Coordinator().setCoordinate(true).setRadius(5));
        }
        if (command[0].equals("skull")) {
            if (player.getSkullTimer() > 0) {
                player.setSkullTimer(0);
                player.setSkullIcon(0);
                player.getUpdateFlag().flag(Flag.APPEARANCE);
            } else {
                CombatFactory.skullPlayer(player);
            }
        }
        if (command[0].equals("fillinv")) {
            for (int i = 0; i < 28; i++) {
                int it = RandomUtility.inclusiveRandom(10000);
                player.getInventory().add(it, 1);
            }
        }

        if (command[0].equals("sp")) {
            StringBuilder name = new StringBuilder();
            for (int i = 1; i < command.length; i++) {
                name.append(command[i]).append((i == command.length - 1) ? "" : " ");
            }

            Player playerToGive = World.getPlayerByName(name.toString());
            SeasonalHandler.addPoints(playerToGive, 1500);
        }

        if (command[0].equals("resetseasonal")) {
            StringBuilder name = new StringBuilder();
            for (int i = 1; i < command.length; i++) {
                name.append(command[i]).append((i == command.length - 1) ? "" : " ");
            }

            Player playerToReset = World.getPlayerByName(name.toString());
            SeasonalHandler.resetSeasonal(playerToReset);
        }

        if (command[0].equals("ssnl")) {
            player.Harvester = true;
            player.Producer = true;
            player.Contender = true;
            player.Strategist = true;
            player.Gilded = true;
            player.Shoplifter = true;
            player.Impulsive = true;
            player.Rapid = true;
            player.Bloodthirsty = true;
            player.Infernal = true;
            player.Summoner = true;
            player.Ruinous = true;
            player.Gladiator = true;
            player.Warlord = true;
            player.Deathless = true;
            player.Executioner = true;
        }

        if (command[0].equals("playnpc")) {
            player.setNpcTransformationId(Integer.parseInt(command[1]));
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }
        if (command[0].equals("playobject")) {
            player.getPacketSender().sendObjectAnimation(new GameObject(2283, player.getPosition().copy()),
                    new Animation(751));
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }
        if (command[0].equals("interface")) {
            int id = Integer.parseInt(command[1]);
            player.getPacketSender().sendInterface(id);
        }

        if (command[0].equals("swi")) {
            int id = Integer.parseInt(command[1]);
            boolean vis = Boolean.parseBoolean(command[2]);
            player.sendParallellInterfaceVisibility(id, vis);
            player.getPacketSender().sendMessage("Done.");
        }
        if (command[0].equals("walkableinterface")) {
            int id = Integer.parseInt(command[1]);
            player.sendParallellInterfaceVisibility(id, true);
        }
        if (command[0].equals("anim")) {
            int id = Integer.parseInt(command[1]);
            player.performAnimation(new Animation(id));
            player.getPacketSender().sendConsoleMessage("Sending animation: " + id);
        }

        if (command[0].equals("osrsanim")) {
            int id = Integer.parseInt(command[1]);
            id += GameSettings.OSRS_ANIM_OFFSET;
            player.performAnimation(new Animation(id));
            player.getPacketSender().sendConsoleMessage("Sending animation: " + id);
        }
        if (command[0].equals("gfx")) {
            int id = Integer.parseInt(command[1]);
            player.performGraphic(new Graphic(id));
            player.getPacketSender().sendConsoleMessage("Sending graphic: " + id);
        }
        if (command[0].equals("osrsgfx")) {
            int id = Integer.parseInt(command[1]) + GameSettings.OSRS_GFX_OFFSET;
            player.performGraphic(new Graphic(id));
            player.getPacketSender().sendConsoleMessage("Sending graphic: " + id);
        }
        if (command[0].equals("object")) {
            int id = Integer.parseInt(command[1]);
            player.getPacketSender().sendObject(new GameObject(id, player.getPosition(), 10, 3));
            player.getPacketSender().sendConsoleMessage("Sending object: " + id);
        }
        if (command[0].equals("config")) {
            int id = Integer.parseInt(command[1]);
            int state = Integer.parseInt(command[2]);
            player.getPacketSender().sendConfig(id, state).sendConsoleMessage("Sent config.");
        }
        if (command[0].equals("checkbank")) {
            Player plr = World.getPlayerByName(wholeCommand.substring(10));
            if (plr != null) {
                player.getPacketSender().sendConsoleMessage("Loading bank..");
                for (Bank b : player.getBanks()) {
                    if (b != null) {
                        b.resetItems();
                    }
                }
                for (int i = 0; i < plr.getBanks().length; i++) {
                    for (Item it : plr.getBank(i).getItems()) {
                        if (it != null) {
                            player.getBank(i).add(it, false);
                        }
                    }
                }
                player.getBank(0).open();
            } else {
                player.getPacketSender().sendConsoleMessage("Player is offline!");
            }
        }
        if (command[0].equals("checkinv")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(9));
            if (player2 == null) {
                player.getPacketSender().sendConsoleMessage("Cannot find that player online..");
                return;
            }
            player.getInventory().setItems(player2.getInventory().getCopiedItems()).refreshItems();
        }
        if (command[0].equals("checkequip")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(11));
            if (player2 == null) {
                player.getPacketSender().sendConsoleMessage("Cannot find that player online..");
                return;
            }
            player.getEquipment().setItems(player2.getEquipment().getCopiedItems()).refreshItems();
            WeaponInterfaces.assign(player, player.getEquipment().get(Equipment.WEAPON_SLOT));
            WeaponAnimations.assign(player, player.getEquipment().get(Equipment.WEAPON_SLOT));
            BonusManager.update(player, 0, 0);
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }
    }

    private static RaidsParty putPlayerInParty(Player player) {
        MinigameAttributes.RaidsAttributes raidsAttributes = player.getMinigameAttributes().getRaidsAttributes();
        if (raidsAttributes.getParty() == null) {
            RaidsParty party = new RaidsParty(player);
            raidsAttributes.setParty(party);
        }
        return raidsAttributes.getParty();
    }

    @Override
    public void handleMessage(Player player, Packet packet) {
        String command = Misc.readString(packet.getBuffer());
        String[] parts = command.toLowerCase().split(" ");
        if (command.contains("\r") || command.contains("\n")) {
            return;
        }

        String commandLog = "[" + Misc.getCurrentServerTime() + "] Username: " + player.getUsername() + " used Command ::" + command + ". Position: " + player.getPosition();

        if (Arrays.stream(automatedClientMessages).noneMatch(x -> x.equals(parts[0]))) {
            player.getActionTracker().offer(new ActionCommand(parts[0], command));
            //PlayerLogs.log("Commands", commandLog);

            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                new MessageBuilder().append(commandLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_COMMANDLOG_CH).get());
        } else {
            handleAutomatedClientMessages(player, parts, command);
        }

        try {
            switch (player.getStaffRights()) {
                case PLAYER:
                    playerCommands(player, parts, command);
                    break;
                case DONATOR:
                case SUPER_DONATOR:
                case EXTREME_DONATOR:
                case LEGENDARY_DONATOR:
                    playerCommands(player, parts, command);
                    donatorCommands(player, parts, command);
                    break;
                case UBER_DONATOR:
                    playerCommands(player, parts, command);
                    donatorCommands(player, parts, command);
                    uberDonatorCommands(player, parts, command);
                    break;
                case MASTER_DONATOR:
                    playerCommands(player, parts, command);
                    donatorCommands(player, parts, command);
                    uberDonatorCommands(player, parts, command);
                    break;
			/*case SUPPORT:
				playerCommands(player, parts, command);
				donatorCommands(player, parts, command);
				uberDonatorCommands(player, parts, command);
				supportCommands(player, parts, command);
				break;*/
                case MODERATOR:
                    playerCommands(player, parts, command);
                    donatorCommands(player, parts, command);
                    uberDonatorCommands(player, parts, command);
                    supportCommands(player, parts, command);
                    moderatorCommands(player, parts, command);
                    break;
                case ADMINISTRATOR:
                    playerCommands(player, parts, command);
                    donatorCommands(player, parts, command);
                    uberDonatorCommands(player, parts, command);
                    supportCommands(player, parts, command);
                    moderatorCommands(player, parts, command);
                    administratorCommands(player, parts, command);
                    break;
                case DEVELOPER:
                    playerCommands(player, parts, command);
                    donatorCommands(player, parts, command);
                    uberDonatorCommands(player, parts, command);
                    supportCommands(player, parts, command);
                    moderatorCommands(player, parts, command);
                    administratorCommands(player, parts, command);
                    developerCommands(player, parts, command);
                    ownerCommands(player, parts, command);
                    break;
                case OWNER:
                    playerCommands(player, parts, command);
                    donatorCommands(player, parts, command);
                    uberDonatorCommands(player, parts, command);
                    supportCommands(player, parts, command);
                    moderatorCommands(player, parts, command);
                    administratorCommands(player, parts, command);
                    developerCommands(player, parts, command);
                    ownerCommands(player, parts, command);
                    break;
                default:
                    playerCommands(player, parts, command);
                    break;
            }
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "error handling player command", e);
            player.getPacketSender().sendMessage("Error executing that command.");
        }
    }
}
