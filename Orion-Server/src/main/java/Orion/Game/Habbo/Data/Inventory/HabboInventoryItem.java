package Orion.Game.Habbo.Data.Inventory;

import Orion.Api.Networking.Message.IMessageComposer;
import Orion.Api.Server.Game.Habbo.Data.Inventory.IHabboInventoryItem;
import Orion.Api.Server.Game.Room.Object.Item.Base.IItemDefinition;
import Orion.Api.Server.Game.Room.Object.Item.Data.ILimitedEditionData;
import Orion.Api.Server.Game.Room.Object.Item.IRoomItem;
import Orion.Api.Storage.Result.IConnectionResult;
import Orion.Game.Room.Object.Item.Data.LimitedEditionData;
import Orion.Writer.Habbo.Inventory.HabboInventoryItemWriter;

public class HabboInventoryItem implements IHabboInventoryItem {
    private long id;
    private int userId;
    private final IItemDefinition itemDefinition;
    private String extraData;
    private ILimitedEditionData limitedItemData;

    public HabboInventoryItem(IConnectionResult data, IItemDefinition itemDefinition) {
        this.itemDefinition = itemDefinition;

        try {
            this.fill(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HabboInventoryItem(
            int id,
            int userId,
            IItemDefinition itemDefinition,
            String extraData,
            ILimitedEditionData limitedItemData
    ) {
        this.id = id;
        this.userId = userId;
        this.itemDefinition = itemDefinition;
        this.extraData = extraData;
        this.limitedItemData = limitedItemData;
    }

    public HabboInventoryItem(IRoomItem item) {
        this.id = item.getData().getItemId();
        this.userId = item.getData().getOwnerId();
        this.itemDefinition = item.getDefinition();
        this.extraData = item.getData().getExtraData();
        this.limitedItemData = item.getData().getLimitedData();
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public int getUserId() {
        return this.userId;
    }

    @Override
    public IItemDefinition getItemDefinition() {
        return this.itemDefinition;
    }

    @Override
    public String getExtraData() {
        return this.extraData;
    }

    @Override
    public ILimitedEditionData getLimitedEditionData() {
        return this.limitedItemData;
    }

    @Override
    public void fill(IConnectionResult data) throws Exception {
        this.id = data.getLong("id");
        this.userId = data.getInt("user_id");
        this.extraData = data.getString("extra_data");

        if(!data.getString("limited_data").isEmpty() && !data.getString("limited_data").equals("0:0")) {
            this.limitedItemData = new LimitedEditionData(data.getString("limited_data"));
        } else {
            this.limitedItemData = null;
        }
    }

    @Override
    public void write(IMessageComposer composer) {
        HabboInventoryItemWriter.write(this, composer);
    }
}
