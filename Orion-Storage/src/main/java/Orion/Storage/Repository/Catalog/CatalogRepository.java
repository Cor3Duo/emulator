package Orion.Storage.Repository.Catalog;

import Orion.Api.Storage.Repository.Catalog.ICatalogRepository;
import Orion.Api.Storage.Result.IConnectionResultConsumer;
import Orion.Storage.Query.Catalog.CatalogQuery;
import Orion.Storage.Repository.DatabaseRepository;
import com.google.inject.Singleton;

@Singleton
public class CatalogRepository extends DatabaseRepository implements ICatalogRepository {
    @Override
    public void getAllCatalogPages(IConnectionResultConsumer result) {
        this.select(CatalogQuery.SELECT_ALL_PAGES.get(), result);
    }

    @Override
    public void getAllCatalogItems(IConnectionResultConsumer result) {
        this.select(CatalogQuery.SELECT_ALL_ITEMS.get(), result);
    }

    @Override
    public void getAllFeaturedPages(IConnectionResultConsumer result) {
        this.select(CatalogQuery.SELECT_ALL_FEATURED_PAGES.get(), result);
    }
}
