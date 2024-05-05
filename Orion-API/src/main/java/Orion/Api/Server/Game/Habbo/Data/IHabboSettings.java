package Orion.Api.Server.Game.Habbo.Data;

import Orion.Api.Util.IFillable;

public interface IHabboSettings extends IFillable {
    int getRespectPointsReceived();

    int getAchievementScore();

    int getRespectPointsGiven();

    int getPetRespectPointsToGive();

    int getRespectPointsToGive();

    boolean isBlockFollowing();

    boolean isBlockFriendRequests();

    boolean isBlockRoomInvites();

    boolean isPreferOldChat();

    boolean isBlockCameraFollow();

    int getGuild();

    String[] getTags();

    boolean allowTrade();

    int getClubExpireTimestamp();

    int getLoginStreak();

    int getRentedItemId();

    int getRentedTimeEnd();

    int getSystemVolume();

    int getFurnitureVolume();

    int getTraxVolume();

    int getChatColor();

    int getHofPoints();

    boolean isBlockStaffAlerts();

    int getCitizenshipLevel();

    int getHelpersLevel();

    boolean isIgnoreBots();

    boolean isIgnorePets();

    boolean isNux();

    int getMuteEndTime();

    boolean allowNameChange();

    boolean isPerkTrade();

    int getForumPostsCount();

    int getUiFlags();

    boolean isHasGottenDefaultSavedSearches();

    int getMaxFriends();

    int getMaxRooms();

    int getLastHCPayday();

    int getHcGiftsClaimed();
}
