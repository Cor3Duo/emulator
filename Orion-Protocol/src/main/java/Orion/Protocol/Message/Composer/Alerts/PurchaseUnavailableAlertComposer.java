package Orion.Protocol.Message.Composer.Alerts;

import Orion.Api.Server.Game.Util.Alert.AlertPurchaseUnavailableCode;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

public class PurchaseUnavailableAlertComposer extends MessageComposer {
    public PurchaseUnavailableAlertComposer(AlertPurchaseUnavailableCode code) {
        super(ComposerHeaders.PurchaseUnavailableAlertComposer);

        appendInt(code.get());
    }
}
