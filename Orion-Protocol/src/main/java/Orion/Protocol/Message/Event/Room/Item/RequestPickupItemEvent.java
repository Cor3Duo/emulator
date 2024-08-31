package Orion.Protocol.Message.Event.Room.Item;

import Orion.Api.Networking.Message.IMessageEvent;
import Orion.Api.Networking.Session.ISession;
import Orion.Api.Protocol.Message.IMessageEventHandler;
import Orion.Api.Server.Game.Room.Data.Model.IRoomTile;
import Orion.Api.Server.Game.Room.IRoom;
import Orion.Api.Server.Game.Room.Object.Item.IRoomFloorItem;
import Orion.Api.Server.Game.Util.Inventory.UnseenItemCategory;
import Orion.Protocol.Message.Composer.Habbo.Inventory.InventoryRefreshComposer;
import Orion.Protocol.Message.Composer.Habbo.Inventory.UnseenItemComposer;
import Orion.Protocol.Message.Composer.Room.Object.RoomItemRemoveComposer;
import Orion.Protocol.Message.Composer.Room.UpdateTileStackHeightComposer;
import Orion.Protocol.Message.Event.EventHeaders;
import com.google.inject.Singleton;
import gnu.trove.set.hash.THashSet;

import java.util.List;

@Singleton
public class RequestPickupItemEvent implements IMessageEventHandler {

    @Override
    public int getId() {
        return EventHeaders.RequestPickupItemEvent;
    }

    @Override
    public void handle(IMessageEvent event, ISession session) {
        int categoryId = event.readInt();
        int itemId = event.readInt();

        if (!session.getHabbo().isInRoom()) return;

        final IRoom room = session.getHabbo().getEntity().getRoom();
        final IRoomFloorItem item = room.getItemsComponent().getFloorItemByVirtualId(itemId);

        if (item == null) return;

        room.getItemsComponent().removeFloorItem(session, item);
    }
}
