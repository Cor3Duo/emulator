package Orion.Game.Habbo.Data.Inventory.Components;

import Orion.Api.Server.Game.Habbo.Data.Inventory.Components.IInventoryItemsComponent;
import Orion.Api.Server.Game.Habbo.Data.Inventory.IHabboInventoryItem;
import Orion.Api.Server.Game.Habbo.IHabbo;
import Orion.Api.Server.Game.Room.Object.Item.Base.IItemDefinition;
import Orion.Api.Server.Game.Room.Object.Item.Data.ILimitedEditionData;
import Orion.Api.Server.Game.Room.Object.Item.IRoomItem;
import Orion.Game.Habbo.Data.Inventory.HabboInventoryItem;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryItemsComponent implements IInventoryItemsComponent {
    private IHabbo habbo;

    private final ConcurrentHashMap<Long, IHabboInventoryItem> items;

    public InventoryItemsComponent() {
        this.items = new ConcurrentHashMap<>();
    }

    @Override
    public Collection<IHabboInventoryItem> getItems() {
        return this.items.values();
    }

    @Override
    public void setItems(ConcurrentHashMap<Long, IHabboInventoryItem> items) {
        this.items.putAll(items);
    }

    @Override
    public IHabboInventoryItem getItemById(long itemId) {
        return this.items.get(itemId);
    }

    @Override
    public void addItem(final IHabboInventoryItem item) {
        this.items.put(item.getId(), item);
    }

    @Override
    public void addItem(IRoomItem item) {
        final IHabboInventoryItem inventoryItem = new HabboInventoryItem(item);

        this.items.putIfAbsent(inventoryItem.getId(), inventoryItem);
    }

    @Override
    public void removeItem(long itemId) {
        this.items.remove(itemId);
    }

    @Override
    public void dispose() {
        this.items.clear();
    }
}
