package Orion.Game.Room.Component;

import Orion.Api.Server.Game.Room.Component.IRoomChatComponent;
import Orion.Api.Server.Game.Room.IRoom;

public class RoomChatComponent implements IRoomChatComponent {
    private final IRoom room;

    public RoomChatComponent(final IRoom room) {
        this.room = room;
    }
}
