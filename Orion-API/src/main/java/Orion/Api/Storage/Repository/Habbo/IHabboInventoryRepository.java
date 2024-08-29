package Orion.Api.Storage.Repository.Habbo;

import Orion.Api.Storage.Result.IConnectionResultConsumer;
import Orion.Api.Storage.Result.IConnectionStatementConsumer;

public interface IHabboInventoryRepository {
    void loadAllHabboItems(IConnectionResultConsumer consumer, int habboId);

    void loadAllHabboBots(IConnectionResultConsumer consumer, int habboId);

    void createInventoryItem(IConnectionStatementConsumer consumer, IConnectionResultConsumer resultConsumer);
}
