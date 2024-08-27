package Orion.Protocol.Message.Event.Catalog;

import Orion.Api.Networking.Message.IMessageEvent;
import Orion.Api.Networking.Session.ISession;
import Orion.Api.Protocol.Message.IMessageEventHandler;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Server.Game.Catalog.ICatalogManager;
import Orion.Protocol.Message.Composer.Catalog.CatalogPageComposer;
import Orion.Protocol.Message.Event.EventHeaders;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class RequestCatalogPage implements IMessageEventHandler {
    @Inject
    private ICatalogManager catalogManager;

    @Override
    public int getId() {
        return EventHeaders.RequestCatalogPage;
    }

    @Override
    public void handle(IMessageEvent event, ISession session) {
        final int pageId = event.readInt();
        final int offerId = event.readInt();
        final String mode = event.readString();

        final ICatalogPage page = this.catalogManager.getPageById(pageId);

        if (page == null || page.getLayout() == null) return;

        if(!page.isVisible() || !page.isEnabled() || page.getMinRank() > session.getHabbo().getData().getRank()) {
            catalogManager.getLogger().warn(STR."[POSSIBLE SCRIPTER] Habbo \{session.getHabbo().getData().getUsername()} does not have page with id: \{pageId}");
            return; // TODO: Disconnect the client
        }

        session.send(new CatalogPageComposer(page, offerId, mode));
    }
}
