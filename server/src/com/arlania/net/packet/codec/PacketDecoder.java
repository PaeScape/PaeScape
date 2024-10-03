package com.arlania.net.packet.codec;

import com.arlania.net.packet.Packet;
import com.arlania.net.packet.Packet.PacketType;
import com.arlania.net.packet.PacketConstants;
import com.arlania.net.security.IsaacRandom;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class PacketDecoder extends FrameDecoder {

    private final IsaacRandom random;

    private int opcode = -1;
    private int size = -1;

    public PacketDecoder(IsaacRandom random) {
        this.random = random;
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        if (opcode == -1) {
            if (buffer.readable()) {
                int encryptedOpcode = buffer.readUnsignedByte();
                opcode = (encryptedOpcode - random.nextInt()) & 0xFF;
                size = PacketConstants.MESSAGE_SIZES[opcode];
            } else {
                return null;
            }
        }
        if (size == -1) {
            if (buffer.readable()) {
                size = buffer.readUnsignedByte();
            } else {
                return null;
            }
        }
        if (buffer.readableBytes() >= size) {
            final byte[] data = new byte[size];
            buffer.readBytes(data);
            final ChannelBuffer payload = ChannelBuffers.buffer(size);
            payload.writeBytes(data);
            try {
                return new Packet(opcode, PacketType.FIXED, payload);
            } finally {
                opcode = -1;
                size = -1;
            }
        }
        return null;
    }
}
