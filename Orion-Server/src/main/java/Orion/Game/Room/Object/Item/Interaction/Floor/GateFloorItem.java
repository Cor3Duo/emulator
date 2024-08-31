package Orion.Game.Room.Object.Item.Interaction.Floor;

import Orion.Api.Server.Game.Room.Object.Entity.IRoomEntity;
import Orion.Api.Server.Game.Room.Object.Entity.Type.IHabboEntity;
import Orion.Api.Server.Game.Room.Object.Item.IRoomFloorItem;
import Orion.Game.Room.Object.Item.Composition.InteractionName;

@InteractionName("gate")
public class GateFloorItem extends DefaultFloorItem {
    public GateFloorItem(final IRoomFloorItem item) {
        super(item);
    }

    @Override
    public void onInteract(IRoomEntity entity, int requestData) {
        if(!(entity instanceof IHabboEntity habboEntity)) return;

        if(!this.item.getRoom().getRightsComponent().hasRights(habboEntity.getHabbo())) return;

        this.item.toggleState();
        this.item.sendUpdate();

        this.onStateChanged(entity);
    }
}
