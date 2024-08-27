package Orion.Game.Catalog.Layouts;

import Orion.Api.Server.Game.Catalog.Data.ICatalogFeaturedPage;
import Orion.Api.Server.Game.Catalog.ICatalogManager;
import Orion.Api.Server.Game.Catalog.Layouts.ICatalogFrontPage;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Networking.Message.IMessageComposer;

import java.util.Map;

@Singleton
public class FrontPageLayout implements ICatalogFrontPage {
    @Inject
    private ICatalogManager catalogManager;

    public void write(final ICatalogPage page, final IMessageComposer composer) {
        composer.appendString("frontpage4");

        composer.appendInt(2);
        composer.appendString(page.getPageHeadline());
        composer.appendString(page.getPageTeaser());

        composer.appendInt(3);
        composer.appendString(page.getPageText1());
        composer.appendString(page.getPageText2());
        composer.appendString(page.getPageTextTeaser());
    }

    @Override
    public void writeFrontPage(final IMessageComposer composer) {
        final Map<Integer, ICatalogFeaturedPage> featuredPages = this.catalogManager.getCatalogFeaturedPages();

        composer.appendInt(featuredPages.size());

        for (final ICatalogFeaturedPage featuredPage : featuredPages.values()) {
            featuredPage.write(composer);
        }
    }
}
