package com.arlania.world.content;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.Item;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.clog.CollectionLog.CustomCollection;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

public class MysteryBox {


    public static void openBox(Player player) {
        player.getInventory().delete(6199, 1);
        int rare = RandomUtility.inclusiveRandom(1, 100);

        player.getCollectionLog().addKill(CustomCollection.MysteryBox.getId());


        if (rare < 6) //rare loot
        {
            int[] MysteryLoot = GameSettings.LEGENDARYUNIQUESfromBOX;

            int randitem = RandomUtility.inclusiveRandom(MysteryLoot.length - 1);
            player.getInventory().add(MysteryLoot[randitem], 1);
            String itemName = new Item(MysteryLoot[randitem]).getDefinition().getName();

            String discordMessage = "[MYSTERY BOX] " + player.getUsername() + " has just received " + itemName + " from a Mystery Box!";
            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());

            PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemName + " from a Mystery Box!");
            player.getCollectionLog().handleDrop(CustomCollection.MysteryBox.getId(), MysteryLoot[randitem], 1);
        } else if ((rare >= 6) && (rare <= 30)) { //common loot
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
