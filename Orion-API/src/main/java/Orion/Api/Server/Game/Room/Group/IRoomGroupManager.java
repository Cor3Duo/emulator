package Orion.Api.Server.Game.Room.Group;

import Orion.Api.Server.Game.Room.Group.Data.IGroupElement;
import Orion.Api.Util.Initializable;

import java.util.Map;

public interface IRoomGroupManager extends Initializable {
    Map<Integer, IGroupElement> getGroupBases();

    Map<Integer, IGroupElement> getGroupBaseColors();

    Map<Integer, IGroupElement> getGroupSymbols();

    Map<Integer, IGroupElement> getGroupSymbolColors();

    Map<Integer, IGroupElement> getGroupBackgroundColors();
}
