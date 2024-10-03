package com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks;


import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.OlmAnimations;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class LifeSiphon {

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
                for (int i = 0; i < 2; i++) {
                    if (tick == 1) {
                        Position pos = Attacks.randomLocation(height);
                        NPC spawn = NPC.of(5090, pos);
                        World.register(spawn);
                        new Projectile(party.getGreatOlmNpc(), spawn, Attacks.BLUE_SMALL_PROJECTILE, 60, 8, 70, 10, 0)
                                .sendProjectile();
                        party.getLifeSiphonPositions().add(pos);
                        party.getOwner().getPacketSender().sendGlobalGraphic(new Graphic(1363 + GameSettings.OSRS_GFX_OFFSET, GraphicHeight.LOW),
                                pos);

                        TaskManager.submit(new Task(10) {
                            @Override
                            public void execute() {
                                World.deregister(spawn);
                                stop();
                            }
                        });

                    }
                }
                if (tick == 10) {
                    for (Player member : party.getPlayers()) {
                        if (member != null && member.getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                            if (!member.getPosition().sameAs(party.getLifeSiphonPositions().get(0))
                                    && !member.getPosition().sameAs(party.getLifeSiphonPositions().get(1))) {
                                int hit = RandomUtility.inclusiveRandom(100, 300);
                                member.dealDamage(new Hit(hit, Hitmask.RED, CombatIcon.NONE));
                                party.getGreatOlmNpc().heal(hit * 4);
                            }
                        }
                    }
                }
                if (tick == 12) {
                    party.getLifeSiphonPositions().clear();
                    stop();
                }
                if (tick == 2) {
                    OlmAnimations.resetAnimation(party);
                }

                tick++;
            }
        });

    }

}
