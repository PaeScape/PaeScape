package com.arlania.world.content.skill.impl.firemaking;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.GameObject;
import com.arlania.model.Skill;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.movement.MovementQueue;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.Sounds;
import com.arlania.world.content.Sounds.Sound;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.skill.impl.dungeoneering.Dungeoneering;
import com.arlania.world.entity.impl.player.Player;

/**
 * The Firemaking skill
 *
 * @author Gabriel Hannason
 */

public class Firemaking {

    public static void lightFire(final Player player, int log, final boolean addingToFire, final int amount) {
        if (!player.getClickDelay().elapsed(2000) || player.getMovementQueue().isLockMovement())
            return;
        if (!player.getLocation().isFiremakingAllowed()) {
            player.getPacketSender().sendMessage("You can not light a fire in this area.");
            return;
        }
        boolean customObjectExists = CustomObjects.objectExists(player.getPosition().copy());
        if (!Dungeoneering.doingDungeoneering(player)) {
            if (customObjectExists && !addingToFire || player.getPosition().getZ() > 0 || !player.getMovementQueue().canWalk(1, 0) && !player.getMovementQueue().canWalk(-1, 0) && !player.getMovementQueue().canWalk(0, 1) && !player.getMovementQueue().canWalk(0, -1)) {
                player.getPacketSender().sendMessage("You can not light a fire here.");
                return;
            }
        }
        final Logdata.logData logData = Logdata.getLogData(player, log);
        if (logData == null)
            return;

        if(ItemDefinition.forId(log).isNoted() && !player.checkAchievementAbilities(player, "processor")) {
            player.getPacketSender().sendMessage("You need to unlock Processor (Achievement Abilitiy) to use noted logs.");
            return;
        }
        player.getMovementQueue().reset();
        if (customObjectExists && addingToFire)
            MovementQueue.stepAway(player);
        player.getPacketSender().sendInterfaceRemoval();
        player.setEntityInteraction(null);
        player.getSkillManager().stopSkilling();
        int cycle = 2 + RandomUtility.inclusiveRandom(3);
        if (player.getSkillManager().getCurrentLevel(Skill.FIREMAKING) < logData.getLevel()) {
            player.getPacketSender().sendMessage("You need a Firemaking level of atleast " + logData.getLevel() + " to light this.");
            return;
        }
        if (!addingToFire) {
            player.getPacketSender().sendMessage("You attempt to light a fire..");
            player.performAnimation(new Animation(733));
            player.getMovementQueue().setLockMovement(true);
        }
        player.setCurrentTask(new Task(addingToFire ? 2 : cycle, player, addingToFire) {
            int added = 0;


            @Override
            public void execute() {
                player.getPacketSender().sendInterfaceRemoval();

                int amountToBurn = player.acceleratedProcessing();

                if (amountToBurn > player.getInventory().getAmount(logData.getLogId()))
                    amountToBurn = player.getInventory().getAmount(logData.getLogId());

                if (addingToFire && player.getInteractingObject() == null) { //fire has died
                    player.getSkillManager().stopSkilling();
                    player.getPacketSender().sendMessage("The fire has died out.");
                    return;
                }
                if (player.getInventory().contains(2946) && RandomUtility.inclusiveRandom(2) == 1) {
                    player.getPacketSender().sendMessage("Your tinderbox's magic saves your materials!");
                } else {
                    player.getInventory().delete(logData.getLogId(), amountToBurn);
                }
                if (addingToFire) {
                    player.performAnimation(new Animation(827));
                    player.getPacketSender().sendMessage("You add some logs to the fire..");
                } else {
                    if (!player.getMovementQueue().isMoving()) {
                        player.getMovementQueue().setLockMovement(false);
                        player.performAnimation(new Animation(65535));
                        MovementQueue.stepAway(player);
                    }
                    CustomObjects.globalFiremakingTask(new GameObject(2732, player.getPosition().copy()), player, logData.getBurnTime());
                    player.getPacketSender().sendMessage("The fire catches and the logs begin to burn.");
                    stop();
                }
                if (logData == Logdata.logData.LOG || logData == Logdata.logData.NOTED_LOG) {
                    player.getAchievementTracker().progress(AchievementData.BURN_10_LOGS, amountToBurn);
                } else if (logData == Logdata.logData.OAK || logData == Logdata.logData.NOTED_OAK) {
                    player.getAchievementTracker().progress(AchievementData.BURN_25_OAKS, amountToBurn);
                } else if (logData == Logdata.logData.WILLOW || logData == Logdata.logData.NOTED_WILLOW) {
                    player.getAchievementTracker().progress(AchievementData.BURN_50_WILLOWS, amountToBurn);
                } else if (logData == Logdata.logData.MAPLE || logData == Logdata.logData.NOTED_MAPLE) {
                    player.getAchievementTracker().progress(AchievementData.BURN_100_MAPLES, amountToBurn);
                } else if (logData == Logdata.logData.YEW || logData == Logdata.logData.NOTED_YEW) {
                    player.getAchievementTracker().progress(AchievementData.BURN_250_YEWS, amountToBurn);
                } else if (logData == Logdata.logData.MAGIC || logData == Logdata.logData.NOTED_MAGIC) {
                    player.getAchievementTracker().progress(AchievementData.BURN_1000_MAGICS, amountToBurn);
                }
                Sounds.sendSound(player, Sound.LIGHT_FIRE);

                int addxp = logData.getXp();
                double bonusxp = 1;

                if (player.getEquipment().contains(220708))
                    bonusxp += .05;
                if (player.getEquipment().contains(220704))
                    bonusxp += .05;
                if (player.getEquipment().contains(220706))
                    bonusxp += .05;
                if (player.getEquipment().contains(220710))
                    bonusxp += .05;
                if (player.getEquipment().contains(220712))
                    bonusxp += .05;

                addxp *= bonusxp;


                player.getSkillManager().addExperience(Skill.FIREMAKING, addxp * amountToBurn);
                added++;
                if (added >= 28 || !player.getInventory().contains(logData.getLogId())) {
                    stop();
                }

            }

            @Override
            public void stop() {
                setEventRunning(false);
                player.performAnimation(new Animation(65535));
                player.getMovementQueue().setLockMovement(false);
                MovementQueue.stepAway(player);
                player.getPacketSender().sendInterfaceRemoval();
                player.setEntityInteraction(null);
            }
        });
        TaskManager.submit(player.getCurrentTask());
        player.getClickDelay().reset(System.currentTimeMillis() + 500);
    }

}