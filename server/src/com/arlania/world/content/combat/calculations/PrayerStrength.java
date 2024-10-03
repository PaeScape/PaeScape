package com.arlania.world.content.combat.calculations;

import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.entity.impl.player.Player;

public class PrayerStrength {
    public static double getPrayerStr(Player plr, CombatType combatType) {

        if (combatType == CombatType.MELEE) {
            if (plr.getPrayerActive()[1] || plr.getCurseActive()[CurseHandler.LEECH_STRENGTH])
                return 1.05;
            else if (plr.getPrayerActive()[6])
                return 1.1;
            else if (plr.getPrayerActive()[14])
                return 1.15;
            else if (plr.getPrayerActive()[24])
                return 1.18;
            else if (plr.getPrayerActive()[25])
                return 1.23;
            else if (plr.getCurseActive()[CurseHandler.TURMOIL])
                return 1.24;
        } else if (combatType == CombatType.RANGED) {
            if (plr.getCurseActive()[CurseHandler.TURMOIL] && (plr.getEquipment().contains(18895) || plr.getEquipment().contains(222249) || plr.turmoilRanged))
                return 1.35;
            if (plr.getPrayerActive()[PrayerHandler.RIGOUR])
                return 1.25;
            if (plr.getPrayerActive()[PrayerHandler.EAGLE_EYE])
                return 1.15;
            if (plr.getPrayerActive()[CurseHandler.LEECH_RANGED])
                return 1.15;
            if (plr.getPrayerActive()[PrayerHandler.SHARP_EYE])
                return 1.05;
            if (plr.getPrayerActive()[PrayerHandler.HAWK_EYE])
                return 1.10;
        } else if (combatType == CombatType.MAGIC) {
            if (plr.getPrayerActive()[PrayerHandler.MYSTIC_WILL] || plr.getCurseActive()[CurseHandler.SAP_MAGE]) {
                return 1.05;
            } else if (plr.getPrayerActive()[PrayerHandler.MYSTIC_LORE]) {
                return 1.10;
            } else if (plr.getPrayerActive()[PrayerHandler.MYSTIC_MIGHT]) {
                return 1.15;
            } else if (plr.getPrayerActive()[PrayerHandler.AUGURY]) {
                return 1.22;
            } else if (plr.getCurseActive()[CurseHandler.LEECH_MAGIC]) {
                return 1.18;
            } else if (plr.getCurseActive()[CurseHandler.TURMOIL] && (plr.getEquipment().contains(18847) || plr.getEquipment().contains(223444) || plr.turmoilMagic)) {
                return 1.35;
            }
        }

        return 1;
    }
}
