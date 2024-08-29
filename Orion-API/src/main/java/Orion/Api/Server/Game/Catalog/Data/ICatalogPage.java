package Orion.Api.Server.Game.Catalog.Data;

import Orion.Api.Networking.Message.IMessageComposer;
import Orion.Api.Server.Game.Catalog.ICatalogManager;
import Orion.Api.Server.Game.Catalog.Items.ICatalogItem;
import Orion.Api.Server.Game.Catalog.Layouts.ICatalogLayout;
import Orion.Api.Util.IFillable;

import java.util.Map;

public interface ICatalogPage extends IFillable {
    int getId();

    int getParentId();

    String getCaptionSave();

    String getCaption();

    String getPageLayout();

    void setLayout(final ICatalogLayout layout);

    ICatalogLayout getLayout();

    int getIconColor();

    int getIconImage();

    int getMinRank();

    int getOrder();

    boolean isVisible();

    boolean isEnabled();

    boolean isClubOnly();

    boolean isVipOnly();

    String[] getIncludes();

    Map<Integer, ICatalogItem> getItems();

    Map<Integer, ICatalogPage> getChildPages();

    void fillItems(final ICatalogManager catalogManager);

    String getPageHeadline();

    String getPageTeaser();

    String getPageSpecial();

    String getPageText1();

    String getPageText2();

    String getPageTextDetails();

    String getPageTextTeaser();

    ICatalogItem getItemById(int id);

    void write(final IMessageComposer composer, boolean canSeeIds);
}
