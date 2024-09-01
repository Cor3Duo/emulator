package Orion.Api.Server.Game.Room.Data;

import Orion.Api.Server.Game.Room.Enums.RoomAccessState;
import Orion.Api.Server.Game.Room.Enums.RoomDiagonalType;
import Orion.Api.Util.IFillable;

import java.util.List;

public interface IRoomData extends IFillable {
    int getId();

    String getName();

    String getDescription();

    String getModel();

    int getOwnerId();

    String getOwnerName();

    int getMaxUsers();

    int getScore();

    String getPassword();

    RoomAccessState getAccessState();

    int getGuildId();

    int getCategoryId();

    String getPaperFloor();

    String getPaperWall();

    String getPaperLandscape();

    int getWallThickness();

    int getWallHeight();

    int getFloorThickness();

    String getMoodlightData();

    List<String> getTags();

    boolean isPublic();

    boolean isStaffPicked();

    boolean allowPets();

    boolean allowPetsEat();

    boolean allowWalkthrough();

    int getChatMode();

    int getChatWeight();

    int getChatSpeed();

    int getChatDistance();

    int getChatProtection();

    int getWhoCanMute();

    int getWhoCanKick();

    int getWhoCanBan();

    int getPollId();

    int getRollerSpeed();

    boolean isPromoted();

    int getTradeMode();

    RoomDiagonalType getDiagonalType();

    boolean hasJukeboxActive();

    boolean isForSale();

    boolean isHideWall();

    boolean isOverrideModel();

    boolean isHideWireds();

    void setName(final String name);

    void setDescription(final String description);

    void setAccessState(final RoomAccessState state);

    void setPassword(final String password);

    void setMaxUsers(final int maxUsers);

    void setCategoryId(final int categoryId);

    void setTradeMode(final int tradeMode);

    void setAllowPets(final boolean allowPets);

    void setAllowPetsEat(final boolean allowPetsEat);

    void setAllowWalkthrough(final boolean allowWalkthrough);

    void setHideWall(final boolean hideWall);

    void setWallThickness(final int wallThickness);

    void setFloorThickness(final int floorThickness);

    void setWhoCanMute(final int whoCanMute);

    void setWhoCanKick(final int whoCanKick);

    void setWhoCanBan(final int whoCanBan);

    void setChatMode(final int chatMode);

    void setChatWeight(final int chatWeight);

    void setChatSpeed(final int chatSpeed);

    void setChatDistance(final int chatDistance);

    void setChatProtection(final int chatProtection);

    void addTag(final String tag);

    void setScore(final int score);
}
