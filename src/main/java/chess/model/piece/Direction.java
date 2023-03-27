package chess.model.piece;

import chess.model.position.File;
import chess.model.position.Rank;
import java.util.Arrays;
import java.util.List;

public enum Direction {

    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0),
    NORTH_EAST(1, 1),
    NORTH_WEST(-1, 1),
    SOUTH_EAST(1, -1),
    SOUTH_WEST(-1, -1),

    NORTH_NORTH_EAST(1, 2),
    NORTH_NORTH_WEST(-1, 2),
    SOUTH_SOUTH_EAST(1, -2),
    SOUTH_SOUTH_WEST(-1, -2),
    NORTH_WEST_WEST(-2, 1),
    NORTH_EAST_EAST(2, 1),
    SOUTH_WEST_WEST(-2, -1),
    SOUTH_EAST_EAST(2, -1);

    public static final List<Direction> DIAGONAL = List.of(NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST);
    public static final List<Direction> ORTHOGONAL = List.of(NORTH, WEST, SOUTH, EAST);
    
    private final int file;
    private final int rank;

    Direction(final int file, final int rank) {
        this.file = file;
        this.rank = rank;
    }

    public static Direction findDirection(final int rank, final int file) {
        return Arrays.stream(values())
                .filter(direction -> direction.match(file, rank))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("방향을 찾을 수 없습니다."));
    }

    public boolean match(final int file, final int rank) {
        int gcd = gcd(Math.abs(rank), Math.abs(file));

        return this.rank == calculateDirection(rank, gcd) && this.file == calculateDirection(file, gcd);
    }

    private int gcd(final int a, final int b) {
        if (b == 0) {
            return a;
        }

        return gcd(b, a % b);
    }

    private int calculateDirection(final int target, final int gcd) {
        if (target == 0) {
            return 0;
        }
        return (target / gcd);
    }

    public Rank findNextRank(final Rank rank) {
        return rank.findNextRank(this.rank);
    }

    public File findNextFile(final File file) {
        return file.findNextFile(this.file);
    }
}