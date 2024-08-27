package Orion.Api.Server.Game.Catalog.Items;

import Orion.Api.Server.Game.Catalog.Data.Purchase.ICatalogPurchaseItem;
import Orion.Api.Server.Game.Room.Object.Item.Base.IItemDefinition;
import Orion.Api.Util.IFillable;
import Orion.Api.Util.IWriteable;

import java.util.List;

public interface ICatalogItem extends IFillable, IWriteable {
    int getId();

    List<ICatalogPurchaseItem> getPurchaseItems();

    int getPageId();

    String getCatalogName();

    int getCostCredits();

    int getCostPoints();

    int getPointsType();

    int getAmount();

    int getLimitedStack();

    int getLimitedSells();

    int getOrderNumber();

    int getOfferId();

    int getSongId();

    String getExtraData();

    boolean isHaveOffer();

    boolean isClubOnly();

    List<IItemDefinition> getItems();

    boolean isLimited();

    int compareTo(ICatalogItem item);

    void fillFurnitureItems();
}