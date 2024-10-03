package com.arlania.world.content.interfaces;

import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class WorldMessageFilter {

    public static void showInterface(Player player) {

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "World Filter"); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, ""); //2nd column header


        if (player.worldFilterDrops)
            player.getPacketSender().sendString(8846, "@gre@Drops");
        else
            player.getPacketSender().sendString(8846, "@red@Drops");

        if (player.worldFilterLevels)
            player.getPacketSender().sendString(8823, "@gre@Levels");
        else
            player.getPacketSender().sendString(8823, "@red@Levels");

        if (player.worldFilterSeasonal)
            player.getPacketSender().sendString(8824, "@gre@Seasonal");
        else
            player.getPacketSender().sendString(8824, "@red@Seasonal");

        if (player.worldFilterMinigames)
            player.getPacketSender().sendString(8827, "@gre@Minigames");
        else
            player.getPacketSender().sendString(8827, "@red@Minigames");

        if (player.worldFilterStatus)
            player.getPacketSender().sendString(8837, "@gre@Status");
        else
            player.getPacketSender().sendString(8837, "@red@Status");

        if (player.worldFilterEvents)
            player.getPacketSender().sendString(8840, "@gre@Events");
        else
            player.getPacketSender().sendString(8840, "@red@Events");

        if (player.worldFilterYell)
            player.getPacketSender().sendString(8843, "@gre@Yell");
        else
            player.getPacketSender().sendString(8843, "@red@Yell");

        if (player.worldFilterPVP)
            player.getPacketSender().sendString(8859, "@gre@PVP");
        else
            player.getPacketSender().sendString(8859, "@red@PVP");

        if (player.worldFilterReminders)
            player.getPacketSender().sendString(8862, "@gre@Reminders");
        else
            player.getPacketSender().sendString(8862, "@red@Reminders");

        player.getPacketSender().sendString(8865, "");
        player.getPacketSender().sendString(15303, "");
        player.getPacketSender().sendString(15306, "");
        player.getPacketSender().sendString(15309, "");


        int string = 8760;
        int cost = 8720;


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "World messages can be filtered."); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@gre@Green@bla@ means you will see the messages."); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@red@Red@bla@ means those messages will be filtered."); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //

    }


    public static void handleButton(Player player, int button) {

        switch (button) {

            case 8846:
                player.worldFilterDrops = !player.worldFilterDrops;
                showInterface(player);
                break;

            case 8823:
                player.worldFilterLevels = !player.worldFilterLevels;
                showInterface(player);
                break;
            case 8824:
                player.worldFilterSeasonal = !player.worldFilterSeasonal;
                showInterface(player);
                break;
            case 8827:
                player.worldFilterMinigames = !player.worldFilterMinigames;
                showInterface(player);
                break;
            case 8837:
                player.worldFilterStatus = !player.worldFilterStatus;
                showInterface(player);
                break;
            case 8840:
                player.worldFilterEvents = !player.worldFilterEvents;
                showInterface(player);
                break;
            case 8843:
                player.worldFilterYell = !player.worldFilterYell;
                showInterface(player);
                break;
            case 8859:
                player.worldFilterPVP = !player.worldFilterPVP;
                showInterface(player);
                break;
            case 8862:
                player.worldFilterReminders = !player.worldFilterReminders;
                showInterface(player);
                break;
            case 8865:
                //player.worldFilterDrops = !player.worldFilterDrops;
                showInterface(player);
                break;
            case 15303:
                //player.worldFilterDrops = !player.worldFilterDrops;
                showInterface(player);
                break;
            case 15306:
                //player.worldFilterDrops = !player.worldFilterDrops;
                showInterface(player);
                break;
            case 15309:
                //player.worldFilterDrops = !player.worldFilterDrops;
                showInterface(player);
                break;


        }

    }


    //Quest reward interface
	/*player.getPacketSender().sendInterface(4909); //interface
	player.getPacketSender().sendString(4913, "Hello"); //1st line
	player.getPacketSender().sendString(4911, "Brother,"); //2nd line
	player.getPacketSender().sendString(4914, "how"); //3rd line
	player.getPacketSender().sendString(4915, "are"); //4th line
	player.getPacketSender().sendString(4918, "you"); //4th line
	player.getPacketSender().sendString(4916, "doing?"); //Bottom Middle*/
    //player.getPacketSender().sendItemsOnInterface(interfaceId, items)

    //Reward % interface
    /*player.getPacketSender().sendWalkableInterface(6673, true); //interface*/

    //Blast furnace interface
	/*player.getPacketSender().sendInterface(14458); //interface
	player.getPacketSender().sendString(14553, "Adamantite: 69");*/
    //player.getPacketSender().sendItemsOnInterface(interfaceId, items)

    //Default
    //player.getPacketSender().sendString(interfaceId, String);
    //player.getPacketSender().sendItemsOnInterface(interfaceId, items);

    //KillsTracker.open(player);
    //DropLog.open(player);

}
