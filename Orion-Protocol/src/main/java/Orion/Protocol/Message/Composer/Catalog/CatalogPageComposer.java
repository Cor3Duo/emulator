package Orion.Protocol.Message.Composer.Catalog;

import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Server.Game.Catalog.Items.ICatalogItem;
import Orion.Api.Server.Game.Catalog.Layouts.ICatalogFrontPage;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

public class CatalogPageComposer extends MessageComposer {
    public CatalogPageComposer(final ICatalogPage page, final int offerId, final String mode) {
        super(ComposerHeaders.CatalogPageComposer);

        appendInt(page.getId());
        appendString(mode);

        page.getLayout().write(page, this);

        appendInt(page.getItems().size());

        for (ICatalogItem item : page.getItems().values()) {
            item.write(this);
        }

        appendInt(offerId);
        appendBoolean(false); // CTX: AcceptSeasonCurrencyAsCredits

        if(page.getLayout() instanceof ICatalogFrontPage) {
            ((ICatalogFrontPage) page.getLayout()).writeFrontPage(this);
        }
    }
}
