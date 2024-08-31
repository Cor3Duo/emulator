package Orion.Game.Room.Object.Entity;

import Orion.Api.Networking.Message.IMessageComposer;
import Orion.Api.Server.Game.Habbo.IHabbo;
import Orion.Api.Server.Game.Room.IRoom;
import Orion.Api.Server.Game.Room.Object.Entity.Component.IEntityEffectComponent;
import Orion.Api.Server.Game.Room.Object.Entity.Component.IEntityWalkComponent;
import Orion.Api.Server.Game.Room.Object.Entity.Enum.RoomEntityStatus;
import Orion.Api.Server.Game.Room.Object.Entity.Type.IHabboEntity;
import Orion.Api.Server.Game.Util.Position;
import Orion.Game.Room.Object.Entity.Component.EntityEffectComponent;
import Orion.Game.Room.Object.Entity.Component.EntityWalkComponent;
import Orion.Protocol.Message.Composer.HotelView.GoToHotelViewComposer;
import Orion.Protocol.Message.Composer.Room.Entities.GiveHandItemComposer;
import Orion.Writer.Room.Object.Entity.HabboEntityWriter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HabboEntity implements IHabboEntity {
    private IHabbo habbo;

    private final int virtualId;

    private final IRoom room;

    private Position position;
    private Position nextPosition;

    private int headRotation;
    private int bodyRotation;

    private final IEntityWalkComponent walkComponent;

    private final Map<RoomEntityStatus, String> status;

    private int danceId;

    private boolean isDisposed = false;
    private boolean needsUpdate = false;

    private int handItemId;
    private int handItemTimer;

    private final IEntityEffectComponent effectComponent;

    public HabboEntity(int virtualId, final IHabbo habbo, final IRoom room) {
        this.virtualId = virtualId;

        this.status = new ConcurrentHashMap<>();

        this.habbo = habbo;
        this.room = room;
        this.position = new Position(room.getModel().getData().getDoorX(), room.getModel().getData().getDoorY(), room.getModel().getDoorZ());

        this.headRotation = room.getModel().getData().getDoorDirection();
        this.bodyRotation = room.getModel().getData().getDoorDirection();

        this.walkComponent = new EntityWalkComponent(this);
        this.effectComponent = new EntityEffectComponent(this);
    }

    @Override
    public IHabbo getHabbo() {
        return this.habbo;
    }

    @Override
    public void setHabbo(IHabbo habbo) {
        this.habbo = habbo;
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
    public int getHeadRotation() {
        return this.headRotation;
    }

    @Override
    public int getBodyRotation() {
        return this.bodyRotation;
    }

    @Override
    public void setHeadRotation(int headRotation) {
        this.headRotation = headRotation;
    }

    @Override
    public void setBodyRotation(int bodyRotation) {
        this.bodyRotation = bodyRotation;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Position getNextPosition() {
        return this.nextPosition;
    }

    @Override
    public void setNextPosition(Position position) {
        this.nextPosition = position;
    }

    @Override
    public IEntityWalkComponent getWalkComponent() {
        return this.walkComponent;
    }

    @Override
    public boolean isWalking() {
        return !this.walkComponent.getProcessingPath().isEmpty();
    }

    @Override
    public void setStatus(RoomEntityStatus status, String value) {
        if(this.status.containsKey(status)) {
            this.status.replace(status, value);
        } else {
            this.status.put(status, value);
        }
    }

    @Override
    public void removeStatus(RoomEntityStatus status) {
        if(!this.status.containsKey(status)) return;

        this.status.remove(status);
    }

    @Override
    public boolean hasStatus(RoomEntityStatus status) {
        return this.status.containsKey(status);
    }

    @Override
    public String getStatus(RoomEntityStatus status) {
        return this.status.get(status);
    }

    @Override
    public void clearStatuses() {
        this.status.clear();
    }

    @Override
    public Map<RoomEntityStatus, String> getAllStatus() {
        return this.status;
    }

    @Override
    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }

    @Override
    public boolean needsUpdate() {
        return this.needsUpdate;
    }

    @Override
    public void setDanceId(int id) {
        this.danceId = id;
    }

    @Override
    public int getDanceId() {
        return this.danceId;
    }

    @Override
    public void sit(double height) {
        this.sit(height, this.bodyRotation);
    }

    @Override
    public void sit(double height, int rotation) {
        this.removeStatus(RoomEntityStatus.LAY);

        this.setStatus(RoomEntityStatus.SIT, String.valueOf(height).replace(",", "."));

        this.bodyRotation = Position.calculateSitRotation(rotation);
        this.headRotation = this.bodyRotation;

        this.setNeedsUpdate(true);
    }

    @Override
    public void lookAt(final int x, final int y) {
        if(this.position.equals(x, y)) return;

        if(this.hasStatus(RoomEntityStatus.LAY)) return;

        final int rotation = Position.calculateRotation(this.position.getX(), this.position.getY(), x, y, false);

        if(!this.hasStatus(RoomEntityStatus.SIT)) {
            this.bodyRotation = rotation;
        }

        if(Math.abs(rotation - this.bodyRotation) <= 1) {
            this.headRotation = rotation;
        }

        this.setNeedsUpdate(true);
    }

    @Override
    public void giveHandItem(int handItemId) {
        this.giveHandItem(handItemId, 240);
    }

    @Override
    public void giveHandItem(int handItemId, int timer) {
        this.handItemId = handItemId;
        this.handItemTimer = timer;

        this.room.broadcastMessage(new GiveHandItemComposer(this.getVirtualId(), handItemId));
    }

    @Override
    public void tickHandItem() {
        if(this.handItemId <= 0 || this.handItemTimer == -999) return;

        if(this.handItemTimer > 0) {
            this.handItemTimer--;
            return;
        }

        this.handItemId = 0;
        this.handItemTimer = 0;

        this.room.broadcastMessage(new GiveHandItemComposer(this.getVirtualId(), 0));
    }

    @Override
    public IEntityEffectComponent getEffectComponent() {
        return this.effectComponent;
    }

    @Override
    public void write(IMessageComposer composer) {
        HabboEntityWriter.write(this, composer);
    }

    @Override
    public boolean isDisposed() {
        return this.isDisposed || this.getHabbo().isDisposed();
    }

    @Override
    public void dispose() {
        this.isDisposed = true;

        this.room.getEntitiesComponent().removeEntity(this);

        this.position = null;
        this.nextPosition = null;

        this.habbo.setEntity(null);
        this.habbo.getSession().send(new GoToHotelViewComposer());

        this.habbo = null;

        this.status.clear();
    }
}
