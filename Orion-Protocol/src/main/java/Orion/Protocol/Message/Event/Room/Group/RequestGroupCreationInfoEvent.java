package Orion.Protocol.Message.Event.Room.Group;

import Orion.Api.Networking.Message.IMessageEvent;
import Orion.Api.Networking.Session.ISession;
import Orion.Api.Protocol.Message.IMessageEventHandler;
import Orion.Api.Server.Core.Configuration.IEmulatorDatabaseSettings;
import Orion.Api.Server.Game.Room.IRoom;
import Orion.Protocol.Message.Composer.Room.Group.ResponseGroupCreationInfoComposer;
import Orion.Protocol.Message.Event.EventHeaders;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import gnu.trove.map.hash.THashMap;

import java.util.List;

@Singleton
public class RequestGroupCreationInfoEvent implements IMessageEventHandler {
    @Inject
    private IEmulatorDatabaseSettings databaseSettings;

    @Override
    public int getId() {
        return EventHeaders.RequestGroupCreationInfoEvent;
    }

    @Override
    public void handle(IMessageEvent event, ISession session) {
        THashMap<Integer, IRoom> ownRooms = session.getHabbo().getRooms().getOwnRooms();
        THashMap<Integer, IRoom> roomsWithRights = session.getHabbo().getRooms().getRoomsWithRights();

        List<IRoom> rooms = new java.util.ArrayList<>(ownRooms.values().stream().toList());
        rooms.addAll(roomsWithRights.values());

        session.send(new ResponseGroupCreationInfoComposer(
                this.databaseSettings.getIntegerOrDefault("catalog.guild.price", 10), rooms
        ));
    }
}
