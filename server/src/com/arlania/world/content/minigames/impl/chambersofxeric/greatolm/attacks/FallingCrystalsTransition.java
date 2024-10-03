package com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.Olm;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;


public class FallingCrystalsTransition {

    public static void performAttack(RaidsParty party, int height, int tick) {

        boolean doAction = false;
        boolean doActionPlayer = false;


        //i = length of crystal phase (originally i<10k)
        for (int i = 0; i < 100; i++) {
            if (tick == i * 2)
                doAction = true;
            if (tick == i * 3)
                doActionPlayer = true;
        }
        if (party.isTransitionPhase()
                || (party.getCurrentPhase() == 3 && party.isLeftHandDead() && party.isRightHandDead()
                && party.getGreatOlmNpc().getConstitution() > 0 && !party.getGreatOlmNpc().isDying())) {

            if (doAction)
                dropCrystal(party, height);

        } else {
            for (Player member : party.getPlayers()) {
                if (member != null && member.getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                    member.getPacketSender().sendCameraNeutrality();
                }
            }

        }
    }

    public static void dropCrystal(RaidsParty party, int height) {

        Position pos = Attacks.randomLocation(height);
        NPC spawn = NPC.of(5090, pos);
        World.register(spawn);
        NPC spawn1 = NPC.of(5090, new Position(pos.getX(), pos.getY() - 1, height));
        World.register(spawn1);
        new Projectile(spawn1, spawn, Attacks.FALLING_CRYSTAL, 140, 1, 220, 0, 0).sendProjectile();

        TaskManager.submit(new Task(4) {

            @Override
            public void execute() {
                party.getOwner().getPacketSender().sendGlobalGraphic(new Graphic(Attacks.GREEN_PUFF), pos);

                for (Player member : party.getPlayers()) {
                    if (member != null && member.getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                        if (member.getPosition().sameAs(pos)) {
                            member.dealDamage(new Hit(RandomUtility.inclusiveRandom((int) (Olm.getCrystalMinHit(party) * 1.5), (int) (Olm.getCrystalMaxHit(party) * 1.5)), Hitmask.RED, CombatIcon.NONE));
                        } else if (Locations.goodDistance(member.getPosition(), pos, 1)) {
                            member.dealDamage(new Hit(RandomUtility.inclusiveRandom(Olm.getCrystalMinHit(party), Olm.getCrystalMaxHit(party)), Hitmask.RED, CombatIcon.NONE));
                        }
                    }
                }

                World.deregister(spawn);
                World.deregister(spawn1);
                stop();
            }
        });
    }

}
