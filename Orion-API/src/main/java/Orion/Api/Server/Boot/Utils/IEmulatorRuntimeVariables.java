package Orion.Api.Server.Boot.Utils;

public interface IEmulatorRuntimeVariables {
    void incrementPlayersOnline();

    void decrementPlayersOnline();

    int getPlayersOnline();
}
