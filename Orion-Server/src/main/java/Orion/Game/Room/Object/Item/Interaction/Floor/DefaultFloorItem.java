package Orion.Game.Room.Object.Item.Interaction.Floor;

import Orion.Api.Server.Game.Room.Object.Entity.IRoomEntity;
import Orion.Api.Server.Game.Room.Object.Item.IRoomFloorItem;
import Orion.Game.Room.Object.Item.Composition.InteractionName;
import Orion.Game.Room.Object.Item.Interaction.RoomItemInteraction;

@InteractionName("default")
public class DefaultFloorItem extends RoomItemInteraction {
    protected final IRoomFloorItem item;

    public DefaultFloorItem(final IRoomFloorItem item) {
        this.item = item;
    }

    @Override
    public void onInteract(IRoomEntity entity, int requestData) {
        if(this.hasInteractionBlocked(entity)) return;

        this.item.toggleState();
        this.item.sendUpdate();

        this.onStateChanged(entity);
    }
}
