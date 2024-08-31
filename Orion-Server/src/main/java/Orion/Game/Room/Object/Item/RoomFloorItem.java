package Orion.Game.Room.Object.Item;

import Orion.Api.Networking.Message.IMessageComposer;
import Orion.Api.Server.Game.Room.IRoom;
import Orion.Api.Server.Game.Room.Object.Item.Base.IItemDefinition;
import Orion.Api.Server.Game.Room.Object.Item.Data.IRoomItemData;
import Orion.Api.Server.Game.Room.Object.Item.IRoomFloorItem;
import Orion.Api.Server.Game.Room.Object.Item.Interaction.IRoomItemInteraction;
import Orion.Api.Server.Game.Util.Position;
import Orion.Protocol.Message.Composer.Room.Object.UpdateFloorItemComposer;
import Orion.Protocol.Utils.HexUtils;
import Orion.Writer.Room.Object.Item.RoomFloorItemWriter;
import gnu.trove.map.hash.THashMap;

import java.util.ArrayList;
import java.util.List;

public class RoomFloorItem implements IRoomFloorItem {
    private final IRoom room;

    private final int virtualId;

    private THashMap<String, Object> attributes;

    private final IRoomItemData data;

    private IRoomItemInteraction interaction;

    private final IItemDefinition definition;

    private final List<Position> affectedPositions;

    public RoomFloorItem(
            final int virtualId,
            final IRoomItemData data,
            final IItemDefinition definition,
            final IRoom room
    ) {
        this.virtualId = virtualId;

        this.data = data;
        this.definition = definition;

        this.room = room;

        this.affectedPositions = new ArrayList<>();
        this.affectedPositions.add(this.data.getPosition());
    }

    @Override
    public void setInteraction(IRoomItemInteraction interaction) {
        if(this.interaction != null) return;

        this.interaction = interaction;
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
    public Position getPosition() {
        return this.data.getPosition();
    }

    @Override
    public void setPosition(Position position) {
        // TODO: Implement this
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
    public IRoomItemInteraction getInteraction() {
        return this.interaction;
    }

    @Override
    public void setAffectedPositions(List<Position> positions) {
        this.affectedPositions.clear();
        this.affectedPositions.addAll(positions);
    }

    @Override
    public List<Position> getAffectedPositions() {
        return this.affectedPositions;
    }

    @Override
    public void sendUpdate() {
        this.room.broadcastMessage(new UpdateFloorItemComposer(this));
    }

    @Override
    public void setAttribute(String key, Object value) {
        this.startAttributes();

        if(this.attributes.containsKey(key)) {
            this.attributes.replace(key, value);
            return;
        }

        this.attributes.put(key, value);
    }

    @Override
    public Object getAttribute(String key) {
        this.startAttributes();

        return this.attributes.get(key);
    }

    @Override
    public boolean hasAttribute(String key) {
        this.startAttributes();

        return this.attributes.containsKey(key);
    }

    @Override
    public void removeAttribute(String key) {
        this.startAttributes();

        this.attributes.remove(key);
    }

    private void startAttributes() {
        if(this.attributes != null) return;

        this.attributes = new THashMap<>();
    }

    @Override
    public void toggleState() {
        String data = this.getData().getExtraData();

        if (!data.isEmpty() && !HexUtils.isNumeric(data)) return;

        int interactionCycleCount = this.getDefinition().getInteractionModesCount();

        if (interactionCycleCount <= 1) return;

        final int cycleValue = data.isEmpty() || data.trim().isEmpty() ? 0 : Integer.parseInt(data);

        this.getData().setExtraData(String.valueOf(
                (cycleValue + 1) % interactionCycleCount
        ));
    }

    @Override
    public void write(final IMessageComposer composer) {
        this.write(composer, false);
    }

    @Override
    public void write(final IMessageComposer composer, final boolean shouldComposeOwnerName) {
        RoomFloorItemWriter.write(this, composer, shouldComposeOwnerName);
    }
}
