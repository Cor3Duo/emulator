package Orion.Game.Room;

import Orion.Api.Server.Game.Room.IRoomManager;
import Orion.Task.ThreadManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Singleton
public class RoomGlobalCycle implements Runnable {
    private final Logger logger = LogManager.getLogger();

    private ScheduledFuture<?> scheduledFuture;

    @Inject
    private ThreadManager threadManager;

    @Inject
    private IRoomManager roomManager;

    public void start() {
        this.scheduledFuture = this.threadManager.getRoomProcessingExecutor().scheduleAtFixedRate(
                this, 500, 500, TimeUnit.MILLISECONDS
        );
    }

    @Override
    public void run() {
        this.unloadInactiveRooms();
    }

    private void unloadInactiveRooms() {
        this.roomManager.getRoomsToUnload().forEach(room -> {
            room.dispose();
            this.roomManager.removeLoadedRoom(room);
        });
    }
}
