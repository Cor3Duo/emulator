package Orion.Game.Catalog.Data.Pages;

import Orion.Api.Networking.Message.IMessageComposer;
import Orion.Api.Storage.Result.IConnectionResult;
import Orion.Game.Catalog.Data.CatalogPage;

public class RootCatalogPage extends CatalogPage {
    public RootCatalogPage(IConnectionResult result) {
        super(result);
    }

    @Override
    public void write(final IMessageComposer composer, boolean canSeeIds) {
        composer.appendBoolean(true);
        composer.appendInt(0);
        composer.appendInt(-1);
        composer.appendString("root");
        composer.appendString("");
        composer.appendInt(0);
    }
}