package com.arlania.world.content.combat.calculations;

import com.arlania.model.Locations;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.minigames.impl.kingdom.NobilitySystem;
import com.arlania.world.content.minigames.impl.strongholdraids.Equipment;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class PvMModifier {
    public static double pvmModifier(Player plr, NPC victim, CombatType combatType) {
        double damageMultiplier = 0;

        NPC npc = victim;

        if (npc == null)
            return 0;

        if (npc.getDefenceWeakened()[0]) {
            damageMultiplier += .1; //(int) ((0.10) * (base));
        } else if (npc.getDefenceWeakened()[1]) {
            damageMultiplier += .2; //(int) ((0.20) * (base));
        } else if (npc.getDefenceWeakened()[2]) {
            damageMultiplier += .3; //(int) ((0.30) * (base));
        }

        //Volatile spec
        if (plr.Volatile && combatType == CombatType.MAGIC) {
            damageMultiplier += 1;
            plr.Volatile = false;
        }

        //Vesta Set
        if (plr.wildVesta > 0 && plr.getEquipment().countVesta(plr) > 0 && victim.getLocation() == Locations.Location.WILDERNESS && combatType == CombatType.MELEE)
            damageMultiplier += plr.wildVesta * plr.getEquipment().countVesta(plr) * .10;

        //Statius Set
        if (plr.wildStatius > 0 & plr.getEquipment().countStatius(plr) > 0 && victim.getLocation() == Locations.Location.WILDERNESS && combatType == CombatType.MELEE)
            damageMultiplier += plr.wildStatius * plr.getEquipment().countStatius(plr) * .10;

        //Morrigan Set
        if (plr.wildMorrigan > 0 & plr.getEquipment().countMorrigan(plr) > 0 && victim.getLocation() == Locations.Location.WILDERNESS && combatType == CombatType.RANGED)
            damageMultiplier += plr.wildMorrigan * plr.getEquipment().countMorrigan(plr) * .10;

        //Zuriel Set
        if (plr.wildZuriel > 0 & plr.getEquipment().countZuriel(plr) > 0 && victim.getLocation() == Locations.Location.WILDERNESS && combatType == CombatType.MAGIC)
            damageMultiplier += plr.wildZuriel * plr.getEquipment().countZuriel(plr) * .10;

        //Boss Weaknesses
        damageMultiplier += npc.specialWeaknesses(plr, npc, combatType);

        if(plr.getLocation() == Locations.Location.SHR)
            damageMultiplier += Equipment.damageBonus(plr);

        damageMultiplier *= (1 + NobilitySystem.getNobilityBoost(plr));

        plr.pvmBoost = (int) (100 * damageMultiplier);

        return damageMultiplier;
    }

    public static boolean bossImmunity(Player plr, NPC victim, CombatType combatType) {
        NPC npc = victim;

        if (npc == null)
            return true;

        if (npc.immuneToMelee(npc, plr) && combatType == CombatType.MELEE)
            return true;
        if (npc.immuneToRange(npc, plr) && combatType == CombatType.RANGED)
            return true;
        if (npc.immuneToMagic(npc, plr) && combatType == CombatType.MAGIC)
            return true;

        if (npc.getId() == 13447) {
            if (plr.getRaidsParty() != null) {
                return plr.getRaidsParty().nexMinionKills < 4;
            }
        }

        return false;
    }
}
