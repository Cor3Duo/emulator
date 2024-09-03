package Orion.Storage.Repository.Room;

import Orion.Api.Storage.Repository.Room.IRoomGroupRepository;
import Orion.Api.Storage.Result.IConnectionResultConsumer;
import Orion.Storage.Query.Room.RoomGroupQuery;
import Orion.Storage.Repository.DatabaseRepository;
import com.google.inject.Singleton;

@Singleton
public class RoomGroupRepository extends DatabaseRepository implements IRoomGroupRepository {
    @Override
    public void loadAllGroupParts(IConnectionResultConsumer consumer) {
        this.select(RoomGroupQuery.LOAD_ALL_GROUP_PARTS.get(), consumer);
    }
}
