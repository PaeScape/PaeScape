package com.arlania.world.content.seasonal.Interface;

import com.arlania.model.GameMode;
import com.arlania.model.StaffRights;
import com.arlania.world.entity.impl.player.Player;

public class Seasonal {

    private static final int[][] LINE_IDS = {{60662, 60663}, {60664, 60665}, {60666, 60667}, {60668, 60669}, {60670, 60671}, {60672, 60673}, {60674, 60675}, {60676, 60677}, {60678, 60679}, {18374, 60701}, {60702, 60703}, {60704, 60705}};
    private static final int[] BUTTON_IDS = {60622, 60625, 60628, 60631, 60634, 60637, 60640, 60643, 60646, 60691, 60694, 60697};
    private static final int[] TAB_IDS = {60602, 60605, 60608, 60611, 60614, 60617};
    private static final int[] INTERFACE_IDS = {60600, 60700, 60800, 60900, 61000, 61100};

    /**
     * Method that handles the destination assigning.
     *
     * @param client The player.assigning to the destination.    //sec
     * @param button Button id being clicked to get the destination.
     */
    public static void assign(Player player, int button) {
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            if (button == BUTTON_IDS[i]) {
                player.destination = i;
            }
        }
        if (player.lastClickedTab == 1)
            seasonalMenu(player, player.destination);
        else if (player.lastClickedTab == 2)
            unlockTraining(player, player.destination);
        else if (player.lastClickedTab == 3)
            unlockDungeons(player, player.destination);
        else if (player.lastClickedTab == 4)
            unlockBosses(player, player.destination);
        else if (player.lastClickedTab == 5)
            unlockMinigames(player, player.destination);
        else if (player.lastClickedTab == 6)
            unlockRaids(player, player.destination);

    }


    public static void seasonalMenu(Player player, int taskId) {
        for (final SeasonalMenu.SeasonalOptions a : SeasonalMenu.SeasonalOptions.values()) {
            if (taskId == a.ordinal()) {

                if (a.getSeasonalUnlockId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this right now.");
                    return;
                }

                if (player.getGameMode() != GameMode.SEASONAL_IRONMAN && player.getStaffRights() != StaffRights.DEVELOPER)
                    return;

                switch (a.getSeasonalUnlockId()) {
                    case 1:
                        if (player.seasonalTeleportUnlocks > 0) {
                            player.sendMessage("@red@Click one of the teleport options on the left to unlock a teleport.");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 2:
                        if (player.seasonalPP > 0) {
                            player.sendMessage("You've claimed " + player.seasonalPP + " PaePoints!");
                            player.setPaePoints(player.getPaePoints() + player.seasonalPP);
                            player.seasonalPP = 0;
                        } else {
                            player.sendMessage("@red@You have no PaePoints to redeem!");
                        }
                        break;
                    case 3:
                        if (player.seasonalCrystalKeys > 0) {
                            if (player.getInventory().getFreeSlots() > 0) {
                                player.sendMessage("You've claimed " + player.seasonalCrystalKeys + " Crystal Keys!");
                                player.getInventory().add(989, player.seasonalCrystalKeys);
                                player.seasonalCrystalKeys = 0;
                            }
                        } else {
                            player.sendMessage("@red@You have no Crystal Keys to redeem!");
                        }
                        break;
                    case 4:
                        if (player.seasonalEventPass > 0) {
                            if (player.getInventory().getFreeSlots() > 0) {
                                player.sendMessage("You've claimed " + player.seasonalEventPass + " Event Passes!");
                                player.getInventory().add(6769, player.seasonalEventPass);
                                player.seasonalEventPass = 0;
                            }
                        } else {
                            player.sendMessage("@red@You have no Event Passes to redeem!");
                        }
                        break;
                    case 5:
                        if (player.seasonalRing > 0) {
                            if (player.getInventory().getFreeSlots() > 0) {
                                player.sendMessage("You've claimed " + player.seasonalRing + " Ring of Fortune!");
                                player.getInventory().add(221140, player.seasonalRing);
                                player.seasonalRing = 0;
                            } else {
                                player.getPacketSender().sendMessage("@red@Your inventory is full.");
                            }

                        }  else {
                            player.sendMessage("@red@You have no Ring of Fortune to redeem!");
                        }
                        break;
                    case 6:
                        if (player.seasonalMBox > 0) {
                            if (player.getInventory().getFreeSlots() > 0) {
                                player.sendMessage("You've claimed " + player.seasonalMBox + " Mystery Boxes!");
                                player.getInventory().add(6199, player.seasonalMBox);
                                player.seasonalMBox = 0;
                            }
                        } else {
                            player.sendMessage("@red@You have no Mystery Boxes to redeem!");
                        }
                        break;
                    case 7:
                        if (player.seasonalEMBox > 0) {
                            if (player.getInventory().getFreeSlots() > 0) {
                                player.sendMessage("You've claimed " + player.seasonalEMBox + " Elite Mystery Boxes!");
                                player.getInventory().add(15501, player.seasonalEMBox);
                                player.seasonalEMBox = 0;
                            }
                        } else {
                            player.sendMessage("@red@You have no Elite Mystery Boxes to redeem!");
                        }
                        break;
                    case 8:
                        if (player.seasonalSBox > 0) {
                            if (player.getInventory().getFreeSlots() > 0) {
                                player.sendMessage("You've claimed " + player.seasonalSBox + " Supreme Mystery Boxes!");
                                player.getInventory().add(603, player.seasonalSBox);
                                player.seasonalSBox = 0;
                            }
                        } else {
                            player.sendMessage("@red@You have no Supreme Mystery Boxes to redeem!");
                        }
                        break;
                    case 9:
                        if (player.seasonalDonationTokens > 0) {
                            if (player.getInventory().getFreeSlots() > 0) {
                                player.sendMessage("You've claimed " + player.seasonalDonationTokens + " Donation store tokens!");
                                player.getInventory().add(8851, player.seasonalDonationTokens);
                                player.seasonalDonationTokens = 0;
                            }
                        } else {
                            player.sendMessage("@red@You have no Donation store tokens to redeem!");
                        }
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        //qty = 0;
                        break;

                }
            }
        }
        openTab(player, 60656);
    }

    public static void unlockTraining(Player player, int taskId) {
        for (final Training.TrainingTeleports b : Training.TrainingTeleports.values()) {
            if (taskId == b.ordinal()) {

                if (b.getSeasonalUnlockId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this right now.");
                    return;
                }

                if (player.getGameMode() != GameMode.SEASONAL_IRONMAN && player.getStaffRights() != StaffRights.DEVELOPER)
                    return;


                switch (b.getSeasonalUnlockId()) {
                    case 1:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalTrainingTeleports[0] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Barbarian Agility teleport!");
                            player.seasonalTrainingTeleports[0]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalTrainingTeleports[0] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 2:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalTrainingTeleports[1] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Crafting Guild teleport!");
                            player.seasonalTrainingTeleports[1]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalTrainingTeleports[1] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 3:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalTrainingTeleports[2] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Essence Mine teleport!");
                            player.seasonalTrainingTeleports[2]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalTrainingTeleports[2] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 4:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalTrainingTeleports[3] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Farming Patch teleport!");
                            player.seasonalTrainingTeleports[3]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalTrainingTeleports[3] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 5:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalTrainingTeleports[4] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Impling teleport!");
                            player.seasonalTrainingTeleports[4]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalTrainingTeleports[4] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 6:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalTrainingTeleports[5] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Mining Guild teleport!");
                            player.seasonalTrainingTeleports[5]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalTrainingTeleports[5] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 7:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalTrainingTeleports[6] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Piscatoris Fishing teleport!");
                            player.seasonalTrainingTeleports[6]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalTrainingTeleports[6] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 8:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalTrainingTeleports[7] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Rimmington Mine teleport!");
                            player.seasonalTrainingTeleports[7]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalTrainingTeleports[7] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 9:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalTrainingTeleports[8] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Woodcutting Guild teleport!");
                            player.seasonalTrainingTeleports[8]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalTrainingTeleports[8] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;

                }
            }
        }
        openTab(player, 60657);
    }

    public static void unlockDungeons(Player player, int taskId) {
        for (final Dungeons.DungeonTeleports c : Dungeons.DungeonTeleports.values()) {
            if (taskId == c.ordinal()) {

                if (c.getSeasonalUnlockId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this right now.");
                    return;
                }

                if (player.getGameMode() != GameMode.SEASONAL_IRONMAN && player.getStaffRights() != StaffRights.DEVELOPER)
                    return;


                switch (c.getSeasonalUnlockId()) {
                    case 1:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalDungeonTeleports[0] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Ancient Cavern teleport!");
                            player.seasonalDungeonTeleports[0]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalDungeonTeleports[0] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 2:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalDungeonTeleports[1] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Asgarnia Ice Dungeon teleport!");
                            player.seasonalDungeonTeleports[1]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalDungeonTeleports[1] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 3:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalDungeonTeleports[2] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Brimhaven Dungeon teleport!");
                            player.seasonalDungeonTeleports[2]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalDungeonTeleports[2] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 4:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalDungeonTeleports[3] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Edgeville Dungeon teleport!");
                            player.seasonalDungeonTeleports[3]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalDungeonTeleports[3] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 5:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalDungeonTeleports[4] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Slayer Dungeon teleport!");
                            player.seasonalDungeonTeleports[4]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalDungeonTeleports[4] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 6:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalDungeonTeleports[5] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Slayer Tower teleport!");
                            player.seasonalDungeonTeleports[5]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalDungeonTeleports[5] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 7:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalDungeonTeleports[6] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Strykewyrm Cavern teleport!");
                            player.seasonalDungeonTeleports[6]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalDungeonTeleports[6] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 8:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalDungeonTeleports[7] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Taverly Dungeon teleport!");
                            player.seasonalDungeonTeleports[7]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalDungeonTeleports[7] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 9:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalDungeonTeleports[8] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Waterbirth Dungeon teleport!");
                            player.seasonalDungeonTeleports[8]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalDungeonTeleports[8] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 10:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalDungeonTeleports[9] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Ape Atoll Dungeon teleport!");
                            player.seasonalDungeonTeleports[8]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalDungeonTeleports[8] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;

                }
            }
        }
        openTab(player, 60658);
    }

    public static void unlockBosses(Player player, int taskId) {

        for (final Bosses.BossesTeleports d : Bosses.BossesTeleports.values()) {
            if (taskId == d.ordinal()) {

                if (d.getSeasonalUnlockId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this right now.");
                    return;
                }

                if (player.getGameMode() != GameMode.SEASONAL_IRONMAN && player.getStaffRights() != StaffRights.DEVELOPER)
                    return;


                switch (d.getSeasonalUnlockId()) {
                    case 1: //TODO SEASONAL make sure players can't enter these boss areas without unlocking the boss teleports
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalBossTeleports[0] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Abyssal Sire teleport!");
                            player.seasonalBossTeleports[0]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalBossTeleports[0] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 2:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalBossTeleports[1] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Cerberus teleport!");
                            player.seasonalBossTeleports[1]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalBossTeleports[1] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 3:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalBossTeleports[2] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Corporeal Beast teleport!");
                            player.seasonalBossTeleports[2]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalBossTeleports[2] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 4:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalBossTeleports[3] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Dagannoth Kings teleport!");
                            player.seasonalBossTeleports[3]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalBossTeleports[3] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 5:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalBossTeleports[4] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Giant Mole teleport!");
                            player.seasonalBossTeleports[4]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalBossTeleports[4] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 6:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalBossTeleports[5] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Godwars Dungeon teleport!");
                            player.seasonalBossTeleports[5]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalBossTeleports[5] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 7:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalBossTeleports[6] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the King Black Dragon teleport!");
                            player.seasonalBossTeleports[6]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalBossTeleports[6] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 8:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalBossTeleports[7] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Thermonuclear Smoke Devil teleport!");
                            player.seasonalBossTeleports[7]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalBossTeleports[7] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 9:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalBossTeleports[8] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Nightmare teleport!");
                            player.seasonalBossTeleports[8]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalBossTeleports[8] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 10:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalBossTeleports[9] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Kalphite Queen teleport!");
                            player.seasonalBossTeleports[9]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalBossTeleports[9] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;

                }
            }
        }
        openTab(player, 60659);
    }

    public static void unlockMinigames(Player player, int taskId) {
        for (final Minigames.MinigamesTeleports e : Minigames.MinigamesTeleports.values()) {
            if (taskId == e.ordinal()) {

                if (e.getSeasonalUnlockId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this right now.");
                    return;
                }

                if (player.getGameMode() != GameMode.SEASONAL_IRONMAN && player.getStaffRights() != StaffRights.DEVELOPER)
                    return;


                switch (e.getSeasonalUnlockId()) {

                    case 1:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalMinigameTeleports[0] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Barrows minigame teleport!");
                            player.seasonalMinigameTeleports[0]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalMinigameTeleports[0] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;

                    case 2:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalMinigameTeleports[1] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Blast Furnace teleport!");
                            player.seasonalMinigameTeleports[1]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalMinigameTeleports[1] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;

                    case 3:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalMinigameTeleports[2] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Blast Mine teleport!");
                            player.seasonalMinigameTeleports[2]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalMinigameTeleports[2] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;

                    case 4:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalMinigameTeleports[3] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Fight Caves teleport!");
                            player.seasonalMinigameTeleports[3]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalMinigameTeleports[3] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;

                    case 5:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalMinigameTeleports[4] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Motherlode Mine teleport!");
                            player.seasonalMinigameTeleports[4]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalMinigameTeleports[4] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;

                    case 6:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalMinigameTeleports[5] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Wintertodt teleport!");
                            player.seasonalMinigameTeleports[5]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalMinigameTeleports[5] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;

                }
            }
        }
        openTab(player, 60660);
    }

    public static void unlockRaids(Player player, int taskId) {
        for (final Raids.RaidsTeleports f : Raids.RaidsTeleports.values()) {
            if (taskId == f.ordinal()) {

                if (f.getSeasonalUnlockId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this right now.");
                    return;
                }

                if (player.getGameMode() != GameMode.SEASONAL_IRONMAN && player.getStaffRights() != StaffRights.DEVELOPER)
                    return;


                switch (f.getSeasonalUnlockId()) {
                    case 1:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalRaidsTeleports[0] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Chambers of Xeric teleport!");
                            player.seasonalRaidsTeleports[0]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalRaidsTeleports[0] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 2:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalRaidsTeleports[1] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Raids Lobby teleport!");
                            player.seasonalRaidsTeleports[1]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalRaidsTeleports[1] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 3:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalRaidsTeleports[2] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Stronghold Raids teleport!");
                            player.seasonalRaidsTeleports[2]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalRaidsTeleports[2] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;
                    case 4:
                        if (player.seasonalTeleportUnlocks > 0 && player.seasonalRaidsTeleports[3] == 0) {
                            player.getPacketSender().sendMessage("You've unlocked the Theatre of Blood teleport!");
                            player.seasonalRaidsTeleports[3]++;
                            player.seasonalTeleportUnlocks--;
                        } else if (player.seasonalRaidsTeleports[3] == 1) {
                            player.getPacketSender().sendMessage("@red@You've already unlocked this teleport!");
                        } else {
                            player.sendMessage("@red@Earn more points to unlock more Teleports!");
                        }
                        break;

                }
            }
        }
        openTab(player, 60661);
    }


    public static void openTab(Player player, int button) {

        if (player.activeMenu == "seasonal") {
            player.getPacketSender().sendString(60656, "Menu");
            player.getPacketSender().sendString(60657, "Training");
            player.getPacketSender().sendString(60658, "Dungeons");
            player.getPacketSender().sendString(60659, "Bosses");
            player.getPacketSender().sendString(60660, "Minigames");
            player.getPacketSender().sendString(60661, "Raids");

            for (int i = 0; i < TAB_IDS.length; i++) {
                if (button == TAB_IDS[i]) {
                    player.lastClickedTab = i + 1;
                    player.getPacketSender().sendInterface(INTERFACE_IDS[i]);
                }
            }
            switch (player.lastClickedTab) {

                case 1:
                    for (final SeasonalMenu.SeasonalOptions a : SeasonalMenu.SeasonalOptions.values()) {

                        String color = "@red@ ";
                        int qty = 0;
                        String qtyDisplay = "";

                        switch (a.getSeasonalUnlockId()) {
                            case 1:
                                qty = player.seasonalTeleportUnlocks;
                                break;
                            case 2:
                                qty = player.seasonalPP;
                                break;
                            case 3:
                                qty = player.seasonalCrystalKeys;
                                break;
                            case 4:
                                qty = player.seasonalEventPass;
                                break;
                            case 5:
                                qty = player.seasonalRing;
                                break;
                            case 6:
                                qty = player.seasonalMBox;
                                break;
                            case 7:
                                qty = player.seasonalEMBox;
                                break;
                            case 8:
                                qty = player.seasonalSBox;
                                break;
                            case 9:
                                qty = player.seasonalDonationTokens;
                                break;
                            case 10:
                                //qty = player.seasonal20bond;
                                break;
                            case 11:
                                //qty = player.seasonal50bond;
                                break;
                            case 12:
                                //qty = 0;
                                break;

                        }

                        if (qty > 0) {
                            color = "@gre@";
                            qtyDisplay = color + " " + qty;
                        }

                        player.getPacketSender().sendTeleString(color + a.getassignName()[0], LINE_IDS[a.ordinal()][0]);
                        player.getPacketSender().sendTeleString(color + a.getassignName()[1] + qtyDisplay, LINE_IDS[a.ordinal()][1]);
                    }
                    break;
                case 2:
                    for (final Training.TrainingTeleports b : Training.TrainingTeleports.values()) {

                        String color = "@red@";
                        int qty = 0;

                        switch (b.getSeasonalUnlockId()) {
                            case 1:
                                qty = player.seasonalTrainingTeleports[0];
                                break;
                            case 2:
                                qty = player.seasonalTrainingTeleports[1];
                                break;
                            case 3:
                                qty = player.seasonalTrainingTeleports[2];
                                break;
                            case 4:
                                qty = player.seasonalTrainingTeleports[3];
                                break;
                            case 5:
                                qty = player.seasonalTrainingTeleports[4];
                                break;
                            case 6:
                                qty = player.seasonalTrainingTeleports[5];
                                break;
                            case 7:
                                qty = player.seasonalTrainingTeleports[6];
                                break;
                            case 8:
                                qty = player.seasonalTrainingTeleports[7];
                                break;
                            case 9:
                                qty = player.seasonalTrainingTeleports[8];
                                break;
                            case 10:
                                break;
                            case 11:
                                break;
                            case 12:
                                break;

                        }

                        if (qty > 0) {
                            color = "@gre@";
                        }

                        player.getPacketSender().sendTeleString(color + b.getassignName()[0], LINE_IDS[b.ordinal()][0]);
                        player.getPacketSender().sendTeleString(color + b.getassignName()[1], LINE_IDS[b.ordinal()][1]);
                    }
                    break;
                case 3:
                    for (final Dungeons.DungeonTeleports c : Dungeons.DungeonTeleports.values()) {


                        String color = "@red@";
                        int qty = 0;

                        switch (c.getSeasonalUnlockId()) {
                            case 1:
                                qty = player.seasonalDungeonTeleports[0];
                                break;
                            case 2:
                                qty = player.seasonalDungeonTeleports[1];
                                break;
                            case 3:
                                qty = player.seasonalDungeonTeleports[2];
                                break;
                            case 4:
                                qty = player.seasonalDungeonTeleports[3];
                                break;
                            case 5:
                                qty = player.seasonalDungeonTeleports[4];
                                break;
                            case 6:
                                qty = player.seasonalDungeonTeleports[5];
                                break;
                            case 7:
                                qty = player.seasonalDungeonTeleports[6];
                                break;
                            case 8:
                                qty = player.seasonalDungeonTeleports[7];
                                break;
                            case 9:
                                qty = player.seasonalDungeonTeleports[8];
                                break;
                            case 10:
                                break;
                            case 11:
                                break;
                            case 12:
                                break;

                        }

                        if (qty > 0) {
                            color = "@gre@";
                        }

                        player.getPacketSender().sendTeleString(color + c.getassignName()[0], LINE_IDS[c.ordinal()][0]);
                        player.getPacketSender().sendTeleString(color + c.getassignName()[1], LINE_IDS[c.ordinal()][1]);
                    }
                    break;
                case 4:
                    for (final Bosses.BossesTeleports d : Bosses.BossesTeleports.values()) {

                        String color = "@red@";
                        int qty = 0;

                        switch (d.getSeasonalUnlockId()) {
                            case 1:
                                qty = player.seasonalBossTeleports[0];
                                break;
                            case 2:
                                qty = player.seasonalBossTeleports[1];
                                break;
                            case 3:
                                qty = player.seasonalBossTeleports[2];
                                break;
                            case 4:
                                qty = player.seasonalBossTeleports[3];
                                break;
                            case 5:
                                qty = player.seasonalBossTeleports[4];
                                break;
                            case 6:
                                qty = player.seasonalBossTeleports[5];
                                break;
                            case 7:
                                qty = player.seasonalBossTeleports[6];
                                break;
                            case 8:
                                qty = player.seasonalBossTeleports[7];
                                break;
                            case 9:
                                qty = player.seasonalBossTeleports[8];
                                break;
                            case 10:
                                qty = player.seasonalBossTeleports[9];
                                break;
                            case 11:
                                break;
                            case 12:
                                break;

                        }

                        if (qty > 0) {
                            color = "@gre@";
                        }

                        player.getPacketSender().sendTeleString(color + d.getassignName()[0], LINE_IDS[d.ordinal()][0]);
                        player.getPacketSender().sendTeleString(color + d.getassignName()[1], LINE_IDS[d.ordinal()][1]);
                    }
                    break;
                case 5:
                    for (final Minigames.MinigamesTeleports e : Minigames.MinigamesTeleports.values()) {

                        String color = "@red@";
                        int qty = 0;

                        switch (e.getSeasonalUnlockId()) {
                            case 1:
                                qty = player.seasonalMinigameTeleports[0];
                                break;
                            case 2:
                                qty = player.seasonalMinigameTeleports[1];
                                break;
                            case 3:
                                qty = player.seasonalMinigameTeleports[2];
                                break;
                            case 4:
                                qty = player.seasonalMinigameTeleports[3];
                                break;
                            case 5:
                                qty = player.seasonalMinigameTeleports[4];
                                break;
                            case 6:
                                qty = player.seasonalMinigameTeleports[5];
                                break;
                            case 7:
                                break;
                            case 8:
                                break;
                            case 9:
                                break;
                            case 10:
                                break;
                            case 11:
                                break;
                            case 12:
                                break;

                        }

                        if (qty > 0) {
                            color = "@gre@";
                        }

                        player.getPacketSender().sendTeleString(color + e.getassignName()[0], LINE_IDS[e.ordinal()][0]);
                        player.getPacketSender().sendTeleString(color + e.getassignName()[1], LINE_IDS[e.ordinal()][1]);
                    }
                    break;
                case 6:
                    for (final Raids.RaidsTeleports f : Raids.RaidsTeleports.values()) {

                        String color = "@red@";
                        int qty = 0;

                        switch (f.getSeasonalUnlockId()) {
                            case 1:
                                qty = player.seasonalRaidsTeleports[0];
                                break;
                            case 2:
                                qty = player.seasonalRaidsTeleports[1];
                                break;
                            case 3:
                                qty = player.seasonalRaidsTeleports[2];
                                break;
                            case 4:
                                qty = player.seasonalRaidsTeleports[3];
                                break;
                            case 5:
                                break;
                            case 6:
                                break;
                            case 7:
                                break;
                            case 8:
                                break;
                            case 9:
                                break;
                            case 10:
                                break;
                            case 11:
                                break;
                            case 12:
                                break;

                        }

                        if (qty > 0) {
                            color = "@gre@";
                        }


                        player.getPacketSender().sendTeleString(color + f.getassignName()[0], LINE_IDS[f.ordinal()][0]);
                        player.getPacketSender().sendTeleString(color + f.getassignName()[1], LINE_IDS[f.ordinal()][1]);
                    }
                    break;
            }
        }
    }


}
