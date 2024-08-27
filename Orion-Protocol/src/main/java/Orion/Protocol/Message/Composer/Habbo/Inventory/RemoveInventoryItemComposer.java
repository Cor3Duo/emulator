package Orion.Protocol.Message.Composer.Habbo.Inventory;

import Orion.Api.Server.Game.Habbo.Data.Inventory.IHabboInventoryItem;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

public class RemoveInventoryItemComposer extends MessageComposer {
    public RemoveInventoryItemComposer(final IHabboInventoryItem item) {
        super(ComposerHeaders.RemoveInventoryItemComposer);

        appendInt((int) item.getId());
    }
}
