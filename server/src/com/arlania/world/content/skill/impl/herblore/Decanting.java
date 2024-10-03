package com.arlania.world.content.skill.impl.herblore;

import com.arlania.world.entity.impl.player.Player;

/**
 * @author Jason MacKeigan (http://www.rune-server.org/members/jason) - base.
 * @author Levi Patton (http://www.rune-server.org/members/auguryps) - Converted.
 * @since September 16, 2016
 */

public class Decanting {


    public static void checkRequirements(Player player) {

        startDecanting(player);
        player.getPacketSender().sendMessage("@blu@Success! Your potions have been combined.");

    }

    public static void startDecanting(Player player) {
        for (Potion p : Potion.values()) {
            int full = p.getFullId();
            int half = p.getHalfId();
            int quarter = p.getQuarterId();
            int threeQuarters = p.getThreeQuartersId();
            int totalDoses = 0;
            int remainder = 0;
            int totalEmptyPots = 0;
            if (player.getInventory().contains(threeQuarters)) {
                totalDoses += (3 * player.getInventory().getAmount(threeQuarters));
                totalEmptyPots += player.getInventory().getAmount(threeQuarters);
                player.getInventory().delete(threeQuarters, player.getInventory().getAmount(threeQuarters));
            }
            if (player.getInventory().contains(half)) {
                totalDoses += (2 * player.getInventory().getAmount(half));
                totalEmptyPots += player.getInventory().getAmount(half);
                player.getInventory().delete(half, player.getInventory().getAmount(half));
            }
            if (player.getInventory().contains(quarter)) {
                totalDoses += (player.getInventory().getAmount(quarter));
                totalEmptyPots += player.getInventory().getAmount(quarter);
                player.getInventory().delete(quarter, player.getInventory().getAmount(quarter));
            }
            if (totalDoses > 0) {
                if (totalDoses >= 4)
                    player.getInventory().add(full, totalDoses / 4);

                if ((totalDoses % 4) != 0) {
                    totalEmptyPots -= 1;
                    remainder = totalDoses % 4;
                    if (remainder == 3)
                        player.getInventory().add(threeQuarters, 1);
                    else if (remainder == 2)
                        player.getInventory().add(half, 1);
                    else if (remainder == 1)
                        player.getInventory().add(quarter, 1);
                }
                totalEmptyPots -= (totalDoses / 4);
                player.getInventory().add(229, totalEmptyPots);
            }
        }

    }


}