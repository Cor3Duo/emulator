package Orion.Game.Room.Process.Entity;

import Orion.Api.Server.Game.Room.Data.Model.IRoomTile;
import Orion.Api.Server.Game.Room.IRoom;
import Orion.Api.Server.Game.Room.Object.Entity.Enum.RoomEntityStatus;
import Orion.Api.Server.Game.Room.Object.Entity.IRoomEntity;
import Orion.Api.Server.Game.Room.Object.Entity.Type.IHabboEntity;
import Orion.Api.Server.Game.Room.Object.Item.IRoomFloorItem;
import Orion.Api.Server.Game.Util.Position;
import Orion.Protocol.Message.Composer.Room.Entities.RoomEntityStatusComposer;

import java.util.ArrayList;
import java.util.List;

public class HabboEntityProcess {
    private final IRoom room;

    private final List<IRoomEntity> entitiesToUpdate;

    public HabboEntityProcess(final IRoom room) {
        this.room = room;

        this.entitiesToUpdate = new ArrayList<>();
    }

    public void onEntityRemoved(final IRoomEntity entity) {
        if(entity instanceof IHabboEntity) {
            this.entitiesToUpdate.remove(entity);

            final IRoomTile tile = this.room.getMappingComponent().getTile(entity.getPosition());

            if(tile != null) {
                tile.onEntityLeave(entity);
            }
        }
    }

    public void process() {
        this.preProcess();
        this.postProcess();
    }

    private void preProcess() {
        for(final IHabboEntity entity : this.room.getEntitiesComponent().getHabboEntities()) {
            if(entity.isDisposed()) {
                this.onEntityRemoved(entity);
                continue;
            }

            entity.tickHandItem();
            entity.getEffectComponent().process();

            if(entity.needsUpdate()) {
                entity.setNeedsUpdate(false);

                this.entitiesToUpdate.add(entity);
            }

            if(entity.hasStatus(RoomEntityStatus.MOVE)) {
                entity.removeStatus(RoomEntityStatus.MOVE);
                entity.removeStatus(RoomEntityStatus.GESTURE);

                this.entitiesToUpdate.add(entity);
            }

            if(!entity.getWalkComponent().getWalkingPath().isEmpty()) {
                entity.getWalkComponent().setProcessingPath(entity.getWalkComponent().getWalkingPath());
                entity.getWalkComponent().clearWalkingPath();
            }

            if(entity.isWalking()) {
                this.processHabboEntityWalk(entity);
            }
        }
    }

    private void postProcess() {
        if(this.entitiesToUpdate.isEmpty()) return;

        this.room.broadcastMessage(new RoomEntityStatusComposer(this.entitiesToUpdate));

        for(final IRoomEntity entity : this.entitiesToUpdate) {
            if(entity instanceof IHabboEntity habboEntity && habboEntity.isDisposed()) {
                this.onEntityRemoved(entity);
                continue;
            }

            if(entity.hasStatus(RoomEntityStatus.SIGN)) {
                entity.removeStatus(RoomEntityStatus.SIGN);
            }

            if(entity.getNextPosition() == null) continue;

            IRoomTile tile = this.room.getMappingComponent().getTile(entity.getNextPosition().getX(), entity.getNextPosition().getY());

            if(tile == null) continue;

            entity.setPosition(new Position(entity.getNextPosition().getX(), entity.getNextPosition().getY(), tile.getWalkHeight()));
            entity.setNextPosition(null);

            tile.onEntityEnter(entity);
        }

        this.entitiesToUpdate.clear();
    }

    private void processHabboEntityWalk(final IHabboEntity entity) {
        final Position nextPosition = entity.getWalkComponent().getProcessingPath().getFirst();

        final IRoomTile currentTile = this.room.getMappingComponent().getTile(entity.getPosition().getX(), entity.getPosition().getY());
        final IRoomTile nextTile = this.room.getMappingComponent().getTile(nextPosition.getX(), nextPosition.getY());

        entity.setBodyRotation(Position.calculateRotation(entity.getPosition(), nextPosition, false));
        entity.setHeadRotation(entity.getBodyRotation());

        entity.setStatus(RoomEntityStatus.MOVE, STR."\{nextPosition.getX()},\{nextPosition.getY()},\{nextTile.getWalkHeight()}");

        entity.removeStatus(RoomEntityStatus.LAY);
        entity.removeStatus(RoomEntityStatus.SIT);

        entity.setNextPosition(nextTile.getPosition().copy());

        entity.getWalkComponent().getProcessingPath().removeFirst();

        this.entitiesToUpdate.add(entity);

        if(!entity.isWalking()) {
            entity.getWalkComponent().clearWalkingPath();
            entity.getWalkComponent().clearProcessingPath();
        }

        if(currentTile != null) {
            currentTile.onEntityLeave(entity);
        }
    }
}
