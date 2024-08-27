package Orion.Api.Server.Game.Util.Alert;

public enum AlertPurchaseFailedCode {
    SERVER_ERROR(0),
    ALREADY_HAVE_BADGE(1),
    UNKNOWN(2)

    ;

    private final int code;

    AlertPurchaseFailedCode(int code) {
        this.code = code;
    }

    public int get() {
        return this.code;
    }
}
