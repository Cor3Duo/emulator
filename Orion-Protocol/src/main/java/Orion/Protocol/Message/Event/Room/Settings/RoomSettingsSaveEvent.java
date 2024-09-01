package Orion.Protocol.Message.Event.Room.Settings;

import Orion.Api.Networking.Message.IMessageEvent;
import Orion.Api.Networking.Session.ISession;
import Orion.Api.Protocol.Message.IMessageEventHandler;
import Orion.Api.Server.Game.Room.Data.IRoomData;
import Orion.Api.Server.Game.Room.Enums.RoomAccessState;
import Orion.Api.Server.Game.Room.IRoom;
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

        roomData.setName(event.readString());
        roomData.setDescription(event.readString());
        roomData.setAccessState(RoomAccessState.values()[event.readInt()]);
        roomData.setPassword(event.readString());
        roomData.setMaxUsers(event.readInt());
        roomData.setCategoryId(event.readInt());

        int tagsCount = event.readInt();
        for (int i = 0; i < tagsCount; i++) {
            event.readString();
        }

        roomData.setTradeMode(event.readInt());
        roomData.setAllowPets(event.readBoolean());
        roomData.setAllowPetsEat(event.readBoolean());
        roomData.setAllowWalkthrough(event.readBoolean());
        roomData.setHideWall(event.readBoolean());

        session.send(new RoomSettingsSaveComposer(room));

        boolean needUpdate = lastWallState != roomData.isHideWall();
        if (needUpdate) {
            session.send(new RoomVisualizationSettingsComposer(room));
        }

        session.send(new RoomSettingsUpdatedComposer(room));
    }
}
