package com.arlania.world.content.combat.strategy.impl;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatFactory;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.effect.CombatPoisonEffect;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

import java.util.ArrayList;

public class Vorkath implements CombatStrategy {

    //Vorkath Anims
	    /*  
	    7946: Dead?
        7947: Swing left wing forward
        7948: Walking
        7949: Dying
        7950: Waking up
        7951: Attack
        7952: Fire Breath
        7954: Blocking
        7955: Blocking
        7956: Coming out of the Block
        7957: Mouth up at an angle? Dragonfire attack
        7958: Nothing
        7959: Walking
        7960: Launching fire from mouth (straight up)
        */

    //Vorkath Gfx and Projectiles
        /*
        1470: Acid Breath
        1471: Purple Breath
        1472: Green Blast (zombie spawn)
        1473: Purple Blast
        1480: Cyan Blast (Explosion undead crab)
        1481: Fireball
         */

    //Dragonfire damage on all attacks
    //Check poison splat

    //Loots
    //Amethyst Arrows
    //Wrath Runes - Surge Spells
    //Ice Arrows - Chance to Freeze on hit
    //Frostbite Dagger - Chance to Freeze on hit
    //Multiple Superior Dragon Bones
    //Celestial Surge Box
    //Zenytes
    //Ice crystal to update Cerberus Boots. Use Ice Crystal on Eternal Flame to give upgrades to each BiS boot


    //special attack - vorkath highlights player's tile and 4 additional tiles in a 5x5 square surrounding the player
    //
    //
    //

    Animation walk = new Animation(7948 + GameSettings.OSRS_ANIM_OFFSET);
    Animation die = new Animation(7949 + GameSettings.OSRS_ANIM_OFFSET);

    Animation scratch = new Animation(7951 + GameSettings.OSRS_ANIM_OFFSET);
    Animation scratchSpin = new Animation(7947 + GameSettings.OSRS_ANIM_OFFSET);
    Animation charge = new Animation(7954 + GameSettings.OSRS_ANIM_OFFSET);

    int basicDragonfire = 393;
    int poisonDragonfire = 394;
    int iceDragonfire = 395;
    int healingDragonfire = 396;

    //fireball
    int projectile = 1481 + GameSettings.OSRS_GFX_OFFSET;

    //Animation portalIn = new Animation(8607 + GameSettings.OSRS_ANIM_OFFSET);
    //Animation portalOut = new Animation(8609 + GameSettings.OSRS_ANIM_OFFSET);


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
        NPC vorkath = (NPC) entity;
        if (vorkath.isChargingAttack() || vorkath.getConstitution() <= 0) {
            vorkath.getCombatBuilder().setAttackTimer(4);
            return true;
        }
        if (victim.getConstitution() <= 0) {
            return true;
        }

        //if (!Locations.goodDistance(vorkath.getPosition().copy(), victim.getPosition().copy(), 3))
        //{
        int randomAttack = RandomUtility.inclusiveRandom(15);

        switch (randomAttack) {

            case 1: //Fireball special attack
                specialDragonfire(victim.asPlayer(), projectile);
                break;

            case 2: //poisonous dragonfire attack
            case 3:
                poisonDragonfire(victim.asPlayer(), vorkath.getAsNpc(), poisonDragonfire);
                break;

            case 4: //healing dragonfire
            case 5:
                healingDragonfire(victim.asPlayer(), vorkath.getAsNpc(), healingDragonfire);
                break;


            default: //basic dragonfire
                basicDragonfire(victim.asPlayer(), vorkath.getAsNpc(), basicDragonfire);
                break;
        }
        //}
        /*else {
            meleeAttack(victim.asPlayer(), vorkath.getAsNpc(), scratchSpin);

            //freeze player and move player away from Vorkath
        }*/

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

    /*public static void meleeAttack(Player victim, NPC vorkath, Animation scratchSpin){
        int randy = RandomUtility.getRandom(500);
        vorkath.performAnimation(scratchSpin);
        vorkath.setConstitution(vorkath.getConstitution() + randy);
        victim.dealDamage(new Hit(randy, Hitmask.RED, CombatIcon.MELEE));
    }*/

    public static void basicDragonfire(Player victim, NPC vorkath, int gfx) {

        vorkath.setChargingAttack(true);
        vorkath.performAnimation(new Animation(7952 + GameSettings.OSRS_ANIM_OFFSET));
        vorkath.getCombatBuilder().setContainer(new CombatContainer(vorkath, victim, 1, 3, CombatType.RANGED, true));
        TaskManager.submit(new Task(1, vorkath, false) {
            int tick = 0;

            @Override
            public void execute() {
                if (tick == 1 && vorkath.getId() == 22061) {
                    new Projectile(vorkath, victim, gfx, 44, 3, 43, 43, 0).sendProjectile();
                } else if (tick == 2) {
                    victim.performGraphic(new Graphic(5));
                } else if (tick == 3) {
                    victim.performGraphic(new Graphic(5));
                    vorkath.setChargingAttack(false).getCombatBuilder().setAttackTimer(6);
                    stop();
                }
                tick++;
            }
        });
    }

    public static void poisonDragonfire(Player victim, NPC vorkath, int gfx) {

        vorkath.setChargingAttack(true);
        vorkath.performAnimation(new Animation(7952 + GameSettings.OSRS_ANIM_OFFSET));
        vorkath.getCombatBuilder().setContainer(new CombatContainer(vorkath, victim, 1, 3, CombatType.DRAGON_FIRE, true));

        CombatFactory.poisonEntity(victim, CombatPoisonEffect.PoisonType.SUPER);

        TaskManager.submit(new Task(1, vorkath, false) {
            int tick = 0;

            @Override
            public void execute() {
                if (tick == 1 && vorkath.getId() == 22061) {
                    new Projectile(vorkath, victim, gfx, 44, 3, 43, 43, 0).sendProjectile();
                } else if (tick == 2) {
                    victim.performGraphic(new Graphic(5));
                } else if (tick == 3) {
                    victim.performGraphic(new Graphic(5));
                    vorkath.setChargingAttack(false).getCombatBuilder().setAttackTimer(6);
                    stop();
                }
                tick++;
            }
        });

    }

    public static void healingDragonfire(Player victim, NPC vorkath, int gfx) {

        vorkath.setChargingAttack(true);
        vorkath.performAnimation(new Animation(7952 + GameSettings.OSRS_ANIM_OFFSET));
        vorkath.getCombatBuilder().setContainer(new CombatContainer(vorkath, victim, 1, 3, CombatType.DRAGON_FIRE, true));

        vorkath.setConstitution(vorkath.getConstitution() + 100);

        TaskManager.submit(new Task(1, vorkath, false) {
            int tick = 0;

            @Override
            public void execute() {
                if (tick == 1 && vorkath.getId() == 22061) {
                    new Projectile(vorkath, victim, gfx, 44, 3, 43, 43, 0).sendProjectile();
                } else if (tick == 2) {
                    victim.performGraphic(new Graphic(5));
                } else if (tick == 3) {
                    victim.performGraphic(new Graphic(5));
                    vorkath.setChargingAttack(false).getCombatBuilder().setAttackTimer(6);
                    stop();
                }
                tick++;
            }
        });

    }

    public static void specialDragonfire(Player victim, int projectile) {
        victim.getPacketSender().sendMessage("@red@Vorkath shoots a fireball into the air!");

        Player player = victim;

        Position posPlayer = victim.getPosition();
        NPC spawn = NPC.of(5090, posPlayer);
        World.register(spawn);
        NPC spawn1 = NPC.of(5090, new Position(posPlayer.getX(), posPlayer.getY() - 1, 0));
        World.register(spawn1);
        new Projectile(spawn1, spawn, projectile, 140, 1, 220, 0, 0).sendProjectile();
        //new Projectile(spawn1, spawn, 559, 140, 1, 220, 0, 0).sendProjectile();

        TaskManager.submit(new Task(4) {
            @Override
            public void execute() {
                player.getPacketSender().sendGlobalGraphic(new Graphic(311), posPlayer);

                if (player.getPosition().sameAs(posPlayer)) {
                    player.dealDamage(new Hit(RandomUtility.inclusiveRandom(400, 600), Hitmask.RED, CombatIcon.NONE));
                } else if (Locations.goodDistance(player.getPosition(), posPlayer, 1)) {
                    player.dealDamage(new Hit(RandomUtility.inclusiveRandom(100, 300), Hitmask.RED, CombatIcon.NONE));
                }


                World.deregister(spawn);
                World.deregister(spawn1);
                stop();
            }
        });
        dropCrystal(player, 0);
    }

    public static void dropCrystal(Player player, int height) {

        Position pos = randomLocation(height);
        NPC spawn = NPC.of(5090, pos);
        World.register(spawn);
        NPC spawn1 = NPC.of(5090, new Position(pos.getX(), pos.getY() - 1, height));
        World.register(spawn1);
        new Projectile(spawn1, spawn, 559, 140, 1, 220, 0, 0).sendProjectile();

        TaskManager.submit(new Task(4) {

            @Override
            public void execute() {
                player.getPacketSender().sendGlobalGraphic(new Graphic(311), pos);

                if (player.getPosition().sameAs(pos)) {
                    player.dealDamage(new Hit(RandomUtility.inclusiveRandom(150, 300), Hitmask.RED, CombatIcon.NONE));
                } else if (Locations.goodDistance(player.getPosition(), pos, 1)) {
                    player.dealDamage(new Hit(RandomUtility.inclusiveRandom(100, 200), Hitmask.RED, CombatIcon.NONE));
                }

                World.deregister(spawn);
                World.deregister(spawn1);
                stop();
            }
        });
    }

    public static Position randomLocation(int height) {
        ArrayList<Position> positions = new ArrayList<>();

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 17; y++) {
                if (x != 2462 && y != 4781)
                    positions.add(new Position(2462 + x, 4781 + y, height));
            }
        }

        return positions.get(RandomUtility.inclusiveRandom(positions.size() - 1));
    }

    public static void spawnMinion(Player player, NPC vorkath) {

        player.getPacketSender().sendMessage("@red@A spooky ghost appears!");
        int minion = 103;
        NPC n = null;

        if (player.getLocation() == Location.INSTANCEDBOSSES) {
            n = new NPC(minion, new Position(2910, 4386, player.getPosition().getZ()));
            player.getRegionInstance().getNpcsList().add(n);
        } else
            n = new NPC(minion, new Position(2462, 4770, 0));
        World.register(n);
        n.getCombatBuilder().attack(player);

    }
}
