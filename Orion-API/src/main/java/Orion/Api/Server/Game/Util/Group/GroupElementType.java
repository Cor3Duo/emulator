package Orion.Api.Server.Game.Util.Group;

public enum GroupElementType {
    Base,
    Symbol,
    BaseColor,
    SymbolColor,
    BackgroundColor;

    public static GroupElementType from(String type) throws Exception {
        return switch (type) {
            case "background_color" -> GroupElementType.BackgroundColor;
            case "base_color" -> GroupElementType.BaseColor;
            case "base" -> GroupElementType.Base;
            case "symbol" -> GroupElementType.Symbol;
            case "symbol_color" -> GroupElementType.SymbolColor;
            default -> throw new Exception("Group element type doesn't exists.");
        };
    }
}
