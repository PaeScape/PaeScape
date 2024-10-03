package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.model.Locations;
import com.arlania.model.Position;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

import java.util.List;

public class Vetion implements CombatStrategy {

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
        NPC vetion = (NPC) entity;
        if (vetion.isChargingAttack() || victim.getConstitution() <= 0) {
            return true;
        }
        if (Locations.goodDistance(vetion.getPosition().copy(), victim.getPosition().copy(), 3) && RandomUtility.inclusiveRandom(5) <= 3) {
            vetion.performAnimation(new Animation(5487));
            vetion.getCombatBuilder().setContainer(new CombatContainer(vetion, victim, 1, 1, CombatType.MELEE, true));
        } else {
            vetion.setChargingAttack(true);
            vetion.performAnimation(new Animation(vetion.getDefinition().getAttackAnimation()));
            vetion.getCombatBuilder().setContainer(new CombatContainer(vetion, victim, 1, 1, CombatType.MAGIC, true));

            final Position start = victim.getPosition().copy();
            final Position second = new Position(start.getX() + 2, start.getY() + RandomUtility.inclusiveRandom(2));
            final Position last = new Position(start.getX() - 2, start.getY() - RandomUtility.inclusiveRandom(2));

            final Player p = (Player) victim;
            final List<Player> list = Misc.getCombinedPlayerList(p);

            TaskManager.submit(new Task(1, vetion, false) {
                int tick = 0;

                @Override
                public void execute() {
                    if (tick == 0) {
                        p.getPacketSender().sendGlobalGraphic(new Graphic(281), start);
                        p.getPacketSender().sendGlobalGraphic(new Graphic(281), second);
                        p.getPacketSender().sendGlobalGraphic(new Graphic(281), last);
                    } else if (tick == 3) {
                        for (Player t : list) {
                            if (t == null)
                                continue;

                        }
                        vetion.setChargingAttack(false);
                        stop();
                    }
                    tick++;
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
