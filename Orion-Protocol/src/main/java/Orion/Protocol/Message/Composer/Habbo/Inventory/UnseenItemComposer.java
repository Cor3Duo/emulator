package Orion.Protocol.Message.Composer.Habbo.Inventory;

import Orion.Api.Server.Game.Util.Inventory.UnseenItemCategory;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

import java.util.List;

public class UnseenItemComposer extends MessageComposer {
    public UnseenItemComposer(UnseenItemCategory category, List<Integer> unseenItemsId) {
        super(ComposerHeaders.UnseenItemComposer);

        appendInt(1);
        appendInt(category.ordinal());
        appendInt(unseenItemsId.size());

        for (int unseenItemId : unseenItemsId) {
            appendInt(unseenItemId);
        }
    }

    public UnseenItemComposer(UnseenItemCategory category, int unseenItemId) {
        super(ComposerHeaders.UnseenItemComposer);

        appendInt(1);
        appendInt(category.ordinal());
        appendInt(1);
        appendInt(unseenItemId);
    }
}
