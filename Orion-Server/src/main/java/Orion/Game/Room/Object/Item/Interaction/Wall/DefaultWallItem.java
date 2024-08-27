package Orion.Game.Room.Object.Item.Interaction.Wall;

import Orion.Api.Server.Game.Room.Object.Entity.IRoomEntity;
import Orion.Api.Server.Game.Room.Object.Entity.Type.IHabboEntity;
import Orion.Api.Server.Game.Room.Object.Item.IRoomWallItem;
import Orion.Game.Room.Object.Item.Composition.InteractionName;
import Orion.Game.Room.Object.Item.Interaction.RoomItemInteraction;

@InteractionName("default_wall")
public class DefaultWallItem extends RoomItemInteraction {
    private final IRoomWallItem item;

    public DefaultWallItem(IRoomWallItem item) {
        this.item = item;
    }

    public void onInteract(IRoomEntity entity, int requestData) {
        if(!(entity instanceof IHabboEntity habboEntity)) return;

        if(!this.item.getRoom().getRightsComponent().hasRights(habboEntity.getHabbo())) return;

        this.item.toggleState();
        this.item.sendUpdate();

        this.onStateChanged(entity);
    }
}
