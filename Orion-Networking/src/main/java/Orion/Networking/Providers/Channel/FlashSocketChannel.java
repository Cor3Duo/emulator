package Orion.Networking.Providers.Channel;

import Orion.Api.Networking.Session.ISessionManager;
import Orion.Api.Networking.Session.Throttle.IAddressAttempt;
import Orion.Api.Protocol.IServerMessageHandler;
import Orion.Networking.Codec.CrossDomainDecoder;
import Orion.Networking.Codec.Message.Flash.FlashMessageDecoder;
import Orion.Networking.Codec.Message.Flash.FlashMessageEncoder;
import Orion.Networking.Session.Handler.FlashSessionHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FlashSocketChannel extends ChannelInitializer<SocketChannel> {
    private final Logger logger = LogManager.getLogger();

    private final EventExecutorGroup executorGroup;

    private final ISessionManager sessionManager;

    private final FlashSessionHandler sessionHandler;

    public FlashSocketChannel(
            final ISessionManager sessionManager,
            final EventLoopGroup eventLoopGroupFactory,
            final IServerMessageHandler serverMessageHandler
    ) {
        this.sessionManager = sessionManager;
        this.executorGroup = eventLoopGroupFactory;

        this.sessionHandler = new FlashSessionHandler(this.sessionManager, serverMessageHandler);
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        final String ip = socketChannel.remoteAddress().getAddress().getHostAddress();

        this.sessionManager.createOrUpdateConnectionAttempt(ip);

        final IAddressAttempt addressAttempt = this.sessionManager.getConnectionAttempt(ip);

//        if (addressAttempt != null && addressAttempt.shouldBlockConnection()) {
//            this.logger.warn(STR."Connection attempt from \{ip} has been blocked.");
//
//            socketChannel.disconnect();
//            return;
//        }

        socketChannel.config().setTrafficClass(0x18);

        socketChannel.pipeline()
                .addLast("crossDomainDecoder", new CrossDomainDecoder())
                .addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8))
                .addLast("messageDecoder", new FlashMessageDecoder())
                .addLast("messageEncoder", new FlashMessageEncoder())
                .addLast("idleHandler", new IdleStateHandler(60, 30, 0))
                .addLast(this.executorGroup, "sessionHandler", this.sessionHandler);
    }
}
