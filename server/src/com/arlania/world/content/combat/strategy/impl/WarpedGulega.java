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

public class WarpedGulega implements CombatStrategy {

    private static final Animation mageAttack = new Animation(15001);
    private static final Animation meleeAttack = new Animation(15004);
    private static final Animation spawnSnake = new Animation(15002);

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
        NPC warpedgulega = (NPC) entity;
        if (warpedgulega.isChargingAttack() || warpedgulega.getConstitution() <= 0) {
            return true;
        }
        switch (RandomUtility.inclusiveRandom(10)) {
            case 1:
                warpedgulega.performAnimation(spawnSnake);
                NPC snake = new NPC(3484, entity.getPosition());
                World.register(snake);
                snake.getCombatBuilder().attack(snake.getRandomTarget());
                return true;
            case 2:
                AcidDrip.performAttack(victim.asPlayer().getRaidsParty(), entity.getAsNpc(), 30);
                break;
        }

        CombatType style = RandomUtility.inclusiveRandom(2) >= 1 && Locations.goodDistance(warpedgulega.getPosition(), victim.getPosition(), 1) ? CombatType.MELEE : CombatType.MAGIC;
        if (style == CombatType.MELEE) {
            warpedgulega.performAnimation(meleeAttack);
            warpedgulega.getCombatBuilder().setContainer(new CombatContainer(warpedgulega, victim, 1, 1, CombatType.MELEE, true));
        } else {
            warpedgulega.performAnimation(mageAttack);

            Player target = (Player) victim;
            for (Player t : Misc.getCombinedPlayerList(target)) {
                if (t == null || t.isTeleporting())
                    continue;
                if (t.getPosition().distanceToPoint(warpedgulega.getPosition().getX(), warpedgulega.getPosition().getY()) > 40)
                    continue;
                new Projectile(warpedgulega, target, graphic1.getId(), 44, 3, 43, 43, 0).sendProjectile();

            }//for
            TaskManager.submit(new Task(2, target, false) {
                @Override
                public void execute() {
                    for (Player t : Misc.getCombinedPlayerList(target)) {
                        if (t == null)
                            continue;
                        warpedgulega.getCombatBuilder().setVictim(t);
                        new CombatHitTask(warpedgulega.getCombatBuilder(), new CombatContainer(warpedgulega, t, 1, CombatType.MAGIC, true)).handleAttack();

                        if (Locations.goodDistance(warpedgulega.getPosition(), victim.getPosition(), 1)) {
                            new CombatHitTask(warpedgulega.getCombatBuilder(), new CombatContainer(warpedgulega, t, 1, CombatType.MELEE, true)).handleAttack();
                        }

                    }//for
                    warpedgulega.setChargingAttack(false);
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
