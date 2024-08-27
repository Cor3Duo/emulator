package Orion.Api.Server.Game.Util.Alert;

public enum AlertPurchaseUnavailableCode {
    ILLEGAL_PURCHASE(0),
    REQUIRES_CLUB(1),

    ;

    private final int code;

    AlertPurchaseUnavailableCode(int code) {
        this.code = code;
    }

    public int get() {
        return this.code;
    }
}
