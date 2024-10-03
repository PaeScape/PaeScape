package com.arlania.net.packet.impl;

import com.arlania.model.definitions.NpcDefinition;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionExamineNPC;

public class ExamineNpcPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int npc = packet.readShort();
        if (npc <= 0) {
            return;
        }
        NpcDefinition npcDef = NpcDefinition.forId(npc);
        if (npcDef != null) {
            player.getActionTracker().offer(new ActionExamineNPC(npcDef.getId()));
            player.getPacketSender().sendMessage(npcDef.getExamine());
        }
    }

}
