package Orion.Api.Server.Game.Room.Component;

import Orion.Api.Server.Game.Habbo.IHabbo;

public interface IRoomVotesComponent {
    boolean habboHasVote(IHabbo habbo);

    void removeHabboVote(IHabbo habbo);

    void addHabboVote(IHabbo habbo);
}
