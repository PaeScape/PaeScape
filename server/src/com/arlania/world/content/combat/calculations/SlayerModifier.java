package com.arlania.world.content.combat.calculations;

import com.arlania.world.content.combat.CombatType;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class SlayerModifier {

    public static double slayerModifier(Player plr, NPC victim) {

        double slayerMultiplier = 0;

        for (int i = 0; i < plr.getSlayer().getSlayerTask().getNpcId().length; i++) {
            if (victim.getId() == plr.getSlayer().getSlayerTask().getNpcId()[i]) {
                slayerMultiplier += plr.getEquipment().wearingSlayerHelm();
            }
        }

        plr.slayerBoost = (int) (100 * slayerMultiplier);

        return slayerMultiplier;
    }
}
