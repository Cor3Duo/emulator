package Orion.Api.Server.Game.Catalog.Components;

import Orion.Api.Server.Game.Catalog.Providers.IPurchaseProvider;

public interface ICatalogPurchaseProvidersComponent {
    void init();

    IPurchaseProvider getProviderByName(String name);
}
