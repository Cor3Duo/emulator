package Orion.Protocol.Message.Event.Catalog;

import Orion.Api.Networking.Message.IMessageEvent;
import Orion.Api.Networking.Session.ISession;
import Orion.Api.Protocol.Message.IMessageEventHandler;
import Orion.Api.Server.Game.Catalog.ICatalogManager;
import Orion.Protocol.Message.Composer.Catalog.CatalogIndexComposer;
import Orion.Protocol.Message.Composer.Catalog.CatalogModeComposer;
import Orion.Protocol.Message.Event.EventHeaders;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class RequestCatalogModeEvent implements IMessageEventHandler {
    @Inject
    private ICatalogManager catalogManager;

    @Override
    public int getId() {
        return EventHeaders.RequestCatalogModeEvent;
    }

    @Override
    public void handle(IMessageEvent event, ISession session) {
        final String mode = event.readString();

        session.send(new CatalogModeComposer(mode));
        session.send(new CatalogIndexComposer(
                mode,
                session.getHabbo(),
                this.catalogManager,
                session.getHabbo().getPermission().hasAccountPermission("catalog_ids")
        ));
    }
}
