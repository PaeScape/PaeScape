package com.arlania.world.content.skill.impl.smithing;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.content.Sounds;
import com.arlania.world.content.Sounds.Sound;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;

public class Smelting {


    public static void openInterface(Player player) {
        player.getSkillManager().stopSkilling();
        for (int j = 0; j < SmithingData.SMELT_BARS.length; j++)
            player.getPacketSender().sendInterfaceModel(SmithingData.SMELT_FRAME[j], SmithingData.SMELT_BARS[j], 150);
        player.getPacketSender().sendChatboxInterface(2400);
    }

    public static void smeltBar(final Player player, final int barId, final int amount) {
        player.getSkillManager().stopSkilling();
        player.getPacketSender().sendInterfaceRemoval();
        if (!SmithingData.canSmelt(player, barId))
            return;
        player.performAnimation(new Animation(896));

        player.setCurrentTask(new Task(3, player, true) {
            int amountMade = 0;

            @Override
            public void execute() {
                if (!SmithingData.canSmelt(player, barId)) {
                    stop();
                    return;
                }
                if (player.getOres()[1] == 453) {
                    if ((player.getCoalBag() >= SmithingData.getCoalAmount(barId)) && (player.getInventory().contains(18339)) && (player.getCoalBag() < SmithingData.getCoalAmount(barId))) {
                        player.getPacketSender().sendMessage("@red@You do not have enough coal to make those bars.");
                        return;
                    } else if (player.getInventory().getAmount(453) < SmithingData.getCoalAmount(barId) && (Player.checkAchievementAbilities(player, "processor") && player.getInventory().getAmount(454) < SmithingData.getCoalAmount(barId))) {
                        player.getPacketSender().sendMessage("@red@You do not have enough coal to make those bars.");
                        return;
                    }
                }

                if (!player.getInventory().contains(player.getOres()[0]) && (Player.checkAchievementAbilities(player, "processor") && !player.getInventory().contains(player.getOres()[0] + 1))) {
                    player.getPacketSender().sendMessage("@red@You've run out of ores.");
                    return;
                }
                player.setPositionToFace(new Position(3022, 9742, 0));
                player.performAnimation(new Animation(896));
                handleBarCreation(barId, player);
                amountMade++;

                if (amountMade >= 27)
                    stop();

                if (amountMade >= amount)
                    stop();
            }
        });
        TaskManager.submit(player.getCurrentTask());

    }

    public static void handleBarCreation(int barId, Player player) {
        int addxp = getExperience(barId);

        if (player.getEquipment().contains(219988))
            addxp *= 1.10;

        int amountToProcess = player.acceleratedProcessing();

        int firstOre = player.getOres()[0];
        int secondOre = player.getOres()[1];

        if (!player.getInventory().contains(firstOre)) {

            if (Player.checkAchievementAbilities(player, "processor") && player.getInventory().contains(firstOre + 1)) {
                firstOre += 1; //get noted ID
            } else {
                player.getPacketSender().sendMessage("You don't have enough ore to make this bar.");
                return;
            }
        }

        if (!player.getInventory().contains(secondOre)) {

            if (Player.checkAchievementAbilities(player, "processor") && player.getInventory().contains(secondOre + 1)) {
                secondOre += 1; //get noted ID
            } else {
                player.getPacketSender().sendMessage("You don't have enough ore to make this bar.");
                return;
            }
        }

        if (ItemDefinition.forId(firstOre).isNoted() && ItemDefinition.forId(secondOre).isNoted() && Player.checkAchievementAbilities(player, "processor")) {
            barId += 1;
        }


        int oreQuantity = firstOre * amountToProcess;
        int coalQuantity = SmithingData.getCoalAmount(barId) * amountToProcess;

        int oreBars = amountToProcess;
        int coalBars = amountToProcess;

        if (player.getInventory().getAmount(firstOre) < oreQuantity) {
            oreQuantity = player.getInventory().getAmount(firstOre);
            oreBars = player.getInventory().getAmount(firstOre);
        }

        if (secondOre == 453) {
            if (player.getInventory().getAmount(453) < coalQuantity) {
                coalQuantity = player.getInventory().getAmount(453);
                coalBars = player.getInventory().getAmount(453);
            }
        } else if (secondOre == 454 && Player.checkAchievementAbilities(player, "processor")) {
            if (player.getInventory().getAmount(454) < coalQuantity) {
                coalQuantity = player.getInventory().getAmount(454);
                coalBars = player.getInventory().getAmount(454);
            }
        } else if (secondOre == 436) {
            if (player.getInventory().getAmount(secondOre) < oreQuantity) {
                oreQuantity = player.getInventory().getAmount(secondOre);
                coalBars = player.getInventory().getAmount(436);
            }
        } else if (secondOre == 437 && Player.checkAchievementAbilities(player, "processor")) {
            if (player.getInventory().getAmount(secondOre) < oreQuantity) {
                oreQuantity = player.getInventory().getAmount(secondOre);
                coalBars = player.getInventory().getAmount(437);
            }
        }

        int smallest;
        smallest = Math.min(Math.min(oreBars, coalBars), amountToProcess);



        if(barId == 2350 && Player.checkAchievementAbilities(player, "processor")) {
            smallest = Math.min(Math.min(player.getInventory().getAmount(437), player.getInventory().getAmount(439)), amountToProcess);
        } else if(barId == 2349) {
            smallest = Math.min(Math.min(player.getInventory().getAmount(436), player.getInventory().getAmount(438)), amountToProcess);
        }

        amountToProcess = smallest;

        if (firstOre > 0) {
            player.getInventory().delete(firstOre, amountToProcess);
            if ((barId == 2357) && (player.getEquipment().contains(776))) {
                player.getSkillManager().addExperience(Skill.SMITHING, addxp * 3 * amountToProcess);
            } else if (barId == 2349 || barId == 2350) {
                player.getSkillManager().addExperience(Skill.SMITHING, addxp * 3 * amountToProcess);
                player.getInventory().delete(secondOre, amountToProcess);
            } else if ((secondOre == 453)) {
                if ((player.getCoalBag() >= SmithingData.getCoalAmount(barId)) && (player.getInventory().contains(18339)) && (player.getCoalBag() >= SmithingData.getCoalAmount(barId))) {
                    player.setCoalBag(player.getCoalBag() - (SmithingData.getCoalAmount(barId) * amountToProcess));
                } else if (player.getInventory().getAmount(453) >= SmithingData.getCoalAmount(barId)) {
                    player.getInventory().delete(secondOre, (SmithingData.getCoalAmount(barId) * amountToProcess));
                } else {
                    player.getPacketSender().sendMessage("@red@You do not have enough coal to make those bars.");
                }
            }
            if (barId != 2351) { //Iron bar - 50% successrate
                if (ItemDefinition.forId(barId).isNoted() && (player.getInventory().contains(barId) || player.getInventory().getFreeSlots() > 0)) {
                    player.getInventory().add(barId, amountToProcess);
                } else if (player.getInventory().getFreeSlots() >= amountToProcess)
                    player.getInventory().add(barId, amountToProcess);
                else
                    GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(barId, amountToProcess), player.getPosition().copy(), player.getUsername(), false, 120, true, 120));

                player.getSkillManager().addExperience(Skill.SMITHING, addxp * amountToProcess);
                if (barId == 2363 || barId == 2364) {
                    player.getAchievementTracker().progress(AchievementData.SMELT_1000_RUNE_BARS, amountToProcess);
                }
                if (barId == 2361 || barId == 2362) {
                    player.getAchievementTracker().progress(AchievementData.SMELT_500_ADDY_BARS, amountToProcess);
                }
                if (barId == 2359 || barId == 2360) {
                    player.getAchievementTracker().progress(AchievementData.SMELT_250_MITHRIL_BARS, amountToProcess);
                }
                if (barId == 2353 || barId == 2354) {
                    player.getAchievementTracker().progress(AchievementData.SMELT_100_STEEL_BARS, amountToProcess);
                }
                if (barId == 2352) {
                    player.getAchievementTracker().progress(AchievementData.SMELT_25_IRON_BARS, amountToProcess);
                }
                if (barId == 2349 || barId == 2350) {
                    player.getAchievementTracker().progress(AchievementData.SMELT_10_BRONZE_BARS, amountToProcess);
                }

            } else if (SmithingData.ironOreSuccess(player)) {
                //Achievements.finishAchievement(player, AchievementData.SMELT_AN_IRON_BAR);
                if (ItemDefinition.forId(barId).isNoted() && (player.getInventory().contains(barId) || player.getInventory().getFreeSlots() > 0)) {
                    player.getInventory().add(barId, amountToProcess);
                } else if (player.getInventory().getFreeSlots() >= amountToProcess)
                    player.getInventory().add(barId, amountToProcess);
                else
                    GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(barId + 1, amountToProcess), player.getPosition().copy(), player.getUsername(), false, 120, true, 120));

                player.getSkillManager().addExperience(Skill.SMITHING, addxp * amountToProcess);

                player.getAchievementTracker().progress(AchievementData.SMELT_25_IRON_BARS, amountToProcess);
            } else
                player.getPacketSender().sendMessage("The Iron ore burns too quickly and you're unable to make an Iron bar.");
            Sounds.sendSound(player, Sound.SMELT_ITEM);
        }
    }


    public static int getExperience(int barId) {

        switch (barId) {
            case 2349: // Bronze bar
                return 10;
            case 2351: // Iron bar
                return 15;
            case 2355: // Silver bar
                return 20;
            case 2353: // Steel bar
                return 25;
            case 2357: // Gold bar
                return 50;
            case 2359: // Mithril bar
                return 100;
            case 2361: // Adamant bar
                return 150;
            case 2363: // Runite bar
                return 200;
        }

        return 0;
    }
}
