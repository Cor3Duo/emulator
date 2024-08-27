package Orion.Game.Catalog.Components;

import Orion.Api.Server.Game.Catalog.Components.ICatalogPurchaseProvidersComponent;
import Orion.Api.Server.Game.Catalog.Providers.IPurchaseProvider;
import Orion.Game.Catalog.Providers.DefaultPurchaseProvider;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import java.util.HashMap;

@Singleton
public class CatalogPurchaseProvidersComponent implements ICatalogPurchaseProvidersComponent {
    private final HashMap<String, IPurchaseProvider> providers;

    @Inject
    private Injector injector;

    public CatalogPurchaseProvidersComponent() {
        this.providers = new HashMap<>();
    }

    public void init() {
        this.registerProviders();
    }

    public IPurchaseProvider getProviderByName(String name) {
        return this.providers.get(name);
    }

    private void registerProviders() {
        this.providers.putIfAbsent("default", new DefaultPurchaseProvider());

        this.providers.forEach((name, provider) -> {
            this.injector.injectMembers(provider);
        });
    }
}
