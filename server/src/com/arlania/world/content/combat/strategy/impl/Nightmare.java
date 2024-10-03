package com.arlania.world.content.combat.strategy.impl;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

import java.util.ArrayList;

public class Nightmare implements CombatStrategy {

	
	/*8566 - fades away
	8567 - comes out of the ground
	8573 - lays on the ground and then floats up
	8580 - spins in the air and then lays down (dies?)
	8594 - Scratches (straight up)
	8595 - Scratches (spin)
	8597 - Charging (bats flying around?)
	8600 - In pain? Looks cool
	8607 - jumps into the ground
	8609 - jumps out of the ground
	8634 - Walking*/

    Animation walk = new Animation(8634 + GameSettings.OSRS_ANIM_OFFSET);
    Animation die = new Animation(8580 + GameSettings.OSRS_ANIM_OFFSET);

    Animation scratch = new Animation(8594 + GameSettings.OSRS_ANIM_OFFSET);
    Animation scratchSpin = new Animation(8595 + GameSettings.OSRS_ANIM_OFFSET);
    Animation charge = new Animation(8597 + GameSettings.OSRS_ANIM_OFFSET);

    Animation portalIn = new Animation(8607 + GameSettings.OSRS_ANIM_OFFSET);
    Animation portalOut = new Animation(8609 + GameSettings.OSRS_ANIM_OFFSET);


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
        NPC nightmare = (NPC) entity;
        if (victim.getConstitution() <= 0) {
            return true;
        }


        if ((Locations.goodDistance(nightmare.getPosition().copy(), victim.getPosition().copy(), 3) && RandomUtility.inclusiveRandom(15) <= 2) &&
                (nightmare.getConstitution() >= 18000)) {
            int randy = RandomUtility.inclusiveRandom(500);
            nightmare.performAnimation(scratchSpin);
            nightmare.setConstitution(nightmare.getConstitution() + randy);
            victim.dealDamage(new Hit(randy, Hitmask.RED, CombatIcon.MELEE));
        } else {
            nightmare.performAnimation(charge);
            nightmare.getCombatBuilder().setContainer(new CombatContainer(nightmare, victim, 2, 3, CombatType.MAGIC, true));
            victim.performGraphic(new Graphic(377));

            nightmare.walkToPosition(victim.getPosition());

        }

        if (nightmare.getConstitution() <= 12000) {
            Player player = (Player) victim;

            Position posPlayer = victim.getPosition();
            NPC spawn = NPC.of(5090, posPlayer);
            World.register(spawn);
            NPC spawn1 = NPC.of(5090, new Position(posPlayer.getX(), posPlayer.getY() - 1, 0));
            World.register(spawn1);
            new Projectile(spawn1, spawn, 500 + GameSettings.OSRS_GFX_OFFSET, 140, 1, 220, 0, 0).sendProjectile();
            //new Projectile(spawn1, spawn, 559, 140, 1, 220, 0, 0).sendProjectile();

            TaskManager.submit(new Task(5) {
                @Override
                public void execute() {
                    player.getPacketSender().sendGlobalGraphic(new Graphic(311), posPlayer);

                    if (player.getPosition().sameAs(posPlayer)) {
                        player.dealDamage(new Hit(RandomUtility.inclusiveRandom(300, 450), Hitmask.RED, CombatIcon.NONE));
                    } else if (Locations.goodDistance(player.getPosition(), posPlayer, 1)) {
                        player.dealDamage(new Hit(RandomUtility.inclusiveRandom(100, 200), Hitmask.RED, CombatIcon.NONE));
                    }


                    World.deregister(spawn);
                    World.deregister(spawn1);
                    stop();
                }
            });

            dropCrystal(player, 0);
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

    public static void spawnMinion(Player player) {

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
