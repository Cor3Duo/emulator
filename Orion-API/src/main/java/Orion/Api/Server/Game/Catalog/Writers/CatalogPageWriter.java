package Orion.Api.Server.Game.Catalog.Writers;

import Orion.Api.Networking.Message.IMessageComposer;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;

public abstract class CatalogPageWriter {
    public static void write(
            final ICatalogPage page,
            final IMessageComposer composer,
            final boolean canSeeIds
    ) {
        composer.appendBoolean(page.isVisible());
        composer.appendInt(page.getIconImage());
        composer.appendInt(page.isEnabled() ? page.getId() : -1);
        composer.appendString(page.getCaptionSave());

        if (canSeeIds) {
            composer.appendString(STR."\{page.getCaption()} (\{page.getId()})");
        } else {
            composer.appendString(STR."\{page.getCaption()}");
        }

        composer.appendInt(0); // CTX: offerId
        composer.appendInt(page.getChildPages().size());

        for (final ICatalogPage childPage : page.getChildPages().values()) {
            childPage.write(composer, canSeeIds);
        }
    }
}
