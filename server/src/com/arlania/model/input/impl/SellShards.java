package com.arlania.model.input.impl;

import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.input.EnterAmount;
import com.arlania.util.Misc;
import com.arlania.world.entity.impl.player.Player;

public class SellShards extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        player.getPacketSender().sendInterfaceRemoval();

        int shards = player.getInventory().getAmount(18016);
        if (amount > shards)
            amount = shards;
        if (amount > 0) {
            long rew = (long) ItemDefinition.forId(18016).getValue() * amount;
            player.setMoneyInPouch(player.getMoneyInPouch() + rew);
            player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
            player.getInventory().delete(18016, amount);
            player.getPacketSender().sendMessage("You've sold " + amount + " Spirit Shards for " + Misc.insertCommasToNumber("" + rew) + " coins.");
        }
    }

}
