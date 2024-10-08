package Orion.Storage.Repository.Room;

import Orion.Api.Storage.Repository.Room.IRoomRepository;
import Orion.Api.Storage.Result.IConnectionResultConsumer;
import Orion.Storage.Query.Room.RoomQuery;
import Orion.Storage.Repository.DatabaseRepository;
import com.google.inject.Singleton;

@Singleton
public class RoomRepository extends DatabaseRepository implements IRoomRepository {
    @Override
    public void loadPublicRooms(IConnectionResultConsumer consumer, String isPublic, String isStaffPicked) {
        this.select(RoomQuery.SELECT_ALL_PUBLIC_ROOMS.get(), consumer, isPublic, isStaffPicked);
    }

    @Override
    public void loadStaffPickedRooms(IConnectionResultConsumer consumer, String isStaffPicked) {
        this.select(RoomQuery.SELECT_ALL_STAFF_PICKED_ROOMS.get(), consumer, isStaffPicked);
    }

    @Override
    public void loadFlatCategories(IConnectionResultConsumer consumer) {
        this.select(RoomQuery.SELECT_ALL_ROOMS_CATEGORIES.get(), consumer);
    }

    @Override
    public void loadRoomModels(IConnectionResultConsumer consumer) {
        this.select(RoomQuery.SELECT_ALL_ROOM_MODELS.get(), consumer);
    }

    @Override
    public void loadCustomRoomModel(IConnectionResultConsumer consumer, int roomId) {
        this.select(RoomQuery.LOAD_CUSTOM_ROOM_MODEL.get(), consumer, roomId);
    }

    @Override
    public void createRoom(IConnectionResultConsumer consumer, int ownerId, String ownerName, String name, String description, String modelName, int categoryId, int maxUsers, int tradeType) {
        this.insert(RoomQuery.CREATE_ROOM.get(), consumer, ownerId, ownerName, name, description, modelName, categoryId, maxUsers, tradeType);
    }

    @Override
    public void loadRoomById(IConnectionResultConsumer consumer, int roomId) {
        this.select(RoomQuery.SELECT_ROOM_BY_ID.get(), consumer, roomId);
    }
}

