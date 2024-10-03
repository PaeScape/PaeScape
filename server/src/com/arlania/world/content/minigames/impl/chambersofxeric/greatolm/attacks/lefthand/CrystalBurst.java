package com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.lefthand;


import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.CombatIcon;
import com.arlania.model.GameObject;
import com.arlania.model.Graphic;
import com.arlania.model.GraphicHeight;
import com.arlania.model.movement.MovementQueue;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.OlmAnimations;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.Attacks;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.player.Player;

public class CrystalBurst {

    public static void performAttack(RaidsParty party, int height) {
        party.setLeftHandAttackTimer(20);

        TaskManager.submit(new Task(1, party, true) {
            int tick = 0;

            @Override
            public void execute() {
                if (party.isLeftHandDead()) {
                    stop();
                }
                if (tick == 1) {
                    party.getLeftHandObject().performAnimation(OlmAnimations.flashingCrystalLeftHand);

                }
                if (tick == 3) {
                    int i = 0;

                    for (Player member : party.getPlayers()) {
                        if (member != null && member.getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                            party.getCrystalBursts()[i] = member.getPosition();
                            CustomObjects.spawnGlobalObject(new GameObject(330033, party.getCrystalBursts()[i], 10, 3));
                            i++;
                        }
                    }
                    party.setCrystalAmount(i);

                }

                if (tick == 7) {
                    party.getLeftHandObject().performAnimation(OlmAnimations.leftHand);

                    for (int i = 0; i < party.getCrystalAmount(); i++) {
                        CustomObjects.spawnGlobalObject(new GameObject(330034, party.getCrystalBursts()[i], 10, 3));
                        for (Player member : party.getPlayers()) {
                            if (member != null && member.getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                                if (member.getPosition().sameAs(party.getCrystalBursts()[i])) {
                                    member.sendMessage(
                                            "The crystal beneath your feet grows rapidly and shunts you to the side.");
                                    MovementQueue.stepAway(member);
                                    Attacks.hitPlayer(member, 200, 400, CombatType.MAGIC, CombatIcon.NONE, 2, false);
                                }
                            }
                        }
                    }
                }
                if (tick == 9) {
                    for (int i = 0; i < party.getCrystalAmount(); i++) {
                        party.getOwner().getPacketSender().sendGlobalGraphic(
                                new Graphic(Attacks.LEFTOVER_CRYSTALS, GraphicHeight.MIDDLE),
                                party.getCrystalBursts()[i]);

                        CustomObjects.spawnGlobalObject(new GameObject(-1, party.getCrystalBursts()[i], 10, 3));

                        for (Player player : party.getPlayers()) {
                            player.getPacketSender().sendObject(new GameObject(-1, party.getCrystalBursts()[i], 10, 3));
                        }
                    }

                    stop();
                }
                tick++;
            }
        });
    }

}
