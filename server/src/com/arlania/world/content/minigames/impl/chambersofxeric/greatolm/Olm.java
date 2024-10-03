package com.arlania.world.content.minigames.impl.chambersofxeric.greatolm;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Direction;
import com.arlania.model.GameObject;
import com.arlania.model.Locations.Location;
import com.arlania.model.Position;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.FallingCrystalsTransition;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.MagicAttack;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.OrbAttack;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.RangeAttack;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.lefthand.AutoHeal;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.lefthand.Lightning;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.lefthand.Swap;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.npc.NPC;

public class Olm {



    public static void startPhase1(RaidsParty party, int height) {

        if (party.getCurrentPhase() >= 1) {
            party.getPlayers().forEach(player -> System.out.println("Multiple attempts to create the first phase for great olm."));
            return;
        }

        party.setCurrentPhase(1);

        World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.OLM, party.getOwner().getPosition().getZ()));

        party.setLeftHandPosition(new Position(3238, 5733, height));
        party.setGreatOlmPosition(new Position(3238, 5738, height));
        party.setRightHandPosition(new Position(3238, 5743, height));

        NPC leftHandNpc = NPC.of(7555 + GameSettings.OSRS_NPC_OFFSET, party.getLeftHandPosition()); // left claw
        NPC greatolmNpc = NPC.of(7554 + GameSettings.OSRS_NPC_OFFSET, party.getGreatOlmPosition()); // olm head
        NPC rightHandNpc = NPC.of(7553 + GameSettings.OSRS_NPC_OFFSET, party.getRightHandPosition());// right claw

        party.setLeftHandNpc(leftHandNpc); // left claw
        party.setGreatOlmNpc(greatolmNpc); // olm head
        party.setRightHandNpc(rightHandNpc);// right claw

        World.register(party.getLeftHandNpc());
        World.register(party.getGreatOlmNpc());
        World.register(party.getRightHandNpc());

        leftHandNpc.setFreezeDelay(100000);
        greatolmNpc.setFreezeDelay(100000);
        rightHandNpc.setFreezeDelay(100000);

        party.setLeftHandObject(new GameObject(329883, party.getLeftHandPosition(), 10, 1));
        party.setGreatOlmObject(new GameObject(329880, party.getGreatOlmPosition(), 10, 1));
        party.setRightHandObject(new GameObject(329886, party.getRightHandPosition(), 10, 1));

        CustomObjects.spawnGlobalObject(party.getLeftHandObject());
        CustomObjects.spawnGlobalObject(party.getGreatOlmObject());
        CustomObjects.spawnGlobalObject(party.getRightHandObject());


        TaskManager.submit(new Task(1, party, false) {
            @Override
            public void execute() {
                party.getLeftHandObject().performAnimation(OlmAnimations.goingUpLeftHand);
                party.getGreatOlmObject().performAnimation(OlmAnimations.goingUp);
                party.getRightHandObject().performAnimation(OlmAnimations.goingUpRightHand);
                stop();
            }
        });
        TaskManager.submit(new Task(5, party, false) {
            @Override
            public void execute() {
                party.setLeftHandObject(new GameObject(329884, party.getLeftHandPosition(), 10, 1));
                party.setGreatOlmObject(new GameObject(329881, party.getGreatOlmPosition(), 10, 1));
                party.setRightHandObject(new GameObject(329887, party.getRightHandPosition(), 10, 1));

                CustomObjects.spawnGlobalObject(party.getLeftHandObject());
                CustomObjects.spawnGlobalObject(party.getGreatOlmObject());
                CustomObjects.spawnGlobalObject(party.getRightHandObject());
                party.setTransitionPhase(true);
                party.setLeftHandDead(false);
                party.setRightHandDead(false);
                party.setSwitchingPhases(false);
                party.setCurrentLeftHandCycle(0);
                //raisePower(party, height);
                sequence(party, height);
                stop();
            }
        });
        party.getGreatOlmNpc().directionFacing = Direction.NONE;
        party.getGreatOlmNpc().previousDirectionFacing = Direction.NONE;

    }


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

    public static void startTask(RaidsParty party, int height) {
        TaskManager.submit(new Task(1, party, false) {
            int tick = 0;

            @Override
            public void execute() {

                if ((party.isLeftHandDead() && party.isRightHandDead() && !party.isLastPhaseStarted())) {
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
                            CustomObjects.deleteGlobalObject(party.getRightHandObject());
                            //CustomObjects.spawnGlobalObject(new GameObject(329888, new Position(3220, 5733, height), 10, 3));
                            CustomObjects.spawnGlobalObject(new GameObject(329888, new Position(3238, 5743, height), 10, 1));
                            stop();
                        }
                    });
                }

                if (party.getLeftHandNpc().isDying() && !party.getLeftHandNpc().isDead() && party.getLeftHandObject() != null) {
                    party.getLeftHandNpc().isDead = true;
                    party.getLeftHandObject().performAnimation(OlmAnimations.goingDownLeftHand);
                    TaskManager.submit(new Task(2, party, false) {
                        @Override
                        public void execute() {
                            CustomObjects.deleteGlobalObject(party.getLeftHandObject());
//                            CustomObjects.spawnGlobalObject(new GameObject(329885, new Position(3220, 5743, height), 10, 3));
                            CustomObjects.spawnGlobalObject(new GameObject(329885, new Position(3238, 5733, height), 10, 1));
                            stop();
                        }
                    });
                }

                if (party.getGreatOlmNpc().isDying()) {
                    party.getGreatOlmObject().performAnimation(OlmAnimations.goingDownEnraged);
                    stop();
                }
                tick++;
            }
        });
    }

    public static void sequence(RaidsParty party, int height) {

        TaskManager.submit(new Task(1, party, false) {
            int tick = 0;

            @Override
            public void execute() {
                if (party.getGreatOlmNpc().isDying()) {
                    stop();
                    return;
                }

                party.setCanAttack(false);
                party.setCanAttackHand(false);

                if (tick % party.getOlmAttackTimer() == 0) {
                    party.setCanAttack(true);
                }

                if (!party.getGreatOlmNpc().isDying() && party.getGreatOlmNpc().getConstitution() > 0) {

                    if (party.isCanAttackHand() && !party.isLeftHandDead() && !party.isLeftHandProtected()) {
                        if (party.getCurrentLeftHandCycle() == 0) {
                            party.setCurrentLeftHandCycle(party.getCurrentLeftHandCycle() + 1);
                            Swap.performAttack(party, height);
                        } else if (party.getCurrentLeftHandCycle() == 1) {
                            party.setCurrentLeftHandCycle(party.getCurrentLeftHandCycle() + 1);
                            Lightning.performAttack(party, height);
                        } else if (party.getCurrentLeftHandCycle() == 2) {
                            party.setCurrentLeftHandCycle(0);
                            Lightning.performAttack(party, height);
                        }
                    } else if (party.getCurrentLeftHandCycle() == 3) {
                        party.setCurrentLeftHandCycle(0);
                        AutoHeal.performAttack(party, height);
                    }


                    if (party.getCanAttack()) {
                        party.setOlmAttacking(true);
                        if (party.getAttackCount() >= 5) {
                            party.setAttackCount(0);
                            FallingCrystalsTransition.performAttack(party, height, 6);
                        }
                        party.setAttackCount(party.getAttackCount() + 1);
                        int random = RandomUtility.inclusiveRandom(8);
                        if (random <= 3)
                            MagicAttack.performAttack(party, height);
                        else if (random <= 7)
                            RangeAttack.performAttack(party, height);
                        else if (random == 8)
                            OrbAttack.performAttack(party, height);
                    }

                }
                tick++;
            }

        });

    }

    public static int getOlmMinHit(RaidsParty party) {
        int difficulty = party.getOwner().difficulty;
        int minHit = 100 + (5 * difficulty);
        return minHit;
    }

    public static int getOlmMaxHit(RaidsParty party) {
        int difficulty = party.getOwner().difficulty;
        int maxHit = 250 + (10 * difficulty);
        return maxHit;
    }

    public static int getCrystalMinHit(RaidsParty party) {
        int difficulty = party.getOwner().difficulty;
        int maxHit = 100 + (5 * difficulty);
        return maxHit;
    }

    public static int getCrystalMaxHit(RaidsParty party) {
        int difficulty = party.getOwner().difficulty;
        int maxHit = 200 + (10 * difficulty);
        return maxHit;
    }



    public static void cleanupModels(int height) {
        // left hand
        CustomObjects.deleteGlobalObject(new GameObject(329884, new Position(3238, 5733, height), 10, 1));
        // head
        CustomObjects.deleteGlobalObject(new GameObject(329881, new Position(3238, 5738, height), 10, 1));
        // right hand
        CustomObjects.deleteGlobalObject(new GameObject(329887, new Position(3238, 5743, height), 10, 1));
        // left hand
        CustomObjects.deleteGlobalObject(new GameObject(329883, new Position(3238, 5733, height), 10, 1));
        // head
        CustomObjects.deleteGlobalObject(new GameObject(329880, new Position(3238, 5738, height), 10, 1));
        // right hand
        CustomObjects.deleteGlobalObject(new GameObject(329886, new Position(3238, 5743, height), 10, 1));
        // right hand rock
        CustomObjects.deleteGlobalObject(new GameObject(329885, new Position(3238, 5743, height), 10, 3));
        // left hand rock
        CustomObjects.deleteGlobalObject(new GameObject(329885, new Position(3238, 5733, height), 10, 3));
    }

    public static void destroyInstance(RaidsParty party, int height) {
        World.deregister(party.getGreatOlmNpc());
        World.deregister(party.getLeftHandNpc());
        World.deregister(party.getRightHandNpc());
        cleanupModels(height);
    }

}
