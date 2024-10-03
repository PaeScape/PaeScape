package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Projectile;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatHitTask;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class KreeArra implements CombatStrategy {

    @Override
    public boolean canAttack(Character entity, Character victim) {
        //return victim.isPlayer() && ((Player)victim).getMinigameAttributes().getGodwarsDungeonAttributes().hasEnteredRoom();
        return victim.isPlayer();
    }

    @Override
    public CombatContainer attack(Character entity, Character victim) {
        return null;
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        NPC kreearra = (NPC) entity;

        if (victim.getConstitution() <= 0) {
            return true;
        }
        if (kreearra.isChargingAttack()) {
            return true;
        }

        final CombatType style = RandomUtility.inclusiveRandom(1) == 0 ? CombatType.MAGIC : CombatType.RANGED;
        kreearra.performAnimation(new Animation(kreearra.getDefinition().getAttackAnimation()));
        kreearra.setChargingAttack(true);
        Player target = (Player) victim;
        TaskManager.submit(new Task(1, kreearra, false) {
            int tick = 0;

            @Override
            public void execute() {
                if (tick == 1) {
                    for (final Player near : Misc.getCombinedPlayerList(target)) {
                        if (near == null || near.isTeleporting())
                            continue;
                        if (near.getPosition().distanceToPoint(kreearra.getPosition().getX(), kreearra.getPosition().getY()) > 20)
                            continue;
                        new Projectile(kreearra, victim, style == CombatType.MAGIC ? 1198 : 1197, 44, 3, 43, 43, 0).sendProjectile();
						/*if(style == CombatType.RANGED) { //Moving players randomly
							int xToMove = RandomUtility.getRandom(1);
							int yToMove = RandomUtility.getRandom(1);
							int xCoord = target.getPosition().getX();
							int yCoord = target.getPosition().getY();
							if (RandomUtility.getRandom(3) <= 1 && MovementQueue.canWalk(target.getPosition(), new Position(xCoord +- xToMove, yCoord +- yToMove, target.getPosition().getZ()), 1)) {
								target.getMovementQueue().reset();
								if(!target.isTeleporting())
									target.moveTo(new Position(xCoord +- xToMove, yCoord +- yToMove, target.getPosition().getZ()));
								target.performGraphic(new Graphic(128));
							}
						}*/
                    }
                } else if (tick == 2) {
                    for (final Player near : Misc.getCombinedPlayerList(target)) {
                        if (near == null || near.isTeleporting())
                            continue;
                        if (near.getPosition().distanceToPoint(kreearra.getPosition().getX(), kreearra.getPosition().getY()) > 20)
                            continue;
                        new CombatHitTask(kreearra.getCombatBuilder(), new CombatContainer(kreearra, victim, 1, style, true)).handleAttack();
                    }
                    kreearra.setChargingAttack(false);
                    stop();
                }
                tick++;
            }
        });
        return true;
    }

    @Override
    public int attackDelay(Character entity) {
        return entity.getAttackSpeed();
    }

    @Override
    public int attackDistance(Character entity) {
        return 8;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}
