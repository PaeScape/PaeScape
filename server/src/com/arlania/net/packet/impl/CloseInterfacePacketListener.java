package com.arlania.net.packet.impl;

import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.Action;

public class CloseInterfacePacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        player.getActionTracker().offer(new Action(Action.ActionType.CLOSE_INTERFACE));
        player.getPacketSender().sendClientRightClickRemoval();
        player.getPacketSender().sendInterfaceRemoval();
        //	player.getPacketSender().sendTabInterface(Constants.CLAN_CHAT_TAB, 29328); //Clan chat tab
        //player.getAttributes().setSkillGuideInterfaceData(null);
    }
}
