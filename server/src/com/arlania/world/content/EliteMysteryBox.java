package com.arlania.world.content;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.Item;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.clog.CollectionLog.CustomCollection;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

public class EliteMysteryBox {


    public static void openBox(Player player) {
        player.getInventory().delete(15501, 1);
        int rare = RandomUtility.inclusiveRandom(1, 10);

        player.getInventory().add(6199, 1);

        int[] MysteryLoot = GameSettings.LEGENDARYUNIQUES;

        int randitem = RandomUtility.inclusiveRandom(MysteryLoot.length - 1);
        player.getInventory().add(MysteryLoot[randitem], 1);
        String itemName = new Item(MysteryLoot[randitem]).getDefinition().getName();

        String discordMessage = "[ELITE BOX] " + player.getUsername() + " has just received " + itemName + " from an Elite Mystery Box! " + GameSettings.PEEPOLOVE;
        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
        PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemName + " from an Elite Mystery Box!");

        player.getCollectionLog().handleDrop(CustomCollection.MysteryBox.getId(), MysteryLoot[randitem], 1);
    }


}
