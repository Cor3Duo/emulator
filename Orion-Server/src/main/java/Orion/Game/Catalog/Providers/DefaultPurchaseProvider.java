package Orion.Game.Catalog.Providers;

import Orion.Api.Networking.Session.ISession;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Server.Game.Catalog.Items.ICatalogItem;
import Orion.Api.Server.Game.Catalog.Providers.IPurchaseProvider;
import Orion.Api.Storage.Repository.Habbo.IHabboInventoryRepository;
import com.google.inject.Inject;

public class DefaultPurchaseProvider implements IPurchaseProvider {
    @Inject
    private IHabboInventoryRepository repository;

    @Override
    public boolean isValidAttempt(final ISession session, final ICatalogPage page, final ICatalogItem item, int amount, String extraData) {
        return true;
    }

    @Override
    public void handle(
            final ISession session,
            final ICatalogPage page,
            final ICatalogItem item,
            int amount,
            int itemCreditsCost,
            int itemPointsCost,
            String extraData
    ) {

    }
}
