package com.arlania.model.input.impl;

import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.input.EnterAmount;
import com.arlania.util.Misc;
import com.arlania.world.entity.impl.player.Player;

public class BuyShards extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        player.getPacketSender().sendInterfaceRemoval();
        int shardsCanHold = Integer.MAX_VALUE - player.getInventory().getAmount(18016);

        if (shardsCanHold == 0) {
            player.getPacketSender().sendMessage("You cannot hold any more Spirit Shards.");
            return;
        }

        long moneyAmount = player.getMoneyInPouch();
        int shardValue = ItemDefinition.forId(18016).getValue();
        long canBeBought = Math.min(moneyAmount / shardValue, amount);
        canBeBought = Math.min(canBeBought, shardsCanHold);

        if (canBeBought == 0) {
            player.getPacketSender().sendMessage("You do not have enough money in your @red@money pouch@bla@ to buy that amount.");
            return;
        }
        long cost = shardValue * canBeBought;
        if (player.getMoneyInPouch() < cost) {
            player.getPacketSender().sendMessage("You do not have enough money in your @red@money pouch@bla@ to buy that amount.");
            return;
        }
        player.setMoneyInPouch(player.getMoneyInPouch() - cost);
        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch()); // Update
        player.getInventory().add(18016, (int) canBeBought);
        player.getPacketSender().sendMessage("You've bought " + canBeBought + " Spirit Shards for " + Misc.insertCommasToNumber("" + cost) + " coins.");
    }

}
