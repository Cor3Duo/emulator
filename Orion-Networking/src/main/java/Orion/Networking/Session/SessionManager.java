package Orion.Networking.Session;

import Orion.Api.Networking.Session.ISession;
import Orion.Api.Networking.Session.ISessionManager;
import Orion.Api.Networking.Session.Throttle.IAddressAttempt;
import Orion.Api.Server.Game.Habbo.IHabboManager;
import Orion.Networking.Session.Throttle.AddressAttempt;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class SessionManager implements ISessionManager {
    public static final AttributeKey<ISession> SESSION_KEY = AttributeKey.valueOf("Orion.Session");
    public static final AttributeKey<Integer> CHANNEL_KEY = AttributeKey.valueOf("Orion.ChannelId");

    private final ConcurrentHashMap<Integer, ISession> sessions;
    private final ConcurrentHashMap<String, IAddressAttempt> connectionAttempts;
    private final ChannelGroup channelGroup;

    private final AtomicInteger sessionId;

    @Inject
    private IHabboManager habboManager;

    public SessionManager() {
        this.sessionId = new AtomicInteger();
        this.sessions = new ConcurrentHashMap<>();
        this.connectionAttempts = new ConcurrentHashMap<>();

        this.channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    @Override
    public boolean addChannel(ChannelHandlerContext context, String ipAddress) {
        final int sessionId = this.sessionId.incrementAndGet();
        final Session session = new Session(sessionId, context, ipAddress);

        context.channel().attr(SESSION_KEY).set(session);
        context.channel().attr(CHANNEL_KEY).set(sessionId);

        this.channelGroup.add(context.channel());

        return this.sessions.putIfAbsent(sessionId, session) == null;
    }

    @Override
    public void createOrUpdateConnectionAttempt(String address) {
        this.connectionAttempts.computeIfAbsent(address, _ -> new AddressAttempt()).increment();
    }

    @Override
    public IAddressAttempt getConnectionAttempt(String address) {
        return this.connectionAttempts.get(address);
    }

    @Override
    public void removeSession(ISession session) {
        this.sessions.remove(session.getId());
    }

    @Override
    public void disposeSession(ISession session) {
        if(session.getHabbo() != null) {
            this.habboManager.disposeHabbo(session.getHabbo());
        }

        session.disconnect();

        this.removeSession(session);
    }
}
