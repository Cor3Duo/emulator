package Orion.Api.Storage.Repository.Catalog;

import Orion.Api.Storage.Result.IConnectionResultConsumer;

public interface ICatalogRepository {
    void getAllCatalogPages(IConnectionResultConsumer result);

    void getAllCatalogItems(IConnectionResultConsumer result);

    void getAllFeaturedPages(IConnectionResultConsumer result);
}
