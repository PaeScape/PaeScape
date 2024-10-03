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

public class Zerker implements CombatStrategy {

    private static final Animation attack_anim = new Animation(1658);
    private static final Animation special_anim = new Animation(11989);
    private static final Animation eat_anim = new Animation(829);
    private static final Animation veng_anim = new Animation(4410);
    private static final Animation tele_anim = new Animation(8939);
    private static int spec = 100;
    private static int veng = 200;
    //private static int food = 20;
    private static int warning = 0;
    private static final int nospecnpc = 9;
    private static final int specnpc = 11;

    public static NPC zerker;
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
        zerker = (NPC) entity;

        if (zerker.isChargingAttack() || zerker.getConstitution() <= 0) {
            return true;
        }

        if (Locations.distanceTo(zerker.getPosition(), victim.getPosition(), 1) > 1) {
            zerker.setConstitution(zerker.getConstitution() + 420);
            zerker.performAnimation(eat_anim);
            zerker.forceChat("Om nom nom");
            zerker.walkToPosition(new Position(victim.getPosition().getX(), victim.getPosition().getY() + 1));
        }
		
		/*if(Locations.goodDistance(zerker.getPosition().copy(), victim.getPosition().copy(), 1) && (zerker.getConstitution() < 800))
		{
			zerker.walkToPosition(new Position(victim.getPosition().getX(), victim.getPosition().getY() + 1));
			zerker.setConstitution(zerker.getConstitution() + 220);
			zerker.performAnimation(eat_anim);
			zerker.forceChat("Om nom nom");
		}*/

        //zerker.forceChat("Spec: "+ spec + "/100 - veng " + veng + "/100 - active veng: " + zerker.hasVengeance() );
        zerker.setTransformationId(nospecnpc);
        zerker.getUpdateFlag().flag(Flag.TRANSFORM);

        if (veng >= 200) {
            veng = 100;
            zerker.setHasVengeance(true);
        }

        if (victim.getPrayerActive()[PrayerHandler.PROTECT_FROM_MELEE]) {
            zerker.forceChat("Stop using the melee protect prayer. You won't kill me!");
            zerker.setConstitution(1000);
            spec = 100;
            veng = 100;
            zerker.setHasVengeance(true);
            //food = 20;

        } else if (victim.getConstitution() <= 0) {
            zerker.setConstitution(990);
            spec = 100;
            veng = 100;
            zerker.setHasVengeance(true);
            //food = 20;
            warning = 0;
        }
		
		/*else if (food <= 0)
		{
			NPC newzerker = new NPC(9, new Position(zerker.getPosition().getX(), zerker.getPosition().getY(),
					zerker.getPosition().getZ()));
			
			zerker.forceChat("Out of food, see ya!");
			zerker.performAnimation(tele_anim);
			despawn(true);
			
			World.register(newzerker);
			zerker.forceChat("Sorry that was Greg, my name is Jim!");
			zerker.setConstitution(1000);
			spec = 100;
			veng = 100;
			zerker.setHasVengeance(true);
			food = 20;
			despawn(false);
		}*/

        if ((victim.getConstitution() <= 350) && (spec >= 50)) {
            if (veng >= 100) {
                veng = veng - 100;
                zerker.setHasVengeance(true);
            }
            zerker.setTransformationId(specnpc);
            zerker.getUpdateFlag().flag(Flag.TRANSFORM);
            zerker.performAnimation(special_anim);
            victim.dealDamage(new Hit(RandomUtility.inclusiveRandom(720), Hitmask.RED, CombatIcon.MELEE));
            zerker.forceChat("Get Rekt!");
            spec = spec - 50;
        } else if (zerker.getConstitution() <= 300) {
            zerker.setConstitution(zerker.getConstitution() + 440);
            zerker.performAnimation(eat_anim);
            //food = food - 2;
            zerker.forceChat("Om nom nom");
        } else if ((veng >= 100) && (!zerker.hasVengeance()) && (victim.getConstitution() <= 450)) {
            zerker.performAnimation(veng_anim);
            zerker.performAnimation(attack_anim);
            zerker.getCombatBuilder().setContainer(new CombatContainer(zerker, victim, 1, 1, CombatType.MELEE, true));
            veng = veng - 100;
            zerker.setHasVengeance(true);
        } else if (victim.getConstitution() <= 550) {
            zerker.performAnimation(attack_anim);
            zerker.getCombatBuilder().setContainer(new CombatContainer(zerker, victim, 1, 1, CombatType.MELEE, true));
        } else if (zerker.getConstitution() <= 600) {
            zerker.setConstitution(zerker.getConstitution() + 220);
            zerker.performAnimation(eat_anim);
            zerker.forceChat("Om nom nom");
            //food = food - 1;
        } else if (victim.getConstitution() <= 450) {
            zerker.performAnimation(attack_anim);
            zerker.getCombatBuilder().setContainer(new CombatContainer(zerker, victim, 1, 1, CombatType.MELEE, true));
        } else if (zerker.getConstitution() <= 400) {
            zerker.setConstitution(zerker.getConstitution() + 220);
            zerker.performAnimation(eat_anim);
            zerker.forceChat("Om nom nom");
            //food = food - 1;
        } else {
            zerker.performAnimation(attack_anim);
            zerker.getCombatBuilder().setContainer(new CombatContainer(zerker, victim, 1, 1, CombatType.MELEE, true));
        }

        if (victim.getConstitution() <= 0 || victim == null) {
            zerker.setConstitution(1000);
            spec = 100;
            veng = 100;
            zerker.setHasVengeance(true);
            //food = 20;
        }


        spec = spec + 2;
        if (spec > 100) {
            spec = 100;
        }
        veng = veng + 5;
        if (veng > 100) {
            veng = 100;
        }
        zerker.setChargingAttack(false);
        return true;
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
        return CombatType.MELEE;
    }

    public static void despawn(boolean ZERKER) {
        if (ZERKER) {
            if (zerker != null && zerker.isRegistered())
                World.deregister(zerker);
        }
    }
}