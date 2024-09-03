package Orion.Api.Server.Game.Room.Group.Data;

import Orion.Api.Server.Game.Util.Group.GroupElementType;
import Orion.Api.Util.IFillable;

public interface IGroupElement extends IFillable {
    int getId();

    String getFirstValue();

    String getSecondValue();

    GroupElementType getType();
}
