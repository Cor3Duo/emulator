package Orion.Protocol.Message.Composer.Catalog;

import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Server.Game.Catalog.ICatalogManager;
import Orion.Api.Server.Game.Habbo.IHabbo;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

import java.util.Collection;

public class CatalogIndexComposer extends MessageComposer {
    public CatalogIndexComposer(
            final String mode,
            final IHabbo habbo,
            final ICatalogManager catalogManager,
            boolean canSeeIds
    ) {
        super(ComposerHeaders.CatalogIndexComposer);

        final Collection<ICatalogPage> pages = catalogManager.getPagesForHabbo(-1, habbo);

        catalogManager.getPageById(-1).write(this, canSeeIds);

        appendInt(pages.size());

        for (final ICatalogPage page : pages) {
            page.write(this, canSeeIds);
        }

        appendBoolean(false); // CTX: newAdditionsAvailable
        appendString(mode);
    }
}
