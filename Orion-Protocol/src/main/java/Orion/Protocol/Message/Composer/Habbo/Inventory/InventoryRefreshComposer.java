package Orion.Protocol.Message.Composer.Habbo.Inventory;

import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

public class InventoryRefreshComposer extends MessageComposer {
    public InventoryRefreshComposer() {
        super(ComposerHeaders.InventoryRefreshComposer);
    }
}
