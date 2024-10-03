package com.arlania.world.content.combat.strategy.impl;

import com.arlania.model.Animation;
import com.arlania.model.Projectile;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;

public class Spinolyp implements CombatStrategy {

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
        NPC spinolyp = (NPC) entity;
        if (spinolyp.getConstitution() <= 0 || victim.getConstitution() <= 0) {
            return true;
        }
        spinolyp.performAnimation(new Animation(spinolyp.getDefinition().getAttackAnimation()));
        boolean mage = RandomUtility.inclusiveRandom(10) <= 7;
        new Projectile(spinolyp, victim, mage ? 1658 : 1017, 44, 3, 43, 43, 0).sendProjectile();
        spinolyp.getCombatBuilder().setContainer(new CombatContainer(spinolyp, victim, 1, mage ? 3 : 2, mage ? CombatType.MAGIC : CombatType.RANGED, true));
        return true;
    }


    @Override
    public int attackDelay(Character entity) {
        return entity.getAttackSpeed();
    }

    @Override
    public int attackDistance(Character entity) {
        return 6;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}
