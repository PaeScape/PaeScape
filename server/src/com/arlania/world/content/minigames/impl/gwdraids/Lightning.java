package com.arlania.world.content.minigames.impl.gwdraids;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.Attacks;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.player.Player;

import java.util.ArrayList;

public class Lightning {

    private static final Graphic lightningGFX = new Graphic(1207);

    public static void performAttack(RaidsParty party, int height) {

        TaskManager.submit(new Task(1, party, true) {
            int tick = 0;

            @Override
            public void execute() {

                if (tick == 3) {
                    for (int i = 0; i < 2; i++) {
                        party.getLightningSpots()[i] = new Position(randomLocation(height).getX(), 5275, height);
                        party.getOwner().getPacketSender().sendGlobalGraphic(lightningGFX, party.getLightningSpots()[i]);
                    }
                    for (int i = 2; i < 4; i++) {
                        party.getLightningSpots()[i] = new Position(randomLocation(height).getX(), 5258, height);
                        party.getOwner().getPacketSender().sendGlobalGraphic(lightningGFX, party.getLightningSpots()[i]);
                    }
                }

                if (tick >= 4) {
                    for (int i = 0; i < 2; i++) {
                        party.getLightningSpots()[i] = new Position(party.getLightningSpots()[i].getX(), party.getLightningSpots()[i].getY() - 1, height);
                    }
                    for (int i = 2; i < 4; i++) {
                        party.getLightningSpots()[i] = new Position(party.getLightningSpots()[i].getX(), party.getLightningSpots()[i].getY() + 1, height);
                    }
                    for (int i = 0; i < 4; i++) {
                        party.getOwner().getPacketSender().sendGlobalGraphic(lightningGFX, party.getLightningSpots()[i]);
                        /*if (party.getLightningSpots()[i].getY() > 5277 || party.getLightningSpots()[i].getY() < 5257) {
                            stop();
                        }*/
                        //party.getOwner().getPacketSender().sendMessage("Position [" + i + "] , X: " + party.getLightningSpots()[i].getX() + ", Y: " + party.getLightningSpots()[i].getY() + ", Z: " + party.getLightningSpots()[i].getZ());

                        for (Player member : party.getPlayers()) {
                            if (member.getPosition().sameAs(party.getLightningSpots()[i])) {
                                member.getMovementQueue().freeze(2);
                                PrayerHandler.deactivateAll(member);
                                CurseHandler.deactivateAll(member);
                                member.sendMessage("@red@You've been electocuted to the spot!");
                                member.sendMessage("You've been injured and can't use protection prayers!");
                                member.dealDamage(new Hit(RandomUtility.inclusiveRandom(150, 300), Hitmask.RED, CombatIcon.NONE));
                            }
                        }
                    }
                }

                if (tick == 20) {
                    stop();
                }
                tick++;
            }
        });
    }

    public static Position randomLocation(int height) {
        ArrayList<Position> positions = new ArrayList<>();

        for (int x = 0; x < 18; x++) {
            for (int y = 0; y < 18; y++) {

                int posX = x + 2889;
                int posY = y + 5258;

                positions.add(new Position(posX, posY, height));
            }
        }

        return positions.get(RandomUtility.inclusiveRandom(positions.size() - 1));
    }
}
