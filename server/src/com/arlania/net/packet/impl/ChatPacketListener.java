package com.arlania.net.packet.impl;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.ChatMessage.Message;
import com.arlania.model.Flag;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.util.Misc;
import com.arlania.world.content.PlayerPunishment;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.Action;
import org.javacord.api.entity.message.MessageBuilder;

/**
 * This packet listener manages the spoken text by a player.
 *
 * @author relex lawl
 */

public class ChatPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int effects = packet.readUnsignedByteS();
        int color = packet.readUnsignedByteS();
        int size = packet.getSize();
        byte[] text = packet.readReversedBytesA(size);
        player.getActionTracker().offer(new Action(Action.ActionType.CHAT));
        if (PlayerPunishment.muted(player.getUsername()) || PlayerPunishment.IPMuted(player.getHostAddress())) {
            player.getPacketSender().sendMessage("You are muted and cannot chat.");
            return;
        }
        String str = Misc.textUnpack(text, size).toLowerCase().replaceAll(";", ".");

        if (Misc.blockedWord(str)) {
            //DialogueManager.sendStatement(player, "A word was blocked in your sentence. Please do not repeat it!");
            //return;
        }


        String PublicChat = "[Public] Player: " + player.getUsername() + ": " + str;

        //PlayerLogs.log("PublicChat", PublicChat);

        player.getChatMessages().set(new Message(color, effects, text));
        if (GameSettings.DISCORD)
            new MessageBuilder().append(player.getDisplayName() + ": " + str).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_CHAT_CH).get());

        player.getUpdateFlag().flag(Flag.CHAT);
    }

}
