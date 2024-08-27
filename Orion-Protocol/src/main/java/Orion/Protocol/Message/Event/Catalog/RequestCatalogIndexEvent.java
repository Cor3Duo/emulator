package Orion.Protocol.Message.Event.Catalog;

import Orion.Api.Networking.Message.IMessageEvent;
import Orion.Api.Networking.Session.ISession;
import Orion.Api.Protocol.Message.IMessageEventHandler;
import Orion.Protocol.Message.Event.EventHeaders;
import com.google.inject.Singleton;

@Singleton
public class RequestCatalogIndexEvent implements IMessageEventHandler {
    @Override
    public int getId() {
        return EventHeaders.RequestCatalogIndexEvent;
    }

    @Override
    public void handle(IMessageEvent event, ISession session) {
        
    }
}
