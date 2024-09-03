package Orion.Protocol.Message.Event.Room.Group;

import Orion.Api.Networking.Message.IMessageEvent;
import Orion.Api.Networking.Session.ISession;
import Orion.Api.Protocol.Message.IMessageEventHandler;
import Orion.Protocol.Message.Event.EventHeaders;
import com.google.inject.Singleton;

@Singleton
public class RequestGroupBadgesEvent implements IMessageEventHandler {
    @Override
    public int getId() {
        return EventHeaders.RequestGroupBadgesEvent;
    }

    @Override
    public void handle(IMessageEvent event, ISession session) {
    }
}
