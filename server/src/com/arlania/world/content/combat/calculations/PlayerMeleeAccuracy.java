package com.arlania.world.content.combat.calculations;

import com.arlania.model.Skill;
import com.arlania.model.container.impl.Equipment;
import com.arlania.world.content.BonusManager;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.effect.EquipmentBonus;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.PerkHandler;
import com.arlania.world.entity.impl.player.Player;

public class PlayerMeleeAccuracy {
    public static int playerMeleeAccuracy(Player plr, Character victim) {
        int attackLevel = plr.getSkillManager().getCurrentLevel(Skill.ATTACK);

        boolean hasVoid = EquipmentBonus.wearingVoid(plr, CombatType.MELEE);

        if (PrayerHandler.isActivated(plr, PrayerHandler.CLARITY_OF_THOUGHT)) {
            attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.05;
        } else if (PrayerHandler.isActivated(plr, PrayerHandler.IMPROVED_REFLEXES)) {
            attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.1;
        } else if (PrayerHandler.isActivated(plr, PrayerHandler.INCREDIBLE_REFLEXES)) {
            attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.15;
        } else if (PrayerHandler.isActivated(plr, PrayerHandler.CHIVALRY)) {
            attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.15;
        } else if (PrayerHandler.isActivated(plr, PrayerHandler.PIETY)) {
            attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.2;
        } else if (CurseHandler.isActivated(plr, CurseHandler.LEECH_ATTACK)) {
            attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.05 + plr.getLeechedBonuses()[2];
        } else if (CurseHandler.isActivated(plr, CurseHandler.TURMOIL)) {
            attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.3 + plr.getLeechedBonuses()[2];
        }


        attackLevel *= plr.isSpecialActivated() ? plr.getCombatSpecial().getAccuracyBonus() : 1;
        int i = 1;

        switch (plr.getFightType().getBonusType()) {
            case BonusManager.ATTACK_SLASH:
                i = (int) plr.getBonusManager().getAttackBonus()[BonusManager.ATTACK_SLASH];
                break;
            case BonusManager.ATTACK_STAB:
                i = (int) plr.getBonusManager().getAttackBonus()[BonusManager.ATTACK_STAB];
                break;
            case BonusManager.ATTACK_CRUSH:
                i = (int) plr.getBonusManager().getAttackBonus()[BonusManager.ATTACK_CRUSH];
                break;
            case BonusManager.ATTACK_RANGE:
                i = (int) plr.getBonusManager().getAttackBonus()[BonusManager.ATTACK_RANGE];
                break;
            case BonusManager.ATTACK_MAGIC:
                i = (int) plr.getBonusManager().getAttackBonus()[BonusManager.ATTACK_MAGIC];
                break;
        }

        if (Equipment.hasObsidianEffect(plr) || hasVoid)
            i *= 1.20;

        if (plr.getEquipment().wearingFullInquisitor())
            i *= 1.25;


        if (victim.isNpc()) {
            NPC npc = (NPC) victim;
            if (npc.getDefenceWeakened()[0]) {
                attackLevel *= 1.1; //(int) ((0.10) * (base));
            } else if (npc.getDefenceWeakened()[1]) {
                attackLevel *= 1.2; //(int) ((0.20) * (base));
            } else if (npc.getDefenceWeakened()[2]) {
                attackLevel *= 1.3; //(int) ((0.30) * (base));
            }

            if (npc.getDefinition().getName().toLowerCase().contains("dragon") || npc.getDefinition().getName().toLowerCase().contains("olm")
                    && plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().toLowerCase().contains("dragon hunter")) {
                i *= (int) 1.50;
            }

            for (int j = 0; j < plr.getSlayer().getSlayerTask().getNpcId().length; j++) {
                if (npc.getId() == plr.getSlayer().getSlayerTask().getNpcId()[j])
                    i = (int) (i * (1 + plr.getEquipment().wearingSlayerHelm()));
            }

            if (plr.Dexterity == 1 && PerkHandler.canUseMasteryPerks(plr)) {
                double factor = plr.completedLogs * .005;
                attackLevel *= (1 + factor);
            }
        }

        return (int) (attackLevel + (attackLevel * 0.15) + (i + i * 0.04));
    }
}
