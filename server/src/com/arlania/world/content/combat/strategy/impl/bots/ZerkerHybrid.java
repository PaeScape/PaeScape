package com.arlania.world.content.combat.strategy.impl.bots;

import com.arlania.model.*;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;

public class ZerkerHybrid implements CombatStrategy {

    private static final Animation attack_anim = new Animation(15072);
    private static final Animation special_anim = new Animation(11989);
    private static final Animation eat_anim = new Animation(829);
    private static final Animation barrage_anim = new Animation(1979);
    private static final Animation tele_anim = new Animation(8939);
    private static final Graphic frozen = new Graphic(369);
    private static int spec = 100;
    private static int food = 20;
    private static final int nospecnpc = 8;
    private static final int specnpc = 10;

    public static NPC zerkerhybrid;
    //veng
    //prayer
    //special attack

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
        zerkerhybrid = (NPC) entity;

        if (zerkerhybrid.isChargingAttack() || zerkerhybrid.getConstitution() <= 0) {
            return true;
        }

        zerkerhybrid.setTransformationId(nospecnpc);
        zerkerhybrid.getUpdateFlag().flag(Flag.TRANSFORM);


        if ((victim.getConstitution() <= 350) && (spec >= 50) && (!victim.getPrayerActive()[PrayerHandler.PROTECT_FROM_MELEE])) {
            SpecialAttack(victim);
        } else if (zerkerhybrid.getConstitution() <= 300) {
            MagicAttack(victim);
            Eat();
            Eat();
        } else if (zerkerhybrid.getConstitution() <= 500) {
            MagicAttack(victim);
            Eat();
        } else if (victim.getConstitution() <= 450) {
            if (victim.getPrayerActive()[PrayerHandler.PROTECT_FROM_MELEE])
                MagicAttack(victim);

            else if (victim.getPrayerActive()[PrayerHandler.PROTECT_FROM_MAGIC])
                MeleeAttack(victim);

            else {
                if (RandomUtility.inclusiveRandom(2) == 1)
                    MeleeAttack(victim);

                else
                    MagicAttack(victim);
            }
        } else if (zerkerhybrid.getConstitution() <= 700 && victim.asPlayer().getSpecialPercentage() > 50)
            Eat();


        else {
            if (victim.getPrayerActive()[PrayerHandler.PROTECT_FROM_MELEE])
                MagicAttack(victim);

            else if (victim.getPrayerActive()[PrayerHandler.PROTECT_FROM_MAGIC])
                MeleeAttack(victim);

            else {
                if (RandomUtility.inclusiveRandom(2) == 1)
                    MeleeAttack(victim);

                else
                    MagicAttack(victim);
            }
        }

        if (victim.getConstitution() <= 0) {
            zerkerhybrid.setConstitution(1000);
            spec = 100;
        }
		
		/*
		int xToMove = RandomUtility.getRandom(4);
		int yToMove = RandomUtility.getRandom(4);
		int xCoord = zerkerhybrid.getPosition().getX();
		int yCoord = zerkerhybrid.getPosition().getY();
		
		PathFinder.findPath(zerkerhybrid, xCoord +- xToMove, yCoord +- yToMove, true, 1, 1);
		*/

        spec = spec + 2;
        if (spec > 100) {
            spec = 100;
        }
        zerkerhybrid.setChargingAttack(false);
        return true;
    }


    public void Eat() {

        zerkerhybrid.setConstitution(zerkerhybrid.getConstitution() + 220);
        zerkerhybrid.performAnimation(eat_anim);
        zerkerhybrid.forceChat("Om nom nom");
        food = food - 1;

    }


    public void MagicAttack(Character victim) {
        zerkerhybrid.performAnimation(barrage_anim);
        victim.dealDamage(new Hit(RandomUtility.inclusiveRandom(320), Hitmask.RED, CombatIcon.MAGIC));
        if (RandomUtility.inclusiveRandom(2) == 1) {
            Freeze(victim);
        }

    }

    public void MeleeAttack(Character victim) {

        int randMove = RandomUtility.inclusiveRandom(1);
        int NorthOrSouth = RandomUtility.inclusiveRandom(1);

        if (NorthOrSouth == 1)
            zerkerhybrid.walkToPosition(new Position(victim.getPosition().getX() + randMove, victim.getPosition().getY() + randMove));
        else
            zerkerhybrid.walkToPosition(new Position(victim.getPosition().getX() - randMove, victim.getPosition().getY() - randMove));


        zerkerhybrid.performAnimation(attack_anim);
        zerkerhybrid.getCombatBuilder().setContainer(new CombatContainer(zerkerhybrid, victim, 1, 1, CombatType.MELEE, true));

    }

    public void SpecialAttack(Character victim) {

        int randMove = RandomUtility.inclusiveRandom(1);
        int NorthOrSouth = RandomUtility.inclusiveRandom(1);

        if (NorthOrSouth == 1)
            zerkerhybrid.walkToPosition(new Position(victim.getPosition().getX() + randMove, victim.getPosition().getY() + randMove));
        else
            zerkerhybrid.walkToPosition(new Position(victim.getPosition().getX() - randMove, victim.getPosition().getY() - randMove));

        zerkerhybrid.setTransformationId(specnpc);
        zerkerhybrid.getUpdateFlag().flag(Flag.TRANSFORM);
        zerkerhybrid.performAnimation(special_anim);
        victim.dealDamage(new Hit(RandomUtility.inclusiveRandom(720), Hitmask.RED, CombatIcon.MELEE));
        zerkerhybrid.forceChat("Get Rekt!");
        spec = spec - 50;

    }

    public void Freeze(Character victim) {
        victim.getMovementQueue().freeze(15);
        victim.performGraphic(frozen);

        int randMove = RandomUtility.inclusiveRandom(4) + 1;
        int NorthOrSouth = RandomUtility.inclusiveRandom(1);

        if (NorthOrSouth == 1)
            zerkerhybrid.walkToPosition(new Position(victim.getPosition().getX() + randMove, victim.getPosition().getY() + randMove));
        else
            zerkerhybrid.walkToPosition(new Position(victim.getPosition().getX() - randMove, victim.getPosition().getY() - randMove));
    }

    @Override
    public int attackDelay(Character entity) {
        return entity.getAttackSpeed();
    }

    @Override
    public int attackDistance(Character entity) {


        return 1;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }

    public static void despawn(boolean ZERKER) {
        if (ZERKER) {
            if (zerkerhybrid != null && zerkerhybrid.isRegistered())
                World.deregister(zerkerhybrid);
        }
    }
}