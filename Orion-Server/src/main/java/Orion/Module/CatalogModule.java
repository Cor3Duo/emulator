package Orion.Module;

import Orion.Api.Server.Game.Catalog.Components.ICatalogLayoutsComponent;
import Orion.Api.Server.Game.Catalog.Components.ICatalogPurchaseProvidersComponent;
import Orion.Api.Server.Game.Catalog.Handlers.ICatalogPurchaseHandler;
import Orion.Api.Server.Game.Catalog.ICatalogManager;
import Orion.Api.Server.Game.Catalog.Items.ICatalogFactory;
import Orion.Game.Catalog.CatalogManager;
import Orion.Game.Catalog.Components.CatalogLayoutsComponent;
import Orion.Game.Catalog.Components.CatalogPurchaseProvidersComponent;
import Orion.Game.Catalog.Factories.CatalogFactory;
import Orion.Game.Catalog.Handlers.CatalogPurchaseHandler;
import com.google.inject.AbstractModule;

public class CatalogModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ICatalogManager.class).to(CatalogManager.class);
        bind(ICatalogFactory.class).to(CatalogFactory.class);
        bind(ICatalogLayoutsComponent.class).to(CatalogLayoutsComponent.class);
        bind(ICatalogPurchaseHandler.class).to(CatalogPurchaseHandler.class);
        bind(ICatalogPurchaseProvidersComponent.class).to(CatalogPurchaseProvidersComponent.class);
    }
}
