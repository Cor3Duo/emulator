package Orion.Api.Server.Game.Habbo.Data.Inventory.Components;

import Orion.Api.Server.Game.Habbo.Data.Inventory.IHabboInventoryItem;
import Orion.Api.Server.Game.Habbo.IHabbo;
import Orion.Api.Server.Game.Room.Object.Item.IRoomItem;
import Orion.Api.Util.IDisposable;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public interface IInventoryItemsComponent extends IDisposable {
    Collection<IHabboInventoryItem> getItems();

    void setItems(ConcurrentHashMap<Long, IHabboInventoryItem> items);

    IHabboInventoryItem getItemById(long itemId);

    void addItem(final IHabboInventoryItem item);

    void addItem(IRoomItem item);

    void removeItem(long itemI);
}
