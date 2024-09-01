package Orion.Protocol.Message.Composer.Room.Settings;

import Orion.Api.Server.Game.Room.IRoom;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

public class RoomSettingsUpdatedComposer extends MessageComposer {
    public RoomSettingsUpdatedComposer(IRoom room) {
        super(ComposerHeaders.RoomSettingsUpdatedComposer);

        appendInt(room.getData().getId());
    }
}
