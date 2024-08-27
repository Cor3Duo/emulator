package Orion.Api.Server.Game.Catalog.Enums;

public enum CatalogFeaturedPageType {
    PAGE_NAME(0),
    PAGE_ID(1),
    PRODUCT_NAME(2)
    ;

    private final int value;

    CatalogFeaturedPageType(int value) {
        this.value = value;
    }

    public static CatalogFeaturedPageType fromValue(String value) {
        return switch (value) {
            case "page_id" -> PAGE_ID;
            case "product_name" -> PRODUCT_NAME;
            default -> PAGE_NAME;
        };
    }

    public int get() {
        return value;
    }
}