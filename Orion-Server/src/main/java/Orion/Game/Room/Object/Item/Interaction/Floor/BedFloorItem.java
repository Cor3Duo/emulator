package Orion.Game.Room.Object.Item.Interaction.Floor;

import Orion.Api.Server.Game.Room.Object.Entity.Enum.RoomEntityStatus;
import Orion.Api.Server.Game.Room.Object.Entity.IRoomEntity;
import Orion.Api.Server.Game.Room.Object.Entity.Type.IHabboEntity;
import Orion.Api.Server.Game.Room.Object.Item.IRoomFloorItem;
import Orion.Game.Room.Object.Item.Composition.InteractionName;

@InteractionName("bed")
public class BedFloorItem extends DefaultFloorItem {
    public BedFloorItem(final IRoomFloorItem item) {
        super(item);
    }

    @Override
    public void onEntityEnter(IRoomEntity entity) {
        if(!(entity instanceof IHabboEntity habboEntity)) return;

        habboEntity.setHeadRotation(this.item.getData().getRotation());
        habboEntity.setBodyRotation(this.item.getData().getRotation());

        habboEntity.setStatus(RoomEntityStatus.LAY, STR."\{item.getDefinition().getStackHeight()}");
        habboEntity.setNeedsUpdate(true);
    }

    @Override
    public void onEntityLeave(IRoomEntity entity) {
        entity.removeStatus(RoomEntityStatus.LAY);
        entity.setNeedsUpdate(true);
    }
}
