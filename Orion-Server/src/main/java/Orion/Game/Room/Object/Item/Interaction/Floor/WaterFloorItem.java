package Orion.Game.Room.Object.Item.Interaction.Floor;

import Orion.Api.Server.Game.Room.Object.Entity.IRoomEntity;
import Orion.Api.Server.Game.Room.Object.Entity.Type.IHabboEntity;
import Orion.Api.Server.Game.Room.Object.Item.IRoomFloorItem;
import Orion.Game.Room.Object.Item.Composition.InteractionName;
import Orion.Game.Room.Object.Item.Interaction.RoomItemInteraction;

@InteractionName("water")
public class WaterFloorItem extends RoomItemInteraction {
    private final IRoomFloorItem item;

    public WaterFloorItem(final IRoomFloorItem item) {
        this.item = item;
    }

    @Override
    public void onEntityEnter(IRoomEntity entity) {
        if(entity instanceof IHabboEntity) return;

        // give pet dip status
    }

    @Override
    public void onEntityLeave(IRoomEntity entity) {
        if(entity instanceof IHabboEntity) return;

        // remove pet dip status
    }
}
