package Orion.Game.Catalog.Data;

import Orion.Api.Networking.Message.IMessageComposer;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Server.Game.Catalog.ICatalogManager;
import Orion.Api.Server.Game.Catalog.Items.ICatalogItem;
import Orion.Api.Server.Game.Catalog.Layouts.ICatalogLayout;
import Orion.Api.Server.Game.Catalog.Writers.CatalogPageWriter;
import Orion.Api.Storage.Result.IConnectionResult;

import java.util.HashMap;
import java.util.Map;

public class CatalogPage implements ICatalogPage {
    private int id;
    private int parentId;

    private String captionSave;
    private String caption;

    private String pageLayout;
    private ICatalogLayout layout;

    private int iconColor;
    private int iconImage;
    private int minRank;
    private int orderNum;
    private boolean isVisible;
    private boolean isEnabled;
    private boolean isClubOnly;
    private boolean isVipOnly;

    private String pageHeadline;
    private String pageTeaser;
    private String pageSpecial;
    private String pageText1;
    private String pageText2;
    private String pageTextDetails;
    private String pageTextTeaser;

    private String[] includes;

    private final Map<Integer, ICatalogItem> items;
    private final Map<Integer, ICatalogPage> childPages;

    public CatalogPage(IConnectionResult result) {
        this.items = new HashMap<>();
        this.childPages = new HashMap<>();

        if(result == null) return;

        try {
            this.fill(result);
        } catch (Exception e) {
            System.out.println(STR."Error while trying to fetch a catalog page: \{e.getMessage()}");
        }
    }

    public int getId() {
        return this.id;
    }

    public int getParentId() {
        return this.parentId;
    }

    public String getCaptionSave() {
        return this.captionSave;
    }

    public String getCaption() {
        return this.caption;
    }

    public String getPageLayout() {
        return this.pageLayout;
    }

    public void setLayout(final ICatalogLayout layout) {
        this.layout = layout;
    }

    public ICatalogLayout getLayout() {
        return this.layout;
    }

    public int getIconColor() {
        return this.iconColor;
    }

    public int getIconImage() {
        return this.iconImage;
    }

    public int getMinRank() {
        return this.minRank;
    }

    public int getOrder() {
        return this.orderNum;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public boolean isClubOnly() {
        return this.isClubOnly;
    }

    public boolean isVipOnly() {
        return this.isVipOnly;
    }

    public String getPageHeadline() {
        return this.pageHeadline;
    }

    public String getPageTeaser() {
        return this.pageTeaser;
    }

    public String getPageSpecial() {
        return this.pageSpecial;
    }

    public String getPageText1() {
        return this.pageText1;
    }

    public String getPageText2() {
        return this.pageText2;
    }

    public String getPageTextDetails() {
        return this.pageTextDetails;
    }

    public String getPageTextTeaser() {
        return this.pageTextTeaser;
    }

    public String[] getIncludes() {
        return this.includes;
    }

    public Map<Integer, ICatalogItem> getItems() {
        return this.items;
    }

    public Map<Integer, ICatalogPage> getChildPages() {
        return this.childPages;
    }

    public ICatalogItem getItemById(int id) {
        return this.items.get(id);
    }

    @Override
    public void write(final IMessageComposer composer, boolean canSeeIds) {
        CatalogPageWriter.write(this, composer, canSeeIds);
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.id = result.getInt("id");
        this.parentId = result.getInt("parent_id");

        this.captionSave = result.getString("caption_save");
        this.caption = result.getString("caption");

        this.pageLayout = result.getString("page_layout");
        this.iconColor = result.getInt("icon_color");
        this.iconImage = result.getInt("icon_image");
        this.minRank = result.getInt("min_rank");
        this.orderNum = result.getInt("order_num");
        this.isVisible = result.getBoolean("visible");
        this.isEnabled = result.getBoolean("enabled");
        this.isClubOnly = result.getBoolean("club_only");
        this.isVipOnly = result.getBoolean("vip_only");

        this.pageHeadline = result.getString("page_headline");
        this.pageTeaser = result.getString("page_teaser");
        this.pageSpecial = result.getString("page_special");
        this.pageText1 = result.getString("page_text1");
        this.pageText2 = result.getString("page_text2");
        this.pageTextDetails = result.getString("page_text_details");
        this.pageTextTeaser = result.getString("page_text_teaser");

        if(!result.getString("includes").isEmpty()) {
            this.includes = result.getString("includes").split(";");
        }
    }

    @Override
    public void fillItems(final ICatalogManager catalogManager) {
        for(final ICatalogItem item : catalogManager.getCatalogItems().values()) {
            if(item.getPageId() != this.id) continue;

            this.items.putIfAbsent(item.getId(), item);
        }
    }
}
