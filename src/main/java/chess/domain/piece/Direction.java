package chess.domain.piece;

import chess.domain.File;
import chess.domain.Rank;

import java.util.Arrays;
import java.util.Set;

public enum Direction {

    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, 1),
    DOWN(0, -1),
    LEFT_UP(-1, 1),
    LEFT_DOWN(-1, -1),
    RIGHT_UP(1, 1),
    RIGHT_DOWN(1, -1),
    LEFT_LEFT_UP(-2, 1),
    LEFT_LEFT_DOWN(-2, -1),
    RIGHT_RIGHT_UP(2, 1),
    RIGHT_RIGHT_DOWN(2, -1),
    LEFT_UP_UP(-1, 2),
    RIGHT_UP_UP(1, 2),
    LEFT_DOWN_DOWN(-1, -2),
    RIGHT_DOWN_DOWN(1, -2),
    ;

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Direction find(final int dx, final int dy) {
        return Arrays.stream(values())
                .filter(direction -> direction.isSameGradiant(dx, dy) && direction.isSameSign(dx, dy))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이동 방향입니다."));
    }

    public File moveFile(final File currentFile) {
        return currentFile.moveByOffset(this.x);
    }

    public Rank moveRank(final Rank currentRank) {
        return currentRank.moveByOffset(this.y);
    }

    private boolean isSameGradiant(int dx, int dy) {
        return (double) this.x / this.y == (double) dx / dy;
    }

    private boolean isSameSign(int dx, int dy) {
        return this.x * dx >= 0 && this.y * dy >= 0;
    }

    public static Set<Direction> getHorizonAndVerticals() {
        return Set.of(LEFT, RIGHT, UP, DOWN);
    }

    public static Set<Direction> getDiagonals() {
        return Set.of(LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN);
    }

    public static Set<Direction> getHorizonVerticalAndDiagonals() {
        return Set.of(LEFT, RIGHT, UP, DOWN, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN);
    }

    public static Set<Direction> getLShapes() {
        return Set.of(LEFT_LEFT_UP, LEFT_LEFT_DOWN, RIGHT_RIGHT_UP, RIGHT_RIGHT_DOWN,
                LEFT_UP_UP, RIGHT_UP_UP, LEFT_DOWN_DOWN, RIGHT_DOWN_DOWN);
    }
}
