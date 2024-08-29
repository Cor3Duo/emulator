package Orion.Api.Server.Game.Catalog;

import Orion.Api.Server.Game.Catalog.Components.ICatalogLayoutsComponent;
import Orion.Api.Server.Game.Catalog.Components.ICatalogPurchaseProvidersComponent;
import Orion.Api.Server.Game.Catalog.Data.ICatalogFeaturedPage;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Server.Game.Catalog.Items.ICatalogItem;
import Orion.Api.Server.Game.Habbo.IHabbo;
import Orion.Api.Util.Initializable;
import gnu.trove.map.hash.TIntObjectHashMap;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICatalogManager extends Initializable {
    Logger getLogger();

    HashMap<Integer, ICatalogItem> getCatalogItems();

    HashMap<Integer, ICatalogPage> getCatalogPages();

    ICatalogPage getPageById(int id);

    ICatalogLayoutsComponent getLayouts();

    ICatalogPurchaseProvidersComponent getProviders();

    boolean hasPageById(int id);

    boolean habboHasPageById(IHabbo habbo, int id);

    List<ICatalogPage> getPagesForHabbo(int parentId, IHabbo habbo);

    ICatalogPage getPageForHabbo(int pageId, IHabbo habbo);

    TIntObjectHashMap<ICatalogFeaturedPage> getCatalogFeaturedPages();

    boolean pageContainsItem(int pageId, int itemId);
}

