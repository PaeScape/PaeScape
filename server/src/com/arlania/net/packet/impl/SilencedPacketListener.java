package com.arlania.net.packet.impl;

import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener is called when a packet should not execute
 * any particular action or event, but will also not print out
 * any debug information.
 *
 * @author relex lawl
 */

public class SilencedPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
    }

}
