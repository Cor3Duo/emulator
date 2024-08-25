package Orion.Game.Habbo;

import Orion.Api.Networking.Session.ISession;
import Orion.Api.Server.Game.Habbo.Data.*;
import Orion.Api.Server.Game.Habbo.IHabbo;
import Orion.Api.Server.Game.Room.Object.Entity.Type.IHabboEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

public class Habbo implements IHabbo {
    private final Logger logger;

    private ISession session;

    private IHabboData data;
    private IHabboSettings settings;
    private IHabboInventory inventory;
    private IHabboNavigator navigator;
    private IHabboRooms rooms;
    private IHabboCurrencies currencies;
    private IHabboAchievements achievements;
    private IHabboPermission permission;
    private IHabboMessenger messenger;

    private IHabboEntity entity;

    private final ConcurrentHashMap<String, Object> status;

    private boolean isDisposed = false;

    public Habbo(
            final IHabboData data,
            final IHabboSettings settings,
            final IHabboInventory inventory,
            final IHabboNavigator navigator,
            final IHabboRooms rooms,
            final IHabboCurrencies currencies,
            final IHabboAchievements achievements,
            final IHabboPermission permission,
            final IHabboMessenger messenger
    ) {
        this.data = data;
        this.settings = settings;
        this.inventory = inventory;
        this.navigator = navigator;
        this.rooms = rooms;
        this.currencies = currencies;
        this.achievements = achievements;
        this.permission = permission;
        this.messenger = messenger;

        this.status = new ConcurrentHashMap<>();

        this.logger = LogManager.getLogger(STR."[Habbo: \{this.data.getUsername()}]");
    }

    @Override
    public void setSession(ISession session) {
        this.session = session;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public ISession getSession() {
        return this.session;
    }

    @Override
    public IHabboData getData() {
        return this.data;
    }

    @Override
    public IHabboSettings getSettings() {
        return this.settings;
    }

    @Override
    public IHabboInventory getInventory() {
        return this.inventory;
    }

    @Override
    public IHabboNavigator getNavigator() {
        return this.navigator;
    }

    @Override
    public IHabboRooms getRooms() {
        return this.rooms;
    }

    @Override
    public IHabboCurrencies getCurrencies() {
        return this.currencies;
    }

    @Override
    public IHabboAchievements getAchievements() {
        return this.achievements;
    }

    @Override
    public IHabboPermission getPermission() {
        return this.permission;
    }

    @Override
    public IHabboMessenger getMessenger() {
        return this.messenger;
    }

    @Override
    public IHabboEntity getEntity() {
        return this.entity;
    }

    @Override
    public void setEntity(IHabboEntity entity) {
        this.entity = entity;
    }

    @Override
    public void addStatus(String condition, Object value) {
        this.status.put(condition, value);
    }

    @Override
    public void removeStatus(String condition) {
        this.status.remove(condition);
    }

    @Override
    public void clearStatus() {
        this.status.clear();
    }

    @Override
    public boolean hasStatus(String condition) {
        return this.status.containsKey(condition);
    }

    @Override
    public Object getStatus(String condition) {
        return this.status.get(condition);
    }

    @Override
    public ConcurrentHashMap<String, Object> getStatus() {
        return this.status;
    }

    @Override
    public boolean isInRoom() {
        return this.entity != null && this.entity.getRoom() != null;
    }

    @Override
    public boolean isDisposed() {
        return this.isDisposed || this.session.isDisposed();
    }

    @Override
    public void onDisconnect() {
        if(this.isDisposed) return;

        this.isDisposed = true;

        this.logger.debug("Just left the game.");

        if(this.entity != null) {
            this.entity.dispose();
            this.entity = null;
        }

        this.data = null;
        this.permission = null;

        this.settings.dispose();
        this.settings = null;

        this.navigator.dispose();
        this.navigator = null;

        this.rooms.dispose();
        this.rooms = null;

        this.currencies.dispose();
        this.currencies = null;

        this.inventory.dispose();
        this.inventory = null;

        this.achievements.dispose();
        this.achievements = null;

        this.messenger.dispose();
        this.messenger = null;
    }
}
