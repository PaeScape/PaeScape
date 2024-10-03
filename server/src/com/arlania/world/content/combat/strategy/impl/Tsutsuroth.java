package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatHitTask;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class Tsutsuroth implements CombatStrategy {

    private static final Animation anim1 = new Animation(6947);
    private static final Animation anim2 = new Animation(14971);
    private static final Animation osrs_anim1 = new Animation(25905);
    private static final Animation osrs_anim2 = new Animation(21330);
    private static final Graphic graphic1 = new Graphic(1211, GraphicHeight.MIDDLE);
    private static final Graphic graphic2 = new Graphic(390);

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
        NPC tsutsuroth = (NPC) entity;
        if (victim.getConstitution() <= 0) {
            return true;
        }
        if (tsutsuroth.isChargingAttack()) {
            return true;
        }
        Player target = (Player) victim;
        CombatType style = RandomUtility.inclusiveRandom(8) >= 6 ? CombatType.MELEE : CombatType.MAGIC;
        if (style == CombatType.MELEE) {

            if (tsutsuroth.getId() == 12766)
                tsutsuroth.performAnimation(new Animation(14963));
            else if (tsutsuroth.getId() == 6203)
                tsutsuroth.performAnimation(new Animation(6945));
            else if (tsutsuroth.getId() == 20495)
                tsutsuroth.performAnimation(osrs_anim1);

            tsutsuroth.getCombatBuilder().setContainer(new CombatContainer(tsutsuroth, victim, 1, 1, CombatType.MELEE, true));
            int specialAttack = RandomUtility.inclusiveRandom(4);
            if (specialAttack == 2) {
                int amountToDrain = RandomUtility.inclusiveRandom(400);
                target.getPacketSender().sendMessage("The demon slams through your defence and steals some Prayer points..");
                if (amountToDrain > target.getSkillManager().getCurrentLevel(Skill.PRAYER)) {
                    amountToDrain = target.getSkillManager().getCurrentLevel(Skill.PRAYER);
                }
                target.getSkillManager().setCurrentLevel(Skill.PRAYER, target.getSkillManager().getCurrentLevel(Skill.PRAYER) - amountToDrain);
                if (target.getSkillManager().getCurrentLevel(Skill.PRAYER) <= 0) {
                    target.getPacketSender().sendMessage("You have run out of Prayer points!");
                }
            }
        } else {
            if (tsutsuroth.getId() == 6203)
                tsutsuroth.performAnimation(anim1);
            else if (tsutsuroth.getId() == 12766)
                tsutsuroth.performAnimation(anim2);
            else if (tsutsuroth.getId() == 20495)
                tsutsuroth.performAnimation(osrs_anim2);


            tsutsuroth.setChargingAttack(true);
            TaskManager.submit(new Task(2, target, false) {
                int tick = 0;

                @Override
                public void execute() {
                    switch (tick) {
                        case 0:
                            for (Player t : Misc.getCombinedPlayerList(target)) {
                                if (t.getPosition().distanceToPoint(tsutsuroth.getPosition().getX(), tsutsuroth.getPosition().getY()) > 20)
                                    continue;
                                new Projectile(tsutsuroth, target, graphic1.getId(), 44, 3, 43, 43, 0).sendProjectile();
                            }
                            break;
                        case 2:
                            for (Player t : Misc.getCombinedPlayerList(target)) {
                                target.performGraphic(graphic2);
                                tsutsuroth.getCombatBuilder().setVictim(t);
                                new CombatHitTask(tsutsuroth.getCombatBuilder(), new CombatContainer(tsutsuroth, t, 1, CombatType.MAGIC, true)).handleAttack();
                            }
                            tsutsuroth.setChargingAttack(false);
                            stop();
                            break;
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
