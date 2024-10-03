package com.arlania.net.packet.impl;

import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.World;
import com.arlania.world.content.ProfileViewing;
import com.arlania.world.entity.impl.player.Player;

public class ViewProfilePacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int index = packet.readLEShort();
        if (index < 0) {
            return;
        }
        Player other = World.getPlayerByIndex(index);
        if (other == null) {
            return;
        }
        if (other.getPosition().getDistance(player.getPosition()) > 5) {
            player.sendMessage("The other player is too far away from you.");
            return;
        }
        ProfileViewing.view(player, other);
    }
}
