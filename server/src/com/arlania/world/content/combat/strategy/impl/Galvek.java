package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatHitTask;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.content.minigames.impl.strongholdraids.AcidDrip;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

import java.util.ArrayList;

public class Galvek implements CombatStrategy {

    private static final Animation mageAttack = new Animation(29162);
    private static final Animation meleeAttack = new Animation(29161);
    private static final Animation spawnDragon = new Animation(29163);

    private static final Graphic graphic1 = new Graphic(986, 3, GraphicHeight.LOW);

    @Override
    public boolean canAttack(Character entity, Character victim) {
        return victim.isPlayer();
    }

    @Override
    public CombatContainer attack(Character entity, Character victim) {
        return null;
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        NPC galvek = (NPC) entity;
        Player target = (Player) victim;

        if (galvek.isChargingAttack() || galvek.getConstitution() <= 0) {
            return true;
        }
        switch (RandomUtility.inclusiveRandom(10)) {
            case 1: //spawn dragon
                galvek.performAnimation(spawnDragon);
                NPC dragon = new NPC(22609, entity.getPosition());
                World.register(dragon);
                dragon.getCombatBuilder().attack(dragon.getRandomTarget());
                return true;
            case 2:
                AcidDrip.performAttack(victim.asPlayer().getRaidsParty(), entity.getAsNpc(), 30);
                break;
            case 3: //rain fire
                for (Player t : Misc.getCombinedPlayerList(target)) {
                    if (t == null || t.isTeleporting())
                        continue;
                    if (t.getPosition().distanceToPoint(galvek.getPosition().getX(), galvek.getPosition().getY()) > 40)
                        continue;
                    rainFire(victim.asPlayer(), galvek.getPosition().getZ());
                }//for
                break;
        }

        CombatType style = RandomUtility.inclusiveRandom(2) >= 1 && Locations.goodDistance(galvek.getPosition(), victim.getPosition(), 1) ? CombatType.MELEE : CombatType.MAGIC;
        if (style == CombatType.MELEE) {
            galvek.performAnimation(meleeAttack);
            galvek.getCombatBuilder().setContainer(new CombatContainer(galvek, victim, 1, 1, CombatType.MELEE, true));
        } else {
            galvek.performAnimation(mageAttack);

            for (Player t : Misc.getCombinedPlayerList(target)) {
                if (t == null || t.isTeleporting())
                    continue;
                if (t.getPosition().distanceToPoint(galvek.getPosition().getX(), galvek.getPosition().getY()) > 20)
                    continue;
                new Projectile(galvek, target, graphic1.getId(), 44, 3, 43, 43, 0).sendProjectile();

            }//for
            TaskManager.submit(new Task(2, target, false) {
                @Override
                public void execute() {
                    for (Player t : Misc.getCombinedPlayerList(target)) {
                        if (t == null)
                            continue;
                        galvek.getCombatBuilder().setVictim(t);
                        new CombatHitTask(galvek.getCombatBuilder(), new CombatContainer(galvek, t, 1, CombatType.MAGIC, true)).handleAttack();

                        if (Locations.goodDistance(galvek.getPosition(), victim.getPosition(), 1)) {
                            new CombatHitTask(galvek.getCombatBuilder(), new CombatContainer(galvek, t, 1, CombatType.MELEE, true)).handleAttack();
                        }

                    }//for
                    galvek.setChargingAttack(false);
                    stop();
                }
            });
        }
        return true;
    }

    @Override
    public int attackDelay(Character entity) {
        return entity.getAttackSpeed();
    }

    @Override
    public int attackDistance(Character entity) {
        return 7;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }

    public static void rainFire(Player player, int height) {

        Position pos = player.getPosition();
        NPC spawn = NPC.of(5090, pos);
        World.register(spawn);
        NPC spawn1 = NPC.of(5090, new Position(pos.getX(), pos.getY() - 1, height));
        World.register(spawn1);
        new Projectile(spawn, spawn, 1166, 140, 1, 220, 0, 0).sendProjectile();

        TaskManager.submit(new Task(4) {

            @Override
            public void execute() {
                player.getPacketSender().sendGlobalGraphic(new Graphic(311), pos);


                if (player.getPosition().sameAs(pos)) {
                    player.dealDamage(new Hit(RandomUtility.inclusiveRandom(200, 300), Hitmask.RED, CombatIcon.NONE));
                } else if (Locations.goodDistance(player.getPosition(), pos, 1)) {
                    player.dealDamage(new Hit(RandomUtility.inclusiveRandom(100, 150), Hitmask.RED, CombatIcon.NONE));
                }

                World.deregister(spawn);
                World.deregister(spawn1);
                stop();
            }
        });
    }
}
