package Orion.Api.Server.Game.Room.Object.Item.Interaction;

import Orion.Api.Server.Game.Room.Object.Entity.IRoomEntity;
import Orion.Api.Server.Game.Room.Object.Item.IRoomFloorItem;

public interface IRoomItemInteraction {
    boolean needsTick();

    void setTickCounter(double tickCounter);

    void finalizeTicks();

    void tick();

    void onTick();

    void onTickCompleted();

    void onPlaced();

    void onPickup();

    void onInteract(IRoomEntity entity, int requestData);

    void onStateChanged(IRoomEntity entity);

    void onEntityEnter(IRoomEntity entity);

    void onEntityLeave(IRoomEntity entity);

    void onItemAddedToStack(IRoomFloorItem item);
}
