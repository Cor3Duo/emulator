package Orion.Api.Server.Game.Util;

import java.util.ArrayList;
import java.util.List;

public class Position {
    public static final Position ZERO = new Position(0, 0, 0);

    public static final int NORTH = 0;
    public static final int NORTH_EAST = 1;
    public static final int EAST = 2;
    public static final int SOUTH_EAST = 3;
    public static final int SOUTH = 4;
    public static final int SOUTH_WEST = 5;
    public static final int WEST = 6;
    public static final int NORTH_WEST = 7;

    public static final int[] COLLIDE_TILES = new int[]{
            NORTH, EAST, SOUTH, WEST
    };

    public static final int[] DIAG_TILES = new int[]{
            NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST
    };

    private final int x;
    private final int y;
    private final double z;

    public Position(int x, int y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = 0d;
    }

    public static int calculateRotation(int x, int y, int newX, int newY, boolean isReverse) {
        int rotation = 0;

        if (x > newX && y > newY)
            rotation = 7;
        else if (x < newX && y < newY)
            rotation = 3;
        else if (x > newX && y < newY)
            rotation = 5;
        else if (x < newX && y > newY)
            rotation = 1;
        else if (x > newX)
            rotation = 6;
        else if (x < newX)
            rotation = 2;
        else if (y < newY)
            rotation = 4;

        if (isReverse) {
            rotation = rotation > 3 ? rotation - 4 : rotation + 4;
        }

        return rotation;
    }

    public static int calculateRotation(Position oldPosition, Position newPosition, boolean isReverse) {
        return calculateRotation(oldPosition.getX(), oldPosition.getY(), newPosition.getX(), newPosition.getY(), isReverse);
    }

    public static int getInvertedRotation(int currentRotation) {
        return switch (currentRotation) {
            case NORTH_EAST -> SOUTH_WEST;
            case NORTH_WEST -> SOUTH_EAST;
            case SOUTH_WEST -> NORTH_EAST;
            case SOUTH_EAST -> NORTH_WEST;
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
            default -> currentRotation;
        };
    }

    public Position positionInFront(int rotation) {
        return calculatePosition(this.x, this.y, rotation, false, 1);
    }

    public static Position calculatePosition(int x, int y, int angle, boolean isReversed, int distance) {
        if (isReversed) {
            angle = (angle + 4) % 8;
        }

        switch (angle) {
            case 0:
                y -= distance;
                break;
            case 1:
                x += distance;
                y -= distance;
                break;
            case 2:
                x += distance;
                break;
            case 3:
                x += distance;
                y += distance;
                break;
            case 4:
                y += distance;
                break;
            case 5:
                x -= distance;
                y += distance;
                break;
            case 6:
                x -= distance;
                break;
            case 7:
                x -= distance;
                y -= distance;
                break;
        }

        return new Position(x, y);
    }
    
    public static int calculateSitRotation(int rotation) {
        switch (rotation) {
            case 1:
            case 3:
            case 5:
            case 7: {
                rotation++;
                break;
            }
        }

        return rotation;
    }

    public static List<Position> getAffectedPositions(
            int length,
            int width,
            int rotation,
            Position position
    ) {
        return getAffectedPositions(length, width, rotation, position, false);
    }

    public static List<Position> getAffectedPositions(
            int length,
            int width,
            int rotation,
            Position position,
            boolean shouldAddOriginalPosition
    ) {
        final List<Position> positions = new ArrayList<>();

        if(shouldAddOriginalPosition) {
            positions.add(position);
        }

        for(int y = 0; y < length; y++) {
            for(int x = 0; x < width; x++) {
                if(shouldAddOriginalPosition && x == 0 && y == 0) continue;

                if(rotation == 0 || rotation == 4) {
                    positions.add(new Position(position.getX() + x, position.getY() + y));
                    continue;
                }

                if(rotation == 2 || rotation == 6) {
                    positions.add(new Position(position.getX() + y, position.getY() + x));
                }
            }
        }

        return positions;
    }

    public Position add(Position other) {
        return new Position(other.getX() + getX(), other.getY() + getY(), other.getZ() + getZ());
    }

    public int getDistanceSquared(Position point) {
        return Math.abs(this.x - point.getX()) + Math.abs(this.y - point.getY());
    }

    public boolean equals(Position o) {
        return o.getX() == this.getX() && o.getY() == this.getY();
    }

    public boolean equals(int x, int y) {
        return x == this.getX() && y == this.getY();
    }

    public boolean isTouching(Position pos) {
        if (!(Math.abs(this.getX() - pos.getX()) > 1 || Math.abs(this.getY() - pos.getY()) > 1)) {
            return true;
        }

        return this.getX() == pos.getX() && this.getY() == pos.getY();
    }

    public Position squareInFront(int angle) {
        return calculatePosition(this.x, this.y, angle, false, 1);
    }

    public Position squareBehind(int angle) {
        return calculatePosition(this.x, this.y, angle, true, 1);
    }

    public Position copy() {
        return new Position(this.x, this.y, this.z);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }
}
