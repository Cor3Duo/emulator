package Orion.Game.Catalog.Layouts;

import Orion.Api.Server.Game.Catalog.Layouts.ICatalogLayout;
import com.google.inject.Singleton;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Networking.Message.IMessageComposer;

@Singleton
public class DefaultCatalogLayout implements ICatalogLayout {
    public void write(final ICatalogPage page, final IMessageComposer composer) {
        composer.appendString("default_3x3");

        composer.appendInt(3);
        composer.appendString(page.getPageHeadline());
        composer.appendString(page.getPageTeaser());
        composer.appendString(page.getPageSpecial());

        composer.appendInt(3);
        composer.appendString(page.getPageText1());
        composer.appendString(page.getPageTextDetails());
        composer.appendString(page.getPageTextTeaser());
    }
}
