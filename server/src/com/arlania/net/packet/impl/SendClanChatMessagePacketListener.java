package com.arlania.net.packet.impl;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.content.PlayerPunishment;
import com.arlania.world.content.clan.ClanChatManager;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.Action;
import org.javacord.api.entity.message.MessageBuilder;

public class SendClanChatMessagePacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        /** Gets requested bytes from the buffer client > server **/
        int size = packet.getSize();
        /** Check to flood **/
        if (size < 1 || size > 255) {
            System.err.println("blocked packet from sending from clan chat. Requested size=" + size);
            return;
        }

        String clanMessage = packet.readString();
        /** Checks invalid messages **/
        if (!validChatMessage(clanMessage))
            return;

        if (PlayerPunishment.muted(player.getUsername()) || PlayerPunishment.IPMuted(player.getHostAddress())) {
            player.getPacketSender().sendMessage("You are muted and cannot chat.");
            return;
        }

        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(player.getDisplayName() + ": " + clanMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_CLAN_CHAT_CH).get());

        player.getActionTracker().offer(new Action(Action.ActionType.SEND_CLAN_CHAT));
        ClanChatManager.sendMessage(player, clanMessage);
    }

    public static boolean validChatMessage(String clanMessage) {
        if (clanMessage == null || clanMessage.isEmpty() || clanMessage.length() > 255)
            return false;
        return clanMessage.chars().allMatch(ch -> VALID_CHAT_CHARS.chars().anyMatch(valid -> ch == valid));
    }

    public static final String VALID_CHAT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";
}
