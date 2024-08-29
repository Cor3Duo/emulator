package Orion.Api.Server.Game.Util.Inventory;

public enum UnseenItemCategory {
    Unknown(0),
    OwnedItem(1),
    RentedItem(2),
    Pet(3),
    Badge(4),
    Bot(5),
    Game(6),

    ;

    private final int id;

    UnseenItemCategory(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
