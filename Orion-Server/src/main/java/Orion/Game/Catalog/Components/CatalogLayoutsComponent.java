package Orion.Game.Catalog.Components;

import Orion.Api.Server.Game.Catalog.Components.ICatalogLayoutsComponent;
import Orion.Api.Server.Game.Catalog.Layouts.ICatalogLayout;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import Orion.Game.Catalog.Layouts.*;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class CatalogLayoutsComponent implements ICatalogLayoutsComponent {
    private final Map<String, ICatalogLayout> layouts;

    @Inject
    private Injector injector;

    public CatalogLayoutsComponent() {
        this.layouts = new HashMap<>();
    }

    public ICatalogLayout getByName(String name) {
        return this.layouts.get(name);
    }

    public void registerLayouts() {
        this.layouts.put("default_3x3", new DefaultCatalogLayout());
        this.layouts.put("guild_furni", new GroupFurnitureLayout());
        this.layouts.put("guilds", new GroupFrontPageLayout());
        this.layouts.put("guild_forum", new GroupForumLayout());
        this.layouts.put("info_duckets", new InfoDucketsLayout());
        this.layouts.put("info_rentables", new InfoRentablesLayout());
        this.layouts.put("info_loyalty", new InfoLoyaltyLayout());
        this.layouts.put("loyalty_vip_buy", new LoyaltyVipBuyLayout());
        this.layouts.put("bots", new BotsLayout());
        this.layouts.put("pets", new PetsLayout());
        this.layouts.put("pets2", new Pets2Layout());
        this.layouts.put("pets3", new Pets3Layout());
        this.layouts.put("club_gift", new ClubGiftsLayout());
        this.layouts.put("frontpage", new FrontPageLayout());
        this.layouts.put("badge_display", new BadgeDisplayLayout());
        this.layouts.put("spaces_new", new SpacesLayout());
        this.layouts.put("soundmachine", new TraxLayout());
        this.layouts.put("info_pets", new InfoPetsLayout());
        this.layouts.put("club_buy", new ClubBuyLayout());
        this.layouts.put("roomads", new RoomAdsLayout());
        this.layouts.put("trophies", new TrophiesLayout());
        this.layouts.put("single_bundle", new SingleBundleLayout());
        this.layouts.put("marketplace", new MarketplaceLayout());
        this.layouts.put("marketplace_own_items", new MarketplaceOwnItemsLayout());
        this.layouts.put("recycler", new RecyclerLayout());
        this.layouts.put("recycler_info", new RecyclerInfoLayout());
        this.layouts.put("recycler_prizes", new RecyclerPrizesLayout());
        this.layouts.put("sold_ltd_items", new SoldLTDItemsLayout());
        this.layouts.put("default_3x3_color_grouping", new ColorGroupingLayout());
        this.layouts.put("recent_purchases", new RecentPurchasesLayout());
        this.layouts.put("room_bundle", new RoomBundleLayout());
        this.layouts.put("petcustomization", new PetCustomizationLayout());
        this.layouts.put("root", new CatalogRootLayout());
        this.layouts.put("vip_buy", new VipBuyLayout());
        this.layouts.put("frontpage_featured", new FrontPageFeaturedLayout());
        this.layouts.put("builders_club_addons", new BuildersClubAddonsLayout());
        this.layouts.put("builders_club_frontpage", new BuildersClubFrontPageLayout());
        this.layouts.put("builders_club_loyalty", new BuildersClubLoyaltyLayout());
        this.layouts.put("monkey", new InfoMonkeyLayout());
        this.layouts.put("niko", new InfoNikoLayout());
        this.layouts.put("mad_money", new MadMoneyLayout());

        if(this.injector == null) return;

        for (final ICatalogLayout layout : this.layouts.values()) {
            this.injector.injectMembers(layout);
        }
    }
}
