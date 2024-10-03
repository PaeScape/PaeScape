package com.arlania.net.packet.impl;

import com.arlania.model.Skill;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.entity.impl.player.Player;

public class PrestigeSkillPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int prestigeId = packet.readShort();
        Skill skill = Skill.forPrestigeId(prestigeId);
        if (skill == null) {
            return;
        }
        if (player.getInterfaceId() > 0) {
            player.getPacketSender().sendMessage("Please close all interfaces before doing this.");
        }
        //player.getSkillManager().resetSkill(skill, true);
    }

}
