package com.arlania.world.content;

import com.arlania.GameSettings;
import com.arlania.model.Item;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

public class SmallMysteryBox {


    public static void openBox(Player player) {
        player.getInventory().delete(601, 1);
        int rare = RandomUtility.inclusiveRandom(1, 20);


        if ((rare == 1)) //rare loot
        {
            int[] MysteryLoot = GameSettings.LEGENDARYUNIQUES;

            int randitem = RandomUtility.inclusiveRandom(MysteryLoot.length - 1);
            player.getInventory().add(MysteryLoot[randitem], 1);
            String itemName = new Item(MysteryLoot[randitem]).getDefinition().getName();
            PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemName + " from a Mystery Box!");
        } else if ((rare >= 2) && (rare <= 6)) //rare loot
        {
            int[] MysteryLoot = GameSettings.HIGHUNIQUES;

            int randitem = RandomUtility.inclusiveRandom(MysteryLoot.length - 1);
            player.getInventory().add(MysteryLoot[randitem], 1);
            String itemName = new Item(MysteryLoot[randitem]).getDefinition().getName();
            PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemName + " from a Mystery Box!");
        } else {
            int[] MysteryLoot = GameSettings.MEDIUMUNIQUES;

            int randitem = RandomUtility.inclusiveRandom(MysteryLoot.length - 1);
            player.getInventory().add(MysteryLoot[randitem], 1);
            String itemName = new Item(MysteryLoot[randitem]).getDefinition().getName();
            PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemName + " from a Mystery Box!");

        }

        int[] MysteryFreeLoot = GameSettings.LOWUNIQUES;

        int freeitem = RandomUtility.inclusiveRandom(MysteryFreeLoot.length - 1);
        player.getInventory().add(MysteryFreeLoot[freeitem], 1);

    }


}
