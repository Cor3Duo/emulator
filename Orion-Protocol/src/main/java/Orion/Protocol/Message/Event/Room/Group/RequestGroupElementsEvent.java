package Orion.Protocol.Message.Event.Room.Group;

import Orion.Api.Networking.Message.IMessageEvent;
import Orion.Api.Networking.Session.ISession;
import Orion.Api.Protocol.Message.IMessageEventHandler;
import Orion.Api.Server.Game.Room.Group.IRoomGroupManager;
import Orion.Protocol.Message.Composer.Room.Group.GroupElementsComposer;
import Orion.Protocol.Message.Event.EventHeaders;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class RequestGroupElementsEvent implements IMessageEventHandler {
    @Inject
    private IRoomGroupManager roomGroupManager;

    @Override
    public int getId() {
        return EventHeaders.RequestGroupElementsEvent;
    }

    @Override
    public void handle(IMessageEvent event, ISession session) {
        session.send(new GroupElementsComposer(this.roomGroupManager));
    }
}
