package Orion.Game.Room.Group.Data;

import Orion.Api.Server.Game.Room.Group.Data.IGroupElement;
import Orion.Api.Server.Game.Util.Group.GroupElementType;
import Orion.Api.Storage.Result.IConnectionResult;

public class GroupElement implements IGroupElement {
    private int id;
    private String firstValue;
    private String secondValue;
    private GroupElementType type;

    public GroupElement(final IConnectionResult result) throws Exception {
        this.fill(result);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getFirstValue() {
        return this.firstValue;
    }

    @Override
    public String getSecondValue() {
        return this.secondValue;
    }

    @Override
    public GroupElementType getType() {
        return this.type;
    }

    @Override
    public void fill(IConnectionResult data) throws Exception {
        this.id = data.getInt("id");
        this.firstValue = data.getString("firstvalue");
        this.secondValue = data.getString("secondvalue");
        this.type = GroupElementType.from(data.getString("type"));
    }
}
