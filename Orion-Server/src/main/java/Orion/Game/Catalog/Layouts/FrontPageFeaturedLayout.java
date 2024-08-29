package Orion.Game.Catalog.Layouts;

import Orion.Api.Server.Game.Catalog.Data.ICatalogFeaturedPage;
import Orion.Api.Server.Game.Catalog.ICatalogManager;
import Orion.Api.Server.Game.Catalog.Layouts.ICatalogFrontPage;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Networking.Message.IMessageComposer;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.Map;

@Singleton
public class FrontPageFeaturedLayout implements ICatalogFrontPage {
    @Inject
    private ICatalogManager catalogManager;

    public void write(final ICatalogPage page, final IMessageComposer composer) {
        composer.appendString("frontpage_featured");

        final String[] teaserImages = page.getPageTeaser().split(";");
        final String[] specialImages = page.getPageSpecial().split(";");

        composer.appendInt(1 + teaserImages.length + specialImages.length);
        composer.appendString(page.getPageHeadline());

        for (final String teaserImage : teaserImages) {
            composer.appendString(teaserImage);
        }

        for (final String specialImage : specialImages) {
            composer.appendString(specialImage);
        }

        composer.appendInt(3);
        composer.appendString(page.getPageText1());
        composer.appendString(page.getPageTextDetails());
        composer.appendString(page.getPageTextTeaser());
    }

    @Override
    public void writeFrontPage(final IMessageComposer composer) {
        final TIntObjectHashMap<ICatalogFeaturedPage> featuredPages = this.catalogManager.getCatalogFeaturedPages();

        composer.appendInt(featuredPages.size());

        for (final ICatalogFeaturedPage featuredPage : featuredPages.valueCollection()) {
            featuredPage.write(composer);
        }
    }
}
