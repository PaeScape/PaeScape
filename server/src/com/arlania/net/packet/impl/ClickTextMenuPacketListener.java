package com.arlania.net.packet.impl;

import com.arlania.model.StaffRights;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.content.clan.ClanChatManager;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionClickTextMenu;

public class ClickTextMenuPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {

        int interfaceId = packet.readShort();
        int menuId = packet.readByte();

        if (player.getStaffRights() == StaffRights.DEVELOPER) {
            player.getPacketSender().sendConsoleMessage("Clicked text menu: " + interfaceId + ", menuId: " + menuId);
        }
        player.getActionTracker().offer(new ActionClickTextMenu(interfaceId, menuId));

        if (interfaceId >= 29344 && interfaceId <= 29443) { // Clan chat list
            int index = interfaceId - 29344;
            ClanChatManager.handleMemberOption(player, index, menuId);
        }

    }

    public static final int OPCODE = 222;
}
