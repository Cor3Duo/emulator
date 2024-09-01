package Orion.Game.Room.Data;

import Orion.Api.Server.Game.Room.Data.IRoomData;
import Orion.Api.Server.Game.Room.Enums.RoomAccessState;
import Orion.Api.Server.Game.Room.Enums.RoomDiagonalType;
import Orion.Api.Storage.Result.IConnectionResult;

import java.util.Arrays;
import java.util.List;

public class RoomData implements IRoomData {
    private int id;
    private String name;
    private String description;
    private String model;
    private int ownerId;
    private String ownerName;
    private int maxUsers;
    private int score;
    private String password;
    private RoomAccessState state;
    private int guildId;
    private int categoryId;
    //private INavigatorCategory category; TODO
    private String paperFloor;
    private String paperWall;
    private String paperLandscape;
    private int thicknessWall;
    private int wallHeight;
    private int thicknessFloor;
    private String moodlightData;
    private List<String> tags;
    private boolean isPublic;
    private boolean isStaffPicked;
    private boolean allowPets;
    private boolean allowPetsEat;
    private boolean allowWalkthrough;
    private boolean hideWall;
    private int chatMode;
    private int chatWeight;
    private int chatSpeed;
    private int chatDistance;
    private int chatProtection;
    private boolean overrideModel;
    private int whoCanMute;
    private int whoCanKick;
    private int whoCanBan;
    private int pollId;
    private int rollerSpeed;
    private boolean isPromoted;
    private int tradeMode;
    private RoomDiagonalType diagonalType;
    private boolean hasJukeboxActive;
    private boolean hideWireds;
    private boolean isForSale;

    public RoomData(IConnectionResult data) {
        try {
            this.fill(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getModel() {
        return this.model;
    }

    public int getOwnerId() {
        return this.ownerId;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public int getMaxUsers() {
        return this.maxUsers;
    }

    public int getScore() {
        return this.score;
    }

    public String getPassword() {
        return this.password;
    }

    public RoomAccessState getAccessState() {
        return this.state;
    }

    public int getGuildId() {
        return this.guildId;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public String getPaperFloor() {
        return this.paperFloor;
    }

    public String getPaperWall() {
        return this.paperWall;
    }

    public String getPaperLandscape() {
        return this.paperLandscape;
    }

    public int getThicknessWall() {
        return this.thicknessWall;
    }

    public int getWallHeight() {
        return this.wallHeight;
    }

    public int getThicknessFloor() {
        return this.thicknessFloor;
    }

    public String getMoodlightData() {
        return this.moodlightData;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public boolean isStaffPicked() {
        return this.isStaffPicked;
    }

    public boolean allowPets() {
        return this.allowPets;
    }

    public boolean allowPetsEat() {
        return this.allowPetsEat;
    }

    public boolean allowWalkthrough() {
        return this.allowWalkthrough;
    }

    public boolean isHideWall() {
        return this.hideWall;
    }

    public int getChatMode() {
        return this.chatMode;
    }

    public int getChatWeight() {
        return this.chatWeight;
    }

    public int getChatSpeed() {
        return this.chatSpeed;
    }

    public int getChatDistance() {
        return this.chatDistance;
    }

    public int getChatProtection() {
        return this.chatProtection;
    }

    public boolean isOverrideModel() {
        return this.overrideModel;
    }

    public int getWhoCanMute() {
        return this.whoCanMute;
    }

    public int getWhoCanKick() {
        return this.whoCanKick;
    }

    public int getWhoCanBan() {
        return this.whoCanBan;
    }

    public int getPollId() {
        return this.pollId;
    }

    public int getRollerSpeed() {
        return this.rollerSpeed;
    }

    public boolean isPromoted() {
        return this.isPromoted;
    }

    public int getTradeMode() {
        return this.tradeMode;
    }

    public RoomDiagonalType getDiagonalType() {
        return this.diagonalType;
    }

    public boolean hasJukeboxActive() {
        return this.hasJukeboxActive;
    }

    public boolean isHideWireds() {
        return this.hideWireds;
    }

    public boolean isForSale() {
        return this.isForSale;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setAccessState(final RoomAccessState state) {
        this.state = state;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setMaxUsers(final int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public void setCategoryId(final int categoryId) {
        this.categoryId = categoryId;
    }

    public void setTradeMode(final int tradeMode) {
        this.tradeMode = tradeMode;
    }

    public void setAllowPets(final boolean allowPets) {
        this.allowPets = allowPets;
    }

    public void setAllowPetsEat(final boolean allowPetsEat) {
        this.allowPetsEat = allowPetsEat;
    }

    public void setAllowWalkthrough(final boolean allowWalkthrough) {
        this.allowWalkthrough = allowWalkthrough;
    }

    public void setHideWall(final boolean hideWall) {
        this.hideWall = hideWall;
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.id = result.getInt("id");
        this.name = result.getString("name");
        this.description = result.getString("description");
        this.model = result.getString("model");
        this.ownerId = result.getInt("owner_id");
        this.ownerName = result.getString("owner_name");
        this.maxUsers = result.getInt("users_max");
        this.guildId = result.getInt("guild_id");
        this.score = result.getInt("score");
        this.password = result.getString("password");
        this.state = RoomAccessState.fromValue(result.getString("state"));
        this.categoryId = result.getInt("category");
        this.paperFloor = result.getString("paper_floor");
        this.paperWall = result.getString("paper_wall");
        this.paperLandscape = result.getString("paper_landscape");
        this.thicknessWall = result.getInt("thickness_wall");
        this.wallHeight = result.getInt("wall_height");
        this.thicknessFloor = result.getInt("thickness_floor");
        this.moodlightData = result.getString("moodlight_data");
        this.tags = Arrays.asList(result.getString("tags").split(";"));
        this.isPublic = result.getBoolean("is_public");
        this.isStaffPicked = result.getBoolean("is_staff_picked");
        this.allowPets = result.getBoolean("allow_other_pets");
        this.allowPetsEat = result.getBoolean("allow_other_pets_eat");
        this.allowWalkthrough = result.getBoolean("allow_walkthrough");
        this.hideWall = result.getBoolean("allow_hidewall");
        this.chatMode = result.getInt("chat_mode");
        this.chatWeight = result.getInt("chat_weight");
        this.chatSpeed = result.getInt("chat_speed");
        this.chatDistance = result.getInt("chat_hearing_distance");
        this.chatProtection = result.getInt("chat_protection");
        this.overrideModel = result.getBoolean("override_model");
        this.whoCanMute = result.getInt("who_can_mute");
        this.whoCanKick = result.getInt("who_can_kick");
        this.whoCanBan = result.getInt("who_can_ban");
        this.pollId = result.getInt("poll_id");
        this.rollerSpeed = result.getInt("roller_speed");
        this.isPromoted = result.getBoolean("promoted");
        this.tradeMode = result.getInt("trade_mode");
        this.diagonalType = result.getBoolean("move_diagonally") ? RoomDiagonalType.OFFICIAL : RoomDiagonalType.DISABLED;
        this.hasJukeboxActive = result.getBoolean("jukebox_active");
        this.hideWireds = result.getBoolean("hidewired");
        this.isForSale = result.getBoolean("is_forsale");
    }
}
