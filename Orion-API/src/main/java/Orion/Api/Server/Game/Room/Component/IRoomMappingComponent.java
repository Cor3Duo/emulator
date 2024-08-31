package Orion.Api.Server.Game.Room.Component;

import Orion.Api.Server.Game.Room.Data.Model.IRoomTile;
import Orion.Api.Server.Game.Room.Object.IRoomObject;
import Orion.Api.Server.Game.Room.Object.Item.IRoomFloorItem;
import Orion.Api.Server.Game.Util.Position;
import Orion.Api.Util.IDisposable;
import Orion.Api.Util.Initializable;
import gnu.trove.set.hash.THashSet;

public interface IRoomMappingComponent extends IDisposable, Initializable {
    IRoomTile getTile(final Position position);

    IRoomTile getTile(final int x, final int y);

    THashSet<IRoomTile> getItemAffectedTiles(final IRoomFloorItem item);

    IRoomTile getDoorTile();

    void beforeInitialize();

    boolean isValidPosition(
            final IRoomObject object,
            final Position from,
            final Position to,
            final boolean isLastStep,
            final boolean isRetry,
            final boolean firstGeneration
    );

    boolean isValidPosition(Position position);
}
