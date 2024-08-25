package Orion.Protocol.Message.Composer.Room.Object;

import Orion.Api.Server.Game.Room.Object.Item.IRoomFloorItem;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

public class AddFloorItemComposer extends MessageComposer {
    public AddFloorItemComposer(final IRoomFloorItem item) {
        super(ComposerHeaders.AddFloorItemComposer);

        item.write(this);
    }
}
