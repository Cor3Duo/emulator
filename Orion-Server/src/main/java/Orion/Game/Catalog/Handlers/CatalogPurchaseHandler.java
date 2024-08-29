package Orion.Game.Catalog.Handlers;

import Orion.Api.Networking.Session.ISession;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Server.Game.Catalog.Handlers.ICatalogPurchaseHandler;
import Orion.Api.Server.Game.Catalog.ICatalogManager;
import Orion.Api.Server.Game.Catalog.Items.ICatalogItem;
import Orion.Api.Server.Game.Catalog.Providers.IPurchaseProvider;
import Orion.Api.Server.Game.Enums.CurrencyType;
import Orion.Api.Server.Game.Room.Object.Item.Base.IItemDefinition;
import Orion.Api.Server.Game.Util.Alert.AlertPurchaseFailedCode;
import Orion.Api.Server.Game.Util.Alert.AlertPurchaseUnavailableCode;
import Orion.Api.Server.Game.Util.Alert.GenericErrorType;
import Orion.Api.Server.Game.Util.Alert.MiddleAlertType;
import Orion.Protocol.Message.Composer.Alerts.GenericErrorComposer;
import Orion.Protocol.Message.Composer.Alerts.MiddleAlertComposer;
import Orion.Protocol.Message.Composer.Alerts.PurchaseFailedAlertComposer;
import Orion.Protocol.Message.Composer.Alerts.PurchaseUnavailableAlertComposer;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CatalogPurchaseHandler implements ICatalogPurchaseHandler {
    @Inject
    private ICatalogManager catalogManager;

    public void handle(final ISession session, int pageId, int itemId, String extraData, int amount) {
        if(!this.isValidAttempt(session, amount, pageId, itemId)) {
            session.send(new PurchaseFailedAlertComposer(AlertPurchaseFailedCode.SERVER_ERROR));
            return;
        }

        final ICatalogPage page = this.catalogManager.getPageForHabbo(pageId, session.getHabbo());

        if(page == null) {
            System.out.println("Não tem página");
            session.getHabbo().getLogger().warn(STR."Page not found: \{pageId}");
            return;
        }

        // TODO: VIP rank from database
        if(page.isVipOnly() && session.getHabbo().getData().getRank() != 2) {
            session.getHabbo().getLogger().warn(STR."User attempted to purchase item from VIP page: \{page.getId()}");
            session.send(new GenericErrorComposer(GenericErrorType.NEED_TO_VIP));
            return;
        }

        if(page.getMinRank() > session.getHabbo().getData().getRank()) {
            session.getHabbo().getLogger().warn(STR."User attempted to purchase item with insufficient rank: \{page.getMinRank()}");
            session.send(new PurchaseUnavailableAlertComposer(AlertPurchaseUnavailableCode.ILLEGAL_PURCHASE));
            return;
        }

        final ICatalogItem item = page.getItemById(itemId);

        if(item == null) {
            session.getHabbo().getLogger().warn(STR."Item not found: \{itemId}");
            session.send(new PurchaseFailedAlertComposer(AlertPurchaseFailedCode.SERVER_ERROR));
            return;
        }

        if(amount > 1 && !item.isHaveOffer()) {
            session.getHabbo().getLogger().warn(STR."User attempted to purchase multiple items that don't have an offer: \{item.getId()}");
            session.send(new PurchaseUnavailableAlertComposer(AlertPurchaseUnavailableCode.ILLEGAL_PURCHASE));
            return;
        }

        final int itemCreditsCost = item.getCostCredits() * amount;
        final int itemPointsCost = item.getCostPoints() * amount;

        final int userCreditsAmount = session.getHabbo().getData().getCredits();
        final CurrencyType currencyType = CurrencyType.getFromType(item.getPointsType());

        if(currencyType == null && item.getPointsType() != 0) {
            session.getHabbo().getLogger().warn(STR."Invalid currency type for item: \{item.getId()}");
            session.send(new PurchaseFailedAlertComposer(AlertPurchaseFailedCode.SERVER_ERROR));
            return;
        }

        if(userCreditsAmount < itemCreditsCost) {
            session.getHabbo().getLogger().warn(STR."User doesn't have enough credits to purchase item: \{item.getId()}");
            session.send(new PurchaseFailedAlertComposer(AlertPurchaseFailedCode.UNKNOWN));
            return;
        }

        if(itemPointsCost > 0 && session.getHabbo().getCurrencies().getAmount(currencyType) < itemPointsCost) {
            session.getHabbo().getLogger().warn(STR."User doesn't have enough points to purchase item: \{item.getId()}");
            session.send(new PurchaseFailedAlertComposer(AlertPurchaseFailedCode.UNKNOWN));
            return;
        }

        this.finalizePurchase(session, page, item, amount, itemCreditsCost, itemPointsCost, extraData);
    }

    private boolean isValidAttempt(final ISession session, int amount, int pageId, int itemId) {
        if(amount < 1 || amount > 100) {
            return false;
        }

        if(pageId < 0 || itemId < 0) {
            return false;
        }

        if(!this.catalogManager.habboHasPageById(session.getHabbo(), pageId)) {
            return false;
        }

        return this.catalogManager.pageContainsItem(pageId, itemId);
    }

    private void finalizePurchase(
            final ISession session,
            final ICatalogPage page,
            final ICatalogItem item,
            int amount,
            int itemCreditsCost,
            int itemPointsCost,
            String extraData
    ) {
        final IItemDefinition itemDefinition = item.getItems().getFirst();

        if(itemDefinition == null) {
            session.getHabbo().getLogger().warn(STR."No item definition found for item: \{item.getId()}");
            session.send(new PurchaseFailedAlertComposer(AlertPurchaseFailedCode.SERVER_ERROR));
            return;
        }

        String providerName = itemDefinition.getInteractionType();

        if(itemDefinition.isDecoration()) {
            providerName = "room_decoration";
        }

        if(item.getCatalogName().startsWith("a0 pet")) {
            providerName = "pet";
        }

        if(item.getExtraData().equals("r")) {
            providerName = "bot";
        }

        if(item.isLimited()) {
            providerName = "limited_item";
        }

        providerName = "default";

        IPurchaseProvider provider = this.catalogManager.getProviders().getProviderByName(providerName);

        if(provider == null) {
            session.getHabbo().getLogger().warn(STR."No purchase provider found for item: \{itemDefinition.getInteractionType()}");
            session.send(new PurchaseFailedAlertComposer(AlertPurchaseFailedCode.SERVER_ERROR));
            return;
        }

        provider.handle(session, page, item, amount, itemCreditsCost, itemPointsCost, extraData);
    }
}
