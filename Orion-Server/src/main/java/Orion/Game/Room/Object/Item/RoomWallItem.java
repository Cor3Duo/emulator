package Orion.Game.Room.Object.Item;

import Orion.Api.Networking.Message.IMessageComposer;
import Orion.Api.Server.Game.Room.IRoom;
import Orion.Api.Server.Game.Room.Object.Item.Base.IItemDefinition;
import Orion.Api.Server.Game.Room.Object.Item.Data.IRoomItemData;
import Orion.Api.Server.Game.Room.Object.Item.IRoomWallItem;
import Orion.Api.Server.Game.Room.Object.Item.Interaction.IRoomItemInteraction;
import Orion.Api.Server.Game.Util.Position;
import Orion.Api.Storage.Result.IConnectionResult;
import Orion.Game.Room.Object.Item.Data.RoomItemData;
import Orion.Protocol.Message.Composer.Room.Object.UpdateFloorItemComposer;
import Orion.Protocol.Utils.HexUtils;
import Orion.Writer.Room.Object.Item.RoomWallItemWriter;

public class RoomWallItem implements IRoomWallItem {
    private final IRoom room;

    private final int virtualId;

    private final IRoomItemData data;

    private IRoomItemInteraction interaction;

    private IItemDefinition definition;

    public RoomWallItem(
            final int virtualId,
            final IRoomItemData data,
            final IItemDefinition definition,
            final IRoom room
    ) {
        this.virtualId = virtualId;

        this.data = data;
        this.definition = definition;

        this.room = room;
    }

    @Override
    public IRoom getRoom() {
        return this.room;
    }

    @Override
    public int getVirtualId() {
        return this.virtualId;
    }

    @Override
    public IRoomItemData getData() {
        return this.data;
    }

    @Override
    public IItemDefinition getDefinition() {
        return this.definition;
    }

    @Override
    public void setInteraction(IRoomItemInteraction interaction) {
        if(this.interaction != null) return;

        this.interaction = interaction;
    }

    @Override
    public IRoomItemInteraction getInteraction() {
        return this.interaction;
    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public void setPosition(Position position) {
        // Should be implemented?
    }

    @Override
    public void toggleState() {
        String data = this.getData().getExtraData();

        if (!HexUtils.isNumeric(data)) return;

        int interactionCycleCount = this.getDefinition().getInteractionModesCount();

        if (interactionCycleCount <= 1) return;

        final int cycleValue = data.isEmpty() || data.trim().isEmpty() ? 0 : Integer.parseInt(data);

        this.getData().setExtraData(String.valueOf(
                (cycleValue + 1) % interactionCycleCount
        ));
    }

    @Override
    public void sendUpdate() {
        //this.room.broadcastMessage(new UpdateFloorItemComposer(this));
    }

    @Override
    public void write(IMessageComposer composer) {
        this.write(composer, false);
    }

    @Override
    public void write(final IMessageComposer composer, final boolean shouldComposeOwnerName) {
        RoomWallItemWriter.write(this, composer, shouldComposeOwnerName);
    }
}
