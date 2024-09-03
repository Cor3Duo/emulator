package Orion.Api.Storage.Repository.Room;

import Orion.Api.Storage.Result.IConnectionResultConsumer;

public interface IRoomGroupRepository {
    void loadAllGroupParts(IConnectionResultConsumer consumer);
}
