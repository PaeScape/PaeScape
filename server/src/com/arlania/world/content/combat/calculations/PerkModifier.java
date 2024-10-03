package com.arlania.world.content.combat.calculations;

import com.arlania.world.content.combat.CombatType;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class PerkModifier {
    public static double perkModifier(Player plr, CombatType combatType) {

        double perkMultiplier = 0;

        /*if(plr.isDev()) {
            plr.getPacketSender().sendMessage("total upgrade tiers: " + plr.totalUpgradeTiers);
        }*/

        //adding bonuses from Tier 5 and Tier 10 equipment upgrades
        perkMultiplier += plr.totalUpgradeTiers;

        plr.upgradeBoost = (int) (100 * perkMultiplier);

        /*if(plr.isDev()) {
            plr.getPacketSender().sendMessage("perk multiplier: " + perkMultiplier);
        }*/

        //Berserker perk boost
        if (combatType == CombatType.MELEE)
            perkMultiplier += plr.Berserker * .05;

        //Bullseye perk boost
        if (combatType == CombatType.RANGED)
            perkMultiplier += plr.Bullseye * .05;

        //Prophetic perk boost
        if (combatType == CombatType.MAGIC)
            perkMultiplier += plr.Prophetic * .05;

        plr.perkBoost = (int) (100 * perkMultiplier);

        return perkMultiplier;
    }
}
