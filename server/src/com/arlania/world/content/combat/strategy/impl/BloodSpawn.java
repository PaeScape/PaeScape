package com.arlania.world.content.combat.strategy.impl;

import com.arlania.GameSettings;
import com.arlania.model.Animation;
import com.arlania.model.CombatIcon;
import com.arlania.model.Hit;
import com.arlania.model.Hitmask;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;

public class BloodSpawn implements CombatStrategy {

    private static final Animation attack_anim = new Animation(8101 + GameSettings.OSRS_ANIM_OFFSET);


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
        NPC bloodspawn = (NPC) entity;
        if (bloodspawn.isChargingAttack() || bloodspawn.getConstitution() <= 0) {
            return true;
        }


        victim.dealDamage(new Hit(RandomUtility.inclusiveRandom(150) + 100, Hitmask.RED, CombatIcon.MELEE));

        return true;
    }

    @Override
    public int attackDelay(Character entity) {
        return entity.getAttackSpeed();
    }

    @Override
    public int attackDistance(Character entity) {
        return 4;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MELEE;
    }
}