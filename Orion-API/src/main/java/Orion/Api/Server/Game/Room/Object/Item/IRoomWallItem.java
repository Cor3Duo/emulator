package Orion.Api.Server.Game.Room.Object.Item;

public interface IRoomWallItem extends IRoomItem {
    void toggleState();

    void sendUpdate();
}
