package Orion.Game.Catalog.Layouts;

import Orion.Api.Server.Game.Catalog.Layouts.ICatalogLayout;
import com.google.inject.Singleton;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Networking.Message.IMessageComposer;

@Singleton
public class MarketplaceLayout implements ICatalogLayout {
    public void write(final ICatalogPage page, final IMessageComposer composer) {
        composer.appendString("marketplace");

        composer.appendInt(0);
        composer.appendInt(0);
    }
}
