package Orion.Protocol.Message.Composer.Room.Settings;

import Orion.Api.Server.Game.Room.IRoom;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

public class RoomVisualizationSettingsComposer extends MessageComposer {
    public RoomVisualizationSettingsComposer(IRoom room) {
        super(ComposerHeaders.RoomVisualizationSettingsComposer);

        appendBoolean(room.getData().isHideWall());
        appendInt(room.getData().getThicknessWall());
        appendInt(room.getData().getThicknessFloor());
    }
}
