package Orion.Game.Catalog.Data.Purchase;

import Orion.Api.Server.Game.Catalog.Data.Purchase.ICatalogPurchaseItem;

public class CatalogPurchaseItem implements ICatalogPurchaseItem {
    private final int furnitureId;
    private final int amount;

    public CatalogPurchaseItem(final int furnitureId, final int amount) {
        this.furnitureId = furnitureId;
        this.amount = amount;
    }

    @Override
    public int getFurnitureId() {
        return this.furnitureId;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }
}
