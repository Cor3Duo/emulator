package Orion.Protocol.Message.Composer.LifeCycle;

import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

public class PongComposer extends MessageComposer {
    public PongComposer(int ping) {
        super(ComposerHeaders.PongComposer);

        appendInt(ping);
    }
}
