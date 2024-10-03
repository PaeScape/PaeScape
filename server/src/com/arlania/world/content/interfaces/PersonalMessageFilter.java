package com.arlania.world.content.interfaces;

import com.arlania.world.entity.impl.player.Player;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Interface for personal message filters (Paepoints, boss kills, etc)
 */

public class PersonalMessageFilter {

    private static final List<Integer> buttons = Arrays.asList(8846, 8823, 8824, 8827, 8837, 8840, 8843, 8859, 8862, 8865, 15303, 15306, 15309);

    public static void showInterface(Player player) {

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Personal Filter"); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, ""); //2nd column header

        Iterator<Integer> iterator = buttons.listIterator();
        sendButtonString(player, iterator, player.personalFilterPaepoints, "Paepoints");
        sendButtonString(player, iterator, player.personalFilterWildypoints, "Wildy Pts");
        sendButtonString(player, iterator, player.personalFilterBossKills, "Boss Kills");
        sendButtonString(player, iterator, player.personalFilterImp, "Charm Imp");
        sendButtonString(player, iterator, player.personalFilterBonecrusher, "Bonecrush");
        sendButtonString(player, iterator, player.personalFilterHerbicide, "Herbicide");
        sendButtonString(player, iterator, player.personalFilterMasteryTriggers, "Reflect");
        sendButtonString(player, iterator, player.personalFilterPouch, "Pouch");
        sendButtonString(player, iterator, player.personalFilterKeyLoot, "Key Loot");
        sendButtonString(player, iterator, player.personalFilterDirtBag, "Dirt Bag");
        sendButtonString(player, iterator, player.personalFilterCurses, "Stat Drain");
        sendButtonString(player, iterator, player.personalFilterGathering, "Gathering");
        sendButtonString(player, iterator, player.personalFilterAdze, "Inf. Tools");

        // Fill all other buttons with empty string
        while (iterator.hasNext()) {
            player.getPacketSender().sendString(iterator.next(), "");
        }

        int string = 8760;
        int cost = 8720;

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Personal messages can be filtered."); //
        addBlankLine(player, cost++, string++);
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@gre@Green@bla@ means you will see the messages."); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@red@Red@bla@ means those messages will be filtered."); //

        // Fill with blank lines until the end
        while (cost < 8748) {
            addBlankLine(player, cost++, string++);
        }
    }

    private static void addBlankLine(Player player, int id1, int id2) {
        player.getPacketSender().sendString(id1, "");
        player.getPacketSender().sendString(id2, "");
    }

    private static void sendButtonString(Player player, Iterator<Integer> iterator, boolean isEnabled, String text) {
        player.getPacketSender().sendString(iterator.next(), (isEnabled ? "@gre@" : "@red@") + text);
    }

    public static void handleButton(Player player, int button) {
        switch (buttons.indexOf(button)) {
            case 0:
                player.personalFilterPaepoints = !player.personalFilterPaepoints;
                break;
            case 1:
                player.personalFilterWildypoints = !player.personalFilterWildypoints;
                break;
            case 2:
                player.personalFilterBossKills = !player.personalFilterBossKills;
                break;
            case 3:
                player.personalFilterImp = !player.personalFilterImp;
                break;
            case 4:
                player.personalFilterBonecrusher = !player.personalFilterBonecrusher;
                break;
            case 5:
                player.personalFilterHerbicide = !player.personalFilterHerbicide;
                break;
            case 6:
                player.personalFilterMasteryTriggers = !player.personalFilterMasteryTriggers;
                break;
            case 7:
                player.personalFilterPouch = !player.personalFilterPouch;
                break;
            case 8:
                player.personalFilterKeyLoot = !player.personalFilterKeyLoot;
                break;
            case 9:
                player.personalFilterDirtBag = !player.personalFilterDirtBag;
                break;
            case 10:
                player.personalFilterCurses = !player.personalFilterCurses;
                break;
            case 11:
                player.personalFilterGathering = !player.personalFilterGathering;
                break;
            case 12:
                player.personalFilterAdze = !player.personalFilterAdze;
                break;

        }
        showInterface(player);
    }
}
