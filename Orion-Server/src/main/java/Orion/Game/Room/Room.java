package Orion.Game.Room;

import Orion.Api.Networking.Message.IMessageComposer;
import Orion.Api.Server.Game.Habbo.IHabbo;
import Orion.Api.Server.Game.Room.Component.*;
import Orion.Api.Server.Game.Room.Data.IRoomData;
import Orion.Api.Server.Game.Room.Data.Model.IRoomModel;
import Orion.Api.Server.Game.Room.IRoom;
import Orion.Api.Server.Game.Room.Object.Entity.IRoomEntity;
import Orion.Api.Server.Game.Room.Object.Entity.Type.IHabboEntity;
import Orion.Api.Server.Game.Room.Process.IRoomProcess;
import Orion.Api.Storage.Result.IConnectionResult;
import Orion.Game.Room.Component.*;
import Orion.Game.Room.Data.RoomData;
import Orion.Game.Room.Process.RoomProcess;
import Orion.Protocol.Message.Composer.Room.RemoveHabboEntityComposer;
import Orion.Writer.Room.RoomWriter;

public class Room implements IRoom {
    private final IRoomData data;

    private final IRoomModel model;
    private final IRoomProcess process;

    private boolean isMuted = false;
    private boolean isFullyLoaded = false;
    private int inactiveCycles = 0;

    private final IRoomBansComponent roomBansComponent;
    private final IRoomItemsComponent roomItemsComponent;
    private final IRoomVotesComponent roomVotesComponent;
    private final IRoomRightsComponent roomRightsComponent;
    private final IRoomMappingComponent roomMappingComponent;
    private final IRoomEntitiesComponent roomEntitiesComponent;
    private final IRoomChatComponent roomChatComponent;

    public Room(final IConnectionResult data, final IRoomModel model) {
        this.model = model;
        this.data = new RoomData(data);

        this.process = new RoomProcess(this);
        this.roomItemsComponent = new RoomItemsComponent(this);
        this.roomRightsComponent = new RoomRightsComponent(this);
        this.roomMappingComponent = new RoomMappingComponent(this);
        this.roomEntitiesComponent = new RoomEntitiesComponent(this);
        this.roomChatComponent = new RoomChatComponent(this);

        this.roomBansComponent = new RoomBansComponent();
        this.roomVotesComponent = new RoomVotesComponent();
    }

    public void initialize() {
        if(this.isFullyLoaded()) return;

        this.setFullyLoaded(true);

        this.roomMappingComponent.beforeInitialize();

        this.roomItemsComponent.initialize();
        this.roomEntitiesComponent.initialize();
        this.roomMappingComponent.initialize();

        this.process.initialize();
    }

    @Override
    public IRoomData getData() {
        return this.data;
    }

    @Override
    public IRoomModel getModel() {
        return this.model;
    }

    @Override
    public IRoomProcess getProcess() {
        return this.process;
    }

    @Override
    public IRoomMappingComponent getMappingComponent() {
        return this.roomMappingComponent;
    }

    @Override
    public IRoomEntitiesComponent getEntitiesComponent() {
        return this.roomEntitiesComponent;
    }

    @Override
    public IRoomRightsComponent getRightsComponent() {
        return this.roomRightsComponent;
    }

    @Override
    public IRoomBansComponent getBansComponent() {
        return this.roomBansComponent;
    }

    @Override
    public IRoomVotesComponent getVotesComponent() {
        return this.roomVotesComponent;
    }

    @Override
    public IRoomItemsComponent getItemsComponent() {
        return this.roomItemsComponent;
    }

    @Override
    public IRoomChatComponent getChatComponent() {
        return this.roomChatComponent;
    }

    @Override
    public boolean isFullyLoaded() {
        return this.isFullyLoaded;
    }

    @Override
    public void setFullyLoaded(boolean isFullyLoaded) {
        this.isFullyLoaded = isFullyLoaded;
    }

    @Override
    public boolean habboIsOwner(final IHabbo habbo) {
        return this.getData().getOwnerId() == habbo.getData().getId();
    }

    @Override
    public boolean isMuted() {
        return this.isMuted;
    }

    @Override
    public void setIsMuted(final boolean isMuted) {
        this.isMuted = isMuted;
    }

    @Override
    public boolean isInactive() {
        return this.getEntitiesComponent().getHabboEntities().isEmpty();
    }

    @Override
    public boolean shouldBeUnloaded() {
        // If the room is inactive and fully loaded, we can safely unload it after 1 minute.
        return this.isInactive() && this.isFullyLoaded() && this.inactiveCycles >= 120;
    }

    @Override
    public void checkInactivity() {
        if(!this.isInactive()) {
            this.inactiveCycles = 0;
            return;
        }

        this.inactiveCycles++;
    }

    @Override
    public void onEntityRemoved(final IRoomEntity entity) {
        this.process.onEntityRemoved(entity);

        this.broadcastMessage(new RemoveHabboEntityComposer(entity.getVirtualId()));
    }

    @Override
    public void broadcastMessage(final IMessageComposer composer) {
        for (final IHabboEntity habboEntity : this.getEntitiesComponent().getHabboEntities()) {
            habboEntity.getHabbo().getSession().send(composer);
        }
    }

    @Override
    public void broadcastMessages(final IMessageComposer... composers) {
        for (final IHabboEntity habboEntity : this.getEntitiesComponent().getHabboEntities()) {
            habboEntity.getHabbo().getSession().send(composers);
        }
    }

    @Override
    public void write(final IMessageComposer messageComposer) {
        RoomWriter.write(this, messageComposer);
    }

    @Override
    public int compareTo(IRoom o) {
        if(o.getEntitiesComponent().getHabboEntitiesCount() != this.getEntitiesComponent().getHabboEntitiesCount()) {
            return o.getEntitiesComponent().getHabboEntitiesCount() - this.getEntitiesComponent().getHabboEntitiesCount();
        }

        return o.getData().getId() - this.getData().getId();
    }

    @Override
    public void dispose() {
        this.roomEntitiesComponent.dispose();
        this.roomMappingComponent.dispose();

        this.process.dispose();
    }
}
