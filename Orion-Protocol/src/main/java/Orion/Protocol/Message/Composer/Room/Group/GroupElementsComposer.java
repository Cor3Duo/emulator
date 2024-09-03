package Orion.Protocol.Message.Composer.Room.Group;

import Orion.Api.Server.Game.Room.Group.Data.IGroupElement;
import Orion.Api.Server.Game.Room.Group.IRoomGroupManager;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

import java.util.Map;

public class GroupElementsComposer extends MessageComposer {
    public GroupElementsComposer(final IRoomGroupManager roomGroupManager) {
        super(ComposerHeaders.GroupElementsComposer);

        this.composeGroupElement(roomGroupManager.getGroupBases(), true);
        this.composeGroupElement(roomGroupManager.getGroupSymbols(), true);
        this.composeGroupElement(roomGroupManager.getGroupBaseColors(), false);
        this.composeGroupElement(roomGroupManager.getGroupSymbolColors(), false);
        this.composeGroupElement(roomGroupManager.getGroupBackgroundColors(), false);
    }

    private void composeGroupElement(
            final Map<Integer, IGroupElement> groupElements,
            final boolean shouldComposeSecondValue
    ) {
        appendInt(groupElements.size());

        for (final IGroupElement groupElement : groupElements.values()) {
            appendInt(groupElement.getId());
            appendString(groupElement.getFirstValue());

            if(shouldComposeSecondValue) {
                appendString(groupElement.getSecondValue());
            }
        }
    }
}
