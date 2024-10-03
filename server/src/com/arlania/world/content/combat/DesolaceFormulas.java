package com.arlania.world.content.combat;

import com.arlania.model.Locations.Location;
import com.arlania.model.Skill;
import com.arlania.model.container.impl.Equipment;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.effect.EquipmentBonus;
import com.arlania.world.content.combat.magic.CombatSpell;
import com.arlania.world.content.combat.magic.CombatSpells;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.content.skill.SkillManager;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.PerkHandler;
import com.arlania.world.entity.impl.player.Player;

public class DesolaceFormulas {


    /**
     * Calculates a player's Melee Defence level
     *
     * @param plr The player to calculate Melee defence for
     * @return The player's Melee defence level
     */
    public static int getMeleeDefence(Player plr) {
        int defenceLevel = plr.getSkillManager().getCurrentLevel(Skill.DEFENCE);
        int i = (int) plr.getBonusManager().getDefenceBonus()[bestMeleeDef(plr)];
        if (plr.getPrayerActive()[PrayerHandler.THICK_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.05;
        } else if (plr.getPrayerActive()[PrayerHandler.ROCK_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.1;
        } else if (plr.getPrayerActive()[PrayerHandler.STEEL_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.15;
        } else if (plr.getPrayerActive()[PrayerHandler.CHIVALRY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.2;
        } else if (plr.getPrayerActive()[PrayerHandler.PIETY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (plr.getPrayerActive()[PrayerHandler.RIGOUR]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (plr.getPrayerActive()[PrayerHandler.AUGURY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (plr.getCurseActive()[CurseHandler.TURMOIL]) { // turmoil
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.15;
        }

        if (plr.Stability == 1 && PerkHandler.canUseMasteryPerks(plr)) {
            double factor = plr.completedLogs * .005;
            defenceLevel *= (1 + factor);
        }

        return (int) (defenceLevel + (defenceLevel * 0.15) + (i + i * 1.0));
    }

    public static int bestMeleeDef(Player p) {
        if (p.getBonusManager().getDefenceBonus()[0] > p.getBonusManager().getDefenceBonus()[1] && p.getBonusManager().getDefenceBonus()[0] > p.getBonusManager().getDefenceBonus()[2]) {
            return 0;
        }
        if (p.getBonusManager().getDefenceBonus()[1] > p.getBonusManager().getDefenceBonus()[0] && p.getBonusManager().getDefenceBonus()[1] > p.getBonusManager().getDefenceBonus()[2]) {
            return 1;
        }
        return p.getBonusManager().getDefenceBonus()[2] <= p.getBonusManager().getDefenceBonus()[0] || p.getBonusManager().getDefenceBonus()[2] <= p.getBonusManager().getDefenceBonus()[1] ? 0 : 2;
    }


    /**
     * Calculates a player's Ranged attack (level).
     * Credits: Dexter Morgan
     *
     * @param plr The player to calculate Ranged attack level for
     * @return The player's Ranged attack level
     */
    public static int getRangedAttack(Player plr) {
        int rangeLevel = plr.getSkillManager().getCurrentLevel(Skill.RANGED);
        boolean hasVoid = EquipmentBonus.wearingVoid(plr, CombatType.RANGED);
        double accuracy = plr.isSpecialActivated() ? plr.getCombatSpecial().getAccuracyBonus() : 1;
        rangeLevel *= accuracy;
        if (hasVoid) {
            rangeLevel += SkillManager.getLevelForExperience(plr.getSkillManager().getExperience(Skill.RANGED), plr) * 0.15;
        }
        if (plr.getCurseActive()[PrayerHandler.SHARP_EYE] || plr.getCurseActive()[CurseHandler.SAP_RANGER]) {
            rangeLevel *= 1.05;
        }
        if (plr.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 15492) {
            rangeLevel *= 1.2;
        } else if (plr.getPrayerActive()[PrayerHandler.HAWK_EYE]) {
            rangeLevel *= 1.10;
        } else if (plr.getPrayerActive()[PrayerHandler.EAGLE_EYE]) {
            rangeLevel *= 1.15;
        } else if (plr.getPrayerActive()[PrayerHandler.RIGOUR]) {
            rangeLevel *= 1.25;
        } else if (plr.getCurseActive()[CurseHandler.LEECH_RANGED]) {
            rangeLevel *= 1.15;
        } else if (plr.getCurseActive()[CurseHandler.TURMOIL] &&
                (plr.getEquipment().contains(18895) || plr.getEquipment().contains(222249) || plr.turmoilRanged)) {
            rangeLevel *= 1.35;
        }

        /** SLAYER HELMET **/
        rangeLevel = (int) (rangeLevel * (1 + plr.getEquipment().wearingSlayerHelm()));

        if (plr.getLocation() == Location.OLM && plr.getEquipment().contains(4452))
            rangeLevel *= 1.25;

        if (plr.Dexterity == 1 && PerkHandler.canUseMasteryPerks(plr)) {
            double factor = plr.completedLogs * .005;
            rangeLevel *= (1 + factor);
        }

        return (int) (rangeLevel + (plr.getBonusManager().getAttackBonus()[4] * 2));
    }

    /**
     * Calculates a player's Ranged defence level.
     *
     * @param plr The player to calculate the Ranged defence level for
     * @return The player's Ranged defence level
     */
    public static int getRangedDefence(Player plr) {
        int defenceLevel = plr.getSkillManager().getCurrentLevel(Skill.DEFENCE);
        if (plr.getPrayerActive()[PrayerHandler.THICK_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.05;
        } else if (plr.getPrayerActive()[PrayerHandler.ROCK_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.1;
        } else if (plr.getPrayerActive()[PrayerHandler.STEEL_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.15;
        } else if (plr.getPrayerActive()[PrayerHandler.CHIVALRY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.2;
        } else if (plr.getPrayerActive()[PrayerHandler.PIETY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (plr.getPrayerActive()[PrayerHandler.RIGOUR]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (plr.getPrayerActive()[PrayerHandler.AUGURY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (plr.getCurseActive()[CurseHandler.TURMOIL]) { // turmoil
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE)
                    * 0.20 + plr.getLeechedBonuses()[0];
        }

        if (plr.Stability == 1 && PerkHandler.canUseMasteryPerks(plr)) {
            double factor = plr.completedLogs * .005;
            defenceLevel *= (1 + factor);
        }

        return (int) (defenceLevel + plr.getBonusManager().getDefenceBonus()[4] + (plr.getBonusManager().getDefenceBonus()[4] / 2));
    }

    public static int getMagicAttack(Player plr) {
        boolean voidEquipment = EquipmentBonus.wearingVoid(plr, CombatType.MAGIC);
        int attackLevel = plr.getSkillManager().getCurrentLevel(Skill.MAGIC);
        if (voidEquipment)
            attackLevel += plr.getSkillManager().getCurrentLevel(Skill.MAGIC) * 0.2;
        if (plr.getPrayerActive()[PrayerHandler.MYSTIC_WILL] || plr.getCurseActive()[CurseHandler.SAP_MAGE]) {
            attackLevel *= 1.05;
        } else if (plr.getPrayerActive()[PrayerHandler.MYSTIC_LORE]) {
            attackLevel *= 1.10;
        } else if (plr.getPrayerActive()[PrayerHandler.MYSTIC_MIGHT]) {
            attackLevel *= 1.15;
        } else if (plr.getPrayerActive()[PrayerHandler.AUGURY]) {
            attackLevel *= 1.22;
        } else if (plr.getCurseActive()[CurseHandler.LEECH_MAGIC]) {
            attackLevel *= 1.18;
        } else if (plr.getCurseActive()[CurseHandler.TURMOIL] &&
                (plr.getEquipment().contains(18847) || plr.getEquipment().contains(223444) || plr.turmoilMagic)) {
            attackLevel *= 1.35;
        }

        attackLevel *= plr.isSpecialActivated() ? plr.getCombatSpecial().getAccuracyBonus() : 1;

        switch (plr.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId()) {
            case 18346: //Tome of Frost
            case 20580: //Tome of Fire
                attackLevel += .30;
                break;
        }

        /** SLAYER HELMET **/

        attackLevel = (int) (attackLevel * (1 + plr.getEquipment().wearingSlayerHelm()));

        if (plr.Dexterity == 1 && PerkHandler.canUseMasteryPerks(plr)) {
            double factor = plr.completedLogs * .005;
            attackLevel *= (1 + factor);
        }

        return (int) (attackLevel + (plr.getBonusManager().getAttackBonus()[3] * 2));
    }

    /**
     * Calculates a player's magic defence level
     *
     * @param player The player to calculate magic defence level for
     * @return The player's magic defence level
     */
    public static int getMagicDefence(Player plr) {


        int defenceLevel = plr.getSkillManager().getCurrentLevel(Skill.DEFENCE) / 2 + plr.getSkillManager().getCurrentLevel(Skill.MAGIC) / 2;

        if (plr.getPrayerActive()[PrayerHandler.THICK_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.05;
        } else if (plr.getPrayerActive()[PrayerHandler.ROCK_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.1;
        } else if (plr.getPrayerActive()[PrayerHandler.STEEL_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.15;
        } else if (plr.getPrayerActive()[PrayerHandler.CHIVALRY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.2;
        } else if (plr.getPrayerActive()[PrayerHandler.PIETY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (plr.getPrayerActive()[PrayerHandler.RIGOUR]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (plr.getPrayerActive()[PrayerHandler.AUGURY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (plr.getCurseActive()[CurseHandler.TURMOIL]) { // turmoil
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE)
                    * 0.20 + plr.getLeechedBonuses()[0];
        }

        if (plr.Stability == 1 && PerkHandler.canUseMasteryPerks(plr)) {
            double factor = plr.completedLogs * .005;
            defenceLevel *= (1 + factor);
        }

        return (int) (defenceLevel + plr.getBonusManager().getDefenceBonus()[3] + (plr.getBonusManager().getDefenceBonus()[3] / 3));
    }




    public static int getAttackDelay(Player plr) {
        //use getAttackSpeed() from Player.java

        int speed = 5;
        return speed;
    }
}
