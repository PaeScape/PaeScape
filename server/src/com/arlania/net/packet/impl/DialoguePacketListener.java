package com.arlania.net.packet.impl;

import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.Action;

/**
 * This packet listener handles player's mouse click on the
 * "Click here to continue" option, etc.
 *
 * @author relex lawl
 */

public class DialoguePacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        player.getActionTracker().offer(new Action(Action.ActionType.DIALOGUE));
        switch (packet.getOpcode()) {
            case DIALOGUE_OPCODE:
                DialogueManager.next(player);
                break;
        }
    }

    public static final int DIALOGUE_OPCODE = 40;
}
