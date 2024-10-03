package com.arlania.net.packet.impl;

import com.arlania.model.StaffRights;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

//CALLED EVERY 15 MINUTES OF INACTIVITY

public class IdleLogoutPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        if (player.getStaffRights() == StaffRights.MODERATOR || player.getStaffRights() == StaffRights.ADMINISTRATOR || player.getStaffRights() == StaffRights.OWNER || player.getStaffRights() == StaffRights.DEVELOPER)
            return;

        if (player.aggressorTime > 0)
            return;

        if (player.getCurrentClanChat() != null)
            player.getCurrentClanChat().removeMember(player.getUsername());

        if (player.logout()) {
            World.getPlayers().remove(player);
        }
    }
}
