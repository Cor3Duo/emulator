package Orion.Api.Server.Game.Catalog.Data;

import Orion.Api.Server.Game.Catalog.Enums.CatalogFeaturedPageType;
import Orion.Api.Util.IFillable;
import Orion.Api.Util.IWriteable;

public interface ICatalogFeaturedPage extends IFillable, IWriteable {
    int getSlotId();

    String getCaption();

    String getImage();

    int getExpireTimestamp();

    CatalogFeaturedPageType getType();

    String getPageName();

    int getPageId();

    String getProductName();
}
