package Orion.Api.Server.Game.Catalog.Providers;

import Orion.Api.Networking.Session.ISession;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Server.Game.Catalog.Items.ICatalogItem;

public interface IPurchaseProvider {
    void handle(
            final ISession session,
            final ICatalogPage page,
            final ICatalogItem item,
            int amount,
            int itemCreditsCost,
            int itemPointsCost,
            String extraData
    );

    boolean isValidAttempt(
            final ISession session,
            final ICatalogPage page,
            final ICatalogItem item,
            int amount,
            String extraData
    );
}
