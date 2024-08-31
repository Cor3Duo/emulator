package Orion.Api.Server.Game.Room.Component;

import Orion.Api.Networking.Session.ISession;
import Orion.Api.Server.Game.Room.Object.Item.Enum.FurnitureMovementError;
import Orion.Api.Server.Game.Room.Object.Item.IRoomFloorItem;
import Orion.Api.Server.Game.Room.Object.Item.IRoomWallItem;
import Orion.Api.Server.Game.Util.Position;
import Orion.Api.Util.Initializable;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface IRoomItemsComponent extends Initializable {
    ConcurrentHashMap<Integer, IRoomWallItem> getWallItems();

    ConcurrentHashMap<Integer, IRoomFloorItem> getFloorItems();

    void removeFloorItem(final ISession session, final IRoomFloorItem item);

    void justRemoveFloorItem(final IRoomFloorItem item);

    IRoomFloorItem getFloorItemByVirtualId(int virtualId);

    ConcurrentHashMap<Integer, String> getOwnerNames();

    FurnitureMovementError placeFloorItem(final ISession session, int virtualItemId, int x, int y, int rotation);

    IRoomWallItem getWallItemByVirtualId(int virtualId);

    FurnitureMovementError applyItemMovement(final IRoomFloorItem item, Position position, int rotation);
}
