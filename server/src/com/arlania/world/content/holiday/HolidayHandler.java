package com.arlania.world.content.holiday;

import com.arlania.GameLoader;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class HolidayHandler {

    public static void handleHolidayLoot(Player player, NPC npc ) {

        if(GameLoader.getMonth() == 10 && GameLoader.getYear() == 2023 && !player.halloween23) {
            for(int i = 0; i < halloween23mobs.length; i++) {
                if(halloween23mobs[i] == npc.getId())
                    Halloween23.mobLoot(player, npc);
            }
        }

    }

    public static int[] halloween23mobs = {14655, 16837, 1471, 78};
}
