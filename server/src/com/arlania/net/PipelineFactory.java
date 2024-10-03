package com.arlania.net;

import com.arlania.GameSettings;
import com.arlania.net.login.LoginDecoder;
import com.arlania.net.login.LoginEncoder;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.DefaultChannelPipeline;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.Timer;

/**
 * The channel's events
 *
 * @author Gabriel Hannason
 */
public class PipelineFactory implements ChannelPipelineFactory {

    private final Timer timer;

    public PipelineFactory(Timer timer) {
        this.timer = timer;
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        final ChannelPipeline pipeline = new DefaultChannelPipeline();
        pipeline.addLast("timeout", new IdleStateHandler(timer, GameSettings.IDLE_TIME, 0, 0));
        pipeline.addLast("encoder", new LoginEncoder()); //Replaced by the PacketEncoder after login
        pipeline.addLast("decoder", new LoginDecoder()); //Replaced by the PacketDecoder after login
        pipeline.addLast("handler", new ChannelHandler());
        return pipeline;
    }
}
