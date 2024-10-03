package com.arlania.net;

import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketBuilder;
import com.arlania.net.packet.PacketConstants;
import com.arlania.net.packet.PacketListener;
import com.arlania.net.packet.codec.PacketDecoder;
import com.arlania.net.packet.impl.PlayerRelationPacketListener;
import com.arlania.world.entity.impl.player.Player;
import org.jboss.netty.channel.Channel;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;

/**
 * The session handler dedicated to a player that will handle input and output
 * operations.
 *
 * @author lare96 <http://github.com/lare96>
 * @author blakeman8192
 */
public final class PlayerSession {

    /**
     * The queue of messages that will be handled on the next sequence.
     */
    private final Queue<Packet> prioritizedMessageQueue = new ConcurrentLinkedQueue<>();
    private final Queue<Packet> messageQueue = new ConcurrentLinkedQueue<>();

    /**
     * The channel that will manage the connection for this player.
     */
    private final Channel channel;

    /**
     * The player I/O operations will be executed for.
     */
    private Player player;

    /**
     * The current state of this I/O session.
     */
    private SessionState state = SessionState.CONNECTED;

    /**
     * Creates a new {@link PlayerSession}.
     *
     * @param key      the selection key registered to the selector.
     * @param response the current login response for this session.
     */
    public PlayerSession(Channel channel) {
        this.channel = channel;
    }

    /**
     * Queues the {@code msg} for this session to be encoded and sent to the
     * client.
     *
     * @param msg the message to queue.
     */
    public void queueMessage(PacketBuilder msg) {
        try {
            if (!channel.isOpen())
                return;
            channel.write(msg.toPacket());
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "Unable to queue message for player's session", e);
        }
    }

    /**
     * Processes all of the queued messages from the {@link PacketDecoder} by
     * polling the internal queue, and then handling them via the handleInputMessage.
     */
    public void handleQueuedMessages() {
        handlePrioritizedMessageQueue();
        Packet msg;
        while ((msg = messageQueue.poll()) != null) {
            handleInputMessage(msg);
        }
    }

    public void handlePrioritizedMessageQueue() {
        Packet msg;
        while ((msg = prioritizedMessageQueue.poll()) != null) {
            handleInputMessage(msg);
        }
    }

    /**
     * Handles an incoming message.
     *
     * @param msg The message to handle.
     */
    public void handleInputMessage(Packet msg) {
        try {
            int op = msg.getOpcode();

            PacketListener listener = PacketConstants.PACKETS[op];
            if (op != 11 && op != 60 && op != 5 && op != 12 && op != 103 && op != 230 && op != 4 && op != 98 && op != 164 && op != 248 && !(listener instanceof PlayerRelationPacketListener)) {
                if (msg.getLength() != PacketConstants.MESSAGE_SIZES[op]) {
//                    GameServer.getLogger().log(Level.SEVERE, "Player " + player.getUsername() + " ignored packet opcode: " + op + ", size: " + msg.getLength() + ", actual size: " + PacketConstants.MESSAGE_SIZES[op]);
                    return;
                }
            }

            listener.handleMessage(player, msg);
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "error decoding packet listener for " + player.getUsername(), e);
        }
    }

    /**
     * Uses state-machine to handle upstream messages from Netty.
     *
     * @param msg the message to handle.
     */
    public void handleIncomingMessage(Packet msg) {
        if (messageQueue.size() <= GameSettings.DECODE_LIMIT) {
            if (msg.prioritize()) {
                prioritizedMessageQueue.add(msg);
            } else {
                messageQueue.add(msg);
            }
        }
    }

    public void clearMessages() {
        messageQueue.clear();
    }

    /**
     * Gets the player I/O operations will be executed for.
     *
     * @return the player I/O operations.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the current state of this I/O session.
     *
     * @return the current state.
     */
    public SessionState getState() {
        return state;
    }

    /**
     * Sets the value for {@link PlayerSession#state}.
     *
     * @param state the new value to set.
     */
    public void setState(SessionState state) {
        this.state = state;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
