package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatHitTask;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.content.minigames.impl.gwdraids.Lightning;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class Nex implements CombatStrategy {

    private static final Animation meleeAttack = new Animation(6326);
    private static final Animation mageAttack = new Animation(6354);
    private static final Graphic lightningGFX = new Graphic(982);

    //Nex related graphic?
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
        NPC nex = (NPC) entity;
        if (nex.isChargingAttack() || nex.getConstitution() <= 0) {
            return true;
        }

        Lightning.performAttack(victim.asPlayer().getRaidsParty(), nex.getPosition().getZ());

        CombatType style = RandomUtility.inclusiveRandom(2) >= 1 && Locations.goodDistance(nex.getPosition(), victim.getPosition(), 1) ? CombatType.MELEE : CombatType.MAGIC;

        if (style == CombatType.MELEE) {
            nex.performAnimation(meleeAttack);
            nex.getCombatBuilder().setContainer(new CombatContainer(nex, victim, 1, 1, CombatType.MELEE, true));
        } else {
            nex.performAnimation(mageAttack);

            Player target = (Player) victim;
            for (Player t : Misc.getCombinedPlayerList(target)) {
                if (t == null || t.isTeleporting())
                    continue;
                if (t.getPosition().distanceToPoint(nex.getPosition().getX(), nex.getPosition().getY()) > 20)
                    continue;
                new Projectile(nex, target, graphic1.getId(), 44, 3, 43, 43, 0).sendProjectile();

            }//for
            TaskManager.submit(new Task(2, target, false) {
                @Override
                public void execute() {
                    for (Player t : Misc.getCombinedPlayerList(target)) {
                        if (t == null)
                            continue;
                        nex.getCombatBuilder().setVictim(t);
                        new CombatHitTask(nex.getCombatBuilder(), new CombatContainer(nex, t, 1, CombatType.MAGIC, true)).handleAttack();

                        if (Locations.goodDistance(nex.getPosition(), victim.getPosition(), 1)) {
                            new CombatHitTask(nex.getCombatBuilder(), new CombatContainer(nex, t, 1, CombatType.MELEE, true)).handleAttack();
                        }

                    }//for
                    nex.setChargingAttack(false);
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



}