package com.arlania.world.content;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.Item;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.clog.CollectionLog.CustomCollection;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

public class CustomMysteryBox {


    public static void openBox(Player player) {
        player.getInventory().delete(604, 1);

        int[] loots = GameSettings.CUSTOMUNIQUES;

        int rare = RandomUtility.inclusiveRandom(loots.length - 1);


        player.getInventory().add(loots[rare], 1);

        String itemName = new Item(loots[rare]).getDefinition().getName();

        String discordMessage = "[CUSTOM BOX] " + player.getUsername() + " has just received " + itemName + " from a Custom Mystery Box! " + GameSettings.PEEPOLOVE;
        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
        PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemName + " from a Custom Mystery Box!");

    }


}
