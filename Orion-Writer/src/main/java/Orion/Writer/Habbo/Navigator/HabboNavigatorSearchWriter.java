package Orion.Writer.Habbo.Navigator;

import Orion.Api.Networking.Message.IMessageComposer;
import Orion.Api.Server.Game.Habbo.Data.Navigator.IHabboNavigatorSearch;

public abstract class HabboNavigatorSearchWriter {
    public static void write(
            IHabboNavigatorSearch search,
            IMessageComposer composer
    ) {
        composer.appendInt(search.getId());
        composer.appendString(search.getSearchCode());
        composer.appendString(search.getFilter());
        composer.appendString("");
    }
}

