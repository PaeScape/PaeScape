package com.arlania.world.content.skill.impl.cooking;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.GroundItem;
import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.input.impl.EnterAmountToCook;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;

public class Cooking {

    public static void selectionInterface(Player player, CookingData cookingData) {
        if (cookingData == null)
            return;

        if (ItemDefinition.forId(cookingData.getRawItem()).isNoted() && !player.checkAchievementAbilities(player, "processor")) {
            player.getPacketSender().sendMessage("You must unlock the Processor achievement ability to cook noted fish.");
            return;
        }

        player.setSelectedSkillingItem(cookingData.getRawItem());
        player.setInputHandling(new EnterAmountToCook());
        player.getPacketSender().sendString(2799, ItemDefinition.forId(cookingData.getCookedItem()).getName()).sendInterfaceModel(1746, cookingData.getCookedItem(), 150).sendChatboxInterface(4429);
        player.getPacketSender().sendString(2800, "How many would you like to cook?");
    }

    public static void cook(final Player player, final int rawFish, final int amount) {
        final CookingData fish = CookingData.forFish(rawFish);
        if (fish == null)
            return;
        player.getSkillManager().stopSkilling();
        player.getPacketSender().sendInterfaceRemoval();
        if (!CookingData.canCook(player, rawFish))
            return;
        player.performAnimation(new Animation(896));

        player.setCurrentTask(new Task(2, player, false) {
            int amountCooked = 0;

            @Override
            public void execute() {
                if (!CookingData.canCook(player, rawFish)) {
                    stop();
                    return;
                }

                int amountToProcess = player.acceleratedProcessing();

                if (player.getInventory().getAmount(rawFish) < amountToProcess)
                    amountToProcess = player.getInventory().getAmount(rawFish);


                player.performAnimation(new Animation(896));
                player.getInventory().delete(rawFish, amountToProcess);
                if (!CookingData.success(player, 3, fish.getLevelReq(), fish.getStopBurn()) && !player.getEquipment().contains(775)) {
                    player.getInventory().add(fish.getBurntItem(), amountToProcess);
                    player.getPacketSender().sendMessage("You accidently burn the " + fish.getName() + ".");
                } else {

                    if (ItemDefinition.forId(fish.getCookedItem()).isNoted() && (player.getInventory().contains(fish.getCookedItem()) || player.getInventory().getFreeSlots() > 0)) {
                        player.getInventory().add(fish.getCookedItem(), amountToProcess);
                    } else if (player.getInventory().getFreeSlots() >= amountToProcess)
                        player.getInventory().add(fish.getCookedItem(), amountToProcess);
                    else if (ItemDefinition.forId(fish.getCookedItem()).isNoted()) {
                        GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(fish.getCookedItem(), amountToProcess), player.getPosition().copy(), player.getUsername(), false, 120, true, 120));
                    } else if (!ItemDefinition.forId(fish.getCookedItem()).isNoted()) {
                        GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(fish.getCookedItem() + 1, amountToProcess), player.getPosition().copy(), player.getUsername(), false, 120, true, 120));
                    }

                    player.getSkillManager().addExperience(Skill.COOKING, fish.getXp() * amountToProcess);
                    if (fish == CookingData.SHRIMP || fish == CookingData.NOTED_SHRIMP) {
                        player.getAchievementTracker().progress(AchievementData.COOK_10_SHRIMP, amountToProcess);
                    } else if (fish == CookingData.TROUT || fish == CookingData.NOTED_TROUT) {
                        player.getAchievementTracker().progress(AchievementData.COOK_25_TROUT, amountToProcess);
                    } else if (fish == CookingData.SALMON || fish == CookingData.NOTED_SALMON) {
                        player.getAchievementTracker().progress(AchievementData.COOK_50_SALMON, amountToProcess);
                    } else if (fish == CookingData.LOBSTER || fish == CookingData.NOTED_LOBSTER) {
                        player.getAchievementTracker().progress(AchievementData.COOK_100_LOBSTER, amountToProcess);
                    } else if (fish == CookingData.SHARK || fish == CookingData.NOTED_SHARK) {
                        player.getAchievementTracker().progress(AchievementData.COOK_250_SHARK, amountToProcess);
                    } else if (fish == CookingData.ROCKTAIL || fish == CookingData.NOTED_ROCKTAIL) {
                        player.getAchievementTracker().progress(AchievementData.COOK_1000_ROCKTAIL, amountToProcess);
                    }
                }
                amountCooked += amountToProcess;

                if (player.getSkiller().getSkillerTask().getObjId()[0] == fish.getRawItem()) {
                    for (int k = 0; k < amountToProcess; k++) {
                        player.getSkiller().handleSkillerTaskGather(true);
                        player.getSkillManager().addExperience(Skill.SKILLER, player.getSkiller().getSkillerTask().getXP());
                    }
                }

                if (amountCooked >= 27 * (player.acceleratedProcessing()))
                    stop();
            }

            @Override
            public void stop() {
                setEventRunning(false);
                player.setSelectedSkillingItem(-1);
                player.performAnimation(new Animation(65535));
            }
        });
        TaskManager.submit(player.getCurrentTask());
    }
}