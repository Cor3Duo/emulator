package Orion.Game.Catalog.Data;

import Orion.Api.Networking.Message.IMessageComposer;
import Orion.Api.Server.Game.Catalog.Data.ICatalogFeaturedPage;
import Orion.Api.Server.Game.Catalog.Enums.CatalogFeaturedPageType;
import Orion.Api.Server.Game.Catalog.Writers.CatalogFeaturedPageWriter;
import Orion.Api.Server.Game.Util.TimeUtil;
import Orion.Api.Storage.Result.IConnectionResult;

public class CatalogFeaturedPage implements ICatalogFeaturedPage {
    private int slotId;
    private String caption;
    private String image;
    private int expireTimestamp;
    private CatalogFeaturedPageType type;
    private String pageName;
    private int pageId;
    private String productName;

    public CatalogFeaturedPage(IConnectionResult result) {
        try {
            this.fill(result);
        } catch (Exception e) {
            System.out.println(STR."Error while trying to fetch a catalog featured page: \{e.getMessage()}");
        }
    }

    public int getSlotId() {
        return slotId;
    }

    public String getCaption() {
        return caption;
    }

    public String getImage() {
        return image;
    }

    public int getExpireTimestamp() {
        return (int) (TimeUtil.now() - expireTimestamp);
    }

    public CatalogFeaturedPageType getType() {
        return type;
    }

    public String getPageName() {
        return pageName;
    }

    public int getPageId() {
        return pageId;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.slotId = result.getInt("slot_id");
        this.caption = result.getString("caption");
        this.image = result.getString("image");
        this.expireTimestamp = result.getInt("expire_timestamp");
        this.type = CatalogFeaturedPageType.fromValue(result.getString("type"));
        this.pageName = result.getString("page_name");
        this.pageId = result.getInt("page_id");
        this.productName = result.getString("product_name");
    }

    @Override
    public void write(final IMessageComposer composer) {
        CatalogFeaturedPageWriter.write(this, composer);
    }
}
