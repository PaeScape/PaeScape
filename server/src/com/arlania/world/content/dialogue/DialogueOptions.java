package com.arlania.world.content.dialogue;

import com.arlania.DiscordBot;
import com.arlania.GameLoader;
import com.arlania.GameSettings;
import com.arlania.engine.task.impl.BonusExperienceTask;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.model.container.impl.Shop.ShopManager;
import com.arlania.model.input.impl.*;
import com.arlania.model.movement.MovementQueue;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.*;
import com.arlania.world.content.Artisan.ArtisanMenu;
import com.arlania.world.content.Gambling.FlowersData;
import com.arlania.world.content.achievements.AchievementAbilities;
import com.arlania.world.content.clan.ClanChatManager;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.content.dialogue.impl.AgilityTicketExchange;
import com.arlania.world.content.dialogue.impl.Mandrith;
import com.arlania.world.content.interfaces.MasteryPerkInterface;
import com.arlania.world.content.interfaces.Seasonal.SeasonalPerkInterface;
import com.arlania.world.content.interfaces.WildernessPerkInterface;
import com.arlania.world.content.marketplace.DeathsCoffer;
import com.arlania.world.content.minigames.bots.FarmingBot;
import com.arlania.world.content.minigames.bots.FishingBot;
import com.arlania.world.content.minigames.bots.MiningBot;
import com.arlania.world.content.minigames.bots.WCingBot;
import com.arlania.world.content.minigames.impl.EquipmentUpgrades;
import com.arlania.world.content.minigames.impl.InstancedBosses;
import com.arlania.world.content.minigames.impl.Nomad;
import com.arlania.world.content.minigames.impl.WildyEquipmentUpgrades;
import com.arlania.world.content.minigames.impl.kingdom.NobilitySystem;
import com.arlania.world.content.skill.impl.dungeoneering.Dungeoneering;
import com.arlania.world.content.skill.impl.dungeoneering.DungeoneeringFloor;
import com.arlania.world.content.skill.impl.herblore.Decanting;
import com.arlania.world.content.skill.impl.mining.Mining;
import com.arlania.world.content.skill.impl.slayer.Slayer;
import com.arlania.world.content.skill.impl.slayer.SlayerDialogues;
import com.arlania.world.content.skill.impl.slayer.SlayerMaster;
import com.arlania.world.content.skill.impl.slayer.SuperiorSlayer;
import com.arlania.world.content.skill.impl.summoning.CharmingImp;
import com.arlania.world.content.skill.impl.summoning.SummoningTab;
import com.arlania.world.content.teleport.TeleportData;
import com.arlania.world.content.transportation.JewelryTeleporting;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.content.transportation.TeleportType;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.npc.NpcAggression;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

public class DialogueOptions {

    //Last id used = 78

    public static void handle(Player player, int id) {
        if (player.getStaffRights() == StaffRights.DEVELOPER) {
            player.getPacketSender().sendMessage("Dialogue button id: " + id + ", action id: " + player.getDialogueActionId()).sendConsoleMessage("Dialogue button id: " + id + ", action id: " + player.getDialogueActionId());
        }
        if (Effigies.handleEffigyAction(player, id)) {
            return;
        }
        String discordMessage = "";

        if (id == FIRST_OPTION_OF_FIVE) {
            if (player.getDialogue() != null) {
                if (player.getDialogue().action(1)) {
                    return;
                }
            }
            switch (player.getDialogueActionId()) {
                case 1029:
                    if (player.getMoneyInPouch() >= 5000000) {
                        player.setMoneyInPouch((player.getMoneyInPouch() - 5000000));
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch()); // Update

                        if(player.getLocation() == Location.CORPOREAL_BEAST) {
                            player.moveTo(new Position(3206, 3680, 0));
                        } else {
                            player.moveTo(new Position(player.getPosition().getX(), player.getPosition().getY() + 2));
                        }
                        player.wildRisk = 2500000;
                        World.taxes += 2500000;
                        player.wildBoost = 0;
                        player.getPacketSender().sendInterfaceRemoval();

                        discordMessage = "[Wilderness] " + player.getUsername() + " has sunk 2.5m crossing the Wildy Ditch";

                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GOLD_SINKS_CH).get());
                    }
                    break;
                case 1026:
                    if (player.getMoneyInPouch() >= 5000000) {
                        player.setMoneyInPouch((player.getMoneyInPouch() - 5000000));
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch()); // Update
                        player.moveTo(new Position(3226, 3665));
                        player.wildRisk = 2500000;
                        World.taxes += 2500000;
                        player.wildBoost = 0;
                        player.getPacketSender().sendInterfaceRemoval();

                        discordMessage = "[Wilderness] " + player.getUsername() + " has sunk 2.5m using the Edgeville Obelisk";

                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GOLD_SINKS_CH).get());
                    }
                    break;

                case 1020:
                    player.moveTo(new Position(3135, 3598, 0));
                    player.getPacketSender().sendMessage("You successfully move to Venenatis!");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    break;

                case 230:
                    TeleportHandler.teleportPlayer(player, TeleportData.WINTERTODT.getPosition(), player.getSpellbook().getTeleportType());
                    break;
                case 214:
                    if (player.getPointsHandler().getAmountDonated() >= 5 && !player.getInventory().isFull()) {
                        player.getPointsHandler().setAmountDonated(player.getPointsHandler().getAmountDonated(), -5);
                        player.getPacketSender().sendMessage("You now have $" + player.getPointsHandler().getAmountDonated() + " donated on this account.");

                        player.getInventory().add(19864, 5);
                        DonationBonds.checkForRankUpdate(player);
                    }

                    break;
                case 190:
                    GameMode.set(player, GameMode.NORMAL, false);
                    StartScreen.handleButton(player, 35769);
                    player.moveTo(GameSettings.DEFAULT_POSITION, false);

                    player.getInventory().deleteAll();
                    player.getEquipment().deleteAll();

                    player.sendMessage("@red@Hardcore ironman lose their status with a death ANYWHERE!!");
                    player.getInventory().add(15246, 1); //starter kit
                    player.getInventory().add(1856, 1); //guide book

                    break;
                case 79:
                    if (!player.getClickDelay().elapsed(2000))
                        return;
                    for (NPC npc : player.getLocalNpcs()) {
                        if (npc != null && npc.getPosition().equals(player.getPosition())) {
                            player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                            return;
                        }
                    }
                    if (CustomObjects.objectExists(player.getPosition().copy())) {
                        player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                        return;
                    }
                    FlowersData flowers = FlowersData.generate2();
                    final GameObject flower = new GameObject(flowers.objectId, player.getPosition().copy());
                    player.getMovementQueue().reset();
                    player.getInventory().delete(299, 1);
                    player.performAnimation(new Animation(827));
                    player.getPacketSender().sendMessage("You plant the seed..");
                    player.getMovementQueue().reset();
                    player.setDialogueActionId(42);
                    player.setInteractingObject(flower);
                    DialogueManager.start(player, 78);
                    MovementQueue.stepAway(player);
                    CustomObjects.globalObjectRemovalTask(flower, 90);
                    player.setPositionToFace(flower.getPosition());
                    player.getClickDelay().reset();

                    break;
                case 77:
                    if (!player.getClickDelay().elapsed(2000))
                        return;
                    for (NPC npc : player.getLocalNpcs()) {
                        if (npc != null && npc.getPosition().equals(player.getPosition())) {
                            player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                            return;
                        }
                    }
                    if (CustomObjects.objectExists(player.getPosition().copy())) {
                        player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                        return;
                    }
                    FlowersData flowers3 = FlowersData.generate10();
                    final GameObject flower3 = new GameObject(flowers3.objectId, player.getPosition().copy());
                    player.getMovementQueue().reset();
                    player.getInventory().delete(299, 1);
                    player.performAnimation(new Animation(827));
                    player.getPacketSender().sendMessage("You plant the seed..");
                    player.getMovementQueue().reset();
                    player.setDialogueActionId(42);
                    player.setInteractingObject(flower3);
                    DialogueManager.start(player, 78);
                    MovementQueue.stepAway(player);
                    CustomObjects.globalObjectRemovalTask(flower3, 90);
                    player.setPositionToFace(flower3.getPosition());
                    player.getClickDelay().reset();

                    break;
                case 78:
                    if (!player.getClickDelay().elapsed(2000))
                        return;
                    for (NPC npc : player.getLocalNpcs()) {
                        if (npc != null && npc.getPosition().equals(player.getPosition())) {
                            player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                            return;
                        }
                    }
                    if (CustomObjects.objectExists(player.getPosition().copy())) {
                        player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                        return;
                    }
                    FlowersData flowers2 = FlowersData.generate6();
                    final GameObject flower2 = new GameObject(flowers2.objectId, player.getPosition().copy());
                    player.getMovementQueue().reset();
                    player.getInventory().delete(299, 1);
                    player.performAnimation(new Animation(827));
                    player.getPacketSender().sendMessage("You plant the seed..");
                    player.getMovementQueue().reset();
                    player.setDialogueActionId(42);
                    player.setInteractingObject(flower2);
                    DialogueManager.start(player, 78);
                    MovementQueue.stepAway(player);
                    CustomObjects.globalObjectRemovalTask(flower2, 90);
                    player.setPositionToFace(flower2.getPosition());
                    player.getClickDelay().reset();
                    break;
                case 1:
                    TeleportHandler.teleportPlayer(player, new Position(3420, 3510), player.getSpellbook().getTeleportType());
                    break;
                case 2:
                    TeleportHandler.teleportPlayer(player, new Position(3235, 3295), player.getSpellbook().getTeleportType());
                    break;
                case 9:
                    DialogueManager.start(player, 16);
                    break;
                case 11:
                    NobilitySystem.openLineage(player, GameMode.NORMAL);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(3087, 3517), player.getSpellbook().getTeleportType());
                    break;
			/*case 13:
				player.setDialogueActionId(78);
				DialogueManager.start(player, 124);
				break;*/ // prestiging
                case 14:
                    TeleportHandler.teleportPlayer(player, new Position(3097 + RandomUtility.inclusiveRandom(1), 9869 + RandomUtility.inclusiveRandom(1)), player.getSpellbook().getTeleportType());
                    break;
                case 15:
                    player.getPacketSender().sendInterfaceRemoval();
                    int total = player.getSkillManager().getMaxLevel(Skill.ATTACK) + player.getSkillManager().getMaxLevel(Skill.STRENGTH);
                    boolean has99 = player.getSkillManager().getMaxLevel(Skill.ATTACK) >= 99 || player.getSkillManager().getMaxLevel(Skill.STRENGTH) >= 99;
                    if (total < 130 && !has99) {
                        player.getPacketSender().sendMessage("");
                        player.getPacketSender().sendMessage("You do not meet the requirements of a Warrior.");
                        player.getPacketSender().sendMessage("You need to have a total Attack and Strength level of at least 140.");
                        player.getPacketSender().sendMessage("Having level 99 in either is fine aswell.");
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(2855, 3543), player.getSpellbook().getTeleportType());
                    break;
                case 17:
                    player.setInputHandling(new SetEmail());
                    player.getPacketSender().sendEnterInputPrompt("Enter your email address:");
                    break;
                case 29:
                    SlayerMaster.changeSlayerMaster(player, SlayerMaster.VANNAKA);
                    break;
                case 36:
                    TeleportHandler.teleportPlayer(player, new Position(2871, 5318, 2), player.getSpellbook().getTeleportType());
                    break;
                case 38:
                    TeleportHandler.teleportPlayer(player, new Position(2273, 4681), player.getSpellbook().getTeleportType());
                    break;
                case 40:
                    TeleportHandler.teleportPlayer(player, new Position(3476, 9502), player.getSpellbook().getTeleportType());
                    break;
                case 41:
                    TeleportHandler.teleportPlayer(player, new Position(2886, 4376), player.getSpellbook().getTeleportType());
                    break;
                //corp
                case 48:
                    JewelryTeleporting.teleport(player, GameSettings.DEFAULT_POSITION);
                    break;
                case 59:
                    if (player.getClickDelay().elapsed(1500)) {
                        PkSets.buySet(player, PkSets.PURE_SET);
                    }
                    break;
                case 60:
                    player.setDialogueActionId(61);
                    DialogueManager.start(player, 102);
                    break;
                case 67:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
                        if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername().equals(player.getUsername())) {
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().setDungeoneeringFloor(DungeoneeringFloor.FIRST_FLOOR);
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().sendMessage("The party leader has changed floor.");
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().refreshInterface();
                        }
                    }
                    break;
                case 68:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
                        if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername().equals(player.getUsername())) {
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().setComplexity(1);
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().sendMessage("The party leader has changed complexity.");
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().refreshInterface();
                        }
                    }
                    break;

            }
        } else if (id == SECOND_OPTION_OF_FIVE) {
            if (player.getDialogue() != null) {
                if (player.getDialogue().action(2)) {
                    return;
                }
            }
            if (player.getDialogue() != null) {
                if (player.getDialogue().action(2)) {
                    return;
                }
            }
            switch (player.getDialogueActionId()) {
                case 1029:
                    if (player.getMoneyInPouch() >= 10000000) {
                        player.setMoneyInPouch((player.getMoneyInPouch() - 10000000));
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch()); // Update

                        if(player.getLocation() == Location.CORPOREAL_BEAST) {
                            player.moveTo(new Position(3206, 3680, 0));
                        } else {
                            player.moveTo(new Position(player.getPosition().getX(), player.getPosition().getY() + 2));
                        }
                        player.wildRisk = 5000000;
                        World.taxes += 5000000;
                        player.wildBoost = 1;
                        player.getPacketSender().sendInterfaceRemoval();

                        discordMessage = "[Wilderness] " + player.getUsername() + " has sunk 5m crossing the Wildy Ditch";

                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GOLD_SINKS_CH).get());
                    }
                    break;
                case 1026:
                    if (player.getMoneyInPouch() >= 10000000) {
                        player.setMoneyInPouch((player.getMoneyInPouch() - 10000000));
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch()); // Update
                        player.moveTo(new Position(3226, 3665));
                        player.wildRisk = 5000000;
                        World.taxes += 5000000;
                        player.wildBoost = 1;
                        player.getPacketSender().sendInterfaceRemoval();

                        discordMessage = "[Wilderness] " + player.getUsername() + " has sunk 5m using the Edgeville Obelisk";

                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GOLD_SINKS_CH).get());
                    }
                    break;
                case 1020:
                    player.moveTo(new Position(3045, 3874, 0));
                    player.getPacketSender().sendMessage("You successfully move to Scorpia!");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;

                case 230:
                    TeleportHandler.teleportPlayer(player, TeleportData.WOODCUTTING_GUILD.getPosition(), player.getSpellbook().getTeleportType());
                    break;
                case 214:
                    if (player.getPointsHandler().getAmountDonated() >= 10 && !player.getInventory().isFull()) {
                        player.getPointsHandler().setAmountDonated(player.getPointsHandler().getAmountDonated(), -10);
                        player.getPacketSender().sendMessage("You now have $" + player.getPointsHandler().getAmountDonated() + " donated on this account.");

                        player.getInventory().add(19864, 10);
                        DonationBonds.checkForRankUpdate(player);
                    }

                    break;
                case 190:
                    GameMode.set(player, GameMode.IRONMAN, false);
                    player.setDialogueActionId(234);
                    DialogueManager.start(player, 234);
                    break;
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(3557 + (RandomUtility.inclusiveRandom(2)), 9946 + RandomUtility.inclusiveRandom(2)), player.getSpellbook().getTeleportType());
                    break;
                case 1:
                    TeleportHandler.teleportPlayer(player, new Position(2933, 9849), player.getSpellbook().getTeleportType());
                    break;
                case 2:
                    TeleportHandler.teleportPlayer(player, new Position(2802, 9148), player.getSpellbook().getTeleportType());
                    break;
                case 9:
                    Lottery.enterLottery(player);
                    break;
                case 78:
                    if (!player.getClickDelay().elapsed(2000))
                        return;
                    for (NPC npc : player.getLocalNpcs()) {
                        if (npc != null && npc.getPosition().equals(player.getPosition())) {
                            player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                            return;
                        }
                    }
                    if (CustomObjects.objectExists(player.getPosition().copy())) {
                        player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                        return;
                    }
                    FlowersData flowers2 = FlowersData.generate7();
                    final GameObject flower2 = new GameObject(flowers2.objectId, player.getPosition().copy());
                    player.getMovementQueue().reset();
                    player.getInventory().delete(299, 1);
                    player.performAnimation(new Animation(827));
                    player.getPacketSender().sendMessage("You plant the seed..");
                    player.getMovementQueue().reset();
                    player.setDialogueActionId(42);
                    player.setInteractingObject(flower2);
                    DialogueManager.start(player, 78);
                    MovementQueue.stepAway(player);
                    CustomObjects.globalObjectRemovalTask(flower2, 90);
                    player.setPositionToFace(flower2.getPosition());
                    player.getClickDelay().reset();

                    break;
                case 11:
                    NobilitySystem.openLineage(player, GameMode.IRONMAN);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(2980 + RandomUtility.inclusiveRandom(3), 3596 + RandomUtility.inclusiveRandom(3)), player.getSpellbook().getTeleportType());
                    break;
                case 79:
                    if (!player.getClickDelay().elapsed(2000))
                        return;
                    for (NPC npc : player.getLocalNpcs()) {
                        if (npc != null && npc.getPosition().equals(player.getPosition())) {
                            player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                            return;
                        }
                    }
                    if (CustomObjects.objectExists(player.getPosition().copy())) {
                        player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                        return;
                    }
                    FlowersData flowers = FlowersData.generate3();
                    final GameObject flower = new GameObject(flowers.objectId, player.getPosition().copy());
                    player.getMovementQueue().reset();
                    player.getInventory().delete(299, 1);
                    player.performAnimation(new Animation(827));
                    player.getPacketSender().sendMessage("You plant the seed..");
                    player.getMovementQueue().reset();
                    player.setDialogueActionId(42);
                    player.setInteractingObject(flower);
                    DialogueManager.start(player, 78);
                    MovementQueue.stepAway(player);
                    CustomObjects.globalObjectRemovalTask(flower, 90);
                    player.setPositionToFace(flower.getPosition());
                    player.getClickDelay().reset();

                    break;
                case 13:
				/*player.getPacketSender().sendInterfaceRemoval();
				for(AchievementData d : AchievementData.values()) {
					if(!AchievementData.getAchievementsOfType().getCompletion()[d.ordinal()]) {
						player.getPacketSender().sendMessage("You must have completed all achievements in order to buy this cape.");
						return;
					}
				}
				boolean usePouch = player.getMoneyInPouch() >= 5000000;
				if(!usePouch && player.getInventory().getAmount(995) < 5000000) {
					player.getPacketSender().sendMessage("You do not have enough coins.");
					return;
				}
				if(usePouch) {
					player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
					player.getPacketSender().sendString(8135, ""+player.getMoneyInPouch());
				} else
					player.getInventory().delete(995, 5000000);
				player.getInventory().add(14022, 1);
				player.getPacketSender().sendMessage("You've purchased an Completionist cape.");
				break;*/
                case 14:
                    TeleportHandler.teleportPlayer(player, new Position(3184 + RandomUtility.inclusiveRandom(1), 5471 + RandomUtility.inclusiveRandom(1)), player.getSpellbook().getTeleportType());
                    break;
                case 15:
                    TeleportHandler.teleportPlayer(player, new Position(2663 + RandomUtility.inclusiveRandom(1), 2651 + RandomUtility.inclusiveRandom(1)), player.getSpellbook().getTeleportType());
                    break;
                case 17:
                    if (player.getBankPinAttributes().hasBankPin()) {
                        DialogueManager.start(player, 12);
                        player.setDialogueActionId(8);
                    } else {
                        BankPin.init(player, false);
                    }
                    break;
                case 29:
                    SlayerMaster.changeSlayerMaster(player, SlayerMaster.DURADEL);
                    break;
                case 36:
                    TeleportHandler.teleportPlayer(player, new Position(1908, 4367), player.getSpellbook().getTeleportType());
                    break;
                case 38:
                    DialogueManager.start(player, 70);
                    player.setDialogueActionId(39);
                    break;
                case 40:
                    TeleportHandler.teleportPlayer(player, new Position(2839, 9557), player.getSpellbook().getTeleportType());
                    break;
                case 41:
                    TeleportHandler.teleportPlayer(player, new Position(2903, 5203), player.getSpellbook().getTeleportType());
                    break;
                case 48:
                    JewelryTeleporting.teleport(player, new Position(3213, 3423));
                    break;
                case 59:
                    if (player.getClickDelay().elapsed(1500)) {
                        PkSets.buySet(player, PkSets.MELEE_MAIN_SET);
                    }
                    break;
                case 60:
                    player.setDialogueActionId(62);
                    DialogueManager.start(player, 102);
                    break;
                case 68:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
                        if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername().equals(player.getUsername())) {
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().setComplexity(2);
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().sendMessage("The party leader has changed complexity.");
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().refreshInterface();
                        }
                    }
                    break;

            }
        } else if (id == THIRD_OPTION_OF_FIVE) {
            if (player.getDialogue() != null) {
                if (player.getDialogue().action(3)) {
                    return;
                }
            }
            switch (player.getDialogueActionId()) {
                case 1029:
                    if (player.getMoneyInPouch() >= 25000000) {
                        player.setMoneyInPouch((player.getMoneyInPouch() - 25000000));
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch()); // Update

                        if(player.getLocation() == Location.CORPOREAL_BEAST) {
                            player.moveTo(new Position(3206, 3680, 0));
                        } else {
                            player.moveTo(new Position(player.getPosition().getX(), player.getPosition().getY() + 2));
                        }
                        player.wildRisk = 12500000;
                        World.taxes += 12500000;
                        player.wildBoost = 2;
                        player.getPacketSender().sendInterfaceRemoval();

                        discordMessage = "[Wilderness] " + player.getUsername() + " has sunk 12.5m crossing the Wildy Ditch";

                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GOLD_SINKS_CH).get());
                    }
                    break;
                case 1026:
                    if (player.getMoneyInPouch() >= 25000000) {
                        player.setMoneyInPouch((player.getMoneyInPouch() - 25000000));
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch()); // Update
                        player.moveTo(new Position(3226, 3665));
                        player.wildRisk = 12500000;
                        World.taxes += 12500000;
                        player.wildBoost = 2;
                        player.getPacketSender().sendInterfaceRemoval();

                        discordMessage = "[Wilderness] " + player.getUsername() + " has sunk 12.5m using the Edgeville Obelisk";

                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GOLD_SINKS_CH).get());
                    }
                    break;
                case 1020:
                    player.moveTo(new Position(3271, 3924, 0));
                    player.getPacketSender().sendMessage("You successfully move to Chaos Elemental!");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;

                case 230:
                    TeleportHandler.teleportPlayer(player, TeleportData.BLAST_MINE.getPosition(), player.getSpellbook().getTeleportType());
                    break;
                case 214:
                    if (player.getPointsHandler().getAmountDonated() >= 25 && !player.getInventory().isFull()) {
                        player.getPointsHandler().setAmountDonated(player.getPointsHandler().getAmountDonated(), -25);
                        player.getPacketSender().sendMessage("You now have $" + player.getPointsHandler().getAmountDonated() + " donated on this account.");

                        player.getInventory().add(19864, 25);
                        DonationBonds.checkForRankUpdate(player);
                    }

                    break;
                case 190:
                    GameMode.set(player, GameMode.ULTIMATE_IRONMAN, false);
                    player.setDialogueActionId(234);
                    DialogueManager.start(player, 234);
                    break;
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(3204 + (RandomUtility.inclusiveRandom(2)), 3263 + RandomUtility.inclusiveRandom(2)), player.getSpellbook().getTeleportType());
                    break;
                case 1:
                    TeleportHandler.teleportPlayer(player, new Position(2480, 5175), player.getSpellbook().getTeleportType());
                    break;
                case 2:
                    TeleportHandler.teleportPlayer(player, new Position(2793, 2773), player.getSpellbook().getTeleportType());
                    break;
                case 9:
                    DialogueManager.start(player, Lottery.Dialogues.getCurrentPot(player));
                    break;
                case 10:
                    DialogueManager.start(player, Mandrith.getDialogue(player));
                    break;

                case 78:
                    if (!player.getClickDelay().elapsed(2000))
                        return;
                    for (NPC npc : player.getLocalNpcs()) {
                        if (npc != null && npc.getPosition().equals(player.getPosition())) {
                            player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                            return;
                        }
                    }
                    if (CustomObjects.objectExists(player.getPosition().copy())) {
                        player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                        return;
                    }
                    FlowersData flowers2 = FlowersData.generate8();
                    final GameObject flower2 = new GameObject(flowers2.objectId, player.getPosition().copy());
                    player.getMovementQueue().reset();
                    player.getInventory().delete(299, 1);
                    player.performAnimation(new Animation(827));
                    player.getPacketSender().sendMessage("You plant the seed..");
                    player.getMovementQueue().reset();
                    player.setDialogueActionId(42);
                    player.setInteractingObject(flower2);
                    DialogueManager.start(player, 78);
                    MovementQueue.stepAway(player);
                    CustomObjects.globalObjectRemovalTask(flower2, 90);
                    player.setPositionToFace(flower2.getPosition());
                    player.getClickDelay().reset();

                    break;
                case 79:
                    if (!player.getClickDelay().elapsed(2000))
                        return;
                    for (NPC npc : player.getLocalNpcs()) {
                        if (npc != null && npc.getPosition().equals(player.getPosition())) {
                            player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                            return;
                        }
                    }
                    if (CustomObjects.objectExists(player.getPosition().copy())) {
                        player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                        return;
                    }
                    FlowersData flowers = FlowersData.generate4();
                    final GameObject flower = new GameObject(flowers.objectId, player.getPosition().copy());
                    player.getMovementQueue().reset();
                    player.getInventory().delete(299, 1);
                    player.performAnimation(new Animation(827));
                    player.getPacketSender().sendMessage("You plant the seed..");
                    player.getMovementQueue().reset();
                    player.setDialogueActionId(42);
                    player.setInteractingObject(flower);
                    DialogueManager.start(player, 78);
                    MovementQueue.stepAway(player);
                    CustomObjects.globalObjectRemovalTask(flower, 90);
                    player.setPositionToFace(flower.getPosition());
                    player.getClickDelay().reset();

                    break;
                case 11:
                    NobilitySystem.openLineage(player, GameMode.HARDCORE_IRONMAN);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(3239 + RandomUtility.inclusiveRandom(2), 3619 + RandomUtility.inclusiveRandom(2)), player.getSpellbook().getTeleportType());
                    break;
                case 13:
                    player.getPacketSender().sendInterfaceRemoval();


                    player.getPacketSender().sendMessage("Speak To Paescape to obtain this rank..");
                    //DialogueManager.start(player, 122);
                    //player.setDialogueActionId(76);
                    break;
                case 14:
                    TeleportHandler.teleportPlayer(player, new Position(2713, 9564), player.getSpellbook().getTeleportType());
                    break;
                case 15:
                    TeleportHandler.teleportPlayer(player, new Position(3368 + RandomUtility.inclusiveRandom(5), 3267 + RandomUtility.inclusiveRandom(3)), player.getSpellbook().getTeleportType());
                    player.getPacketSender().sendMessage("Don't Lose all of your items, Goodluck!");
                    break;
                case 17:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getBankPinAttributes().hasBankPin()) {
                        player.getPacketSender().sendMessage("Please visit the nearest bank and enter your pin before doing this.");
                        return;
                    }
                    if (player.getSummoning().getFamiliar() != null) {
                        player.getPacketSender().sendMessage("Please dismiss your familiar first.");
                        return;
                    }
                    if (player.getGameMode() == GameMode.NORMAL) {
                        DialogueManager.start(player, 83);
                    } else {
                        player.setDialogueActionId(46);
                        DialogueManager.start(player, 84);
                    }
                    break;
                case 29:
                    SlayerMaster.changeSlayerMaster(player, SlayerMaster.KURADEL);
                    break;
                case 36:
                    player.setDialogueActionId(37);
                    DialogueManager.start(player, 70);
                    break;
                case 38:
                    TeleportHandler.teleportPlayer(player, new Position(2547, 9448), player.getSpellbook().getTeleportType());
                    break;
                case 40:
                    TeleportHandler.teleportPlayer(player, new Position(2891, 4767), player.getSpellbook().getTeleportType());
                    break;
                case 41:
                    TeleportHandler.teleportPlayer(player, new Position(3236, 3941), player.getSpellbook().getTeleportType());
                    break;
                //scorp
                case 48:
                    JewelryTeleporting.teleport(player, new Position(3368, 3267));
                    break;
                case 59:
                    if (player.getClickDelay().elapsed(1500)) {
                        PkSets.buySet(player, PkSets.RANGE_MAIN_SET);
                    }
                    break;
                case 60:
                    player.setDialogueActionId(63);
                    DialogueManager.start(player, 102);
                    break;
                case 68:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
                        if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername().equals(player.getUsername())) {
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().setComplexity(3);
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().sendMessage("The party leader has changed complexity.");
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().refreshInterface();
                        }
                    }
                    break;
            }
        } else if (id == FOURTH_OPTION_OF_FIVE) {
            if (player.getDialogue() != null) {
                if (player.getDialogue().action(4)) {
                    return;
                }
            }
            switch (player.getDialogueActionId()) {
                case 1029:
                    if (player.getMoneyInPouch() >= 50000000) {
                        player.setMoneyInPouch((player.getMoneyInPouch() - 50000000));
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch()); // Update

                        if(player.getLocation() == Location.CORPOREAL_BEAST) {
                            player.moveTo(new Position(3206, 3680, 0));
                        } else {
                            player.moveTo(new Position(player.getPosition().getX(), player.getPosition().getY() + 2));
                        }
                        player.wildRisk = 25000000;
                        World.taxes += 25000000;
                        player.wildBoost = 3;
                        player.getPacketSender().sendInterfaceRemoval();

                        discordMessage = "[Wilderness] " + player.getUsername() + " has sunk 25m crossing the Wildy Ditch";

                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GOLD_SINKS_CH).get());
                    }
                    break;
                case 1026:
                    if (player.getMoneyInPouch() >= 50000000) {
                        player.setMoneyInPouch((player.getMoneyInPouch() - 50000000));
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch()); // Update
                        player.moveTo(new Position(3226, 3665));
                        player.wildRisk = 25000000;
                        World.taxes += 25000000;
                        player.wildBoost = 3;
                        player.getPacketSender().sendInterfaceRemoval();

                        discordMessage = "[Wilderness] " + player.getUsername() + " has sunk 25m using the Edgeville Obelisk";

                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GOLD_SINKS_CH).get());
                    }
                    break;
                case 1020:
                    player.moveTo(new Position(3067, 3787, 0));
                    player.getPacketSender().sendMessage("You successfully move to Vet'ion!");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;

                case 230:
                    TeleportHandler.teleportPlayer(player, TeleportData.CHAMBERS_OF_XERIC.getPosition(), player.getSpellbook().getTeleportType());
                    break;
                case 0:
                    TeleportHandler.teleportPlayer(player, new Position(3173 - (RandomUtility.inclusiveRandom(2)), 2981 + RandomUtility.inclusiveRandom(2)), player.getSpellbook().getTeleportType());
                    break;
                case 1:
                    TeleportHandler.teleportPlayer(player, new Position(3279, 2964), player.getSpellbook().getTeleportType());
                    break;
                case 2:
                    TeleportHandler.teleportPlayer(player, new Position(3085, 9672), player.getSpellbook().getTeleportType());
                    break;
                case 9:
                    DialogueManager.start(player, Lottery.Dialogues.getLastWinner(player));
                    break;
                case 11:
                    NobilitySystem.openLineage(player, GameMode.ULTIMATE_IRONMAN);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(3329 + RandomUtility.inclusiveRandom(2), 3660 + RandomUtility.inclusiveRandom(2), 0), player.getSpellbook().getTeleportType());
                    break;
                case 78:
                    if (!player.getClickDelay().elapsed(2000))
                        return;
                    for (NPC npc : player.getLocalNpcs()) {
                        if (npc != null && npc.getPosition().equals(player.getPosition())) {
                            player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                            return;
                        }
                    }
                    if (CustomObjects.objectExists(player.getPosition().copy())) {
                        player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                        return;
                    }
                    FlowersData flowers2 = FlowersData.generate9();
                    final GameObject flower2 = new GameObject(flowers2.objectId, player.getPosition().copy());
                    player.getMovementQueue().reset();
                    player.getInventory().delete(299, 1);
                    player.performAnimation(new Animation(827));
                    player.getPacketSender().sendMessage("You plant the seed..");
                    player.getMovementQueue().reset();
                    player.setDialogueActionId(42);
                    player.setInteractingObject(flower2);
                    DialogueManager.start(player, 78);
                    MovementQueue.stepAway(player);
                    CustomObjects.globalObjectRemovalTask(flower2, 90);
                    player.setPositionToFace(flower2.getPosition());
                    player.getClickDelay().reset();

                    break;
                case 79:
                    if (!player.getClickDelay().elapsed(2000))
                        return;
                    for (NPC npc : player.getLocalNpcs()) {
                        if (npc != null && npc.getPosition().equals(player.getPosition())) {
                            player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                            return;
                        }
                    }
                    if (CustomObjects.objectExists(player.getPosition().copy())) {
                        player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                        return;
                    }
                    FlowersData flowers = FlowersData.generate5();
                    final GameObject flower = new GameObject(flowers.objectId, player.getPosition().copy());
                    player.getMovementQueue().reset();
                    player.getInventory().delete(299, 1);
                    player.performAnimation(new Animation(827));
                    player.getPacketSender().sendMessage("You plant the seed..");
                    player.getMovementQueue().reset();
                    player.setDialogueActionId(42);
                    player.setInteractingObject(flower);
                    DialogueManager.start(player, 78);
                    MovementQueue.stepAway(player);
                    CustomObjects.globalObjectRemovalTask(flower, 90);
                    player.setPositionToFace(flower.getPosition());
                    player.getClickDelay().reset();

                    break;
                case 13:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (!player.getUnlockedLoyaltyTitles()[LoyaltyProgramme.LoyaltyTitles.MAXED.ordinal()]) {
                        player.getPacketSender().sendMessage("You must have unlocked the 'Maxed' Loyalty Title in order to buy this cape.");
                        return;
                    }
                    boolean usePouch = player.getMoneyInPouch() >= 2500000;
                    if (!usePouch && player.getInventory().getAmount(995) < 2500000) {
                        player.getPacketSender().sendMessage("You do not have enough coins.");
                        return;
                    }
                    if (usePouch) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 2500000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                    } else
                        player.getInventory().delete(995, 2500000);
                    player.getInventory().add(213280, 1);
                    player.getPacketSender().sendMessage("You've purchased a Max cape.");
                    break;
                case 14:
                    TeleportHandler.teleportPlayer(player, new Position(2884, 9797), player.getSpellbook().getTeleportType());
                    break;
                case 15:
                    TeleportHandler.teleportPlayer(player, new Position(3565, 3313), player.getSpellbook().getTeleportType());
                    break;
                case 29:
                    SlayerMaster.changeSlayerMaster(player, SlayerMaster.SUMONA);
                    break;
                case 36:
                    TeleportHandler.teleportPlayer(player, new Position(2717, 9805), player.getSpellbook().getTeleportType());
                    break;
                case 38:
                    TeleportHandler.teleportPlayer(player, new Position(1891, 3177), player.getSpellbook().getTeleportType());
                    break;
                case 40:
                    TeleportHandler.teleportPlayer(player, new Position(3050, 9573), player.getSpellbook().getTeleportType());
                    break;
                case 41:
                    TeleportHandler.teleportPlayer(player, new Position(3350, 3734), player.getSpellbook().getTeleportType());
                    break;
                case 48:
                    JewelryTeleporting.teleport(player, new Position(2447, 5169));
                    break;
                case 59:
                    if (player.getClickDelay().elapsed(1500)) {
                        PkSets.buySet(player, PkSets.MAGIC_MAIN_SET);
                    }
                    break;
                case 60:
                    player.setDialogueActionId(64);
                    DialogueManager.start(player, 102);
                    break;
                case 68:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
                        if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername().equals(player.getUsername())) {
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().setComplexity(4);
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().sendMessage("The party leader has changed complexity.");
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().refreshInterface();
                        }
                    }
                    break;
                case 190:
                    GameMode.set(player, GameMode.HARDCORE_IRONMAN, false);
                    player.setDialogueActionId(234);
                    DialogueManager.start(player, 234);
                    break;

                case 214:
                    if (player.getPointsHandler().getAmountDonated() >= 100 && !player.getInventory().isFull()) {
                        player.getPointsHandler().setAmountDonated(player.getPointsHandler().getAmountDonated(), -100);
                        player.getPacketSender().sendMessage("You now have $" + player.getPointsHandler().getAmountDonated() + " donated on this account.");

                        player.getInventory().add(19864, 100);
                        DonationBonds.checkForRankUpdate(player);
                    }

                    break;
            }
        } else if (id == FIFTH_OPTION_OF_FIVE) {
            if (player.getDialogue() != null) {
                if (player.getDialogue().action(5)) {
                    return;
                }
            }
            switch (player.getDialogueActionId()) {
                case 1029:
                    if (player.getMoneyInPouch() >= 100000000) {
                        player.setMoneyInPouch((player.getMoneyInPouch() - 100000000));
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch()); // Update

                        if(player.getLocation() == Location.CORPOREAL_BEAST) {
                            player.moveTo(new Position(3206, 3680, 0));
                        } else {
                            player.moveTo(new Position(player.getPosition().getX(), player.getPosition().getY() + 2));
                        }
                        player.wildRisk = 50000000;
                        World.taxes += 50000000;
                        player.wildBoost = 4;
                        player.getPacketSender().sendInterfaceRemoval();

                        discordMessage = "[Wilderness] " + player.getUsername() + " has sunk 50m crossing the Wildy Ditch";

                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GOLD_SINKS_CH).get());
                    }
                    break;
                case 1026:
                    if (player.getMoneyInPouch() >= 100000000) {
                        player.setMoneyInPouch((player.getMoneyInPouch() - 100000000));
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch()); // Update
                        player.moveTo(new Position(3226, 3665));
                        player.wildRisk = 50000000;
                        World.taxes += 50000000;
                        player.wildBoost = 4;
                        player.getPacketSender().sendInterfaceRemoval();

                        discordMessage = "[Wilderness] " + player.getUsername() + " has sunk 50m using the Edgeville Obelisk";

                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GOLD_SINKS_CH).get());
                    }
                    break;
                case 1020:
                    player.moveTo(new Position(3000, 3711, 0));
                    player.getPacketSender().sendMessage("You successfully move to Callisto!");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 230:
                    player.getPacketSender().sendMessage("@red@This teleport is not currently available.");
                    break;
                case 0:
                    player.setDialogueActionId(1);
                    DialogueManager.next(player);
                    break;
                case 1:
                    player.setDialogueActionId(2);
                    DialogueManager.next(player);
                    break;
                case 2:
                    player.setDialogueActionId(0);
                    DialogueManager.start(player, 0);
                    break;
                case 9:
                case 10:
                case 11:
                case 13:
                case 17:
                case 48:
                case 60:
                case 67:
                case 68:
                case 1030:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 29:
                    SlayerMaster.changeSlayerMaster(player, SlayerMaster.DAVE);
                    break;
                case 12:
                    int random = RandomUtility.inclusiveRandom(4);
                    switch (random) {
                        case 0:
                            TeleportHandler.teleportPlayer(player, new Position(3035, 3701, 0), player.getSpellbook().getTeleportType());
                            break;
                        case 1:
                            TeleportHandler.teleportPlayer(player, new Position(3036, 3694, 0), player.getSpellbook().getTeleportType());
                            break;
                        case 2:
                            TeleportHandler.teleportPlayer(player, new Position(3045, 3697, 0), player.getSpellbook().getTeleportType());
                            break;
                        case 3:
                            TeleportHandler.teleportPlayer(player, new Position(3043, 3691, 0), player.getSpellbook().getTeleportType());
                            break;
                        case 4:
                            TeleportHandler.teleportPlayer(player, new Position(3037, 3687, 0), player.getSpellbook().getTeleportType());
                            break;
                    }
                    break;
                case 14:
                    DialogueManager.start(player, 23);
                    player.setDialogueActionId(14);
                    break;
                case 79:
                    DialogueManager.start(player, 1);
                    player.setDialogueActionId(78);
                    break;
                case 78:
                    DialogueManager.start(player, 2);
                    player.setDialogueActionId(77);
                    break;
                case 77:
                    DialogueManager.start(player, 0);
                    player.setDialogueActionId(79);
                    break;
                case 15:
                    DialogueManager.start(player, 32);
                    player.setDialogueActionId(18);
                    break;
                case 36:
                    DialogueManager.start(player, 66);
                    player.setDialogueActionId(38);
                    break;
                case 38:
                    DialogueManager.start(player, 68);
                    player.setDialogueActionId(40);
                    break;
                case 40:
                    DialogueManager.start(player, 69);
                    player.setDialogueActionId(41);
                    break;
                case 41:
                    DialogueManager.start(player, 65);
                    player.setDialogueActionId(36);
                    break;
                case 59:
                    if (player.getClickDelay().elapsed(1500)) {
                        PkSets.buySet(player, PkSets.HYBRIDING_MAIN_SET);
                    }
                    break;

                case 190:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getPacketSender().sendMessage("Please select another game mode.");
                    break;
                case 214:
                    if (player.getPointsHandler().getAmountDonated() > 0 && !player.getInventory().isFull()) {
                        int all = player.getPointsHandler().getAmountDonated();

                        player.getPointsHandler().setAmountDonated(player.getPointsHandler().getAmountDonated(), -all);
                        player.getPacketSender().sendMessage("You now have $" + player.getPointsHandler().getAmountDonated() + " donated on this account.");

                        player.getInventory().add(19864, all);
                        DonationBonds.checkForRankUpdate(player);
                    }

                    break;

            }
        } else if (id == FIRST_OPTION_OF_FOUR) {
            if (player.getDialogue() != null) {
                if (player.getDialogue().action(1)) {
                    return;
                }
            }
            switch (player.getDialogueActionId()) {
                case 1023:
                    player.moveTo(new Position(3250, 3777, 0));
                    player.getPacketSender().sendMessage("You successfully move to the Amethyst Mining Area!");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 1022:
                    player.moveTo(new Position(3184, 3946, 0));
                    player.getPacketSender().sendMessage("You successfully moved to the Wilderness Resource Area!");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 1021:
                    player.moveTo(new Position(2966, 3948, 0));
                    player.getPacketSender().sendMessage("You successfully move to Vorkath!");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;

                case 1019:
                    if (player.WildyPoints >= 10) {
                        DialogueManager.start(player, 1020);
                        player.setDialogueActionId(1020);
                    } else if (player.WildyPoints < 10) {
                        player.getPacketSender().sendMessage("You need 10 Wildy Points to active the Oddskull.");
                    } else {
                        player.getPacketSender().sendMessage("You must wait up to 10 minutes to activate the oddSkull again.");
                    }
                    break;
                case 5:
                    player.getPacketSender().sendInterfaceRemoval();
//				MySQLController.getStore().claim(player);
                    break;
                case 8:
                    ShopManager.getShops().get(71).open(player);
                    break;
                case 9:
                    TeleportHandler.teleportPlayer(player, new Position(2440, 3090), player.getSpellbook().getTeleportType());
                    break;
                case 13:
                    if (player.Artisan > 0) {
                        player.activeMenu = "skiller";
                        ArtisanMenu.openTab(player, 60602);
                    } else
                        player.getSkiller().resetSkillerSkill();
                    break;
                case 14:
                    TeleportHandler.teleportPlayer(player, new Position(2871, 5318, 2), player.getSpellbook().getTeleportType());
                    break;
                case 18:
                    TeleportHandler.teleportPlayer(player, new Position(2439 + RandomUtility.inclusiveRandom(2), 5171 + RandomUtility.inclusiveRandom(2)), player.getSpellbook().getTeleportType());
                    break;
                case 26:
                    TeleportHandler.teleportPlayer(player, new Position(2480, 3435), player.getSpellbook().getTeleportType());
                    break;
                case 27:
                    ClanChatManager.createClan(player);
                    break;
                case 28:
                    player.setDialogueActionId(29);
                    DialogueManager.start(player, 62);
                    break;
                case 30:
                    player.getSlayer().assignTask();
                    break;
                case 31:
                    DialogueManager.start(player, SlayerDialogues.findAssignment(player));
                    break;
                case 41:
                    DialogueManager.start(player, 76);
                    break;
                case 45:
                    GameMode.set(player, GameMode.NORMAL, false);
                    break;
                case 164:
                    if (player.getMoneyInPouchAsInt() >= 1000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 1000000);
                        player.getPacketSender().sendMessage("1,000,000 coins have been removed from your pouch.");
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getMinigameAttributes().getMiningBotAttributes().setResourcesLeftToGather(player.getMinigameAttributes().getMiningBotAttributes().getResourcesLeftToGather() + 500);

                        if (!player.getMinigameAttributes().getMiningBotAttributes().getHasMiningBot()) {
                            player.getMinigameAttributes().getMiningBotAttributes().setHasMiningBot(true);
                            MiningBot.start(player);
                        }
                    } else {
                        player.getPacketSender().sendMessage("You must have 1,000,000 coins in your pouch");
                    }
                    break;

                case 165:
                    player.getInventory().delete(15246, 1);
                    player.getInventory().add(1153, 1); //helm
                    player.getInventory().add(1115, 1); //body
                    player.getInventory().add(1067, 1); //legs
                    player.getInventory().add(1191, 1); //shield
                    player.getInventory().add(1323, 1); //scim
                    player.getInventory().add(995, 500000); //coins
                    player.getInventory().add(385, 21); //food
                    player.getPacketSender().sendMessage("@red@Complete achievements, slayer tasks, kills bosses, and compete in pvp to gain PaePoints");
                    player.getPacketSender().sendMessage("@red@PaePoints can be spent at the Specialty and Untradeables shop in the Home Bank.");
                    DialogueManager.next(player);
                    break;
            }
        } else if (id == SECOND_OPTION_OF_FOUR) {
            if (player.getDialogue() != null) {
                if (player.getDialogue().action(2)) {
                    return;
                }
            }
            switch (player.getDialogueActionId()) {
                case 1023:
                    player.moveTo(new Position(3366, 3615, 0));
                    player.getPacketSender().sendMessage("You successfully move to the Anglerfish Fishing Area!");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 1022:
                    player.moveTo(new Position(2958, 3820, 0));
                    player.getPacketSender().sendMessage("You successfully moved to the Chaos Altar!");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 1021:
                    player.moveTo(new Position(3086, 3927, 0));
                    player.getPacketSender().sendMessage("You successfully move to the Revenant Arena!");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;

                case 1019:
                    if (player.WildyPoints >= 10) {
                        DialogueManager.start(player, 1021);
                        player.setDialogueActionId(1021);
                    } else if (player.WildyPoints < 10) {
                        player.getPacketSender().sendMessage("You need 10 Wildy Points to active the Oddskull.");
                    } else {
                        player.getPacketSender().sendMessage("You must wait up to 10 minutes to activate the oddSkull again.");
                    }
                    break;
                case 5:
                    DialogueManager.start(player, DonationBonds.getTotalFunds(player));
                    break;
                case 8:
                    LoyaltyProgramme.open(player);
                    break;
                case 9:
                    DialogueManager.start(player, 14);
                    break;
                case 13:
                    if (player.Artisan > 0) {
                        player.activeMenu = "skiller";
                        ArtisanMenu.openTab(player, 60602);
                    } else
                        player.getSkiller().resetSkillerTask();
                    break;
                case 14:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSkillManager().getCurrentLevel(Skill.SLAYER) < 50) {
                        player.getPacketSender().sendMessage("You need a Slayer level of at least 50 to visit this dungeon.");
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(2731, 5095), player.getSpellbook().getTeleportType());
                    break;
                case 18:
                    TeleportHandler.teleportPlayer(player, new Position(2399, 5177), player.getSpellbook().getTeleportType());
                    break;
                case 26:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSkillManager().getMaxLevel(Skill.AGILITY) < 35) {
                        player.getPacketSender().sendMessage("You need an Agility level of at least level 35 to use this course.");
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(2552, 3556), player.getSpellbook().getTeleportType());
                    break;
                case 27:
                    ClanChatManager.clanChatSetupInterface(player, true);
                    break;
                case 28:
                    player.getPacketSender().sendMessage("@red@Vannaka: 5 - No requirement.");
                    player.getPacketSender().sendMessage("@red@Duradel: 10 - 50 Slayer required.");
                    player.getPacketSender().sendMessage("@red@Kuradel: 15 - 75 Slayer required.");
                    player.getPacketSender().sendMessage("@red@Sumona: 20 - 92 Slayer required.");
                    player.getPacketSender().sendMessage("@red@Dave: 25 - Wilderness");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 31:
                    if (player.Unnatural < 1)
                        DialogueManager.start(player, SlayerDialogues.resetTaskDialogue(player));
                    else
                        DialogueManager.start(player, SlayerDialogues.resetTaskDialogueUnnatural(player));
                    break;
                case 41:
                    WellOfGoodwill.lookDownWell(player);
                    break;
                case 42:
                    WellOfEvents.lookDownWell(player);
                    break;
                case 45:
                    GameMode.set(player, GameMode.IRONMAN, false);
                    break;
                case 164:
                    if (player.getMoneyInPouchAsInt() >= 1000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 1000000);
                        player.getPacketSender().sendMessage("1,000,000 coins have been removed from your pouch.");
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getMinigameAttributes().getWCingBotAttributes().setResourcesLeftToGather(player.getMinigameAttributes().getWCingBotAttributes().getResourcesLeftToGather() + 1000);

                        if (!player.getMinigameAttributes().getWCingBotAttributes().getHasWCingBot()) {
                            player.getMinigameAttributes().getWCingBotAttributes().setHasWCingBot(true);
                            WCingBot.start(player);
                        }
                    } else {
                        player.getPacketSender().sendMessage("You must have 1,000,000 coins in your pouch");
                    }
                    break;
                case 165:
                    player.getInventory().delete(15246, 1);
                    player.getInventory().add(1169, 1); //helm
                    player.getInventory().add(1129, 1); //body
                    player.getInventory().add(1095, 1); //legs
                    player.getInventory().add(882, 500); //arrows
                    player.getInventory().add(841, 1); //bow
                    player.getInventory().add(995, 500000); //coins
                    player.getInventory().add(385, 21); //food
                    player.getPacketSender().sendMessage("@or2@Complete achievements, slayer tasks, kills bosses, and compete in pvp to gain PaePoints");
                    player.getPacketSender().sendMessage("@or2@PaePoints can be spent at the Specialty and Untradeables shop in the Edgeville Bank.");
                    DialogueManager.next(player);
                    break;
            }
        } else if (id == THIRD_OPTION_OF_FOUR) {
            if (player.getDialogue() != null) {
                if (player.getDialogue().action(3)) {
                    return;
                }
            }
            if (player.getDialogue() != null) {
                if (player.getDialogue().action(3)) {
                    return;
                }
            }
            switch (player.getDialogueActionId()) {
                case 1023:
                    player.moveTo(new Position(3374, 3889, 0));
                    player.getPacketSender().sendMessage("You successfully move to the Wrath Altar!");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 1022:
                    player.moveTo(new Position(3000, 3933, 0));
                    player.getPacketSender().sendMessage("You successfully moved to the Wilderness Agility Course!");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 1021:
                    player.moveTo(new Position(3070, 3869, 0));
                    player.getPacketSender().sendMessage("You successfully move to Wildywyrm!");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;

                case 1019:
                    if (player.WildyPoints >= 10) {
                        DialogueManager.start(player, 1022);
                        player.setDialogueActionId(1022);
                    } else if (player.WildyPoints < 10) {
                        player.getPacketSender().sendMessage("You need 10 Wildy Points to active the Oddskull.");
                    } else {
                        player.getPacketSender().sendMessage("You must wait up to 10 minutes to activate the oddSkull again.");
                    }
                    break;
                case 5:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getStaffRights() == StaffRights.PLAYER) {
                        player.getPacketSender().sendMessage("You need to be a member to teleport to this zone.").sendMessage("To become a member, visit www.Paescape.com and purchase a scroll.");
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(3424, 2919), player.getSpellbook().getTeleportType());
                    break;
                case 8:
                    LoyaltyProgramme.reset(player);
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 9:
                    ShopManager.getShops().get(41).open(player);
                    break;
                case 14:
                    TeleportHandler.teleportPlayer(player, new Position(1745, 5325), player.getSpellbook().getTeleportType());
                    break;
                case 13:
                    DialogueManager.start(player, 198);
                    player.setDialogueActionId(198);
                    break;
                case 18:
                    TeleportHandler.teleportPlayer(player, new Position(3503, 3562), player.getSpellbook().getTeleportType());
                    break;
                case 26:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSkillManager().getMaxLevel(Skill.AGILITY) < 55) {
                        player.getPacketSender().sendMessage("You need an Agility level of at least level 55 to use this course.");
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(2998, 3914), player.getSpellbook().getTeleportType());
                    break;
                case 27:
                    ClanChatManager.deleteClan(player);
                    break;
                case 28:
                    player.getPacketSender().sendMessage("@blu@5 tasks - 2x PaePoint multiplier.");
                    player.getPacketSender().sendMessage("@blu@10 tasks - 5x PaePoint multiplier.");
                    player.getPacketSender().sendMessage("@blu@25 tasks - 10x PaePoint multiplier and tasks reset.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 31:
                    DialogueManager.start(player, SlayerDialogues.totalPointsReceived(player));
                    break;
                case 41:
                    player.setInputHandling(new DonateToWellGoodwill());
                    player.getPacketSender().sendInterfaceRemoval().sendEnterAmountPrompt("How much money would you like to contribute with?");
                    break;
                case 45:
                    GameMode.set(player, GameMode.ULTIMATE_IRONMAN, false);
                    break;
                case 164:
                    if (player.getMoneyInPouchAsInt() >= 1000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 1000000);
                        player.getPacketSender().sendMessage("1,000,000 coins have been removed from your pouch.");
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getMinigameAttributes().getFarmingBotAttributes().setResourcesLeftToGather(player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToGather() + 250);

                        if (!player.getMinigameAttributes().getFarmingBotAttributes().getHasFarmingBot()) {
                            player.getMinigameAttributes().getFarmingBotAttributes().setHasFarmingBot(true);
                            FarmingBot.start(player);
                        }
                    } else {
                        player.getPacketSender().sendMessage("You must have 1,000,000 coins in your pouch");
                    }
                    break;

                case 165:
                    player.getInventory().delete(15246, 1);
                    player.getInventory().add(579, 1); //helm
                    player.getInventory().add(577, 1); //body
                    player.getInventory().add(1011, 1); //legs
                    player.getInventory().add(558, 500); //mind runes
                    player.getInventory().add(554, 2500); //fire runes
                    player.getInventory().add(1381, 1); //staff
                    player.getInventory().add(995, 500000); //coins
                    player.getInventory().add(385, 20); //food
                    player.getPacketSender().sendMessage("@or2@Complete achievements, slayer tasks, kills bosses, and compete in pvp to gain PaePoints");
                    player.getPacketSender().sendMessage("@or2@PaePoints can be spent at the Specialty and Untradeables shop in the Edgeville Bank.");
                    DialogueManager.next(player);
                    break;
            }
        } else if (id == FOURTH_OPTION_OF_FOUR) {
            if (player.getDialogue() != null) {
                if (player.getDialogue().action(4)) {
                    return;
                }
            }
            switch (player.getDialogueActionId()) {
                case 1023:
                    player.moveTo(new Position(2952, 3850, 0));
                    player.getPacketSender().sendMessage("You successfully move to the Kyatt Hunter Area");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 1022:
                    player.moveTo(new Position(3275, 3659, 0));
                    player.getPacketSender().sendMessage("You successfully moved to Larran's Chest!");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 1021:
                    player.moveTo(new Position(3089, 3840, 0));
                    player.getPacketSender().sendMessage("You successfully move to Tormented Demons!");
                    player.WildyPoints -= 10;
                    player.getPacketSender().sendMessage("You consume 10 Wildy Points by activating the Oddskull.");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;

                case 1019:
                    if (player.WildyPoints >= 10) {
                        DialogueManager.start(player, 1023);
                        player.setDialogueActionId(1023);
                    } else if (player.WildyPoints < 10) {
                        player.getPacketSender().sendMessage("You need 10 Wildy Points to active the Oddskull.");
                    } else {
                        player.getPacketSender().sendMessage("You must wait up to 10 minutes to activate the oddSkull again.");
                    }
                    break;
                case 5:
                case 9:
                case 26:
                case 27:
                case 41:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 8:
                    player.getPacketSender().sendMessage("You currently have " + player.getPointsHandler().getLoyaltyPoints() + " Loyalty points.");
                    ShopManager.getShops().get(73).open(player);
                    break;
                case 13:
                    DialogueManager.start(player, 199);
                    player.setDialogueActionId(199);
                    break;
                case 28:
                    if (player.getSlayer().getSlayerMaster().getPosition() != null) {
                        TeleportHandler.teleportPlayer(player, new Position(player.getSlayer().getSlayerMaster().getPosition().getX(), player.getSlayer().getSlayerMaster().getPosition().getY(), player.getSlayer().getSlayerMaster().getPosition().getZ()), player.getSpellbook().getTeleportType());
                        if (player.getSkillManager().getCurrentLevel(Skill.SLAYER) <= 50)
                            player.getPacketSender().sendMessage("").sendMessage("You can train Slayer with a friend by using a Slayer gem on them.").sendMessage("Slayer gems can be bought from all Slayer masters.");
                    }
                    break;
                case 14:
                    player.setDialogueActionId(14);
                    DialogueManager.start(player, 22);
                    break;
                case 18:
                    DialogueManager.start(player, 25);
                    player.setDialogueActionId(15);
                    break;
                case 30:
                case 31:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSlayer().getDuoPartner() != null) {
                        Slayer.resetDuo(player, World.getPlayerByName(player.getSlayer().getDuoPartner()));
                    }
                    break;
                case 45:
                    player.getPacketSender().sendString(1, "www.Paescape.com");
                    break;
                case 164:
                    if (player.getMoneyInPouchAsInt() >= 1000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 1000000);
                        player.getPacketSender().sendMessage("1,000,000 coins have been removed from your pouch.");
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getMinigameAttributes().getFishingBotAttributes().setResourcesLeftToGather(player.getMinigameAttributes().getFishingBotAttributes().getResourcesLeftToGather() + 500);

                        if (!player.getMinigameAttributes().getFishingBotAttributes().getHasFishingBot()) {
                            player.getMinigameAttributes().getFishingBotAttributes().setHasFishingBot(true);
                            FishingBot.start(player);
                        }
                    } else {
                        player.getPacketSender().sendMessage("You must have 1,000,000 coins in your pouch");
                    }
                    break;

                case 165:
                    player.getInventory().delete(15246, 1);
                    player.getInventory().add(2347, 1); //hammer
                    player.getInventory().add(1267, 1); //pick
                    player.getInventory().add(1349, 1); //axe
                    player.getInventory().add(946, 1); //knife
                    player.getInventory().add(590, 1); //tinderbox
                    player.getInventory().add(1755, 1); //chisel
                    player.getInventory().add(1733, 1); //needle
                    player.getInventory().add(1734, 100); //thread
                    player.getInventory().add(1592, 1); //ring mould
                    player.getInventory().add(1595, 1); //amulet mould
                    player.getInventory().add(1597, 1); //necklace mould
                    player.getInventory().add(11065, 1); //bracelet mould
                    player.getInventory().add(303, 1); //small net
                    player.getInventory().add(305, 1); //big net
                    player.getInventory().add(307, 1); //fishing rod
                    player.getInventory().add(309, 1); //fly fishing rod
                    player.getInventory().add(301, 1); //lobster pot
                    player.getInventory().add(311, 1); //harpoon
                    player.getInventory().add(313, 100); //bait
                    player.getInventory().add(314, 100); //feather
                    player.getInventory().add(995, 500000); //coins
                    player.getPacketSender().sendMessage("@or2@Complete achievements, slayer tasks, kills bosses, and compete in pvp to gain PaePoints");
                    player.getPacketSender().sendMessage("@or2@PaePoints can be spent at the Specialty and Untradeables shop in the Edgeville Bank.");
                    DialogueManager.next(player);
                    break;
            }
        } else if (id == FIRST_OPTION_OF_TWO) {
            if (player.getDialogue() != null) {
                if (player.getDialogue().action(1)) {
                    return;
                }
            }
            switch (player.getDialogueActionId()) {

                case 1051:
                    if (player.seasonalPerks[0] == 1 && !player.Harvester && !player.Producer) {
                        player.Harvester = true;
                        player.getPacketSender().sendMessage("You've unlocked the Harvester perk!");
                        SeasonalPerkInterface.showPerkInterface(player);
                        player.activeInterface = "seasonalperks";
                    }
                    break;
                case 1052:
                    if (player.seasonalPerks[1] == 1 && !player.Contender && !player.Strategist) {
                        player.Contender = true;
                        player.getPacketSender().sendMessage("You've unlocked the Contender perk!");
                        SeasonalPerkInterface.showPerkInterface(player);
                        player.activeInterface = "seasonalperks";
                    }
                    break;
                case 1053:
                    if (player.seasonalPerks[2] == 1 && !player.Gilded && !player.Shoplifter) {
                        player.Gilded = true;
                        player.getPacketSender().sendMessage("You've unlocked the Gilded perk!");
                        SeasonalPerkInterface.showPerkInterface(player);
                        player.activeInterface = "seasonalperks";
                    }
                    break;
                case 1054:
                    if (player.seasonalPerks[3] == 1 && !player.Impulsive && !player.Rapid) {
                        player.Impulsive = true;
                        player.getPacketSender().sendMessage("You've unlocked the Impulsive perk!");
                        SeasonalPerkInterface.showPerkInterface(player);
                        player.activeInterface = "seasonalperks";
                    }
                    break;
                case 1055:
                    if (player.seasonalPerks[4] == 1 && !player.Bloodthirsty && !player.Infernal && player.getInventory().getFreeSlots() > 0) {
                        player.Bloodthirsty = true;
                        player.getPacketSender().sendMessage("You've unlocked the Bloodthirsty perk and received a Slayer's Medallion!");
                        player.getInventory().add(213392, 1);
                        SeasonalPerkInterface.showPerkInterface(player);
                        player.activeInterface = "seasonalperks";
                    } else if (player.getInventory().getFreeSlots() == 0) {
                        player.getPacketSender().sendMessage("You need a free inventory space to unlock this perk!");
                    }
                    break;
                case 1056:
                    if (player.seasonalPerks[5] == 1 && !player.Summoner && !player.Ruinous && player.getInventory().getFreeSlots() > 0) {
                        player.Summoner = true;
                        player.getPacketSender().sendMessage("You've unlocked the Summoner perk and received a Book of Knowledge!");
                        player.getInventory().add(730, 1);
                        SeasonalPerkInterface.showPerkInterface(player);
                        player.activeInterface = "seasonalperks";
                    }
                    break;
                case 1057:
                    if (player.seasonalPerks[6] == 1 && !player.Gladiator && !player.Warlord) {
                        player.Gladiator = true;
                        player.getPacketSender().sendMessage("You've unlocked the Gladiator perk!");
                        SeasonalPerkInterface.showPerkInterface(player);
                        player.activeInterface = "seasonalperks";
                    }
                    break;
                case 1058:
                    if (player.seasonalPerks[7] == 1 && !player.Deathless && !player.Executioner) {
                        player.Deathless = true;
                        player.getPacketSender().sendMessage("You've unlocked the Deathless perk!");
                        SeasonalPerkInterface.showPerkInterface(player);
                        player.activeInterface = "seasonalperks";
                    }
                    break;
                
                
                case 1038:
                    if(player.getMoneyInPouch() >= 1000000000 && player.getInventory().getFreeSlots() > 0) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 1000000000);
                        player.getInventory().add(219476, 1);
                    } else {
                        player.getPacketSender().sendInterfaceRemoval();
                        player.getPacketSender().sendMessage("You need 1 billion coins for this cape.");
                    }

                    break;
                case 312:
                    player.getCofferData();
                    DeathsCoffer.openInter(player);
                    break;
                case 234:
                    player.boxScape = true;
                    StartScreen.handleButton(player, 35769);
                    player.moveTo(GameSettings.DEFAULT_POSITION, false);
                    break;
                case 67:
                case 753:
                    player.getPacketSender().sendInterfaceRemoval();
                    if ((player.getLocation() == Location.RAIDS_LOBBY || player.getLocation() == Location.CRASH_ISLAND)
                            && player.getMinigameAttributes().getRaidsAttributes().getParty() == null) {
                        if (player.getMinigameAttributes().getRaidsAttributes().getPartyInvitation() != null) {
                            player.getMinigameAttributes().getRaidsAttributes().getPartyInvitation().add(player);
                        }
                        player.getMinigameAttributes().getRaidsAttributes().setPartyInvitation(null);
                    }
                    break;
                case 198:
                    player.getPacketSender().sendInterfaceRemoval();

                    if (player.prestige == 0) {
                        if (!player.getUnlockedLoyaltyTitles()[LoyaltyProgramme.LoyaltyTitles.MAXED.ordinal()]) {
                            player.getPacketSender().sendMessage("You must have unlocked the 'Maxed' Loyalty Title in order to buy this cape.");
                            return;
                        }
                    }
                    boolean usePouch = player.getMoneyInPouch() >= 2500000;
                    if (!usePouch && player.getInventory().getAmount(995) < 2500000) {
                        player.getPacketSender().sendMessage("You do not have enough coins.");
                        return;
                    }
                    if (usePouch) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 2500000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                    } else
                        player.getInventory().delete(995, 2500000);
                    player.getInventory().add(213280, 1);
                    player.getPacketSender().sendMessage("You've purchased a Max cape.");
                    break;
                case 199:

                    if (player.getSummoning().getFamiliar() != null) {
                        player.getPacketSender().sendMessage("@red@You can't prestige when something is following you.");

                        if (GameSettings.DISCORD)
                            new MessageBuilder().append(player.getUsername() + " was not able to prestige because they had a familiar or pet.").send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_PRESTIGE_CH).get());

                        return;
                    }

                    if (player.getLocation() == Location.WILDERNESS || player.getCombatBuilder().isBeingAttacked()) {
                        player.getPacketSender().sendMessage("You cannot do this at the moment");

                        if (GameSettings.DISCORD)
                            new MessageBuilder().append(player.getUsername() + " was not able to prestige because they were in combat or in the wilderness.").send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_PRESTIGE_CH).get());

                        return;
                    }

                    if (!player.getEquipment().isNaked(player)) {
                        player.getPacketSender().sendMessage("Please unequip all your items first.");

                        if (GameSettings.DISCORD)
                            new MessageBuilder().append(player.getUsername() + " was not able to prestige because they had items equipped.").send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_PRESTIGE_CH).get());

                        return;
                    }

                    if (player.getInventory().getFreeSlots() <= 5) {
                        player.getPacketSender().sendMessage("You must have 5 empty inventory slots to Prestige.");

                        if (GameSettings.DISCORD)
                            new MessageBuilder().append(player.getUsername() + " was not able to prestige because they did not have 5 inventory slots open.").send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_PRESTIGE_CH).get());

                        return;
                    }

                    CurseHandler.deactivateAll(player);
                    PrayerHandler.deactivateAll(player);

                    Prestige.processPrestige(player);

                    break;
                case 200:
                    EquipmentUpgrades.upgradeItem(player, false);
                    break;
                case 201:
                    EquipmentUpgrades.upgradeItem(player, true);
                    break;
                case 1059:
                    WildyEquipmentUpgrades.upgradeItem(player, false);
                    break;
                case 1060:
                    WildyEquipmentUpgrades.upgradeItem(player, true);
                    break;

                case 215:
                    if ((player.wildAccelerate == 0) && (player.WildyPoints >= 1000)) {
                        player.wildAccelerate = 1;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked wild Accelerate!");
                        player.WildyPoints = player.WildyPoints - 1000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildAccelerate == 1) && (player.WildyPoints >= 2500)) {
                        player.wildAccelerate = 2;
                        player.getPacketSender().sendMessage("@blu@You have just upgraded wild Accelerate!");
                        player.WildyPoints = player.WildyPoints - 2500;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildAccelerate == 2) && (player.WildyPoints >= 5000)) {
                        player.wildAccelerate = 3;
                        player.getPacketSender().sendMessage("@blu@You have just completed wild Accelerate!");
                        player.WildyPoints = player.WildyPoints - 5000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildAccelerate == 0) && (player.WildyPoints < 1000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 1000 Wildy Points to unlock!");
                    } else if ((player.wildAccelerate == 1) && (player.WildyPoints < 2500)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 2500 Wildy Points to unlock!");
                    } else if ((player.wildAccelerate == 2) && (player.WildyPoints < 5000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 5000 Wildy Points to unlock!");
                    } else {
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");
                        player.getPacketSender().sendInterfaceRemoval();
                    }

                    break;

                case 216:
                    if ((player.wildLooter == 0) && (player.WildyPoints >= 1000)) {
                        player.wildLooter = 1;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked wild Looter!");
                        player.WildyPoints = player.WildyPoints - 1000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildLooter == 1) && (player.WildyPoints >= 2500)) {
                        player.wildLooter = 2;
                        player.getPacketSender().sendMessage("@blu@You have just upgraded wild Looter!");
                        player.WildyPoints = player.WildyPoints - 2500;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildLooter == 2) && (player.WildyPoints >= 5000)) {
                        player.wildLooter = 3;
                        player.getPacketSender().sendMessage("@blu@You have just completed wild Looter!");
                        player.WildyPoints = player.WildyPoints - 5000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildLooter == 0) && (player.WildyPoints < 1000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 1000 Wildy Points to unlock!");
                    } else if ((player.wildLooter == 1) && (player.WildyPoints < 2500)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 2500 Wildy Points to unlock!");
                    } else if ((player.wildLooter == 2) && (player.WildyPoints < 5000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 5000 Wildy Points to unlock!");
                    } else {
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");
                        player.getPacketSender().sendInterfaceRemoval();
                    }

                    break;

                case 217:
                    if ((player.wildSpecialist == 0) && (player.WildyPoints >= 1000)) {
                        player.wildSpecialist = 1;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked wild Specialist!");
                        player.WildyPoints = player.WildyPoints - 1000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildSpecialist == 1) && (player.WildyPoints >= 2500)) {
                        player.wildSpecialist = 2;
                        player.getPacketSender().sendMessage("@blu@You have just upgraded wild Specialist!");
                        player.WildyPoints = player.WildyPoints - 2500;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildSpecialist == 2) && (player.WildyPoints >= 5000)) {
                        player.wildSpecialist = 3;
                        player.getPacketSender().sendMessage("@blu@You have just completed wild Specialist!");
                        player.WildyPoints = player.WildyPoints - 5000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildSpecialist == 0) && (player.WildyPoints < 1000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 1000 Wildy Points to unlock!");
                    } else if ((player.wildSpecialist == 1) && (player.WildyPoints < 2500)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 2500 Wildy Points to unlock!");
                    } else if ((player.wildSpecialist == 2) && (player.WildyPoints < 5000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 5000 Wildy Points to unlock!");
                    } else {
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");
                        player.getPacketSender().sendInterfaceRemoval();
                    }

                    break;

                case 218:
                    if ((player.wildEnraged == 0) && (player.WildyPoints >= 1000)) {
                        player.wildEnraged = 1;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked wild Enraged!");
                        player.WildyPoints = player.WildyPoints - 1000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildEnraged == 1) && (player.WildyPoints >= 2500)) {
                        player.wildEnraged = 2;
                        player.getPacketSender().sendMessage("@blu@You have just upgraded wild Enraged!");
                        player.WildyPoints = player.WildyPoints - 2500;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildEnraged == 2) && (player.WildyPoints >= 5000)) {
                        player.wildEnraged = 3;
                        player.getPacketSender().sendMessage("@blu@You have just completed wild Enraged!");
                        player.WildyPoints = player.WildyPoints - 5000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildEnraged == 0) && (player.WildyPoints < 1000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 1000 Wildy Points to unlock!");
                    } else if ((player.wildEnraged == 1) && (player.WildyPoints < 2500)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 2500 Wildy Points to unlock!");
                    } else if ((player.wildEnraged == 2) && (player.WildyPoints < 5000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 5000 Wildy Points to unlock!");
                    } else {
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");
                        player.getPacketSender().sendInterfaceRemoval();
                    }

                    break;

                case 219:
                    if ((player.wildVesta == 0) && (player.WildyPoints >= 1000)) {
                        player.wildVesta = 1;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked wild Vesta!");
                        player.WildyPoints = player.WildyPoints - 1000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildVesta == 1) && (player.WildyPoints >= 2500)) {
                        player.wildVesta = 2;
                        player.getPacketSender().sendMessage("@blu@You have just upgraded wild Vesta!");
                        player.WildyPoints = player.WildyPoints - 2500;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildVesta == 2) && (player.WildyPoints >= 5000)) {
                        player.wildVesta = 3;
                        player.getPacketSender().sendMessage("@blu@You have just completed wild Vesta!");
                        player.WildyPoints = player.WildyPoints - 5000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildVesta == 0) && (player.WildyPoints < 1000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 1000 Wildy Points to unlock!");
                    } else if ((player.wildVesta == 1) && (player.WildyPoints < 2500)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 2500 Wildy Points to unlock!");
                    } else if ((player.wildVesta == 2) && (player.WildyPoints < 5000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 5000 Wildy Points to unlock!");
                    } else {
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");
                        player.getPacketSender().sendInterfaceRemoval();
                    }

                    break;

                case 220:
                    if ((player.wildStatius == 0) && (player.WildyPoints >= 1000)) {
                        player.wildStatius = 1;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked wild Statius!");
                        player.WildyPoints = player.WildyPoints - 1000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildStatius == 1) && (player.WildyPoints >= 2500)) {
                        player.wildStatius = 2;
                        player.getPacketSender().sendMessage("@blu@You have just upgraded wild Statius!");
                        player.WildyPoints = player.WildyPoints - 2500;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildStatius == 2) && (player.WildyPoints >= 5000)) {
                        player.wildStatius = 3;
                        player.getPacketSender().sendMessage("@blu@You have just completed wild Statius!");
                        player.WildyPoints = player.WildyPoints - 5000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildStatius == 0) && (player.WildyPoints < 1000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 1000 Wildy Points to unlock!");
                    } else if ((player.wildStatius == 1) && (player.WildyPoints < 2500)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 2500 Wildy Points to unlock!");
                    } else if ((player.wildStatius == 2) && (player.WildyPoints < 5000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 5000 Wildy Points to unlock!");
                    } else {
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");
                        player.getPacketSender().sendInterfaceRemoval();
                    }

                    break;

                case 221:
                    if ((player.wildMorrigan == 0) && (player.WildyPoints >= 1000)) {
                        player.wildMorrigan = 1;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked wild Morrigan!");
                        player.WildyPoints = player.WildyPoints - 1000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildMorrigan == 1) && (player.WildyPoints >= 2500)) {
                        player.wildMorrigan = 2;
                        player.getPacketSender().sendMessage("@blu@You have just upgraded wild Morrigan!");
                        player.WildyPoints = player.WildyPoints - 2500;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildMorrigan == 2) && (player.WildyPoints >= 5000)) {
                        player.wildMorrigan = 3;
                        player.getPacketSender().sendMessage("@blu@You have just completed wild Morrigan!");
                        player.WildyPoints = player.WildyPoints - 5000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildMorrigan == 0) && (player.WildyPoints < 1000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 1000 Wildy Points to unlock!");
                    } else if ((player.wildMorrigan == 1) && (player.WildyPoints < 2500)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 2500 Wildy Points to unlock!");
                    } else if ((player.wildMorrigan == 2) && (player.WildyPoints < 5000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 5000 Wildy Points to unlock!");
                    } else {
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");
                        player.getPacketSender().sendInterfaceRemoval();
                    }

                    break;

                case 222:
                    if ((player.wildZuriel == 0) && (player.WildyPoints >= 1000)) {
                        player.wildZuriel = 1;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked wild Zuriel!");
                        player.WildyPoints = player.WildyPoints - 1000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildZuriel == 1) && (player.WildyPoints >= 2500)) {
                        player.wildZuriel = 2;
                        player.getPacketSender().sendMessage("@blu@You have just upgraded wild Zuriel!");
                        player.WildyPoints = player.WildyPoints - 2500;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildZuriel == 2) && (player.WildyPoints >= 5000)) {
                        player.wildZuriel = 3;
                        player.getPacketSender().sendMessage("@blu@You have just completed wild Zuriel!");
                        player.WildyPoints = player.WildyPoints - 5000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildZuriel == 0) && (player.WildyPoints < 1000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 1000 Wildy Points to unlock!");
                    } else if ((player.wildZuriel == 1) && (player.WildyPoints < 2500)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 2500 Wildy Points to unlock!");
                    } else if ((player.wildZuriel == 2) && (player.WildyPoints < 5000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 5000 Wildy Points to unlock!");
                    } else {
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");
                        player.getPacketSender().sendInterfaceRemoval();
                    }

                    break;

                case 223:
                    if ((player.wildSavior == 0) && (player.WildyPoints >= 1000)) {
                        player.wildSavior = 1;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked wild Savior!");
                        player.WildyPoints = player.WildyPoints - 1000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildSavior == 1) && (player.WildyPoints >= 2500)) {
                        player.wildSavior = 2;
                        player.getPacketSender().sendMessage("@blu@You have just upgraded wild Savior!");
                        player.WildyPoints = player.WildyPoints - 2500;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildSavior == 2) && (player.WildyPoints >= 5000)) {
                        player.wildSavior = 3;
                        player.getPacketSender().sendMessage("@blu@You have just completed wild Savior!");
                        player.WildyPoints = player.WildyPoints - 5000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildSavior == 0) && (player.WildyPoints < 1000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 1000 Wildy Points to unlock!");
                    } else if ((player.wildSavior == 1) && (player.WildyPoints < 2500)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 2500 Wildy Points to unlock!");
                    } else if ((player.wildSavior == 2) && (player.WildyPoints < 5000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 5000 Wildy Points to unlock!");
                    } else {
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");
                        player.getPacketSender().sendInterfaceRemoval();
                    }

                    break;

                case 224:
                    if ((player.wildAlchemize == 0) && (player.WildyPoints >= 1000)) {
                        player.wildAlchemize = 1;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked wild Alchemize!");
                        player.WildyPoints = player.WildyPoints - 1000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildAlchemize == 1) && (player.WildyPoints >= 2500)) {
                        player.wildAlchemize = 2;
                        player.getPacketSender().sendMessage("@blu@You have just upgraded wild Alchemize!");
                        player.WildyPoints = player.WildyPoints - 2500;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildAlchemize == 2) && (player.WildyPoints >= 5000)) {
                        player.wildAlchemize = 3;
                        player.getPacketSender().sendMessage("@blu@You have just completed wild Alchemize!");
                        player.WildyPoints = player.WildyPoints - 5000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildAlchemize == 0) && (player.WildyPoints < 1000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 1000 Wildy Points to unlock!");
                    } else if ((player.wildAlchemize == 1) && (player.WildyPoints < 2500)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 2500 Wildy Points to unlock!");
                    } else if ((player.wildAlchemize == 2) && (player.WildyPoints < 5000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 5000 Wildy Points to unlock!");
                    } else {
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");
                        player.getPacketSender().sendInterfaceRemoval();
                    }

                    break;

                case 232:
                    if ((player.wildTainted == 0) && (player.WildyPoints >= 5000)) {
                        player.wildTainted = 1;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked wild Tainted!");
                        player.WildyPoints = player.WildyPoints - 5000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildTainted == 0) && (player.WildyPoints < 5000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 5000 Wildy Points to unlock!");
                    } else {
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");
                        player.getPacketSender().sendInterfaceRemoval();
                    }

                    break;

                case 233:
                    if ((player.wildBotanist == 0) && (player.WildyPoints >= 5000)) {
                        player.wildBotanist = 1;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked wild Botanist!");
                        player.WildyPoints = player.WildyPoints - 5000;
                        InformationPanel.refreshPanel(player);
                        WildernessPerkInterface.showPerkInterface(player);
                    } else if ((player.wildBotanist == 0) && (player.WildyPoints < 5000)) {
                        player.getPacketSender().sendMessage("@red@This perk costs 5000 Wildy Points to unlock!");
                    } else {
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");
                        player.getPacketSender().sendInterfaceRemoval();
                    }

                    break;

                case 225:

                    player.getPacketSender().sendInterfaceRemoval();

                    if (player.coxRaidBonus == 5) {
                        player.getPacketSender().sendMessage("@red@You have the maximum amount of upgrades.");
                        return;
                    }

                    boolean useMoneyPouch = player.getMoneyInPouch() >= 500000000;
                    if (!useMoneyPouch && player.getInventory().getAmount(995) < 500000000) {
                        player.getPacketSender().sendMessage("You do not have enough coins.");
                        return;
                    }
                    if (useMoneyPouch) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 500000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                    } else
                        player.getInventory().delete(995, 500000000);

                    player.coxRaidBonus++;

                    player.getPacketSender().sendMessage("You've purchased the bonus CoX drop rate upgrade.");

                    String commandLog = "[" + Misc.getCurrentServerTime() + "] Username: " + player.getUsername() + " purchased CoX Drop Rate upgrade";
                    PlayerLogs.log("Drop Rate", commandLog);

                    break;

                case 226:

                    player.getPacketSender().sendInterfaceRemoval();

                    if (player.tobRaidBonus == 5) {
                        player.getPacketSender().sendMessage("@red@You have the maximum amount of upgrades.");
                        return;
                    }

                    useMoneyPouch = player.getMoneyInPouch() >= 500000000;
                    if (!useMoneyPouch && player.getInventory().getAmount(995) < 500000000) {
                        player.getPacketSender().sendMessage("You do not have enough coins.");
                        return;
                    }
                    if (useMoneyPouch) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 500000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                    } else
                        player.getInventory().delete(995, 500000000);

                    player.tobRaidBonus++;

                    player.getPacketSender().sendMessage("You've purchased the bonus ToB drop rate upgrade.");

                    commandLog = "[" + Misc.getCurrentServerTime() + "] Username: " + player.getUsername() + " purchased ToB Drop Rate upgrade";
                    PlayerLogs.log("Drop Rate", commandLog);

                    break;

                case 227:

                    player.getPacketSender().sendInterfaceRemoval();

                    if (player.chaosRaidBonus == 5) {
                        player.getPacketSender().sendMessage("@red@You have the maximum amount of upgrades.");
                        return;
                    }

                    useMoneyPouch = player.getMoneyInPouch() >= 500000000;
                    if (!useMoneyPouch && player.getInventory().getAmount(995) < 500000000) {
                        player.getPacketSender().sendMessage("You do not have enough coins.");
                        return;
                    }
                    if (useMoneyPouch) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 500000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                    } else
                        player.getInventory().delete(995, 500000000);

                    player.chaosRaidBonus++;

                    player.getPacketSender().sendMessage("You've purchased the bonus Chaos Raid drop rate upgrade.");

                    commandLog = "[" + Misc.getCurrentServerTime() + "] Username: " + player.getUsername() + " purchased Chaos Raid Drop Rate upgrade";
                    PlayerLogs.log("Drop Rate", commandLog);

                    break;

                case 228:

                    player.getPacketSender().sendInterfaceRemoval();

                    if (player.gwdRaidBonus == 5) {
                        player.getPacketSender().sendMessage("@red@You have the maximum amount of upgrades.");
                        return;
                    }

                    useMoneyPouch = player.getMoneyInPouch() >= 500000000;
                    if (!useMoneyPouch && player.getInventory().getAmount(995) < 500000000) {
                        player.getPacketSender().sendMessage("You do not have enough coins.");
                        return;
                    }
                    if (useMoneyPouch) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 500000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                    } else
                        player.getInventory().delete(995, 500000000);

                    player.gwdRaidBonus++;

                    player.getPacketSender().sendMessage("You've purchased the bonus GWD Raid drop rate upgrade.");

                    commandLog = "[" + Misc.getCurrentServerTime() + "] Username: " + player.getUsername() + " purchased GWD Raid Drop Rate upgrade";
                    PlayerLogs.log("Drop Rate", commandLog);
                    break;

                case 231:

                    player.getPacketSender().sendInterfaceRemoval();

                    if (player.shrRaidBonus == 5) {
                        player.getPacketSender().sendMessage("@red@You have the maximum amount of upgrades.");
                        return;
                    }

                    useMoneyPouch = player.getMoneyInPouch() >= 500000000;
                    if (!useMoneyPouch && player.getInventory().getAmount(995) < 500000000) {
                        player.getPacketSender().sendMessage("You do not have enough coins.");
                        return;
                    }
                    if (useMoneyPouch) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 500000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                    } else
                        player.getInventory().delete(995, 500000000);

                    player.shrRaidBonus++;

                    player.getPacketSender().sendMessage("You've purchased the bonus SHR Raid drop rate upgrade.");

                    commandLog = "[" + Misc.getCurrentServerTime() + "] Username: " + player.getUsername() + " purchased SHR Raid Drop Rate upgrade";
                    PlayerLogs.log("Drop Rate", commandLog);
                    break;

                case 310:
                    if (player.getRaidsParty() != null) {
                        for (Player member : player.getRaidsParty().getOwner().getRaidsParty().getPlayers()) {
                            member.seasonalLeader = member.getRaidsParty().getOwner().getUsername();
                            member.moveTo(GameSettings.DEFAULT_POSITION);
                        }
                    } else
                        player.sendMessage("You must join a party then talk to Waydar to move home.");
                    break;
                case 3:
                    ShopManager.getShops().get(22).open(player);
                    break;
                case 4:
                    SummoningTab.handleDismiss(player, true);
                    break;
                case 709:
                    player.getPacketSender().sendInterfaceRemoval();
                    Decanting.checkRequirements(player);
                    break;
                case 7:
                    BankPin.init(player, false);
                    break;
                case 8:
                    BankPin.deletePin(player);
                    break;
                case 16:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getMinigameAttributes().getBarrowsMinigameAttributes().getKillcount() < 5) {
                        player.getPacketSender().sendMessage("You must have a killcount of at least 5 to enter the tunnel.");
                        return;
                    }
                    player.moveTo(new Position(3552, 9692));
                    break;
                case 20:
                    player.getPacketSender().sendInterfaceRemoval();
                    DialogueManager.start(player, 39);
                    player.getMinigameAttributes().getRecipeForDisasterAttributes().setPartFinished(0, true);
                    PlayerPanel.refreshPanel(player);
                    break;
                case 23:
                    DialogueManager.start(player, 50);
                    player.getMinigameAttributes().getNomadAttributes().setPartFinished(0, true);
                    player.setDialogueActionId(24);
                    PlayerPanel.refreshPanel(player);
                    break;
                case 24:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 33:
                    if (player.getPaePoints() < 5) {
                        player.getPacketSender().sendMessage("You need at least 5 points to reset your task!");
                        return;
                    } else {
                        player.getPacketSender().sendInterfaceRemoval();
                        player.getSlayer().resetSlayerTask();
                    }
                    break;
                case 34:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getSlayer().handleInvitation(true);
                    break;
                case 37:
                    TeleportHandler.teleportPlayer(player, new Position(2961, 3882), player.getSpellbook().getTeleportType());
                    break;
                case 39:
                    TeleportHandler.teleportPlayer(player, new Position(3281, 3914), player.getSpellbook().getTeleportType());
                    break;

                case 42:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getInteractingObject() != null && player.getInteractingObject().getDefinition() != null && player.getInteractingObject().getDefinition().getName().equalsIgnoreCase("flowers")) {
                        if (CustomObjects.objectExists(player.getInteractingObject().getPosition())) {
                            player.getInventory().add(FlowersData.forObject(player.getInteractingObject().getId()).itemId, 1);
                            CustomObjects.deleteGlobalObject(player.getInteractingObject());
                            player.setInteractingObject(null);
                        }
                    }
                    break;
                case 44:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getMinigameAttributes().getGodwarsDungeonAttributes().setHasEnteredRoom(true);
                    player.moveTo(new Position(2911, 5203));
                    player.getPacketSender().sendMessage("You enter Nex's lair..");
                    NpcAggression.target(player);
                    break;
                case 46:
                    player.getPacketSender().sendInterfaceRemoval();
                    DialogueManager.start(player, 82);
                    player.setPlayerLocked(true).setDialogueActionId(45);
                    break;
                case 57:
                    //Graveyard.start(player);
                    break;
                case 66:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() == null) {
                        if (player.getMinigameAttributes().getDungeoneeringAttributes().getPartyInvitation() != null) {
                            player.getMinigameAttributes().getDungeoneeringAttributes().getPartyInvitation().add(player);
                        }
                    }
                    player.getMinigameAttributes().getDungeoneeringAttributes().setPartyInvitation(null);
                    break;
                case 71:
                    if (player.getClickDelay().elapsed(1000)) {
                        if (Dungeoneering.doingDungeoneering(player)) {
                            Dungeoneering.leave(player, false, true);
                            player.getClickDelay().reset();
                        }
                    }
                    break;
                case 72:
                    if (player.getClickDelay().elapsed(1000)) {
                        if (Dungeoneering.doingDungeoneering(player)) {
                            Dungeoneering.leave(player, false, player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner() != player);
                            player.getClickDelay().reset();
                        }
                    }
                    break;
                case 73:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.moveTo(new Position(3653, player.getPosition().getY()));
                    break;
                case 74:
                    player.getPacketSender().sendMessage("The ghost teleports you away.");
                    player.getPacketSender().sendInterfaceRemoval();
                    player.moveTo(new Position(3651, 3486));
                    break;
                case 76:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getStaffRights().isStaff()) {
                        player.getPacketSender().sendMessage("You cannot change your rank.");
                        return;
                    }
                    //player.setStaffRights(PlayerRights.YOUTUBER);
                    player.getPacketSender().sendRights();
                    break;
                case 78:
                    player.getPacketSender().sendString(38006, "Select a skill...").sendString(38090, "Which skill would you like to prestige?");
                    player.getPacketSender().sendInterface(38000);
                    player.setUsableObject(new Object[2]).setUsableObject(0, "prestige");
                    break;

                //Perks
                case 79:
                    Perks.processPerk(player, "Berserker");
                    break;
                case 80:
                    Perks.processPerk(player, "Bullseye");
                    break;
                case 81:
                    Perks.processPerk(player, "Prophetic");
                    break;
                case 82:
                    Perks.processPerk(player, "Vampirism");
                    break;
                case 83:
                    Perks.processPerk(player, "Survivalist");
                    break;
                case 84:
                    Perks.processPerk(player, "Accelerate");
                    break;
                case 85:
                    Perks.processPerk(player, "Lucky");
                    break;
                case 86:
                    Perks.processPerk(player, "Prodigy");
                    break;
                case 87:
                    Perks.processPerk(player, "Unnatural");
                    break;
                case 88:
                    Perks.processPerk(player, "Artisan");
                    break;

                //Achievement Abilities
                case 313:
                    AchievementAbilities.processAbility(player, "banker");
                    break;
                case 314:
                    AchievementAbilities.processAbility(player, "processor");
                    break;
                case 315:
                    AchievementAbilities.processAbility(player, "gatherer");
                    break;
                case 316:
                    AchievementAbilities.processAbility(player, "sweeten");
                    break;
                case 317:
                    AchievementAbilities.processAbility(player, "entertainer");
                    break;
                case 318:
                    AchievementAbilities.processAbility(player, "combatant");
                    break;
                case 319:
                    AchievementAbilities.processAbility(player, "cryptic");
                    break;
                case 320:
                    AchievementAbilities.processAbility(player, "gambler");
                    break;
                case 321:
                    AchievementAbilities.processAbility(player, "prosperous");
                    break;
                case 322:
                    AchievementAbilities.processAbility(player, "bountiful");
                    break;

                //Mastery Perks

                case 111:
                    if ((player.Detective == 0) && ((player.completedLogs / 4) > player.masteryPerksUnlocked) && player.unlockedMasteryPerks(player)) {
                        player.Detective = 1;
                        player.masteryPerksUnlocked++;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked Detective!");
                        MasteryPerkInterface.showPerkInterface(player);
                        break;
                    } else if (player.Detective > 0)
                        player.getPacketSender().sendMessage("@red@You've already unlocked this perk!");

                    else if (!player.unlockedMasteryPerks(player))
                        player.getPacketSender().sendMessage("@red@You need to unlock all Perks and Prestige Perks to unlock Mastery Perks!");

                    else if ((player.completedLogs / 4) <= player.masteryPerksUnlocked)
                        player.getPacketSender().sendMessage("@red@You need to complete more Collection Logs to unlock more Mastery Perks!");

                    else
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");

                    player.getPacketSender().sendInterfaceRemoval();

                    break;

                case 112:
                    if ((player.Critical == 0) && ((player.completedLogs / 4) > player.masteryPerksUnlocked) && player.unlockedMasteryPerks(player)) {
                        player.Critical = 1;
                        player.masteryPerksUnlocked++;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked Critical!");
                        MasteryPerkInterface.showPerkInterface(player);
                        break;
                    } else if (player.Critical > 0)
                        player.getPacketSender().sendMessage("@red@You've already unlocked this perk!");

                    else if (!player.unlockedMasteryPerks(player))
                        player.getPacketSender().sendMessage("@red@You need to unlock all Perks and Prestige Perks to unlock Mastery Perks!");

                    else if ((player.completedLogs / 4) <= player.masteryPerksUnlocked)
                        player.getPacketSender().sendMessage("@red@You need to complete more Collection Logs to unlock more Mastery Perks!");

                    else
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");

                    player.getPacketSender().sendInterfaceRemoval();

                    break;

                case 113:
                    if ((player.Flurry == 0) && ((player.completedLogs / 4) > player.masteryPerksUnlocked) && player.unlockedMasteryPerks(player)) {
                        player.Flurry = 1;
                        player.masteryPerksUnlocked++;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked Flurry!");
                        MasteryPerkInterface.showPerkInterface(player);
                        break;
                    } else if (player.Flurry > 0)
                        player.getPacketSender().sendMessage("@red@You've already unlocked this perk!");

                    else if (!player.unlockedMasteryPerks(player))
                        player.getPacketSender().sendMessage("@red@You need to unlock all Perks and Prestige Perks to unlock Mastery Perks!");

                    else if ((player.completedLogs / 4) <= player.masteryPerksUnlocked)
                        player.getPacketSender().sendMessage("@red@You need to complete more Collection Logs to unlock more Mastery Perks!");

                    else
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");

                    player.getPacketSender().sendInterfaceRemoval();

                    break;

                case 114:
                    if ((player.Consistent == 0) && ((player.completedLogs / 4) > player.masteryPerksUnlocked) && player.unlockedMasteryPerks(player)) {
                        player.Consistent = 1;
                        player.masteryPerksUnlocked++;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked Consistent!");
                        MasteryPerkInterface.showPerkInterface(player);
                        break;
                    } else if (player.Consistent > 0)
                        player.getPacketSender().sendMessage("@red@You've already unlocked this perk!");

                    else if (!player.unlockedMasteryPerks(player))
                        player.getPacketSender().sendMessage("@red@You need to unlock all Perks and Prestige Perks to unlock Mastery Perks!");

                    else if ((player.completedLogs / 4) <= player.masteryPerksUnlocked)
                        player.getPacketSender().sendMessage("@red@You need to complete more Collection Logs to unlock more Mastery Perks!");

                    else
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");

                    player.getPacketSender().sendInterfaceRemoval();

                    break;

                case 115:
                    if ((player.Dexterity == 0) && ((player.completedLogs / 4) > player.masteryPerksUnlocked) && player.unlockedMasteryPerks(player)) {
                        player.Dexterity = 1;
                        player.masteryPerksUnlocked++;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked Dexterity!");
                        MasteryPerkInterface.showPerkInterface(player);
                        break;
                    } else if (player.Dexterity > 0)
                        player.getPacketSender().sendMessage("@red@You've already unlocked this perk!");

                    else if (!player.unlockedMasteryPerks(player))
                        player.getPacketSender().sendMessage("@red@You need to unlock all Perks and Prestige Perks to unlock Mastery Perks!");

                    else if ((player.completedLogs / 4) <= player.masteryPerksUnlocked)
                        player.getPacketSender().sendMessage("@red@You need to complete more Collection Logs to unlock more Mastery Perks!");

                    player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");

                    if (player.Dexterity == 0)
                        player.getPacketSender().sendInterfaceRemoval();

                    break;

                case 116:
                    if ((player.Stability == 0) && ((player.completedLogs / 4) > player.masteryPerksUnlocked) && player.unlockedMasteryPerks(player)) {
                        player.Stability = 1;
                        player.masteryPerksUnlocked++;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked Stability!");
                        MasteryPerkInterface.showPerkInterface(player);
                        break;
                    } else if (player.Stability > 0)
                        player.getPacketSender().sendMessage("@red@You've already unlocked this perk!");

                    else if (!player.unlockedMasteryPerks(player))
                        player.getPacketSender().sendMessage("@red@You need to unlock all Perks and Prestige Perks to unlock Mastery Perks!");

                    else if ((player.completedLogs / 4) <= player.masteryPerksUnlocked)
                        player.getPacketSender().sendMessage("@red@You need to complete more Collection Logs to unlock more Mastery Perks!");

                    player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");

                    if (player.Stability == 0)
                        player.getPacketSender().sendInterfaceRemoval();

                    break;

                case 117:
                    if ((player.Absorb == 0) && ((player.completedLogs / 4) > player.masteryPerksUnlocked) && player.unlockedMasteryPerks(player)) {
                        player.Absorb = 1;
                        player.masteryPerksUnlocked++;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked Absorb!");
                        MasteryPerkInterface.showPerkInterface(player);
                        break;
                    } else if (player.Absorb > 0)
                        player.getPacketSender().sendMessage("@red@You've already unlocked this perk!");

                    else if (!player.unlockedMasteryPerks(player))
                        player.getPacketSender().sendMessage("@red@You need to unlock all Perks and Prestige Perks to unlock Mastery Perks!");

                    else if ((player.completedLogs / 4) <= player.masteryPerksUnlocked)
                        player.getPacketSender().sendMessage("@red@You need to complete more Collection Logs to unlock more Mastery Perks!");

                    else
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");

                    player.getPacketSender().sendInterfaceRemoval();

                    break;

                case 118:
                    if ((player.Efficiency == 0) && ((player.completedLogs / 4) > player.masteryPerksUnlocked) && player.unlockedMasteryPerks(player)) {
                        player.Efficiency = 1;
                        player.masteryPerksUnlocked++;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked Efficiency!");
                        MasteryPerkInterface.showPerkInterface(player);
                        break;
                    } else if (player.Efficiency > 0)
                        player.getPacketSender().sendMessage("@red@You've already unlocked this perk!");

                    else if (!player.unlockedMasteryPerks(player))
                        player.getPacketSender().sendMessage("@red@You need to unlock all Perks and Prestige Perks to unlock Mastery Perks!");

                    else if ((player.completedLogs / 4) <= player.masteryPerksUnlocked)
                        player.getPacketSender().sendMessage("@red@You need to complete more Collection Logs to unlock more Mastery Perks!");

                    else
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");

                    player.getPacketSender().sendInterfaceRemoval();

                    break;

                case 119:
                    if ((player.Efficacy == 0) && ((player.completedLogs / 4) > player.masteryPerksUnlocked) && player.unlockedMasteryPerks(player)) {
                        player.Efficacy = 1;
                        player.masteryPerksUnlocked++;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked Efficacy!");
                        MasteryPerkInterface.showPerkInterface(player);
                        break;
                    } else if (player.Efficacy > 0)
                        player.getPacketSender().sendMessage("@red@You've already unlocked this perk!");

                    else if (!player.unlockedMasteryPerks(player))
                        player.getPacketSender().sendMessage("@red@You need to unlock all Perks and Prestige Perks to unlock Mastery Perks!");

                    else if ((player.completedLogs / 4) <= player.masteryPerksUnlocked)
                        player.getPacketSender().sendMessage("@red@You need to complete more Collection Logs to unlock more Mastery Perks!");

                    else
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");

                    player.getPacketSender().sendInterfaceRemoval();

                    break;

                case 120:
                    if ((player.Devour == 0) && ((player.completedLogs / 4) > player.masteryPerksUnlocked) && player.unlockedMasteryPerks(player)) {
                        player.Devour = 1;
                        player.masteryPerksUnlocked++;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked Devour!");
                        MasteryPerkInterface.showPerkInterface(player);
                        break;
                    } else if (player.Devour > 0)
                        player.getPacketSender().sendMessage("@red@You've already unlocked this perk!");

                    else if (!player.unlockedMasteryPerks(player))
                        player.getPacketSender().sendMessage("@red@You need to unlock all Perks and Prestige Perks to unlock Mastery Perks!");

                    else if ((player.completedLogs / 4) <= player.masteryPerksUnlocked)
                        player.getPacketSender().sendMessage("@red@You need to complete more Collection Logs to unlock more Mastery Perks!");

                    else
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");

                    player.getPacketSender().sendInterfaceRemoval();

                    break;

                case 121:
                    if ((player.Wealthy == 0) && ((player.completedLogs / 4) > player.masteryPerksUnlocked) && player.unlockedMasteryPerks(player)) {
                        player.Wealthy = 1;
                        player.masteryPerksUnlocked++;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked Wealthy!");
                        MasteryPerkInterface.showPerkInterface(player);
                        break;
                    } else if (player.Wealthy > 0)
                        player.getPacketSender().sendMessage("@red@You've already unlocked this perk!");

                    else if (!player.unlockedMasteryPerks(player))
                        player.getPacketSender().sendMessage("@red@You need to unlock all Perks and Prestige Perks to unlock Mastery Perks!");

                    else if ((player.completedLogs / 4) <= player.masteryPerksUnlocked)
                        player.getPacketSender().sendMessage("@red@You need to complete more Collection Logs to unlock more Mastery Perks!");

                    else
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");

                    player.getPacketSender().sendInterfaceRemoval();

                    break;

                case 122:
                    if ((player.Reflect == 0) && ((player.completedLogs / 4) > player.masteryPerksUnlocked) && player.unlockedMasteryPerks(player)) {
                        player.Reflect = 1;
                        player.masteryPerksUnlocked++;
                        player.getPacketSender().sendMessage("@blu@You have just unlocked Reflect!");
                        MasteryPerkInterface.showPerkInterface(player);
                        break;
                    } else if (player.Reflect > 0)
                        player.getPacketSender().sendMessage("@red@You've already unlocked this perk!");

                    else if (!player.unlockedMasteryPerks(player))
                        player.getPacketSender().sendMessage("@red@You need to unlock all Perks and Prestige Perks to unlock Mastery Perks!");

                    else if ((player.completedLogs / 4) <= player.masteryPerksUnlocked)
                        player.getPacketSender().sendMessage("@red@You need to complete more Collection Logs to unlock more Mastery Perks!");

                    else
                        player.getPacketSender().sendMessage("@red@You do not meet the requirements to upgrade this perk.");

                    player.getPacketSender().sendInterfaceRemoval();

                    break;


                case 42069:
                    DonationBonds.claimReward(player, 1);
                    break;

            }
        } else if (id == SECOND_OPTION_OF_TWO) {
            if (player.getDialogue() != null) {
                if (player.getDialogue().action(2)) {
                    return;
                }
            }
            switch (player.getDialogueActionId()) {

                case 1051:
                    if (player.seasonalPerks[0] == 1 && !player.Harvester && !player.Producer) {
                        player.Producer = true;
                        player.getPacketSender().sendMessage("You've unlocked the Producer perk!");
                        SeasonalPerkInterface.showPerkInterface(player);
                        player.activeInterface = "seasonalperks";
                    }
                    break;
                case 1052:
                    if (player.seasonalPerks[1] == 1 && !player.Contender && !player.Strategist) {
                        player.Strategist = true;
                        player.getPacketSender().sendMessage("You've unlocked the Strategist perk!");
                        SeasonalPerkInterface.showPerkInterface(player);
                        player.activeInterface = "seasonalperks";
                    }
                    break;
                case 1053:
                    if (player.seasonalPerks[2] == 1 && !player.Gilded && !player.Shoplifter) {
                        player.Shoplifter = true;
                        player.getPacketSender().sendMessage("You've unlocked the Shoplifter perk!");
                        SeasonalPerkInterface.showPerkInterface(player);
                        player.activeInterface = "seasonalperks";
                    }
                    break;
                case 1054:
                    if (player.seasonalPerks[3] == 1 && !player.Impulsive && !player.Rapid) {
                        player.Rapid = true;
                        player.getPacketSender().sendMessage("You've unlocked the Rapid perk!");
                        SeasonalPerkInterface.showPerkInterface(player);
                        player.activeInterface = "seasonalperks";
                    }
                    break;
                case 1055:
                    if (player.seasonalPerks[4] == 1 && !player.Bloodthirsty && !player.Infernal && player.getInventory().getFreeSlots() > 0) {
                        player.Infernal = true;
                        player.getPacketSender().sendMessage("You've unlocked the Infernal perk and received an Inferno Adze!");
                        player.getInventory().add(13661, 1);
                        SeasonalPerkInterface.showPerkInterface(player);
                        player.activeInterface = "seasonalperks";
                    } else if (player.getInventory().getFreeSlots() == 0) {
                        player.getPacketSender().sendMessage("You need a free inventory space to unlock this perk!");
                    }
                    break;
                case 1056:
                    if (player.seasonalPerks[5] == 1 && !player.Summoner && !player.Ruinous && player.getInventory().getFreeSlots() > 0) {
                        player.Ruinous = true;
                        player.getPacketSender().sendMessage("You've unlocked the Ruinous perk and a Book of Faith!");
                        player.getInventory().add(600, 1);
                        SeasonalPerkInterface.showPerkInterface(player);
                        player.activeInterface = "seasonalperks";
                    }
                    break;
                case 1057:
                    if (player.seasonalPerks[6] == 1 && !player.Gladiator && !player.Warlord) {
                        player.Warlord = true;
                        player.getPacketSender().sendMessage("You've unlocked the Warlord perk!");
                        SeasonalPerkInterface.showPerkInterface(player);
                        player.activeInterface = "seasonalperks";
                    }
                    break;
                case 1058:
                    if (player.seasonalPerks[7] == 1 && !player.Deathless && !player.Executioner) {
                        player.Executioner = true;
                        player.getPacketSender().sendMessage("You've unlocked the Executioner perk!");
                        SeasonalPerkInterface.showPerkInterface(player);
                        player.activeInterface = "seasonalperks";
                    }
                    break;

                case 234:
                    player.boxScape = false;
                    StartScreen.handleButton(player, 35769);
                    player.moveTo(GameSettings.DEFAULT_POSITION, false);
                    break;

                case 3:
                    ShopManager.getShops().get(23).open(player);
                    break;
                case 4:
                case 16:
                case 20:
                case 23:
                case 33:
                case 37:
                case 39:
                case 42:
                case 44:
                case 46:
                case 57:
                case 71:
                case 72:
                case 73:
                case 74:
                case 76:
                case 78:
                case 709:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 7:
                case 8:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN)
                        player.getUimBank().open();
                    else
                        player.getBank(player.getCurrentBankTab()).open();
                    break;
                case 24:
                    Nomad.startFight(player);
                    break;
                case 34:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getSlayer().handleInvitation(false);
                    break;
                case 66:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getMinigameAttributes().getDungeoneeringAttributes().getPartyInvitation() != null && player.getMinigameAttributes().getDungeoneeringAttributes().getPartyInvitation().getOwner() != null)
                        player.getMinigameAttributes().getDungeoneeringAttributes().getPartyInvitation().getOwner().getPacketSender().sendMessage(player.getUsername() + " has declined your invitation.");
                    player.getMinigameAttributes().getDungeoneeringAttributes().setPartyInvitation(null);
                    break;
                case 312:
                    //player.getCofferData();
                    //DeathsCoffer.createUpdatedBlankCofferSaveFile(player);
                    //TODO
                    player.getPacketSender().sendMessage("This feature is not ready yet.");
                    break;
                case 42069:
                    DonationBonds.claimReward(player, 2);
                    break;
                default:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;

            }
        } else if (id == FIRST_OPTION_OF_THREE) {
            if (player.getDialogue() != null) {
                if (player.getDialogue().action(1)) {
                    return;
                }
            }
            switch (player.getDialogueActionId()) {
                case 1035:
                    player.getBingo().reroll(true, 995, 100_000_000);
                    break;
                case 5:
                    DialogueManager.start(player, DonationBonds.getTotalFunds(player));
                    break;
                case 10:
                    player.getPacketSender().sendMessage("You sell your Artifacts for Coins.");
                    Artifacts.sellArtifacts(player);
                    break;
                case 15:
                    DialogueManager.start(player, 35);
                    player.setDialogueActionId(19);
                    break;
                case 21:
                    TeleportHandler.teleportPlayer(player, new Position(3080, 3498), player.getSpellbook().getTeleportType());
                    break;
                case 22:
                    TeleportHandler.teleportPlayer(player, new Position(1891, 3177), player.getSpellbook().getTeleportType());
                    break;
                case 25:
                    TeleportHandler.teleportPlayer(player, new Position(2589, 4319), TeleportType.PURO_PURO);
                    break;
                case 35:
                    player.getPacketSender().sendEnterAmountPrompt("How many shards would you like to buy? (You can use K, M, B prefixes)");
                    player.setInputHandling(new BuyShards());
                    break;
                case 41:
                    TeleportHandler.teleportPlayer(player, new Position(2884 + RandomUtility.inclusiveRandom(1), 4374 + RandomUtility.inclusiveRandom(1)), player.getSpellbook().getTeleportType());
                    break;
                case 43:
                    TeleportHandler.teleportPlayer(player, new Position(2773, 9195), player.getSpellbook().getTeleportType());
                    break;
                case 47:
                    TeleportHandler.teleportPlayer(player, new Position(2978, 3237), player.getSpellbook().getTeleportType());
                    break;
                case 48:
                    if (player.getInteractingObject() != null) {
                        Mining.startMining(player, new GameObject(24444, player.getInteractingObject().getPosition()));
                    }
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 56:
                    TeleportHandler.teleportPlayer(player, new Position(2717, 3499), player.getSpellbook().getTeleportType());
                    break;
                case 58:
                    DialogueManager.start(player, AgilityTicketExchange.getDialogue(player));
                    break;
                case 61:
                    CharmingImp.changeConfig(player, 0, 0);
                    break;
                case 62:
                    CharmingImp.changeConfig(player, 1, 0);
                    break;
                case 63:
                    CharmingImp.changeConfig(player, 2, 0);
                    break;
                case 64:
                    CharmingImp.changeConfig(player, 3, 0);
                    break;
                case 65:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSlayer().getDuoPartner() != null) {
                        player.getPacketSender().sendMessage(
                                "You already have a duo partner.");
                        return;
                    }
                    player.getPacketSender().sendMessage("<img=10> To do Social slayer, simply use your Slayer gem on another player.");
                    break;
                case 69:
                    ShopManager.getShops().get(44).open(player);
                    player.getPacketSender().sendMessage("<img=10> <col=660000>You currently have " + player.getRaidPoints() + " Raid Points.");
                    break;
                case 70:
                case 71:
                    if (player.getInventory().contains(19670) && player.getClickDelay().elapsed(700)) {
                        final int amt = player.getDialogueActionId() == 70 ? 1 : player.getInventory().getAmount(19670);
                        player.getPacketSender().sendInterfaceRemoval();
                        player.getInventory().delete(19670, amt);
                        player.getPacketSender().sendMessage("You claim the " + (amt > 1 ? "scrolls" : "scroll") + " and receive your reward.");
                        int minutes = player.getGameMode() == GameMode.NORMAL ? 10 : 5;
                        BonusExperienceTask.addBonusXp(player, minutes * amt);
                        player.getClickDelay().reset();
                    }
                    break;
                case 72:
                    TeleportHandler.teleportPlayer(player, new Position(2973, 3373), player.getSpellbook().getTeleportType());
                    break;
                case 100:
                    player.getTradingPostManager().open();
                    break;
                case 163:
                    if (player.getMinigameAttributes().getMiningBotAttributes().getResourcesLeftToCollect() > 0) {
                        int qty = player.getMinigameAttributes().getMiningBotAttributes().getResourcesLeftToCollect();
                        int[] ores = new int[qty];
                        int[] gems = new int[qty];
                        int j = 0;
                        for (int i = 0; i < qty; i++) {
                            int[] oreTypes = {441, 454, 445, 448, 450, 452};
                            int randomOre = oreTypes[RandomUtility.inclusiveRandom(oreTypes.length - 1)];
                            ores[i] = randomOre;

                            if (i % 50 == 0) {
                                int[] gemTypes = {1618, 1620, 1622, 1624, 1632};
                                int randomGem = gemTypes[RandomUtility.inclusiveRandom(gemTypes.length - 1)];
                                gems[j] = randomGem;
                                j++;
                            }
                        }
                        for (int i = 0; i < qty; i++) {
                            player.getInventory().add(ores[i], 1);
                            player.getInventory().add(gems[j], 1);
                        }
                        player.getMinigameAttributes().getMiningBotAttributes().setResourcesLeftToCollect(0);
                        player.getPacketSender().sendMessage("All ores have been added to your inventory.");
                        player.getPacketSender().sendMessage("Your Miner has " + player.getMinigameAttributes().getMiningBotAttributes().getResourcesLeftToGather() + " ores left to mine.");
                    }
                    if (player.getMinigameAttributes().getWCingBotAttributes().getResourcesLeftToCollect() > 0) {
                        int qty = player.getMinigameAttributes().getWCingBotAttributes().getResourcesLeftToCollect();
                        int[] logs = new int[qty];
                        for (int i = 0; i < qty; i++) {
                            int[] logTypes = {1514, 1516, 1518, 1520};
                            int randomLog = logTypes[RandomUtility.inclusiveRandom(logTypes.length - 1)];
                            logs[i] = randomLog;
                        }
                        for (int i = 0; i < qty; i++) {
                            player.getInventory().add(logs[i], 1);
                        }
                        player.getMinigameAttributes().getWCingBotAttributes().setResourcesLeftToCollect(0);
                        player.getPacketSender().sendMessage("All logs have been added to your inventory.");
                        player.getPacketSender().sendMessage("Your Woodcutter has " + player.getMinigameAttributes().getWCingBotAttributes().getResourcesLeftToGather() + " logs left to cut.");
                    }
                    if (player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToCollect() > 0) {
                        int qty = player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToCollect();
                        int[] herbs = new int[qty];
                        for (int i = 0; i < qty; i++) {
                            int[] herbTypes = {200, 202, 204, 206, 208, 210, 212, 214, 216, 218, 220, 2486, 3050, 3052};
                            int randomHerb = herbTypes[RandomUtility.inclusiveRandom(herbTypes.length - 1)];
                            herbs[i] = randomHerb;
                        }
                        for (int i = 0; i < qty; i++) {
                            player.getInventory().add(herbs[i], 1);
                        }
                        player.getMinigameAttributes().getFarmingBotAttributes().setResourcesLeftToCollect(0);
                        player.getPacketSender().sendMessage("All herbs have been added to your inventory.");
                        player.getPacketSender().sendMessage("Your Farmer has " + player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToGather() + " herbs left to farm.");
                    }
                    if (player.getMinigameAttributes().getFishingBotAttributes().getResourcesLeftToCollect() > 0) {
                        int qty = player.getMinigameAttributes().getFishingBotAttributes().getResourcesLeftToCollect();
                        int[] fish = new int[qty];
                        for (int i = 0; i < qty; i++) {
                            int[] fishTypes = {372, 378, 384, 390, 396};
                            int randomFish = fishTypes[RandomUtility.inclusiveRandom(fishTypes.length - 1)];
                            fish[i] = randomFish;
                        }
                        for (int i = 0; i < qty; i++) {
                            player.getInventory().add(fish[i], 1);
                        }
                        player.getMinigameAttributes().getFishingBotAttributes().setResourcesLeftToCollect(0);
                        player.getPacketSender().sendMessage("All fish have been added to your inventory.");
                        player.getPacketSender().sendMessage("Your Fisher has " + player.getMinigameAttributes().getFishingBotAttributes().getResourcesLeftToGather() + " fish left to catch.");
                    } else {
                        player.getPacketSender().sendMessage("You have no resources waiting for you.");
                    }
                    break;
                case 229:
                    if (player.getInventory().getAmount(989) >= 1) {
                        player.getInventory().delete(989, 1);
                        player.instanceBoost = 0;
                        player.instanceRespawnBoost = 0;
                        InstancedBosses.enter(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 1 crystal key in your inventory for this.");
                        player.getPacketSender().sendInterfaceRemoval();
                    }
                    break;

                case 311:
                    if (player.getInventory().contains(player.slayerInstanceKey)) {
                        player.slayerWaveQty = 1;
                        player.getPacketSender().sendInterfaceRemoval();
                        SuperiorSlayer.createInstance(player);
                    }
                    break;
                case 1011: //melee tutorial
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getInventory().add(9703, 1); //sword
                    player.getInventory().add(9704, 1); //shield
                    player.tutorialIsland = 6;
                    break;
            }
        } else if (id == SECOND_OPTION_OF_THREE) {
            if (player.getDialogue() != null) {
                if (player.getDialogue().action(2)) {
                    return;
                }
            }
            switch (player.getDialogueActionId()) {
                 case 1035:
                    player.getBingo().reroll(true, 19864, 5);
                    break;
                case 15:
                    DialogueManager.start(player, 25);
                    player.setDialogueActionId(15);
                    break;
                case 5:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getStaffRights() == StaffRights.PLAYER) {
                        player.getPacketSender().sendMessage("You need to be a member to teleport to this zone.").sendMessage("To become a member, visit www.Paescape.com and purchase a scroll.");
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(3424, 2919), player.getSpellbook().getTeleportType());
                    break;
                //Mandrith
                case 10:
                    player.getPacketSender().sendMessage("You trade your Artifacts for Wildy Points.");
                    Artifacts.tradeArtifacts(player);
                    break;
                case 22:
                    Nomad.openQuestLog(player);
                    break;
                case 25:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSkillManager().getCurrentLevel(Skill.HUNTER) < 23) {
                        player.getPacketSender().sendMessage("You need a Hunter level of at least 23 to visit the hunting area.");
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(2922, 2885), player.getSpellbook().getTeleportType());
                    break;
                case 35:
                    player.getPacketSender().sendEnterAmountPrompt("How many shards would you like to sell? (You can use K, M, B prefixes)");
                    player.setInputHandling(new SellShards());
                    break;
                case 41:
                    TeleportHandler.teleportPlayer(player, new Position(2903, 5204), player.getSpellbook().getTeleportType());
                    break;
                case 43:
                    TeleportHandler.teleportPlayer(player, new Position(2577, 9881), player.getSpellbook().getTeleportType());
                    break;
                case 47:
                    TeleportHandler.teleportPlayer(player, new Position(3023, 9740), player.getSpellbook().getTeleportType());
                    break;
                case 48:
                    if (player.getInteractingObject() != null) {
                        Mining.startMining(player, new GameObject(24445, player.getInteractingObject().getPosition()));
                    }
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 56:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING) < 60) {
                        player.getPacketSender().sendMessage("You need a Woodcutting level of at least 60 to teleport there.");
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(2711, 3463), player.getSpellbook().getTeleportType());
                    break;
                case 58:
                    ShopManager.getShops().get(39).open(player);
                    break;
                case 61:
                    CharmingImp.changeConfig(player, 0, 1);
                    break;
                case 62:
                    CharmingImp.changeConfig(player, 1, 1);
                    break;
                case 63:
                    CharmingImp.changeConfig(player, 2, 1);
                    break;
                case 64:
                    CharmingImp.changeConfig(player, 3, 1);
                    break;
                case 65:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSlayer().getDuoPartner() != null) {
                        Slayer.resetDuo(player, World.getPlayerByName(player.getSlayer().getDuoPartner()));
                    }
                    break;
                case 69:
                    //if ((player.getPaePoints() >= 25)&&(player.getInventory().getFreeSlots() == 28)&&(player.getEquipment().isNaked(player)))
                    if (player.getPaePoints() >= 25) {
                        player.setPaePoints(player.getPaePoints() - 25);
                        player.getPacketSender().sendMessage("@red@This raid costs you 25 Paepoints.");


                        TeleportHandler.teleportPlayer(player, new Position(3291, 5452, 0), player.getSpellbook().getTeleportType());
                    } else {
                        player.getPacketSender().sendMessage("@red@You need 25 Paepoints to do this raid.");
                        //player.getPacketSender().sendMessage("@red@You need an empty inventory to enter this raid.");
                        //player.getPacketSender().sendMessage("@red@You can't wear any equipment into this raid.");
                    }
                    break;
                case 70:
                case 71:
                    final boolean all = player.getDialogueActionId() == 71;
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getInventory().getFreeSlots() == 0) {
                        player.getPacketSender().sendMessage("You do not have enough free inventory space to do that.");
                        return;
                    }
                    if (player.getInventory().contains(19670) && player.getClickDelay().elapsed(700)) {
                        int amt = !all ? 1 : player.getInventory().getAmount(19670);

                        player.setVotePoints(player.getVotePoints() + amt);
                        player.setPaePoints(player.getPaePoints() + (10 * amt));
                        player.getInventory().add(995, 50000 * amt);
                        player.getInventory().delete(19670, amt);
                        player.getPointsHandler().refreshPanel();
                        player.getPacketSender().sendMessage("You claim the " + (amt > 1 ? "scrolls" : "scroll") + " and receive your reward.");
                        player.sendMessage("You've received " + amt + " Vote Points, " + (amt * 10) + " PaePoints and " + (50000 * amt) + " Coins!");
                        player.getClickDelay().reset();
                    }
                    break;
                case 72:
                    TeleportHandler.teleportPlayer(player, new Position(3186, 3425), player.getSpellbook().getTeleportType());
                    break;
                case 100:
                    player.getTradingPostManager().openEditor();
                    break;
                case 163:
                    if ((player.getMinigameAttributes().getMiningBotAttributes().getResourcesLeftToGather() > 0) && (!player.getMinigameAttributes().getMiningBotAttributes().getHasMiningBot())) {
                        player.getPacketSender().sendMessage("My worker still needs to mine " + player.getMinigameAttributes().getMiningBotAttributes().getResourcesLeftToGather() + " ores for you!");
                        MiningBot.start(player);
                    }
                    if ((player.getMinigameAttributes().getWCingBotAttributes().getResourcesLeftToGather() > 0) && (!player.getMinigameAttributes().getWCingBotAttributes().getHasWCingBot())) {
                        player.getPacketSender().sendMessage("My worker still needs to cut " + player.getMinigameAttributes().getWCingBotAttributes().getResourcesLeftToGather() + " logs for you!");
                        WCingBot.start(player);
                    }
                    if ((player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToGather() > 0) && (!player.getMinigameAttributes().getFarmingBotAttributes().getHasFarmingBot())) {
                        player.getPacketSender().sendMessage("My worker still needs to farm " + player.getMinigameAttributes().getFarmingBotAttributes().getResourcesLeftToGather() + " herbs for you!");
                        FarmingBot.start(player);
                    }
                    if ((player.getMinigameAttributes().getFishingBotAttributes().getResourcesLeftToGather() > 0) && (!player.getMinigameAttributes().getFishingBotAttributes().getHasFishingBot())) {
                        player.getPacketSender().sendMessage("My worker still needs to catch " + player.getMinigameAttributes().getFishingBotAttributes().getResourcesLeftToGather() + " fish for you!");
                        FishingBot.start(player);
                    }
                    break;
                case 229:
                    if (player.getInventory().getAmount(989) >= 25) {
                        player.getInventory().delete(989, 25);
                        player.instanceBoost = 5;
                        player.instanceRespawnBoost = 4;
                        InstancedBosses.enter(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 25 crystal keys in your inventory for this.");
                        player.getPacketSender().sendInterfaceRemoval();
                    }
                    break;

                case 311:
                    if (player.getInventory().contains(player.slayerInstanceKey) && player.getMoneyInPouch() > 10000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 10000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        World.taxes += 10000000;
                        player.slayerWaveQty = 2;
                        player.getPacketSender().sendInterfaceRemoval();
                        SuperiorSlayer.createInstance(player);

                        discordMessage = "[Superior Slayer] " + player.getUsername() + " has sunk 10m entering a Superior Slayer Instance.";

                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GOLD_SINKS_CH).get());
                    }
                    break;
                case 1011: //ranged tutorial
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getInventory().add(9705, 1); //bow
                    player.getInventory().add(9706, 250); //arrows
                    player.tutorialIsland = 6;
                    break;
            }
        } else if (id == THIRD_OPTION_OF_THREE) {
            if (player.getDialogue() != null) {
                if (player.getDialogue().action(3)) {
                    return;
                }
            }
            switch (player.getDialogueActionId()) {
                case 5:
                case 15:
                case 19:
                case 21:
                case 22:
                case 25:
                case 35:
                case 47:
                case 48:
                case 56:
                case 58:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 69:
                case 70:
                case 71:
                case 72:
                case 77:
                case 100:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 43:
                    DialogueManager.start(player, 65);
                    player.setDialogueActionId(36);
                    break;
                case 41:
                    player.setDialogueActionId(36);
                    DialogueManager.start(player, 65);
                    break;
                case 163:
                    player.setDialogueActionId(164);
                    DialogueManager.start(player, 164);
                    break;
                case 10:
                    ShopManager.getShops().get(26).open(player);
                    break;
                case 229:
                    if (player.getInventory().getAmount(989) >= 100) {
                        player.getInventory().delete(989, 100);
                        player.instanceBoost = 10;
                        player.instanceRespawnBoost = 8;
                        InstancedBosses.enter(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 100 crystal keys in your inventory for this.");
                        player.getPacketSender().sendInterfaceRemoval();
                    }
                    break;

                case 311:
                    if (player.getInventory().contains(player.slayerInstanceKey) && player.getMoneyInPouch() > 25000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 25000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        World.taxes += 25000000;
                        player.slayerWaveQty = 3;
                        player.getPacketSender().sendInterfaceRemoval();
                        SuperiorSlayer.createInstance(player);

                        discordMessage = "[Superior Slayer] " + player.getUsername() + " has sunk 25m entering a Superior Slayer Instance.";

                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GOLD_SINKS_CH).get());
                    }
                    break;
                case 1011: //magic tutorial
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getInventory().add(1381, 1); //air staff
                    player.getInventory().add(558, 250); //mind runes
                    player.tutorialIsland = 6;
                    break;

            }
        }
    }

    public static int FIRST_OPTION_OF_FIVE = 2494;
    public static int SECOND_OPTION_OF_FIVE = 2495;
    public static int THIRD_OPTION_OF_FIVE = 2496;
    public static int FOURTH_OPTION_OF_FIVE = 2497;
    public static int FIFTH_OPTION_OF_FIVE = 2498;

    public static int FIRST_OPTION_OF_FOUR = 2482;
    public static int SECOND_OPTION_OF_FOUR = 2483;
    public static int THIRD_OPTION_OF_FOUR = 2484;
    public static int FOURTH_OPTION_OF_FOUR = 2485;


    public static int FIRST_OPTION_OF_THREE = 2471;
    public static int SECOND_OPTION_OF_THREE = 2472;
    public static int THIRD_OPTION_OF_THREE = 2473;

    public static int FIRST_OPTION_OF_TWO = 2461;
    public static int SECOND_OPTION_OF_TWO = 2462;

}
