package Orion.Api.Server.Game.Catalog.Writers;

import Orion.Api.Networking.Message.IMessageComposer;
import Orion.Api.Server.Game.Catalog.Items.ICatalogItem;
import Orion.Api.Server.Game.Room.Object.Item.Base.IItemDefinition;
import Orion.Api.Server.Game.Room.Object.Item.ItemDefinitionType;

public abstract class CatalogItemWriter {
    public static void write(
            final ICatalogItem item,
            final IMessageComposer composer
    ) {
        composer.appendInt(item.getId());
        composer.appendString(item.getCatalogName());
        composer.appendBoolean(false);
        composer.appendInt(item.getCostCredits());
        composer.appendInt(item.getCostPoints());
        composer.appendInt(item.getPointsType());
        composer.appendBoolean(item.isAllowGift()); // Check HC and isClubOnly

        composer.appendInt(item.getItems().size());

        for (final IItemDefinition itemDefinition : item.getItems()) {
            composer.appendString(itemDefinition.getType().get().toLowerCase());

            if(itemDefinition.getType().equals(ItemDefinitionType.BADGE)) {
                composer.appendString(itemDefinition.getItemName());
            }

            composer.appendInt(itemDefinition.getSpriteId());

            composer.appendString(getExtraData(itemDefinition, item));

            composer.appendInt(item.getAmount());
            composer.appendBoolean(item.isLimited());

            if(item.isLimited()) {
                composer.appendInt(item.getLimitedStack());
                composer.appendInt(item.getLimitedSells());
            }
        }

        composer.appendInt(item.isClubOnly() ? 1 : 0);
        composer.appendBoolean(!item.isLimited() && item.isHaveOffer());
        composer.appendBoolean(false);
        composer.appendString(STR."\{item.getCatalogName()}.png");
    }

    public static String getExtraData(final IItemDefinition itemDefinition, final ICatalogItem item) {
        if(itemDefinition.isDecoration()) {
            return itemDefinition.getItemName().split("_")[2];
        }

        if(itemDefinition.getType().equals(ItemDefinitionType.ROBOT) && itemDefinition.getItemName().contains("bot")) {
            String botFigure = item.getExtraData();

            for (final String catalogItemData : item.getExtraData().split(";")) {
                if(!catalogItemData.startsWith("figure:")) continue;

                botFigure = catalogItemData.replaceAll("(?i)figure:", "");
            }

            return botFigure;
        }

        if(itemDefinition.getType().equals(ItemDefinitionType.ROBOT)
                || itemDefinition.getItemName().equalsIgnoreCase("poster")
                || itemDefinition.getItemName().startsWith("SONG ")
        ) {
            return item.getExtraData();
        }

        return "";
    }
}
