package com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.special;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.OlmAnimations;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.Attacks;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.player.Player;

public class DeepBurn {

    public static void performAttack(RaidsParty party, int height) {
        party.getGreatOlmNpc().performGreatOlmAttack(party);
        party.setOlmAttackTimer(6);
        party.sendMessage("The Great Olm sounds a cry...");

        TaskManager.submit(new Task(1, party, true) {
            int tick = 0;

            @Override
            public void execute() {
                if (party.getGreatOlmNpc().isDying() || party.isSwitchingPhases()) {
                    stop();
                }
                if (tick == 1) {
                    int random = RandomUtility.inclusiveRandom(party.getPlayers().size() - 1);
                    party.getBurnPlayers().add(party.getPlayers().get(random));
                    new Projectile(party.getGreatOlmNpc(), party.getBurnPlayers().get(0), Attacks.GREEN_PROJECTILE, 60,
                            8, 70, 10, 0).sendProjectile();

                }
                if (tick == 2) {
                    OlmAnimations.resetAnimation(party);
                    if (party.getBurnPlayers().get(0).getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                        party.getBurnPlayers().get(0).forceChat("Burn with me!");
                        party.getBurnPlayers().get(0).dealDamage(new Hit(50, Hitmask.RED, CombatIcon.NONE));
                    }
                }
                if (tick == 19) {
                    if (party.getBurnPlayers() != null) {
                        for (int i = 0; i < party.getBurnPlayers().size(); i++) {
                            if (party.getBurnPlayers().get(i) != null
                                    && party.getBurnPlayers().get(i).getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                                party.getBurnPlayers().get(i)
                                        .sendMessage("You feel the deep burning inside dissipate.");
                            }
                        }
                    }
                    party.getBurnPlayers().clear();
                    stop();
                }
                for (int iz = 0; iz < 19; iz++) {
                    if (tick == (4 * iz) + 6) {
                        if (party.getBurnPlayers() != null) {
                            for (Player member : party.getPlayers()) {
                                if (member != null && member.getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                                    for (int i = 0; i < party.getBurnPlayers().size() - 1; i++) {
                                        if (Locations.goodDistance(member.getPosition(),
                                                party.getBurnPlayers().get(i).getPosition(), 1)) {
                                            if (!party.getBurnPlayers().contains(member))
                                                party.getBurnPlayers().add(member);
                                        }
                                    }
                                }
                            }
                            for (int i = 0; i < party.getBurnPlayers().size(); i++) {

                                if (party.getBurnPlayers().get(i) != null
                                        && party.getBurnPlayers().get(i).getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                                    party.getBurnPlayers().get(i).forceChat("Burn with me!");
                                    party.getBurnPlayers().get(i).dealDamage(new Hit(50, Hitmask.RED, CombatIcon.NONE));
                                }
                            }
                        } else {
                            party.sendMessage("NULL");
                        }

                    }
                }

                tick++;
            }
        });

    }

}
