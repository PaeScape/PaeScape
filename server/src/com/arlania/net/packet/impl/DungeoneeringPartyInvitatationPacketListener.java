package com.arlania.net.packet.impl;

import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.util.Misc;
import com.arlania.world.entity.impl.player.Player;

public class DungeoneeringPartyInvitatationPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        String plrToInvite = Misc.readString(packet.getBuffer());
        if (plrToInvite == null || plrToInvite.length() <= 0)
            return;
        plrToInvite = Misc.formatText(plrToInvite);
    }
}
