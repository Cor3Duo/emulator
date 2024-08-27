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
            session.send(new MiddleAlertComposer(MiddleAlertType.ADMIN_PERSISTENT, "Invalid page."));
            return;
        }

        // TODO: VIP rank from database
        if(page.isVipOnly() && session.getHabbo().getData().getRank() != 2) {
            session.send(new GenericErrorComposer(GenericErrorType.NEED_TO_VIP));
            return;
        }

        if(page.getMinRank() > session.getHabbo().getData().getRank()) {
            session.send(new PurchaseUnavailableAlertComposer(AlertPurchaseUnavailableCode.ILLEGAL_PURCHASE));
            return;
        }

        final ICatalogItem item = page.getItemById(itemId);

        if(item == null) {
            session.send(new PurchaseFailedAlertComposer(AlertPurchaseFailedCode.SERVER_ERROR));
            return;
        }

        if(amount > 1 && !item.isHaveOffer()) {
            session.send(new PurchaseUnavailableAlertComposer(AlertPurchaseUnavailableCode.ILLEGAL_PURCHASE));
            return;
        }

        final int itemCreditsCost = item.getCostCredits() * amount;
        final int itemPointsCost = item.getCostPoints() * amount;

        final int userCreditsAmount = session.getHabbo().getData().getCredits();
        final CurrencyType currencyType = CurrencyType.getFromType(item.getPointsType());

        if(currencyType == null && item.getPointsType() != 0) {
            session.send(new PurchaseFailedAlertComposer(AlertPurchaseFailedCode.SERVER_ERROR));
            return;
        }

        if(userCreditsAmount < itemCreditsCost) {
            session.send(new PurchaseFailedAlertComposer(AlertPurchaseFailedCode.UNKNOWN));
            return;
        }

        if(itemPointsCost > 0 && session.getHabbo().getCurrencies().getAmount(currencyType) < itemPointsCost) {
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
        final IItemDefinition furniture = item.getItems().getFirst();

        if(furniture == null) {
            session.send(new PurchaseFailedAlertComposer(AlertPurchaseFailedCode.SERVER_ERROR));
            return;
        }

        final IPurchaseProvider provider = this.catalogManager.getProviders().getProviderByName(furniture.getInteractionType());

        if(provider == null) {
            session.send(new PurchaseFailedAlertComposer(AlertPurchaseFailedCode.SERVER_ERROR));
            return;
        }

        if(!provider.isValidAttempt(session, page, item, amount, extraData)) {
            session.send(new PurchaseFailedAlertComposer(AlertPurchaseFailedCode.SERVER_ERROR));
            return;
        }

        provider.handle(session, page, item, amount, itemCreditsCost, itemPointsCost, extraData);
    }
}
