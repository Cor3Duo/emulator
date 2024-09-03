package Orion.Protocol.Message.Event.Room.Settings;

import Orion.Api.Networking.Message.IMessageEvent;
import Orion.Api.Networking.Session.ISession;
import Orion.Api.Protocol.Message.IMessageEventHandler;
import Orion.Api.Server.Game.Habbo.IHabbo;
import Orion.Api.Server.Game.Room.Data.IRoomData;
import Orion.Api.Server.Game.Room.Enums.RoomAccessState;
import Orion.Api.Server.Game.Room.IRoom;
import Orion.Api.Server.Game.Room.Object.Entity.Type.IHabboEntity;
import Orion.Protocol.Message.Composer.Room.Settings.RoomSettingsSaveComposer;
import Orion.Protocol.Message.Composer.Room.Settings.RoomSettingsUpdatedComposer;
import Orion.Protocol.Message.Composer.Room.Settings.RoomVisualizationSettingsComposer;
import Orion.Protocol.Message.Event.EventHeaders;
import com.google.inject.Singleton;

@Singleton
public class RoomSettingsSaveEvent implements IMessageEventHandler {
    @Override
    public int getId() {
        return EventHeaders.RoomSettingsSaveEvent;
    }

    @Override
    public void handle(IMessageEvent event, ISession session) {
        if (!session.getHabbo().isInRoom()) return;

        int roomId = event.readInt();

        IRoom room = session.getHabbo().getEntity().getRoom();
        if (!room.habboIsOwner(session.getHabbo()) || room.getData().getId() != roomId) return;

        IRoomData roomData = room.getData();

        boolean lastWallState = roomData.isHideWall();
        int lastWallThickness = roomData.getWallThickness();
        int lastFloorThickness = roomData.getFloorThickness();

        String name = event.readString();
        if (name.length() > 50) {
            return;
        }

        String description = event.readString();
        if (description.length() > 150) {
            return;
        }

        String password = event.readString();
        if (password.length() > 20) {
            return;
        }

        // TODO: Apply word filter
        roomData.setName(name);
        roomData.setDescription(description);
        roomData.setAccessState(RoomAccessState.values()[event.readInt()]);
        roomData.setPassword(password);
        roomData.setMaxUsers(event.readInt());
        roomData.setCategoryId(event.readInt());

        int tagsCount = event.readInt();
        roomData.getTags().clear();
        for (int i = 0; i < tagsCount; i++) {
            roomData.addTag(event.readString());
        }

        roomData.setTradeMode(event.readInt());
        roomData.setAllowPets(event.readBoolean());
        roomData.setAllowPetsEat(event.readBoolean());
        roomData.setAllowWalkthrough(event.readBoolean());
        roomData.setHideWall(event.readBoolean());
        roomData.setWallThickness(event.readInt());
        roomData.setFloorThickness(event.readInt());
        roomData.setWhoCanMute(event.readInt());
        roomData.setWhoCanKick(event.readInt());
        roomData.setWhoCanBan(event.readInt());
        roomData.setChatMode(event.readInt());
        roomData.setChatWeight(event.readInt());
        roomData.setChatSpeed(event.readInt());
        roomData.setChatDistance(event.readInt());
        roomData.setChatProtection(event.readInt());

        session.send(new RoomSettingsSaveComposer(room));

        boolean needUpdate = lastWallState != roomData.isHideWall() || lastWallThickness != roomData.getWallThickness() || lastFloorThickness != roomData.getFloorThickness();
        if (needUpdate) {
            room.broadcastMessages(new RoomVisualizationSettingsComposer(room));
        }

        room.broadcastMessage(new RoomSettingsUpdatedComposer(room));
    }
}
