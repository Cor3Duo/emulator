package Orion.Api.Server.Game.Room.Object.Entity;

import Orion.Api.Server.Game.Room.Object.Entity.Component.IEntityEffectComponent;
import Orion.Api.Server.Game.Room.Object.Entity.Component.IEntityWalkComponent;
import Orion.Api.Server.Game.Room.Object.Entity.Data.IEntityEffect;
import Orion.Api.Server.Game.Room.Object.IRoomObject;
import Orion.Api.Server.Game.Util.Position;
import Orion.Api.Util.IDisposable;
import Orion.Api.Util.IStatusable;
import Orion.Api.Util.IWriteable;

public interface IRoomEntity extends IRoomObject, IWriteable, IStatusable, IDisposable {
    int getHeadRotation();

    int getBodyRotation();

    void setHeadRotation(int headRotation);

    void setBodyRotation(int bodyRotation);

    Position getNextPosition();

    void setNextPosition(Position position);

    void setNeedsUpdate(boolean needsUpdate);

    boolean needsUpdate();

    void setDanceId(int id);

    int getDanceId();

    void sit(double height);

    void sit(double height, int rotation);

    void giveHandItem(int handItemId);

    void giveHandItem(int handItemId, int timer);

    void tickHandItem();

    IEntityWalkComponent getWalkComponent();

    IEntityEffectComponent getEffectComponent();

    boolean isDisposed();

    void leaveRoom();
}
