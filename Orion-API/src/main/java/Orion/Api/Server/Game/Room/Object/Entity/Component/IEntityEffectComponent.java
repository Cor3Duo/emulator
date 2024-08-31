package Orion.Api.Server.Game.Room.Object.Entity.Component;

import Orion.Api.Server.Game.Room.Object.Entity.Data.IEntityEffect;

public interface IEntityEffectComponent {
    void setEffect(int effectId, int duration, int itemId);

    boolean hasEffect(int effectId);

    boolean hasItemEffect();

    boolean hasEffect(int effectId, boolean isItemEffect);

    IEntityEffect getEffect();

    void process();

    void removeEffect();

    boolean needsToBeRemoved();

    void setToBeRemoved(boolean needsToBeRemoved);
}
