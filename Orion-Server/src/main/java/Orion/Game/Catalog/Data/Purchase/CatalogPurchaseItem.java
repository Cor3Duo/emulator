package Orion.Game.Catalog.Data.Purchase;

import Orion.Api.Server.Game.Catalog.Data.Purchase.ICatalogPurchaseItem;

public class CatalogPurchaseItem implements ICatalogPurchaseItem {
    private final int itemId;
    private final int amount;

    public CatalogPurchaseItem(final int itemId, final int amount) {
        this.itemId = itemId;
        this.amount = amount;
    }

    @Override
    public int getItemId() {
        return this.itemId;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }
}
