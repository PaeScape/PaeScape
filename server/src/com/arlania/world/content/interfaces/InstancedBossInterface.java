package com.arlania.world.content.interfaces;

import com.arlania.world.content.minigames.impl.InstancedBosses;
import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class InstancedBossInterface {

    public static void showInterface(Player player) {

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Instanced Bosses"); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, ""); //2nd column header

        player.getPacketSender().sendString(8846, "1"); //1st Button
        player.getPacketSender().sendString(8823, "2"); //2nd Button
        player.getPacketSender().sendString(8824, "3"); //3rd Button
        player.getPacketSender().sendString(8827, "4"); //4th Button
        player.getPacketSender().sendString(8837, "5"); //5th Button
        player.getPacketSender().sendString(8840, "6"); //6th Button
        player.getPacketSender().sendString(8843, "7"); //7th Button
        player.getPacketSender().sendString(8859, "8"); //8th Button
        player.getPacketSender().sendString(8862, "9"); //9th Button
        player.getPacketSender().sendString(8865, "10"); //10th Button
        player.getPacketSender().sendString(15303, ""); //11th Button
        player.getPacketSender().sendString(15306, ""); //12th Button
        player.getPacketSender().sendString(15309, "Start"); //13th Button

        if (player.inSaradomin)
            player.getPacketSender().sendString(8846, "@gre@Saradomin");
        else
            player.getPacketSender().sendString(8846, "@red@Saradomin");

        if (player.inZamorak)
            player.getPacketSender().sendString(8823, "@gre@Zamorak");
        else
            player.getPacketSender().sendString(8823, "@red@Zamorak");

        if (player.inArmadyl)
            player.getPacketSender().sendString(8824, "@gre@Armadyl");
        else
            player.getPacketSender().sendString(8824, "@red@Armadyl");

        if (player.inBandos)
            player.getPacketSender().sendString(8827, "@gre@Bandos");
        else
            player.getPacketSender().sendString(8827, "@red@Bandos");

        if (player.inCerberus)
            player.getPacketSender().sendString(8840, "@gre@Cerberus");
        else
            player.getPacketSender().sendString(8840, "@red@Cerberus");

        if (player.inSire)
            player.getPacketSender().sendString(8843, "@gre@Sire");
        else
            player.getPacketSender().sendString(8843, "@red@Sire");

        if (player.inThermo)
            player.getPacketSender().sendString(8859, "@gre@Thermo");
        else
            player.getPacketSender().sendString(8859, "@red@Thermo");

        if (player.inCorporeal)
            player.getPacketSender().sendString(8862, "@gre@Corporeal");
        else
            player.getPacketSender().sendString(8862, "@red@Corporeal");

        if (player.inKBD)
            player.getPacketSender().sendString(8865, "@gre@KBD");
        else
            player.getPacketSender().sendString(8865, "@red@KBD");

        player.getPacketSender().sendString(15309, "@red@Start");

        int string = 8760;
        int cost = 8720;


        player.getPacketSender().sendString(string++, "@blu@You can select any number of bosses before"); //
        player.getPacketSender().sendString(string++, "@blu@starting your instance."); //


        player.getPacketSender().sendString(string++, "@blu@To start an instance you must have an "); //
        player.getPacketSender().sendString(string++, "@blu@Enchanted Key in your inventory. "); //

        player.getPacketSender().sendString(string++, "@blu@You can purchase an Enchanted Key from"); //
        player.getPacketSender().sendString(string++, "@blu@either Specialty Store just north of here. "); //

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
        player.getPacketSender().sendString(string++, ""); //v
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //v

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //


    }

    public static void handleButton(Player player, int button) {

        switch (button) {

            case 8846:
                player.inSaradomin = !player.inSaradomin;
                showInterface(player);
                break;

            case 8823:
                player.inZamorak = !player.inZamorak;
                showInterface(player);
                break;

            case 8824:
                player.inArmadyl = !player.inArmadyl;
                showInterface(player);
                break;

            case 8827:
                player.inBandos = !player.inBandos;
                showInterface(player);
                break;

            case 8837:
                //player.inZulrah = !player.inZulrah;
                //showInterface(player);
                break;

            case 8840:
                player.inCerberus = !player.inCerberus;
                showInterface(player);
                break;

            case 8843:
                player.inSire = !player.inSire;
                showInterface(player);
                break;

            case 8859:
                player.inThermo = !player.inThermo;
                showInterface(player);
                break;

            case 8862:
                player.inCorporeal = !player.inCorporeal;
                showInterface(player);
                break;

            case 8865:
                player.inKBD = !player.inKBD;
                showInterface(player);
                break;

            case 15303:
                break;

            case 15306:
                break;

            case 15309:
                if (player.inSaradomin || player.inZamorak || player.inArmadyl || player.inBandos //|| player.inZulrah
                        || player.inCerberus || player.inSire || player.inThermo || player.inCorporeal || player.inKBD) {
                    if (player.getInventory().contains(13591)) {
                        if (!player.instancedBosses) {
                            player.getInventory().delete(13591, 1);
                            player.getPacketSender().sendInterfaceRemoval();
                            InstancedBosses.enter(player);
                        } else
                            player.getPacketSender().sendMessage("@red@You have already confirmed this action.");
                    } else {
                        player.getPacketSender().sendMessage("@red@You need to buy an Enchanted Key from the Specialty Store!");
                        player.getPacketSender().sendInterfaceRemoval();
                    }
                } else {
                    player.sendMessage("@red@Select a boss.");
                }
                break;

        }

    }

}
