package Orion.Api.Server.Game.Catalog.Components;

import Orion.Api.Server.Game.Catalog.Layouts.ICatalogLayout;

public interface ICatalogLayoutsComponent {
    ICatalogLayout getByName(String name);

    void registerLayouts();
}