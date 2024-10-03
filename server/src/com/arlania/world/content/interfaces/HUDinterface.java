package com.arlania.world.content.interfaces;

import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class HUDinterface {

    public static void showHUDInterface(Player player) {

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Perks Interface"); //title header
        player.getPacketSender().sendString(8718, "Cost"); //1st column header
        player.getPacketSender().sendString(8719, "Description"); //2nd column header

        /*player.getPacketSender().sendString(8846, "Overload"); //1st Button
        player.getPacketSender().sendString(8823, "Poison"); //2nd Button
        player.getPacketSender().sendString(8824, "Anti-fire"); //3rd Button
        player.getPacketSender().sendString(8827, "Bonus Time"); //4th Button
        player.getPacketSender().sendString(8837, "Slayer"); //5th Button
        player.getPacketSender().sendString(8840, "Skiller"); //6th Button
        player.getPacketSender().sendString(8843, "Cballs"); //7th Button
        player.getPacketSender().sendString(8859, "BP Exp"); //8th Button
        player.getPacketSender().sendString(8862, "BP Bosses"); //9th Button
        player.getPacketSender().sendString(8865, "EP Exp"); //10th Button
        player.getPacketSender().sendString(15303, "EP Bosses"); //11th Button
        player.getPacketSender().sendString(15306, "PE Timer"); //12th Button
        player.getPacketSender().sendString(15309, "Color"); //13th Button*/

        if (player.hudOvl)
            player.getPacketSender().sendString(8846, "@gre@Overload");
        else
            player.getPacketSender().sendString(8846, "@red@Overload");

        if (player.hudPoison)
            player.getPacketSender().sendString(8823, "@gre@Poison");
        else
            player.getPacketSender().sendString(8823, "@red@Poison");

        if (player.hudFire)
            player.getPacketSender().sendString(8824, "@gre@Anti-fire");
        else
            player.getPacketSender().sendString(8824, "@red@Anti-fire");

        if (player.hudBonus)
            player.getPacketSender().sendString(8827, "@gre@Bonus Time");
        else
            player.getPacketSender().sendString(8827, "@red@Bonus Time");

        if (player.hudSlayer)
            player.getPacketSender().sendString(8837, "@gre@Slayer");
        else
            player.getPacketSender().sendString(8837, "@red@Slayer");

        if (player.hudSkiller)
            player.getPacketSender().sendString(8840, "@gre@Skiller");
        else
            player.getPacketSender().sendString(8840, "@red@Skiller");

        if (player.hudCballs)
            player.getPacketSender().sendString(8843, "@gre@Cballs");
        else
            player.getPacketSender().sendString(8843, "@red@Cballs");

        if (player.hudBPexp)
            player.getPacketSender().sendString(8859, "@gre@BP Exp");
        else
            player.getPacketSender().sendString(8859, "@red@BP Exp");

        if (player.hudBPkills)
            player.getPacketSender().sendString(8862, "@gre@BP Bosses");
        else
            player.getPacketSender().sendString(8862, "@red@BP Bosses");

        if (player.hudEPexp)
            player.getPacketSender().sendString(8865, "@gre@EP Exp");
        else
            player.getPacketSender().sendString(8865, "@red@EP Exp");

        if (player.hudEPkills)
            player.getPacketSender().sendString(15303, "@gre@EP Bosses");
        else
            player.getPacketSender().sendString(15303, "@red@EP Bosses");

        if (player.hudPETimer)
            player.getPacketSender().sendString(15306, "@gre@PE Timer");
        else
            player.getPacketSender().sendString(15306, "@red@PE Timer");


        player.getPacketSender().sendString(15309, "@" + player.hudColor + "@Color");


        int string = 8760;
        int cost = 8720;


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@red@Overload"); //
        player.getPacketSender().sendString(string++, "@red@- Display remaining time of Overload."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@gre@Poison"); //
        player.getPacketSender().sendString(string++, "@gre@- Display remaining time of Anti-poison."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Anti-Fire"); //
        player.getPacketSender().sendString(string++, "@blu@- Display remaining time of Anti-fire."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@gre@Bonus Time"); //
        player.getPacketSender().sendString(string++, "@gre@- Display your remaining Bonus Time."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Slayer"); //
        player.getPacketSender().sendString(string++, "@blu@- Display quantity of Slayer task left."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@red@Skiller"); //
        player.getPacketSender().sendString(string++, "@red@- Display quantity of Skiller task left."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@red@Cballs"); //
        player.getPacketSender().sendString(string++, "@red@- Display quantity of cannonballs in cannon."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@gre@BP Exp"); //
        player.getPacketSender().sendString(string++, "@gre@- Display current Battle Pass Experience."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@BP Bosses"); //
        player.getPacketSender().sendString(string++, "@blu@- Display current Battle Pass Boss kills."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@gre@EP Exp"); //
        player.getPacketSender().sendString(string++, "@gre@- Display current Event Pass Experience."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@EP Bosses"); //
        player.getPacketSender().sendString(string++, "@blu@- Display current Event Pass Boss kills."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@PE Timer"); //
        player.getPacketSender().sendString(string++, "@blu@- Display your active Personal event timer."); //


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
                if (player.hudOvl) {
                    player.hudOvl = false;
                    player.hudChoices -= 1;
                } else if (player.hudChoices < 5) {
                    player.hudOvl = true;
                    player.hudChoices += 1;
                }
                HUDinterface.showHUDInterface(player);
                break;

            case 8823:
                if (player.hudPoison) {
                    player.hudPoison = false;
                    player.hudChoices -= 1;
                } else if (player.hudChoices < 5) {
                    player.hudPoison = true;
                    player.hudChoices += 1;
                }
                HUDinterface.showHUDInterface(player);
                break;
            case 8824:
                if (player.hudFire) {
                    player.hudFire = false;
                    player.hudChoices -= 1;
                } else if (player.hudChoices < 5) {
                    player.hudFire = true;
                    player.hudChoices += 1;
                }
                HUDinterface.showHUDInterface(player);
                break;
            case 8827:
                if (player.hudBonus) {
                    player.hudBonus = false;
                    player.hudChoices -= 1;
                } else if (player.hudChoices < 5) {
                    player.hudBonus = true;
                    player.hudChoices += 1;
                }
                HUDinterface.showHUDInterface(player);
                break;
            case 8837:
                if (player.hudSlayer) {
                    player.hudSlayer = false;
                    player.hudChoices -= 1;
                } else if (player.hudChoices < 5) {
                    player.hudSlayer = true;
                    player.hudChoices += 1;
                }
                HUDinterface.showHUDInterface(player);
                break;
            case 8840:

                if (player.hudSkiller) {
                    player.hudSkiller = false;
                    player.hudChoices -= 1;
                } else if (player.hudChoices < 5) {
                    player.hudSkiller = true;
                    player.hudChoices += 1;
                }
                HUDinterface.showHUDInterface(player);
                break;
            case 8843:
                if (player.hudCballs) {
                    player.hudCballs = false;
                    player.hudChoices -= 1;
                } else if (player.hudChoices < 5) {
                    player.hudCballs = true;
                    player.hudChoices += 1;
                }
                HUDinterface.showHUDInterface(player);
                break;
            case 8859:
                if (player.hudBPexp) {
                    player.hudBPexp = false;
                    player.hudChoices -= 1;
                } else if (player.hudChoices < 5) {
                    player.hudBPexp = true;
                    player.hudChoices += 1;
                }
                HUDinterface.showHUDInterface(player);
                break;
            case 8862:
                if (player.hudBPkills) {
                    player.hudBPkills = false;
                    player.hudChoices -= 1;
                } else if (player.hudChoices < 5) {
                    player.hudBPkills = true;
                    player.hudChoices += 1;
                }
                HUDinterface.showHUDInterface(player);
                break;
            case 8865:
                if (player.hudEPexp) {
                    player.hudEPexp = false;
                    player.hudChoices -= 1;
                } else if (player.hudChoices < 5) {
                    player.hudEPexp = true;
                    player.hudChoices += 1;
                }
                HUDinterface.showHUDInterface(player);
                break;
            case 15303:
                if (player.hudEPkills) {
                    player.hudEPkills = false;
                    player.hudChoices -= 1;
                } else if (player.hudChoices < 5) {
                    player.hudEPkills = true;
                    player.hudChoices += 1;
                }
                HUDinterface.showHUDInterface(player);
                break;
            case 15306:
                if (player.hudPETimer) {
                    player.hudPETimer = false;
                    player.hudChoices -= 1;
                } else if (player.hudChoices < 5) {
                    player.hudPETimer = true;
                    player.hudChoices += 1;
                }
                HUDinterface.showHUDInterface(player);
                break;
            case 15309:

                //player.sendMessage(player.hudColor);

                if (player.hudColor == "cya") {
                    player.hudColor = "yel";
                    HUDinterface.showHUDInterface(player);
                    break;
                } else if (player.hudColor == "yel") {
                    player.hudColor = "or2";
                    HUDinterface.showHUDInterface(player);
                    break;
                } else if (player.hudColor == "or2") {
                    player.hudColor = "red";
                    HUDinterface.showHUDInterface(player);
                    break;
                } else if (player.hudColor == "red") {
                    player.hudColor = "gr2";
                    HUDinterface.showHUDInterface(player);
                    break;
                } else if (player.hudColor == "gr2") {
                    player.hudColor = "bla";
                    HUDinterface.showHUDInterface(player);
                    break;
                } else if (player.hudColor == "bla") {
                    player.hudColor = "cya";
                    HUDinterface.showHUDInterface(player);
                    break;
                } else {
                    player.hudColor = "cya";
                    HUDinterface.showHUDInterface(player);
                    break;
                }


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
