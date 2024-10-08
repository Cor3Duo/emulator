package Orion.Api.Server.Game.Habbo.Data;

import Orion.Api.Server.Game.Habbo.Data.Navigator.IHabboNavigatorCategorySetting;
import Orion.Api.Server.Game.Habbo.Data.Navigator.IHabboNavigatorSearch;
import Orion.Api.Server.Game.Habbo.Data.Navigator.IHabboNavigatorWindowSetting;
import Orion.Api.Server.Game.Navigator.Enums.NavigatorDisplayMode;
import Orion.Api.Server.Game.Navigator.Enums.NavigatorLayoutDisplay;
import Orion.Api.Util.IDisposable;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;

import java.util.List;

public interface IHabboNavigator extends IDisposable {
    void setNavigatorSearches(THashSet<IHabboNavigatorSearch> navigatorSearches);

    void setNavigatorCategoriesSettings(THashMap<String, IHabboNavigatorCategorySetting> navigatorCategoriesSettings);

    IHabboNavigatorWindowSetting getNavigatorWindowSettings();

    THashSet<IHabboNavigatorSearch> getNavigatorSearches();

    IHabboNavigatorCategorySetting getNavigatorCategorySettingsFromName(String name);

    NavigatorDisplayMode getDisplayModeForCategory(String name);

    NavigatorDisplayMode getDisplayModeForCategory(String name, NavigatorDisplayMode defaultMode);

    NavigatorLayoutDisplay getLayoutDisplayForCategory(String name);

    NavigatorLayoutDisplay getLayoutDisplayForCategory(String name, NavigatorLayoutDisplay defaultLayout);

    List<IHabboNavigatorSearch> getNavigatorSearchForTab(String tabName);
}
