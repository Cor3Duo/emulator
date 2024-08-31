package Orion.Protocol.Message.Composer.Room.Entities.Effects;

import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

public class RoomEntityEffectComposer extends MessageComposer {
    public RoomEntityEffectComposer(int entityId, int effectId, int delay) {
        super(ComposerHeaders.RoomEntityEffectComposer);

        appendInt(entityId);
        appendInt(effectId);
        appendInt(delay);
    }
}
