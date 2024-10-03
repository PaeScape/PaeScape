package com.arlania.world.content.combat.calculations;

import com.arlania.world.entity.impl.npc.NPC;

public class NpcMaxHit {
    public static int npcMaxHit(NPC npc) {

        int maxHit = npc.getDefinition().getMaxHit();

        if (npc.hasSummoner) {
            maxHit *= 2;
        }

        if (npc.getStrengthWeakened()[0]) {
            maxHit -= (int) ((0.10) * (maxHit));
        } else if (npc.getStrengthWeakened()[1]) {
            maxHit -= (int) ((0.20) * (maxHit));
        } else if (npc.getStrengthWeakened()[2]) {
            maxHit -= (int) ((0.30) * (maxHit));
        }

        /** CUSTOM NPCS **/
        if (npc.getId() == 2026) { //Dharok the wretched
            maxHit += (int) ((npc.getDefaultConstitution() - npc.getConstitution()) * 0.2);
        }



        return maxHit;
    }
}
