package Orion.Api.Server.Game.Room.Object.Entity.Data;

public interface IEntityEffect {
    int getEffectId();

    int getDuration();

    int getItemId();

    boolean isItemEffect();

    int decreaseDuration();
}
