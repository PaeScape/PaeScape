package com.arlania.world.content.combat.strategy.impl;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatHitTask;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class VerzikVitur implements CombatStrategy {

    private static final Animation melee_anim = new Animation(29382);
    private static final Animation magic_anim = new Animation(8125 + GameSettings.OSRS_ANIM_OFFSET);
    private static final Graphic blood = new Graphic(373);


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
        NPC VerzikVitur = (NPC) entity;
        if (VerzikVitur.isChargingAttack() || VerzikVitur.getConstitution() <= 0) {
            return true;
        }
        //CombatType style = RandomUtility.getRandom(4) <= 1 && Locations.goodDistance(VerzikVitur.getPosition(), victim.getPosition(), 1) ? CombatType.MELEE : CombatType.MAGIC;

        CombatType style = CombatType.MELEE;
        int attackStyle = RandomUtility.inclusiveRandom(5);

        if (attackStyle < 3) {
            style = CombatType.MELEE;
        } else {
            style = CombatType.MAGIC;
        }

        if (style == CombatType.MELEE) {
            VerzikVitur.performAnimation(melee_anim);
            VerzikVitur.getCombatBuilder().setContainer(new CombatContainer(VerzikVitur, victim, 1, 1, CombatType.MELEE, true));
        } else {
            //VerzikVitur.forceChat("BLEEEEEEED!");
            VerzikVitur.performAnimation(magic_anim);
            //VerzikVitur.setChargingAttack(true);
            Player target = (Player) victim;
            for (Player t : Misc.getCombinedPlayerList(target)) {
                if (t == null || t.isTeleporting())
                    continue;
                if (t.getPosition().distanceToPoint(VerzikVitur.getPosition().getX(), VerzikVitur.getPosition().getY()) > 20)
                    continue;

            }//for
            TaskManager.submit(new Task(2, target, false) {
                @Override
                public void execute() {
                    for (Player t : Misc.getCombinedPlayerList(target)) {
                        if (t == null)
                            continue;
                        VerzikVitur.getCombatBuilder().setVictim(t);
                        new CombatHitTask(VerzikVitur.getCombatBuilder(), new CombatContainer(VerzikVitur, t, 1, CombatType.MAGIC, true)).handleAttack();
                        t.performGraphic(blood);
                        int randHeal = RandomUtility.inclusiveRandom(200);
                        VerzikVitur.setConstitution(VerzikVitur.getConstitution() + randHeal);
                        if (VerzikVitur.getConstitution() > VerzikVitur.getDefinition().getHitpoints())
                            VerzikVitur.setConstitution(VerzikVitur.getDefinition().getHitpoints());

                    }//for
                    //VerzikVitur.setChargingAttack(false);
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
        return 6;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }

}