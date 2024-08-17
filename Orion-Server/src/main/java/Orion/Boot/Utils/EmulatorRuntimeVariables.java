package Orion.Boot.Utils;

import Orion.Api.Server.Boot.Utils.IEmulatorRuntimeVariables;
import com.google.inject.Singleton;

import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class EmulatorRuntimeVariables implements IEmulatorRuntimeVariables {
    public boolean isDebugMode;
    public AtomicInteger playersOnline = new AtomicInteger(0);

    @Override
    public void incrementPlayersOnline() {
        this.playersOnline.incrementAndGet();
    }

    @Override
    public void decrementPlayersOnline() {
        this.playersOnline.decrementAndGet();
    }

    @Override
    public int getPlayersOnline() {
        return this.playersOnline.get();
    }
}
