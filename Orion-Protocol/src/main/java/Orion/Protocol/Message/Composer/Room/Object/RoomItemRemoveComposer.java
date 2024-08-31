package Orion.Protocol.Message.Composer.Room.Object;

import Orion.Api.Server.Game.Room.Object.Item.IRoomFloorItem;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

public class RoomItemRemoveComposer extends MessageComposer {
    public RoomItemRemoveComposer(final IRoomFloorItem item) {
        super(ComposerHeaders.RoomItemRemoveComposer);

        appendString(STR."\{item.getVirtualId()}");
        appendBoolean(false); // isExpired
        appendInt(item.getData().getOwnerId());
        appendInt(0); // delay
    }
}