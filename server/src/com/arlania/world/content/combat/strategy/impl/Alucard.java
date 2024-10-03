package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatHitTask;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class Alucard implements CombatStrategy {

    private static final Animation attack_anim = new Animation(440);
    private static final Animation special_anim = new Animation(2876);
    private static final Animation barrage_anim = new Animation(1979);
    private static final Graphic blood = new Graphic(377);


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
        NPC alucard = (NPC) entity;
        if (alucard.isChargingAttack() || alucard.getConstitution() <= 0) {
            return true;
        }
        //CombatType style = RandomUtility.getRandom(4) <= 1 && Locations.goodDistance(alucard.getPosition(), victim.getPosition(), 1) ? CombatType.MELEE : CombatType.MAGIC;

        int attackStyle = RandomUtility.inclusiveRandom(5);
        CombatType style = CombatType.MELEE;

        if (attackStyle < 3) style = CombatType.MELEE;
        else style = CombatType.MAGIC;

        if (style == CombatType.MELEE) {
            int spec = RandomUtility.inclusiveRandom(3);

            if (spec == 1) {

                if ((victim.getPrayerActive()[PrayerHandler.PROTECT_FROM_MELEE]) || (victim.getCurseActive()[CurseHandler.DEFLECT_MELEE])) {
                    alucard.forceChat("YOUR GOD CAN'T SAVE YOU!");
                    alucard.performAnimation(special_anim);
                    victim.dealDamage(new Hit(RandomUtility.inclusiveRandom(350), Hitmask.RED, CombatIcon.MELEE));
                } else {
                    alucard.forceChat("DEATH BE UPON YOU!");
                    alucard.performAnimation(special_anim);
                    victim.dealDamage(new Hit(RandomUtility.inclusiveRandom(700), Hitmask.RED, CombatIcon.MELEE));
                }

            } else {
                alucard.performAnimation(attack_anim);
                alucard.getCombatBuilder().setContainer(new CombatContainer(alucard, victim, 1, 1, CombatType.MELEE, true));
            }
        } else {
            alucard.forceChat("BLEEEEEEED!");
            alucard.performAnimation(barrage_anim);
            alucard.setChargingAttack(true);
            Player target = (Player) victim;
            for (Player t : Misc.getCombinedPlayerList(target)) {
                if (t == null || t.isTeleporting())
                    continue;
                if (t.getPosition().distanceToPoint(alucard.getPosition().getX(), alucard.getPosition().getY()) > 20)
                    continue;

            }//for
            TaskManager.submit(new Task(2, target, false) {
                @Override
                public void execute() {
                    for (Player t : Misc.getCombinedPlayerList(target)) {
                        if (t == null)
                            continue;
                        alucard.getCombatBuilder().setVictim(t);
                        new CombatHitTask(alucard.getCombatBuilder(), new CombatContainer(alucard, t, 1, CombatType.MAGIC, true)).handleAttack();
                        t.performGraphic(blood);
                        int randHeal = RandomUtility.inclusiveRandom(200);
                        alucard.setConstitution(alucard.getConstitution() + randHeal);
                        if (alucard.getConstitution() > alucard.getDefinition().getHitpoints())
                            alucard.setConstitution(alucard.getDefinition().getHitpoints());

                    }//for
                    alucard.setChargingAttack(false);
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
        return 4;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}