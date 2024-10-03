package com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.special;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.OlmAnimations;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.player.Player;

public class AcidDrip {

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
                    if (party.getPlayersInRaids().size() >= 1) {
                        int random = RandomUtility.inclusiveRandom(party.getPlayersInRaids().size() - 1);
                        party.setAcidDripPlayer(party.getPlayers().get(random));
                        party.getAcidDripPlayer().sendMessage(
                                "@red@The Great Olm has smothered you in acid. It starts to drip off slowly.");
                    } else {
                        stop();
                    }
                }
                if (tick == 2) {
                    OlmAnimations.resetAnimation(party);
                }
                if (tick == 30) {
                    party.getDripPools().clear();

                    for (Position dripPool : party.getDripPools()) {
                        for (Player player : party.getPlayers()) {
                            player.getPacketSender().sendObject(new GameObject(-1, dripPool, 10, 3));
                        }
                    }
                    stop();
                }
                for (int iz = 0; iz < 30; iz++) {
                    if (party.getAcidDripPlayer() != null) {
                        if (tick == iz + 2) {
                            if (party.getAcidDripPlayer().getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                                Position acidSpotPosition = party.getAcidDripPlayer().getPosition();
                                GameObject pool = new GameObject(330032, acidSpotPosition, GameObject.ObjectType.GROUND_OBJECT.getInteger(), RandomUtility.inclusiveRandom(3));
                                CustomObjects.acidPool(pool, party.getOwner(), 1, 25);
                                party.getDripPools().add(acidSpotPosition);
                            }
                        }
                        if (tick == (2 * iz) + 3) {
                            for (Player member : party.getPlayers()) {
                                if (member != null && member.getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                                    for (int i = 0; i < party.getDripPools().size(); i++) {
                                        if (member.getPosition().sameAs(party.getDripPools().get(i))) {
                                            member.dealDamage(new Hit(20 + RandomUtility.inclusiveRandom(10),
                                                    Hitmask.DARK_GREEN, CombatIcon.NONE));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                tick++;
            }
        });

    }

}
