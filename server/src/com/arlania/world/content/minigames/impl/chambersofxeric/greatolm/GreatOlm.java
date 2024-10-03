package com.arlania.world.content.minigames.impl.chambersofxeric.greatolm;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.clip.region.RegionClipping;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.minigames.MinigameAttributes;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.player.Player;

/**
 * A class that will handle Great Olm spawning and sequence
 * THIS IS NEVER USED
 *
 * @author Ints
 */
public class GreatOlm {

    public static void start(final Player p) {
        p.getPacketSender().sendInterfaceRemoval();
        if (p.getMinigameAttributes().getRaidsAttributes().getParty() == null) {
            DialogueManager.sendStatement(p, "You need to be in a Raids party to begin.");
            return;
        }
        final RaidsParty party = p.getMinigameAttributes().getRaidsAttributes().getParty();


        if (party.getOwner() != p) {
            p.getPacketSender().sendMessage("Only the party leader can start the fight.");
            return;
        }

        final int height = p.getIndex() * 4;
        for (Player member : party.getPlayers()) {
            member.getPacketSender().sendInterfaceRemoval();
            MinigameAttributes.RaidsAttributes raidsAttributes = member.getMinigameAttributes().getRaidsAttributes();
            raidsAttributes.setInsideRaid(true);
            member.setRegionInstance(null);
            member.getMovementQueue().reset();
            member.getClickDelay().reset();
            member.moveTo(new Position(3232, 5730, height));
            member.getPacketSender().sendInteractionOption("null", 2, true);
            member.getMinigameAttributes().getRaidsAttributes().setInsideRaid(true);
            member.getSkillManager().stopSkilling();
            member.getPacketSender().sendClientRightClickRemoval();
        }
        for (Player player : party.getPlayers()) {
            player.getPacketSender().sendCameraNeutrality();
            player.getMinigameAttributes().getRaidsAttributes().setParty(party);
            player.getMinigameAttributes().getRaidsAttributes().setInsideRaid(true);
            player.getPacketSender().sendInteractionOption("null", 2, true);
            party.getPlayersInRaids().add(player);
        }
        party.setInstanceLevel(height);
        party.setDeaths(0);
        party.setKills(0);
        party.sendMessage("Welcome to the Great olm");
        party.setCanAttackLeftHand(false);
        party.setCanAttack(false);
        party.setOlmAttackTimer(6);
        party.setLeftHandAttackTimer(6);
        party.setCurrentPhase(0);
        party.setClenchedHand(false);
        party.setLeftHandProtected(false);
        party.setHeight(height);
        party.setClenchedHandFirst(false);
        party.setClenchedHandSecond(false);
        party.setUnClenchedHandFirst(false);
        party.setUnClenchedHandSecond(false);
        party.setLastPhaseStarted(false);
        party.setSwitchingPhases(false);

        // (party.getPlayersInRaidsDungeon(party) >= 1) {
        TaskManager.submit(new Task(1) {
            int tick = 0;

            @Override
            public void execute() {
                if (tick == 2) {
                    animationFix(party, height);
                }
                if (tick == 10)
                    startTask(party, height);
                if (tick == 20) {
                    GreatOlmCombat.sequence(party, height);
                    stop();
                }
                tick++;
            }
        });
        // }

    }

    public static void startTask(RaidsParty party, int height) {
        TaskManager.submit(new Task(1, party, false) {
            int tick = 0;

            @Override
            public void execute() {
                if (tick == 0) {
                    Phases.startPhase1(party, height);
                    party.setSwitchingPhases(false);
                    System.out.println("debug1");
                }

                if (party.getPlayersInRaidsLocation(party) == 0) {
                    destroyInstance(party, height);

                    stop();
                }

                phaseChange(party, height);
                if (tick >= 11) {
                    if (!party.isSwitchingPhases()) {
                        directionChange(party, height, tick);
                    }
                }

                if (party.getCurrentPhase() < 3 && party.getLeftHandNpc().getConstitution() <= 4900
                        && !party.isRightHandDead() && !party.isClenchedHand() && !party.isClenchedHandFirst()
                        && party.getLeftHandNpc().getConstitution() > 0) {
                    clenchHand(party, height);
                    party.setClenchedHandFirst(true);

                }

                if (party.getCurrentPhase() < 3
                        && party.getLeftHandNpc().getConstitution() <= 4000 + RandomUtility.inclusiveRandom(200)
                        && !party.isRightHandDead() && !party.isClenchedHand() && !party.isClenchedHandSecond()
                        && party.getLeftHandNpc().getConstitution() > 0) {
                    clenchHand(party, height);
                    party.setClenchedHandSecond(true);

                }

                if (party.getCurrentPhase() < 3 && party.getRightHandNpc().getConstitution() <= 4800
                        && party.isRightHandDead() && !party.isLeftHandDead() && party.isClenchedHand()
                        && !party.isUnClenchedHandFirst()) {
                    party.setUnClenchedHandFirst(true);
                    unClenchHand(party, height);
                }

                if (party.getCurrentPhase() < 3 && party.getRightHandNpc().getConstitution() <= 2500
                        && party.isRightHandDead() && !party.isLeftHandDead() && party.isClenchedHand()
                        && !party.isUnClenchedHandSecond()) {
                    party.setUnClenchedHandSecond(true);
                    unClenchHand(party, height);
                }

                if (party.getCurrentPhase() < 3 && party.getRightHandNpc().getConstitution() <= 0
                        && party.isRightHandDead() && !party.isLeftHandDead() && party.isClenchedHand()) {
                    unClenchHand(party, height);
                }

                if (!party.isLastPhaseStarted()
                        && (party.getCurrentPhase() == 3 && party.isLeftHandDead() && party.isRightHandDead())) {
                    party.setLastPhaseStarted(true);
                    party.sendMessage("The Great Olm is giving its all. This is its final stand.");
                }

                if (party.getRightHandNpc().isDying() || party.getRightHandNpc().getConstitution() <= 0) {
                    party.setCanAttackLeftHand(true);
                }
                if (party.getRightHandNpc().isDying() && !party.getRightHandNpc().isDead() && party.getRightHandObject() != null) {
                    party.getRightHandNpc().isDead = true;
                    party.getRightHandObject().performAnimation(OlmAnimations.goingDownRightHand);
                    TaskManager.submit(new Task(2, party, false) {
                        @Override
                        public void execute() {
                            CustomObjects.deleteGlobalObject(new GameObject(329888, new Position(3220, 5733, height), 10, 3));
                            CustomObjects.deleteGlobalObject(new GameObject(329888, new Position(3238, 5743, height), 10, 1));
                            CustomObjects.spawnGlobalObject(new GameObject(329888, new Position(3220, 5733, height), 10, 3));
                            stop();
                            CustomObjects.spawnGlobalObject(new GameObject(329888, new Position(3238, 5743, height), 10, 1));
                        }
                    });
                }

                if (party.getLeftHandNpc().isDying() && !party.getLeftHandNpc().isDead() && party.getLeftHandObject() != null) {
                    party.getLeftHandNpc().isDead = true;
                    party.getLeftHandObject().performAnimation(OlmAnimations.goingDownLeftHand);
                    TaskManager.submit(new Task(2, party, false) {
                        @Override
                        public void execute() {
                            CustomObjects.deleteGlobalObject(new GameObject(329885, new Position(3220, 5743, height), 10, 3));
                            CustomObjects.deleteGlobalObject(new GameObject(329885, new Position(3220, 5743, height), 10, 1));
                            CustomObjects.spawnGlobalObject(new GameObject(329885, new Position(3220, 5743, height), 10, 3));
                            CustomObjects.spawnGlobalObject(new GameObject(329885, new Position(3238, 5733, height), 10, 1));
                            stop();
                        }
                    });
                }

                if (party.getGreatOlmNpc().isDying()) {
                    party.getGreatOlmObject().performAnimation(OlmAnimations.goingDownEnraged);
                    finishDungeon(party, height);
                    stop();
                }
                tick++;
            }
        });
    }

    public static void cleanupModels(int height) {
        CustomObjects.deleteGlobalObject(new GameObject(329885, new Position(3238, 5733, height), 10, 1));
        CustomObjects.deleteGlobalObject(new GameObject(329885, new Position(3238, 5738, height), 10, 1));
        CustomObjects.deleteGlobalObject(new GameObject(329885, new Position(3238, 5743, height), 10, 1));
    }

    public static void finishDungeon(RaidsParty party, int height) {
        party.getGreatOlmObject().performAnimation(OlmAnimations.goingDown);

        cleanupModels(height);
//        CustomObjects.spawnGlobalObject(new GameObject(329885, new Position(3238, 5733, height), 10, 1));
//        CustomObjects.spawnGlobalObject(new GameObject(329882, new Position(3238, 5738, height), 10, 1));
//        CustomObjects.spawnGlobalObject(new GameObject(329888, new Position(3238, 5743, height), 10, 1));
        //  CustomObjects.spawnGlobalObject(new GameObject(329885, new Position(3220, 5743, height), 10, 3));
        // CustomObjects.spawnGlobalObject(new GameObject(329882, new Position(3220, 5738, height), 10, 3));
        // CustomObjects.spawnGlobalObject(new GameObject(329888, new Position(3220, 5733, height), 10, 3));


        GameObject crystal = new GameObject(330018, new Position(3232, 5749, height));
        TaskManager.submit(new Task(3) {
            @Override
            public void execute() {
                crystal.performAnimation(new Animation(7506 + GameSettings.OSRS_ANIM_OFFSET));
                GameObject chest = new GameObject(330028, new Position(3233, 5751, height), 10, 0);

                CustomObjects.spawnGlobalObject(chest);
                World.deregister(party.getGreatOlmNpc());
                World.deregister(party.getLeftHandNpc());
                World.deregister(party.getRightHandNpc());
                stop();
            }
        });

        TaskManager.submit(new Task(5) {
            @Override
            public void execute() {
                RegionClipping.removeObject(crystal);
                World.deregister(crystal);

                for (Player player : party.getPlayers()) {
                    player.getPacketSender().sendObject(new GameObject(-1, new Position(3232, 5749, height), 10, 3));
                }

                stop();
            }
        });

        //RaidsReward.grantLoot(party);

        //String message = "<col=996633>" + party.getOwner().getUsername() + "'s raid party has just defeated the Great Olm!";
        //World.sendMessage(message);
    }

    public static void destroyInstance(RaidsParty party, int height) {
        World.deregister(party.getGreatOlmNpc());
        World.deregister(party.getLeftHandNpc());
        World.deregister(party.getRightHandNpc());
        CustomObjects.deleteGlobalObject(new GameObject(329885, new Position(3220, 5743, height), 10, 3));
        CustomObjects.deleteGlobalObject(new GameObject(329885, new Position(3220, 5738, height), 10, 3));
        CustomObjects.deleteGlobalObject(new GameObject(329885, new Position(3220, 5733, height), 10, 3));
        CustomObjects.deleteGlobalObject(new GameObject(329885, new Position(3238, 5733, height), 10, 1));
        CustomObjects.deleteGlobalObject(new GameObject(329885, new Position(3238, 5738, height), 10, 1));
        CustomObjects.deleteGlobalObject(new GameObject(329885, new Position(3238, 5743, height), 10, 1));
//        CustomObjects.spawnGlobalObject(new GameObject(329885, new Position(3220, 5743, height), 10, 3));
//        CustomObjects.spawnGlobalObject(new GameObject(329882, new Position(3220, 5738, height), 10, 3));
//        CustomObjects.spawnGlobalObject(new GameObject(329888, new Position(3220, 5733, height), 10, 3));
//        CustomObjects.spawnGlobalObject(new GameObject(329885, new Position(3238, 5733, height), 10, 1));
//        CustomObjects.spawnGlobalObject(new GameObject(329882, new Position(3238, 5738, height), 10, 1));
//        CustomObjects.spawnGlobalObject(new GameObject(329888, new Position(3238, 5743, height), 10, 1));
    }

    public static void phaseChange(RaidsParty party, int height) {

        if (party.getCurrentPhase() == 1 && party.isLeftHandDead() && party.isRightHandDead()
                && !party.isSwitchingPhases()) {
            System.out.println("debug3");
            party.setLeftHandDead(false);
            party.setRightHandDead(false);
            party.setSwitchingPhases(true);
            TaskManager.submit(new Task(2, party, false) {
                @Override
                public void execute() {
                    Phases.startPhase2(party, height);
                    stop();
                }
            });
        }

        if (party.getCurrentPhase() == 2 && party.isLeftHandDead() && party.isRightHandDead()
                && !party.isSwitchingPhases()) {
            party.setLeftHandDead(false);
            party.setRightHandDead(false);
            party.setSwitchingPhases(true);
            TaskManager.submit(new Task(2, party, false) {
                @Override
                public void execute() {
                    Phases.startPhase3(party, height);
                    stop();

                }
            });
        }
    }

    public static void directionChange(RaidsParty party, int height, int tick) {

        int middlePositions = 0;
        int southPositions = 0;
        int northPositions = 0;
        for (Player member : party.getPlayers()) {
            if (member != null && member.getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                if (member.getPosition().getY() >= 5743) {
                    northPositions++;
                } else if (member.getPosition().getY() <= 5737) {
                    southPositions++;
                } else {
                    middlePositions++;
                }
            }
        }
        boolean switchDirections = tick % party.getOlmAttackTimer() == 0;

        if ((switchDirections && !party.isTransitionPhase()) || party.isSwitchAfterAttack()) {
            if (party.isOlmAttacking()) {
                party.setSwitchAfterAttack(true);
            } else {
                party.setOlmTurning(true);
                party.setSwitchAfterAttack(false);
                if ((party.getGreatOlmNpc().previousDirectionFacing == party.getGreatOlmNpc().directionFacing)) {
                    party.setOlmTurning(false);
                } else {
                    TaskManager.submit(new Task(2) {
                        @Override
                        public void execute() {
                            party.setOlmTurning(false);
                            stop();
                        }
                    });
                }
                party.getGreatOlmNpc().previousDirectionFacing = party.getGreatOlmNpc().directionFacing;

                if (northPositions > southPositions && northPositions > middlePositions) {
                    party.getGreatOlmNpc().directionFacing = Direction.NORTH;
                } else if (southPositions > northPositions && southPositions > middlePositions) {
                    party.getGreatOlmNpc().directionFacing = Direction.SOUTH;
                } else {
                    party.getGreatOlmNpc().directionFacing = Direction.NONE;
                }

                if (party.getGreatOlmNpc().directionFacing != party.getGreatOlmNpc().previousDirectionFacing) {
                    if (party.getGreatOlmNpc().getPosition().getX() >= 3238)
                        DirectionSwitching.switchDirectionsEast(party);
                    else
                        DirectionSwitching.switchDirectionsWest(party);
                }
            }
        }
    }

    public static void clenchHand(RaidsParty party, int height) {
        party.getLeftHandObject().performAnimation(OlmAnimations.clinchingLeftHand);
        party.setClenchedHand(true);
        party.setLeftHandProtected(true);
        TaskManager.submit(new Task(2) {
            @Override
            public void execute() {
                party.getLeftHandObject().performAnimation(OlmAnimations.clenchedLeftHand);
                party.sendMessage("The Great Olm's left claw clenches to protect itself temporarily.");
                stop();
            }
        });
    }

    public static void unClenchHand(RaidsParty party, int height) {
        party.getLeftHandObject().performAnimation(OlmAnimations.backToNormalLeftHand);
        party.setClenchedHand(false);
        party.setLeftHandProtected(false);
        TaskManager.submit(new Task(2) {
            @Override
            public void execute() {
                party.getLeftHandObject().performAnimation(OlmAnimations.leftHand);
                stop();
            }
        });
        TaskManager.submit(new Task(3) {
            @Override
            public void execute() {
                stop();
            }
        });
    }


    public static boolean insideChamber(Player player) {
        return player.getLocation() == Locations.Location.OLM && player.getPosition().getY() >= 5730;
    }

    public static boolean withinOlmRange(Player player) {
        return player.getLocation() == Locations.Location.OLM && player.getPosition().getY() >= 5730 && player.getPosition().getY() <= 5750;
    }

    /**
     * Fix for the animation issue of olms hands.
     *
     * @param party
     * @param height
     */
    public static void animationFix(RaidsParty party, int height) {
        party.setLeftHandPosition(new Position(3238, 5733, height));
        party.setGreatOlmPosition(new Position(3238, 5738, height));
        party.setRightHandPosition(new Position(3238, 5743, height));
        party.setLeftHandObject(new GameObject(329883, party.getLeftHandPosition(), 10, 1));
        party.setGreatOlmObject(new GameObject(329880, party.getGreatOlmPosition(), 10, 1));
        party.setRightHandObject(new GameObject(329886, party.getRightHandPosition(), 10, 1));
        party.getLeftHandObject().performAnimation(OlmAnimations.goingUpLeftHand);
        party.getGreatOlmObject().performAnimation(OlmAnimations.goingUp);
        party.getRightHandObject().performAnimation(OlmAnimations.goingUpRightHand);
    }

}
