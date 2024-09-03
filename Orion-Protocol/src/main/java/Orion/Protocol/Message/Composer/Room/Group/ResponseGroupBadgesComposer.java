package Orion.Protocol.Message.Composer.Room.Group;

import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

public class ResponseGroupBadgesComposer extends MessageComposer {
    public ResponseGroupBadgesComposer() {
        super(ComposerHeaders.ResponseGroupBadgesComposer);
    }
}
