package Orion.Game.Catalog.Factories;

import Orion.Api.Server.Game.Catalog.Data.ICatalogFeaturedPage;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Server.Game.Catalog.ICatalogManager;
import Orion.Api.Server.Game.Catalog.Items.ICatalogFactory;
import Orion.Api.Server.Game.Catalog.Items.ICatalogItem;
import Orion.Api.Server.Game.Room.Object.Item.IRoomItemManager;
import Orion.Api.Storage.Result.IConnectionResult;
import Orion.Game.Catalog.Data.CatalogFeaturedPage;
import Orion.Game.Catalog.Data.CatalogPage;
import Orion.Game.Catalog.Data.Purchase.CatalogPurchaseItem;
import Orion.Game.Catalog.Items.CatalogItem;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CatalogFactory implements ICatalogFactory {
    @Inject
    private ICatalogManager catalogManager;

    @Inject
    private IRoomItemManager roomItemManager;

    @Override
    public ICatalogPage createCatalogPage(IConnectionResult result) {
        final ICatalogPage catalogPage = new CatalogPage(result);

        catalogPage.fillItems(this.catalogManager);
        catalogPage.setLayout(this.catalogManager.getLayouts().getByName(catalogPage.getPageLayout()));

        if(this.catalogManager.getLayouts().getByName(catalogPage.getPageLayout()) == null) {
            System.out.println(STR."Catalog layout not found: \{catalogPage.getPageLayout()}");
        }

        return catalogPage;
    }

    @Override
    public ICatalogFeaturedPage createCatalogFeaturedPage(IConnectionResult result) {
        return new CatalogFeaturedPage(result);
    }

    @Override
    public ICatalogItem createCatalogItem(final IConnectionResult result) {
        final ICatalogItem catalogItem = new CatalogItem(result);

        catalogItem.fillItemsDefinition(this.roomItemManager);

        return catalogItem;
    }
}
