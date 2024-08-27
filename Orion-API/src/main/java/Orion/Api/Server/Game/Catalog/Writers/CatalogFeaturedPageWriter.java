package Orion.Api.Server.Game.Catalog.Writers;

import Orion.Api.Networking.Message.IMessageComposer;
import Orion.Api.Server.Game.Catalog.Data.ICatalogFeaturedPage;
import Orion.Api.Server.Game.Catalog.Enums.CatalogFeaturedPageType;

public abstract class CatalogFeaturedPageWriter {
    public static void write(
            final ICatalogFeaturedPage page,
            final IMessageComposer composer
    ) {
        composer.appendInt(page.getSlotId());
        composer.appendString(page.getCaption());
        composer.appendString(page.getImage());
        composer.appendInt(page.getType().get());

        switch (page.getType()) {
            case CatalogFeaturedPageType.PAGE_NAME:
                composer.appendString(page.getPageName());
                break;
            case CatalogFeaturedPageType.PAGE_ID:
                composer.appendInt(page.getPageId());
                break;
            case CatalogFeaturedPageType.PRODUCT_NAME:
                composer.appendString(page.getProductName());
                break;
        }

        composer.appendInt(page.getExpireTimestamp());
    }
}
