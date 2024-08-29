package Orion.Game.Catalog.Items;

import Orion.Api.Networking.Message.IMessageComposer;
import Orion.Api.Server.Game.Catalog.Data.Purchase.ICatalogPurchaseItem;
import Orion.Api.Server.Game.Catalog.Items.ICatalogItem;
import Orion.Api.Server.Game.Catalog.Writers.CatalogItemWriter;
import Orion.Api.Server.Game.Room.Object.Item.Base.IItemDefinition;
import Orion.Api.Server.Game.Room.Object.Item.IRoomItemManager;
import Orion.Api.Storage.Result.IConnectionResult;
import Orion.Game.Catalog.Data.Purchase.CatalogPurchaseItem;

import java.util.ArrayList;
import java.util.List;

public class CatalogItem implements ICatalogItem {
    private int id;
    private final List<ICatalogPurchaseItem> purchaseItems;
    private int pageId;
    private String catalogName;
    private int costCredits;
    private int costPoints;
    private int pointsType;
    private int amount;
    private int limitedStack;
    private int limitedSells;
    private int orderNumber;
    private int offerId;
    private int songId;
    private String extraData;
    private boolean haveOffer;
    private boolean isClubOnly;

    private final List<IItemDefinition> items;

    public CatalogItem(final IConnectionResult result) {
        this.items = new ArrayList<>();
        this.purchaseItems = new ArrayList<>();

        try {
            this.fill(result);
        } catch (Exception e) {
            System.out.println(STR."Error while filling catalog item: \{e.getMessage()}");
        }
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public List<ICatalogPurchaseItem> getPurchaseItems() {
        return this.purchaseItems;
    }

    @Override
    public int getPageId() {
        return this.pageId;
    }

    @Override
    public String getCatalogName() {
        return this.catalogName;
    }

    @Override
    public int getCostCredits() {
        return this.costCredits;
    }

    @Override
    public int getCostPoints() {
        return this.costPoints;
    }

    @Override
    public int getPointsType() {
        return this.pointsType;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public int getLimitedStack() {
        return this.limitedStack;
    }

    @Override
    public int getLimitedSells() {
        return this.limitedSells;
    }

    @Override
    public int getOrderNumber() {
        return this.orderNumber;
    }

    @Override
    public int getOfferId() {
        return this.offerId;
    }

    @Override
    public int getSongId() {
        return this.songId;
    }

    @Override
    public String getExtraData() {
        return this.extraData;
    }

    @Override
    public boolean isHaveOffer() {
        return this.haveOffer;
    }

    @Override
    public boolean isClubOnly() {
        return this.isClubOnly;
    }

    @Override
    public List<IItemDefinition> getItems() {
        return this.items;
    }

    @Override
    public boolean isLimited() {
        return this.getLimitedStack() > 0;
    }

    @Override
    public boolean isAllowGift() {
        return !this.items.isEmpty() && this.items.getFirst().isAllowGift();
    }

    public int compareTo(final ICatalogItem item) {
        return this.orderNumber - item.getOrderNumber();
    }

    @Override
    public void write(final IMessageComposer composer) {
        CatalogItemWriter.write(this, composer);
    }

    @Override
    public void fill(final IConnectionResult data) throws Exception {
        this.id = data.getInt("id");
        this.pageId = data.getInt("page_id");
        this.catalogName = data.getString("catalog_name");
        this.costCredits = data.getInt("cost_credits");
        this.costPoints = data.getInt("cost_points");
        this.pointsType = data.getInt("points_type");
        this.amount = data.getInt("amount");
        this.limitedStack = data.getInt("limited_stack");
        this.limitedSells = data.getInt("limited_sells");
        this.orderNumber = data.getInt("order_number");
        this.offerId = data.getInt("offer_id");
        this.songId = data.getInt("song_id");
        this.extraData = data.getString("extradata");
        this.haveOffer = data.getBoolean("have_offer");
        this.isClubOnly = data.getBoolean("club_only");

        final String[] catalogItems = data.getString("item_ids").split(";");

        for (final String item : catalogItems) {
            final String[] itemData = item.split(":");

            if(itemData.length == 1) {
                this.purchaseItems.add(new CatalogPurchaseItem(Integer.parseInt(itemData[0]), 1));

                continue;
            }

            this.purchaseItems.add(new CatalogPurchaseItem(Integer.parseInt(itemData[0]), Integer.parseInt(itemData[1])));
        }
    }

    @Override
    public void fillItemsDefinition(final IRoomItemManager itemManager) {
        for (final ICatalogPurchaseItem purchaseItem : this.purchaseItems) {
            final IItemDefinition itemDefinition = itemManager.getItemDefinitionById(purchaseItem.getItemId());

            if (itemDefinition == null) continue;

            this.items.add(itemDefinition);
        }
    }
}
