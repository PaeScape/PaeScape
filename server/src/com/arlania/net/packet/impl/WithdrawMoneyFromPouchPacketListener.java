package com.arlania.net.packet.impl;

import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.content.MoneyPouch;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionWithdrawMoneyFromPouch;

public class WithdrawMoneyFromPouchPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int amount = packet.readInt();
        if (player.getTrading().inTrade() || player.getDueling().inDuelScreen) {
            player.getPacketSender().sendMessage("You cannot withdraw money at the moment.");
        } else {
            player.getActionTracker().offer(new ActionWithdrawMoneyFromPouch(amount));
            MoneyPouch.withdrawMoney(player, amount);
        }
    }

}
