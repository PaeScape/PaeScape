package com.arlania.world.content;

import com.arlania.model.Item;
import com.arlania.world.entity.impl.player.Player;

public class MagicPaper {

    public static boolean noteItems(Player player, int usedItemId) {

        //early return if the player has no magic paper
        if(!player.getInventory().contains(1811))
            return false;

        int maxnotes = 1;

        int notedItemId = usedItemId + 1;

        Item usedItem = new Item(usedItemId);

        boolean noteable = false;

        /*
         * Special cases for items that wouldn't normally note.
         */

        switch (usedItem.getId()) {
            case 6812:
                notedItemId = 6816;
                noteable = true;
                break;
        }

        /*
         * Declaring the noted item you are trying to make
         */

        Item canNote = new Item(notedItemId);

        /*
         * if item is noted nothing happens
         */


        if (usedItem.getDefinition().isNoted() || usedItem.getDefinition().isStackable()) {
            player.getPacketSender().sendMessage("@red@You can't note a stackable item.");
            return false;
        } else if (!usedItem.getDefinition().getName().contains(canNote.getDefinition().getName())) {
            player.getPacketSender().sendMessage("@red@This item can't be noted.");
            return false;
        }


        /*
         * if item is unnoted and the next item id is noted. Notes items.
         */

        else if ((canNote.getDefinition().isNoted() && !usedItem.getDefinition().isNoted()) || noteable) {

            int paperQty = player.getInventory().getAmount(1811);
            int usedItemQty = player.getInventory().getAmount(usedItemId);


            if (player.checkAchievementAbilities(player, "banker")) {
                maxnotes = Math.min(player.magicPaperAmount, usedItemQty);
            } else {
                maxnotes = Math.min(Math.min(player.magicPaperAmount, paperQty), usedItemQty);
            }

            player.getSkillManager().stopSkilling();

            if (!player.notingItems) {
                player.notingItems = true;
                if ((player.getInventory().getAmount(1811) >= maxnotes || player.checkAchievementAbilities(player, "banker")) && player.getInventory().getAmount(usedItemId) >= maxnotes) {
                    player.getInventory().delete(usedItemId, maxnotes);
                    player.getInventory().add(notedItemId, maxnotes);
                    if (player.achievementAbilities[0] == 0) {
                        player.getInventory().delete(1811, maxnotes);
                    }
                }
                player.notingItems = false;
            }
            return true;
        }

        //not sure how the else would trigger
        else {
            player.getPacketSender().sendMessage("This is an error. Please let Pat know. Error code #MP1");
            return false;
        }

    }

    public static boolean unNoteItems(Player player, int notedItemId) {

        //early return if the player has no magic paper
        if(!player.getInventory().contains(1811))
            return false;

        Item usedItem = new Item(notedItemId);
        int unnotedItemId = notedItemId - 1;
        Item unnotedItem = new Item(unnotedItemId);

        if(unnotedItem.getDefinition().isNoted() || unnotedItem.getDefinition().isStackable()) {
            player.getPacketSender().sendMessage("You can't unnote stacked or noted items.");
            return false;
        } else if (!usedItem.getDefinition().isNoted()) {
            player.getPacketSender().sendMessage("This item can't be unnoted. If it's a noted item please contact Pat.");
            return false;
        } else {

            int maxnotes = 1;
            int paperQty = player.getInventory().getAmount(1811);
            int notedQty = player.getInventory().getAmount(notedItemId);

            if (player.checkAchievementAbilities(player, "banker")) {
                maxnotes = Math.min(Math.min(player.magicPaperAmount, player.getInventory().getFreeSlots()), notedQty);
            } else {
                maxnotes = Math.min(Math.min(Math.min(player.magicPaperAmount, paperQty), notedQty), player.getInventory().getFreeSlots());
            }

            player.getSkillManager().stopSkilling();

            if (!player.notingItems) {
                player.notingItems = true;
                if ((player.getInventory().getAmount(1811) >= maxnotes || player.checkAchievementAbilities(player, "banker")) && player.getInventory().getAmount(notedItemId) >= maxnotes) {
                    player.getInventory().delete(notedItemId, maxnotes);
                    player.getInventory().add(unnotedItemId, maxnotes);

                    if(player.achievementAbilities[0] == 0) {
                        player.getInventory().delete(1811, maxnotes);
                    }
                }

                player.notingItems = false;
            }
            return true;

        }



    }


}
