package Orion.Game.Room.Object.Entity.Component;

import Orion.Api.Server.Game.Room.Object.Entity.Component.IEntityEffectComponent;
import Orion.Api.Server.Game.Room.Object.Entity.Data.IEntityEffect;
import Orion.Api.Server.Game.Room.Object.Entity.Type.IHabboEntity;
import Orion.Game.Room.Object.Entity.Data.EntityEffect;
import Orion.Protocol.Message.Composer.Room.Entities.Effects.RoomEntityEffectComposer;

public class EntityEffectComponent implements IEntityEffectComponent {
    private final IHabboEntity habboEntity;

    private IEntityEffect effect;

    private boolean needsToBeRemoved;

    public EntityEffectComponent(final IHabboEntity habboEntity) {
        this.habboEntity = habboEntity;
    }

    @Override
    public void setEffect(int effectId, int duration, int itemId) {
        this.effect = new EntityEffect(effectId, duration, itemId);

        this.habboEntity.getRoom().broadcastMessage(
                new RoomEntityEffectComposer(this.habboEntity.getVirtualId(), effectId, 0)
        );
    }

    @Override
    public boolean hasEffect(int effectId) {
        return this.hasEffect(effectId, false);
    }

    @Override
    public boolean hasItemEffect() {
        return this.effect != null && this.effect.isItemEffect();
    }

    @Override
    public boolean hasEffect(int effectId, boolean isItemEffect) {
        if(this.effect == null) return false;

        if(this.effect.getEffectId() == effectId) {
            return !isItemEffect || this.effect.getItemId() > 0;
        }

        return false;
    }

    @Override
    public IEntityEffect getEffect() {
        return this.effect;
    }

    @Override
    public void process() {
        if(this.needsToBeRemoved && this.effect != null) {
            this.removeEffect();
            return;
        }

        if(this.effect != null && this.effect.getDuration() > 0 && this.effect.decreaseDuration() < 1) {
            this.needsToBeRemoved = true;
        }
    }

    @Override
    public void removeEffect() {
        this.effect = null;
        this.needsToBeRemoved = false;

        this.habboEntity.getRoom().broadcastMessage(
                new RoomEntityEffectComposer(this.habboEntity.getVirtualId(), 0, 0)
        );
    }

    @Override
    public boolean needsToBeRemoved() {
        return this.needsToBeRemoved;
    }

    @Override
    public void setToBeRemoved(boolean needsToBeRemoved) {
        this.needsToBeRemoved = needsToBeRemoved;
    }
}
