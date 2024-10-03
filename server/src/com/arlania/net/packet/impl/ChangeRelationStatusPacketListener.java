package com.arlania.net.packet.impl;

import com.arlania.model.PlayerRelations.PrivateChatStatus;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.Action;

public class ChangeRelationStatusPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int actionId = packet.readInt();
        player.getActionTracker().offer(new Action(Action.ActionType.CHANGE_RELATION_STATUS));
        player.getRelations().setStatus(PrivateChatStatus.forActionId(actionId), true);
    }

}
