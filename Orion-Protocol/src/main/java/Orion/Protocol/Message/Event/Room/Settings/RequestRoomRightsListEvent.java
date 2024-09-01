package Orion.Protocol.Message.Event.Room.Settings;

import Orion.Api.Networking.Message.IMessageEvent;
import Orion.Api.Networking.Session.ISession;
import Orion.Api.Protocol.Message.IMessageEventHandler;
import Orion.Api.Server.Game.Room.IRoom;
import Orion.Protocol.Message.Composer.Room.Rights.RoomRightsListComposer;
import Orion.Protocol.Message.Event.EventHeaders;
import com.google.inject.Singleton;

@Singleton
public class RequestRoomRightsListEvent implements IMessageEventHandler {
    @Override
    public int getId() {
        return EventHeaders.RequestRoomRightsListEvent;
    }

    @Override
    public void handle(IMessageEvent event, ISession session) {
        if (!session.getHabbo().isInRoom()) return;

        int roomId = event.readInt();

        IRoom room = session.getHabbo().getEntity().getRoom();
        if (!room.habboIsOwner(session.getHabbo()) || room.getData().getId() != roomId) return;

        session.send(new RoomRightsListComposer(room));
    }
}
