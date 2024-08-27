package Orion.Api.Server.Game.Catalog.Layouts;

import Orion.Api.Networking.Message.IMessageComposer;

public interface ICatalogFrontPage extends ICatalogLayout {
    void writeFrontPage(final IMessageComposer composer);
}
