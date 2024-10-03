package com.arlania.world.content.interfaces;

import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class EventInterface {

    public static void showEventInterface(Player player) {

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Events Interface"); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, "Events"); //2nd column header

        player.getPacketSender().sendString(8846, "Accuracy"); //1st Button
        player.getPacketSender().sendString(8823, "Accelerate"); //2nd Button
        player.getPacketSender().sendString(8824, "Drop Rate"); //3rd Button
        player.getPacketSender().sendString(8827, "2x Boss PP"); //4th Button
        player.getPacketSender().sendString(8837, "2x Skiller"); //5th Button
        player.getPacketSender().sendString(8840, "2x Slayer"); //6th Button
        player.getPacketSender().sendString(8843, "Loaded"); //7th Button
        player.getPacketSender().sendString(8859, "2x Loot"); //8th Button
        player.getPacketSender().sendString(8862, "2x XP"); //9th Button
        player.getPacketSender().sendString(8865, "Event Box"); //10th Button
        player.getPacketSender().sendString(15303, "Boss Kills"); //11th Button
        player.getPacketSender().sendString(15306, "Max Hit"); //12th Button
        player.getPacketSender().sendString(15309, ""); //13th Button

        if (player.accuracyEvent)
            player.getPacketSender().sendString(8846, "@gre@Accuracy");
        else
            player.getPacketSender().sendString(8846, "@red@Accuracy");

        if (player.accelerateEvent)
            player.getPacketSender().sendString(8823, "@gre@Accelerate");
        else
            player.getPacketSender().sendString(8823, "@red@Accelerate");

        if (player.droprateEvent)
            player.getPacketSender().sendString(8824, "@gre@Drop Rate");
        else
            player.getPacketSender().sendString(8824, "@red@Drop Rate");

        if (player.doubleBossPointEvent)
            player.getPacketSender().sendString(8827, "@gre@2x Boss PP");
        else
            player.getPacketSender().sendString(8827, "@red@2x Boss PP");

        if (player.doubleSkillerPointsEvent)
            player.getPacketSender().sendString(8837, "@gre@2x Skiller PP");
        else
            player.getPacketSender().sendString(8837, "@red@2x Skiller PP");

        if (player.doubleSlayerPointsEvent)
            player.getPacketSender().sendString(8840, "@gre@2x Slayer PP");
        else
            player.getPacketSender().sendString(8840, "@red@2x Slayer PP");

        if (player.loadedEvent)
            player.getPacketSender().sendString(8843, "@gre@Loaded");
        else
            player.getPacketSender().sendString(8843, "@red@Loaded");

        if (player.doubleLoot)
            player.getPacketSender().sendString(8859, "@gre@2x Loot");
        else
            player.getPacketSender().sendString(8859, "@red@2x Loot");

        if (player.doubleExpEvent)
            player.getPacketSender().sendString(8862, "@gre@2x XP");
        else
            player.getPacketSender().sendString(8862, "@red@2x XP");

        if (player.eventBoxEvent)
            player.getPacketSender().sendString(8865, "@gre@Event Box");
        else
            player.getPacketSender().sendString(8865, "@red@Event Box");

        if (player.bossKillsEvent)
            player.getPacketSender().sendString(15303, "@gre@Boss Kills");
        else
            player.getPacketSender().sendString(15303, "@red@Boss Kills");

        if (player.maxHitEvent)
            player.getPacketSender().sendString(15306, "@gre@Max Hit");
        else
            player.getPacketSender().sendString(15306, "@red@Max Hit");


        /*if (player.personalEvent)
            player.getPacketSender().sendString(15309, "@gre@Active");
        else
            player.getPacketSender().sendString(15309, "@red@Inactive");*/


        int string = 8760;
        int cost = 8720;


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@bla@Accuracy"); //
        player.getPacketSender().sendString(string++, "@bla@- Remaining time: " + player.accuracyEventTimer); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@bla@Accelerate"); //
        player.getPacketSender().sendString(string++, "@bla@- Remaining time: " + player.accelerateEventTimer); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@bla@Drop Rate"); //
        player.getPacketSender().sendString(string++, "@bla@- Remaining time: " + player.droprateEventTimer); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@bla@2x Boss PP"); //
        player.getPacketSender().sendString(string++, "@bla@- Remaining time: " + player.doubleBossPointEventTimer); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@bla@2x Skiller PP"); //
        player.getPacketSender().sendString(string++, "@bla@- Remaining time: " + player.doubleSkillerPointsEventTimer); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@bla@2x Slayer"); //
        player.getPacketSender().sendString(string++, "@bla@- Remaining time: " + player.doubleSlayerPointsEventTimer); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@bla@Loaded"); //
        player.getPacketSender().sendString(string++, "@bla@- Remaining time: " + player.loadedEventTimer); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@bla@2x Loot"); //
        player.getPacketSender().sendString(string++, "@bla@- Remaining time: " + player.doubleLootTimer); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@bla@2x XP"); //
        player.getPacketSender().sendString(string++, "@bla@- Remaining time: " + player.doubleExpEventTimer); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@bla@Event Box"); //
        player.getPacketSender().sendString(string++, "@bla@- Remaining time: " + player.universalDropEventTimer); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@bla@Boss Kills"); //
        player.getPacketSender().sendString(string++, "@bla@- Remaining time: " + player.bossKillsEventTimer); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@bla@Max Hit"); //
        player.getPacketSender().sendString(string++, "@bla@- Remaining time: " + player.maxHitEventTimer); //


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

        if (player.inRaid(player)) {
            player.getPacketSender().sendMessage("@red@You can't start an event in a raid!");
            return;
        }


        switch (button) {

            case 8846:
                if (!player.accuracyEvent && !player.personalEvent && player.accuracyEventTimer > 0) {
                    player.personalEventType = "Accuracy";
                    player.accuracyEvent = true;
                    player.accelerateEvent = false;
                    player.droprateEvent = false;
                    player.doubleBossPointEvent = false;
                    player.doubleSkillerPointsEvent = false;
                    player.doubleSlayerPointsEvent = false;
                    player.loadedEvent = false;
                    player.doubleLoot = false;
                    player.doubleExpEvent = false;
                    player.eventBoxEvent = false;
                    player.bossKillsEvent = false;
                    player.maxHitEvent = false;
                } else if (player.accuracyEventTimer == 0) {
                    player.sendMessage("@red@You do not have enough event time to activate this event.");
                }
                player.getPacketSender().sendInterfaceRemoval();
                break;

            case 8823:
                if (!player.accelerateEvent && !player.personalEvent && player.accelerateEventTimer > 0) {
                    player.personalEventType = "Accelerate";
                    player.accuracyEvent = false;
                    player.accelerateEvent = true;
                    player.droprateEvent = false;
                    player.doubleBossPointEvent = false;
                    player.doubleSkillerPointsEvent = false;
                    player.doubleSlayerPointsEvent = false;
                    player.loadedEvent = false;
                    player.doubleLoot = false;
                    player.doubleExpEvent = false;
                    player.eventBoxEvent = false;
                    player.bossKillsEvent = false;
                    player.maxHitEvent = false;
                } else if (player.accelerateEventTimer == 0) {
                    player.sendMessage("@red@You do not have enough event time to activate this event.");
                }
                player.getPacketSender().sendInterfaceRemoval();
                break;

            case 8824:
                if (!player.droprateEvent && !player.personalEvent && player.droprateEventTimer > 0) {
                    player.personalEventType = "Drop Rate";
                    player.accuracyEvent = false;
                    player.accelerateEvent = false;
                    player.droprateEvent = true;
                    player.doubleBossPointEvent = false;
                    player.doubleSkillerPointsEvent = false;
                    player.doubleSlayerPointsEvent = false;
                    player.loadedEvent = false;
                    player.doubleLoot = false;
                    player.doubleExpEvent = false;
                    player.eventBoxEvent = false;
                    player.bossKillsEvent = false;
                    player.maxHitEvent = false;
                } else if (player.droprateEventTimer == 0) {
                    player.sendMessage("@red@You do not have enough event time to activate this event.");
                }
                player.getPacketSender().sendInterfaceRemoval();
                break;

            case 8827:
                if (!player.doubleBossPointEvent && !player.personalEvent && player.doubleBossPointEventTimer > 0) {
                    player.personalEventType = "2x Boss PP";
                    player.accuracyEvent = false;
                    player.accelerateEvent = false;
                    player.droprateEvent = false;
                    player.doubleBossPointEvent = true;
                    player.doubleSkillerPointsEvent = false;
                    player.doubleSlayerPointsEvent = false;
                    player.loadedEvent = false;
                    player.doubleLoot = false;
                    player.doubleExpEvent = false;
                    player.eventBoxEvent = false;
                    player.bossKillsEvent = false;
                    player.maxHitEvent = false;
                } else if (player.doubleBossPointEventTimer == 0) {
                    player.sendMessage("@red@You do not have enough event time to activate this event.");
                }
                player.getPacketSender().sendInterfaceRemoval();
                break;

            case 8837:
                if (!player.doubleSkillerPointsEvent && !player.personalEvent && player.doubleSkillerPointsEventTimer > 0) {
                    player.personalEventType = "2x Skiller PP";
                    player.accuracyEvent = false;
                    player.accelerateEvent = false;
                    player.droprateEvent = false;
                    player.doubleBossPointEvent = false;
                    player.doubleSkillerPointsEvent = true;
                    player.doubleSlayerPointsEvent = false;
                    player.loadedEvent = false;
                    player.doubleLoot = false;
                    player.doubleExpEvent = false;
                    player.eventBoxEvent = false;
                    player.bossKillsEvent = false;
                    player.maxHitEvent = false;
                } else if (player.doubleSkillerPointsEventTimer == 0) {
                    player.sendMessage("@red@You do not have enough event time to activate this event.");
                }
                player.getPacketSender().sendInterfaceRemoval();
                break;

            case 8840:
                if (!player.doubleSlayerPointsEvent && !player.personalEvent && player.doubleSlayerPointsEventTimer > 0) {
                    player.personalEventType = "2x Slayer PP";
                    player.accuracyEvent = false;
                    player.accelerateEvent = false;
                    player.droprateEvent = false;
                    player.doubleBossPointEvent = false;
                    player.doubleSkillerPointsEvent = false;
                    player.doubleSlayerPointsEvent = true;
                    player.loadedEvent = false;
                    player.doubleLoot = false;
                    player.doubleExpEvent = false;
                    player.eventBoxEvent = false;
                    player.bossKillsEvent = false;
                    player.maxHitEvent = false;
                } else if (player.doubleSlayerPointsEventTimer == 0) {
                    player.sendMessage("@red@You do not have enough event time to activate this event.");
                }
                player.getPacketSender().sendInterfaceRemoval();
                break;

            case 8843:
                if (!player.loadedEvent && !player.personalEvent && player.loadedEventTimer > 0) {
                    player.personalEventType = "Loaded";
                    player.accuracyEvent = false;
                    player.accelerateEvent = false;
                    player.droprateEvent = false;
                    player.doubleBossPointEvent = false;
                    player.doubleSkillerPointsEvent = false;
                    player.doubleSlayerPointsEvent = false;
                    player.loadedEvent = true;
                    player.doubleLoot = false;
                    player.doubleExpEvent = false;
                    player.eventBoxEvent = false;
                    player.bossKillsEvent = false;
                    player.maxHitEvent = false;
                } else if (player.loadedEventTimer == 0) {
                    player.sendMessage("@red@You do not have enough event time to activate this event.");
                }
                player.getPacketSender().sendInterfaceRemoval();
                break;

            case 8859:
                if (!player.doubleLoot && !player.personalEvent && player.doubleLootTimer > 0) {
                    player.personalEventType = "2x Loot";
                    player.accuracyEvent = false;
                    player.accelerateEvent = false;
                    player.droprateEvent = false;
                    player.doubleBossPointEvent = false;
                    player.doubleSkillerPointsEvent = false;
                    player.doubleSlayerPointsEvent = false;
                    player.loadedEvent = false;
                    player.doubleLoot = true;
                    player.doubleExpEvent = false;
                    player.eventBoxEvent = false;
                    player.bossKillsEvent = false;
                    player.maxHitEvent = false;
                } else if (player.doubleLootTimer == 0) {
                    player.sendMessage("@red@You do not have enough event time to activate this event.");
                }
                player.getPacketSender().sendInterfaceRemoval();
                break;

            case 8862:
                if (!player.doubleExpEvent && !player.personalEvent && player.doubleExpEventTimer > 0) {
                    player.personalEventType = "2x Exp";
                    player.accuracyEvent = false;
                    player.accelerateEvent = false;
                    player.droprateEvent = false;
                    player.doubleBossPointEvent = false;
                    player.doubleSkillerPointsEvent = false;
                    player.doubleSlayerPointsEvent = false;
                    player.loadedEvent = false;
                    player.doubleLoot = false;
                    player.doubleExpEvent = true;
                    player.eventBoxEvent = false;
                    player.bossKillsEvent = false;
                    player.maxHitEvent = false;
                } else if (player.doubleExpEventTimer == 0) {
                    player.sendMessage("@red@You do not have enough event time to activate this event.");
                }
                player.getPacketSender().sendInterfaceRemoval();
                break;

            case 8865:
                if (!player.eventBoxEvent && !player.personalEvent && player.universalDropEventTimer > 0) {
                    player.personalEventType = "Event Box";
                    player.accuracyEvent = false;
                    player.accelerateEvent = false;
                    player.droprateEvent = false;
                    player.doubleBossPointEvent = false;
                    player.doubleSkillerPointsEvent = false;
                    player.doubleSlayerPointsEvent = false;
                    player.loadedEvent = false;
                    player.doubleLoot = false;
                    player.doubleExpEvent = false;
                    player.eventBoxEvent = true;
                    player.bossKillsEvent = false;
                    player.maxHitEvent = false;
                } else if (player.universalDropEventTimer == 0) {
                    player.sendMessage("@red@You do not have enough event time to activate this event.");
                }
                player.getPacketSender().sendInterfaceRemoval();
                break;

            case 15303:
                if (!player.bossKillsEvent && !player.personalEvent && player.bossKillsEventTimer > 0) {
                    player.personalEventType = "Boss Kills";
                    player.accuracyEvent = false;
                    player.accelerateEvent = false;
                    player.droprateEvent = false;
                    player.doubleBossPointEvent = false;
                    player.doubleSkillerPointsEvent = false;
                    player.doubleSlayerPointsEvent = false;
                    player.loadedEvent = false;
                    player.doubleLoot = false;
                    player.doubleExpEvent = false;
                    player.eventBoxEvent = false;
                    player.bossKillsEvent = true;
                    player.maxHitEvent = false;
                } else if (player.bossKillsEventTimer == 0) {
                    player.sendMessage("@red@You do not have enough event time to activate this event.");
                }
                player.getPacketSender().sendInterfaceRemoval();
                break;

            case 15306:
                if (!player.maxHitEvent && !player.personalEvent && player.maxHitEventTimer > 0) {
                    if (!GlobalEventHandler.effectActive(GlobalEvent.Effect.BERSERKER)) {
                        player.personalEventType = "Max Hit";
                        player.accuracyEvent = false;
                        player.accelerateEvent = false;
                        player.droprateEvent = false;
                        player.doubleBossPointEvent = false;
                        player.doubleSkillerPointsEvent = false;
                        player.doubleSlayerPointsEvent = false;
                        player.loadedEvent = false;
                        player.doubleLoot = false;
                        player.doubleExpEvent = false;
                        player.eventBoxEvent = false;
                        player.bossKillsEvent = false;
                        player.maxHitEvent = true;
                    } else
                        player.getPacketSender().sendMessage("@red@You can't activate Max Hit event during a Berserker event!");
                } else if (player.maxHitEventTimer == 0) {
                    player.sendMessage("@red@You do not have enough event time to activate this event.");
                }
                player.getPacketSender().sendInterfaceRemoval();
                break;

            case 15309:

                EventInterface.showEventInterface(player);
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
