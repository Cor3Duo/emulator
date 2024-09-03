package Orion.Storage.Query.Room;

public enum RoomGroupQuery {
    LOAD_ALL_GROUP_PARTS("SELECT * FROM guilds_elements"),

    ;

    private final String query;

    RoomGroupQuery(final String query) {
        this.query = query;
    }

    public String get() {
        return this.query;
    }
}
