package com.arlania.world.content.skill.impl.prayer;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.model.Locations.Location;
import com.arlania.model.Skill;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.input.impl.EnterAmountOfBonesToSacrifice;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;


public class BonesOnAltar {

    public static void openInterface(Player player, int itemId) {
        player.getSkillManager().stopSkilling();
        player.setSelectedSkillingItem(itemId);
        player.setInputHandling(new EnterAmountOfBonesToSacrifice());
        player.getPacketSender().sendString(2799, ItemDefinition.forId(itemId).getName()).sendInterfaceModel(1746, itemId, 150).sendChatboxInterface(4429);
        player.getPacketSender().sendString(2800, "How many would you like to offer?");
    }

    public static void offerBones(final Player player, final int boneId, final int amount) {

        final BonesData currentBone = BonesData.forId(boneId);
        if (currentBone == null)
            return;

        if (ItemDefinition.forId(boneId).isStackable() && !player.checkAchievementAbilities(player, "processor")) {
            player.getPacketSender().sendMessage("You need to unlock Processor (Achievement Abilitiy) to use noted bones.");
            return;
        }
        player.getPacketSender().sendInterfaceRemoval();
        player.setCurrentTask(new Task(2, player, true) {
            int amountSacrificed = 0;

            @Override
            public void execute() {
                if (amountSacrificed >= amount * player.acceleratedProcessing()) {
                    stop();
                    return;
                }
                if (!player.getInventory().contains(boneId)) {
                    player.getPacketSender().sendMessage("You have run out of " + ItemDefinition.forId(boneId).getName() + ".");
                    stop();
                    return;
                }
                if (player.getInteractingObject() != null) {
                    player.setPositionToFace(player.getInteractingObject().getPosition().copy());
                    player.getInteractingObject().performGraphic(new Graphic(624));
                }

                int amountToSacrifice = player.acceleratedProcessing();

                if (player.getInventory().getAmount(boneId) < amountToSacrifice)
                    amountToSacrifice = player.getInventory().getAmount(boneId);

                amountSacrificed += amountToSacrifice;
                player.getInventory().delete(boneId, amountToSacrifice);

                if (player.getLocation() == Location.WILDERNESS) {
                    if (RandomUtility.inclusiveRandom(3) == 0) {
                        player.getPacketSender().sendMessage("@red@The magic of the Chaos Altar puts a bone in your inventory!");
                        player.getInventory().add(boneId, amountToSacrifice);
                    }
                }

                player.performAnimation(new Animation(713));
                player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 5 * amountToSacrifice);
            }

            @Override
            public void stop() {
                setEventRunning(false);
                player.getPacketSender().sendMessage("You have pleased the gods with your " + (amountSacrificed == 1 ? "sacrifice" : "sacrifices") + ".");
            }
        });
        TaskManager.submit(player.getCurrentTask());
    }
}
