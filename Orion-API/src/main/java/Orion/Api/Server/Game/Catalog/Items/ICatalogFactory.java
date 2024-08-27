package Orion.Api.Server.Game.Catalog.Items;

import Orion.Api.Server.Game.Catalog.Data.ICatalogFeaturedPage;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Storage.Result.IConnectionResult;

public interface ICatalogFactory {
    ICatalogItem createCatalogItem(IConnectionResult result) throws Exception;

    ICatalogPage createCatalogPage(IConnectionResult result) throws Exception;

    ICatalogFeaturedPage createCatalogFeaturedPage(IConnectionResult result);
}

