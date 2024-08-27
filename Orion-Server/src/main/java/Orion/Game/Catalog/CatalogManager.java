package Orion.Game.Catalog;

import Orion.Api.Server.Game.Catalog.Components.ICatalogLayoutsComponent;
import Orion.Api.Server.Game.Catalog.Components.ICatalogPurchaseProvidersComponent;
import Orion.Api.Server.Game.Catalog.Data.ICatalogFeaturedPage;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Server.Game.Catalog.ICatalogManager;
import Orion.Api.Server.Game.Catalog.Items.ICatalogFactory;
import Orion.Api.Server.Game.Catalog.Items.ICatalogItem;
import Orion.Api.Server.Game.Habbo.IHabbo;
import Orion.Api.Storage.Repository.Catalog.ICatalogRepository;
import Orion.Game.Catalog.Data.Pages.RootCatalogPage;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class CatalogManager implements ICatalogManager {
    private final Logger logger = LogManager.getLogger();

    @Inject
    private ICatalogRepository catalogRepository;

    @Inject
    private ICatalogFactory catalogFactory;

    @Inject
    private ICatalogLayoutsComponent catalogLayoutsComponent;

    @Inject
    private ICatalogPurchaseProvidersComponent catalogPurchaseProvidersComponent;

    private final HashMap<Integer, ICatalogItem> catalogItems;

    private final HashMap<Integer, ICatalogPage> catalogPages;

    private final HashMap<Integer, ICatalogFeaturedPage> catalogFeaturedPages;

    public CatalogManager() {
        this.catalogItems = new HashMap<>();
        this.catalogPages = new HashMap<>();
        this.catalogFeaturedPages = new HashMap<>();
    }

    @Override
    public void initialize() {
        this.catalogLayoutsComponent.registerLayouts();
        this.catalogPurchaseProvidersComponent.init();

        this.loadCatalogItems();
        this.loadCatalogPages();
        this.loadFeaturedPages();
    }

    private void loadCatalogItems() {
        this.catalogRepository.getAllCatalogItems(result -> {
            if (result == null) return;

            try {
                final ICatalogItem catalogItem = catalogFactory.createCatalogItem(result);

                catalogItems.put(catalogItem.getId(), catalogItem);
            } catch (Exception e) {
                logger.error("Error while trying to fetch catalog item {}", result.getInt("id"), e);
            }
        });

        this.logger.info(STR."Loaded \{this.catalogItems.size()} catalog items from database.");
    }

    private void loadCatalogPages() {
        this.catalogPages.put(-1, new RootCatalogPage(null));

        this.catalogRepository.getAllCatalogPages(result -> {
            if (result == null) return;

            try {
                final ICatalogPage catalogPage = catalogFactory.createCatalogPage(result);

                this.catalogPages.put(catalogPage.getId(), catalogPage);
            } catch (Exception e) {
                logger.error("Error while trying to fetch catalog page [{}]", result.getInt("id"), e);
            }
        });

        this.logger.info(STR."Loaded \{this.catalogPages.size()} catalog pages from database.");

        for (var catalogPage : this.catalogPages.values()) {
            final ICatalogPage parentPage = this.catalogPages.get(catalogPage.getParentId());

            if (parentPage != null) {
                parentPage.getChildPages().putIfAbsent(catalogPage.getId(), catalogPage);
            }
        }
    }

    private void loadFeaturedPages() {
        this.catalogRepository.getAllFeaturedPages(result -> {
            if(result == null) return;

            try {
                final ICatalogFeaturedPage catalogFeaturedPage = catalogFactory.createCatalogFeaturedPage(result);

                this.catalogFeaturedPages.put(catalogFeaturedPage.getSlotId(), catalogFeaturedPage);
            } catch (Exception e) {
                logger.error("Error while trying to fetch catalog featured page [{}]", result.getInt("id"), e);
            }
        });

        this.logger.info(STR."Loaded \{this.catalogFeaturedPages.size()} catalog featured pages from database.");
    }

    @Override
    public ICatalogLayoutsComponent getLayouts() {
        return this.catalogLayoutsComponent;
    }

    @Override
    public ICatalogPurchaseProvidersComponent getProviders() {
        return this.catalogPurchaseProvidersComponent;
    }

    @Override
    public Map<Integer, ICatalogFeaturedPage> getCatalogFeaturedPages() {
        return this.catalogFeaturedPages;
    }

    @Override
    public ICatalogPage getPageById(int id) {
        return this.catalogPages.get(id);
    }

    @Override
    public boolean hasPageById(int id) {
        return this.catalogPages.containsKey(id);
    }

    @Override
    public boolean habboHasPageById(IHabbo habbo, int id) {
        if (!this.hasPageById(id)) return false;

        final ICatalogPage page = this.catalogPages.get(id);

        if (page == null) return false;

        return page.isEnabled() && page.isVisible() && page.getMinRank() <= habbo.getData().getRank();
    }

    @Override
    public boolean pageContainsItem(int pageId, int itemId) {
        final ICatalogPage page = this.catalogPages.get(pageId);

        if(page == null) return false;

        return page.getItems().containsKey(itemId);
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public HashMap<Integer, ICatalogItem> getCatalogItems() {
        return catalogItems;
    }

    @Override
    public HashMap<Integer, ICatalogPage> getCatalogPages() {
        return this.catalogPages;
    }

    @Override
    public List<ICatalogPage> getPagesForHabbo(int parentId, IHabbo habbo) {
        final List<ICatalogPage> pages = new ArrayList<>(this.catalogPages.size());

        for (final ICatalogPage page : this.catalogPages.values()) {
            if (page.getParentId() != parentId) continue;

            if (!page.isEnabled() || page.getMinRank() > habbo.getData().getRank()) continue;

            pages.add(page);
        }

        return pages;
    }

    @Override
    public ICatalogPage getPageForHabbo(int pageId, IHabbo habbo) {
        final ICatalogPage page = this.catalogPages.get(pageId);

        if (page == null || !page.isVisible()) {
            return null;
        }

        if (!page.isEnabled() || page.getMinRank() > habbo.getData().getRank()) {
            return null;
        }

        return page;
    }
}