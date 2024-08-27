package Orion.Protocol.Message.Composer.Catalog;

import Orion.Api.Server.Game.Catalog.Items.ICatalogItem;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

public class PurchaseOkComposer extends MessageComposer {
    public PurchaseOkComposer(final ICatalogItem item) {
        super(ComposerHeaders.PurchaseOkComposer);

        item.write(this);
    }
}
