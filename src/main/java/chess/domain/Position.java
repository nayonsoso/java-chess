package chess.domain;

import chess.domain.piece.Direction;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Position {

    private static final Map<String, Position> CACHE;

    private final File file;

    private final Rank rank;

    static {
        CACHE = Arrays.stream(File.values())
                .flatMap(Position::createPositionWithEachRank)
                .collect(Collectors.toMap(
                        position -> toKey(position.file, position.rank),
                        position -> position));
    }

    private static Stream<Position> createPositionWithEachRank(File file) {
        return Arrays.stream(Rank.values())
                .map(rank -> new Position(file, rank));
    }

    private static String toKey(final File file, final Rank rank) {
        return file.name() + rank.name();
    }

    private Position(final File file, final Rank rank) {
        this.file = file;
        this.rank = rank;
    }

    public static Position of(final String input) {
        char fileIdx = input.substring(0, 1).charAt(0);
        int rankIdx = Integer.parseInt(input.substring(1, 2));
        File file = File.from(fileIdx);
        Rank rank = Rank.from(rankIdx);
        Position position = CACHE.get(toKey(file, rank));
        if (position == null) {
            throw new IllegalArgumentException("범위를 벗어난 위치입니다.");
        }

        return position;
    }

    public static Position of(final File file, final Rank rank) {
        Position position = CACHE.get(toKey(file, rank));
        if (position == null) {
            throw new IllegalArgumentException("범위를 벗어난 위치입니다.");
        }

        return position;
    }

    public Position moveTowardDirection(final Direction direction) {
        File fileAfterMove = direction.moveFile(this.file);
        Rank rankAfterMove = direction.moveRank(this.rank);
        return CACHE.get(toKey(fileAfterMove, rankAfterMove));
    }

    public Direction calculateDirection(final Position target) {
        int dx = target.file.calculateDifference(this.file);
        int dy = target.rank.calculateDifference(this.rank);

        return Direction.find(dx, dy);
    }

    public boolean isAtFile(final File file) {
        return this.file == file;
    }

    public boolean isAtRank(final Rank rank) {
        return this.rank == rank;
    }

    public File getFile() {
        return file;
    }

    public Rank getRank() {
        return rank;
    }
}
