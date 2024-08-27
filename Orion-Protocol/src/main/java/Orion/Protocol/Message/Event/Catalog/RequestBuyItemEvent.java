package Orion.Protocol.Message.Event.Catalog;

import Orion.Api.Networking.Message.IMessageEvent;
import Orion.Api.Networking.Session.ISession;
import Orion.Api.Protocol.Message.IMessageEventHandler;
import Orion.Api.Server.Core.Configuration.IEmulatorDatabaseSettings;
import Orion.Api.Server.Game.Catalog.Handlers.ICatalogPurchaseHandler;
import Orion.Api.Server.Game.Util.Alert.MiddleAlertType;
import Orion.Protocol.Message.Composer.Alerts.MiddleAlertComposer;
import Orion.Protocol.Message.Event.EventHeaders;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class RequestBuyItemEvent implements IMessageEventHandler {
    @Inject
    private ICatalogPurchaseHandler catalogPurchaseHandler;

    @Override
    public int getId() {
        return EventHeaders.RequestBuyItemEvent;
    }

    @Override
    public void handle(IMessageEvent event, ISession session) {
        final int pageId = event.readInt();
        final int itemId = event.readInt();
        final String extraData = event.readString();
        final int amount = Math.min(Math.max(event.readInt(), 1), 100);

        // TODO: Check last purchase time

        if(!session.getHabbo().getSettings().allowTrade()) {
            session.send(new MiddleAlertComposer(MiddleAlertType.ADMIN_PERSISTENT, "You are not allowed to purchase items from the catalog."));
            return;
        }

        this.catalogPurchaseHandler.handle(session, pageId, itemId, extraData, amount);
    }
}
