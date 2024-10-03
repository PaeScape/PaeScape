package com.arlania.world.content;

import com.arlania.model.Item;
import com.arlania.world.entity.impl.player.Player;

public class RunePouch {

    public static void loadPouch(Player player, final int itemId, boolean divinePouch) {

        if(divinePouch) {
            if (itemId != player.getRunePouchTypeOne() &&
                    itemId != player.getRunePouchTypeTwo() &&
                    itemId != player.getRunePouchTypeThree() &&
                    itemId != player.getRunePouchTypeFour() &&
                    player.getRunePouchQtyOne() > 0 &&
                    player.getRunePouchQtyTwo() > 0 &&
                    player.getRunePouchQtyThree() > 0 &&
                    player.getRunePouchQtyFour() > 0) {
                player.getPacketSender().sendMessage("You can't load any additional runes into your divine rune pouch.");
                return;
            }
        }
        else if(!divinePouch) {
            if (itemId != player.getRunePouchTypeOne() &&
                    itemId != player.getRunePouchTypeTwo() &&
                    itemId != player.getRunePouchTypeThree() &&
                    player.getRunePouchQtyOne() > 0 &&
                    player.getRunePouchQtyTwo() > 0 &&
                    player.getRunePouchQtyThree() > 0 ) {
                player.getPacketSender().sendMessage("You can't load any additional runes into your rune pouch.");
                return;
            }
        }

        if (player.getRunePouchQtyOne() < 1 || (itemId == player.getRunePouchTypeOne())) {
            for (int i = 0; i < RUNES.length; i++) {
                if (itemId == RUNES[i]) {
                    Item rune = new Item(itemId);
                    player.setRunePouchTypeOne(itemId);
                    player.setRunePouchQtyOne(player.getInventory().getAmount(itemId) + player.getRunePouchQtyOne());
                    player.getPacketSender().sendMessage("@blu@You add " + player.getInventory().getAmount(itemId) + " " + rune.getDefinition().getName() + "s to your rune pouch.");
                    player.getInventory().delete(itemId, player.getInventory().getAmount(itemId));
                }
            }
        } else if (player.getRunePouchQtyTwo() < 1 || (itemId == player.getRunePouchTypeTwo())) {
            for (int i = 0; i < RUNES.length; i++) {
                if (itemId == RUNES[i]) {
                    Item rune = new Item(itemId);
                    player.setRunePouchTypeTwo(itemId);
                    player.setRunePouchQtyTwo(player.getInventory().getAmount(itemId) + player.getRunePouchQtyTwo());
                    player.getPacketSender().sendMessage("@blu@You add " + player.getInventory().getAmount(itemId) + " " + rune.getDefinition().getName() + "s to your rune pouch.");
                    player.getInventory().delete(itemId, player.getInventory().getAmount(itemId));
                }
            }
        } else if (player.getRunePouchQtyThree() < 1 || (itemId == player.getRunePouchTypeThree())) {
            for (int i = 0; i < RUNES.length; i++) {
                if (itemId == RUNES[i]) {
                    Item rune = new Item(itemId);
                    player.setRunePouchTypeThree(itemId);
                    player.setRunePouchQtyThree(player.getInventory().getAmount(itemId) + player.getRunePouchQtyThree());
                    player.getPacketSender().sendMessage("@blu@You add " + player.getInventory().getAmount(itemId) + " " + rune.getDefinition().getName() + "s to your rune pouch.");
                    player.getInventory().delete(itemId, player.getInventory().getAmount(itemId));
                }
            }
        } else if (divinePouch && (player.getRunePouchQtyFour() < 1 || (itemId == player.getRunePouchTypeFour()))) {
            for (int i = 0; i < RUNES.length; i++) {
                if (itemId == RUNES[i]) {
                    Item rune = new Item(itemId);
                    player.setRunePouchTypeFour(itemId);
                    player.setRunePouchQtyFour(player.getInventory().getAmount(itemId) + player.getRunePouchQtyFour());
                    player.getPacketSender().sendMessage("@blu@You add " + player.getInventory().getAmount(itemId) + " " + rune.getDefinition().getName() + "s to your rune pouch.");
                    player.getInventory().delete(itemId, player.getInventory().getAmount(itemId));
                }
            }
        }
    }

    public static void emptyPouch(Player player) {
        player.getInventory().add(player.getRunePouchTypeOne(), player.getRunePouchQtyOne());
        player.getInventory().add(player.getRunePouchTypeTwo(), player.getRunePouchQtyTwo());
        player.getInventory().add(player.getRunePouchTypeThree(), player.getRunePouchQtyThree());
        player.getInventory().add(player.getRunePouchTypeFour(), player.getRunePouchQtyFour());

        player.setRunePouchQtyOne(0);
        player.setRunePouchQtyTwo(0);
        player.setRunePouchQtyThree(0);
        player.setRunePouchQtyFour(0);

        player.getPacketSender().sendMessage("@red@You've emptied your Rune Pouch!");

    }

    public static void checkPouch(Player player) {
        Item runeOneId = new Item(player.getRunePouchTypeOne());
        Item runeTwoId = new Item(player.getRunePouchTypeTwo());
        Item runeThreeId = new Item(player.getRunePouchTypeThree());
        Item runeFourId = new Item(player.getRunePouchTypeFour());
        String runeOne = runeOneId.getDefinition().getName();
        String runeTwo = runeTwoId.getDefinition().getName();
        String runeThree = runeThreeId.getDefinition().getName();
        String runeFour = runeFourId.getDefinition().getName();

        if (runeOneId.getDefinition().getId() < 500 || player.getRunePouchQtyOne() < 1)
            runeOne = "null";
        if (runeTwoId.getDefinition().getId() < 500 || player.getRunePouchQtyTwo() < 1)
            runeTwo = "null";
        if (runeThreeId.getDefinition().getId() < 500 || player.getRunePouchQtyThree() < 1)
            runeThree = "null";
        if (runeFourId.getDefinition().getId() < 500 || player.getRunePouchQtyFour() < 1)
            runeFour = "null";
		
		/*if(player.getRunePouchQtyOne() > 0)
			player.getPacketSender().sendMessage("@or2@" +runeOne+ ": " +player.getRunePouchQtyOne());
		if(player.getRunePouchQtyTwo() > 0)
			player.getPacketSender().sendMessage("@or2@" +runeTwo+ ": " +player.getRunePouchQtyTwo());
		if(player.getRunePouchQtyThree() > 0)
			player.getPacketSender().sendMessage("@or2@" +runeThree+ ": " +player.getRunePouchQtyThree());*/

        player.getPacketSender().sendMessage("@blu@" + runeOne + ": @red@" + player.getRunePouchQtyOne() +
                "     @blu@" + runeTwo + ": @red@" + player.getRunePouchQtyTwo() +
                "     @blu@" + runeThree + ": @red@" + player.getRunePouchQtyThree() +
                "     @blu@" + runeFour + ": @red@" + player.getRunePouchQtyFour());
    }


    public static final int[] RUNES = {554, 555, 556, 557, 558, 559, 560, 561, 562, 563, 564, 565, 566, 9075};


}
