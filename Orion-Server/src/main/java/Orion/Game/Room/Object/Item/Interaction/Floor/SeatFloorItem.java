package Orion.Game.Room.Object.Item.Interaction.Floor;

import Orion.Api.Server.Game.Room.Object.Entity.Enum.RoomEntityStatus;
import Orion.Api.Server.Game.Room.Object.Entity.IRoomEntity;
import Orion.Api.Server.Game.Room.Object.Item.IRoomFloorItem;
import Orion.Api.Server.Game.Util.Position;
import Orion.Game.Room.Object.Item.Composition.InteractionName;

@InteractionName("seat_floor")
public class SeatFloorItem extends DefaultFloorItem {
    public SeatFloorItem(final IRoomFloorItem item) {
        super(item);
    }

    @Override
    public void onEntityEnter(IRoomEntity entity) {
        for (final Position position : this.item.getAffectedPositions()) {
            if(!position.equals(entity.getPosition())) continue;

            entity.sit(item.getDefinition().getStackHeight(), item.getData().getRotation());
            break;
        }
    }

    @Override
    public void onEntityLeave(IRoomEntity entity) {
        entity.removeStatus(RoomEntityStatus.SIT);
        entity.setNeedsUpdate(true);
    }
}
