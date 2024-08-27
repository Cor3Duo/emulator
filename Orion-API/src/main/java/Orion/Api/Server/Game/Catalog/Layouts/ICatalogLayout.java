package Orion.Api.Server.Game.Catalog.Layouts;

import Orion.Api.Networking.Message.IMessageComposer;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;

public interface ICatalogLayout {
    void write(final ICatalogPage page, final IMessageComposer composer);
}
