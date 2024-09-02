package Orion.Protocol.Message.Composer.Room.Group;

import Orion.Api.Server.Game.Room.IRoom;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

import java.util.List;

public class ResponseGroupCreationInfoComposer extends MessageComposer {
    public ResponseGroupCreationInfoComposer(int groupCost, List<IRoom> rooms) {
        super(ComposerHeaders.ResponseGroupCreationInfoComposer);

        appendInt(groupCost);
        appendInt(rooms.size());

        for (IRoom room : rooms) {
            appendInt(room.getData().getId());
            appendString(room.getData().getName());
            appendBoolean(false);
        }
    }
}
