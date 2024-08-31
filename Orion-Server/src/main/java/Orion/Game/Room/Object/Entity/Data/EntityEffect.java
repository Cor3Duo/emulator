package Orion.Game.Room.Object.Entity.Data;

import Orion.Api.Server.Game.Room.Object.Entity.Data.IEntityEffect;

public class EntityEffect implements IEntityEffect {
    private final int effectId;
    private int duration;
    private final int itemId;

    public EntityEffect(int effectId) {
        this(effectId, 0, 0);
    }

    public EntityEffect(int effectId, int duration) {
        this(effectId, duration, 0);
    }

    public EntityEffect(int effectId, int duration, int itemId) {
        this.itemId = itemId;
        this.effectId = effectId;
        this.duration = duration * 2;
    }

    public int getEffectId() {
        return this.effectId;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getItemId() {
        return this.itemId;
    }

    @Override
    public boolean isItemEffect() {
        return this.itemId > 0;
    }

    @Override
    public int decreaseDuration() {
        return --this.duration;
    }
}
