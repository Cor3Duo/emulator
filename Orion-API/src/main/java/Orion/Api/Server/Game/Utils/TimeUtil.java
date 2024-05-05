package Orion.Api.Server.Game.Utils;

public abstract class TimeUtil {
    /**
     * Get the current time in Unix Timestamp
     */
    public static long now() {
        return (int) (System.currentTimeMillis() / 1000L);
    }
}
