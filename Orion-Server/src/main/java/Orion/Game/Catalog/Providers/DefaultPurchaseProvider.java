package Orion.Game.Catalog.Providers;

import Orion.Api.Networking.Session.ISession;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Server.Game.Catalog.Data.Purchase.ICatalogPurchaseItem;
import Orion.Api.Server.Game.Catalog.Items.ICatalogItem;
import Orion.Api.Server.Game.Catalog.Providers.IPurchaseProvider;
import Orion.Api.Server.Game.Habbo.Data.Inventory.IHabboInventoryItem;
import Orion.Api.Server.Game.Util.Alert.AlertPurchaseFailedCode;
import Orion.Api.Server.Game.Util.Inventory.UnseenItemCategory;
import Orion.Api.Storage.Repository.Habbo.IHabboInventoryRepository;
import Orion.Game.Habbo.Data.Inventory.HabboInventoryItem;
import Orion.Protocol.Message.Composer.Alerts.PurchaseFailedAlertComposer;
import Orion.Protocol.Message.Composer.Catalog.PurchaseOkComposer;
import Orion.Protocol.Message.Composer.Habbo.Inventory.InventoryRefreshComposer;
import Orion.Protocol.Message.Composer.Habbo.Inventory.UnseenItemComposer;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class DefaultPurchaseProvider implements IPurchaseProvider {
    @Inject
    private IHabboInventoryRepository repository;

    @Override
    public boolean isValidAttempt(final ISession session, final ICatalogPage page, final ICatalogItem item, int amount, String extraData) {
        return true;
    }

    @Override
    public void handle(
            final ISession session,
            final ICatalogPage page,
            final ICatalogItem item,
            int amount,
            int itemCreditsCost,
            int itemPointsCost,
            String extraData
    ) {
        if(!this.isValidAttempt(session, page, item, amount, extraData)) {
            session.send(new PurchaseFailedAlertComposer(AlertPurchaseFailedCode.SERVER_ERROR));
            return;
        }

        final List<Integer> unseenItems = new ArrayList<>();

        this.repository.createInventoryItem(statement -> {
            for (int i = 0; i < amount; i++) {
                for(final ICatalogPurchaseItem purchaseItem : item.getPurchaseItems()) {
                    statement.setInt(1, session.getHabbo().getData().getId());
                    statement.setInt(2, purchaseItem.getItemId());
                    statement.setString(3, extraData);

                    statement.addBatch();
                }
            }
        }, result -> {
            final int insertedId = result.getInt(1);

            final IHabboInventoryItem inventoryItem = new HabboInventoryItem(
                insertedId,
                session.getHabbo().getData().getId(),
                item.getItems().getFirst(),
                extraData,
                null
            );

            session.getHabbo().getInventory().getItemsComponent().addItem(inventoryItem);

            unseenItems.add(insertedId);
        });

        session.send(
                new UnseenItemComposer(UnseenItemCategory.OwnedItem, unseenItems),
                new PurchaseOkComposer(item),
                new InventoryRefreshComposer()
        );
    }
}
