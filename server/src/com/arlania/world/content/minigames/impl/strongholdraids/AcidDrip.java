package com.arlania.world.content.minigames.impl.strongholdraids;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class AcidDrip {

    public static void performAttack(RaidsParty party, NPC npc, int damage) {
        TaskManager.submit(new Task(1, party, true) {
            int tick = 0;

            @Override
            public void execute() {
                if (npc.isDying()) {
                    stop();
                }
                if (tick == 1) {
                    if (party.getPlayers().size() >= 1) {

                        for(Player members : party.getPlayers()) {
                            if (members.getLocation() == Locations.Location.SHR) {
                                Position acidSpotPosition = members.getPosition();
                                GameObject pool = new GameObject(330032, acidSpotPosition, 11, 1);
                                CustomObjects.acidPool(pool, members, 1, 25);
                                party.getDripPools().add(acidSpotPosition);
                            }
                        }
                    } else {
                        stop();
                    }
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
                    if (tick == (2 * iz) + 3) {
                        for (Player member : party.getPlayers()) {
                            for (int i = 0; i < party.getDripPools().size(); i++) {
                                if (member.getPosition().sameAs(party.getDripPools().get(i))) {
                                    member.dealDamage(new Hit(damage + RandomUtility.inclusiveRandom(20), Hitmask.DARK_GREEN, CombatIcon.NONE));
                                    //member.dealDamage(new Hit(20 + Misc.getRandom(10), Hitmask.DARK_GREEN, CombatIcon.NONE));
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
