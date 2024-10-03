package com.arlania.world.content;

import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;

public class CharmBox {

    public static int blueCharm = 12163;
    public static int greenCharm = 12159;
    public static int redCharm = 12160;
    public static int goldCharm = 12158;


    public static void open(Player player) {
        int amount = 10 + RandomUtility.inclusiveRandom(20);
        player.getInventory().delete(10025, 1);
        player.getPacketSender().sendMessage("You open the charm box and receive some charms.");
        player.getInventory().add(blueCharm, amount);
        player.getInventory().add(redCharm, amount);
        player.getInventory().add(goldCharm, amount);
        player.getInventory().add(greenCharm, amount);
    }

}
