package Orion.Api.Server.Game.Habbo.Data;

import Orion.Api.Server.Game.Habbo.Data.Inventory.Components.IInventoryBotsComponent;
import Orion.Api.Server.Game.Habbo.Data.Inventory.Components.IInventoryItemsComponent;
import Orion.Api.Util.IDisposable;

public interface IHabboInventory extends IDisposable {
    boolean hasInventoryLoaded();

    IInventoryItemsComponent getItemsComponent();

    IInventoryBotsComponent getBotsComponent();

    void setInventoryLoaded();
}
