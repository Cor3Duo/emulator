package Orion.Protocol.Message.Composer.Room.Settings;

import Orion.Api.Server.Game.Room.Data.IRoomData;
import Orion.Api.Server.Game.Room.IRoom;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

import java.util.List;

public class RoomSettingsDataComposer extends MessageComposer {
    public RoomSettingsDataComposer(final IRoom room) {
        super(ComposerHeaders.RoomSettingsDataComposer);

        IRoomData roomData = room.getData();

        appendInt(roomData.getId());
        appendString(roomData.getName());
        appendString(roomData.getDescription());
        appendInt(roomData.getAccessState().getState());
        appendInt(roomData.getCategoryId());
        appendInt(roomData.getMaxUsers());
        appendInt(300); // Limite de usuarios ?

        final List<String> tags = roomData.getTags();

        appendInt(tags.size());
        for (final String tag : tags) {
            appendString(tag);
        }

        appendInt(roomData.getTradeMode());
        appendInt(roomData.allowPets() ? 1 : 0);
        appendInt(roomData.allowPetsEat() ? 1 : 0);
        appendInt(roomData.allowWalkthrough() ? 1 : 0);
        appendInt(roomData.isHideWall() ? 1 : 0);
        appendInt(roomData.getWallThickness());
        appendInt(roomData.getFloorThickness());

        // Chat Settings
        appendInt(roomData.getChatMode());
        appendInt(roomData.getChatWeight());
        appendInt(roomData.getChatSpeed());
        appendInt(roomData.getChatDistance());
        appendInt(roomData.getChatProtection());

        appendBoolean(true); // irineu

        // Room Moderation Settings
        appendInt(roomData.getWhoCanMute());
        appendInt(roomData.getWhoCanKick());
        appendInt(roomData.getWhoCanBan());
    }
}
