package Orion.Game.Catalog.Layouts;

import Orion.Api.Networking.Message.IMessageComposer;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Server.Game.Catalog.Layouts.ICatalogLayout;
import com.google.inject.Singleton;

@Singleton
public class BadgeDisplayLayout implements ICatalogLayout {
    public void write(final ICatalogPage page, final IMessageComposer composer) {
        composer.appendString("badge_display");

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
