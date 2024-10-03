package com.arlania.world.content;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.Item;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.clog.CollectionLog.CustomCollection;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

public class SupremeMysteryBox {


    public static void openBox(Player player) {
        player.getInventory().delete(603, 1);

        int[] loots = {21000, 21001, 21002, 21003, 21004, 21005, 21006,  //ToB
                21031, 21032, 21033, 20012, 20010, 20011, 20013, 20014, 14062, 20016, 20017, 20018, 222951,  //Chaos Raids
                20998, 4450, 4454, 4452, 18888, 18889, 18890, 14484, 18844,  //CoX Rewards
                14008, 14009, 14010, 14011, 14012, 14013, 14014, 14015, 14016, 21035
        };


        int rare = RandomUtility.inclusiveRandom(loots.length - 1);


        player.getInventory().add(loots[rare], 1);

        String itemName = new Item(loots[rare]).getDefinition().getName();

        String discordMessage = "[SUPREME BOX] " + player.getUsername() + " has just received " + itemName + " from a Supreme Mystery Box! " + GameSettings.PEEPOLOVE;
        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
        PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemName + " from a Supreme Mystery Box!");

        player.getCollectionLog().handleDrop(CustomCollection.MysteryBox.getId(), loots[rare], 1);


    }


}
