package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Projectile;
import com.arlania.model.Skill;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatHitTask;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class Cerberus implements CombatStrategy {

    @Override
    public boolean canAttack(Character entity, Character victim) {
        return true;
    }

    @Override
    public CombatContainer attack(Character entity, Character victim) {
        return null;
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        NPC cerberus = (NPC) entity;
        if (victim.getConstitution() <= 0) {
            return true;
        }
		/*if(cerberus.isChargingAttack()) {
			return true;
		}
		cerberus.setChargingAttack(true);*/
        cerberus.performAnimation(new Animation((4492)));
        final CombatType attkType = RandomUtility.inclusiveRandom(5) <= 2 ? CombatType.RANGED : CombatType.MAGIC;
        cerberus.getCombatBuilder().setContainer(new CombatContainer(cerberus, victim, 1, 4, attkType, RandomUtility.inclusiveRandom(5) > 1));
        TaskManager.submit(new Task(1, cerberus, false) {
            int tick = 0;

            @Override
            public void execute() {
                if (tick == 2) {
                    new Projectile(cerberus, victim, (attkType == CombatType.RANGED ? 450 : 439), 44, 3, 43, 43, 0).sendProjectile();
                } else if (tick == 3) {
                    new CombatHitTask(cerberus.getCombatBuilder(), new CombatContainer(cerberus, victim, 1, CombatType.MAGIC, true)).handleAttack();
                    if (RandomUtility.inclusiveRandom(10) <= 2) {
                        Player p = (Player) victim;
                        int lvl = p.getSkillManager().getCurrentLevel(Skill.PRAYER);
                        lvl *= 0.9;
                        p.getSkillManager().setCurrentLevel(Skill.PRAYER, p.getSkillManager().getCurrentLevel(Skill.PRAYER) - lvl <= 0 ? 1 : lvl);
                        p.getPacketSender().sendMessage("Cerberus has reduced your Prayer level.");
                    }
                    //cerberus.setChargingAttack(false);
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
        return 5;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}
