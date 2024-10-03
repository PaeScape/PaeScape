package com.arlania.world.content.skill.impl.farming;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.GameMode;
import com.arlania.model.Skill;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.input.impl.EnterAmountOfSeedsToPlant;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.entity.impl.player.Player;


public class SeedsOnPatch {

    public static void openInterface(Player player, int itemId) {
        player.getSkillManager().stopSkilling();
        player.setSelectedSkillingItem(itemId);
        player.setInputHandling(new EnterAmountOfSeedsToPlant());
        player.getPacketSender().sendString(2799, ItemDefinition.forId(itemId).getName()).sendInterfaceModel(1746, itemId, 150).sendChatboxInterface(4429);
        player.getPacketSender().sendString(2800, "How many would you like to plant?");
    }

    public static void offerSeeds(final Player player, final int amount) {
        final int seedId = player.getSelectedSkillingItem();
        player.getSkillManager().stopSkilling();
        final SeedsData currentSeed = SeedsData.forId(seedId);
        if (currentSeed == null)
            return;
        player.getPacketSender().sendInterfaceRemoval();
        player.setCurrentTask(new Task(2, player, true) {
            int amountPlanted = 0;

            @Override
            public void execute() {
                if (currentSeed.getLevelReq() > player.getSkillManager().getCurrentLevel(Skill.FARMING)) {
                    player.getPacketSender().sendMessage("You need a farming level of " + currentSeed.getLevelReq() + " to plant this herb");
                    stop();
                    return;
                }
                if (amountPlanted >= amount) {
                    stop();
                    return;
                }
                if (amountPlanted == 27) {
                    stop();
                    return;
                }
                if (!player.getInventory().contains(seedId)) {
                    player.getPacketSender().sendMessage("You have run out of " + ItemDefinition.forId(seedId).getName() + ".");
                    stop();
                    return;
                }
                if (player.getInteractingObject() != null) {
                    player.setPositionToFace(player.getInteractingObject().getPosition().copy());
                    //player.getInteractingObject().performGraphic(new Graphic(624));
                }

                int amountToProcess = player.acceleratedProcessing();

                if (player.getInventory().getAmount(seedId) < amountToProcess)
                    amountToProcess = player.getInventory().getAmount(seedId);


                amountPlanted++;
                player.getInventory().delete(seedId, amountToProcess);
                player.performAnimation(new Animation(2274));
                int herbs = RandomUtility.inclusiveRandom(2, 5) * amountToProcess;

                if ((player.getInventory().contains(7409)) || (player.getEquipment().contains(7409)))
                    herbs += 3 * amountToProcess;

                /*if (player.Botanist == 1)
                    herbs *= 2;*/

                int addxp = currentSeed.getPlantingXP() * herbs;

                double bonusxp = 1;

                if (player.getEquipment().contains(213646))
                    bonusxp += .05;
                if (player.getEquipment().contains(213642))
                    bonusxp += .05;
                if (player.getEquipment().contains(213640))
                    bonusxp += .05;
                if (player.getEquipment().contains(213644))
                    bonusxp += .05;

                addxp *= bonusxp;


                player.getSkillManager().addExperience(Skill.FARMING, addxp);


                if (player.checkAchievementAbilities(player, "gatherer") && ItemDefinition.forId(currentSeed.getGrimyID() + 1).isNoted()) {
                    player.getInventory().add(currentSeed.getGrimyID() + 1, herbs);
                } else if (player.getGameMode() == GameMode.SEASONAL_IRONMAN & player.Harvester && ItemDefinition.forId(currentSeed.getGrimyID() + 1).isNoted()) {
                    player.getInventory().add(currentSeed.getGrimyID() + 1, herbs);
                } else {
                    player.getInventory().add(currentSeed.getGrimyID(), herbs);
                }


                if (seedId == 5291) {
                    player.getAchievementTracker().progress(AchievementData.FARM_10_GUAM, amountToProcess);
                } else if (seedId == 5292) {
                    player.getAchievementTracker().progress(AchievementData.FARM_25_MARRENTIL, amountToProcess);
                } else if (seedId == 5295) {
                    player.getAchievementTracker().progress(AchievementData.FARM_50_RANARR, amountToProcess);
                } else if (seedId == 5297) {
                    player.getAchievementTracker().progress(AchievementData.FARM_100_IRIT, amountToProcess);
                } else if (seedId == 5303) {
                    player.getAchievementTracker().progress(AchievementData.FARM_250_DWARF_WEED, amountToProcess);
                } else if (seedId == 5304) {
                    player.getAchievementTracker().progress(AchievementData.FARM_1000_TORSTOL, amountToProcess);
                }


            }

            @Override
            public void stop() {
                setEventRunning(false);
                player.getPacketSender().sendMessage("You have planted " + (amountPlanted == 1 ? "seed" : "seeds") + ".");
            }
        });
        TaskManager.submit(player.getCurrentTask());
    }
}
