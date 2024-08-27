package Orion.Protocol.Message.Composer.Catalog;

import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Server.Game.Catalog.ICatalogManager;
import Orion.Api.Server.Game.Habbo.IHabbo;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

import java.util.Collection;

public class CatalogModeComposer extends MessageComposer {
    public CatalogModeComposer(final String mode) {
        super(ComposerHeaders.CatalogModeComposer);

        appendString(mode.equalsIgnoreCase("normal") ? 0 : 1);
    }
}
