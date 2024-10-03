package com.arlania.world.content.combat.strategy.impl;

import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.model.Skill;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatHitTask;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.player.Player;

/**
 * @author Lukafurlan
 **/

public class Venenatis implements CombatStrategy {

    public static int phase;

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
        Player player = (Player) victim;
        if (player.getConstitution() <= 0 || entity.getConstitution() <= 0) {
            return true;
        }

        if (phase == 0) {
            int rand = RandomUtility.inclusiveRandom(10);
            int amountToDrain = RandomUtility.inclusiveRandom(200);
            if (rand == 5) {
                new CombatHitTask(entity.getCombatBuilder(), new CombatContainer(entity, player, 1, CombatType.MAGIC, true)).handleAttack();
                entity.performAnimation(new Animation(5327));
                player.performGraphic(new Graphic(377));
                player.getPacketSender().sendMessage("@red@You were hit by powerfull magic attack!");
            } else if (rand == 2) {
                new CombatHitTask(entity.getCombatBuilder(), new CombatContainer(entity, player, 1, CombatType.MAGIC, true)).handleAttack();
                new CombatHitTask(entity.getCombatBuilder(), new CombatContainer(entity, player, 1, CombatType.MAGIC, true)).handleAttack();
                entity.performAnimation(new Animation(5327));
                player.performGraphic(new Graphic(377));
                player.getPacketSender().sendMessage("@red@Venenatis casts two powerfull spells at once!");
            } else if (rand == 4 || rand == 3) {
                new CombatHitTask(entity.getCombatBuilder(), new CombatContainer(entity, player, 1, CombatType.MELEE, true)).handleAttack();
                player.getSkillManager().setCurrentLevel(Skill.PRAYER, player.getSkillManager().getCurrentLevel(Skill.PRAYER) - amountToDrain);
                player.getPacketSender().sendMessage("@blu@Venenatis has drained " + amountToDrain + " prayer points!");
                entity.performAnimation(new Animation(5327));
            } else {
                new CombatHitTask(entity.getCombatBuilder(), new CombatContainer(entity, player, 1, CombatType.MELEE, true)).handleAttack();
                entity.performAnimation(new Animation(5327));
            }
        }
        return true;
    }

    @Override
    public int attackDelay(Character entity) {
        return entity.getAttackSpeed();
    }

    @Override
    public int attackDistance(Character entity) {
        return 3;
    }


    @Override
    public CombatType getCombatType() {
        // TODO Auto-generated method stub
        return null;
    }

}