package Orion.Protocol.Message.Event.Room.Item;

import Orion.Api.Networking.Message.IMessageEvent;
import Orion.Api.Networking.Session.ISession;
import Orion.Api.Protocol.Message.IMessageEventHandler;
import Orion.Api.Server.Game.Room.IRoom;
import Orion.Api.Server.Game.Room.Object.Item.Enum.FurnitureMovementError;
import Orion.Api.Server.Game.Util.Alert.MiddleAlertType;
import Orion.Protocol.Message.Composer.Alerts.MiddleAlertComposer;
import Orion.Protocol.Message.Event.EventHeaders;
import com.google.inject.Singleton;

@Singleton
public class RequestPlaceItemEvent implements IMessageEventHandler {
    @Override
    public int getId() {
        return EventHeaders.RequestPlaceItemEvent;
    }

    @Override
    public void handle(IMessageEvent event, ISession session) {
        if(!session.getHabbo().isInRoom()) return;

        final IRoom room = session.getHabbo().getEntity().getRoom();

        if(room == null) return;

        if(!room.getRightsComponent().hasRights(session.getHabbo()) && !session.getHabbo().getPermission().hasAccountPermission("moverotate")) {
            session.send(new MiddleAlertComposer(MiddleAlertType.FURNITURE_PLACEMENT_ERROR, FurnitureMovementError.NO_RIGHTS));
            return;
        }

        final String data = event.readString();
        final String[] splitData = data.split(" ");

        if(splitData.length <= 3) return;

        final int virtualItemId = Integer.parseInt(splitData[0]);

        if(splitData[1].startsWith(":")) {
            this.handleWallItemPlacement(session, splitData);
            return;
        }

        try {
            final int x = Integer.parseInt(splitData[1]);
            final int y = Integer.parseInt(splitData[2]);
            final int rotation = Integer.parseInt(splitData[3]);

            FurnitureMovementError movementError = room.getItemsComponent().placeFloorItem(session, virtualItemId, x, y, rotation);

            System.out.println(movementError.get());
        } catch (Exception error) {
            session.getHabbo().getLogger().error(STR."Failed to parse item placement data: \{data}");
        }
    }

    private void handleWallItemPlacement(ISession session, String[] splitData) {
        String wallPosition = String.join(" ", splitData[1], splitData[2], splitData[3]);
    }
}
