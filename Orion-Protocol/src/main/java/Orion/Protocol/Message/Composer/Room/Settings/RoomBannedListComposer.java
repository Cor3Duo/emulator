package Orion.Protocol.Message.Composer.Room.Settings;

import Orion.Api.Server.Game.Room.Data.Ban.IRoomBan;
import Orion.Api.Server.Game.Room.IRoom;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

import java.util.List;

public class RoomBannedListComposer extends MessageComposer {
    public RoomBannedListComposer(final IRoom room) {
        super(ComposerHeaders.RoomBannedListComposer);

        final List<IRoomBan> bans = room.getBansComponent().getBans();

        appendInt(bans.size());
        for (final IRoomBan ban : bans) {
            appendInt(ban.getUserId());
            appendString(ban.getUsername());
        }
    }
}
