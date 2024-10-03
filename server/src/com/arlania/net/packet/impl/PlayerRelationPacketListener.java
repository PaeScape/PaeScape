package com.arlania.net.packet.impl;

import com.arlania.DiscordBot;
import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.util.Misc;
import com.arlania.util.NameUtils;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionPlayerRelation;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.logging.Level;

/**
 * This packet listener is called when a player is doing something relative
 * to their friends or ignore list, such as adding or deleting a player from said list.
 *
 * @author relex lawl
 */

public class PlayerRelationPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        try {
            long username = packet.readLong();
            player.getActionTracker().offer(new ActionPlayerRelation(packet.getOpcode(), username));
            switch (packet.getOpcode()) {
                case ADD_FRIEND_OPCODE:
                    player.getRelations().addFriend(username);
                    break;
                case ADD_IGNORE_OPCODE:
                    player.getRelations().addIgnore(username);
                    break;
                case REMOVE_FRIEND_OPCODE:
                    player.getRelations().deleteFriend(username);
                    break;
                case REMOVE_IGNORE_OPCODE:
                    player.getRelations().deleteIgnore(username);
                    break;
                case SEND_PM_OPCODE:
                    Player friend = World.getPlayerByName(Misc.formatText(NameUtils.longToString(username)).replaceAll("_", " "));
                    int size = packet.getSize();
                    byte[] message = packet.readBytes(size);

                    String str = Misc.textUnpack(message, size).toLowerCase().replaceAll(";", ".");
                    if (Misc.blockedWord(str)) {
                        //DialogueManager.sendStatement(player, "A word was blocked in your sentence. Please do not repeat it!");
                        //return;
                    }


                    String PrivateChat = "[Private] " + player.getUsername() + " to " + friend.getUsername() + " - " + str;

                    //PlayerLogs.log("PrivateChat", PrivateChat);
                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(player.getDisplayName() + ": " + PrivateChat).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_PRIVATE_CHAT_CH).get());


                    player.getRelations().message(friend, message, size);
                    break;
            }
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    public static final int ADD_FRIEND_OPCODE = 188;

    public static final int REMOVE_FRIEND_OPCODE = 215;

    public static final int ADD_IGNORE_OPCODE = 133;

    public static final int REMOVE_IGNORE_OPCODE = 74;

    public static final int SEND_PM_OPCODE = 126;
}
