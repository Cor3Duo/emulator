package Orion.Protocol.Message.Composer.Room.Settings;

import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

public class RoomMuteAllComposer extends MessageComposer {
    public RoomMuteAllComposer(final boolean muteAll) {
        super(ComposerHeaders.RoomMuteAllComposer);

        appendBoolean(muteAll);
    }
}
