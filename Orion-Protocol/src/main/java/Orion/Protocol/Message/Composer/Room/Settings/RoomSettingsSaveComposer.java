package Orion.Protocol.Message.Composer.Room.Settings;

import Orion.Api.Server.Game.Room.IRoom;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

public class RoomSettingsSaveComposer extends MessageComposer {
    public RoomSettingsSaveComposer(final IRoom room) {
        super(ComposerHeaders.RoomSettingsSaveComposer);

        appendInt(room.getData().getId());
    }
}
