package Orion.Protocol.Message.Composer.Alerts;

import Orion.Api.Server.Game.Util.Alert.AlertPurchaseFailedCode;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

public class PurchaseFailedAlertComposer extends MessageComposer {
    public PurchaseFailedAlertComposer(AlertPurchaseFailedCode code) {
        super(ComposerHeaders.PurchaseFailedAlertComposer);

        appendInt(code.get());
    }
}
