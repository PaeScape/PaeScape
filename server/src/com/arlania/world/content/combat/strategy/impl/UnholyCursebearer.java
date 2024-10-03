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

public class UnholyCursebearer implements CombatStrategy {

    private static final Animation meleeAttack = new Animation(13169);
    private static final Animation mageAttack = new Animation(13169);
    private static final Animation spawnZombie = new Animation(13169);

    private static final Graphic graphic1 = new Graphic(2441, 3, GraphicHeight.LOW);

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
        NPC cursebearer = (NPC) entity;
        if (cursebearer.isChargingAttack() || cursebearer.getConstitution() <= 0) {
            return true;
        }
        switch (RandomUtility.inclusiveRandom(10)) {
            case 1:
                cursebearer.performAnimation(spawnZombie);
                NPC zombie = new NPC(8162, entity.getPosition());
                World.register(zombie);
                zombie.getCombatBuilder().attack(zombie.getRandomTarget());
                return true;
        }

        CombatType style = RandomUtility.inclusiveRandom(2) >= 1 && Locations.goodDistance(cursebearer.getPosition(), victim.getPosition(), 1) ? CombatType.MELEE : CombatType.MAGIC;
        if (style == CombatType.MELEE) {
            cursebearer.performAnimation(meleeAttack);
            cursebearer.getCombatBuilder().setContainer(new CombatContainer(cursebearer, victim, 1, 1, CombatType.MELEE, true));
        } else {
            cursebearer.performAnimation(mageAttack);

            Player target = (Player) victim;
            for (Player t : Misc.getCombinedPlayerList(target)) {
                if (t == null || t.isTeleporting())
                    continue;
                if (t.getPosition().distanceToPoint(cursebearer.getPosition().getX(), cursebearer.getPosition().getY()) > 40)
                    continue;
                new Projectile(cursebearer, target, graphic1.getId(), 44, 3, 43, 43, 0).sendProjectile();

            }//for
            TaskManager.submit(new Task(2, target, false) {
                @Override
                public void execute() {
                    for (Player t : Misc.getCombinedPlayerList(target)) {
                        if (t == null)
                            continue;
                        cursebearer.getCombatBuilder().setVictim(t);
                        new CombatHitTask(cursebearer.getCombatBuilder(), new CombatContainer(cursebearer, t, 1, CombatType.MAGIC, true)).handleAttack();

                        if (Locations.goodDistance(cursebearer.getPosition(), victim.getPosition(), 1)) {
                            new CombatHitTask(cursebearer.getCombatBuilder(), new CombatContainer(cursebearer, t, 1, CombatType.MELEE, true)).handleAttack();
                        }

                    }//for
                    cursebearer.setChargingAttack(false);
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