package com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.special;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.OlmAnimations;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.Attacks;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class AcidSpray {

    public static void performAttack(RaidsParty party, int height) {
        party.getGreatOlmNpc().performGreatOlmAttack(party);
        party.setOlmAttackTimer(6);

        TaskManager.submit(new Task(1, party, true) {
            int tick = 0;

            @Override
            public void execute() {
                if (party.getGreatOlmNpc().isDying() || party.isSwitchingPhases()) {
                    stop();
                }
                if (tick == 1) {
                    for (int i = 0; i < 10; i++) {
                        Position acidSpotPosition = Attacks.randomLocation(height);
                        party.getAcidPoolsNpcs()[i] = NPC.of(5090, acidSpotPosition);

                        World.register(party.getAcidPoolsNpcs()[i]);
                        new Projectile(party.getGreatOlmNpc(), party.getAcidPoolsNpcs()[i],
                                Attacks.DARK_GREEN_SMALL_PROJECTILE, 60, 8, 70, 10, 0).sendProjectile();
                        // GameObject pool = new GameObject(30032, acidSpotPosition);
                        // GameObjects.acidPool(pool, party.getOwner(), 15);

                    }
                }
                if (tick == 2) {
                    OlmAnimations.resetAnimation(party);
                }
                if (tick == 3) {
                    for (int i = 0; i < 10; i++) {
                        Position acidSpotPosition = party.getAcidPoolsNpcs()[i].getPosition();
                        GameObject pool = new GameObject(330032, acidSpotPosition);
                        CustomObjects.spawnTempObject(pool, party.getOwner(), 15);
                    }
                }
                if (tick >= 2 && tick <= 18) {
                    for (Player member : party.getPlayers()) {
                        if (member != null && member.getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                            for (int i = 0; i < 7; i++) {
                                if (member.getPosition().sameAs(party.getAcidPoolsNpcs()[i].getPosition())) {
                                    member.dealDamage(new Hit(
                                            5 + RandomUtility.inclusiveRandom(30), Hitmask.DARK_GREEN, CombatIcon.NONE));
                                }
                            }
                        }
                    }
                }
                if (tick == 19) {
                    for (int i = 0; i < 10; i++) {
                        World.deregister(party.getAcidPoolsNpcs()[i]);

                        for (Player player : party.getPlayers()) {
                            player.getPacketSender().sendObject(new GameObject(-1, party.getAcidPoolsNpcs()[i].getPosition(), 10, 3));
                        }
                    }
                    stop();
                }
                tick++;
            }
        });

    }

}
