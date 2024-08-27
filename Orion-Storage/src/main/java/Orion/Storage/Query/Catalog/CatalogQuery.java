package Orion.Storage.Query.Catalog;

public enum CatalogQuery {
    SELECT_ALL_PAGES("SELECT * FROM catalog_pages ORDER BY parent_id, id"),

    SELECT_ALL_ITEMS("SELECT * FROM catalog_items WHERE item_ids <> 0"),

    SELECT_ALL_FEATURED_PAGES("SELECT * FROM catalog_featured_pages ORDER BY slot_id ASC"),

    ;

    private final String query;

    CatalogQuery(String query) {
        this.query = query;
    }

    public String get() {
        return query;
    }
}
