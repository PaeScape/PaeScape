package com.arlania.world.content.interfaces;

import com.arlania.world.content.Sounds;
import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class GeneralSettings {

    public static void showInterface(Player player) {

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "General Settings"); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, ""); //2nd column header


        if (player.getSoundsActive())
            player.getPacketSender().sendString(8846, "@gre@Sounds");
        else
            player.getPacketSender().sendString(8846, "@red@Sounds");

        if (player.getMusicActive())
            player.getPacketSender().sendString(8823, "@gre@Music");
        else
            player.getPacketSender().sendString(8823, "@red@Music");

        if (player.worldFilterSeasonal)
            player.getPacketSender().sendString(8824, "@gre@");
        else
            player.getPacketSender().sendString(8824, "@red@");

        if (player.worldFilterMinigames)
            player.getPacketSender().sendString(8827, "@gre@");
        else
            player.getPacketSender().sendString(8827, "@red@");

        if (player.worldFilterStatus)
            player.getPacketSender().sendString(8837, "@gre@");
        else
            player.getPacketSender().sendString(8837, "@red@");

        if (player.worldFilterEvents)
            player.getPacketSender().sendString(8840, "@gre@");
        else
            player.getPacketSender().sendString(8840, "@red@");

        if (player.worldFilterYell)
            player.getPacketSender().sendString(8843, "@gre@");
        else
            player.getPacketSender().sendString(8843, "@red@");

        if (player.worldFilterPVP)
            player.getPacketSender().sendString(8859, "@gre@");
        else
            player.getPacketSender().sendString(8859, "@red@");

        if (player.worldFilterReminders)
            player.getPacketSender().sendString(8862, "@gre@");
        else
            player.getPacketSender().sendString(8862, "@red@");

        player.getPacketSender().sendString(8865, "");
        player.getPacketSender().sendString(15303, "");
        player.getPacketSender().sendString(15306, "");
        player.getPacketSender().sendString(15309, "");


        int string = 8760;
        int cost = 8720;


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "General Settings"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Sound effects: " + player.getSoundsActive()); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Music is still being worked on."); //
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
                player.setSoundsActive(!player.getSoundsActive());
                if (player.soundsActive()) {
                    Sounds.sendSound(player, 319);
                }
                showInterface(player);
                break;

            case 8823:
                player.setMusicActive(!player.getMusicActive());
                if (player.getMusicActive()) {
                    player.playMusic(player);
                }
                showInterface(player);
                break;
            case 8824:

                break;
            case 8827:

                break;
            case 8837:

                break;
            case 8840:

                break;
            case 8843:

                break;
            case 8859:

                break;
            case 8862:

                break;
            case 8865:

                break;
            case 15303:

                break;
            case 15306:

                break;
            case 15309:

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
