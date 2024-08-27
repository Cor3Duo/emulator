package Orion.Api.Server.Game.Catalog.Handlers;

import Orion.Api.Networking.Session.ISession;

public interface ICatalogPurchaseHandler {
    void handle(final ISession session, int pageId, int itemId, String extraData, int amount);
}
