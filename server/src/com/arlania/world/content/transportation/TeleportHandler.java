package com.arlania.world.content.transportation;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.model.Locations.Location;
import com.arlania.model.Position;
import com.arlania.world.content.Sounds;
import com.arlania.world.content.Sounds.Sound;
import com.arlania.world.content.skill.impl.dungeoneering.Dungeoneering;
import com.arlania.world.entity.impl.player.Player;


public class TeleportHandler {

    public static void teleportPlayer(final Player player, final Position targetLocation, final TeleportType teleportType) {
        if (teleportType != TeleportType.LEVER) {
            if (!checkReqs(player, targetLocation)) {
                return;
            }
        }
        if (player.getLocation() == Location.CONSTRUCTION) {
            player.getPacketSender().sendMessage("Please use the portal to exit your house");
            return;
        }
        if (!player.getClickDelay().elapsed(4500) || player.getMovementQueue().isLockMovement())
            return;

        player.setTeleporting(true).getMovementQueue().setLockMovement(true).reset();
        cancelCurrentActions(player);
        player.performAnimation(teleportType.getStartAnimation());
        player.performGraphic(teleportType.getStartGraphic());
        Sounds.sendSound(player, Sound.TELEPORT);
        if (player.getRegionInstance() != null)
            player.getRegionInstance().destruct();
        TaskManager.submit(new Task(1, player, true) {
            int tick = 0;

            @Override
            public void execute() {
                switch (teleportType) {
                    case LEVER:
                        if (tick == 0)
                            player.performAnimation(new Animation(2140));
                        else if (tick == 2) {
                            player.performAnimation(new Animation(8939, 20));
                            player.performGraphic(new Graphic(1576));
                        } else if (tick == 4) {
                            player.performAnimation(new Animation(8941));
                            player.performGraphic(new Graphic(1577));
                            player.moveTo(targetLocation).setPosition(targetLocation);
                            player.getMovementQueue().setLockMovement(false).reset();
                            stop();
                        }
                        break;
                    default:
                        if (tick == teleportType.getStartTick()) {
                            cancelCurrentActions(player);
                            player.performAnimation(teleportType.getEndAnimation());
                            player.performGraphic(teleportType.getEndGraphic());

                            if (Dungeoneering.doingDungeoneering(player)) {
                                final Position dungEntrance = player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getDungeoneeringFloor().getEntrance().copy().setZ(player.getPosition().getZ());
                                player.moveTo(dungEntrance).setPosition(dungEntrance);
                            } else {
                                player.moveTo(targetLocation).setPosition(targetLocation);
                            }

                            player.setTeleporting(false);
                        } else if (tick == teleportType.getStartTick() + 3) {
                            player.getMovementQueue().setLockMovement(false).reset();
                        } else if (tick == teleportType.getStartTick() + 4)
                            stop();
                        break;
                }
                tick++;
            }

            @Override
            public void stop() {
                setEventRunning(false);
                player.setTeleporting(false);
                player.getClickDelay().reset(0);
            }
        });
        player.getClickDelay().reset();
    }

    public static void teleportPlayer2(final Player player, final Position targetLocation, final TeleportType teleportType) {
        if (teleportType != TeleportType.LEVER) {
            if (!checkReqs(player, targetLocation)) {
                return;
            }
        }
        if (player.getLocation() == Location.CONSTRUCTION) {
            player.getPacketSender().sendMessage("Please use the portal to exit your house");
            return;
        }
        if (!player.getClickDelay().elapsed(4500) || player.getMovementQueue().isLockMovement())
            return;
        player.setTeleporting(true).getMovementQueue().setLockMovement(true).reset();
        cancelCurrentActions(player);
        player.performAnimation(teleportType.getStartAnimation());
        player.performGraphic(teleportType.getStartGraphic());
        Sounds.sendSound(player, Sound.TELEPORT);
        TaskManager.submit(new Task(1, player, true) {
            int tick = 0;

            @Override
            public void execute() {
                switch (teleportType) {
                    case LEVER:
                        if (tick == 0)
                            player.performAnimation(new Animation(2140));
                        else if (tick == 2) {
                            player.performAnimation(new Animation(8939, 20));
                            player.performGraphic(new Graphic(1576));
                        } else if (tick == 4) {
                            player.performAnimation(new Animation(8941));
                            player.performGraphic(new Graphic(1577));
                            player.moveTo(targetLocation).setPosition(targetLocation);
                            player.getMovementQueue().setLockMovement(false).reset();
                            stop();
                        }
                        break;
                    default:
                        if (tick == teleportType.getStartTick()) {
                            cancelCurrentActions(player);
                            player.performAnimation(teleportType.getEndAnimation());
                            player.performGraphic(teleportType.getEndGraphic());

                            if (Dungeoneering.doingDungeoneering(player)) {
                                final Position dungEntrance = player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getDungeoneeringFloor().getEntrance().copy().setZ(player.getPosition().getZ());
                                player.moveTo(dungEntrance).setPosition(dungEntrance);
                            } else {
                                player.moveTo(targetLocation).setPosition(targetLocation);
                            }

                            player.setTeleporting(false);
                        } else if (tick == teleportType.getStartTick() + 3) {
                            player.getMovementQueue().setLockMovement(false).reset();
                        } else if (tick == teleportType.getStartTick() + 4)
                            stop();
                        break;
                }
                tick++;
            }

            @Override
            public void stop() {
                setEventRunning(false);
                player.setTeleporting(false);
                player.getClickDelay().reset(0);
            }
        });
        player.getClickDelay().reset();
    }


    public static boolean interfaceOpen(Player player) {
        if (player.getInterfaceId() > 0 && player.getInterfaceId() != 50100) {
            player.getPacketSender().sendMessage("Please close the interface you have open before opening another.");
            return true;
        }
        return false;
    }

    public static boolean checkReqs(Player player, Position targetLocation) {

        if (player.getStaffRights().getStaffRank() < 9) {
            if (player.getConstitution() <= 0)
                return false;
            if (player.getTeleblockTimer() > 0) {
                player.getPacketSender().sendMessage("A magical spell is blocking you from teleporting.");
                return false;
            }
            if (player.getLocation() != null && !player.getLocation().canTeleport(player))
                return false;
            if (player.isPlayerLocked() || player.isCrossingObstacle()) {
                player.getPacketSender().sendMessage("You cannot teleport right now.");
                return false;
            }
        }

        return true;
    }

    public static void cancelCurrentActions(Player player) {
        player.getPacketSender().sendInterfaceRemoval();
        player.setTeleporting(false);
        player.setWalkToTask(null);
        player.setInputHandling(null);
        player.getSkillManager().stopSkilling();
        player.setEntityInteraction(null);
        player.getMovementQueue().setFollowCharacter(null);
        player.getCombatBuilder().cooldown(false);
        player.setResting(false);
    }
}
