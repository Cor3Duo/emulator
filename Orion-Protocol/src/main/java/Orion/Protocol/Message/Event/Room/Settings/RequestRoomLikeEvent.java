package Orion.Protocol.Message.Event.Room.Settings;

import Orion.Api.Networking.Message.IMessageEvent;
import Orion.Api.Networking.Session.ISession;
import Orion.Api.Protocol.Message.IMessageEventHandler;
import Orion.Api.Server.Game.Habbo.IHabbo;
import Orion.Api.Server.Game.Room.Component.IRoomVotesComponent;
import Orion.Api.Server.Game.Room.IRoom;
import Orion.Protocol.Message.Composer.Room.Score.RoomScoreComposer;
import Orion.Protocol.Message.Event.EventHeaders;
import com.google.inject.Singleton;

@Singleton
public class RequestRoomLikeEvent implements IMessageEventHandler {
    @Override
    public int getId() {
        return EventHeaders.RequestRoomLikeEvent;
    }

    @Override
    public void handle(IMessageEvent event, ISession session) {
        IHabbo habbo = session.getHabbo();

        if (!habbo.isInRoom()) return;

        IRoom room = habbo.getEntity().getRoom();
        IRoomVotesComponent votesComponent = room.getVotesComponent();
        if (votesComponent.habboHasVote(habbo)) return;

        int score = room.getData().getScore() + 1;

        room.getData().setScore(score);
        votesComponent.addHabboVote(habbo);

        session.send(new RoomScoreComposer(score, false));
    }
}
