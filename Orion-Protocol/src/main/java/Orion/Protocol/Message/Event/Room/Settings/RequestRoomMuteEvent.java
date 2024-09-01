package Orion.Protocol.Message.Event.Room.Settings;

import Orion.Api.Networking.Message.IMessageEvent;
import Orion.Api.Networking.Session.ISession;
import Orion.Api.Protocol.Message.IMessageEventHandler;
import Orion.Api.Server.Game.Room.IRoom;
import Orion.Api.Server.Game.Room.Object.Entity.Type.IHabboEntity;
import Orion.Protocol.Message.Composer.Room.Chat.HabboWhisperComposer;
import Orion.Protocol.Message.Composer.Room.Settings.RoomMuteAllComposer;
import Orion.Protocol.Message.Event.EventHeaders;
import com.google.inject.Singleton;

@Singleton
public class RequestRoomMuteEvent implements IMessageEventHandler {
    @Override
    public int getId() {
        return EventHeaders.RequestRoomMuteEvent;
    }

    @Override
    public void handle(IMessageEvent event, ISession session) {
        if (!session.getHabbo().isInRoom()) return;

        IRoom room = session.getHabbo().getEntity().getRoom();
        if (!room.habboIsOwner(session.getHabbo())) return;

        final boolean muteAll = !room.isMuted();
        room.setIsMuted(muteAll);

        String message = muteAll ? "O quarto foi mutado." : "O quarto foi desmutado.";

        for (IHabboEntity entity : room.getEntitiesComponent().getHabboEntities()) {
            entity.getHabbo().getSession().send(new HabboWhisperComposer(entity.getVirtualId(), message, 0));
        }
        session.send(new RoomMuteAllComposer(muteAll));
    }
}
