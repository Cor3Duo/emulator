package Orion.Game.Catalog.Layouts;

import Orion.Api.Server.Game.Catalog.Layouts.ICatalogLayout;
import com.google.inject.Singleton;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Networking.Message.IMessageComposer;

@Singleton
public class InfoLoyaltyLayout implements ICatalogLayout {
    public void write(final ICatalogPage page, final IMessageComposer composer) {
        composer.appendString("info_loyalty");

        composer.appendInt(1);
        composer.appendString(page.getPageHeadline());

        composer.appendInt(1);
        composer.appendString(page.getPageText1());
    }
}
