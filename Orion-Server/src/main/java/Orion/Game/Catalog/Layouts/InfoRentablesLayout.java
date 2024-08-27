package Orion.Game.Catalog.Layouts;

import Orion.Api.Server.Game.Catalog.Layouts.ICatalogLayout;
import com.google.inject.Singleton;
import Orion.Api.Server.Game.Catalog.Data.ICatalogPage;
import Orion.Api.Networking.Message.IMessageComposer;

@Singleton
public class InfoRentablesLayout implements ICatalogLayout {
    public void write(final ICatalogPage page, final IMessageComposer composer) {
        composer.appendString("info_rentables");

        String[] textData = page.getPageText1().split("\\|\\|");

        composer.appendInt(1);
        composer.appendString(page.getPageHeadline());
        composer.appendInt(textData.length);

        for (final String text : textData) {
            composer.appendString(text);
        }

        composer.appendInt(0);
    }
}
