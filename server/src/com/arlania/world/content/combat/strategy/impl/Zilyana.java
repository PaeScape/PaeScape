package com.arlania.world.content.combat.strategy.impl;

import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.model.Locations;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;

public class Zilyana implements CombatStrategy {

    private static final Animation attack_anim = new Animation(6967);
    private static final Animation osrs_attack_anim = new Animation(28231);

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
        NPC zilyana = (NPC) entity;
        if (victim.getConstitution() <= 0) {
            return true;
        }
        if (Locations.goodDistance(zilyana.getPosition().copy(), victim.getPosition().copy(), 1) && RandomUtility.inclusiveRandom(5) <= 3) {
            zilyana.performAnimation(new Animation(zilyana.getDefinition().getAttackAnimation()));
            zilyana.getCombatBuilder().setContainer(new CombatContainer(zilyana, victim, 1, 1, CombatType.MELEE, true));
        } else {
            if (zilyana.getId() == 6247)
                zilyana.performAnimation(attack_anim);
            if (zilyana.getId() == 20493)
                zilyana.performAnimation(osrs_attack_anim);
            zilyana.performGraphic(new Graphic(1220));
            zilyana.getCombatBuilder().setContainer(new CombatContainer(zilyana, victim, 2, 3, CombatType.MAGIC, true));
            zilyana.getCombatBuilder().setAttackTimer(7);
        }
        return true;
    }

    @Override
    public int attackDelay(Character entity) {
        return entity.getAttackSpeed();
    }

    @Override
    public int attackDistance(Character entity) {
        return 2;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}
