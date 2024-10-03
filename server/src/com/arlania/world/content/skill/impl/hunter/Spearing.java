package com.arlania.world.content.skill.impl.hunter;

import com.arlania.model.*;
import com.arlania.model.container.impl.Equipment;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatFactory;
import com.arlania.world.content.combat.effect.CombatPoisonEffect.PoisonType;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class Spearing {

    public static void spearNPC(Player player, int npcId, NPC npc) {

        int reward = 995;
        int rewardQty = 0;
        int lvlreq = 1;
        int nofail = 99;
        int xp = 0;
        Position pos = player.getPosition();

        if (!player.getClickDelay().elapsed(300))
            return;

        if (npcId == 5103)//Kyatt
        {
            lvlreq = 80;
            xp = 300;
            reward = 10104;
            rewardQty = 1;
        } else if (npcId == 5104)//Larupia
        {
            lvlreq = 40;
            xp = 100;
            reward = 10096;
            rewardQty = 1;
        } else if (npcId == 5105)//Graahk
        {
            lvlreq = 60;
            xp = 200;
            reward = 10100;
            rewardQty = 1;
        } else if (npcId == 21785)//Herbiboar
        {
            lvlreq = 90;
            xp = 400;
            int[] possibleReward = {200, 202, 204, 206, 208, 210, 212, 214, 216, 218, 220, 2486, 3050, 3052};
            reward = possibleReward[RandomUtility.inclusiveRandom(possibleReward.length - 1)];
            rewardQty = 1;
        }

        nofail = lvlreq * 2;

        int spearReq = RandomUtility.inclusiveRandom(1, nofail);

        int spear = 0;

        if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().toLowerCase().contains("spear")) {
            if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().toLowerCase().contains("bronze"))
                spear = 1;
            else if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().toLowerCase().contains("iron"))
                spear = 3;
            else if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().toLowerCase().contains("steel"))
                spear = 5;
            else if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().toLowerCase().contains("mithril"))
                spear = 8;
            else if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().toLowerCase().contains("adamant"))
                spear = 12;
            else if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().toLowerCase().contains("rune"))
                spear = 15;
            else if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().toLowerCase().contains("dragon"))
                spear = 20;
            else if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().toLowerCase().contains("guthan"))
                spear = 25;
            else if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().toLowerCase().contains("zamor"))
                spear = 30;
        } else {
            player.getPacketSender().sendMessage("You must use a spear to hunt these.");
            return;
        }


        if (player.getCombatBuilder().isBeingAttacked()) {
            player.getPacketSender().sendMessage("You must wait a few seconds after being out of combat before doing this.");
            return;
        }
        if (player.getSkillManager().getCurrentLevel(Skill.HUNTER) < lvlreq) {
            player.getPacketSender().sendMessage("You need a Hunter level of at least " + lvlreq + " to spear this npc.");
            return;
        }

        player.performAnimation(new Animation(12006));

        if (spearReq > (player.getSkillManager().getCurrentLevel(Skill.HUNTER) + spear)) {
            npc.forceChat("RAAAARRRRRRRR");
            npc.performAnimation(new Animation(5228));
            player.dealDamage(new Hit(lvlreq, Hitmask.RED, CombatIcon.NONE));

            if (npc.getId() == 21785 && !player.isPoisoned() && player.getPoisonImmunity() < 1) {
                player.dealDamage(new Hit(lvlreq, Hitmask.DARK_GREEN, CombatIcon.NONE));
                CombatFactory.poisonEntity(player, PoisonType.SUPER);
            }
        } else {


            if (player.getEquipment().contains(10069))
                xp *= 1.25;

            player.getInventory().add(reward, rewardQty * player.acceleratedResources());
            player.getSkillManager().addExperience(Skill.HUNTER, xp * player.acceleratedResources());

            for (int i = 0; i < player.getSkiller().getSkillerTask().getObjId().length; i++) {
                if (npcId == player.getSkiller().getSkillerTask().getObjId()[i]) {
                    for (int k = 0; k < player.acceleratedResources(); k++) {
                        player.getSkiller().handleSkillerTaskGather(true);
                        player.getSkillManager().addExperience(Skill.SKILLER, player.getSkiller().getSkillerTask().getXP());
                    }
                }
            }
        }
    }
}

	
