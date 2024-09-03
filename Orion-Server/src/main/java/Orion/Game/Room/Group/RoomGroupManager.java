package Orion.Game.Room.Group;

import Orion.Api.Server.Game.Room.Group.Data.IGroupElement;
import Orion.Api.Server.Game.Room.Group.IRoomGroupManager;
import Orion.Api.Server.Game.Util.Group.GroupElementType;
import Orion.Api.Storage.Repository.Room.IRoomGroupRepository;
import Orion.Game.Room.Group.Data.GroupElement;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import gnu.trove.map.hash.THashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@Singleton
public class RoomGroupManager implements IRoomGroupManager {
    private final Logger logger = LogManager.getLogger();

    @Inject
    private IRoomGroupRepository roomGroupRepository;

    private final Map<GroupElementType, Map<Integer, IGroupElement>> groupElements;

    public RoomGroupManager() {
        this.groupElements = new THashMap<>();
    }

    @Override
    public Map<Integer, IGroupElement> getGroupBases() {
        return this.groupElements.get(GroupElementType.Base);
    }

    public Map<Integer, IGroupElement> getGroupBaseColors() {
        return this.groupElements.get(GroupElementType.BaseColor);
    }

    public Map<Integer, IGroupElement> getGroupSymbols() {
        return this.groupElements.get(GroupElementType.Symbol);
    }

    public Map<Integer, IGroupElement> getGroupSymbolColors() {
        return this.groupElements.get(GroupElementType.SymbolColor);
    }

    public Map<Integer, IGroupElement> getGroupBackgroundColors() {
        return this.groupElements.get(GroupElementType.BackgroundColor);
    }

    @Override
    public void initialize() {
        this.loadAllGroupParts();
    }

    private void loadAllGroupParts() {
        this.roomGroupRepository.loadAllGroupParts(result -> {
            if(result == null) return;

            try {
                final IGroupElement element = new GroupElement(result);

                if(!this.groupElements.containsKey(element.getType())) {
                    this.groupElements.put(element.getType(), new THashMap<>());
                }

                this.groupElements.get(element.getType()).put(element.getId(), element);
            } catch (Exception e) {
                this.logger.error("Failed to load an element group", e);
            }
        });
    }
}
