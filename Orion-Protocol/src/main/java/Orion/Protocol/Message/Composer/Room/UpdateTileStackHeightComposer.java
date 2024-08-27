package Orion.Protocol.Message.Composer.Room;

import Orion.Api.Server.Game.Room.Data.Model.IRoomTile;
import Orion.Networking.Message.MessageComposer;
import Orion.Protocol.Message.Composer.ComposerHeaders;

import java.util.List;

public class UpdateTileStackHeightComposer extends MessageComposer {
    public UpdateTileStackHeightComposer(final List<IRoomTile> tilesToUpdate) {
        super(ComposerHeaders.UpdateTileStackHeightComposer);

        appendInt(tilesToUpdate.size());

        for (final IRoomTile tile : tilesToUpdate) {
            this.composeTile(tile);
        }
    }

    public UpdateTileStackHeightComposer(final IRoomTile tile) {
        super(ComposerHeaders.UpdateTileStackHeightComposer);

        appendInt(1);

        this.composeTile(tile);
    }

    private void composeTile(final IRoomTile tile) {
        appendByte(tile.getPosition().getX());
        appendByte(tile.getPosition().getY());
        appendShort((short) tile.getStackHeight());
    }
}
