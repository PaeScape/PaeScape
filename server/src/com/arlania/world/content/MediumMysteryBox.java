package com.arlania.world.content;

import com.arlania.GameSettings;
import com.arlania.model.Item;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

public class MediumMysteryBox {


    public static void openBox(Player player) {
        player.getInventory().delete(602, 1);
        int rare = RandomUtility.inclusiveRandom(1, 20);


        if ((rare == 1)) //rare loot
        {
            int[] MysteryLoot = GameSettings.MASTERUNIQUES;

            int randitem = RandomUtility.inclusiveRandom(MysteryLoot.length - 1);
            player.getInventory().add(MysteryLoot[randitem], 1);
            String itemName = new Item(MysteryLoot[randitem]).getDefinition().getName();
            String message = "@blu@[MYSTERY BOX] " + player.getUsername() + " has just received @red@" + itemName + "@blu@ from a Medium Mystery Box!";
            World.sendMessage("drops", message);
            PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemName + " from a Mystery Box!");
        } else if ((rare >= 2) && (rare <= 6)) //rare loot
        {
            int[] MysteryLoot = GameSettings.LEGENDARYUNIQUES;

            int randitem = RandomUtility.inclusiveRandom(MysteryLoot.length - 1);
            player.getInventory().add(MysteryLoot[randitem], 1);
            String itemName = new Item(MysteryLoot[randitem]).getDefinition().getName();
            String message = "@blu@[MYSTERY BOX] " + player.getUsername() + " has just received @red@" + itemName + "@blu@ from a Medium Mystery Box!";
            World.sendMessage("drops", message);
            PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemName + " from a Mystery Box!");
        } else {
            int[] MysteryLoot = GameSettings.HIGHUNIQUES;

            int randitem = RandomUtility.inclusiveRandom(MysteryLoot.length - 1);
            player.getInventory().add(MysteryLoot[randitem], 1);
            String itemName = new Item(MysteryLoot[randitem]).getDefinition().getName();
            String message = "@blu@[MYSTERY BOX] " + player.getUsername() + " has just received @red@" + itemName + "@blu@ from a Medium Mystery Box!";
            World.sendMessage("drops", message);
            PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemName + " from a Mystery Box!");

        }


        player.getInventory().add(601, 1);

    }


}
