package com.arlania.world.content.minigames.impl.chambersofxeric.greatolm;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Direction;
import com.arlania.model.GameObject;
import com.arlania.model.Position;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.npc.NPC;

public class Phases {

    private static final String[] phases = new String[]{"@gre@acid", "@mag@crystal", "@red@flame"};

    public static void raisePower(RaidsParty party, int height) {

        int random = RandomUtility.inclusiveRandom(2);
        if (!party.getPhaseAttack().contains(phases[random])) {
            party.sendMessage("The Great Olm rises with the power of " + phases[random] + ".");
            party.getPhaseAttack().add(phases[random]);
        } else {
            if (!party.getPhaseAttack().contains(phases[0])) {
                party.sendMessage("The Great Olm rises with the power of " + phases[0] + ".");
                party.getPhaseAttack().add(phases[0]);
            } else if (!party.getPhaseAttack().contains(phases[1])) {
                party.sendMessage("The Great Olm rises with the power of " + phases[1] + ".");
                party.getPhaseAttack().add(phases[1]);
            } else if (!party.getPhaseAttack().contains(phases[2])) {
                party.sendMessage("The Great Olm rises with the power of " + phases[2] + ".");
                party.getPhaseAttack().add(phases[2]);
            }
        }
    }

    public static final int OLM_LEFT_HAND = 21555;
    public static final int OLM_HEAD = 21554;
    public static final int OLM_RIGHT_HAND = 21553;

    public static void startPhase1(RaidsParty party, int height) {

        if (party.getCurrentPhase() >= 1) {
            party.getPlayers().forEach(player -> System.out.println("Multiple attempts to create the first phase for great olm."));
            return;
        }

        party.setCurrentPhase(1);

        party.setLeftHandPosition(new Position(3238, 5733, height));
        party.setGreatOlmPosition(new Position(3238, 5738, height));
        party.setRightHandPosition(new Position(3238, 5743, height));

        NPC leftHandNpc = NPC.of(7555 + GameSettings.OSRS_NPC_OFFSET, party.getLeftHandPosition()); // left claw
        NPC greatolmNpc = NPC.of(7554 + GameSettings.OSRS_NPC_OFFSET, party.getGreatOlmPosition()); // olm head
        NPC rightHandNpc = NPC.of(7553 + GameSettings.OSRS_NPC_OFFSET, party.getRightHandPosition());// right claw

        leftHandNpc.setConstitution(8000 * party.getOwner().teamSize);
        greatolmNpc.setConstitution(20000 * party.getOwner().teamSize);
        rightHandNpc.setConstitution(8000 * party.getOwner().teamSize);

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
                party.setTransitionPhase(false);
                party.setLeftHandDead(false);
                party.setRightHandDead(false);
                party.setSwitchingPhases(false);
                raisePower(party, height);

                stop();
            }
        });
        party.getGreatOlmNpc().directionFacing = Direction.NONE;
        party.getGreatOlmNpc().previousDirectionFacing = Direction.NONE;

    }

    public static void startPhase2(RaidsParty party, int height) {

        party.getGreatOlmObject().performAnimation(OlmAnimations.goingDown);
        party.getGreatOlmNpc().setVisible(false);

        TaskManager.submit(new Task(3, party, false) {

            int tick = 0;

            @Override
            public void execute() {
                if (tick == 1) {
                    CustomObjects.spawnGlobalObject(new GameObject(329885, new Position(3238, 5733, height), 10, 1));
                    CustomObjects.spawnGlobalObject(new GameObject(329882, new Position(3238, 5738, height), 10, 1));
                    CustomObjects.spawnGlobalObject(new GameObject(329888, new Position(3238, 5743, height), 10, 1));

                }
                if (tick == 3) {
                    CustomObjects.spawnGlobalObject(new GameObject(329885, new Position(3238, 5733, height), 10, 1));
                    CustomObjects.spawnGlobalObject(new GameObject(329882, new Position(3238, 5738, height), 10, 1));
                    CustomObjects.spawnGlobalObject(new GameObject(329888, new Position(3238, 5743, height), 10, 1));
                    party.setTransitionPhase(true);

                }
                if (tick == 10) {
                    party.setTransitionPhase(false);
                    party.setLeftHandDead(false);
                    party.setRightHandDead(false);
                    party.setClenchedHand(false);
                    party.setLeftHandProtected(false);
                    party.setHeight(height);
                    party.setClenchedHandFirst(false);
                    party.setClenchedHandSecond(false);
                    party.setLeftHandPosition(new Position(3220, 5743, height));
                    party.setGreatOlmPosition(new Position(3220, 5738, height));
                    party.setRightHandPosition(new Position(3220, 5733, height));

                    Position leftHandNpc = new Position(party.getLeftHandPosition().getX() + 3, party.getLeftHandPosition().getY(), height);
                    Position RightHandNpc = new Position(party.getRightHandPosition().getX() + 3, party.getRightHandPosition().getY(), height);
                    Position greatolmNpc = new Position(party.getGreatOlmPosition().getX() + 3, party.getGreatOlmPosition().getY(), height);

                    party.setLeftHandNpc(NPC.of(OLM_LEFT_HAND, leftHandNpc)); // left claw
                    party.setRightHandNpc(NPC.of(OLM_RIGHT_HAND, RightHandNpc));// right claw

                    party.getGreatOlmNpc().setVisible(true);
                    party.getGreatOlmNpc().moveTo(greatolmNpc);

                    World.register(party.getLeftHandNpc());
                    World.register(party.getRightHandNpc());

                    party.setLeftHandObject(new GameObject(329883, party.getLeftHandPosition(), 10, 3));
                    party.setGreatOlmObject(new GameObject(329880, party.getGreatOlmPosition(), 10, 3));
                    party.setRightHandObject(new GameObject(329886, party.getRightHandPosition(), 10, 3));

                    CustomObjects.spawnGlobalObject(party.getLeftHandObject());
                    CustomObjects.spawnGlobalObject(party.getGreatOlmObject());
                    CustomObjects.spawnGlobalObject(party.getRightHandObject());

                    party.getGreatOlmObject().performAnimation(OlmAnimations.goingUp);
                    party.getLeftHandObject().performAnimation(OlmAnimations.goingUpLeftHand);
                    party.getRightHandObject().performAnimation(OlmAnimations.goingUpRightHand);

                    TaskManager.submit(new Task(5, party, false) {
                        @Override
                        public void execute() {
                            party.setLeftHandObject(new GameObject(329884, party.getLeftHandPosition(), 10, 3));
                            party.setGreatOlmObject(new GameObject(329881, party.getGreatOlmPosition(), 10, 3));
                            party.setRightHandObject(new GameObject(329887, party.getRightHandPosition(), 10, 3));

                            CustomObjects.spawnGlobalObject(party.getLeftHandObject());
                            CustomObjects.spawnGlobalObject(party.getGreatOlmObject());
                            CustomObjects.spawnGlobalObject(party.getRightHandObject());
                            party.setCurrentPhase(2);
                            party.setSwitchingPhases(false);
                            raisePower(party, height);

                            stop();
                        }
                    });
                    stop();

                }
                tick++;

            }
        });

    }

    public static void startPhase3(RaidsParty party, int height) {
        party.setLeftHandPosition(new Position(3238, 5733, height));
        party.setGreatOlmPosition(new Position(3238, 5738, height));
        party.setRightHandPosition(new Position(3238, 5743, height));

        //party.getGreatOlmObject().performAnimation(OlmAnimations.goingDown);
        //party.getGreatOlmNpc().setVisible(false);
        //party.getLeftHandObject().performAnimation(OlmAnimations.goingDownLeftHand);
        //party.getRightHandObject().performAnimation(OlmAnimations.goingDownRightHand);


        //party.setTransitionPhase(true);
        TaskManager.submit(new Task(30, party, false) {
            @Override
            public void execute() {
                party.getLeftHandNpc().moveTo(party.getLeftHandPosition());
                party.getGreatOlmNpc().moveTo(party.getGreatOlmPosition());
                party.getRightHandNpc().moveTo(party.getRightHandPosition());

                party.setLeftHandNpc(NPC.of(OLM_LEFT_HAND, party.getLeftHandPosition())); // left claw
                party.setRightHandNpc(NPC.of(OLM_RIGHT_HAND, party.getRightHandPosition()));// right claw

                party.getLeftHandNpc().setFreezeDelay(100000);
                party.getRightHandNpc().setFreezeDelay(100000);

                World.register(party.getLeftHandNpc());
                World.register(party.getRightHandNpc());

                party.setLeftHandObject(new GameObject(329883, party.getLeftHandPosition(), 10, 1));
                party.setGreatOlmObject(new GameObject(329880, party.getGreatOlmPosition(), 10, 1));
                party.setRightHandObject(new GameObject(329886, party.getRightHandPosition(), 10, 1));

                CustomObjects.spawnGlobalObject(party.getLeftHandObject());
                CustomObjects.spawnGlobalObject(party.getGreatOlmObject());
                CustomObjects.spawnGlobalObject(party.getRightHandObject());

                party.getLeftHandObject().performAnimation(OlmAnimations.goingUpLeftHand);
                party.getGreatOlmObject().performAnimation(OlmAnimations.goingUpEnraged);
                party.getRightHandObject().performAnimation(OlmAnimations.goingUpRightHand);

                party.getGreatOlmNpc().findNewTarget();

                TaskManager.submit(new Task(5, party, false) {
                    @Override
                    public void execute() {
                        party.setLeftHandObject(new GameObject(329884, party.getLeftHandPosition(), 10, 1));
                        party.setGreatOlmObject(new GameObject(329881, party.getGreatOlmPosition(), 10, 1));
                        party.setRightHandObject(new GameObject(329887, party.getRightHandPosition(), 10, 1));

                        CustomObjects.spawnGlobalObject(party.getLeftHandObject());
                        CustomObjects.spawnGlobalObject(party.getGreatOlmObject());
                        CustomObjects.spawnGlobalObject(party.getRightHandObject());

                        party.getGreatOlmNpc().setVisible(true);
                        party.getGreatOlmObject().performAnimation(OlmAnimations.faceMiddleEnraged);

                        party.setLeftHandDead(false);
                        party.setRightHandDead(false);
                        party.setTransitionPhase(false);
                        party.setClenchedHand(false);
                        party.setLeftHandProtected(false);
                        party.setHeight(height);
                        party.setClenchedHandFirst(false);
                        party.setClenchedHandSecond(false);
                        party.setCurrentPhase(3);
                        party.setSwitchingPhases(false);
                        raisePower(party, height);
                        stop();
                    }
                });
                stop();
            }
        });

    }

}
